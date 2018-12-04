package theking530.staticpower.tileentity.vacuumchest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.itemfilter.ItemFilter;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.FilterSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerVacuumChest extends BaseContainer {
	
	private TileEntityVacuumChest vacuumChest;

	public ContainerVacuumChest(InventoryPlayer invPlayer, TileEntityVacuumChest teVChest) {
		vacuumChest = teVChest;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new StaticPowerContainerSlot(vacuumChest.slotsOutput, x + y * 9, 8 + x * 18, 20 + y * 18));
			}
		}
		this.addSlotToContainer(new FilterSlot(vacuumChest.slotsInternal, 0, 8, 78));
		
		this.addSlotToContainer(new UpgradeSlot(vacuumChest.slotsUpgrades, 0, 116, 78));
		this.addSlotToContainer(new UpgradeSlot(vacuumChest.slotsUpgrades, 1, 134, 78));
		this.addSlotToContainer(new UpgradeSlot(vacuumChest.slotsUpgrades, 2, 152, 78));
		

		this.addPlayerInventory(invPlayer, 8, 103);
		this.addPlayerHotbar(invPlayer, 8, 161);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof ItemFilter && !mergeItemStack(stack, 27)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 28, 31, false)) {
        	return true;
        }
        if (!mergeItemStack(stack, 0, 27, false)) {
        	return true;
        }
		return false;	
	}
}
	

