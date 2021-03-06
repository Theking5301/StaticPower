package theking530.staticpower.tileentities.components.control;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent;
import theking530.staticpower.tileentities.components.power.EnergyStorageComponent;

public class MachineProcessingComponent extends AbstractProcesingComponent {
	public static final int DEFAULT_MOVING_TIME = 4;

	protected Supplier<ProcessingCheckState> canStartProcessingCallback;
	protected Supplier<ProcessingCheckState> canContinueProcessingCallback;
	protected Supplier<ProcessingCheckState> processingEndedCallback;

	private Runnable processingStartedCallback;

	public MachineProcessingComponent(String name, int processingTime, @Nonnull Supplier<ProcessingCheckState> canStartProcessingCallback,
			@Nonnull Supplier<ProcessingCheckState> canContinueProcessingCallback, @Nonnull Supplier<ProcessingCheckState> processingEndedCallback, boolean serverOnly) {
		super(name, processingTime, serverOnly);
		this.canStartProcessingCallback = canStartProcessingCallback;
		this.canContinueProcessingCallback = canContinueProcessingCallback;
		this.processingEndedCallback = processingEndedCallback;
	}

	public MachineProcessingComponent(String name, int processingTime, @Nonnull Supplier<ProcessingCheckState> processingEndedCallback, boolean serverOnly) {
		this(name, processingTime, () -> ProcessingCheckState.error(""), () -> ProcessingCheckState.ok(), processingEndedCallback, serverOnly);
	}

	public static MachineProcessingComponent createMovingProcessingComponent(String name, @Nonnull Supplier<ProcessingCheckState> canStartProcessingCallback,
			@Nonnull Supplier<ProcessingCheckState> canContinueProcessingCallback, @Nonnull Supplier<ProcessingCheckState> processingEndedCallback, boolean serverOnly) {
		return new MachineProcessingComponent(name, DEFAULT_MOVING_TIME, processingEndedCallback, processingEndedCallback, processingEndedCallback, serverOnly);
	}

	protected ProcessingCheckState canStartProcessing() {
		// Check the redstone state.
		ProcessingCheckState redstoneState;
		if (!(redstoneState = checkRedstoneState()).isOk()) {
			return redstoneState;
		}

		// Check the power state.
		ProcessingCheckState powerState;
		if (!(powerState = checkPowerRequirements()).isOk()) {
			return powerState;
		}

		// If the above are met, return the callback's result.
		return canStartProcessingCallback.get();
	}

	protected ProcessingCheckState canContinueProcessing() {
		// Check the parent state.
		ProcessingCheckState superCall = super.canContinueProcessing();
		if (!superCall.isOk()) {
			return superCall;
		}

		// If the above are met, return the callback's result.
		return canContinueProcessingCallback.get();
	}

	protected ProcessingCheckState canCompleteProcessing() {
		// Check the parent state.
		ProcessingCheckState superCall = super.canCompleteProcessing();
		if (!superCall.isOk()) {
			return superCall;
		}

		// If the above are met, return the callback's result.
		return processingEndedCallback.get();
	}

	@Override
	protected void onProcessingStarted() {
		if (processingStartedCallback != null) {
			processingStartedCallback.run();
		}
	}

	@Override
	public MachineProcessingComponent setUpgradeInventory(UpgradeInventoryComponent inventory) {
		return (MachineProcessingComponent) super.setUpgradeInventory(inventory);
	}

	@Override
	public MachineProcessingComponent setEnergyComponent(EnergyStorageComponent energyComponent) {
		return (MachineProcessingComponent) super.setEnergyComponent(energyComponent);
	}

	@Override
	public MachineProcessingComponent setProcessingPowerUsage(long power) {
		return (MachineProcessingComponent) super.setProcessingPowerUsage(power);
	}

	@Override
	public MachineProcessingComponent setCompletedPowerUsage(long power) {
		return (MachineProcessingComponent) super.setCompletedPowerUsage(power);
	}

	@Override
	public MachineProcessingComponent setPowerUsageMuiltiplier(float multiplier) {
		return (MachineProcessingComponent) super.setPowerUsageMuiltiplier(multiplier);
	}

	@Override
	public MachineProcessingComponent disableProcessingPowerUsage() {
		return (MachineProcessingComponent) super.disableProcessingPowerUsage();
	}

	@Override
	public MachineProcessingComponent disableCompletedPowerUsage() {
		return (MachineProcessingComponent) super.disableCompletedPowerUsage();
	}

	@Override
	public MachineProcessingComponent setRedstoneControlComponent(RedstoneControlComponent redstoneControlComponent) {
		return (MachineProcessingComponent) super.setRedstoneControlComponent(redstoneControlComponent);
	}

	@Override
	public MachineProcessingComponent setShouldControlBlockState(boolean shouldControl) {
		return (MachineProcessingComponent) super.setShouldControlBlockState(shouldControl);
	}

	public MachineProcessingComponent setProcessingStartedCallback(Runnable processingStartedCallback) {
		this.processingStartedCallback = processingStartedCallback;
		return this;
	}
}
