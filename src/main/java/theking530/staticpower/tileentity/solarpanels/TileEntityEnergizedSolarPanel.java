package theking530.staticpower.tileentity.solarpanels;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import theking530.staticpower.energy.StaticEnergyStorage;

public class TileEntityEnergizedSolarPanel extends TileEntityBasicSolarPanel implements IEnergyHandler, IEnergyProvider {
	
	public void initializeSolarPanel() {
		energyStorage = new StaticEnergyStorage(512);
		energyStorage.setMaxReceive(80);
		energyStorage.setMaxExtract(80*2);
	}
}
