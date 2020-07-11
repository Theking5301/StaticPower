package theking530.staticpower.cables.fluid;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import theking530.staticpower.cables.AbstractCableProviderComponent;
import theking530.staticpower.cables.CableUtilities;
import theking530.staticpower.cables.network.CableNetworkModuleTypes;
import theking530.staticpower.cables.network.ServerCable;
import theking530.staticpower.cables.network.ServerCable.CableConnectionState;
import theking530.staticpower.items.cableattachments.extractor.ExtractorAttachment;
import theking530.staticpower.network.StaticPowerMessageHandler;

public class FluidCableComponent extends AbstractCableProviderComponent implements IFluidHandler {
	public static final String FLUID_CAPACITY_DATA_TAG_KEY = "fluid_capacity";
	public static final int EXTRACTION_RATE = 10;
	public static final float UPDATE_THRESHOLD = 0.1f;
	private final int capacity;
	private FluidStack lastUpdateFluidStack;
	private float lastUpdateFilledPercentage;

	public FluidCableComponent(String name, int capacity) {
		super(name, CableNetworkModuleTypes.FLUID_NETWORK_MODULE);
		this.capacity = capacity;
		lastUpdateFluidStack = FluidStack.EMPTY;
		lastUpdateFilledPercentage = 0.0f;
	}

	@Override
	public void preProcessUpdate() {
		super.preProcessUpdate();
		if (!getWorld().isRemote) {
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				boolean shouldUpdate = !network.getFluidStorage().getFluid().isFluidEqual(lastUpdateFluidStack);
				shouldUpdate |= Math.abs(lastUpdateFilledPercentage - getFilledPercentage()) > UPDATE_THRESHOLD;
				shouldUpdate |= lastUpdateFilledPercentage > 0 && this.getFluidInTank(0).isEmpty();
				if (shouldUpdate) {
					updateClientRenderValues();
				}
			});
		}
	}

	public void updateClientRenderValues() {
		if (!getWorld().isRemote) {
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				lastUpdateFluidStack = network.getFluidStorage().getFluid();
				lastUpdateFilledPercentage = Math.min(1.0f, getFilledPercentage());

				FluidCableUpdatePacket packet = new FluidCableUpdatePacket(getPos(), lastUpdateFluidStack, lastUpdateFilledPercentage);
				StaticPowerMessageHandler.sendMessageToPlayerInArea(StaticPowerMessageHandler.MAIN_PACKET_CHANNEL, getWorld(), getPos(), 32, packet);
			});
		}
	}

	public void recieveUpdateRenderValues(FluidStack stack, float fluidAmount) {
		if (getWorld().isRemote) {
			lastUpdateFluidStack = stack;
			lastUpdateFilledPercentage = Math.min(1.0f, fluidAmount);
		}
	}

	public float getFilledPercentage() {
		if (getWorld().isRemote) {
			return lastUpdateFilledPercentage;
		} else {
			AtomicReference<Float> output = new AtomicReference<Float>(0.0f);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				output.set((float) network.getFluidStorage().getFluidAmount() / network.getFluidStorage().getCapacity());
			});
			return output.get();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getTanks() {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicInteger recieve = new AtomicInteger(0);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				recieve.set(network.getFluidStorage().getTanks());
			});
			return recieve.get();
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicReference<FluidStack> fluid = new AtomicReference<FluidStack>(FluidStack.EMPTY);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				fluid.set(network.getFluidStorage().getFluidInTank(tank));
			});
			return fluid.get();
		} else {
			return lastUpdateFluidStack;
		}
	}

	@Override
	public int getTankCapacity(int tank) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicInteger recieve = new AtomicInteger(0);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				recieve.set(network.getFluidStorage().getTankCapacity(tank));
			});
			return recieve.get();
		} else {
			return 0;
		}
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicBoolean recieve = new AtomicBoolean(false);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				recieve.set(network.getFluidStorage().isFluidValid(tank, stack));
			});
			return recieve.get();
		} else {
			return false;
		}
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (!getTileEntity().getWorld().isRemote) {
			AtomicInteger recieve = new AtomicInteger(0);
			this.<FluidNetworkModule>getNetworkModule(CableNetworkModuleTypes.FLUID_NETWORK_MODULE).ifPresent(network -> {
				recieve.set(network.getFluidStorage().fill(resource, action));
			});
			return recieve.get();
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return FluidStack.EMPTY;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return FluidStack.EMPTY;
	}

	@Override
	public <T> LazyOptional<T> provideCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> this).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	protected ServerCable createCable() {
		return new ServerCable(getWorld(), getPos(), getSupportedNetworkModuleTypes(), (cable) -> {
			cable.setProperty(FLUID_CAPACITY_DATA_TAG_KEY, capacity);
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
			if (te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()).isPresent()) {
				return CableConnectionState.TILE_ENTITY;
			}
		}
		return CableConnectionState.NONE;
	}

	@Override
	protected boolean canAttachAttachment(ItemStack attachment) {
		return !attachment.isEmpty() && attachment.getItem() instanceof ExtractorAttachment;
	}

	public CompoundNBT serializeSaveNbt(CompoundNBT nbt) {
		super.serializeSaveNbt(nbt);
		// Save the filled percent.
		nbt.putFloat("filled_percentage", lastUpdateFilledPercentage);

		// Put the fluid stack.
		CompoundNBT fluidNbt = new CompoundNBT();
		lastUpdateFluidStack.writeToNBT(fluidNbt);
		nbt.put("fluid", fluidNbt);
		return nbt;
	}

	public void deserializeSaveNbt(CompoundNBT nbt) {
		super.deserializeSaveNbt(nbt);
		// Load the filled percent.
		lastUpdateFilledPercentage = nbt.getFloat("filled_percentage");

		// Load the last update fluidstack.
		lastUpdateFluidStack = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt.get("fluid"));
	}
}
