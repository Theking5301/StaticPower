package theking530.staticpower.machines.batteries.tileentities;

public class TileEntityLumumBattery extends TileEntityBattery{

	public TileEntityLumumBattery() {
		super();
		setMaximumPowerIO(10000);
		energyStorage.setCapacity(50000000);
	}
	@Override
	public String getName() {
		return "container.LumumBattery";
	}
}
