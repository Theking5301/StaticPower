package theking530.staticpower.client.gui.widgets.valuebars;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import theking530.staticpower.machines.BaseMachine;

public class GuiPowerBarFromEnergyStorage {

	GuiPowerBar POWER_BAR = new GuiPowerBar();
	private BaseMachine MACHINE;
	
	public GuiPowerBarFromEnergyStorage(BaseMachine teInfuser) {
		MACHINE = teInfuser;
	}
	
	public List drawText() {
		if(MACHINE.PROCESSING_TIME == 0) {
			return POWER_BAR.drawText(MACHINE.STORAGE.getEnergyStored(), MACHINE.STORAGE.getMaxEnergyStored(), MACHINE.STORAGE.getMaxReceive(), 0);
		}
		return POWER_BAR.drawText(MACHINE.STORAGE.getEnergyStored(), MACHINE.STORAGE.getMaxEnergyStored(), MACHINE.STORAGE.getMaxReceive(), MACHINE.getProcessingCost()/MACHINE.PROCESSING_TIME);
	}
	public void drawPowerBar(int xpos, int ypos, int width, int height, float zLevel) {
		POWER_BAR.drawPowerBar(xpos, ypos, width, height, zLevel, MACHINE.STORAGE.getEnergyStored(), MACHINE.STORAGE.getMaxEnergyStored());
	}
}