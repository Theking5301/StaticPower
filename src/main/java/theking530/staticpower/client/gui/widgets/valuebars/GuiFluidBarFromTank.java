package theking530.staticpower.client.gui.widgets.valuebars;

import java.util.List;

import api.gui.IGuiWidget;
import net.minecraftforge.fluids.IFluidTank;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.tileentity.ISideConfigurable;

public class GuiFluidBarFromTank implements IGuiWidget{
	
	IFluidTank tank;
	private boolean isVisible;
	private int xPosition;
	private int yPosition;
	private int xSize;
	private int ySize;
	private BaseGuiContainer owningGui;
	
	private Mode mode;
	private ISideConfigurable sideConfigurable;
	
	public GuiFluidBarFromTank(IFluidTank tank, int xPosition, int yPosition, int xSize, int ySize, Mode mode, ISideConfigurable sideConfigurable) {
		this.tank = tank;
		this.isVisible = true;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xSize = xSize;
		this.ySize = ySize;
		this.mode = mode;
		this.sideConfigurable = sideConfigurable;
	}
	public GuiFluidBarFromTank(IFluidTank tank, int xPosition, int yPosition, int xSize, int ySize) {
		this(tank, xPosition, yPosition, xSize, ySize, null, null);
	}
	public GuiFluidBarFromTank(IFluidTank tank) {
		this(tank, 0, 0, 0, 0);
	}
	
	@Override
	public void setOwningGui(BaseGuiContainer owningGui) {
		this.owningGui = owningGui;		
	}
	public List<String> drawText() {
		return GuiFluidBarUtilities.getTooltip(tank.getFluidAmount(), tank.getCapacity(), tank.getFluid());
	}
	public void drawFluidBar(int xpos, int ypos, int width, int height, float zLevel) {  

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
		if(sideConfigurable != null && mode != null) {
			if(sideConfigurable.getSideWithModeCount(mode) > 0) {
				GuiFluidBarUtilities.drawFluidBar(tank.getFluid(),tank.getCapacity(), tank.getFluidAmount(),owningGui.getGuiLeft()+xPosition, owningGui.getGuiTop()+yPosition, 0.0f, xSize, ySize, mode, true);	
				return;
			}
		}
		GuiFluidBarUtilities.drawFluidBar(tank.getFluid(),tank.getCapacity(), tank.getFluidAmount(),owningGui.getGuiLeft()+xPosition, owningGui.getGuiTop()+yPosition, 0.0f, xSize, ySize, true);	
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
		return GuiFluidBarUtilities.getTooltip(tank.getFluidAmount(), tank.getCapacity(), tank.getFluid());
	}
	@Override
	public void mouseHover(int mouseX, int mouseY) {		
	}
}
