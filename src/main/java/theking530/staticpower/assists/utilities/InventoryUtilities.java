package theking530.staticpower.assists.utilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtilities {

	public static boolean canInsertItemsIntoInventory(IItemHandler inv, ItemStack[] items) {
		IItemHandler dupInv = new ItemStackHandler(inv.getSlots());
		List<ItemStack> dupItems = new ArrayList<ItemStack>();
		
		for(int i=0; i<inv.getSlots(); i++) {
			dupInv.insertItem(i, inv.getStackInSlot(i).copy(), false);
		}
		for(int i=0; i<items.length; i++) {
			dupItems.add(items[i].copy());
		}
		
		for(int i=0; i<dupInv.getSlots(); i++) {		
			for(int j=dupItems.size()-1; j>=0; j--) {
				if(dupItems.get(j).isEmpty()) {
					continue;
				}
				if(dupInv.getStackInSlot(i).isEmpty()) {
					dupItems.set(j, ItemStack.EMPTY);
					continue;
				}
				dupItems.set(j, dupInv.insertItem(i, dupItems.get(j), false));
			}
		}		
		for(int i=0; i<dupItems.size(); i++) {
			if(!dupItems.get(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}	
	public static boolean canInsertItemsIntoInventory(IItemHandler inv, List<ItemStack> items) {
		ItemStack[] itemArray = new ItemStack[items.size()];
		items.toArray(itemArray);
		return canInsertItemsIntoInventory(inv, itemArray);
	}
	public static boolean canFullyInsertStackIntoSlot(IItemHandler inv, int slot, ItemStack stack) {
		return inv.insertItem(slot, stack, true).isEmpty();
	}
	public static boolean canPartiallyInsertItemIntoSlot(IItemHandler inv, int slot, ItemStack stack) {
		return inv.insertItem(slot, stack, true).getCount() != stack.getCount();
	}
	public static boolean canFullyInsertItemIntoInventory(IItemHandler inv, ItemStack stack) {
		ItemStack output = stack;
		for(int i=0; i<inv.getSlots(); i++) {
			output = inv.insertItem(i, output, true);
			if(output.getCount() <= 0) {
				break;
			}
		}
		return output.getCount() <= 0;
	}
	public static boolean canInsertItemIntoInventory(IItemHandler inv, ItemStack stack) {
		for(int i=0; i<inv.getSlots(); i++) {
			if(canPartiallyInsertItemIntoSlot(inv, i, stack)) {
				return true;
			}
		}
		return false;
	}
	public static ItemStack insertItemIntoInventory(IItemHandler inv, ItemStack stack) {
		ItemStack output = stack;
		for(int i=0; i<inv.getSlots(); i++) {
			output = inv.insertItem(i, output, false);
			if(output.getCount() <= 0) {
				break;
			}
		}
		return output;
	}
	public static ItemStack insertItemIntoInventory(IItemHandler inv, ItemStack stack, int start, int stop) {
		for(int i=start; i<stop+1; i++) {
			if(canPartiallyInsertItemIntoSlot(inv, i, stack)) {
				return inv.insertItem(i, stack.copy(), false);
			}
		}
		return stack;
	}
	public static ItemStack fullyInsertItemIntoInventory(IItemHandler inv, ItemStack stack) {
		for(int i=0; i<inv.getSlots(); i++) {
			if(canFullyInsertStackIntoSlot(inv, i, stack)) {
				return inv.insertItem(i, stack, false);
			}
		}
		return stack;
	}
}
