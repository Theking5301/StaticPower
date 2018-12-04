package theking530.staticpower.machines.former;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;
import theking530.staticpower.handlers.crafting.registries.FormerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FormerRecipeWrapper;
import theking530.staticpower.machines.TileEntityMachine;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.tileentity.SideConfiguration;

public class TileEntityFormer extends TileEntityMachine {
	
	public TileEntityFormer() {
		initializeSlots(2, 2, 1);
		initializeBasicMachine(2, 1000, 100000, 80, 100);
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 1, energyStorage));
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, Mode.Input, 0, 1));
		setName("container.Former");
	}
	
	@Override
   	public boolean hasValidRecipe() {
		return FormerRecipeRegistry.Forming().getFormingResult(slotsInput.getStackInSlot(0), slotsInput.getStackInSlot(1)) != null;
	}
	@Override
	public boolean canProcess() {
		if(hasValidRecipe()) {
			FormerRecipeWrapper recipe = FormerRecipeRegistry.Forming().getFormingResult(slotsInput.getStackInSlot(0), slotsInput.getStackInSlot(1));
			if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItemStack()) && energyStorage.getEnergyStored() >= getProcessingEnergy()) {
				return true;
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
					FormerRecipeWrapper recipe = FormerRecipeRegistry.Forming().getFormingResult(slotsInternal.getStackInSlot(0), slotsInternal.getStackInSlot(1));
					if(recipe != null && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItemStack())) {
						slotsOutput.insertItem(0, recipe.getOutputItemStack().copy(), false);
						setInternalStack(0, ItemStack.EMPTY);
					}
				}
			}
		}	
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Input2);
		modes.add(Mode.Output);
		modes.add(Mode.Regular);
		modes.add(Mode.Disabled);
		return modes;
	}
	public void setDefaultSideConfiguration(SideConfiguration configuration) {
		configuration.setToDefault();
		configuration.setSideConfiguration(Mode.Input2, SideUtilities.getEnumFacingFromSide(BlockSide.BACK, getFacingDirection()));
	}
}