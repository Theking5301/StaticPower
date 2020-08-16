package theking530.staticpower.tileentities.powered.chargingstation;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import theking530.staticpower.init.ModBlocks;
import theking530.staticpower.init.ModTileEntityTypes;
import theking530.staticpower.items.utilities.EnergyHandlerItemStackUtilities;
import theking530.staticpower.tileentities.TileEntityMachine;
import theking530.staticpower.tileentities.components.control.BatteryComponent;
import theking530.staticpower.tileentities.components.items.InputServoComponent;
import theking530.staticpower.tileentities.components.items.InventoryComponent;
import theking530.staticpower.tileentities.components.items.OutputServoComponent;
import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent;
import theking530.staticpower.tileentities.utilities.MachineSideMode;
import theking530.staticpower.utilities.InventoryUtilities;

public class TileEntityChargingStation extends TileEntityMachine {
	public final InventoryComponent unchargedInventory;
	public final InventoryComponent chargedInventory;
	public final InventoryComponent batterySlot;
	public final UpgradeInventoryComponent upgradesInventory;

	public TileEntityChargingStation() {
		super(ModTileEntityTypes.CHARGING_STATION);

		registerComponent(unchargedInventory = new InventoryComponent("unchargedInventory", 4, MachineSideMode.Input));
		registerComponent(chargedInventory = new InventoryComponent("chargedInventory", 4, MachineSideMode.Output));
		registerComponent(batterySlot = new InventoryComponent("batterySlot", 1, MachineSideMode.Never));
		registerComponent(upgradesInventory = new UpgradeInventoryComponent("UpgradeInventory", 3));

		registerComponent(new BatteryComponent("BatteryComponent", batterySlot, 0, energyStorage.getStorage()));
		registerComponent(new OutputServoComponent("OutputServo", 1, chargedInventory, 0, 1, 2, 3));
		registerComponent(new InputServoComponent("InputServo", 2, unchargedInventory, 0, 1, 2, 3));
		
		// Set the energy storage upgrade inventory.
		energyStorage.setUpgradeInventory(upgradesInventory);
	}

	@Override
	public void process() {
		if (!getWorld().isRemote) {
			// Capture the count of chargeable items.
			int count = 0;
			for (int i = 0; i < unchargedInventory.getSlots(); i++) {
				ItemStack stack = unchargedInventory.getStackInSlot(i);
				if (stack != ItemStack.EMPTY && EnergyHandlerItemStackUtilities.isEnergyContainer(stack)) {
					if (EnergyHandlerItemStackUtilities.getEnergyStored(stack) < EnergyHandlerItemStackUtilities.getEnergyStorageCapacity(stack)) {
						count++;
					}
				}
			}

			// Charge up to four items simultanously.
			if (energyStorage.getStorage().getEnergyStored() > 0) {
				for (int i = 0; i < unchargedInventory.getSlots(); i++) {
					ItemStack stack = unchargedInventory.getStackInSlot(i);
					if (stack != ItemStack.EMPTY && EnergyHandlerItemStackUtilities.isEnergyContainer(stack)) {
						if (EnergyHandlerItemStackUtilities.getEnergyStored(stack) < EnergyHandlerItemStackUtilities.getEnergyStorageCapacity(stack)) {
							int maxOutput = energyStorage.getStorage().getCurrentMaximumPowerOutput() / count;
							int charged = EnergyHandlerItemStackUtilities.addEnergyToItemstack(stack, maxOutput, false);
							energyStorage.usePower(charged);
						} else {
							moveChargedItemToOutputs(i);
						}
					}
				}
			}
		}
	}

	/**
	 * Attempt to move the charged {@link ItemStack} from the fromSlot of the
	 * unchargedInventory into any open slot in the chargedInventory.
	 * 
	 * @param fromSlot The slot that contains the charged {@link ItemStack}.
	 */
	public void moveChargedItemToOutputs(int fromSlot) {
		// Iterate through each of the output slots.
		for (int i = 0; i < 4; i++) {
			// If we can place the charged item into an output slot, do so. There's no need
			// to check the result of the call to insertItem as we already know the result
			// is going to be empty.
			if (InventoryUtilities.canFullyInsertStackIntoSlot(chargedInventory, i, unchargedInventory.getStackInSlot(fromSlot))) {
				System.out.println(unchargedInventory.getStackInSlot(fromSlot));
				ItemStack stack = unchargedInventory.extractItem(fromSlot, 1, false);
				chargedInventory.insertItem(i, stack, false);
			}
		}
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
		return new ContainerChargingStation(windowId, inventory, this);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.ChargingStation.getTranslationKey());
	}
}
