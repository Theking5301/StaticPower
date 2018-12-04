package theking530.staticpower.machines.treefarmer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;
import theking530.staticpower.assists.utilities.TileEntityUtilities;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.handlers.crafting.registries.FarmerRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseRangeUpgrade;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityTreeFarm extends TileEntityMachineWithTank {

	public static final int MAX_WOOD_RECURSIVE_DEPTH = 40;
	public static final int MAX_LEAVES_RECURSIVE_DEPTH = 40;
	public static final int DEFAULT_RANGE = 2;
	public static final int DEFAULT_SAPLING_SPACING = 2;
	
	private int range = DEFAULT_RANGE;
	
	private Random rand;
	private ArrayList<ItemStack> farmedStacks;
	private FluidContainerComponent fluidInteractionComponent;
	private boolean shouldDrawRadiusPreview;
	
	private List<BlockPos> blocks;
	private int currentBlockIndex;
	
	private Ingredient woodIngredient;
	private Ingredient leafIngredient;
	private Ingredient saplingIngredient;

	public TileEntityTreeFarm() {
		initializeBasicMachine(2, 20, 100000, 100, 10);
		initializeTank(10000);	
		initializeSlots(3, 10, 9);

		currentBlockIndex = 0;
		blocks = null;
		woodIngredient = Craft.ing("logWood");
		leafIngredient = Craft.ing("treeLeaves");
		saplingIngredient = Craft.ing("treeSapling");
		shouldDrawRadiusPreview = false;
		rand = new Random();
		farmedStacks = new ArrayList<ItemStack>();
		
		registerComponent(fluidInteractionComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank));
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 0, energyStorage));
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1, 2, 3, 4, 5, 6, 7, 8));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1, 2, 3, 4, 5, 6, 7));
		
		fluidInteractionComponent.setMode(FluidContainerInteractionMode.FILL);
		setName("container.TreeFarmer");
	}
	@Override
	public void process(){
		if(blocks == null) {		
			setNewBlockRange(range);
		}
		if(processingTimer < processingTime && canProcess()) {
			processingTimer++;
			useEnergy(maxEnergyUsagePerTick());
		}else{
			if(farmedStacks.size() <= 0 && canProcess()) {
				incrementPosition();
				farmTree(getCurrentPosition());
				fluidTank.drain(1, true);
				processingTimer = 0;
				updateBlock();
			}else{
				for(int i=farmedStacks.size()-1; i>=0; i--) {
					if(saplingIngredient.apply(farmedStacks.get(i))) {
						farmedStacks.set(i, InventoryUtilities.insertItemIntoInventory(slotsInput, farmedStacks.get(i), 0, 8));
					}
					ItemStack insertedStack = InventoryUtilities.insertItemIntoInventory(slotsOutput, farmedStacks.get(i), 0, 8);
					if(insertedStack == ItemStack.EMPTY) {
						farmedStacks.remove(i);
					}else{
						farmedStacks.set(i, insertedStack);
					}
				}
			}
		}	
	}
	
	@Override    
	public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        currentBlockIndex = nbt.getInteger("currentIndex");
        if(nbt.hasKey("FARMED_COUNT")) {
            for(int i=0; i<nbt.getInteger("FARMED_COUNT"); i++) {
            	farmedStacks.add(new ItemStack(nbt.getCompoundTag("FARMED"+i)));
            }
        }
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
		nbt.setInteger("currentIndex", currentBlockIndex);
		nbt.setInteger("FARMED_COUNT", farmedStacks.size());
		for(int i=0; i<farmedStacks.size(); i++) {
			nbt.setTag("FARMED" + i, farmedStacks.get(i).writeToNBT(new NBTTagCompound()));
		}
    	return nbt;
	}

	public void upgradeTick(){
		rangeUpgrade();
		super.upgradeTick();
	}
	public void rangeUpgrade() {
		boolean flag = false;
		int slot = 0;
		for(int i=0; i<3; i++) {
			if(slotsUpgrades.getStackInSlot(i) != null) {
				if(slotsUpgrades.getStackInSlot(i).getItem() instanceof BaseRangeUpgrade) {
					flag = true;
					slot = i;
				}
			}
		}
		int newRange = range;
		if(flag) {
			BaseRangeUpgrade tempUpgrade = (BaseRangeUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			newRange = (int) (DEFAULT_RANGE*tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0));
		}else{
			newRange = DEFAULT_RANGE;
		}
		if(newRange != range) {
			setNewBlockRange(newRange);
		}
		range = newRange;
	}
	private void setNewBlockRange(int range) {
		EnumFacing forwardDirection = getFacingDirection();
		EnumFacing rightDirection = SideUtilities.getEnumFacingFromSide(BlockSide.RIGHT, forwardDirection);
		
		BlockPos fromPosition = getPos().offset(forwardDirection.getOpposite());
		fromPosition = fromPosition.offset(forwardDirection.getOpposite(), range*2);
		fromPosition = fromPosition.offset(rightDirection.getOpposite(), range);
		
		BlockPos toPosition = getPos();
		toPosition = toPosition.offset(rightDirection, range);
		toPosition = toPosition.offset(forwardDirection.getOpposite(), 1);
		
		Iterable<BlockPos> blockPos = BlockPos.getAllInBox(fromPosition, toPosition);
		Iterator<BlockPos> it = blockPos.iterator();
		
		List<BlockPos> positions = new ArrayList<BlockPos>();
		do {
			BlockPos pos = it.next();
			positions.add(pos);
		}while(it.hasNext());
		blocks = positions;
		currentBlockIndex = 0;
	}
	
	private void incrementPosition() {
		if(currentBlockIndex < blocks.size()-1) {
			currentBlockIndex++;
		}else{
			currentBlockIndex = 0;
		}
		BlockPos pos = this.getCurrentPosition();
		getWorld().spawnParticle(EnumParticleTypes.TOTEM, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
	}
	public void useAxe(){
		if(slotsInput.getStackInSlot(1) != ItemStack.EMPTY && slotsInput.getStackInSlot(1).getItem() instanceof ItemAxe) {
			if(slotsInput.getStackInSlot(1).attemptDamageItem(1, rand, null)) {
				slotsInput.setStackInSlot(1, ItemStack.EMPTY);
				getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);		
			}	
		}
	}
	
	public boolean canProcess() {
		if(energyStorage.getEnergyStored() >= getProcessingEnergy() && !slotsInput.getStackInSlot(0).isEmpty() && slotsInput.getStackInSlot(0).getItem() instanceof ItemAxe) {
			if(fluidTank.getFluid() != null) {
				if(fluidTank.getFluid().amount > 0) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean farmTree(BlockPos pos) {
		if(!getWorld().isRemote) {
			for(int i=0; i<getRadius()*2; i++) {
				if(isFarmableBlock(pos.add(0, i, 0))) {
					farmedStacks.addAll(harvestTree(pos.add(0, i, 0), 0));		
				}
			}
			plantSapling(pos);
			if(bonemealSapling(pos)) {
	    		getWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5D, pos.getY() + 1.0D,  pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean isFarmableBlock(BlockPos pos) {
		Block block = getWorld().getBlockState(pos).getBlock();
		if(block != Blocks.AIR && !block.hasTileEntity(getWorld().getBlockState(pos)) && block.getBlockHardness(getWorld().getBlockState(pos), getWorld(), pos) != -1) {
			return woodIngredient.apply(new ItemStack(Item.getItemFromBlock(block)));
		}
		return false;		
	}
	
	@SuppressWarnings("deprecation")
	private List<ItemStack> harvestTree(BlockPos pos, int index) {
		List<ItemStack> wood = new ArrayList<ItemStack>();
		if(index >= MAX_WOOD_RECURSIVE_DEPTH) {
			return wood;
		}
		wood.addAll(getBlockDrops(pos));
		getWorld().playSound(null, pos, getWorld().getBlockState(pos).getBlock().getSoundType(getWorld().getBlockState(pos), world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
		getWorld().setBlockToAir(pos);	
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos testPos = pos.offset(facing);
			if(isFarmableBlock(testPos)) {
				wood.addAll(harvestTree(testPos, index + 1));	
			}
		}
		for(EnumFacing facing : EnumFacing.values()) {
			Block block = getWorld().getBlockState(pos.offset(facing)).getBlock();
			if(block != Blocks.AIR && !block.hasTileEntity(getWorld().getBlockState(pos)) && block.getBlockHardness(getWorld().getBlockState(pos), getWorld(), pos) != -1) {
				if(leafIngredient.apply(new ItemStack(Item.getItemFromBlock(block)))) {
					wood.addAll(recurseLeaves(pos.offset(facing), 0));	
				}
			}
		}
		return wood;
	}
	@SuppressWarnings("deprecation")
	private List<ItemStack> recurseLeaves(BlockPos pos, int index) {
		List<ItemStack> leaves = new ArrayList<ItemStack>();
		if(index >= MAX_LEAVES_RECURSIVE_DEPTH) {
			return leaves;
		}
		leaves.addAll(getBlockDrops(pos));
		getWorld().playSound(null, pos, getWorld().getBlockState(pos).getBlock().getSoundType(getWorld().getBlockState(pos), world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 0.5F, 1.0F);
		getWorld().setBlockToAir(pos);	
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos testPos = pos.offset(facing);
			Block block = getWorld().getBlockState(testPos).getBlock();
			if(block != Blocks.AIR && !block.hasTileEntity(getWorld().getBlockState(testPos)) && block.getBlockHardness(getWorld().getBlockState(testPos), getWorld(), testPos) != -1) {
				if(leafIngredient.apply(new ItemStack(Item.getItemFromBlock(block)))) {
					leaves.addAll(recurseLeaves(testPos, index + 1));	
				}
			}
		}
		return leaves;
	}
	public boolean bonemealSapling(BlockPos pos) {
		if(TileEntityUtilities.diceRoll(getGrowthBonusChance())) {
			ItemStack bonemeal = new ItemStack(Items.DYE, 1, 0);
			ItemDye.applyBonemeal(bonemeal, getWorld(), pos);
		}
		return true;
	}
	public boolean plantSapling(BlockPos pos) {
		if(currentBlockIndex % DEFAULT_SAPLING_SPACING == 0) {
			Block block = getWorld().getBlockState(pos).getBlock();
			if(block == Blocks.AIR) {
				for(int i=1; i<10; i++) {
					ItemStack inputStack = slotsInput.getStackInSlot(i);
					if(!inputStack.isEmpty() && saplingIngredient.apply(inputStack)) {
						FakePlayer player = FakePlayerFactory.getMinecraft((WorldServer)getWorld());
						player.setHeldItem(EnumHand.MAIN_HAND, inputStack.copy());
						inputStack.onItemUse(player, getWorld(), pos.offset(EnumFacing.DOWN), EnumHand.MAIN_HAND, EnumFacing.UP, 0.0f, 1.0f, 0.0f);
						slotsInput.extractItem(i, 1, false);
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public FluidContainerComponent getFluidInteractionComponent() {
		return fluidInteractionComponent;
	}
	public int getRadius() {
		return range;
	}
	public float getGrowthBonusChance() {
		if(fluidTank.getFluid() != null) {
			if(FarmerRecipeRegistry.Farming().getOutput(fluidTank.getFluid()) != null) {
				return FarmerRecipeRegistry.Farming().getOutput(fluidTank.getFluid()).getTreeFarmerBonemealChance();
			}
		}
		return 0;
	}
	public BlockPos getCurrentPosition() {
		return blocks.get(currentBlockIndex);
	}
	public boolean getShouldDrawRadiusPreview() {
		return shouldDrawRadiusPreview;
	}
	public void setShouldDrawRadiusPreview(boolean shouldDraw) {
		shouldDrawRadiusPreview = shouldDraw;
	}
	
    @Override
	public int fill(FluidStack resource, boolean doFill, EnumFacing facing) {
		if(resource != null && FarmerRecipeRegistry.Farming().getOutput(resource) != null) {
			return fluidTank.fill(resource, doFill);
		}
		return 0;
	}
	public List<ItemStack> getBlockDrops(BlockPos pos) {
        NonNullList<ItemStack> ret = NonNullList.create();
		getWorld().getBlockState(pos).getBlock().getDrops(ret, getWorld(), pos, getWorld().getBlockState(pos), 0);
		return ret;
	}
    @SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		if(facing == EnumFacing.UP) {
    			return (T) slotsInput;
    		}else{
    			return (T) slotsOutput;
    		}
    	}
    	return super.getCapability(capability, facing);
    }
    
}
