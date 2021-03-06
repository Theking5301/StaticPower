package theking530.api.multipart;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BasicMultiPartSlot extends AbstractMultiPartSlot {
	private final Class<? extends Item> acceptableClass;

	public BasicMultiPartSlot(String name, String unlocalizedName, boolean isOptional, Class<? extends Item> acceptableClass) {
		super(name, unlocalizedName, isOptional);
		this.acceptableClass = acceptableClass;
	}

	@Override
	public boolean canAcceptItem(ItemStack stack) {
		return acceptableClass.isInstance(stack.getItem());
	}

	public Class<? extends Item> getAcceptableItemClass() {
		return acceptableClass;
	}
}
