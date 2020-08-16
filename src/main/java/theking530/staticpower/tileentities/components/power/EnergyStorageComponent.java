package theking530.staticpower.tileentities.components.power;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import theking530.common.utilities.TriFunction;
import theking530.staticpower.energy.StaticPowerFEStorage;
import theking530.staticpower.items.upgrades.IUpgradeItem.UpgradeType;
import theking530.staticpower.network.StaticPowerMessageHandler;
import theking530.staticpower.tileentities.components.AbstractTileEntityComponent;
import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent;
import theking530.staticpower.tileentities.components.items.UpgradeInventoryComponent.UpgradeItemWrapper;

public class EnergyStorageComponent extends AbstractTileEntityComponent {
	public enum EnergyManipulationAction {
		PROVIDE, RECIEVE
	}

	public static final int ENERGY_SYNC_MAX_DELTA = 100;
	protected final StaticPowerFEStorage EnergyStorage;
	protected TriFunction<Integer, Direction, EnergyManipulationAction, Boolean> filter;
	private int lastSyncEnergy;

	private UpgradeInventoryComponent upgradeInventory;
	private float powerCapacityUpgradeMultiplier;
	private float powerIOUpgradeMultiplier;
	private int defaultCapacity;
	private int defaultMaxInput;
	private int defaultMaxOutput;

	private EnergyComponentCapabilityAccess capabilityAccessor;

