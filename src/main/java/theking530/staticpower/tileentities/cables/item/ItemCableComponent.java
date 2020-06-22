package theking530.staticpower.tileentities.cables.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.items.cableattachments.extractor.ExtractorAttachment;
import theking530.staticpower.items.cableattachments.filter.FilterAttachment;
import theking530.staticpower.network.StaticPowerMessageHandler;
import theking530.staticpower.tileentities.cables.AbstractCableProviderComponent;
import theking530.staticpower.tileentities.cables.CableUtilities;
import theking530.staticpower.tileentities.cables.ServerCable;
import theking530.staticpower.tileentities.cables.ServerCable.CableConnectionState;
import theking530.staticpower.tileentities.cables.network.CableNetwork;
import theking530.staticpower.tileentities.cables.network.CableNetworkManager;
import theking530.staticpower.tileentities.cables.network.modules.ItemCableAddedPacket;
import theking530.staticpower.tileentities.cables.network.modules.ItemCableRemovedPacket;
import theking530.staticpower.tileentities.cables.network.modules.ItemNetworkModule;
import theking530.staticpower.tileentities.cables.network.modules.factories.CableNetworkModuleTypes;

public class ItemCableComponent extends AbstractCableProviderComponent implements IItemHandler {
	private int itemTransferSpeed = 40;
	private HashMap<Long, ItemRoutingParcelClient> containedPackets;
	private Direction lastCapabilityRequestedDirection;

	public ItemCableComponent(String name) {
		super(name, CableNetworkModuleTypes.ITEM_NETWORK_MODULE);
		containedPackets = new HashMap<Long, ItemRoutingParcelClient>();
		lastCapabilityRequestedDirection = Direction.UP;
	}

	@Override
	public void preProcessUpdate() {
		super.preProcessUpdate();
		// Only do this on the client.
		if (getWorld().isRemote) {
			for (ItemRoutingParcelClient packet : containedPackets.values()) {
				packet.incrementMoveTimer();
			}
		}
	}

	@Override
	public void onOwningTileEntityRemoved() {
		// Only perform the following on the server.
		if (!getWorld().isRemote) {
			// Get the network.
			CableNetwork network = CableNetworkManager.get(getWorld()).getCable(getPos()).getNetwork();
			ItemNetworkModule itemNetworkModule = (ItemNetworkModule) network.getModule(CableNetworkModuleTypes.ITEM_NETWORK_MODULE);
			if (network == null || itemNetworkModule == null) {
				throw new RuntimeException(String.format("Encountered a null network for an ItemCableComponent at position: %1$s.", getPos()));
			}

			// Tell the network module this cable was broken.
			itemNetworkModule.onItemCableBroken(getPos());
		}

		super.onOwningTileEntityRemoved();
	}

	public int getTransferSpeed() {
		return itemTransferSpeed;
	}

