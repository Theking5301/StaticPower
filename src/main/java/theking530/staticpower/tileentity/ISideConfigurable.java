package theking530.staticpower.tileentity;

import java.util.List;

import net.minecraft.util.EnumFacing;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;

public interface ISideConfigurable {

	public enum SideIncrementDirection {
		FORWARD, BACKWARDS;
	}
	
	public boolean isSideConfigurable();
	public Mode getSideConfiguration(BlockSide side);
    public Mode getSideConfiguration(EnumFacing facing);
    
    public Mode[] getSideConfigurations();
    public List<Mode> getValidSideConfigurations();
    
	public void setSideConfiguration(Mode newMode, BlockSide side);
    public void setSideConfiguration(Mode newMode, EnumFacing facing);
    
	public void incrementSideConfiguration(EnumFacing side, SideIncrementDirection direction);
	public int getSideWithModeCount(Mode mode);
}
