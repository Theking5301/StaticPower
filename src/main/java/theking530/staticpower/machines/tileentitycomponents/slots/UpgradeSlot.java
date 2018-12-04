package theking530.staticpower.machines.tileentitycomponents.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.items.upgrades.IMachineUpgrade;

public class UpgradeSlot extends StaticPowerContainerSlot {

	public UpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof IMachineUpgrade;          
    }

}
