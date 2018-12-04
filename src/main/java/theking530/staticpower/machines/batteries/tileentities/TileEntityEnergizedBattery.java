package theking530.staticpower.machines.batteries.tileentities;

public class TileEntityEnergizedBattery extends TileEntityBattery{

	public TileEntityEnergizedBattery() {
		super();
		setMaximumPowerIO(1000);
		energyStorage.setCapacity(5000000);
	}
	@Override
	public String getName() {
		return "container.EnergizedBattery";
	}
}
