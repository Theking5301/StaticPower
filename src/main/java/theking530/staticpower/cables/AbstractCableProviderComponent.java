package theking530.staticpower.cables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import theking530.staticpower.cables.attachments.AbstractCableAttachment;
import theking530.staticpower.cables.network.AbstractCableNetworkModule;
import theking530.staticpower.cables.network.CableNetwork;
import theking530.staticpower.cables.network.CableNetworkManager;
import theking530.staticpower.cables.network.ServerCable;
import theking530.staticpower.cables.network.ServerCable.CableConnectionState;
import theking530.staticpower.tileentities.components.AbstractTileEntityComponent;
import theking530.staticpower.tileentities.components.control.redstonecontrol.RedstoneMode;
import theking530.staticpower.utilities.WorldUtilities;

public abstract class AbstractCableProviderComponent extends AbstractTileEntityComponent {
	/** KEEP IN MIND: This is purely cosmetic and on the client side. */
	public static final ModelProperty<CableRenderingState> CABLE_RENDERING_STATE = new ModelProperty<>();
	/** The type of this cable. */
	private final HashSet<ResourceLocation> SupportedNetworkModules;
	/**
	 * Keeps track of which sides of the cable are disabled. This value is a client
	 * copy of the master value which exists on the server.
	 */
	private final boolean[] DisabledSides;
	/**
	 * Cache for the connection states. This is updated every time a new baked model
	 * is requested AND also, on first placement.
	 */
	protected final CableConnectionState[] connectionStates;
	/** If false, the connection states will be reinitialized. */
	protected boolean connectionStatesInitialized;
	/** Container for all the attachments on this cable. */
	protected final ItemStack[] attachments;
	/** Container for all the covers on this cable. */
	protected final ItemStack[] covers;
	/** List of valid attachment classes. */
	private final HashSet<Class<? extends AbstractCableAttachment>> validAttachments;

