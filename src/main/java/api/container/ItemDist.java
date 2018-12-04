package api.container;

import net.minecraft.item.ItemStack;

public class ItemDist {

	public void distributeItem(ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4, ItemStack output5) {
		
	}
	public int distributedSize(ItemStack itemStack1, ItemStack itemStack2) {
		if(itemStack1 != null && itemStack2 != null) {
			if(itemStack1.isItemEqual(itemStack2)) {
				int i = itemStack1.getCount();
				int j = itemStack2.getCount();
				return i+j;
			}
		}
		return 0;
	}
	public static boolean areSlotsAvailable(ItemStack stack, ItemStack slot, int invStackLimit) {
		if (slot == ItemStack.EMPTY) {
			return true;
		}	
		if(slot.isItemEqual(stack) && (!stack.getHasSubtypes() || stack.getItemDamage() == slot.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, slot)) {
			int i = slot.getCount() + stack.getCount();
			if(i > stack.getCount() || i > invStackLimit) {
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
}
