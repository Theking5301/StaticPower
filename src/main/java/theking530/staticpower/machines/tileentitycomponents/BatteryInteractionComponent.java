package theking530.staticpower.machines.tileentitycomponents;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.energy.StaticEnergyStorage;

public class BatteryInteractionComponent implements ITileEntityComponent{

	private String NAME;
	private boolean isEnabled;
	
	private ItemStackHandler BATTERY_SLOT_HANDLER;
	private int BATTERY_SLOT;
	private StaticEnergyStorage ENERGY_STORAGE;
	public BatteryInteractionComponent(String componentName, ItemStackHandler batterySlotHandler, int batterySlot, StaticEnergyStorage energyHandler) {	
		NAME = componentName;
		BATTERY_SLOT_HANDLER = batterySlotHandler;
		BATTERY_SLOT = batterySlot;
		ENERGY_STORAGE = energyHandler;
	}

	@Override
	public void preProcessUpdate() {
		if(BATTERY_SLOT < BATTERY_SLOT_HANDLER.getSlots()) {
			if(ENERGY_STORAGE.getEnergyStored() < ENERGY_STORAGE.getMaxEnergyStored()) {
				if(BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT) != null && BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT).getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem batteryItem = (IEnergyContainerItem) BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT).getItem();
					if(batteryItem.getEnergyStored(BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT)) > 0) {
						int recieved = ENERGY_STORAGE.receiveEnergy(Math.min(batteryItem.getEnergyStored(BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT)), ENERGY_STORAGE.getMaxReceive()), false);
						batteryItem.extractEnergy(BATTERY_SLOT_HANDLER.getStackInSlot(BATTERY_SLOT), recieved, false);					
					}
				}
			}
		}
	}

	@Override
	public String getComponentName() {
		return NAME;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public void postProcessUpdate() {

	}
}
