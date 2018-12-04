package theking530.staticpower.machines.basicfarmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockReed;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.registries.FarmerRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseRangeUpgrade;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityBasicFarmer extends TileEntityMachineWithTank {

	public static final int DEFAULT_RANGE = 2;
	
	private int range = DEFAULT_RANGE;
	private int blocksFarmedPerTick = 1;
	private int growthBonusChance = 50;
	private BlockPos currentCoordinate;
	private Random rand;
	private ArrayList<ItemStack> farmedStacks;
	private FluidContainerComponent fluidInteractionComponent;
	private boolean shouldDrawRadiusPreview;
	
	public TileEntityBasicFarmer() {
		initializeBasicMachine(2, 20, 100000, 100, 10);
		initializeTank(10000);	
		initializeSlots(3, 2, 9);

		registerComponent(fluidInteractionComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank));
		fluidInteractionComponent.setMode(FluidContainerInteractionMode.FILL);

		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 0, energyStorage));
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1, 2, 3, 4, 5, 6, 7, 8));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1));
		
		shouldDrawRadiusPreview = false;
		
		currentCoordinate = getStartingCoord();
		rand = new Random();
		farmedStacks = new ArrayList<ItemStack>();
	}
	@Override
	public void process(){
		updateGrowthChange();
		if(processingTimer < processingTime && canFarm()) {
			processingTimer++;
			useEnergy(maxEnergyUsagePerTick() * blocksFarmedPerTick);
		}else{
			if(farmedStacks.size() <= 1 && canFarm()) {
				for(int i=0; i<blocksFarmedPerTick; i++) {
					incrementPosition();
					checkFarmingPlot(currentCoordinate);
				}
				fluidTank.drain(1 * blocksFarmedPerTick, true);
				updateBlock();
				processingTimer = 0;
			}else{
				if(!getWorld().isRemote) {
					for(int i=farmedStacks.size()-1; i>=0; i--) {
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
	}
	public void updateGrowthChange() {
		if(fluidTank.getFluid() != null) {
			if(fluidTank.getFluid().getFluid() == ModFluids.StaticFluid) {
				growthBonusChance = 15;
			}else if(fluidTank.getFluid().getFluid() == ModFluids.EnergizedFluid){
				growthBonusChance = 25;
			}else if(fluidTank.getFluid().getFluid() == ModFluids.LumumFluid){
				growthBonusChance = 50;
			}else{
				growthBonusChance = 0;
			}
		}else{
			growthBonusChance = 1;
		}
	}
	@Override  
    public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        if(nbt.hasKey("CURR_X")) {
            currentCoordinate = new BlockPos(new Vec3i(nbt.getInteger("CURR_X"), nbt.getInteger("CURR_Y"), nbt.getInteger("CURR_Z")));	
        }
        if(nbt.hasKey("FARMED_COUNT")) {
            for(int i=0; i<nbt.getInteger("FARMED_COUNT"); i++) {
            	farmedStacks.add(new ItemStack(nbt.getCompoundTag("FARMED"+i)));
            }
        }
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
		nbt.setInteger("CURR_X", currentCoordinate.getX());
		nbt.setInteger("CURR_Y", currentCoordinate.getY());
		nbt.setInteger("CURR_Z", currentCoordinate.getZ());
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
		if(flag) {
			BaseRangeUpgrade tempUpgrade = (BaseRangeUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			range = (int) (DEFAULT_RANGE*tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0));
		}else{
			range = DEFAULT_RANGE;
		}
	}
	
	public FluidContainerComponent getFluidInteractionComponent() {
		return fluidInteractionComponent;
	}
	public int getRadius() {
		return range;
	}
	public int getGrowthBonus() {
		return growthBonusChance;
	}
	public boolean getShouldDrawRadiusPreview() {
		return shouldDrawRadiusPreview;
	}
	public void setShouldDrawRadiusPreview(boolean shouldDraw) {
		shouldDrawRadiusPreview = shouldDraw;
	}
	
	private void incrementPosition() {
		if(currentCoordinate == getEndingCoord()) {
			currentCoordinate = getStartingCoord();
			return;
		}
		if(getAdjustedCurrentPos().getX() >= getAdjustedEndingPos().getX()-1) {
			currentCoordinate = new BlockPos(getStartingCoord().getX(), currentCoordinate.getY(), currentCoordinate.getZ() + getZDirection());
		}
		if(getAdjustedCurrentPos().getZ() >= getAdjustedEndingPos().getZ()+1) {
			currentCoordinate = getStartingCoord();
		}
		if(getAdjustedCurrentPos().getX() <= getAdjustedEndingPos().getX()){
			currentCoordinate = currentCoordinate.add(getXDirection(), 0, 0);
			return;
		}
	}
	private BlockPos getAdjustedCurrentPos() {
		BlockPos temp1 = currentCoordinate.subtract(getStartingCoord());
		BlockPos absPos = new BlockPos(Math.abs(temp1.getX()), Math.abs(temp1.getY()), Math.abs(temp1.getZ()));
		return absPos;
	}
	private BlockPos getAdjustedEndingPos() {
		BlockPos temp1 = getEndingCoord().subtract(getStartingCoord());
		BlockPos absPos = new BlockPos(Math.abs(temp1.getX()), Math.abs(temp1.getY()), Math.abs(temp1.getZ()));
		return absPos;
	}
	private BlockPos getEndingCoord() {
		return new BlockPos(pos.getX() + range+1, pos.getY(), pos.getZ() + range);
	}
	private BlockPos getStartingCoord() {
		return new BlockPos(pos.getX() - range-1, pos.getY(), pos.getZ() - range);
	}
	private int getXDirection(){
		if(getStartingCoord().getX() > getEndingCoord().getX()) {
			return -1;
		}else{
			return 1;
		}
	}
	private int getZDirection(){
		if(getStartingCoord().getZ() > getEndingCoord().getZ()) {
			return -1;
		}else{
			return 1;
		}
	}
	public boolean canFarm() {
		if(energyStorage.getEnergyStored() >= getProcessingEnergy() * blocksFarmedPerTick && !slotsInput.getStackInSlot(0).isEmpty() && slotsInput.getStackInSlot(0).getItem() instanceof ItemHoe && !slotsInput.getStackInSlot(1).isEmpty() && slotsInput.getStackInSlot(1).getItem() instanceof ItemAxe) {
			if(fluidTank.getFluid() != null) {
				if(fluidTank.getFluid().amount > 0) {
					return true;
				}
			}
		}
		return false;
	}
	public void useHoe(){
		if(slotsInput.getStackInSlot(0) != ItemStack.EMPTY && slotsInput.getStackInSlot(0).getItem() instanceof ItemHoe) {
			if(slotsInput.getStackInSlot(0).attemptDamageItem(1, rand, null)) {
				slotsInput.setStackInSlot(0, ItemStack.EMPTY);
				getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);		
			}	
		}
	}
	public void useAxe(){
		if(slotsInput.getStackInSlot(1) != ItemStack.EMPTY && slotsInput.getStackInSlot(1).getItem() instanceof ItemAxe) {
			if(slotsInput.getStackInSlot(1).attemptDamageItem(1, rand, null)) {
				slotsInput.setStackInSlot(1, ItemStack.EMPTY);
				getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);		
			}	
		}
	}
	public boolean checkFarmingPlot(BlockPos pos) {
		boolean grown = false;
		if(!getWorld().isRemote) {
			grown = growCrop(pos);
		}
		if(grown) {
    		getWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5D, pos.getY() + 1.0D,  pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		getWorld().spawnParticle(EnumParticleTypes.TOTEM, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
		if(isFarmableBlock(pos)) {
			harvestGenericCrop(pos);			
			harvestSugarCane(pos);
			harvestCactus(pos);
			harvestNetherWart(pos);
			harvestMelonOrPumpkin(pos);
		}
		return true;
	}
	public boolean isFarmableBlock(BlockPos pos) {
		if(getWorld().getBlockState(pos) == null) {
			return false;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockCrops) {
			return true;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockReed){	 
			return true;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockCactus){	  
			return true;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockNetherWart) {
			return true;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockMelon) {
			return true;
		}
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockPumpkin) {
			return true;
		}
		return false;		
	}
	public boolean harvestGenericCrop(BlockPos pos) {
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockCrops) {
			BlockCrops tempCrop = (BlockCrops)getWorld().getBlockState(pos).getBlock();
			if(tempCrop.isMaxAge(getWorld().getBlockState(pos))) {
				getWorld().playSound(null, pos, getWorld().getBlockState(pos).getBlock().getSoundType(getWorld().getBlockState(pos), world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
	        	if(!getWorld().isRemote) {
					farmedStacks.addAll(getBlockDrops(pos));
					getWorld().setBlockState(pos, tempCrop.withAge(0), 3);	
					useHoe();
					if(farmedStacks.size() > 0) {
						for(int i=farmedStacks.size()-1; i>=0; i--) {
							if(farmedStacks.get(i).getItem() instanceof IPlantable) {
								farmedStacks.remove(i);
								break;
							}
						}
					}
	        	}
			}
		}
		return false;
	}
	public boolean harvestSugarCane(BlockPos pos) {
		if(getWorld().getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockReed){	   
        	if(!getWorld().isRemote) {       		
		        NonNullList<ItemStack> drops1 = NonNullList.create();
				getWorld().getBlockState(pos.add(0, 1, 0)).getBlock().getDrops(drops1, getWorld(), pos.add(0, 1, 0), getWorld().getBlockState(pos.add(0, 1, 0)), 0);
				farmedStacks.addAll(getBlockDrops(pos.add(0, 1, 0)));
				getWorld().setBlockToAir(pos.add(0, 1, 0));
        		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 2);
        		useAxe();
            	       		
				if(getWorld().getBlockState(pos.add(0, 2, 0)).getBlock() instanceof BlockReed) {
					farmedStacks.addAll(getBlockDrops(pos.add(0, 2, 0)));
					getWorld().setBlockToAir(pos.add(0, 2, 0));	
					getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 2);
					useAxe();
				}
				getWorld().playSound(null, pos, getWorld().getBlockState(pos.add(0, 1, 0)).getBlock().getSoundType(getWorld().getBlockState(pos.add(0, 1, 0)), world, pos.add(0, 1, 0), null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.add(0, 1, 0).getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
				return true;
        	}
		}
		return false;
	}
	public boolean harvestCactus(BlockPos pos) {
		if(getWorld().getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockCactus){	      
        	if(!getWorld().isRemote) {
				farmedStacks.addAll(getBlockDrops(pos.add(0, 1, 0)));
				getWorld().setBlockToAir(pos.add(0, 1, 0));	
				useAxe();
        		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
        		       		
				if(getWorld().getBlockState(pos.add(0, 2, 0)).getBlock() instanceof BlockCactus) {
					farmedStacks.addAll(getBlockDrops(pos.add(0, 2, 0)));
					getWorld().setBlockToAir(pos.add(0, 2, 0));	
					getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
					useAxe();
				}
				getWorld().playSound(null, pos, getWorld().getBlockState(pos.add(0, 1, 0)).getBlock().getSoundType(getWorld().getBlockState(pos.add(0, 1, 0)), world, pos.add(0, 1, 0), null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.add(0, 1, 0).getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
	        	return true;
        	}
		}
		return false;
	}
	public boolean harvestMelonOrPumpkin(BlockPos pos) {
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockMelon || getWorld().getBlockState(pos).getBlock() instanceof BlockPumpkin){	   
        	if(!getWorld().isRemote) {
				farmedStacks.addAll(getBlockDrops(pos));
				getWorld().setBlockToAir(pos);	
				useAxe();
        		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);       		       		
        	}
			getWorld().playSound(null, pos, getWorld().getBlockState(pos).getBlock().getSoundType(getWorld().getBlockState(pos), world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
        	return true;
		}
		return false;
	}
	public boolean harvestNetherWart(BlockPos pos) {
		if(getWorld().getBlockState(pos).getBlock() instanceof BlockNetherWart) {
			BlockNetherWart tempNetherwart = (BlockNetherWart) getWorld().getBlockState(pos).getBlock();
			if(tempNetherwart.getMetaFromState(getWorld().getBlockState(pos)) >= 3) {
				getWorld().playSound(null, pos, getWorld().getBlockState(pos).getBlock().getSoundType(getWorld().getBlockState(pos), world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
	        	if(!getWorld().isRemote) {
					farmedStacks.addAll(getBlockDrops(pos));
		        	getWorld().setBlockState(pos, Blocks.NETHER_WART.getDefaultState(), 2);
					useHoe();
					getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
					if(farmedStacks.size() > 0) {
						for(int i=farmedStacks.size()-1; i>=0; i--) {
							if(farmedStacks.get(i).getItem() instanceof IPlantable) {
								farmedStacks.remove(i);
								break;
							}
						}
					}
	        	}
			}
			return true;
		}
		return false;
	}
	public boolean growCrop(BlockPos pos) {
		if(rand.nextInt(100) <= growthBonusChance) {
	        if(getWorld().getBlockState(pos) != null && getWorld().getBlockState(pos).getBlock() instanceof IGrowable) {
	        	IGrowable tempCrop = (IGrowable) getWorld().getBlockState(pos).getBlock();
	        	if(tempCrop.canGrow(getWorld(), pos, getWorld().getBlockState(pos), true)) {
	        		tempCrop.grow(getWorld(), rand, pos, getWorld().getBlockState(pos));
	        		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
	        		return true;
	        	}
	        }	
		}
		return false;
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
		getWorld().getBlockState(currentCoordinate).getBlock().getDrops(ret, getWorld(), currentCoordinate, getWorld().getBlockState(currentCoordinate), 0);
		return ret;
	}
	@Override
	public String getName() {
		return "container.BasicFarmer";
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
