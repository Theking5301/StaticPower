package theking530.api.power;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class PowerEnergyInterface implements IEnergyStorage, IStaticVoltHandler {
	public enum EnergyType {
		STATIC_VOLT, FORGE_ENERGY
	}

	protected final IStaticVoltHandler staticVoltHandler;
	protected final IEnergyStorage energyStorage;
	protected final EnergyType actualType;

	public PowerEnergyInterface(IStaticVoltHandler staticVoltHandler) {
		this.staticVoltHandler = staticVoltHandler;
		energyStorage = null;
		actualType = EnergyType.STATIC_VOLT;
	}

	public PowerEnergyInterface(IEnergyStorage energyStorage) {
		this.energyStorage = energyStorage;
		this.staticVoltHandler = null;
		actualType = EnergyType.FORGE_ENERGY;
	}

	/******************************
	 * IStaticVoltHandler Interface
	 ******************************/
	@Override
	public int getStoredPower() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getStoredPower();
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.getEnergyStored() / IStaticVoltHandler.FE_TO_SV_CONVERSION;
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getCapacity();
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.getMaxEnergyStored() / IStaticVoltHandler.FE_TO_SV_CONVERSION;
		}
		return 0;
	}

	@Override
	public int receivePower(int power, boolean simulate) {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.receivePower(power, simulate);
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.receiveEnergy(power * IStaticVoltHandler.FE_TO_SV_CONVERSION, simulate) / IStaticVoltHandler.FE_TO_SV_CONVERSION;
		}
		return 0;
	}

	@Override
	public int drainPower(int power, boolean simulate) {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.drainPower(power, simulate);
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.extractEnergy(power * IStaticVoltHandler.FE_TO_SV_CONVERSION, simulate) / IStaticVoltHandler.FE_TO_SV_CONVERSION;
		}
		return 0;
	}

	@Override
	public boolean canRecievePower() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.canRecievePower();
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.canReceive();
		}
		return false;
	}

	@Override
	public boolean canDrainPower() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.canDrainPower();
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.canExtract();
		}
		return false;
	}

	/**************************
	 * IEnergyStorage Interface
	 **************************/
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (actualType == EnergyType.STATIC_VOLT) {
			return receivePower(maxReceive / IStaticVoltHandler.FE_TO_SV_CONVERSION, simulate) * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return receiveEnergy(maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (actualType == EnergyType.STATIC_VOLT) {
			return drainPower(maxExtract / IStaticVoltHandler.FE_TO_SV_CONVERSION, simulate) * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return extractEnergy(maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public int getEnergyStored() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getStoredPower() * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getCapacity() * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.getMaxEnergyStored();
		}
		return 0;
	}

	@Override
	public boolean canExtract() {
		return canDrainPower();
	}

	@Override
	public boolean canReceive() {
		return canRecievePower();
	}

	public static @Nullable PowerEnergyInterface getFromItemStack(ItemStack stack) {
		// Skip empty stacks.
		if (stack.isEmpty()) {
			return null;
		}

		// Check for SV.
		IStaticVoltHandler svHandler = stack.getCapability(CapabilityStaticVolt.STATIC_VOLT_CAPABILITY).orElse(null);
		if (svHandler != null) {
			return new PowerEnergyInterface(svHandler);
		}

		// Check for FE.
		IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
		if (energyStorage != null) {
			return new PowerEnergyInterface(energyStorage);
		}

		// If we make it this far, return null.
		return null;
	}

	@Override
	public int getMaxReceive() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getMaxReceive() * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.receiveEnergy(Integer.MAX_VALUE, true);
		}
		return 0;
	}

	@Override
	public int getMaxDrain() {
		if (actualType == EnergyType.STATIC_VOLT) {
			return staticVoltHandler.getMaxDrain() * IStaticVoltHandler.FE_TO_SV_CONVERSION;
		} else if (actualType == EnergyType.FORGE_ENERGY) {
			return energyStorage.extractEnergy(Integer.MAX_VALUE, true);
		}
		return 0;
	}
}
