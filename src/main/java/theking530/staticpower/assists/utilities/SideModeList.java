package theking530.staticpower.assists.utilities;

import java.awt.Color;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class SideModeList {
	
	public enum Mode {
		Regular("Regular", EnumTextFormatting.WHITE, new Color(139, 139, 139)),
		Input("Input", EnumTextFormatting.BLUE, new Color(80, 130, 179)),
		Input2("Input2", EnumTextFormatting.DARK_PURPLE, new Color(100, 0, 200)),
		Output("Output", EnumTextFormatting.GOLD, new Color(200, 140, 50)),
		Output2("Output1", EnumTextFormatting.GREEN, new Color(0, 200, 50)),
		Output3("Output2", EnumTextFormatting.YELLOW, new Color(220, 220, 0)),
		Disabled("Disabled", EnumTextFormatting.RED, new Color(200, 20, 20));

	private String name;
    private TextFormatting fontColor;
    private Color borderColor;
    
	private Mode(String name, TextFormatting fontColor, Color borderColor) {
		this.name = name;
		this.fontColor = fontColor;
		this.borderColor = borderColor;
	}
	public TextFormatting getFontColor() {
		return fontColor;
	}
	public String getName() {
		return "mode." + name;
	}
	public String getLocalizedName() {
		return I18n.format(getName());
	}
	public Color getBorderColor() {
		return borderColor;
	}
	public boolean isInputMode() {
		return this == Input || this == Input2; 
	}
	public boolean isOutputMode() {
		return !isInputMode();
	}
	
	public static Mode getModeFromInt(int mode) {
		switch (mode) {
		case 0:
			return Regular;
		case 1:
			return Input;
		case 2:
			return Output;
		case 3:
			return Disabled;
		default:
			break;
			}
		return null;
		}
	}
}
