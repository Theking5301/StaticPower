package theking530.staticpower.tileentities.nonpowered.miner;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;
import theking530.staticcore.initialization.tileentity.TileEntityTypeAllocator;
import theking530.staticcore.utilities.Color;
import theking530.staticcore.utilities.SDMath;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.rendering.CustomRenderer;
import theking530.staticpower.client.utilities.GuiTextUtilities;
import theking530.staticpower.items.tools.miningdrill.DrillBit;
import theking530.staticpower.tileentities.TileEntityConfigurable;
import theking530.staticpower.tileentities.components.control.AbstractProcesingComponent.ProcessingCheckState;
import theking530.staticpower.tileentities.components.control.MachineProcessingComponent;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;
import theking530.staticpower.tileentities.components.heat.HeatStorageComponent;
import theking530.staticpower.tileentities.components.heat.HeatStorageComponent.HeatManipulationAction;
import theking530.staticpower.tileentities.components.items.InventoryComponent;
import theking530.staticpower.tileentities.components.items.InventoryComponent.InventoryChangeType;
import theking530.staticpower.tileentities.components.items.ItemStackHandlerFilter;
import theking530.staticpower.tileentities.components.items.OutputServoComponent;
import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent;
import theking530.staticpower.tileentities.components.loopingsound.LoopingSoundComponent;
import theking530.staticpower.utilities.InventoryUtilities;
import theking530.staticpower.utilities.WorldUtilities;

public abstract class AbstractTileEntityMiner extends TileEntityConfigurable {
	public final InventoryComponent drillBitInventory;
	public final InventoryComponent outputInventory;
	public final InventoryComponent internalInventory;
	public final UpgradeInventoryComponent upgradesInventory;

	public final MachineProcessingComponent processingComponent;
	public final HeatStorageComponent heatStorage;
	public final LoopingSoundComponent miningSoundComponent;

	private boolean shouldDrawRadiusPreview;
	private final List<BlockPos> blocks;
	private int currentBlockIndex;

	public AbstractTileEntityMiner(TileEntityTypeAllocator<? extends AbstractTileEntityMiner> allocator) {
		super(allocator);
		disableFaceInteraction();
		blocks = new ArrayList<BlockPos>();

		registerComponent(outputInventory = new InventoryComponent("OutputInventory", 1, MachineSideMode.Output));
		registerComponent(drillBitInventory = new InventoryComponent("DrillBitInventory", 1, MachineSideMode.Never).setShiftClickEnabled(true).setFilter(new ItemStackHandlerFilter() {
			public boolean canInsertItem(int slot, ItemStack stack) {
				return stack.getItem() instanceof DrillBit;
			}
		}));

		registerComponent(internalInventory = new InventoryComponent("InternalInventory", 64, MachineSideMode.Never));
		registerComponent(upgradesInventory = (UpgradeInventoryComponent) new UpgradeInventoryComponent("UpgradeInventory", 3).setModifiedCallback(this::upgradeInventoryChanged));

		registerComponent(
				processingComponent = new MachineProcessingComponent("ProcessingComponent", getProcessingTime(), this::canProcess, this::canProcess, this::processingCompleted, true));
		processingComponent.setShouldControlBlockState(true);
		processingComponent.setRedstoneControlComponent(redstoneControlComponent);
		processingComponent.setProcessingPowerUsage(getFuelUsage());
		processingComponent.setUpgradeInventory(upgradesInventory);

		registerComponent(miningSoundComponent = new LoopingSoundComponent("MiningSoundComponent", 20));

		// Add the heat storage and the upgrade inventory to the heat component.
		registerComponent(
				heatStorage = new HeatStorageComponent("HeatStorageComponent", 350.0f, 1.0f).setCapabiltiyFilter((amount, direction, action) -> action == HeatManipulationAction.COOL));
		heatStorage.setUpgradeInventory(upgradesInventory);

		registerComponent(new OutputServoComponent("OutputServo", 1, outputInventory));
		heatStorage.getStorage().setCanHeat(false);
	}