	public AbstractCableProviderComponent(String name, ResourceLocation... supportedModules) {
		super(name);

		// Capture the types.
		SupportedNetworkModules = new HashSet<ResourceLocation>();
		for (ResourceLocation module : supportedModules) {
			SupportedNetworkModules.add(module);
		}

		// Initialize the valid attachments set.
		validAttachments = new HashSet<Class<? extends AbstractCableAttachment>>();

		// Initialize the disabled sides, connection states, and attachments arrays.
		DisabledSides = new boolean[] { false, false, false, false, false, false };
		connectionStates = new CableConnectionState[] { CableConnectionState.NONE, CableConnectionState.NONE, CableConnectionState.NONE, CableConnectionState.NONE, CableConnectionState.NONE,
				CableConnectionState.NONE };
		attachments = new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY };
		covers = new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY };
		connectionStatesInitialized = false;
	}

	public void preProcessUpdate() {
		if (getWorld().isRemote || (!getWorld().isRemote && getNetwork() != null)) {
			for (Direction dir : Direction.values()) {
				if (!attachments[dir.ordinal()].isEmpty()) {
					ItemStack attachment = attachments[dir.ordinal()];
					((AbstractCableAttachment) attachment.getItem()).attachmentTick(attachment, dir, this);
				}
			}
		}

		if (!connectionStatesInitialized) {
			scanForAttachments();
		}
	}

	@Override
	public void onNeighborChanged(BlockState currentState, BlockPos neighborPos) {
		super.onNeighborChanged(currentState, neighborPos);

		// Update the network graph.
		if (!getWorld().isRemote()) {
			ServerCable cable = CableNetworkManager.get((ServerWorld) getWorld()).getCable(getPos());
			if (cable != null && cable.getNetwork() != null) {
				cable.getNetwork().updateGraph((ServerWorld) getWorld(), getPos());
			}
		}

		scanForAttachments();
	}

	@Override
	public void onOwningBlockBroken(BlockState state, BlockState newState, boolean isMoving) {
		// Drop the covers and attachments.
		for (Direction dir : Direction.values()) {
			if (hasAttachment(dir)) {
				WorldUtilities.dropItem(getWorld(), getPos(), removeAttachment(dir));
			}
			if (hasCover(dir)) {
				WorldUtilities.dropItem(getWorld(), getPos(), removeCover(dir));
			}
		}
	}

	/**
	 * Checks to see if the provided side is disabled. This should only be called on
	 * the client. The server should query the {@link ServerCable} directly.
	 * 
	 * @param side
	 * @return
	 */
	public boolean isSideDisabled(Direction side) {
		return DisabledSides[side.ordinal()];
	}

	public void setSideDisabledState(Direction side, boolean disabledState) {
		DisabledSides[side.ordinal()] = disabledState;
		scanForAttachments();
		getTileEntity().markTileEntityForSynchronization();
		if (!getWorld().isRemote) {
			CableNetworkManager.get(getWorld()).getCable(getPos()).setDisabledStateOnSide(side, disabledState);
		}
	}

	/**
	 * Gets the connection state on the provided side.
	 * 
	 * @param side
	 * @return
	 */
	public CableConnectionState getConnectionState(Direction side) {
		return connectionStates[side.ordinal()];
	}

	/**
	 * Gets the connection states on all sides.
	 * 
	 * @return
	 */
	public CableConnectionState[] getConnectionStates() {
		return connectionStates;
	}

	/**
	 * Allows us to provide additional data about state of the cable for rendering
	 * purposes.
	 */
	@Override
	public void getModelData(ModelDataMap.Builder builder) {
		builder.withInitial(CABLE_RENDERING_STATE, getRenderingState());
	}

	public CableRenderingState getRenderingState() {
		return new CableRenderingState(connectionStates, getAttachmentModels(), attachments, covers, DisabledSides, getPos());
	}

	public HashSet<ResourceLocation> getSupportedNetworkModuleTypes() {
		return this.SupportedNetworkModules;
	}

	/**
	 * When the owning tile entity is validated, we check to see if there is a cable
	 * wrapper in the network for this cable. If not, we provide one.
	 */
	@Override
	public void onOwningTileEntityValidate() {
		super.onOwningTileEntityValidate();
		// If we're on the server, check to see if the cable network manager for this
		// world is tracking a cable at this position. If it is not, add this cable for
		// tracking.
		if (!getTileEntity().getWorld().isRemote) {
			CableNetworkManager manager = CableNetworkManager.get(getWorld());
			if (!manager.isTrackingCable(getPos())) {
				ServerCable wrapper = createCable();
				initializeCableProperties(wrapper);
				manager.addCable(wrapper);
			} else {
				initializeCableProperties(manager.getCable(getPos()));
			}
		}
	}

	/**
	 * After placed, we update the connection states.
	 */
	@Override
	public void updatePostPlacement(BlockState state, Direction direction, BlockState facingState, BlockPos FacingPos) {
		super.updatePostPlacement(state, direction, facingState, FacingPos);
		scanForAttachments();
	}

	/**
	 * When the owning tile entity is removed from the world, we remove the cable
	 * wrapper for this tile entity as well.
	 */
	@Override
	public void onOwningTileEntityRemoved() {
		super.onOwningTileEntityRemoved();
		// If we're on the server, get the cable manager and remove the cable at the
		// current position.
		if (!getWorld().isRemote) {
			CableNetworkManager manager = CableNetworkManager.get(getTileEntity().getWorld());
			manager.removeCable(getTileEntity().getPos());
		}
	}

	/**
	 * Adds a valid attachment class that can be attached to this cable.
	 * 
	 * @param attachmentClass
	 */
	protected void addValidAttachmentClass(Class<? extends AbstractCableAttachment> attachmentClass) {
		validAttachments.add(attachmentClass);
	}

	/**
	 * Attaches an attachment to the provided side. Returns true if the attachment
	 * was added. Returns false otherwise.
	 * 
	 * @param attachment The attachment itemstack to add.
	 * @param side       The side to attach it to.
	 * @return True if the attachment was applied, false otherwise.
	 */
	public boolean attachAttachment(ItemStack attachment, Direction side) {
		// Check if we can attach the attachment.
		if (!canAttachAttachment(attachment)) {
			return false;
		}

		// If there is no attachment on the provided side, add it.
		if (attachments[side.ordinal()].isEmpty()) {
			attachments[side.ordinal()] = attachment.copy();
			attachments[side.ordinal()].setCount(1);

			// Raise the on added method on the attachment.
			AbstractCableAttachment attachmentItem = (AbstractCableAttachment) attachments[side.ordinal()].getItem();
			attachmentItem.onAddedToCable(attachments[side.ordinal()], side, this);

			// Initialize the data container on the server.
			if (!getWorld().isRemote) {
				ServerCable cable = CableNetworkManager.get(getWorld()).getCable(getPos());
				cable.reinitializeAttachmentDataForSide(side, attachmentItem.getRegistryName());
				attachmentItem.initializeServerDataContainer(attachments[side.ordinal()], side, this, cable.getAttachmentDataForSide(side));
			}

			// Re-sync the tile entity.
			getTileEntity().markTileEntityForSynchronization();
			return true;
		}

		return false;
	}

	/**
	 * Removes an attachment from the provided side and returns the itemstack for
	 * the attachment. If there is no attachment, returns an empty Itemstack.
	 * 
	 * @param side The side to remove from.
	 * @return
	 */
	public ItemStack removeAttachment(Direction side) {
		// Ensure we have an attachment on the provided side.
		if (hasAttachment(side)) {
			// Get the attachment for the side.
			ItemStack output = attachments[side.ordinal()];

			// Raise the on removed method on the attachment.
			AbstractCableAttachment attachmentItem = (AbstractCableAttachment) output.getItem();
			attachmentItem.onRemovedFromCable(output, side, this);

			// Remove the attachment and return it.
			attachments[side.ordinal()] = ItemStack.EMPTY;
			getTileEntity().markTileEntityForSynchronization();

			// Clear the attachment data from the server.
			if (!getWorld().isRemote) {
				CableNetworkManager.get(getWorld()).getCable(getPos()).clearAttachmentDataForSide(side);
			}

			return output;
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Attaches a cover to the provided side. Returns true if the cover was added.
	 * Returns false otherwise.
	 * 
	 * @param attachment The cover itemstack to add.
	 * @param side       The side to attach it to.
	 * @return True if the cover was applied, false otherwise.
	 */
	public boolean attachCover(ItemStack attachment, Direction side) {
		// If there is no cover on the provided side, add it.
		if (covers[side.ordinal()].isEmpty()) {
			covers[side.ordinal()] = attachment.copy();
			covers[side.ordinal()].setCount(1);

			getTileEntity().markTileEntityForSynchronization();
			return true;
		}
		return false;
	}

	/**
	 * Removes a cover from the provided side and returns the itemstack for the
	 * cover. If there is no cover, returns an empty Itemstack.
	 * 
	 * @param side The side to remove from.
	 * @return
	 */
	public ItemStack removeCover(Direction side) {
		// Ensure we have an attachment on the provided side.
		if (hasCover(side)) {
			// Get the attachment for the side.
			ItemStack output = covers[side.ordinal()];

			// Remove the attachment and return it.
			covers[side.ordinal()] = ItemStack.EMPTY;
			getTileEntity().markTileEntityForSynchronization();
			return output;
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Checks to see if an attachment is attached on that side.
	 * 
	 * @param side The side to check for.
	 * @return Returns true if a attachment is applied on the provided side.
	 */
	public boolean hasAttachment(Direction side) {
		return !attachments[side.ordinal()].isEmpty();
	}

	/**
	 * Checks to see if this component has any attachments of the provided type.
	 * 
	 * @param attachmentClass The attachment class to check.
	 * @return True if the cable has at least one attachment of this type.
	 */
	public boolean hasAttachmentOfType(Class<? extends AbstractCableAttachment> attachmentClass) {
		for (Direction dir : Direction.values()) {
			if (attachmentClass.isInstance(attachments[dir.ordinal()].getItem())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets a list of all attachments of the provided class.
	 * 
	 * @param attachmentClass The attachment class to check for.
	 * @return
	 */
	public List<ItemStack> getAttachmentsOfType(Class<? extends AbstractCableAttachment> attachmentClass) {
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		for (Direction dir : Direction.values()) {
			if (attachmentClass.isInstance(attachments[dir.ordinal()].getItem())) {
				outputs.add(attachments[dir.ordinal()]);
			}
		}
		return outputs;
	}

	/**
	 * Checks to see if a cover is attached on that side.
	 * 
	 * @param side The side to check for.
	 * @return Returns true if a cover is applied on the provided side.
	 */
	public boolean hasCover(Direction side) {
		return !covers[side.ordinal()].isEmpty();
	}

	/**
	 * Gets the cover on the provided side. If no cover is present, returns an empty
	 * itemstack.
	 * 
	 * @param side The side to check for.
	 * @return
	 */
	public ItemStack getCover(Direction side) {
		return covers[side.ordinal()];
	}

	/**
	 * Gets the attachment on the provided side. If no attachment is present,
	 * returns an empty itemstack.
	 * 
	 * @param side The side to check for.
	 * @return
	 */
	public ItemStack getAttachment(Direction side) {
		return attachments[side.ordinal()];
	}

	/**
	 * Checks to see if the provided attachment passes the redstone test.
	 * 
	 * @param attachment
	 * @return
	 */
	public boolean doesAttachmentPassRedstoneTest(ItemStack attachment) {
		AbstractCableAttachment attachmentItem = (AbstractCableAttachment) attachment.getItem();
		return RedstoneMode.evaluateRedstoneMode(attachmentItem.getRedstoneMode(attachment), getWorld(), getPos());
	}

	/**
	 * Gets the network this cable is currently connected to. NOTE: This is a server
	 * only method. Calling this on the client will throw an error.
	 * 
	 * @return
	 */
	public CableNetwork getNetwork() {
		return CableNetworkManager.get(getWorld()).isTrackingCable(getPos()) ? CableNetworkManager.get(getWorld()).getCable(getPos()).getNetwork() : null;
	}

	/**
	 * Gets the module from this network if present. We have to wrap it in an
	 * optional because while we can guarantee once this component is validated that
	 * the network is valid, since this component exposes external methods, other
	 * tile entity that are made valid before us may call some of our methods.
	 * 
	 * @param <T>        The type of the module.
	 * @param moduleType The resource location model type.
	 * @return
	 */
	public <T extends AbstractCableNetworkModule> Optional<T> getNetworkModule(ResourceLocation moduleType) {
		CableNetworkManager manager = CableNetworkManager.get(getTileEntity().getWorld());
		ServerCable cable = manager.getCable(getTileEntity().getPos());
		if (cable != null && cable.getNetwork() != null) {
			if (cable.getNetwork().hasModule(moduleType)) {
				return Optional.of(cable.getNetwork().getModule(moduleType));
			}
		}
		return Optional.empty();
	}

	public boolean areCableCompatible(AbstractCableProviderComponent otherProvider, Direction side) {
		if (otherProvider.hasAttachment(side) || this.hasAttachment(side.getOpposite())) {
			return false;
		}
		for (ResourceLocation moduleType : otherProvider.getSupportedNetworkModuleTypes()) {
			if (SupportedNetworkModules.contains(moduleType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public CompoundNBT serializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.serializeUpdateNbt(nbt, fromUpdate);

		// Serialize the disabled states.
		for (int i = 0; i < DisabledSides.length; i++) {
			nbt.putBoolean("disabledState" + i, DisabledSides[i]);
		}

		// Serialize the attachments.
		for (int i = 0; i < attachments.length; i++) {
			CompoundNBT itemNbt = new CompoundNBT();
			attachments[i].write(itemNbt);
			nbt.put("attachment" + i, itemNbt);
		}

		// Serialize the covers.
		for (int i = 0; i < covers.length; i++) {
			CompoundNBT itemNbt = new CompoundNBT();
			covers[i].write(itemNbt);
			nbt.put("cover" + i, itemNbt);
		}

		// Serialize the connection states.
		for (int i = 0; i < connectionStates.length; i++) {
			nbt.putInt("connection_state" + i, connectionStates[i].ordinal());
		}
		return nbt;
	}

	@Override
	public void deserializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.deserializeUpdateNbt(nbt, fromUpdate);

		// Deserialize the disabled states.
		for (int i = 0; i < DisabledSides.length; i++) {
			DisabledSides[i] = nbt.getBoolean("disabledState" + i);
		}

		// Deserialize the attachments.
		for (int i = 0; i < attachments.length; i++) {
			CompoundNBT itemNbt = nbt.getCompound("attachment" + i);
			attachments[i] = ItemStack.read(itemNbt);
		}

		// Deserialize the covers.
		for (int i = 0; i < covers.length; i++) {
			CompoundNBT itemNbt = nbt.getCompound("cover" + i);
			covers[i] = ItemStack.read(itemNbt);
		}

		// Deserialize the connection states.
		for (int i = 0; i < connectionStates.length; i++) {
			connectionStates[i] = CableConnectionState.values()[nbt.getInt("connection_state" + i)];
		}

		// If on the client, update the blocks.
		if (getWorld() != null && getWorld().isRemote) {
			getTileEntity().markTileEntityForSynchronization();
		}
	}

	protected void scanForAttachments() {
		// Do this synchronized (may cause a hicup, will have to watch and see. May need
		// to rethink this if that ends up being the case).
		synchronized (connectionStates) {
			for (Direction dir : Direction.values()) {
				if (!isSideDisabled(dir)) {
					BlockPos offsetPos = getPos().offset(dir);
					connectionStates[dir.ordinal()] = cacheConnectionState(dir, getWorld().getTileEntity(offsetPos), offsetPos);
				} else {
					connectionStates[dir.ordinal()] = CableConnectionState.NONE;
				}
			}
		}
	}

	protected ServerCable createCable() {
		return new ServerCable(getWorld(), getPos(), getSupportedNetworkModuleTypes());
	}

	protected void initializeCableProperties(ServerCable cable) {

	}

	protected ResourceLocation[] getAttachmentModels() {
		ResourceLocation[] output = new ResourceLocation[] { null, null, null, null, null, null };
		for (Direction dir : Direction.values()) {
			output[dir.ordinal()] = getAttachmentModelForSide(dir);
		}
		return output;
	}

	protected ResourceLocation getAttachmentModelForSide(Direction side) {
		if (!attachments[side.ordinal()].isEmpty()) {
			ItemStack attachmentItemStack = attachments[side.ordinal()];
			AbstractCableAttachment attachmentItem = (AbstractCableAttachment) attachmentItemStack.getItem();
			return attachmentItem.getModel(attachmentItemStack, this);
		}
		return null;
	}

	protected boolean canAttachAttachment(ItemStack attachment) {
		if (attachment.isEmpty()) {
			return false;
		}
		if (validAttachments.contains(attachment.getItem().getClass())) {
			return true;
		}
		return false;
	}

	protected abstract CableConnectionState cacheConnectionState(Direction side, @Nullable TileEntity te, BlockPos blockPosition);
}
