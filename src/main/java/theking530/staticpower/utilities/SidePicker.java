package theking530.staticpower.utilities;

import java.util.Map;

import org.lwjgl.input.Mouse;

import com.google.common.collect.Maps;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class SidePicker {

	private static final ProjectionHelper projectionHelper = new ProjectionHelper();

	public enum Side {
		XNeg,
		XPos,
		YNeg,
		YPos,
		ZNeg,
		ZPos;

		public static Side fromForgeDirection(EnumFacing dir) {
			switch (dir) {
				case WEST:
					return XNeg;
				case EAST:
					return XPos;
				case DOWN:
					return YNeg;
				case UP:
					return YPos;
				case NORTH:
					return ZNeg;
				case SOUTH:
					return ZPos;
				default:
					break;
			}
			return null;
		}

		public EnumFacing toForgeDirection() {
			switch (this) {
				case XNeg:
					return EnumFacing.WEST;
				case XPos:
					return EnumFacing.EAST;
				case YNeg:
					return EnumFacing.DOWN;
				case YPos:
					return EnumFacing.UP;
				case ZNeg:
					return EnumFacing.NORTH;
				case ZPos:
					return EnumFacing.SOUTH;
				default:
					throw new IllegalArgumentException(toString());
			}
		}
	}

	public static class HitCoord {
		public final Side side;
		public final Vec3d coord;

		public HitCoord(Side side, Vec3d coord) {
			this.side = side;
			this.coord = coord;
		}
	}

	private final double negX, negY, negZ;
	private final double posX, posY, posZ;

	public SidePicker(double negX, double negY, double negZ, double posX, double posY, double posZ) {
		this.negX = negX;
		this.negY = negY;
		this.negZ = negZ;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public SidePicker(double halfSize) {
		negX = negY = negZ = -halfSize;
		posX = posY = posZ = +halfSize;
	}

	private static Vec3d getMouseVector(float z) {
		return projectionHelper.unproject(Mouse.getX(), Mouse.getY(), z);
	}

	private Vec3d calculateXPoint(Vec3d near, Vec3d diff, double x) {
		double p = (x - near.x) / diff.x;

		double y = near.y + diff.y * p;
		double z = near.z + diff.z * p;

		if (negY <= y && y <= posY && negZ <= z && z <= posZ) return new Vec3d(x, y, z);

		return null;
	}

	private Vec3d calculateYPoint(Vec3d near, Vec3d diff, double y) {
		double p = (y - near.y) / diff.y;

		double x = near.x + diff.x * p;
		double z = near.z + diff.z * p;

		if (negX <= x && x <= posX && negZ <= z && z <= posZ) return new Vec3d(x, y, z);

		return null;
	}

	private Vec3d calculateZPoint(Vec3d near, Vec3d diff, double z) {
		double p = (z - near.z) / diff.z;

		double x = near.x + diff.x * p;
		double y = near.y + diff.y * p;

		if (negX <= x && x <= posX && negY <= y && y <= posY) return new Vec3d(x, y, z);

		return null;
	}

	private static void addPoint(Map<Side, Vec3d> map, Side side, Vec3d value) {
		if (value != null) map.put(side, value);
	}

	private Map<Side, Vec3d> calculateHitPoints(Vec3d near, Vec3d far) {
		Vec3d diff = far.subtract(near);

		Map<Side, Vec3d> result = Maps.newEnumMap(Side.class);
		addPoint(result, Side.XNeg, calculateXPoint(near, diff, negX));
		addPoint(result, Side.XPos, calculateXPoint(near, diff, posX));

		addPoint(result, Side.YNeg, calculateYPoint(near, diff, negY));
		addPoint(result, Side.YPos, calculateYPoint(near, diff, posY));

		addPoint(result, Side.ZNeg, calculateZPoint(near, diff, negZ));
		addPoint(result, Side.ZPos, calculateZPoint(near, diff, posZ));
		return result;
	}

	public Map<Side, Vec3d> calculateMouseHits() {
		projectionHelper.updateMatrices();
		Vec3d near = getMouseVector(0);
		Vec3d far = getMouseVector(1);

		return calculateHitPoints(near, far);
	}

	public HitCoord getNearestHit() {
		projectionHelper.updateMatrices();
		Vec3d near = getMouseVector(0);
		Vec3d far = getMouseVector(1);

		Map<Side, Vec3d> hits = calculateHitPoints(near, far);

		if (hits.isEmpty()) return null;

		Side minSide = null;
		double minDist = Double.MAX_VALUE;

		// yeah, I know there are two entries max, but... meh
		for (Map.Entry<Side, Vec3d> e : hits.entrySet()) {
			double dist = e.getValue().subtract(near).lengthVector();
			if (dist < minDist) {
				minDist = dist;
				minSide = e.getKey();
			}
		}

		if (minSide == null) return null; // !?

		return new HitCoord(minSide, hits.get(minSide));
	}
}