	@Override
	public void process() {
		// If there are no blocks, refresh them.
		if (this.blocks.size() == 0) {
			refreshBlocksInRange(getRadius());
		}

		if (!world.isRemote) {
			// If the internal inventory is not empty, try to put the items sequentially
			// into the output slot.
			if (!InventoryUtilities.isInventoryEmpty(internalInventory)) {
				for (int i = 0; i < internalInventory.getSlots(); i++) {
					ItemStack stackInSlot = internalInventory.getStackInSlot(i);
					ItemStack remaining = InventoryUtilities.insertItemIntoInventory(outputInventory, stackInSlot, false);
					if (remaining.getCount() != stackInSlot.getCount()) {
						internalInventory.extractItem(i, stackInSlot.getCount() - remaining.getCount(), false);
					}
				}
			}

			if (processingComponent.isPerformingWork()) {
				heatStorage.getStorage().setCanHeat(true);
				heatStorage.getStorage().heat(getHeatGeneration(), false);
				heatStorage.getStorage().setCanHeat(false);
			}

			if (processingComponent.getIsOnBlockState()) {
				miningSoundComponent.startPlayingSound(SoundEvents.ENTITY_MINECART_RIDING.getRegistryName(), SoundCategory.BLOCKS, 0.2f, 0.5f, getPos(), 64);
			} else {
				miningSoundComponent.stopPlayingSound();
			}
		} else {
			if (processingComponent.isPerformingWork()) {
				if (SDMath.diceRoll(0.5)) {
					BlockPos minedPos = getCurrentlyTargetedBlockPos();
					getWorld().addParticle(ParticleTypes.POOF, minedPos.getX() + 0.5f, minedPos.getY() + 0.5f, minedPos.getZ() + 0.5f, 0.0f, 0.01f, 0.0f);
				}
			}
		}
	}

	public abstract int getProcessingTime();

	public abstract int getHeatGeneration();

	public abstract int getRadius();

	public abstract long getFuelUsage();

	public boolean isDoneMining() {
		return currentBlockIndex == -1;
	}

	public int getBlocksRemaining() {
		return blocks.size() - (currentBlockIndex + 1);
	}

	public int getTicksRemainingUntilCompletion() {
		return getBlocksRemaining() * processingComponent.getMaxProcessingTime() - processingComponent.getCurrentProcessingTime();
	}

	/**
	 * Upgrade handler.
	 * 
	 * @param type
	 * @param stack
	 * @param slot
	 */
	protected void upgradeInventoryChanged(InventoryChangeType type, ItemStack stack, Integer slot) {
		refreshBlocksInRange(getRadius());
	}

	/**
	 * Checks to make sure we can mine.
	 * 
	 * @return
	 */
	public ProcessingCheckState canProcess() {
		// If we're done processing, stop.
		if (isDoneMining()) {
			return ProcessingCheckState.skip();
		}
		if (!InventoryUtilities.isInventoryEmpty(internalInventory)) {
			return ProcessingCheckState.error("Items backed up in internal inventory.");
		}
		if (!hasDrillBit()) {
			return ProcessingCheckState.error("Missing drill bit.");
		}
		if (getDrillBit().getDamage() >= getDrillBit().getMaxDamage()) {
			return ProcessingCheckState.error("Drill bit needs repair!");
		}
		if (!heatStorage.getStorage().canFullyAbsorbHeat(getHeatGeneration())) {
			return ProcessingCheckState.error("Not enough heat capacity (Requires " + GuiTextUtilities.formatHeatToString(getHeatGeneration()).getString() + ")");
		}
		return ProcessingCheckState.ok();
	}

