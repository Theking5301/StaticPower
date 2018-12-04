package theking530.staticpower.machines.batteries.tileentities;

public class TileEntityBasicBattery extends TileEntityBattery{

	public TileEntityBasicBattery() {
		super();
		setMaximumPowerIO(100);
	}
	@Override
	public String getName() {
		return "container.BasicBattery";
	}
}
