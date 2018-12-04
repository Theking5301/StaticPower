	package theking530.staticpower.tileentity.solarpanels;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import theking530.staticpower.energy.StaticEnergyStorage;

public class TileEntityLumumSolarPanel extends TileEntityBasicSolarPanel implements IEnergyHandler, IEnergyProvider {
	
	public void initializeSolarPanel() {
		energyStorage = new StaticEnergyStorage(1024);
		energyStorage.setMaxReceive(160);
		energyStorage.setMaxExtract(160*2);
	}	
}
