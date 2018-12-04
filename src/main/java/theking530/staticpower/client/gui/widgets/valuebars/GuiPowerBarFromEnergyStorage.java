package theking530.staticpower.client.gui.widgets.valuebars;

import java.util.List;

import api.gui.IGuiWidget;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.machines.TileEntityMachine;

public class GuiPowerBarFromEnergyStorage implements IGuiWidget {

	private TileEntityMachine machine;
	
	private BaseGuiContainer owningGui;
	private boolean isVisible;
	
	private int xPosition;
	private int yPosition;
	private int xSize;
	private int ySize;
	
	public GuiPowerBarFromEnergyStorage(TileEntityMachine machine, int xPosition, int yPosition, int xSize, int ySize) {
		this.machine = machine;
		isVisible = true;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	public GuiPowerBarFromEnergyStorage(TileEntityMachine machine) {
		this.machine = machine;
		isVisible = true;
	}
	@Override
	public void setOwningGui(BaseGuiContainer owningGui) {
		this.owningGui = owningGui;
	}
	@Override
	public boolean isVisible() {
		return isVisible;
	}
	@Override
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	@Override
	public void setPosition(int xPos, int yPos) {
		xPosition = xPos;
		yPosition = yPos;
	}
	@Override
	public void setSize(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
	}
	@Override
	public void renderBackground(int mouseX, int mouseY, float partialTicks) {
		GuiPowerBarUtilities.drawPowerBar(owningGui.getGuiLeft()+xPosition, owningGui.getGuiTop()+yPosition, xSize, ySize, 0.0f, machine.energyStorage.getEnergyStored(), machine.energyStorage.getMaxEnergyStored());
	}
	@Override
	public void renderForeground(int mouseX, int mouseY, float partialTicks) {}
	
	@Override
	public boolean shouldDrawTooltip(int mouseX, int mouseY) {
		if(mouseX >= owningGui.getGuiLeft()+xPosition && mouseY <= owningGui.getGuiTop()+yPosition  && mouseX <= owningGui.getGuiLeft()+xPosition+xSize && mouseY >= owningGui.getGuiTop()+yPosition-ySize ) {	
			return true; 
		}
		return false;  
	}
	@Override
	public List<String> getTooltip() {
		if(machine.processingTime == 0) {
			return GuiPowerBarUtilities.getTooltip(machine.energyStorage.getEnergyStored(), machine.energyStorage.getMaxEnergyStored(), machine.energyStorage.getMaxReceive(), 0);
		}
		return GuiPowerBarUtilities.getTooltip(machine.energyStorage.getEnergyStored(), machine.energyStorage.getMaxEnergyStored(), machine.energyStorage.getMaxReceive(), machine.getProcessingEnergy()/machine.processingTime);
	}

	@Override
	public void mouseHover(int mouseX, int mouseY) {

	}
}
