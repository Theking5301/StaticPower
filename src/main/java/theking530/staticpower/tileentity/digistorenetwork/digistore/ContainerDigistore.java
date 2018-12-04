package theking530.staticpower.tileentity.digistorenetwork.digistore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerDigistore extends BaseContainer {
	
	private TileEntityDigistore barrel;

	public ContainerDigistore(InventoryPlayer invPlayer, TileEntityDigistore teBarrel) {
		barrel = teBarrel;

		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 0, 152, 9));
		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 1, 152, 27));
		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 2, 152, 45));
		
		addPlayerHotbar(invPlayer, 8, 126);
		addPlayerInventory(invPlayer, 8, 68);

	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 0, 3, false)) {
        	return true;
        }
		return false;	
	}
}
	

