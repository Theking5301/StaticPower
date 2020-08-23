package theking530.staticpower.cables.heat;

import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.util.concurrent.AtomicDouble;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import theking530.staticpower.cables.AbstractCableProviderComponent;
import theking530.staticpower.cables.CableUtilities;
import theking530.staticpower.cables.network.CableNetworkManager;
import theking530.staticpower.cables.network.CableNetworkModuleTypes;
import theking530.staticpower.cables.network.ServerCable;
import theking530.staticpower.cables.network.ServerCable.CableConnectionState;
import theking530.staticpower.network.StaticPowerMessageHandler;
import theking530.staticpower.tileentities.components.heat.CapabilityHeatable;
import theking530.staticpower.tileentities.components.heat.IHeatStorage;
import theking530.staticpower.tileentities.components.serialization.UpdateSerialize;

public class HeatCableComponent extends AbstractCableProviderComponent implements IHeatStorage {
	public static final float HEAT_SYNC_MIN_DELTA = 10.0f;
	public static final String HEAT_CAPACITY_DATA_TAG_KEY = "heat_capacity";
	public static final String HEAT_RATE_DATA_TAG_KEY = "heat_transfer_rate";
	private final float capacity;
	private final float transferRate;
	@UpdateSerialize
	private float clientSideHeat;
	@UpdateSerialize
	private float clientSideHeatCapacity;

	public HeatCableComponent(String name, float capacity, float transferRate) {
		super(name, CableNetworkModuleTypes.HEAT_NETWORK_MODULE);
		this.capacity = capacity;
		this.transferRate = transferRate;
	}

	@Override
	public void preProcessUpdate() {
		super.preProcessUpdate();
		if (!getWorld().isRemote) {
			this.<HeatNetworkModule>getNetworkModule(CableNetworkModuleTypes.HEAT_NETWORK_MODULE).ifPresent(network -> {
				boolean shouldUpdate = Math.abs(network.getHeatStorage().getCurrentHeat() - clientSideHeat) >= HEAT_SYNC_MIN_DELTA;
				shouldUpdate |= network.getHeatStorage().getMaximumHeat() != clientSideHeatCapacity;
				shouldUpdate |= clientSideHeat == 0 && network.getHeatStorage().getCurrentHeat() > 0;
				shouldUpdate |= clientSideHeat > 0 && network.getHeatStorage().getCurrentHeat() == 0;
				if (shouldUpdate) {
					updateClientValues();
				}
			});
		}
	}

	public void updateClientValues() {
		if (!getWorld().isRemote) {
			this.<HeatNetworkModule>getNetworkModule(CableNetworkModuleTypes.HEAT_NETWORK_MODULE).ifPresent(network -> {
				clientSideHeat = network.getHeatStorage().getCurrentHeat();
				clientSideHeatCapacity = network.getHeatStorage().getMaximumHeat();

				// Only send the packet to nearby players since these packets get sent
				// frequently.
				HeatCableUpdatePacket packet = new HeatCableUpdatePacket(getPos(), clientSideHeat, clientSideHeatCapacity);
				StaticPowerMessageHandler.sendMessageToPlayerInArea(StaticPowerMessageHandler.MAIN_PACKET_CHANNEL, getWorld(), getPos(), 128, packet);
			});
		}
	}

	@Override
	protected boolean canAttachAttachment(ItemStack attachment) {
		return false;
	}

	@Override
	public float getCurrentHeat() {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicDouble recieve = new AtomicDouble(0);
			getHeatNetworkModule().ifPresent(module -> {
				recieve.set(module.getHeatStorage().getCurrentHeat());
			});
			return (float) recieve.get();
		} else {
			return clientSideHeat;
		}
	}

	@Override
	public float getMaximumHeat() {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicDouble recieve = new AtomicDouble(0);
			getHeatNetworkModule().ifPresent(module -> {
				recieve.set(module.getHeatStorage().getMaximumHeat());
			});
			return (float) recieve.get();
		} else {
			return clientSideHeatCapacity;
		}
	}

	@Override
	public float getConductivity() {
		return transferRate;
	}

	@Override
	public float heat(float amountToHeat, boolean simulate) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicDouble recieve = new AtomicDouble(0);
			getHeatNetworkModule().ifPresent(module -> {
				recieve.set(module.getHeatStorage().heat(amountToHeat, simulate));
			});
			return (float) recieve.get();
		} else {
			return 0;
		}
	}

	@Override
	public float cool(float amountToCool, boolean simulate) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicDouble recieve = new AtomicDouble(0);
			getHeatNetworkModule().ifPresent(module -> {
				recieve.set(module.getHeatStorage().cool(amountToCool, simulate));
			});
			return (float) recieve.get();
		} else {
			return 0;
		}
	}

	public void updateFromNetworkUpdatePacket(float clientHeat, float clientCapacity) {
		this.clientSideHeat = clientHeat;
		this.clientSideHeatCapacity = clientCapacity;
	}

	/**
	 * Gets the heat network module for the network this cable belongs to. We have
	 * to wrap it in an optional because while we can guarantee once this component
	 * is validated that the network is valid, since this component exposes external
	 * methods, other tile entity that are made valid before us may call some of our
	 * methods.
	 * 
	 * @return
	 */
	public Optional<HeatNetworkModule> getHeatNetworkModule() {
		CableNetworkManager manager = CableNetworkManager.get(getTileEntity().getWorld());
		ServerCable cable = manager.getCable(getTileEntity().getPos());
		if (cable.getNetwork() != null) {
			return Optional.of(cable.getNetwork().getModule(CableNetworkModuleTypes.HEAT_NETWORK_MODULE));
		}
		return Optional.empty();
	}

	@Override
	protected ServerCable createCable() {
		return new ServerCable(getWorld(), getPos(), getSupportedNetworkModuleTypes(), (cable) -> {
			cable.setProperty(HEAT_CAPACITY_DATA_TAG_KEY, capacity);
			cable.setProperty(HEAT_RATE_DATA_TAG_KEY, transferRate);
		});
	}

	@Override
	protected CableConnectionState cacheConnectionState(Direction side, @Nullable TileEntity te, BlockPos blockPosition) {
		AbstractCableProviderComponent otherProvider = CableUtilities.getCableWrapperComponent(getWorld(), blockPosition);
		if (otherProvider != null && otherProvider.areCableCompatible(this, side)) {
			if (!otherProvider.isSideDisabled(side.getOpposite())) {
				return CableConnectionState.CABLE;
			}
		} else if (te != null && otherProvider == null) {
			if (te.getCapability(CapabilityHeatable.HEAT_STORAGE_CAPABILITY, side.getOpposite()).isPresent()) {
				return CableConnectionState.TILE_ENTITY;
			}
		}
		return CableConnectionState.NONE;
	}

	@Override
	public <T> LazyOptional<T> provideCapability(Capability<T> cap, Direction side) {
		// Only provide the energy capability if we are not disabled on that side.
		if (cap == CapabilityHeatable.HEAT_STORAGE_CAPABILITY) {
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
				return LazyOptional.of(() -> this).cast();
			}
		}
		return LazyOptional.empty();
	}

}
