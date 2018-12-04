package theking530.staticpower.machines.poweredgrinder;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.TileEntityUtilities;
import theking530.staticpower.handlers.crafting.registries.GrinderRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.GrinderOutputWrapper;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.upgrades.BaseOutputMultiplierUpgrade;
import theking530.staticpower.machines.TileEntityMachine;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityPoweredGrinder extends TileEntityMachine {
	
	private float bonusOutputChance;
	
	public TileEntityPoweredGrinder() {
		initializeSlots(2, 1, 3);
		initializeBasicMachine(2, 1000, 100000, 80, 180);
		registerComponent(new BatteryInteractionComponent("BatteryComponent", slotsInternal, 1, energyStorage));
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1, 2));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0));
		bonusOutputChance = 0.0f;
		setName("container.PoweredGrinder");
	}

	@Override
	public boolean hasValidRecipe() {
		return GrinderRecipeRegistry.Grinding().getGrindingRecipe(slotsInput.getStackInSlot(0)) != null;
	}
	@Override
	public boolean canProcess() {
		if(hasValidRecipe()) {
			GrinderOutputWrapper recipe = GrinderRecipeRegistry.Grinding().getGrindingRecipe(slotsInput.getStackInSlot(0));
			if(recipe.getOutputItemCount() > 0) {
				boolean itemOutputValidFlag = true;
				boolean slot1 = false;
				boolean slot2 = false;
				boolean slot3 = false;
				for(int i=0; i<recipe.getOutputItemCount(); i++) {
					if(recipe.getOutputItems().get(i).isValid()) {
						if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItems().get(i).getOutput()) && slot1 == false) {
							slot1 = true;
						}else if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 1, recipe.getOutputItems().get(i).getOutput()) && slot2 == false) {
							slot2 = true;
						}else if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 2, recipe.getOutputItems().get(i).getOutput()) && slot3 == false) {
							slot3 = true;
						}else{
							itemOutputValidFlag = false;
						}
					}
				}
				if(energyStorage.getEnergyStored() >= getProcessingEnergy() && itemOutputValidFlag == true) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Process 
	public void process() {
		if(!getWorld().isRemote) {
			if(canProcess() && !isProcessing() && !isMoving()) {
				moveTimer = 1;
			}
			if(canProcess() && !isProcessing() && isMoving() ) {
				if(moveTimer < moveSpeed) {
					moveTimer++;
				}else{
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
					moveTimer=0;
					processingTimer = 1;
					updateBlock();
				}
			}
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					useEnergy(getProcessingEnergy() / processingTime);
					processingTimer++;
				}else{
					GrinderOutputWrapper recipe = GrinderRecipeRegistry.Grinding().getGrindingRecipe(slotsInternal.getStackInSlot(0));
					if(recipe == null) {
						processingTimer = 0;
						return;
					}
					for(int j=0; j<recipe.getOutputItemCount(); j++) {
						ItemStack result = recipe.getOutputItems().get(j).getOutput();
						if(TileEntityUtilities.diceRoll(recipe.getOutputItems().get(j).getPercentage()+bonusOutputChance)) {
							boolean flag = false;
							int slot = -1;
							for(int i=0; i<3; i++) {
								if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, i, recipe.getOutputItems().get(j).getOutput())) {
									slot = i;
									flag = true;
									break;
								}	
							}
							if(!flag) {
								for(int i=0; i<3; i++) {
									if(InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, i, result)) {
										slot = i;
										break;
									}	
								}
							}
							if(slot != -1) {
								slotsOutput.insertItem(slot, result.copy(), false);
							}
						}						
					}
					setInternalStack(0, ItemStack.EMPTY);
					updateBlock();
					processingTimer=0;
					moveTimer = 0;
				}	
			}
		}
	}
	@Override
	public void upgradeTick(){
		super.upgradeTick();
		outputUpgradeHandler();
	}
	private void outputUpgradeHandler() {
		if(hasUpgrade(ModItems.BasicOutputMultiplierUpgrade)) {
			BaseOutputMultiplierUpgrade tempUpgrade = (BaseOutputMultiplierUpgrade) getUpgrade(ModItems.BasicOutputMultiplierUpgrade).getItem();
			bonusOutputChance = tempUpgrade.getUpgradeValueAtIndex(getUpgrade(ModItems.BasicOutputMultiplierUpgrade), 0);
		}else{
			bonusOutputChance = 0.0f;
		}
		processingEnergyMult = (processingEnergyMult * (1+bonusOutputChance/2.0f));
	}
	
	public float getBonusOutputChance() {
		return Math.min(1.0f, bonusOutputChance);
	}
}