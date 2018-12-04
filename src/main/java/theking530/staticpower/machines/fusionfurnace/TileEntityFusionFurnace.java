package theking530.staticpower.machines.fusionfurnace;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.handlers.crafting.registries.FusionRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FusionFurnaceRecipeWrapper;
import theking530.staticpower.machines.TileEntityMachine;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityFusionFurnace extends TileEntityMachine {
	
	public TileEntityFusionFurnace() {
		initializeSlots(5, 5, 1);
		initializeBasicMachine(2, 1000, 100000, 80, 100);
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1, 2, 3, 4));
		setName("container.FusionFurnace");
	}	

	//Process 
	@Override
	public boolean hasValidRecipe() {
		return FusionRecipeRegistry.Fusing().getFusionResult(getInputItems()) != null;
	}
	@Override
	public boolean canProcess() {
		if(hasValidRecipe()) {
			FusionFurnaceRecipeWrapper recipe = FusionRecipeRegistry.Fusing().getFusionResult(getInputItems());
			if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
				if(energyStorage.getEnergyStored() >= getProcessingEnergy()) {
					return true;
				}
			}
		}
		return false;
	}
	private ItemStack[] getInputItems() {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for(int i=0; i<slotsInput.getSlots(); i++) {
			if(!slotsInput.getStackInSlot(i).isEmpty()) {
				stacks.add(slotsInput.getStackInSlot(i));
			}
		}
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	public void process() {
		if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer = 1;
		}
		if(canProcess() && !isProcessing() && isMoving()) {
			if(moveTimer < moveSpeed) {
				moveTimer++;
			}else{
				if(getInputStack(0) != ItemStack.EMPTY) {
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
				}
				if(getInputStack(1)!= ItemStack.EMPTY) {
					transferItemInternally(slotsInput, 1, slotsInternal, 1);
				}
				if(getInputStack(2) != ItemStack.EMPTY) {
					transferItemInternally(slotsInput, 2, slotsInternal, 2);
				}
				if(getInputStack(3) != ItemStack.EMPTY) {
					transferItemInternally(slotsInput, 3, slotsInternal, 3);
				}
				if(getInputStack(4) != ItemStack.EMPTY) {
					transferItemInternally(slotsInput, 4, slotsInternal, 4);
				}
				moveTimer=0;
				processingTimer = 1;
			}
		}else{
			moveTimer = 0;
		}
		if(isProcessing() && !isMoving()) {
			if(processingTimer <= processingTime) {
				useEnergy(getProcessingEnergy() / processingTime);
				processingTimer++;
			}else{
				FusionFurnaceRecipeWrapper recipe = FusionRecipeRegistry.Fusing().getFusionResult(getInternalStack(0), getInternalStack(1), getInternalStack(2), getInternalStack(3), getInternalStack(4));
				if(recipe != null && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItem())) {
					slotsOutput.insertItem(0, recipe.getOutputItem().copy(), false);
					setInternalStack(0, ItemStack.EMPTY);
					setInternalStack(1, ItemStack.EMPTY);
					setInternalStack(2, ItemStack.EMPTY);
					setInternalStack(3, ItemStack.EMPTY);
					setInternalStack(4, ItemStack.EMPTY);
					processingTimer=0;
				}
			}
		}
	}
 
}


	
