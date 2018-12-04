package theking530.staticpower.assists.customboundingbox;

public class CustomBoundingBox {

	public boolean drawSide[] = { false, false, false, false, false, false };
	public double sideLength[] = { 0, 0, 0, 0, 0, 0 };
	public double middleHeight = 0;
	public double middleWidth = 0;
	public double middleDepth = 0;
	public double minX = 0;
	public double minY = 0;
	public double minZ = 0;

	public CustomBoundingBox(double middleHeight, double middleWidth, double middleDepth, double minX, double minY, double minZ) {

		this.middleDepth = middleDepth;
		this.middleHeight = middleHeight;
		this.middleWidth = middleWidth;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
	}

	public CustomBoundingBox drawSide(int side, boolean draw) {

		if (drawSide.length > side) {
			drawSide[side] = draw;
		}
		return this;
	}

	public CustomBoundingBox setSideLength(int side, double length) {

		if (sideLength.length > side) {
			sideLength[side] = length;
		}
		return this;
	}

	public CustomBoundingBox offsetForDraw(double x, double y, double z) {

		minX += x;
		minY += y;
		minZ += z;
		return this;
	}

	public CustomBoundingBox addExtraSpace(double extraSpace) {

		minX -= extraSpace;
		minY -= extraSpace;
		minZ -= extraSpace;
		middleDepth += extraSpace;
		middleHeight += extraSpace;
		middleWidth += extraSpace;

		for (int i = 0; i < sideLength.length; i++) {
			sideLength[i] += extraSpace;
		}
		return this;
	}

}