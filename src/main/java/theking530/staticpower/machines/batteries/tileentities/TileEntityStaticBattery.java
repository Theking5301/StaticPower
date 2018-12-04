package theking530.staticpower.machines.batteries.tileentities;

public class TileEntityStaticBattery extends TileEntityBattery{

	public TileEntityStaticBattery() {
		super();
		setMaximumPowerIO(250);
		energyStorage.setCapacity(200000);
	}
	@Override
	public String getName() {
		return "container.StaticBattery";
	}
}
