package theking530.staticpower.tileentity.digistorenetwork.manager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerDigistoreManager extends BaseContainer {
	
	private TileEntityDigistoreManager barrel;

	public ContainerDigistoreManager(InventoryPlayer invPlayer, TileEntityDigistoreManager teBarrel) {
		barrel = teBarrel;

		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 0, 152, 9));
		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 1, 152, 27));
		addSlotToContainer(new UpgradeSlot(barrel.slotsUpgrades, 2, 152, 45));
		
		addPlayerHotbar(invPlayer, 8, 126);
		addPlayerInventory(invPlayer, 8, 68);

	}

	//Shift Click Functionality
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())   {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(slotIndex >= 3) {
                if (barrel.canAcceptUpgrade(itemstack1) && !mergeItemStack(itemstack1, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }else{
                if (!mergeItemStack(itemstack1, 3,inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }
            slot.onSlotChanged();
        }

        return itemstack;
    }
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return barrel.isUseableByPlayer(player);
	}	
}
	

