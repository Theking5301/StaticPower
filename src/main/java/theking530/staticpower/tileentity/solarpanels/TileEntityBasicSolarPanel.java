package theking530.staticpower.tileentity.solarpanels;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import theking530.staticpower.energy.PowerDistributor;
import theking530.staticpower.energy.StaticEnergyStorage;

public class TileEntityBasicSolarPanel extends TileEntity implements ITickable, IEnergyHandler, IEnergyProvider {
	
	public StaticEnergyStorage energyStorage; 
	public PowerDistributor energyDistributor;
	
	public TileEntityBasicSolarPanel() {
		initializeSolarPanel();
		energyDistributor = new PowerDistributor(this, energyStorage);
	}
	public void initializeSolarPanel() {
		energyStorage = new StaticEnergyStorage(64);
		energyStorage.setMaxReceive(10);
		energyStorage.setMaxExtract(10*2);
	}	
	@Override
	public void update() {
		if(!getWorld().isRemote) {
			generateRF();
			if(energyStorage.getEnergyStored() > 0) {
				energyDistributor.provideRF(EnumFacing.DOWN, energyStorage.getMaxReceive());		
			}
		}
	}	
	//Functionality
	public void generateRF() {
		if(!getWorld().canBlockSeeSky(pos)) {
		}else if(lightRatio() > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
			energyStorage.receiveEnergy(energyStorage.getMaxReceive(), false);
		}
	}
	public boolean isGenerating() {
		if(!getWorld().canBlockSeeSky(pos)) {
			return false;
		}else if(lightRatio() > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
			return true;
		}
		return false;
	}
	
	//Light
	public float lightRatio() {
		return calculateLightRatio(getWorld(), pos);
	}
	public float calculateLightRatio(World world, BlockPos pos) {
		int lightValue = world.getLightFor(EnumSkyBlock.SKY, pos) - world.getSkylightSubtracted();
		float sunAngle = world.getCelestialAngleRadians(1.0F);

		if(sunAngle < (float) Math.PI) {
		  sunAngle += (0.0F - sunAngle) * 0.2F;
		} else {
			sunAngle += (((float) Math.PI * 2F) - sunAngle) * 0.2F;
		}

		lightValue = Math.round(lightValue * MathHelper.cos(sunAngle));

		lightValue = MathHelper.clamp(lightValue, 0, 15);
		return lightValue / 15f;
	}
	
	//NBT & UPDATE			
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        return nbt;
	}	    

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if(from == EnumFacing.DOWN) {
			return true;
		}
		return false;
	}
	@Override
	public int getEnergyStored(EnumFacing from) {
		return energyStorage.getEnergyStored();
	}
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return energyStorage.getMaxEnergyStored();
	}
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if(from == EnumFacing.DOWN) {
			return energyStorage.extractEnergy(maxExtract, simulate);
		}
		return 0;
	}
	/* CAPABILITIES */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing from) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, from);
	}
	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing from) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(new net.minecraftforge.energy.IEnergyStorage() {

				@Override
				public int receiveEnergy(int maxReceive, boolean simulate) {

					return 0;
				}

				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {

					return TileEntityBasicSolarPanel.this.extractEnergy(from, maxExtract, simulate);
				}

				@Override
				public int getEnergyStored() {

					return TileEntityBasicSolarPanel.this.getEnergyStored(from);
				}

				@Override
				public int getMaxEnergyStored() {

					return TileEntityBasicSolarPanel.this.getMaxEnergyStored(from);
				}

				@Override
				public boolean canExtract() {

					return false;
				}

				@Override
				public boolean canReceive() {

					return true;
				}
			});
		}
		return super.getCapability(capability, from);
	}	
}
