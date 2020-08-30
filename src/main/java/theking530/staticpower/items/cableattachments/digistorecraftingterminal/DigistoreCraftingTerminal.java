package theking530.staticpower.items.cableattachments.digistorecraftingterminal;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import theking530.staticpower.cables.AbstractCableProviderComponent;
import theking530.staticpower.client.StaticPowerAdditionalModels;
import theking530.staticpower.items.cableattachments.digistoreterminal.AbstractDigistoreTerminalAttachment;

public class DigistoreCraftingTerminal extends AbstractDigistoreTerminalAttachment {

	public DigistoreCraftingTerminal(String name) {
		super(name, StaticPowerAdditionalModels.CABLE_DIGISTORE_CRAFTING_TERMINAL_ATTACHMENT_ON, StaticPowerAdditionalModels.CABLE_DIGISTORE_CRAFTING_TERMINAL_ATTACHMENT);
	}

	@Override
	public @Nullable AbstractCableAttachmentContainerProvider getContainerProvider(ItemStack attachment, AbstractCableProviderComponent cable, Direction attachmentSide) {
		return new DigistoreCraftingTerminalContainerProvider(attachment, cable, attachmentSide);
	}

	protected class DigistoreCraftingTerminalContainerProvider extends AbstractCableAttachmentContainerProvider {
		public DigistoreCraftingTerminalContainerProvider(ItemStack stack, AbstractCableProviderComponent cable, Direction attachmentSide) {
			super(stack, cable, attachmentSide);
		}

		@Override
		public Container createMenu(int windowId, PlayerInventory playerInv, PlayerEntity player) {
			return new ContainerDigistoreCraftingTerminal(windowId, playerInv, targetItemStack, attachmentSide, cable);
		}
	}
}
