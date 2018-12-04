package theking530.staticpower.items.tools;

import net.minecraft.item.ItemStack;

public interface ISolderingIron {
	
	/**
	 * @param stack
	 * @return Should Iron be destroyed.
	 */
	boolean useSolderingItem(ItemStack stack);
	boolean canSolder(ItemStack stack);
}