	/**
	 * Once the processing is completed, place the output in the output slot (if
	 * possible). If not, return false. This method will continue to be called until
	 * true is returned.
	 * 
	 * @return
	 */
	protected ProcessingCheckState processingCompleted() {
		if (InventoryUtilities.isInventoryEmpty(internalInventory) && hasDrillBit()) {
			// Safety check. If we are out of range return true.
			if (currentBlockIndex >= blocks.size()) {
				// IF the blocks array has already been initialized, set the current block index
				// to -1.
				if (blocks.size() > 0) {
					currentBlockIndex = -1;
				}
				return ProcessingCheckState.ok();
			}

			// Done!
			if (currentBlockIndex == -1) {
				return ProcessingCheckState.error("Processing Complete!");
			}

			// We need to perform this here too, otherwise we'll skip a tick per generation.
			heatStorage.getStorage().setCanHeat(true);
			heatStorage.getStorage().heat(getHeatGeneration(), false);
			heatStorage.getStorage().setCanHeat(false);

			// Get the block to mine.
			BlockPos minedPos = blocks.get(currentBlockIndex);
			BlockState minedBlockState = world.getBlockState(minedPos);

			// Increment the current block index.
			currentBlockIndex++;
			// IF we have reached the final block, set the current block index to -1.
			if (currentBlockIndex >= blocks.size()) {
				processingComponent.cancelProcessing();
				currentBlockIndex = -1;
			}

			// Skip air blocks.
			if (minedBlockState.getBlock() == Blocks.AIR) {
				return ProcessingCheckState.ok();
			}

			// Play the sound.
			world.playSound(null, getPos(), minedBlockState.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 0.2f, 0.75f);
			world.playSound(null, getCurrentlyTargetedBlockPos(), minedBlockState.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 0.2f, 0.75f);

			// Damage the drill bit.
			if (getDrillBit().attemptDamageItem(1, world.rand, null)) {
				world.playSound(null, getPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			}

			// Check if this is a mineable block. If not, just return true.
			if (!canMineBlock(minedBlockState, minedPos)) {
				return ProcessingCheckState.ok();
			}

			// Insert the mined items into the internal inventory.
			List<ItemStack> minedItems = attemptMineBlock(minedPos);
			for (int i = 0; i < minedItems.size(); i++) {
				InventoryUtilities.insertItemIntoInventory(internalInventory, minedItems.get(i), false);
			}

			// Set the mined block to cobblestone.
			world.setBlockState(minedPos, Blocks.AIR.getDefaultState(), 1 | 2 | 4);

			// Raise the on mined event.
			onBlockMined(minedPos, minedBlockState);

			// Mark the te as dirty.
			markTileEntityForSynchronization();
			return ProcessingCheckState.ok();
		}
		return ProcessingCheckState.error("Items backed up in internal inventory.");
	}

	public void onBlockMined(BlockPos pos, BlockState minedBlock) {

	}

	public boolean getShouldDrawRadiusPreview() {
		return shouldDrawRadiusPreview;
	}

	public void setShouldDrawRadiusPreview(boolean shouldDraw) {
		if (shouldDraw) {
			// If we were already drawing, remove and re-do it.
			if (shouldDrawRadiusPreview) {
				CustomRenderer.removeCubeRenderer(getTileEntity(), "range");
			}

			// Get the radius.
			int radius = getRadius();

			// Set the scale equal to the range * 2 plus 1.
			Vector3f scale = new Vector3f((radius * 2) + 1, getPos().getY() - 0.98f, (radius * 2) + 1);
			// Shift over so we center the range around the farmer.
			Vector3f position = new Vector3f(getTileEntity().getPos().getX(), 1.0f, getTileEntity().getPos().getZ());
			position.add(new Vector3f(-radius, 0.0f, -radius));

			// Add the entry.
			CustomRenderer.addCubeRenderer(getTileEntity(), "range", position, scale, new Color(1.0f, 0.1f, 0.2f, 0.25f));
		} else {
			// Remove the entry.
			CustomRenderer.removeCubeRenderer(getTileEntity(), "range");
		}

		// Update the drawing value.
		shouldDrawRadiusPreview = shouldDraw;
	}

	protected List<ItemStack> attemptMineBlock(BlockPos pos) {
		List<ItemStack> output = WorldUtilities.getBlockDrops(getWorld(), pos);
		return output;
	}

	protected void refreshBlocksInRange(int range) {
		blocks.clear();
		currentBlockIndex = 0;

		for (int i = getPos().getY() - 1; i >= 2; i--) {
			List<BlockPos> tempList = new ArrayList<BlockPos>();
			BlockPos startingPos = new BlockPos(getPos().getX(), i, getPos().getZ());
			for (BlockPos pos : BlockPos.getAllInBoxMutable(startingPos.add(range, 0, range), startingPos.add(-range, 0, -range))) {
				// If the position is on the y level under the miner, and it exists on the same
				// X or Y plane, skip it.
				if (pos.getY() == this.getPos().getY() - 1 && (pos.getX() == getPos().getX() || pos.getZ() == getPos().getZ())) {
					continue;
				}

				// If the position is NOT the mining position (just in case), add it.
				if (pos != getPos()) {
					tempList.add(pos.toImmutable());
				}
			}
			blocks.addAll(tempList);
		}

		// Forward to the first actual block.
		boolean minableBlockFound = false;
		for (int i = 0; i < blocks.size(); i++) {
			BlockState test = world.getBlockState(blocks.get(i));
			if (canMineBlock(test, blocks.get(i))) {
				currentBlockIndex = i;
				minableBlockFound = true;
				break;
			}
		}

		// If we are currently on a block that is out of range, or no minable blocks
		// were found, set the block index to
		// -1 as we are already done.
		if (!minableBlockFound || currentBlockIndex >= blocks.size() - 1) {
			currentBlockIndex = -1;
		}
	}

	public boolean hasDrillBit() {
		return !drillBitInventory.getStackInSlot(0).isEmpty();
	}

	public ItemStack getDrillBit() {
		return drillBitInventory.getStackInSlot(0);
	}

	protected boolean canMineBlock(BlockState block, BlockPos pos) {
		if (block.getBlock().isAir(block, getWorld(), pos)) {
			return false;
		}
		if (block.hasTileEntity()) {
			return false;
		}
		if (!block.getFluidState().isEmpty()) {
			return false;
		}
		return block.getBlockHardness(getWorld(), pos) >= 0;
	}

	public BlockPos getCurrentlyTargetedBlockPos() {
		if (currentBlockIndex == -1 || currentBlockIndex >= blocks.size()) {
			StaticPower.LOGGER.warn(String.format("Attempting to render an invalid current block index: %1$s.", currentBlockIndex));
			return new BlockPos(0, 0, 0);
		}

		return blocks.size() > 0 ? blocks.get(currentBlockIndex) : new BlockPos(0, 0, 0);
	}

	@Override
	public void deserializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.deserializeUpdateNbt(nbt, fromUpdate);
		currentBlockIndex = nbt.getInt("current_index");
	}

	@Override
	public CompoundNBT serializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.serializeUpdateNbt(nbt, fromUpdate);
		nbt.putInt("current_index", currentBlockIndex);

		return nbt;
	}

	@Override
	public void deserializeSaveNbt(CompoundNBT nbt) {
		super.deserializeSaveNbt(nbt);

		// Load the blocks for mining.
		blocks.clear();
		ListNBT savedBlocks = nbt.getList("blocks", Constants.NBT.TAG_COMPOUND);
		for (INBT blockTag : savedBlocks) {
			CompoundNBT blockTagCompound = (CompoundNBT) blockTag;
			blocks.add(BlockPos.fromLong(blockTagCompound.getLong("pos")));
		}
	}

	@Override
	public CompoundNBT serializeSaveNbt(CompoundNBT nbt) {
		super.serializeSaveNbt(nbt);

		// Save the blocks marked for mining.
		ListNBT savedBlocks = new ListNBT();
		blocks.forEach(block -> {
			CompoundNBT blockTag = new CompoundNBT();
			blockTag.putLong("pos", block.toLong());
			savedBlocks.add(blockTag);
		});
		nbt.put("blocks", savedBlocks);

		return nbt;
	}
}
