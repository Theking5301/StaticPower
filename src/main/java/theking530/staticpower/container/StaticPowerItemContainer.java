package theking530.staticpower.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;

public abstract class StaticPowerItemContainer<T extends Item> extends StaticPowerContainer {
	private final ItemStack itemstack;

	protected StaticPowerItemContainer(ContainerTypeAllocator<? extends StaticPowerContainer, ?> allocator, int id, PlayerInventory inv, ItemStack itemStack) {
		super(allocator, id, inv);
		itemstack = itemStack;
		initializeContainer(); // This has to be called here and not in the super.
	}

	/**
	 * Gets the {@link ItemStack} that triggered the opening of this container.
	 * 
	 * @return The {@link ItemStack} that opened this container.
	 */
	public ItemStack getItemStack() {
		return itemstack;
	}

	/**
	 * Gets the {@link ItemStack} from the provided data packet.
	 * 
	 * @param inv  The player's inventory.
	 * @param data The buffer including the index of the itemstack in the player's
	 *             inventory.
	 * @return The {@link ItemStack} that triggered the opening of this container.
	 */
	protected static ItemStack getHeldItemstack(PlayerInventory inv, PacketBuffer data) {
		return inv.getStackInSlot(data.readInt());
	}
}
