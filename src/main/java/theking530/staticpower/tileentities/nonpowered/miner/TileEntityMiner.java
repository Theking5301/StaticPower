package theking530.staticpower.tileentities.nonpowered.miner;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.ForgeHooks;
import theking530.api.IUpgradeItem.UpgradeType;
import theking530.staticcore.initialization.tileentity.TileEntityTypeAllocator;
import theking530.staticcore.initialization.tileentity.TileEntityTypePopulator;
import theking530.staticcore.utilities.SDMath;
import theking530.staticpower.StaticPowerConfig;
import theking530.staticpower.data.StaticPowerTier;
import theking530.staticpower.init.ModBlocks;
import theking530.staticpower.tileentities.components.control.AbstractProcesingComponent.ProcessingCheckState;
import theking530.staticpower.tileentities.components.control.MachineProcessingComponent;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;
import theking530.staticpower.tileentities.components.items.InputServoComponent;
import theking530.staticpower.tileentities.components.items.InventoryComponent;
import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent.UpgradeItemWrapper;

public class TileEntityMiner extends AbstractTileEntityMiner {
	@TileEntityTypePopulator()
	public static final TileEntityTypeAllocator<TileEntityMiner> TYPE = new TileEntityTypeAllocator<>((type) -> new TileEntityMiner(), ModBlocks.Miner);

	private static final int DEFAULT_FUEL_MOVE_TIME = 4;
	public final InventoryComponent fuelInventory;
	public final InventoryComponent fuelBurningInventory;
	public final MachineProcessingComponent fuelComponent;
	public final MachineProcessingComponent fuelMoveComponent;

	public TileEntityMiner() {
		super(TYPE);
		registerComponent(fuelInventory = new InventoryComponent("FuelInventory", 1, MachineSideMode.Input).setShiftClickEnabled(true));
		registerComponent(fuelBurningInventory = new InventoryComponent("FuelBurningInventory", 1, MachineSideMode.Never));
		registerComponent(fuelMoveComponent = new MachineProcessingComponent("FuelMoveComponent", DEFAULT_FUEL_MOVE_TIME, this::canMoveFuel, this::canMoveFuel, this::moveFuel, true)
				.setRedstoneControlComponent(redstoneControlComponent));
		registerComponent(
				fuelComponent = new MachineProcessingComponent("FuelComponent", 0, this::canStartProcessingFuel, this::canContinueProcessingFuel, this::fuelProcessingCompleted, true)
						.setRedstoneControlComponent(redstoneControlComponent));
		registerComponent(new InputServoComponent("FuelInputServo", 20, fuelInventory));
	}

	@Override
	public void process() {
		super.process();

		// Update the processing cost.
		fuelComponent.setTimeUnitsPerTick((int) processingComponent.getCalculatedPowerUsageMultipler());

		// Randomly generate smoke and flame particles.
		if (processingComponent.getIsOnBlockState()) {
			if (SDMath.diceRoll(0.25f)) {
				float randomOffset = (2 * RANDOM.nextFloat()) - 1.0f;
				randomOffset /= 3.5f;

				float forwardOffset = getFacingDirection().getAxisDirection() == AxisDirection.POSITIVE ? -1.05f : -0.05f;
				Vector3f forwardVector = SDMath.transformVectorByDirection(getFacingDirection(), new Vector3f(randomOffset + 0.5f, 0.32f, forwardOffset));
				getWorld().addParticle(ParticleTypes.SMOKE, getPos().getX() + forwardVector.getX(), getPos().getY() + forwardVector.getY(), getPos().getZ() + forwardVector.getZ(), 0.0f,
						0.01f, 0.0f);
				getWorld().addParticle(ParticleTypes.FLAME, getPos().getX() + forwardVector.getX(), getPos().getY() + forwardVector.getY(), getPos().getZ() + forwardVector.getZ(), 0.0f,
						0.01f, 0.0f);
			}
		}
	}

	public ProcessingCheckState canMoveFuel() {
		if (isValidFuel(fuelInventory.getStackInSlot(0)) && fuelBurningInventory.getStackInSlot(0).isEmpty() && hasDrillBit()) {
			return ProcessingCheckState.ok();
		}
		return ProcessingCheckState.skip();
	}

	public ProcessingCheckState moveFuel() {
		int burnTime = getFuelBurnTime(fuelInventory.getStackInSlot(0));
		fuelComponent.setMaxProcessingTime(burnTime);
		fuelComponent.setTimeUnitsPerTick((int) getFuelUsage());
		transferItemInternally(fuelInventory, 0, fuelBurningInventory, 0);
		return ProcessingCheckState.ok();
	}

	public ProcessingCheckState canStartProcessingFuel() {
		if (!isDoneMining() && isValidFuel(fuelInventory.getStackInSlot(0)) && hasDrillBit()) {
			return ProcessingCheckState.ok();
		}
		return ProcessingCheckState.error("Missing Fuel!");
	}

	public ProcessingCheckState canContinueProcessingFuel() {
		if (!isDoneMining()) {
			return ProcessingCheckState.ok();
		}
		return ProcessingCheckState.error(processingComponent.getProcessingErrorMessage());
	}

	public ProcessingCheckState fuelProcessingCompleted() {
		fuelComponent.setMaxProcessingTime(0);
		fuelBurningInventory.setStackInSlot(0, ItemStack.EMPTY);
		return ProcessingCheckState.ok();
	}

	/**
	 * Checks to make sure we can mine.
	 * 
	 * @return
	 */
	public ProcessingCheckState canProcess() {
		ProcessingCheckState superCall = super.canProcess();
		if (superCall.isOk()) {
			if (getRemainingFuel() == 0) {
				return ProcessingCheckState.error("Missing fuel!");
			}
		}
		return superCall;
	}

	@Override
	public void onBlockMined(BlockPos pos, BlockState minedBlock) {
		// IF we have reached the final block, set the current block index to -1.
		if (isDoneMining()) {
			fuelComponent.cancelProcessing();
		}
	}

	@Override
	public int getProcessingTime() {
		return StaticPowerConfig.SERVER.minerProcessingTime.get();
	}

	@Override
	public int getHeatGeneration() {
		return (int) (StaticPowerConfig.SERVER.minerHeatGeneration.get() * processingComponent.getCalculatedPowerUsageMultipler());
	}

	@Override
	public int getRadius() {
		// Get the range upgrade.
		UpgradeItemWrapper upgradeWrapper = upgradesInventory.getMaxTierItemForUpgradeType(UpgradeType.RANGE);

		// If there isn't one, return the base level.
		if (upgradeWrapper.isEmpty()) {
			return StaticPowerConfig.SERVER.minerRadius.get();
		}

		// Otherwise, caluclate the new range.
		StaticPowerTier tier = upgradeWrapper.getTier();
		double newRange = tier.rangeUpgrade.get() * StaticPowerConfig.SERVER.minerRadius.get();
		return (int) newRange;
	}

	@Override
	public long getFuelUsage() {
		return (long) (StaticPowerConfig.SERVER.minerFuelUsage.get() * processingComponent.getCalculatedPowerUsageMultipler());
	}

	public int getFuelBurnTime(ItemStack input) {
		return ForgeHooks.getBurnTime(input);
	}

	public boolean isValidFuel(ItemStack input) {
		return ForgeHooks.getBurnTime(input) > 0;
	}

	public int getRemainingFuel() {
		return fuelComponent.getMaxProcessingTime() - fuelComponent.getCurrentProcessingTime();
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
		return new ContainerMiner(windowId, inventory, this);
	}
}
