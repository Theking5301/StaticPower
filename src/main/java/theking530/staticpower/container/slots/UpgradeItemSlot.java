package theking530.staticpower.container.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import theking530.api.IUpgradeItem;

public class UpgradeItemSlot extends StaticPowerContainerSlot {

	public UpgradeItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof IUpgradeItem;
	}

}
