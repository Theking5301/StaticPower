package theking530.staticpower.tileentity.solarpanels;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.util.EnumFacing;
import theking530.staticpower.energy.StaticEnergyStorage;

public class TileEntityCreativeSolarPanel extends TileEntityBasicSolarPanel implements IEnergyHandler, IEnergyProvider {
	
	public void initializeSolarPanel() {
		energyStorage = new StaticEnergyStorage(2048);
		energyStorage.setMaxReceive(2000);
		energyStorage.setMaxExtract(2000*2);
	}	
	public void updateEntity() {
		generateRF();
		energyDistributor.distributePower();
	}	
	//Functionality
	public void generateRF() {
		energyStorage.receiveEnergy(energyStorage.getMaxReceive(), false);
	}
	public boolean isGenerating() {
		return true;
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
}
