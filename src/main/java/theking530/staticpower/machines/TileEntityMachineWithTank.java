package theking530.staticpower.machines;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.fluids.StaticTankStorage;
import theking530.staticpower.items.upgrades.BaseTankUpgrade;

public class TileEntityMachineWithTank extends TileEntityMachine {

	public static final int DEFAULT_FLUID_CAPACITY = 10000;
	
	public int initialTankCapacity;
	private boolean canFillExternally;
	public StaticTankStorage fluidTank;	
	
	public void initializeTank(int InitialTankCapacity) {	
		initialTankCapacity = InitialTankCapacity;
		fluidTank = new StaticTankStorage("MainFluidTank", initialTankCapacity);
		canFillExternally = true;
	}

	public void useFluidContainer() {

	}
	@Override
	public void upgradeTick(){
		super.upgradeTick();
		tankUpgrade();
	}
	public void tankUpgrade() {
		boolean flag = false;
		int slot = 0;
		for(int i=0; i<3; i++) {
			if(slotsUpgrades.getStackInSlot(i) != null) {
				if(slotsUpgrades.getStackInSlot(i).getItem() instanceof BaseTankUpgrade) {
					flag = true;
					slot = i;
				}
			}
		}
		if(flag) {
			BaseTankUpgrade tempUpgrade = (BaseTankUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			fluidTank.setCapacity((int)(tempUpgrade.getValueMultiplied(initialTankCapacity, tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0))));
		}else{
			fluidTank.setCapacity(initialTankCapacity);
		}
		if(fluidTank.getFluid() != null && fluidTank.getFluidAmount() > fluidTank.getCapacity()) {
			fluidTank.setFluid(new FluidStack(fluidTank.getFluid().getFluid(), fluidTank.getCapacity()));
		}
	}
	
	@Override  
    public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        fluidTank.readFromNBT(nbt.getCompoundTag("TANK"));
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
        NBTTagCompound tank =  new NBTTagCompound();
        fluidTank.writeToNBT(tank);
        nbt.setTag("TANK", tank);
    	return nbt;
	}    
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket(){
    	NBTTagCompound tag = new NBTTagCompound();
    	writeToNBT(tag);
    	return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
    }
	
    public void deserializeOnPlaced(NBTTagCompound nbt, World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)  {
		super.deserializeOnPlaced(nbt, world, pos, state, placer, stack);
        fluidTank.readFromNBT(nbt);
	}	
   
    public void setCanFillExternally(boolean canFill) {
    	canFillExternally = canFill;
    }
    
	public boolean isTankEmpty() {
		return fluidTank.getFluidAmount() <= 0 ? true : false;
	}
	public IFluidTankProperties[] getTankProperties(EnumFacing facing) {
		return fluidTank.getTankProperties();
	}
	public int fill(FluidStack resource, boolean doFill, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		int temp = fluidTank.fill(resource, doFill);
		return temp;
	}
	public FluidStack drain(FluidStack resource, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTank.drain(resource, doDrain);
	}
	public FluidStack drain(int maxDrain, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTank.drain(maxDrain, doDrain);
	}

	public float getFluidLevelScaled(int height) {
		int capacity = fluidTank.getCapacity();
		int volume = fluidTank.getFluidAmount();
		if(capacity != 0) {
			float percentage = (float)volume/(float)capacity;
			return percentage * height;
		}else{
			return 0;
		}			
	}
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
		Mode sideConfig = facing == null ? Mode.Regular : getSideConfiguration(facing);
    	if(facing == null || sideConfig != Mode.Disabled && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {
				@Override
				public IFluidTankProperties[] getTankProperties() {
					return TileEntityMachineWithTank.this.getTankProperties(facing);
				}
				@Override
				public int fill(FluidStack resource, boolean doFill) {
					if(!canFillExternally) {
						return 0;
					}
					if(facing == null || sideConfig == Mode.Input) {
						return TileEntityMachineWithTank.this.fill(resource, doFill, facing);
					}
					return 0;
				}

				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {
					if(facing == null || sideConfig == Mode.Output) {
						return TileEntityMachineWithTank.this.drain(resource, doDrain, facing);				
					}
					return null;
				}
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {
					if(facing == null || sideConfig == Mode.Output) {
						return TileEntityMachineWithTank.this.drain(maxDrain, doDrain, facing);
					}
					return null;
				}			
    		});
    	}
    	return super.getCapability(capability, facing);
    }
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
		Mode sideConfig = facing == null ? Mode.Regular : getSideConfiguration(facing);
    	if(sideConfig != Mode.Disabled && capability==CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
}
