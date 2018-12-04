package theking530.staticpower.conduits;

import net.minecraft.util.EnumFacing;

public interface IConduit {

	public boolean isConduit(EnumFacing side);
	public boolean isReciever(EnumFacing side);
}