	public EnergyStorageComponent(String name, int capacity) {
		this(name, capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public EnergyStorageComponent(String name, int capacity, int maxInput) {
		this(name, capacity, maxInput, 0);
	}

	public EnergyStorageComponent(String name, int capacity, int maxInput, int maxExtract) {
		super(name);
		EnergyStorage = new StaticPowerFEStorage(capacity, maxInput, maxExtract);
		capabilityAccessor = new EnergyComponentCapabilityAccess();
		defaultCapacity = capacity;
		powerCapacityUpgradeMultiplier = 1.0f;
		powerIOUpgradeMultiplier = 1.0f;
		defaultMaxInput = maxInput;
		defaultMaxOutput = maxExtract;
	}

	@Override
	public void preProcessUpdate() {
		if (!getWorld().isRemote) {
			// Check for upgrades.
			checkUpgrades();
		}
	}

	@Override
	public void postProcessUpdate() {
		if (!getWorld().isRemote) {
			// Get the current delta between the amount of power we have and the power we
			// had last tick.
			int delta = Math.abs(EnergyStorage.getEnergyStored() - lastSyncEnergy);

			// Determine if we should sync.
			boolean shouldSync = delta > ENERGY_SYNC_MAX_DELTA;
			shouldSync |= EnergyStorage.getEnergyStored() == 0 && lastSyncEnergy != 0;
			shouldSync |= EnergyStorage.getEnergyStored() == EnergyStorage.getMaxEnergyStored() && lastSyncEnergy != EnergyStorage.getMaxEnergyStored();

			// If we should sync, perform the sync.
			if (shouldSync) {
				lastSyncEnergy = EnergyStorage.getEnergyStored();
				syncToClient();
			}
			EnergyStorage.captureEnergyMetric();
		}
	}

	public EnergyStorageComponent setUpgradeInventory(UpgradeInventoryComponent inventory) {
		upgradeInventory = inventory;
		return this;
	}

	protected void checkUpgrades() {
		// Do nothing if there is no upgrade inventory.
		if (upgradeInventory == null) {
			return;
		}
		// Get the upgrade.
		UpgradeItemWrapper upgrade = upgradeInventory.getMaxTierItemForUpgradeType(UpgradeType.POWER);

		// If it is not valid, set the values back to the defaults. Otherwise, set the
		// new processing speeds.
		if (upgrade.isEmpty()) {
			powerCapacityUpgradeMultiplier = 1.0f;
			powerIOUpgradeMultiplier = 1.0f;
		} else {
			powerCapacityUpgradeMultiplier = upgrade.getTier().getPowerUpgrade() * upgrade.getUpgradeWeight();
			powerCapacityUpgradeMultiplier = upgrade.getTier().getPowerIoUpgrade() * upgrade.getUpgradeWeight();
		}

		// Set the new values.
		getStorage().setCapacity((int) (defaultCapacity * powerCapacityUpgradeMultiplier));
		getStorage().setMaxExtract((int) (defaultMaxOutput * powerIOUpgradeMultiplier));
		getStorage().setMaxReceive((int) (defaultMaxInput * powerIOUpgradeMultiplier));
	}

	/**
	 * Gets the raw energy storage object.
	 * 
	 * @return
	 */
	public StaticPowerFEStorage getStorage() {
		return EnergyStorage;
	}

	/**
	 * Returns true if this energy component has >= the amount of power that was
	 * passed.
	 * 
	 * @param power
	 * @return
	 */
	public boolean hasEnoughPower(int power) {
		return EnergyStorage.getEnergyStored() >= power;
	}

	/**
	 * If this storage component contains at least the provided amount of power, it
	 * will drain that amount and return true. Otherwise, it will do nothing and
	 * return false.
	 * 
	 * @param power The amount of power to drain.
	 * @return True if the provided amount of power was drained, false otherwise.
	 */
	public boolean usePower(int power) {
		if (hasEnoughPower(power)) {
			getStorage().extractEnergy(power, false);
			return true;
		}
		return false;
	}

	/**
	 * Returns true if this energy component can fully accept the amount passed.
	 * 
	 * @param power The amount of power test this component for.
	 * @return
	 */
	public boolean canAcceptPower(int power) {
		return EnergyStorage.getEnergyStored() + power <= EnergyStorage.getMaxEnergyStored();
	}

	/**
	 * If this storage can fully receive the amount of power passed, it will receive
	 * that amount and return true. Otherwise, it will do nothing and return false.
	 * 
	 * @param power The amount of power to receive.
	 * @return True if the provided amount of power was received, false otherwise.
	 */
	public boolean addPower(int power) {
		if (canAcceptPower(power)) {
			getStorage().receiveEnergy(power, false);
			return true;
		}
		return false;
	}

	/**
	 * Returns true if this component has >0 FE.
	 * 
	 * @return
	 */
	public boolean hasPower() {
		return EnergyStorage.getEnergyStored() > 0;
	}

	/**
	 * This method syncs the current state of this energy storage component to all
	 * clients within a 64 block radius.
	 */
	public void syncToClient() {
		if (!getWorld().isRemote) {
			PacketEnergyStorageComponent syncPacket = new PacketEnergyStorageComponent(this, getPos(), this.getComponentName());
			StaticPowerMessageHandler.sendMessageToPlayerInArea(StaticPowerMessageHandler.MAIN_PACKET_CHANNEL, getWorld(), getPos(), 64, syncPacket);
		} else {
			throw new RuntimeException("This method should only be called on the server!");
		}

	}

	/**
	 * Sets the filter used to restrict access to this component through
	 * capabilities. Use this to prevent certain actions from the capability access
	 * (ie. make it so external accessor cannot extract power). No need to check
	 * side configurations (ex. if a side is disabled) here as the energy component
	 * automatically picks those up.
	 * 
	 * @param filter
	 */
	public void setCapabiltiyFilter(TriFunction<Integer, Direction, EnergyManipulationAction, Boolean> filter) {
		this.filter = filter;
	}

	@Override
	public void deserializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.deserializeUpdateNbt(nbt, fromUpdate);
		EnergyStorage.readFromNbt(nbt);

		powerCapacityUpgradeMultiplier = nbt.getFloat("power_capacity_upgrade_multiplier");
		powerIOUpgradeMultiplier = nbt.getFloat("power_IO_upgrade_multiplier");
		defaultCapacity = nbt.getInt("default_capacity");
		defaultMaxInput = nbt.getInt("default_max_input");
		defaultMaxOutput = nbt.getInt("default_max_output");
	}

	@Override
	public CompoundNBT serializeUpdateNbt(CompoundNBT nbt, boolean fromUpdate) {
		super.serializeUpdateNbt(nbt, fromUpdate);
		EnergyStorage.writeToNbt(nbt);

		nbt.putFloat("power_capacity_upgrade_multiplier", powerCapacityUpgradeMultiplier);
		nbt.putFloat("power_IO_upgrade_multiplier", powerIOUpgradeMultiplier);
		nbt.putInt("default_capacity", defaultCapacity);
		nbt.putInt("default_max_input", defaultMaxInput);
		nbt.putInt("default_max_output", defaultMaxOutput);
		return nbt;
	}

	@Override
	public <T> LazyOptional<T> provideCapability(Capability<T> cap, Direction side) {
		if (isEnabled()) {
			if (cap == CapabilityEnergy.ENERGY) {
				capabilityAccessor.currentSide = side;
				return LazyOptional.of(() -> capabilityAccessor).cast();
			}
		}

		return LazyOptional.empty();
	}

	private class EnergyComponentCapabilityAccess implements IEnergyStorage {
		protected Direction currentSide;

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			if (EnergyStorageComponent.this.filter != null && !EnergyStorageComponent.this.filter.apply(maxReceive, currentSide, EnergyManipulationAction.RECIEVE)) {
				return 0;
			}
			return EnergyStorageComponent.this.getStorage().receiveEnergy(maxReceive, simulate);
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			if (EnergyStorageComponent.this.filter != null && !EnergyStorageComponent.this.filter.apply(maxExtract, currentSide, EnergyManipulationAction.PROVIDE)) {
				return 0;
			}
			return EnergyStorageComponent.this.getStorage().extractEnergy(maxExtract, simulate);
		}

		@Override
		public int getEnergyStored() {
			return EnergyStorageComponent.this.getStorage().getEnergyStored();
		}

		@Override
		public int getMaxEnergyStored() {
			return EnergyStorageComponent.this.getStorage().getMaxEnergyStored();
		}

		@Override
		public boolean canExtract() {
			return EnergyStorageComponent.this.getStorage().canExtract();
		}

		@Override
		public boolean canReceive() {
			return EnergyStorageComponent.this.getStorage().canReceive();
		}

	}
}
