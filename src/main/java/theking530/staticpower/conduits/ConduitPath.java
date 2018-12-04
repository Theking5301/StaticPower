package theking530.staticpower.conduits;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import theking530.staticpower.assists.utilities.WorldUtilities;

public class ConduitPath {
	private List<BlockPos> path;
	
	public ConduitPath(List<BlockPos> path) {
		this.path = path;
	}
	public BlockPos getDestination() {
		return path.get(path.size()-1);
	}
	public BlockPos getPenultimate() {
		return path.get(path.size()-2);
	}
	public EnumFacing getPenultimateFacing() {
		return WorldUtilities.getFacingFromPos(getDestination(), getPenultimate());
	}
	public BlockPos get(int i) {
		return path.get(i);
	}
	public int size() {
		return path.size();
	}
}
