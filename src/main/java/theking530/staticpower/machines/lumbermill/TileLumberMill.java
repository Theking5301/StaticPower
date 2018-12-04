package theking530.staticpower.machines.lumbermill;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.handlers.crafting.registries.LumberMillRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.LumberMillRecipeWrapper;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;

public class TileLumberMill extends TileEntityMachineWithTank {

	public TileLumberMill() {
		initializeSlots(4, 1, 2);
		initializeBasicMachine(2, 1000, 100000, 80, 150);
		initializeTank(5000);
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 1, energyStorage));
		
		FluidContainerComponent drainComponent;
		registerComponent(drainComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 2, slotsInternal, 3, this, fluidTank));
		drainComponent.setMode(FluidContainerInteractionMode.DRAIN);
		setName("container.LumberMill");
		setCanFillExternally(false);
	}	
	
	//Functionality
	@Override
	public boolean hasValidRecipe() {
		return LumberMillRecipeRegistry.Milling().getMillingRecipe(slotsInput.getStackInSlot(0)) != null;
	}
	@Override
	public boolean canProcess() {
		if(hasValidRecipe()) {
			LumberMillRecipeWrapper recipe = LumberMillRecipeRegistry.Milling().getMillingRecipe(slotsInput.getStackInSlot(0));
			if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getMainOutput()) && energyStorage.getEnergyStored() >= getProcessingEnergy()) {
				if(!recipe.hasOutputFluid() || (recipe.hasOutputFluid() && fluidTank.canFill(recipe.getOutputFluid()))) {
					if(recipe.getSecondaryOutput().isEmpty() || (!recipe.getSecondaryOutput().isEmpty() && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 1, recipe.getSecondaryOutput()))) {
						return true;
					}	
				}
			}
		}
		return false;
	}
	public void process() {
		if(!getWorld().isRemote) {
			if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer++;
			}
			if(canProcess() && !isProcessing() && isMoving()) {
				if(moveTimer < moveSpeed) {
					moveTimer++;
				}else{
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
					processingTimer = 1;
					moveTimer = 0;
					updateBlock();
				}
			}else{
				moveTimer = 0;
			}
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					useEnergy(getProcessingEnergy() / processingTime);
					processingTimer++;
				}else{
					processingTimer=0;
					updateBlock();
					LumberMillRecipeWrapper recipe = LumberMillRecipeRegistry.Milling().getMillingRecipe(getInternalStack(0));
					if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getMainOutput()) && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 1, recipe.getSecondaryOutput())) {
						slotsOutput.insertItem(0, recipe.getMainOutput().copy(), false);
						slotsOutput.insertItem(1, recipe.getSecondaryOutput().copy(), false);
						if(recipe.hasOutputFluid()) {
							fluidTank.fill(recipe.getOutputFluid(), true);
						}
						setInternalStack(0, ItemStack.EMPTY);
						moveTimer = 0;
					}
				}
			}
		}	
	}
}


	
