package theking530.staticpower.assists.utilities;

import java.text.NumberFormat;
import java.util.Locale;

import theking530.staticpower.energy.StaticEnergyStorage;

public class GuiUtilities {

	public static int getColor(int R, int G, int B, int A) {
		return A << 24 | R << 16 | G << 8 | B;
	}
	public static int getColor(int R, int G, int B) {
		return 255 << 24 | R << 16 | G << 8 | B;
	}
	public static Vector3 getColor(Color color) {
		switch(color) {
		case BLACK: return new Vector3(30,30,30);
		case RED: return new Vector3(255,0,0);
		case GREEN: return new Vector3(0,190,0);
		case BROWN: return new Vector3(90,70,0);
		case DARK_BLUE: return new Vector3(0,0,255);
		case PURPLE: return new Vector3(175,0,255);
		case CYAN: return new Vector3(0,242,255);
		case LIGHT_GRAY: return new Vector3(200,200,200);
		case GRAY: return new Vector3(110,110,110);
		case PINK: return new Vector3(255,166,206);
		case LIME: return new Vector3(0,255,76);
		case YELLOW: return new Vector3(255,255,0);
		case LIGHT_BLUE: return new Vector3(97,165,255);
		case MAGENTA: return new Vector3(255,0,191);
		case ORANGE: return new Vector3(255,174,0);
		case WHITE: return new Vector3(255,255,255);
		}
		return new Vector3(255,255,255);
	}
	public static String getStoredPowerFormatted(StaticEnergyStorage Storage) {
		return NumberFormat.getNumberInstance(Locale.US).format(Storage.getEnergyStored()) + "/" + NumberFormat.getNumberInstance(Locale.US).format(Storage.getMaxEnergyStored()) + " RF";
	}
	public static String getMaxExtractFormatted(StaticEnergyStorage Storage) {
		return NumberFormat.getNumberInstance(Locale.US).format(Storage.getMaxExtract()) + " RF/t";
	}
	public static String getMaxRecieveFormatted(StaticEnergyStorage Storage) {
		return NumberFormat.getNumberInstance(Locale.US).format(Storage.getMaxReceive()) + " RF/t";
	}
	public static String formatIntegerWithCommas(int value) {
		return NumberFormat.getNumberInstance(Locale.US).format(value);
	}
}
