package theking530.staticpower.machines.quarry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.items.itemfilter.ItemFilter;
import theking530.staticpower.items.upgrades.BaseQuarryingUpgrade;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;

public class TileEntityQuarry extends TileEntityMachineWithTank {
	public static final int INITIAL_BLOCKS_PER_TICK = 1;
	
	private BlockPos startCorner;
	private BlockPos endCorner;
	private BlockPos currentQuarryingPosition;
	
	private boolean isConfigured;
	
	private int blocksMinedPertick = INITIAL_BLOCKS_PER_TICK;

	private ArrayList<ItemStack> quarriedStacks = new ArrayList<ItemStack>();
	private FluidContainerComponent fluidInteractionComponent;
	
	public TileEntityQuarry() {
		initializeBasicMachine(2, 100, 100000, 1000, 10);
		initializeTank(10000);
		initializeSlots(4, 0, 1);
		
		startCorner = new BlockPos(0, 0, 0);
		endCorner = startCorner;
		currentQuarryingPosition = startCorner;
		isConfigured = false;
		
		registerComponent(new BatteryInteractionComponent("BatteryInteraction", slotsInternal, 3, energyStorage));
		registerComponent(fluidInteractionComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank));
		fluidInteractionComponent.setMode(FluidContainerInteractionMode.FILL);
		setName("container.Quarry");
	}
	
	@Override
	public void process(){
		if(!getWorld().isRemote) {
			if(quarriedStacks.size() <=0 && isAbleToMine()) {
				drawStartingAndEndingCoords();
				if(!isDoneMining()) {
					if(processingTimer >= processingTime && quarriedStacks.size() <= 0 && energyStorage.getEnergyStored() >= processingEnergyMult*100) {
						for(int i=0; i<blocksMinedPertick; i++) {
							incrementPosition();
							quarriedStacks.addAll(getCurrentBlockDrops());
							if(mineBlock()) {
								if(getFortuneMultiplier() > 0) {
									fluidTank.drain(1, true);
								}
							}
							energyStorage.extractEnergy(getProcessingEnergy(), false);
						}
						updateBlock();
						processingTimer = 0;		
					}else{
						processingTimer++;
					}		
				}	
			}
			if(quarriedStacks.size() > 0) {
				flushQuarriedStacks();
			}	
		}
	}	
	public void upgradeTick(){
		quarryingUpgrade();
		super.upgradeTick();
	}
	public void quarryingUpgrade() {
		boolean flag = false;
		int slot = 0;
		for(int i=0; i<3; i++) {
			if(slotsUpgrades.getStackInSlot(i) != null) {
				if(slotsUpgrades.getStackInSlot(i).getItem() instanceof BaseQuarryingUpgrade) {
					flag = true;
					slot = i;
				}
			}
		}
		if(flag) {
			BaseQuarryingUpgrade tempUpgrade = (BaseQuarryingUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			blocksMinedPertick = (int) tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0);
			processingEnergyMult = (int) (initialProcessingEnergyMult*tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 1));
		}else{
			blocksMinedPertick = INITIAL_BLOCKS_PER_TICK;
			processingEnergyMult = initialProcessingEnergyMult;
		}
	}
	
	@Override    
	public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);      
        currentQuarryingPosition = WorldUtilities.readBlockPosFromNBT(nbt, "CURRENT");
        endCorner = WorldUtilities.readBlockPosFromNBT(nbt, "ENDING");
        startCorner = WorldUtilities.readBlockPosFromNBT(nbt, "STARTING");
        isConfigured = nbt.getBoolean("configured");
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
    	WorldUtilities.writeBlockPosToNBT(nbt, currentQuarryingPosition, "CURRENT");   	
    	WorldUtilities.writeBlockPosToNBT(nbt, endCorner, "ENDING");
    	WorldUtilities.writeBlockPosToNBT(nbt, startCorner, "STARTING");
    	nbt.setBoolean("configured", isConfigured);
		return nbt;	
	}
    	
	private void incrementPosition() {
		if(currentQuarryingPosition.getY() <= 0) {
			return;
		}
		if(currentQuarryingPosition == endCorner) {
			return;
		}
		if(getAdjustedCurrentPos().getX() >= getAdjustedEndingPos().getX()-1) {
			currentQuarryingPosition = new BlockPos(startCorner.getX(), currentQuarryingPosition.getY(), currentQuarryingPosition.getZ() + getZDirection());
		}
		if(getAdjustedCurrentPos().getZ() >= getAdjustedEndingPos().getZ()) {
			currentQuarryingPosition = new BlockPos(currentQuarryingPosition.getX(), currentQuarryingPosition.getY()-1, startCorner.getZ()+getZDirection());
		}
		if(getAdjustedCurrentPos().getX() < getAdjustedEndingPos().getX()){
			currentQuarryingPosition = currentQuarryingPosition.add(getXDirection(), 0, 0);
			return;
		}
	}
	@SuppressWarnings("deprecation")
	private boolean mineBlock() {
		Block block = getWorld().getBlockState(currentQuarryingPosition).getBlock();
		if(block != Blocks.BEDROCK && block != Blocks.AIR && !block.hasTileEntity(getWorld().getBlockState(currentQuarryingPosition)) && block.getBlockHardness(getWorld().getBlockState(pos), getWorld(), pos) != -1) {
			getWorld().playSound(null, currentQuarryingPosition, getWorld().getBlockState(currentQuarryingPosition).getBlock().getSoundType(getWorld().getBlockState(currentQuarryingPosition), world, currentQuarryingPosition, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			getWorld().setBlockToAir(currentQuarryingPosition);	
			return true;
		}
		return false;
	}
	private void drawStartingAndEndingCoords() {
		if (getWorld().isRemote && isAbleToMine()) { 
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, startCorner.getX() + 0.5D, startCorner.getY() + 1.0D, 
					startCorner.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, endCorner.getX() + 0.5D, endCorner.getY() + 1.0D, 
					endCorner.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, startCorner.getX() + 0.5D, startCorner.getY() + 1.0D, 
					endCorner.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, endCorner.getX() + 0.5D, endCorner.getY() + 1.0D, 
					startCorner.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}
	
	private void flushQuarriedStacks() {
		for(int k=quarriedStacks.size()-1; k>=0; k--) {
			if(getInternalStack(0) != ItemStack.EMPTY && getInternalStack(0).getItem() instanceof ItemFilter) {
				ItemFilter tempFilter = (ItemFilter)getInternalStack(0).getItem();
				if(!tempFilter.evaluateFilter(getInternalStack(0), quarriedStacks.get(k))) {
					quarriedStacks.remove(k);
					continue;
				}
				
			}
			if(InventoryUtilities.canInsertItemIntoInventory(slotsOutput, quarriedStacks.get(k))) {
				ItemStack remaining = InventoryUtilities.insertItemIntoInventory(slotsOutput, quarriedStacks.get(k));
				if(remaining.isEmpty() || remaining.getCount() <= 0) {
					quarriedStacks.remove(k);
				}else{
					quarriedStacks.set(k, remaining);
				}
			}
		}	
	}
	
	public void setCoordinates(BlockPos starting, BlockPos ending) {
		startCorner = starting;
		currentQuarryingPosition = startCorner;
		endCorner = ending;
		isConfigured = true;
	}
    public boolean isAbleToMine(){
    	if(startCorner != null && currentQuarryingPosition != null && endCorner != null) {
        	if(startCorner.getY() == 0 && startCorner.getX() == 0 && startCorner.getZ() == 0) {
        		return false;
        	}	
    		return true;
    	}
    	return false;
    }
	public boolean isDoneMining() {
		if(currentQuarryingPosition != null) {
			if(currentQuarryingPosition.getY() == 0) {
				return true;
			}	
		}
		return false;
	}	
    private BlockPos getAdjustedEndingPos() {
		BlockPos temp1 = endCorner.subtract(startCorner);
		BlockPos absPos = new BlockPos(Math.abs(temp1.getX()), Math.abs(temp1.getY()), Math.abs(temp1.getZ()));
		return absPos;
	}
	private BlockPos getAdjustedCurrentPos() {
		BlockPos temp1 = currentQuarryingPosition.subtract(startCorner);
		BlockPos absPos = new BlockPos(Math.abs(temp1.getX()), Math.abs(temp1.getY()), Math.abs(temp1.getZ()));
		return absPos;
	}
	private int getXDirection(){
		if(startCorner.getX() > endCorner.getX()) {
			return -1;
		}else{
			return 1;
		}
	}
	private int getZDirection(){
		if(startCorner.getZ() > endCorner.getZ()) {
			return -1;
		}else{
			return 1;
		}
	}
	public List<ItemStack> getCurrentBlockDrops() {
		if(getWorld().isRemote) {
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, currentQuarryingPosition.getX() + 0.5D, currentQuarryingPosition.getY() + 1.0D, currentQuarryingPosition.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
		}
        NonNullList<ItemStack> ret = NonNullList.create();
		getWorld().getBlockState(currentQuarryingPosition).getBlock().getDrops(ret, getWorld(), currentQuarryingPosition, getWorld().getBlockState(currentQuarryingPosition), getFortuneMultiplier());
		return ret;
	}
	public BlockPos getCurrentPosition() {
		return currentQuarryingPosition;
	}
	public BlockPos getStartingPosition() {
		return startCorner;
	}
	public BlockPos getEndPosition() {
		return endCorner;
	}
	public int getBlocksMinedPerTick() {
		return blocksMinedPertick;
	}
	public FluidContainerComponent getFluidInteractionComponent() {
		return fluidInteractionComponent;
	}
	
	public int getFortuneMultiplier() {
		if(fluidTank.getFluid() != null) {
			if(fluidTank.getFluid().getFluid() == ModFluids.StaticFluid) {
				return 1;
			}
			if(fluidTank.getFluid().getFluid() == ModFluids.EnergizedFluid) {
				return 2;
			}
			if(fluidTank.getFluid().getFluid() == ModFluids.LumumFluid) {
				return 3;
			}
		}
		return 0;
	}
}
