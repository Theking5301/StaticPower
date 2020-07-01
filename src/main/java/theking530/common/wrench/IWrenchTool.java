package theking530.common.wrench;

import net.minecraft.item.ItemStack;

public interface IWrenchTool {
	public RegularWrenchMode getWrenchMode(ItemStack stack);
	public SneakWrenchMode getSneakWrenchMode(ItemStack stack);
}