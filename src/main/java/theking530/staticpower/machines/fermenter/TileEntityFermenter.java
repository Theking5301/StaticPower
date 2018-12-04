package theking530.staticpower.machines.fermenter;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FermenterOutputWrapper;
import theking530.staticpower.items.MiscItems;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityFermenter extends TileEntityMachineWithTank {

	public FluidContainerComponent fluidContainerInteractionComponent;
	
	public TileEntityFermenter() {
		initializeBasicMachine(4, 500, 100000, 160, 45);
		initializeTank(5000);
		initializeSlots(4, 9, 1);
		
		fluidContainerInteractionComponent = new FluidContainerComponent("BucketDrain", slotsInternal, 2, slotsInternal, 3, this, fluidTank);
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 1, energyStorage));

		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1, 2, 3, 4, 5, 6, 7, 8));
		setName("container.Fermenter");
		setCanFillExternally(false);
	}	
	@Override
	public boolean hasValidRecipe() {
		for(int i=0; i<9; i++) {
			FermenterOutputWrapper recipe = FermenterRecipeRegistry.Fermenting().getRecipe(slotsInput.getStackInSlot(i));
			if(recipe != null) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean canProcess() {
		for(int i=0; i<9; i++) {
			FermenterOutputWrapper recipe = FermenterRecipeRegistry.Fermenting().getRecipe(slotsInput.getStackInSlot(i));
			if(recipe != null) {
				FluidStack fermentingResult = recipe.getOutputFluidStack();
				if(fluidTank.canFill(fermentingResult)) {
					if(getEnergyStorage().getEnergyStored() >= getProcessingEnergy()) {
						return slotsOutput.insertItem(0, MiscItems.distilleryGrain, true).isEmpty();
					}
				}
			}
		}
		return false;
	}
	public void process() {
		if(!getWorld().isRemote) {
			if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer = 1;
			}
			if(canProcess() && !isProcessing() && isMoving()) {
				if(moveTimer < moveSpeed) {
					moveTimer++;
				}else{
					for(int i=0; i<9; i++) {
						FermenterOutputWrapper recipe = FermenterRecipeRegistry.Fermenting().getRecipe(slotsInput.getStackInSlot(i));
						if(recipe != null) {
							transferItemInternally(slotsInput, i, slotsInternal, 0);
							processingTimer = 1;
							moveTimer = 0;
							break;
						}
					}	
				}
			}else{
				moveTimer = 0;
			}
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					processingTimer++;
					energyStorage.extractEnergy(getProcessingEnergy()/processingTime, false);
					updateBlock();
				}else{
					FermenterOutputWrapper recipe = FermenterRecipeRegistry.Fermenting().getRecipe(slotsInternal.getStackInSlot(0));
					fluidTank.fill(recipe.getOutputFluidStack(), true);
					slotsInternal.extractItem(0, slotsInternal.getStackInSlot(0).getCount(), false);
					slotsOutput.insertItem(0, MiscItems.distilleryGrain.copy(), false);
					processingTimer = 0;
					updateBlock();
				}
			}
		}
	}
}