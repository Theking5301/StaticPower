package theking530.staticpower.items.itemfilter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import theking530.staticpower.client.gui.widgets.SlotPhantom;
import theking530.staticpower.container.BaseContainer;

public class ContainerItemFilter extends BaseContainer {
	
	private InventoryItemFilter filterInventory;
	
	public ContainerItemFilter(InventoryPlayer invPlayer, InventoryItemFilter invFilter) {
		filterInventory = invFilter;

		int slotOffset = invFilter.getFilterTier()  == FilterTier.BASIC ? 3 : invFilter.getFilterTier() == FilterTier.UPGRADED ? 1 : 0;
		for(int i=0; i<invFilter.getFilterTier().getSlotCount(); i++) {
			this.addSlotToContainer(new SlotPhantom(filterInventory, i, 8 + (i+slotOffset) * 18, 19));
		}

		this.addPlayerInventory(invPlayer, 8, 69);
		this.addPlayerHotbar(invPlayer, 8, 127);
	}
    public boolean canDragIntoSlot(Slot slot){
        return false;
    }
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
		boolean alreadyExists = false;
		int firstEmptySlot = -1;
		
    	for(int i=0; i<filterInventory.getSlots(); i++) {
    		if(firstEmptySlot == -1 && filterInventory.getStackInSlot(i).isEmpty()) {
    			firstEmptySlot = i;
    		}
    		if(ItemHandlerHelper.canItemStacksStack(filterInventory.getStackInSlot(i), stack)) {
    			alreadyExists = true;
    		}
    	}
        if (!alreadyExists && !mergeItemStack(stack, firstEmptySlot, firstEmptySlot+1, false)) {
        	return true;
        }
		return false;	
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}	
	@Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}
}
	

