package theking530.staticcore.gui.drawables;

public interface IDrawable {
	default void draw(float x, float y) {
		draw(x, y, 0.0f);
	}

	public void draw(float x, float y, float z);
}