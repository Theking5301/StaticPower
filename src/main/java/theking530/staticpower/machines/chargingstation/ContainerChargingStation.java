package theking530.staticpower.machines.chargingstation;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerChargingStation extends BaseContainer {

	public ContainerChargingStation(InventoryPlayer invPlayer, TileEntityChargingStation teChargingStation) {	
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(teChargingStation.slotsInput, 0, 51, 24) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return itemStack.getItem() instanceof IEnergyContainerItem;
		    }
		});	
		this.addSlotToContainer(new StaticPowerContainerSlot(teChargingStation.slotsInput, 1, 70, 24) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return itemStack.getItem() instanceof IEnergyContainerItem;
		    }
		});	
		this.addSlotToContainer(new StaticPowerContainerSlot(teChargingStation.slotsInput, 2, 89, 24) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return itemStack.getItem() instanceof IEnergyContainerItem;
		    }
		});	
		this.addSlotToContainer(new StaticPowerContainerSlot(teChargingStation.slotsInput, 3, 108, 24) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return itemStack.getItem() instanceof IEnergyContainerItem;
		    }
		});	
		
		//Output
		this.addSlotToContainer(new OutputSlot(teChargingStation.slotsOutput, 0, 46, 52));
		this.addSlotToContainer(new OutputSlot(teChargingStation.slotsOutput, 1, 69, 52));
		this.addSlotToContainer(new OutputSlot(teChargingStation.slotsOutput, 2, 92, 52));
		this.addSlotToContainer(new OutputSlot(teChargingStation.slotsOutput, 3, 115, 52));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teChargingStation.slotsUpgrades, 0, 152, 8));
		this.addSlotToContainer(new UpgradeSlot(teChargingStation.slotsUpgrades, 1, 152, 30));
		this.addSlotToContainer(new UpgradeSlot(teChargingStation.slotsUpgrades, 2, 152, 52));
		
		//Battery
		this.addSlotToContainer(new BatterySlot(teChargingStation.slotsInternal, 0, 8, 54));
		
		//Armor
		this.addSlotToContainer(new Slot(invPlayer, 39, -24, 14) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem().isValidArmor(itemStack, EntityEquipmentSlot.HEAD, invPlayer.player);
		    }
		});	
		this.addSlotToContainer(new Slot(invPlayer, 38, -24, 33) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem().isValidArmor(itemStack, EntityEquipmentSlot.CHEST, invPlayer.player);
		    }
		});	
		this.addSlotToContainer(new Slot(invPlayer, 37, -24, 52) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem().isValidArmor(itemStack, EntityEquipmentSlot.LEGS, invPlayer.player);
		    }
		});	
		this.addSlotToContainer(new Slot(invPlayer, 36, -24, 71) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
	            return itemStack.getItem().isValidArmor(itemStack, EntityEquipmentSlot.FEET, invPlayer.player);
		    }
		});	

		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 0, 4, false)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 8, 11, false)) {
        	return true;
        }
		return false;	
	}
}

