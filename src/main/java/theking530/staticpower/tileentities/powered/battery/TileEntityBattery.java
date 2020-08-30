package theking530.staticpower.tileentities.powered.battery;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.data.StaticPowerDataRegistry;
import theking530.staticpower.tileentities.TileEntityMachine;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;
import theking530.staticpower.tileentities.components.control.sideconfiguration.SideConfigurationUtilities.BlockSide;
import theking530.staticpower.tileentities.components.power.PowerDistributionComponent;
import theking530.staticpower.tileentities.components.power.EnergyStorageComponent.EnergyManipulationAction;

public class TileEntityBattery extends TileEntityMachine {

	private int minPowerThreshold;
	private int maxPowerThreshold;

	private int maxPowerIO;

	private int inputRFTick;
	private int outputRFTick;

	protected PowerDistributionComponent powerDistributor;

	public TileEntityBattery(TileEntityType<?> type, ResourceLocation tier) {
		super(type);
		DisableFaceInteraction = false;

		registerComponent(powerDistributor = new PowerDistributionComponent("PowerDistributor", energyStorage.getStorage()));
		energyStorage.setCapabiltiyFilter((amount, direction, action) -> {
			if (direction == null) {
				return false;
			}
			if (action == EnergyManipulationAction.PROVIDE && this.ioSideConfiguration.getWorldSpaceDirectionConfiguration(direction) != MachineSideMode.Output) {
				return false;
			}
			if (action == EnergyManipulationAction.RECIEVE && this.ioSideConfiguration.getWorldSpaceDirectionConfiguration(direction) != MachineSideMode.Input) {
				return false;
			}
			if (this.ioSideConfiguration.getWorldSpaceDirectionConfiguration(direction) == MachineSideMode.Disabled) {
				return false;
			}
			return true;
		});

		energyStorage.getStorage().setCapacity(StaticPowerDataRegistry.getTier(tier).getBatteryCapacity());
		inputRFTick = StaticPowerDataRegistry.getTier(tier).getDefaultMachinePowerOutput() * 2;
		outputRFTick = StaticPowerDataRegistry.getTier(tier).getDefaultMachinePowerOutput() * 2;
		maxPowerIO = inputRFTick * 2;

		energyStorage.getStorage().setMaxReceive(inputRFTick);
		energyStorage.getStorage().setMaxExtract(outputRFTick);
	}

	public void process() {
		super.process();
	}

	public void setMinimumPowerThreshold(int newThreshold) {
		minPowerThreshold = newThreshold;
	}

	public void setMaximumPowerThreshold(int newThreshold) {
		maxPowerThreshold = newThreshold;
	}

	public int getMinimumPowerThreshold() {
		return minPowerThreshold;
	}

	public int getMaximumPowerThreshold() {
		return maxPowerThreshold;
	}

	public int getInputLimit() {
		return inputRFTick;
	}

	public int getOutputLimit() {
		return outputRFTick;
	}

	public void setInputLimit(int newLimit) {
		inputRFTick = newLimit;
		energyStorage.getStorage().setMaxReceive(newLimit);
	}

	public void setOutputLimit(int newLimit) {
		outputRFTick = newLimit;
		energyStorage.getStorage().setMaxExtract(newLimit);
	}

	public void setMaximumPowerIO(int newMaxIO) {
		maxPowerIO = newMaxIO;
		setInputLimit(maxPowerIO);
		setOutputLimit(maxPowerIO);
	}

	public int getMaximumPowerIO() {
		return maxPowerIO;
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
		return new ContainerBattery(windowId, inventory, this);
	}

	@Override
	protected boolean isValidSideConfiguration(BlockSide side, MachineSideMode mode) {
		return mode == MachineSideMode.Disabled || mode == MachineSideMode.Output || mode == MachineSideMode.Input;
	}

	@Override
	public void deserializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.deserializeUpdateNbt(nbt, fromUpdate);

		minPowerThreshold = nbt.getInt("min_power_threshold");
		maxPowerThreshold = nbt.getInt("max_power_threshold");

		inputRFTick = nbt.getInt("input_limit");
		outputRFTick = nbt.getInt("output_limit");
	}

	@Override
	public CompoundNBT serializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.serializeUpdateNbt(nbt, fromUpdate);

		nbt.putInt("min_power_threshold", minPowerThreshold);
		nbt.putInt("max_power_threshold", maxPowerThreshold);

		nbt.putInt("input_limit", inputRFTick);
		nbt.putInt("output_limit", outputRFTick);
		return nbt;
	}

	// Tab Integration
	public boolean shouldOutputRedstoneSignal() {
		if (minPowerThreshold == 0 && maxPowerThreshold == 0) {
			return false;
		}
		float storedPercentage = energyStorage.getStorage().getStoredEnergyPercentScaled(100.0f);
		if (storedPercentage >= this.minPowerThreshold && storedPercentage <= this.maxPowerThreshold) {
			return true;
		}
		return false;
	}
}
