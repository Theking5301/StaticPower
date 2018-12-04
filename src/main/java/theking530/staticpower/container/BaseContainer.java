package theking530.staticpower.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public abstract class BaseContainer extends Container{

	protected int playerInventoryStart;
	protected int playerHotbarStart;
	protected int playerInventoryEnd;
	protected int playerHotbarEnd;
	
	protected void addPlayerInventory(InventoryPlayer invPlayer, int xPosition, int yPosition) {
		playerInventoryStart = this.inventorySlots.size();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, xPosition + j * 18, yPosition + i * 18));
			}
		}
		playerInventoryEnd = this.inventorySlots.size()-1;
	}
	protected void addPlayerHotbar(InventoryPlayer invPlayer, int xPosition, int yPosition) {
		playerHotbarStart = this.inventorySlots.size();
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, xPosition + i * 18, yPosition));
		}
		playerHotbarEnd = this.inventorySlots.size()-1;
	}
	
	/*
	 * Returns false if no conditions were met, otherwise returns true. If false, container handles moving the item between the inventory and the hotbar.
	 */
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
		return false;	
	}
	protected boolean containerSlotShiftClicked(ItemStack stack, EntityPlayer player, StaticPowerContainerSlot slot, int slotIndex) {
        if (mergeItemStack(stack, playerHotbarStart, playerHotbarEnd+1, false)) {
            return true;
        }else if (!mergeItemStack(stack, playerInventoryStart, playerInventoryEnd+1, false)) {
            return true;
        }
		return false;	
	}
    protected boolean isInventorySlot(int slot) {
    	return slot >= playerInventoryStart && slot <= playerInventoryEnd;
    }
    protected boolean isHotbarSlot(int slot) {
    	return slot >= playerHotbarStart && slot <= playerHotbarEnd;
    }
    protected boolean mergeItemStack(ItemStack stack, int index) {
    	return mergeItemStack(stack, index, index+1, false);
    }
	public ItemStack transferStackInSlot(EntityPlayer player, int invSlot) {
	    ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(invSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
        	if(slot instanceof StaticPowerContainerSlot) {
        		containerSlotShiftClicked(itemstack1, player, (StaticPowerContainerSlot)this.inventorySlots.get(invSlot), invSlot);
	            slot.onSlotChange(itemstack1, itemstack);
        	}else{
        		if(!playerItemShiftClicked(itemstack1, player, (InventoryPlayer)slot.inventory, slot, invSlot)) {
        	        if (isInventorySlot(invSlot) && !mergeItemStack(itemstack1, playerHotbarStart, playerHotbarEnd+1, false)) {
        	        	return ItemStack.EMPTY;
        	        }
        	        if (isHotbarSlot(invSlot) && !mergeItemStack(itemstack1, playerInventoryStart, playerInventoryEnd+1, false)) {
        	        	return ItemStack.EMPTY;
        	        }
        		}
	            slot.onSlotChange(itemstack1, itemstack);
        	}
	        if (itemstack1.getCount() == 0){
	            slot.putStack(ItemStack.EMPTY);
	        }else {
	            slot.onSlotChanged();
	        }
	        if (itemstack1.getCount() == itemstack.getCount()){
	            return ItemStack.EMPTY;
	        }
	        slot.onTake(player, itemstack1);
        }
        return itemstack;
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
