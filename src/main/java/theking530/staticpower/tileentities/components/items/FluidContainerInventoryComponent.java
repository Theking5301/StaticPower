package theking530.staticpower.tileentities.components.items;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import theking530.staticpower.tileentities.components.serialization.UpdateSerialize;
import theking530.staticpower.tileentities.utilities.interfaces.ItemStackHandlerFilter;
import theking530.staticpower.utilities.InventoryUtilities;

public class FluidContainerInventoryComponent extends InventoryComponent {
	public enum FluidContainerInteractionMode {
		/**
		 * Fills the provided container with the fluid in the fluid handler.
		 */
		FILL,
		/**
		 * Drains the provided container and fills the fluid handler with the drained
		 * fluid.
		 */
		DRAIN;

		public FluidContainerInteractionMode getInverseMode() {
			if (this == DRAIN) {
				return FILL;
			} else {
				return DRAIN;
			}
		}
	}

	public static final int DEFAULT_FLUID_TO_CONTAINER_RATE = 1000;

	private final IFluidHandler fluidHandler;
	private final int fluidToContainerRate;
	@UpdateSerialize
	private FluidContainerInteractionMode interactionMode;

	public FluidContainerInventoryComponent(String name, IFluidHandler fluidHandler) {
		super(name, 2);
		this.fluidHandler = fluidHandler;
		interactionMode = FluidContainerInteractionMode.DRAIN;
		fluidToContainerRate = DEFAULT_FLUID_TO_CONTAINER_RATE;
		setCapabilityExtractEnabled(false);
		setCapabilityInsertEnabled(false);
		setShiftClickEnabled(true);
		setShiftClickPriority(-1);
		setFilter(new ItemStackHandlerFilter() {
			public boolean canInsertItem(int slot, ItemStack stack) {
				return FluidUtil.getFluidHandler(stack).isPresent();
			}
		});
	}

	@Override
	public void preProcessUpdate() {
		// Do nothing if this component is not enabled.
		if (!isEnabled()) {
			return;
		}

		// Only do work on the server.
		if (getWorld().isRemote) {
			return;
		}

		// Get the impetus for the transaction.
		ItemStack primaryStack = getStackInSlot(0);

		// If it is empty, do nothing.
		if (primaryStack.isEmpty()) {
			return;
		}

		// Perform the operation.
		if (interactionMode == FluidContainerInteractionMode.DRAIN) {
			drainFromContainer(primaryStack);
		} else if (interactionMode == FluidContainerInteractionMode.FILL) {
			fillContainer(primaryStack);
		}
	}

	/**
	 * Fills the container from the owning fluid handler.
	 * 
	 * @param container
	 */
	protected void fillContainer(ItemStack container) {
		// If we can't fill this container, return early.
		if (!canFillContainer(container)) {
			return;
		}

		// Attempt to fill the container and capture the result.
		FluidActionResult result = FluidUtil.tryFillContainer(container, fluidHandler, fluidToContainerRate, null, true);

		// If the fill is successful, check to see what we should do with the remaining
		// container.
		if (result.isSuccess()) {
			// Play the sound.
			getWorld().playSound(null, getPos(), fluidHandler.getFluidInTank(0).getFluid() == Fluids.LAVA ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS,
					1.0f, 1.0f);

			// Get the fluid container handler.
			IFluidHandler containerHandler = FluidUtil.getFluidHandler(container).orElse(null);
			// If it's not null, check to see if its full.
			if (containerHandler != null) {
				if (containerHandler.getFluidInTank(0).isEmpty()) {
					// If it is, attempt to insert it into the secondary slot.
					if (InventoryUtilities.canFullyInsertStackIntoSlot(this, 1, result.getResult())) {
						// Perform the insert.
						ItemStack insertedItem = insertItem(1, result.getResult(), false);
						// If successfully, extract the item from the primary alot.
						if (insertedItem.isEmpty()) {
							extractItem(0, 1, false);
						}
					}
				}
			}

			// Sync the tile entity.
			getTileEntity().markTileEntityForSynchronization();
		}
	}

