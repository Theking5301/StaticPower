package theking530.staticpower.assists.utilities;

public class Vector3 {

	private int X;
	private int Y;
	private int Z;
	
	public Vector3(int x, int y, int z) {
		X=x;
		Y=y;
		Z=z;
	}
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getZ() {
		return Z;
	}
	public void setZ(int z) {
		Z = z;
	}

	public Vector3 addVectors(Vector3 vec) {
		return new Vector3(vec.getX() + X, vec.getY() + Y, vec.getZ() + Z);
	}
	public Vector3 subtractVectors(Vector3 vec) {
		return new Vector3(vec.getX() - X, vec.getY() - Y, vec.getZ() - Z);
	}
	public Vector3 normalize() {
		float max = Math.max(X, Y);
		max = Math.max(max, Z) > 0 ? Math.max(max, Z) : 1;
		
		return new Vector3((int)(X/max), (int)(Y/max), (int)(Z/max));
	}
	@Override
	public String toString() {
		String temp = "X: " + X + " Y: " + Y + " Z: " + Z;
		return temp;
	}
}
