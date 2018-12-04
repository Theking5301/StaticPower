package theking530.staticpower.machines.fluidinfuser;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.handlers.crafting.registries.InfuserRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FluidInfuserOutputWrapper;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityFluidInfuser extends TileEntityMachineWithTank {
	
	public FluidContainerComponent fluidContainerInteractionComponent;
	public FluidStack lastContainedFluid;
	
	public TileEntityFluidInfuser() {
		initializeBasicMachine(2, 1000, 100000, 80, 100);
		initializeTank(10000);
		initializeSlots(4, 1, 1);
		
		registerComponent(new BatteryInteractionComponent("BatteryInteraction", slotsInput, 3, energyStorage));
		registerComponent(fluidContainerInteractionComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank));
		fluidContainerInteractionComponent.setMode(FluidContainerInteractionMode.FILL);
		
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		setName("container.FluidInfuser");
	}
	public FluidContainerComponent getFluidInteractionComponent() {
		return fluidContainerInteractionComponent;
	}

	//Functionality
	@Override
 	public boolean hasValidRecipe() {	
		return InfuserRecipeRegistry.Infusing().getInfusingRecipe(slotsInput.getStackInSlot(0), null, true) != null;
	}
	@Override
	public boolean canProcess() {
		if(InfuserRecipeRegistry.Infusing().getInfusingRecipe(slotsInput.getStackInSlot(0), fluidTank.getFluid(), false) != null) {
			FluidInfuserOutputWrapper recipe = InfuserRecipeRegistry.Infusing().getInfusingRecipe(slotsInput.getStackInSlot(0), fluidTank.getFluid(), false);
			if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItemStack())) {
				return getEnergyStorage().getEnergyStored() >= getProcessingEnergy();
			}
		}
		return false;
	}
	@Override  
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        lastContainedFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("LAST_FLUID"));
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if(lastContainedFluid != null) {
            NBTTagCompound last =  new NBTTagCompound();
            lastContainedFluid.writeToNBT(last);	
            nbt.setTag("LAST_FLUID", last);
        }
    	return nbt;
	}

	@Override
	public void process() {
		if(!getWorld().isRemote) {
			if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer = 1;
			}
			if(canProcess() && !isProcessing() && isMoving()) {
				moveTimer++;
				if(moveTimer >= moveSpeed) {
					moveTimer = 0;
					FluidInfuserOutputWrapper recipe = InfuserRecipeRegistry.Infusing().getInfusingRecipe(slotsInput.getStackInSlot(0), fluidTank.getFluid(), false);
					lastContainedFluid = fluidTank.drain(recipe.getRequiredFluidStack(), true);
					useEnergy(getProcessingEnergy());
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
					processingTimer = 1;
					
				}
			}else{
				moveTimer = 0;
			}
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					processingTimer++;
				}else{
					FluidInfuserOutputWrapper recipe = InfuserRecipeRegistry.Infusing().getInfusingRecipe(slotsInternal.getStackInSlot(0), null, true);
					if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItemStack())) {
						slotsOutput.insertItem(0, recipe.getOutputItemStack(), false);
						slotsInternal.setStackInSlot(0, ItemStack.EMPTY);
						processingTimer = 0;
					}
				}
				updateBlock();
			}		
		}
	}
}


	
