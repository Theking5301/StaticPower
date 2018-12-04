package theking530.staticpower.tileentity;

import theking530.staticpower.assists.utilities.RedstoneModeList.RedstoneMode;

public interface IRedstoneConfigurable {

	public RedstoneMode getRedstoneMode();
	public void setRedstoneMode(RedstoneMode newMode);
	public boolean isRedstoneControllable();

}