	/**
	 * Drains fluid from the container into the owning fluid handler.
	 * 
	 * @param container
	 */
	protected void drainFromContainer(ItemStack container) {
		// If we can't drain from this container, return early.
		if (!canDrainFromContainer(container)) {
			return;
		}

		// Attempt to drain the container and capture the result.
		FluidActionResult result = FluidUtil.tryEmptyContainer(container, fluidHandler, fluidToContainerRate, null, true);

		// If the drain is successful, check to see what we should do with the remaining
		// container.
		if (result.isSuccess()) {
			// Play the sound.
			getWorld().playSound(null, getPos(), fluidHandler.getFluidInTank(0).getFluid() == Fluids.LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS,
					1.0f, 1.0f);
			// Get the fluid container handler.
			IFluidHandler containerHandler = FluidUtil.getFluidHandler(result.getResult()).orElse(null);
			// If it's not null, check to see if its empty.
			if (containerHandler != null) {
				if (containerHandler.getFluidInTank(0).isEmpty()) {
					// If it is, attempt to insert it into the secondary slot.
					if (InventoryUtilities.canFullyInsertStackIntoSlot(this, 1, result.getResult())) {
						// Perform the insert.
						ItemStack insertedItem = insertItem(1, result.getResult(), false);
						// If successfully, extract the item from the primary alot.
						if (insertedItem.isEmpty()) {
							extractItem(0, 1, false);
						}
					}
				}
			}
			// Sync the tile entity.
			getTileEntity().markTileEntityForSynchronization();
		}
	}

	/**
	 * Checks to see if we can fill the container from this fluid handler.
	 * 
	 * @param container
	 * @return
	 */
	protected boolean canFillContainer(ItemStack container) {
		// Simulate to drain the container and capture the result.
		FluidTank simulatedTank = new FluidTank(fluidHandler.getTankCapacity(0));
		simulatedTank.fill(fluidHandler.getFluidInTank(0), FluidAction.EXECUTE);
		FluidActionResult result = FluidUtil.tryFillContainer(container, simulatedTank, fluidToContainerRate, null, false);

		// If the drain is successful, check to see what we should do with the remaining
		// container.
		if (result.isSuccess()) {
			// Get the fluid container handler.
			IFluidHandler containerHandler = FluidUtil.getFluidHandler(result.getResult()).orElse(null);
			// If it's not null, check to see if its empty.
			if (containerHandler != null && containerHandler.getTanks() > 0 && FluidUtil.getFluidContained(result.getResult()).orElse(null).getAmount() == containerHandler.getTankCapacity(0)) {
				// If it is, return if we can insert into the secondary slot.
				return InventoryUtilities.canFullyInsertStackIntoSlot(this, 1, result.getResult());
			}
			return InventoryUtilities.canFullyInsertStackIntoSlot(this, 1, result.getResult());
		}
		return false;
	}

	/**
	 * Checks to see if we can drain from the container into this fluid handler.
	 * 
	 * @param container
	 * @return
	 */
	protected boolean canDrainFromContainer(ItemStack container) {
		// Simulate to drain the container and capture the result.
		FluidTank simulatedTank = new FluidTank(fluidHandler.getTankCapacity(0));
		simulatedTank.fill(fluidHandler.getFluidInTank(0), FluidAction.EXECUTE);
		FluidActionResult result = FluidUtil.tryEmptyContainer(container, simulatedTank, fluidToContainerRate, null, false);

		// If the drain is successful, check to see what we should do with the remaining
		// container.
		if (result.isSuccess()) {
			// Replace the fluid since this is just a simulation.
			// Get the fluid container handler.
			IFluidHandler containerHandler = FluidUtil.getFluidHandler(result.getResult()).orElse(null);
			// If it's not null, check to see if its empty.
			if (containerHandler != null && containerHandler.getTanks() > 0) {
				// If it is, return if we can insert into the secondary slot.
				if (containerHandler.getFluidInTank(0).isEmpty()) {
					return InventoryUtilities.canFullyInsertStackIntoSlot(this, 1, result.getResult());
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the new fill/drain mode.
	 * 
	 * @param newMode
	 * @return
	 */
	public FluidContainerInventoryComponent setMode(FluidContainerInteractionMode newMode) {
		interactionMode = newMode;
		return this;
	}

	/**
	 * Gets the current fill or drain mode of the fluid container handler.
	 * 
	 * @return
	 */
	public FluidContainerInteractionMode getFluidInteractionMode() {
		return interactionMode;
	}
}