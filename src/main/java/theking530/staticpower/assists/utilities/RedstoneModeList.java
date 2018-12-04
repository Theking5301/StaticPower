package theking530.staticpower.assists.utilities;


public class RedstoneModeList {
	public enum RedstoneMode {
		Ignore,
		Low,
		High;
		
		public static RedstoneMode getModeFromInt(int mode) {
			return RedstoneMode.values()[mode];
		}
	}	
}
