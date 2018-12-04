package theking530.staticpower.client.gui.widgets.progressbars;

import java.util.List;

import api.gui.GuiDrawUtilities;
import api.gui.IGuiWidget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.tileentity.IProcessing;

public class SquareProgressBar implements IGuiWidget {

	private boolean isVisible;
	private BaseGuiContainer owningGui;
	
	private int xPosition;
	private int yPosition;
	private int xSize;
	private int ySize;
	
	private IProcessing processingEntity;
	private int lastValue;
	private float interp;

	public SquareProgressBar(IProcessing processingEntity, int xPosition, int yPosition, int xSize, int ySize) {
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
			float seconds = processingEntity.getProcessingTime()/20.0f;
			float perSecond = partialTicks/seconds;
			interp += perSecond;
		}

		GuiDrawUtilities.drawSlot(xPosition+owningGui.getGuiLeft(), yPosition+owningGui.getGuiTop(), xSize, ySize);
		
		GlStateManager.disableLighting();
    	drawRect(xPosition+owningGui.getGuiLeft(), yPosition+owningGui.getGuiTop(), (xPosition+owningGui.getGuiLeft()+(xSize*(interp/((float)processingEntity.getProcessingTime())))), yPosition+ySize+owningGui.getGuiTop(), GuiUtilities.getColor(255, 255, 255));
		GlStateManager.enableLighting();
	}
    public static void drawRect(float left, float top, float right, float bottom, int color)
    {
        if (left < right)
        {
        	float i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
        	float j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)left, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)top, 0.0D).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
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
