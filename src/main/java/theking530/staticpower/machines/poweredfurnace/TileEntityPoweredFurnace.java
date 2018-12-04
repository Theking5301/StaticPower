package theking530.staticpower.machines.poweredfurnace;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.machines.TileEntityMachine;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityPoweredFurnace extends TileEntityMachine {

	public TileEntityPoweredFurnace() {
		initializeSlots(2, 1, 1);
		initializeBasicMachine(2, 1000, 100000, 80, 180);
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 1, energyStorage));
		setName("container.PoweredFurnace");
	}
	
	//Functionality
	@Override
	public boolean hasValidRecipe() {
		return !FurnaceRecipes.instance().getSmeltingResult(slotsInput.getStackInSlot(0)).isEmpty();
	}
	@Override
	public boolean canProcess() {
		return hasValidRecipe() && getEnergyStorage().getEnergyStored() > getProcessingEnergy();
	}
	public void process() {
		if(!getWorld().isRemote) {
			if(!isProcessing() && !isMoving() && canProcess()) {
				moveTimer = 1;
			}
			if(!isProcessing() && isMoving() && canProcess()) {
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
					useEnergy(getProcessingEnergy()/processingTime);
					processingTimer++;
					updateBlock();
				}else{
					ItemStack output = FurnaceRecipes.instance().getSmeltingResult(slotsInternal.getStackInSlot(0));
					if(!output.isEmpty() && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, output)) {
						slotsOutput.insertItem(0, output.copy(), false);
						setInternalStack(0, ItemStack.EMPTY);
						processingTimer=0;
						updateBlock();
					}
				}
			}
		}	
	}
}


	
