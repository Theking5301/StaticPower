package theking530.staticpower.client.gui.widgets.progressbars;

import java.util.List;

import api.gui.GuiDrawUtilities;
import api.gui.IGuiWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.tileentity.IProcessing;

public class ArrowProgressBar implements IGuiWidget {

	private boolean isVisible;
	private BaseGuiContainer owningGui;
	
	private int xPosition;
	private int yPosition;
	private int xSize;
	private int ySize;
	
	private IProcessing processingEntity;
	private int lastValue;
	private float interp;

	public ArrowProgressBar(IProcessing processingEntity, int xPosition, int yPosition, int xSize, int ySize) {
		isVisible = true;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xSize = xSize;
		this.ySize = ySize;
		this.processingEntity = processingEntity;
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
		if(lastValue != processingEntity.getCurrentProgress()) {
			lastValue = processingEntity.getCurrentProgress();
			interp = lastValue;
		}
		if(processingEntity.isProcessing()) {
			double seconds = processingEntity.getProcessingTime()/20.0;
			double perSecond = (partialTicks)/seconds;
			interp += perSecond;
		}
		float xPosition = owningGui.getGuiLeft() + this.xPosition;
		float yPosition = owningGui.getGuiTop() + this.yPosition;
		float adjustedProgress = 0.6875f*interp/(float)processingEntity.getProcessingTime();
		
		GlStateManager.disableLighting();
        GlStateManager.enableBlend();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.ARROW_PROGRESS_BAR);	
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize, ySize, 0, 0.5f, 1.0f, 1.0f);
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize*adjustedProgress, ySize, 0, 0, adjustedProgress, 0.5f);

		GlStateManager.enableLighting();
        GlStateManager.disableBlend();
	}
	@Override
	public void renderForeground(int mouseX, int mouseY, float partialTicks) {
		
	}
	@Override
	public boolean shouldDrawTooltip(int mouseX, int mouseY) {
		return false;
	}
	@Override
	public List<String> getTooltip() {
		return null;
	}
	@Override
	public void mouseHover(int mouseX, int mouseY) {
		
	}
}
