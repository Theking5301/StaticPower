package api;

public class RectangleBounds {

	private float x;
	private float y;
	private float w;
	private float h;
	
	public RectangleBounds(float xPos, float yPos, float width, float height) {
		x = xPos;
		y = yPos;
		w = width;
		h = height;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getW() {
		return w;
	}
	public float getH() {
		return h;
	}
}
