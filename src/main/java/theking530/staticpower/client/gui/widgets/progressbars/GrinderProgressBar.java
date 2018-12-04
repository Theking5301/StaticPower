package theking530.staticpower.client.gui.widgets.progressbars;

import java.util.List;

import api.gui.GuiDrawUtilities;
import api.gui.IGuiWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.tileentity.IProcessing;

public class GrinderProgressBar implements IGuiWidget {

	private boolean isVisible;
	private BaseGuiContainer owningGui;
	
	private int xPosition;
	private int yPosition;
	private int xSize;
	private int ySize;
	
	private IProcessing processingEntity;
	private int lastValue;
	private float interp;

	public GrinderProgressBar(IProcessing processingEntity, int xPosition, int yPosition, int xSize, int ySize) {
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
		interp = Math.min(interp, processingEntity.getProcessingTime());
		float xPosition = owningGui.getGuiLeft() + this.xPosition;
		float yPosition = owningGui.getGuiTop() + this.yPosition;
		float adjustedProgress = 0.9f*interp/(float)processingEntity.getProcessingTime();
		
		GlStateManager.disableLighting();
        GlStateManager.enableBlend();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.GRINDER_PROGRESS_BAR);	
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize, ySize, 0.25f, 0.0f, 0.75f, 0.5f);
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize, ySize*adjustedProgress, 0.25f, 0.5f, 0.75f, 0.5f + (0.5f * adjustedProgress));

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
