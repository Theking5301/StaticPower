package theking530.staticpower.client.gui.widgets.valuebars;

import java.util.List;

import theking530.staticpower.machines.heatingelement.HeatStorage;

public class GuiHeatBarFromStorage {

	public HeatStorage STORAGE;
	public GuiHeatBar BAR;
	
	public GuiHeatBarFromStorage(HeatStorage storage) {
		STORAGE = storage;
		BAR = new GuiHeatBar();
	}
	public List<String> drawText() {
		return BAR.drawText(STORAGE.getHeat(), STORAGE.getMaxHeat());
	}
	public void drawHeatBar(double x, double y, double zLevel, double width, double height) {
		GuiHeatBar.drawHeatBar(STORAGE.getMaxHeat(), STORAGE.getHeat(), x, y, zLevel, width, height);
	}
}