	public void addTransferingItem(ItemRoutingParcelClient routingPacket) {
		containedPackets.put(routingPacket.getId(), routingPacket);
		if (!getWorld().isRemote) {
			StaticPowerMessageHandler.MAIN_PACKET_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> getWorld().getChunkAt(getPos())), new ItemCableAddedPacket(this, routingPacket));
		}
		getTileEntity().markDirty();
	}

	public void removeTransferingItem(long parcelId) {
		containedPackets.remove(parcelId);
		if (!getWorld().isRemote) {
			StaticPowerMessageHandler.MAIN_PACKET_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> getWorld().getChunkAt(getPos())), new ItemCableRemovedPacket(this, parcelId));
		}
		getTileEntity().markDirty();
	}

	public Collection<ItemRoutingParcelClient> getContainedItems() {
		return containedPackets.values();
	}

	public boolean canInsertThroughSide(ItemStack stack, Direction destinationSide) {
		// If there is no attachment on that side, we can insert.
		if (!hasAttachment(destinationSide)) {
			return true;
		}

		// If there is a filter attachment, evaluate against it.
		if (getAttachment(destinationSide).getItem() instanceof FilterAttachment) {
			FilterAttachment filterAttachmentItem = (FilterAttachment) getAttachment(destinationSide).getItem();
			return filterAttachmentItem.doesItemPassFilter(getAttachment(destinationSide), stack, this);
		}

		// If there is an attachment, but its not a filter attachment, return false.
		return false;
	}

	@Override
	protected void processAttachment(Direction side, ItemStack attachment) {
		// Process the extractor attachment.
		if (!getWorld().isRemote && attachment.getItem() instanceof ExtractorAttachment && doesAttachmentPassRedstoneTest(attachment)) {
			processExtractorAttachment(side, attachment);
		}
	}

	protected void processExtractorAttachment(Direction side, ItemStack attachment) {
		// Increment the extraction timer. If it returns true, continue. If not, stop.
		if (!incrementExtractionTimer(attachment)) {
			return;
		}

		// Get the tile entity on the pulling side, return if it is null.
		TileEntity te = getWorld().getTileEntity(getPos().offset(side));
		if (te == null || te.isRemoved()) {
			return;
		}

		// Attempt to transfer the item.
		getItemNetworkModule().ifPresent(network -> {
			// Attempt to extract an item.
			te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).ifPresent(inv -> {
				for (int i = 0; i < inv.getSlots(); i++) {
					// Simulate an extract.
					ItemStack extractedItem = inv.extractItem(i, getExtractionStackSize(attachment), true);

					// If the extracted item is empty, continue.
					if (extractedItem.isEmpty()) {
						continue;
					}

					// Skip any items that are not supported by the extraction filter.
					ExtractorAttachment extractorItem = (ExtractorAttachment) attachment.getItem();
					if (!extractorItem.doesItemPassExtractionFilter(attachment, extractedItem, this)) {
						continue;
					}

					// Attempt to transfer the itemstack through the cable network.
					ItemStack remainingAmount = network.transferItemStack(extractedItem, getPos(), side, false);
					if (remainingAmount.getCount() < extractedItem.getCount()) {
						inv.extractItem(i, extractedItem.getCount() - remainingAmount.getCount(), false);
						getTileEntity().markDirty();
						break;
					}
				}
			});
		});
	}

	/**
	 * USED ONLY to render client blocks.
	 */
	@Override
	protected CableConnectionState cacheConnectionState(Direction side, BlockPos blockPosition) {
		// Check to see if there is a cable on this side that can connect to this one.
		// If true, connect. If not, check if there is a TE that we can connect to. If
		// not, return non.
		AbstractCableProviderComponent otherProvider = CableUtilities.getCableWrapperComponent(getWorld(), blockPosition);
		if (otherProvider != null && otherProvider.shouldConnectionToCable(this, side)) {
			return CableConnectionState.CABLE;
		} else if (getWorld().getTileEntity(blockPosition) != null && otherProvider == null) {
			TileEntity te = getWorld().getTileEntity(blockPosition);
			if (te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).isPresent()) {
				return CableConnectionState.TILE_ENTITY;
			}
		}
		return CableConnectionState.NONE;
	}

	@Override
	protected boolean canAttachAttachment(ItemStack attachment) {
		return !attachment.isEmpty() && (attachment.getItem() instanceof ExtractorAttachment || attachment.getItem() instanceof FilterAttachment);
	}

	protected boolean incrementExtractionTimer(ItemStack extractorAttachment) {
		if (!extractorAttachment.hasTag()) {
			extractorAttachment.setTag(new CompoundNBT());
		}
		if (!extractorAttachment.getTag().contains(ExtractorAttachment.EXTRACTION_TIMER_TAG)) {
			extractorAttachment.getTag().putInt(ExtractorAttachment.EXTRACTION_TIMER_TAG, 0);
		}

		// Get the current timer and the extraction rate.
		int currentTimer = extractorAttachment.getTag().getInt(ExtractorAttachment.EXTRACTION_TIMER_TAG);
		int extractionRate = getExtractorExtractionRate(extractorAttachment);

		// Increment the current timer.
		currentTimer += 1;
		if (currentTimer >= extractionRate) {
			extractorAttachment.getTag().putInt(ExtractorAttachment.EXTRACTION_TIMER_TAG, 0);
			return true;
		} else {
			extractorAttachment.getTag().putInt(ExtractorAttachment.EXTRACTION_TIMER_TAG, currentTimer);

			return false;
		}
	}

	protected int getExtractorExtractionRate(ItemStack extractorAttachment) {
		return ((ExtractorAttachment) extractorAttachment.getItem()).getExtractionRate();
	}

	protected int getExtractionStackSize(ItemStack extractorAttachment) {
		return ((ExtractorAttachment) extractorAttachment.getItem()).getExtractionStackSize();
	}

	@Override
	public <T> LazyOptional<T> provideCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
			lastCapabilityRequestedDirection = side;
			return LazyOptional.of(() -> this).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.serializeUpdateNbt(nbt, fromUpdate);

		if (fromUpdate) {
			// Serialize the contained item packets.
			ListNBT itemPackets = new ListNBT();
			containedPackets.values().forEach(parcel -> {
				CompoundNBT packetTag = new CompoundNBT();
				parcel.writeToNbt(packetTag);
				itemPackets.add(packetTag);
			});
			nbt.put("item_packets", itemPackets);
		}

		return nbt;
	}

	@Override
	public void deserializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.deserializeUpdateNbt(nbt, fromUpdate);

		if (fromUpdate) {
			// Clear any contained packets.
			containedPackets.clear();

			// Deserialize the packets.
			ListNBT packets = nbt.getList("item_packets", Constants.NBT.TAG_COMPOUND);
			for (INBT packetTag : packets) {
				CompoundNBT packetTagCompound = (CompoundNBT) packetTag;
				ItemRoutingParcelClient newPacket = ItemRoutingParcelClient.create(packetTagCompound);
				if (!containedPackets.containsKey(newPacket.getId())) {
					containedPackets.put(newPacket.getId(), newPacket);
				}
			}
		}
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!stack.isEmpty()) {
			// Create a copy of the stack maxed to the slot limit of the cable.
			ItemStack insertStack = stack.copy();
			insertStack.setCount(Math.min(stack.getCount(), getSlotLimit(slot)));

			getItemNetworkModule().ifPresent(network -> {
				// Attempt to insert the stack into the cable.
				ItemStack remainingAmount = network.transferItemStack(insertStack, getPos(), lastCapabilityRequestedDirection, false);
				if (remainingAmount.getCount() < insertStack.getCount()) {
					getTileEntity().markDirty();
					stack.setCount(stack.getCount() - insertStack.getCount() + remainingAmount.getCount());
				}
			});
		}
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}

	/**
	 * Gets the item network module for the network this cable belongs to. We have
	 * to wrap it in an optional because while we can guarantee once this component
	 * is validated that the network is valid, since this component exposes external
	 * methods, other tile entity that are made valid before us may call some of our
	 * methods.
	 * 
	 * @return
	 */
	protected Optional<ItemNetworkModule> getItemNetworkModule() {
		CableNetworkManager manager = CableNetworkManager.get(getTileEntity().getWorld());
		ServerCable cable = manager.getCable(getTileEntity().getPos());
		if (cable.getNetwork() != null) {
			return Optional.of(cable.getNetwork().getModule(CableNetworkModuleTypes.ITEM_NETWORK_MODULE));
		}
		return Optional.empty();
	}
}
