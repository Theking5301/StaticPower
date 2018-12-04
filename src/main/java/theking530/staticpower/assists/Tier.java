package theking530.staticpower.assists;

import theking530.staticpower.tileentity.digistorenetwork.digistore.TileEntityDigistore;

public enum Tier {
	BASIC("Basic", 50000, 100000, 2000, 512), 
	CREATIVE("Creative", 0, 0, 0, -1), 
	IRON("Iron", 0, 0, 0, 1024), 
	GOLD("Gold", 0, 0, 0, 4096), 
	TIN("Tin", 0, 0, 0, -1), 
	COPPER("Copper", 0, 0, 0, -1), 
	SILVER("Silver", 0, 0, 0, -1), 
	LEAD("Lead", 0, 0, 0, -64), 
	NICKEL("Nickel", 0, 0, 0, -1), 
	ALUMINIUM("Aluminium", 0, 0, 0, -1), 
	DIAMOND("Diamond", 0, 0, 0, -1), 
	EMERALD("Emerald", 0, 0, 0, -1),
	OBSIDIAN("Obsidian", 0, 0, 0, -TileEntityDigistore.DEFAULT_CAPACITY+1), 
	SAPPHIRE("Sapphire", 0, 0, 0, -1), 
	RUBY("Ruby", 0, 0, 0, -1),
	STATIC("Static", 125000,1000000,4000, 2048), 
	ENERGIZED("Energized", 625000,10000000,8000, 8192), 
	LUMUM("Lumum", 2500000, 100000000,16000, 16384);
	
	private final String name;
	private final int portableBatteryCapacity;
	private final int batteryCapacity;
	private final int fluidCanisterCapacity;
	private final int digistoreItemCapacityUpgrade;
	
	private Tier(String name, int portableBatteryCapacity, int batteryCapacity, int fluidCanisterCapacity, int digistoreItemCapacityUpgrade) {
		this.portableBatteryCapacity = portableBatteryCapacity;
		this.batteryCapacity = batteryCapacity;
		this.fluidCanisterCapacity = fluidCanisterCapacity;		
		this.digistoreItemCapacityUpgrade = digistoreItemCapacityUpgrade;
		this.name= name;
	}
	public int getPortableBatteryCapacity() {
		return portableBatteryCapacity;
	}
	public int getBatteryCapacity() {
		return batteryCapacity;
	}
	public int getFluidCanisterCapacity() {
		return fluidCanisterCapacity;
	}
	public int getDigistoreItemCapacityAmount() {
		return digistoreItemCapacityUpgrade;
	}
	public String getName() {
		return "tier." + name;
	}
	@Override
	public String toString() {
		return name.toLowerCase();
	}
}
