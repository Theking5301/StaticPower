package theking530.staticpower.items.upgrades;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.items.ItemBase;

public class BaseUpgrade extends ItemBase implements IMachineUpgrade{

	BaseUpgrade(String name, Tier tier){
		super(name);
		setMaxStackSize(16);
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		return 0;
	}
	@Override
	public int getValueMultiplied(int value, float multiplier) {
		return (int) (value + (multiplier*(float)value));
	}
	@Override
	public float getValueMultiplied(float value, float multiplier) {
		return value + (multiplier*value);
	}
}
