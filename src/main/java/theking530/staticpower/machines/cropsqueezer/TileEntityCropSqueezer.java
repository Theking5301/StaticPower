package theking530.staticpower.machines.cropsqueezer;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;

public class TileEntityCropSqueezer extends TileEntityMachineWithTank {

	public TileEntityCropSqueezer() {
		initializeBasicMachine(2, 100, 100000, 80, 50);
		initializeTank(5000);
		initializeSlots(4, 1, 1);
		
		registerComponent(new FluidContainerComponent("BucketDrain", slotsInternal, 1, slotsInternal, 2, this, fluidTank));
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 3, this.getEnergyStorage()));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		
		setName("container.CropSqueezer");
		setCanFillExternally(false);
	}
	
    //Process
	@Override
	public boolean hasValidRecipe() {
		return SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(slotsInput.getStackInSlot(0)) != null;
	}
	@Override
	public boolean canProcess() {
		SqueezerOutputWrapper recipe = SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(slotsInput.getStackInSlot(0));
		if(hasValidRecipe() && recipe.getOutputFluid() != null && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
			if(fluidTank.canFill(recipe.getOutputFluid())) {
				return getEnergyStorage().getEnergyStored() > getProcessingEnergy();
			}
		}
		return false;
	}
	public void process() {
		if(!getWorld().isRemote) {
			if(!isProcessing() && !isMoving() && canProcess()) {
				moveTimer = 1;
			}
			if(!isProcessing() && isMoving() && canProcess()) {
				moveTimer++;
				if(moveTimer >= moveSpeed) {
					moveTimer = 0;
					useEnergy(getProcessingEnergy());
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
					if(recipe != null && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
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
}


	
