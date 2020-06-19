package theking530.staticpower.tileentities.nonpowered.digistorenetwork.ioport;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import theking530.staticpower.initialization.ModBlocks;
import theking530.staticpower.initialization.ModTileEntityTypes;
import theking530.staticpower.tileentities.nonpowered.digistorenetwork.BaseDigistoreTileEntity;
import theking530.staticpower.tileentities.nonpowered.digistorenetwork.digistore.TileEntityDigistore;

public class TileEntityDigistoreIOPort extends BaseDigistoreTileEntity {

	public TileEntityDigistoreIOPort() {
		super(ModTileEntityTypes.DIGISTORE_IO_PORT);
		registerComponent(new DigitstoreIOPortInventoryComponent("IOPortComponent"));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		// If this is manager and the player is holding an item, continue.
		if (isManaged()) {
			// Keep track of if any items changed.
			boolean itemInserted = false;

			// Loop through the whole inventory.
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				// Skip empty slots.
				if (player.inventory.getStackInSlot(i).isEmpty()) {
					continue;
				}
				// Get the item in the slot.
				ItemStack currentItem = player.inventory.getStackInSlot(i).copy();

				// Iterate through all the digistores and see if any of them already exist to
				// take the supplied item. If they do, try to insert and keep going until the
				// held stack is empty.
				for (TileEntityDigistore digistore : getManager().getNetwork().getAllNetworkTiles(TileEntityDigistore.class)) {
					if (digistore.doesItemMatchStoredItem(currentItem)) {
						currentItem = digistore.insertItem(currentItem, false);
					}
					// If we inserted all of the held item, break the loop and complete the process.
					if (currentItem.isEmpty()) {
						break;
					}
				}

				// If there are still more items in the held stack, now we can use empty
				// digistores.
				if (!currentItem.isEmpty() && currentItem.isItemEqual(player.getHeldItem(hand))) {
					for (TileEntityDigistore digistore : getManager().getNetwork().getAllNetworkTiles(TileEntityDigistore.class)) {
						currentItem = digistore.insertItem(currentItem, false);
						if (currentItem.isEmpty()) {
							break;
						}
					}
				}

				// Update the held item.
				if (currentItem.getCount() != player.inventory.getStackInSlot(i).getCount()) {
					itemInserted = true;
					player.inventory.setInventorySlotContents(i, currentItem);
				}
			}
			
			// IF an item was inserted and the world is remote, play a sound.
			if (world.isRemote && itemInserted) {
				world.playSound(player, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.CONSUME;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.DigistoreIOPort.getTranslationKey());
	}
}