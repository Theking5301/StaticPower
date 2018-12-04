package theking530.staticpower.tileentity.solarpanels;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import theking530.staticpower.energy.StaticEnergyStorage;

public class TileEntityStaticSolarPanel extends TileEntityBasicSolarPanel implements IEnergyHandler, IEnergyProvider {
	
	public void initializeSolarPanel() {
		energyStorage = new StaticEnergyStorage(128);
		energyStorage.setMaxReceive(20);
		energyStorage.setMaxExtract(20*2);
	}	
}
