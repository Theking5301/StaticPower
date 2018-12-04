package theking530.staticpower.assists.utilities;

public class StaticMath {

	
	public static int clamp(int value, int top, int bottom) {
		if(value < top || value > bottom) {
			return value;
		}else if(value > top) {
			return top;
		}else if(value < bottom) {
			return bottom;
		}
		return value;
	}
	public static int clamp(int value, int bottom) {
		if(value < bottom) {
			return bottom;
		}
		return value;
	}
}
