package api.gui;

import java.util.List;

import theking530.staticpower.client.gui.BaseGuiContainer;

public interface IGuiWidget {
	enum EInputResult {
		HANDLED, UNHANDLED
	}
	
	public void setOwningGui(BaseGuiContainer owningGui);
	
	/*Misc*/
	public boolean isVisible();
	public void setVisible(boolean isVisible);
	public void setPosition(int xPos, int yPos);
	public void setSize(int xSize, int ySize);
	
	/*Render Events*/
	public void renderBackground(int mouseX, int mouseY, float partialTicks);
	public void renderForeground(int mouseX, int mouseY, float partialTicks);
	
	/*Tooltip*/
	public boolean shouldDrawTooltip(int mouseX, int mouseY);
	public List<String> getTooltip();
	
	/*Input Events*/
	public default EInputResult mouseClick(int mouseX, int mouseY, int button) {
		return EInputResult.UNHANDLED;
	}
	public void mouseHover(int mouseX, int mouseY);
}
