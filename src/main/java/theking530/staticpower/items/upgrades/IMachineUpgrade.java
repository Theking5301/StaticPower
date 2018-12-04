package theking530.staticpower.items.upgrades;

import net.minecraft.item.ItemStack;

public interface IMachineUpgrade {

	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber);
	public int getValueMultiplied(int value, float multiplier);
	public float getValueMultiplied(float value, float multiplier);
}
