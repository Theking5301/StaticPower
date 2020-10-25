package theking530.staticpower.cables.item;

import java.util.Collection;
import java.util.HashMap;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.cables.AbstractCableProviderComponent;
import theking530.staticpower.cables.CableUtilities;
import theking530.staticpower.cables.attachments.extractor.ExtractorAttachment;
import theking530.staticpower.cables.attachments.filter.FilterAttachment;
import theking530.staticpower.cables.attachments.retirever.RetrieverAttachment;
import theking530.staticpower.cables.network.CableNetwork;
import theking530.staticpower.cables.network.CableNetworkManager;
import theking530.staticpower.cables.network.CableNetworkModuleTypes;
import theking530.staticpower.cables.network.ServerCable;
import theking530.staticpower.cables.network.ServerCable.CableConnectionState;
import theking530.staticpower.data.TierReloadListener;
import theking530.staticpower.network.StaticPowerMessageHandler;

public class ItemCableComponent extends AbstractCableProviderComponent implements IItemHandler {
	/**
	 * This is the slowest an item may travel.
	 */
	public static final int MAXIMUM_MOVE_TIME = 1000;
	public static final String ITEM_CABLE_MINIMUM_MOVE_TIME_TAG = "min_transfer_time";
	public static final String ITEM_CABLE_FRICTION_FACTOR_TAG = "friction_factor";
	public static final String ITEM_CABLE_ACCELERATION_FACTOR_TAG = "acceleration_factor";

	private final int maxTrasnferSpeed;
	private final float frictionFactor;
	private final float accelerationFactor;
	private final HashMap<Long, ItemRoutingParcelClient> containedPackets;
	private final ResourceLocation tier;
	
	private Direction lastCapabilityRequestedDirection;

	public ItemCableComponent(String name, ResourceLocation tier, int maxTransferSpeed, float frictionFactor, float accelerationFactor) {
		super(name, CableNetworkModuleTypes.ITEM_NETWORK_MODULE);
		containedPackets = new HashMap<Long, ItemRoutingParcelClient>();
		lastCapabilityRequestedDirection = Direction.UP;
		this.maxTrasnferSpeed = maxTransferSpeed;
		this.frictionFactor = frictionFactor;
		this.accelerationFactor = accelerationFactor;
		this.tier = tier;
		addValidAttachmentClass(ExtractorAttachment.class);
		addValidAttachmentClass(FilterAttachment.class);
		addValidAttachmentClass(RetrieverAttachment.class);
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
			if (network == null) {
				CableNetworkManager.get(getWorld()).removeCable(getPos());
				throw new RuntimeException(String.format("Encountered a null network for an ItemCableComponent at position: %1$s.", getPos()));
			}

			// Get the module.
			ItemNetworkModule itemNetworkModule = (ItemNetworkModule) network.getModule(CableNetworkModuleTypes.ITEM_NETWORK_MODULE);

			// Tell the network module this cable was broken.
			itemNetworkModule.onItemCableBroken(getPos());
		}

		super.onOwningTileEntityRemoved();
	}

	public int getMaxTransferSpeed() {
		return maxTrasnferSpeed;
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
	protected ServerCable createCable() {
		return new ServerCable(getWorld(), getPos(), getSupportedNetworkModuleTypes(), (cable) -> {
			cable.setProperty(ITEM_CABLE_MINIMUM_MOVE_TIME_TAG, maxTrasnferSpeed);
			cable.setProperty(ITEM_CABLE_FRICTION_FACTOR_TAG, frictionFactor);
			cable.setProperty(ITEM_CABLE_ACCELERATION_FACTOR_TAG, accelerationFactor);
		});
	}

	/**
	 * USED ONLY to render client blocks.
	 */
	@Override
	protected CableConnectionState cacheConnectionState(Direction side, @Nullable TileEntity te, BlockPos blockPosition) {
		// Check to see if there is a cable on this side that can connect to this one.
		// If true, connect. If not, check if there is a TE that we can connect to. If
		// not, return non.
		AbstractCableProviderComponent otherProvider = CableUtilities.getCableWrapperComponent(getWorld(), blockPosition);
		if (otherProvider != null && otherProvider.areCableCompatible(this, side)) {
			if (!otherProvider.isSideDisabled(side.getOpposite())) {
				return CableConnectionState.CABLE;
			}
		} else if (te != null) {
			if (te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).isPresent()) {
				return CableConnectionState.TILE_ENTITY;
			}
		}
		return CableConnectionState.NONE;
	}

	@Override
	public <T> LazyOptional<T> provideCapability(Capability<T> cap, Direction side) {
		// Only provide the item capability if we are not disabled on the provided side.
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
			boolean disabled = false;
			if (side != null) {
				if (getWorld().isRemote) {
					disabled = isSideDisabled(side);
				} else {
					ServerCable cable = CableNetworkManager.get(getWorld()).getCable(getPos());
					disabled = cable.isDisabledOnSide(side);
				}
			}

			if (!disabled) {
				lastCapabilityRequestedDirection = side;
				return LazyOptional.of(() -> this).cast();
			}
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

			this.<ItemNetworkModule>getNetworkModule(CableNetworkModuleTypes.ITEM_NETWORK_MODULE).ifPresent(network -> {
				// Attempt to insert the stack into the cable. We will use the default
				// extraction speed.
				ItemStack remainingAmount = network.transferItemStack(insertStack, getPos(), lastCapabilityRequestedDirection, false,
						TierReloadListener.getTier(tier).getExtractedItemInitialSpeed());
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
}
