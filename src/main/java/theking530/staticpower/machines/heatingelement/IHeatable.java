package theking530.staticpower.machines.heatingelement;

public interface IHeatable {

	public int getHeat();
	public int recieveHeat(int heat);
	public boolean canHeat();
	public int extractHeat(int heat);
	public int getMaxHeat();
}
