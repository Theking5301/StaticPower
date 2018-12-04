package theking530.staticpower.tileentity;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public interface IUpgradeableTileEntity {

	public void handleUpgrades();
	public boolean isUpgradeable();
	public ItemStackHandler getUpgradeInventory();
	public List<Integer> getUpgradeSlots();
	public boolean isValidUpgrade(ItemStack update);

}
