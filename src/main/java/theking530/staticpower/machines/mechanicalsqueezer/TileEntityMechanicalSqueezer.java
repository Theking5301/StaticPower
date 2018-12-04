package theking530.staticpower.machines.mechanicalsqueezer;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.fluids.StaticTankStorage;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.IProcessing;

public class TileEntityMechanicalSqueezer extends TileEntityBase implements IFluidHandler, IProcessing {

	public StaticTankStorage fluidTank;
	public int processingTimer = 0;
	public int processingTime = 20;
	public int moveTimer = 0;
	public int moveTime = 4;
	public int fluidToContainerRate = 10; 
	
	public TileEntityMechanicalSqueezer() {
		initializeSlots(3, 1, 1);
		fluidTank = new StaticTankStorage("MainFluidTank", 1000);
		this.registerComponent(new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank, fluidToContainerRate));
		
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		setName("container.MechanicalSqueezer");
	}
	
	
	public void readFromSyncNBT(NBTTagCompound nbt) {
        fluidTank.readFromNBT(nbt);
		processingTimer = nbt.getInteger("P_TIMER");
	}
	public NBTTagCompound writeToSyncNBT(NBTTagCompound nbt) {
		fluidTank.writeToNBT(nbt);
		nbt.setInteger("P_TIMER", processingTimer);
		return nbt;
	}
	
	@Override  
    public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        fluidTank.readFromNBT(nbt);
		processingTimer = nbt.getInteger("P_TIMER");
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
		fluidTank.writeToNBT(nbt);
		nbt.setInteger("P_TIMER", processingTimer);
    	return nbt;
	}
	
    /*Process*/
    @Override
	public boolean hasValidRecipe() {
		return SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(slotsInput.getStackInSlot(0)) != null;
	}
	public boolean canProcess() {
		if(hasValidRecipe()) {
			SqueezerOutputWrapper recipe = SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(slotsInput.getStackInSlot(0));
			if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
				if(fluidTank.canFill(recipe.getOutputFluid())) {
					return true;
				}
			}
		}
		return false;
	}
	public int getProgressScaled(int i) {
		float ratio = (float)processingTimer/(float)processingTime;
		return (int) (ratio * i);
	}
	public void rightClick() {
		if(!getWorld().isRemote) {
			//Start Process
			if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer = 1;
			}
			//Start Moving
			if(canProcess() && !isProcessing() && isMoving()) {
				moveTimer++;
				if(moveTimer >= moveTime) {
					moveTimer = 0;
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
					processingTimer = 1;	
				}
			}else{
				moveTimer = 0;
			}
			//Start Processing
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					processingTimer++;
					updateBlock();
				}else{			
					SqueezerOutputWrapper recipe = SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(slotsInternal.getStackInSlot(0));
					if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
						fluidTank.fill(recipe.getOutputFluid(), true);
						slotsOutput.insertItem(0, recipe.getOutputItem().copy(), false);
						slotsInternal.setStackInSlot(0, ItemStack.EMPTY);
						processingTimer = 0;
						updateBlock();
					}
				}
			}	
		}
	}
	
	/*Fluid Handling*/
	public float getFluidLevelScaled(int height) {
		int capacity = fluidTank.getCapacity();
		int volume = fluidTank.getFluidAmount();
		if(capacity != 0) {
			float percentage = (float)volume/(float)capacity;
			return percentage * height;
		}else{
			return 0;
		}
	}
    @SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return (T) this;
    	}
    	return super.getCapability(capability, facing);
    }
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(capability==CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return fluidTank.getTankProperties();
	}
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return fluidTank.fill(resource, doFill);
	}
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return fluidTank.drain(resource, doDrain);
	}
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return fluidTank.drain(maxDrain, doDrain);
	}
	public boolean isProcessing(){
		return processingTimer > 0;
	}
	public boolean isMoving(){
		return moveTimer > 0;
	}
	@Override
	public int getProcessingTime() {
		return processingTime;
	}
	@Override
	public int getCurrentProgress() {
		return processingTimer;
	}
	@Override
	public float getProcessingPercentage() {
		return (float)processingTimer/(float)processingTime;
	}
}
	
