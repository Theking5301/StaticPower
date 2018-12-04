package api.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.GuiTextures;

public class GuiDrawUtilities {
	private static final float genericBackgroundPixel = 1.0f/9.0f;
	
	public static void drawGenericBackground(int width, int height, int guiLeft, int guiTop, float zLevel, Color mainTint, Color rimTint) {		
		//MainBG	
		GL11.glColor3f((float)mainTint.getRed()/255.0f, (float)mainTint.getGreen()/255.0f, (float)mainTint.getBlue()/255.0f);
		Gui.drawRect(guiLeft+3, guiTop+3, guiLeft+width-3, guiTop+height-3, GuiUtilities.getColor(mainTint.getRed(), mainTint.getGreen(), mainTint.getBlue()));
		
		GL11.glColor3f((float)rimTint.getRed()/255.0f, (float)rimTint.getGreen()/255.0f, (float)rimTint.getBlue()/255.0f);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.GENERIC_GUI);
		GL11.glEnable(GL11.GL_BLEND);
		//Corners
		drawTexturedGenericRect(guiLeft, guiTop, 4, 4, 0.0f, 0.0f, zLevel, 4*genericBackgroundPixel, 4*genericBackgroundPixel);
		drawTexturedGenericRect(guiLeft+width-5, guiTop, 5, 4, zLevel, 4*genericBackgroundPixel, 0.0f, 9*genericBackgroundPixel, 4*genericBackgroundPixel);
		drawTexturedGenericRect(guiLeft, guiTop+height-5, 4, 5, 0.0f, zLevel, 4*genericBackgroundPixel, 4*genericBackgroundPixel, 9*genericBackgroundPixel);	
		drawTexturedGenericRect(guiLeft+width-4, guiTop+height-4, 4, 4, zLevel, 5*genericBackgroundPixel, 5*genericBackgroundPixel, 9*genericBackgroundPixel, 9*genericBackgroundPixel);

		//Sides
		drawTexturedGenericRect(guiLeft+4, guiTop, width-7, 3, zLevel, 3*genericBackgroundPixel, 0.0f, 4*genericBackgroundPixel, 3*genericBackgroundPixel);
		drawTexturedGenericRect(guiLeft, guiTop+4, 3, height-8, 0.0f, zLevel, 3*genericBackgroundPixel, 3*genericBackgroundPixel, 4*genericBackgroundPixel);
		drawTexturedGenericRect(guiLeft+4, guiTop+height-3, width-8, 3, zLevel, 4*genericBackgroundPixel, 6*genericBackgroundPixel, 5*genericBackgroundPixel, 9*genericBackgroundPixel);
		drawTexturedGenericRect(guiLeft+width-3, guiTop+4, 3, height-8, zLevel, 6*genericBackgroundPixel, 3*genericBackgroundPixel, 9*genericBackgroundPixel, 4*genericBackgroundPixel);
		GL11.glDisable(GL11.GL_BLEND);
	}
	public static void drawGenericBackground(int width, int height, int guiLeft, int guiTop, float zLevel) {		
		drawGenericBackground(width, height, guiLeft, guiTop, zLevel, new Color(198, 198, 198), new Color(255, 255, 255));
	}
	public static void drawPlayerInventorySlots(int xPos, int yPos) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				drawSlot(xPos + j * 18, yPos + 1 + i * 18, 16, 16);
			}
		}
		for (int i = 0; i < 9; i++) {
			drawSlot(xPos + i * 18, yPos+59, 16, 16);
		}
	}
	public static void drawSlot(int xPos, int yPos, int width, int height, Color color) {
		GlStateManager.disableLighting();
		
		if(color != null) {
			Gui.drawRect(xPos-2, yPos-2, xPos+width+2, yPos+height+2, GuiUtilities.getColor(color.getRed(), color.getGreen(), color.getBlue()));			
		}	
		
		Gui.drawRect(xPos-1, yPos-1, xPos, yPos+height, GuiUtilities.getColor(55, 55, 55));
		Gui.drawRect(xPos, yPos-1, xPos+width, yPos, GuiUtilities.getColor(55, 55, 55));
		Gui.drawRect(xPos+width, yPos-1, xPos+width+1, yPos, GuiUtilities.getColor(139, 139, 139));
		
		Gui.drawRect(xPos-1, yPos+height, xPos, yPos+height+1, GuiUtilities.getColor(139, 139, 139));
		Gui.drawRect(xPos, yPos+height, xPos+width+1, yPos+height+1, GuiUtilities.getColor(255, 255, 255));
		Gui.drawRect(xPos+width, yPos, xPos+width+1, yPos+height+1, GuiUtilities.getColor(255, 255, 255));
		
		Gui.drawRect(xPos, yPos, xPos+width, yPos+height, GuiUtilities.getColor(139, 139, 139));
		
		if(color != null) {
			//Gui.drawRect(xPos, yPos, xPos+width, yPos+height, GuiUtilities.getColor(color.getRed(), color.getGreen(), color.getBlue(), 150));
		}

	}
	public static void drawSlot(int xPos, int yPos, int width, int height) {
		drawSlot(xPos, yPos, width, height, null);
	}
    public static void drawTexturedGenericRect(int xCoord, int yCoord, int width, int height, float zLevel, double minU, double minV, double maxU, double maxV) {
		GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(xCoord, yCoord+height, zLevel).tex(minU, maxV).endVertex();
        bufferbuilder.pos(xCoord+width, yCoord+height, zLevel).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(xCoord+width, yCoord, zLevel).tex(maxU, minV).endVertex();
        bufferbuilder.pos(xCoord, yCoord, zLevel).tex(minU, minV).endVertex();
        tessellator.draw();
    }
	public static void drawStringWithSize(String text, int xPos, int yPos, float scale, int color, boolean withShadow) {
		int textX = (int)((xPos - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) * scale) / scale) - 1;
		int textY = (int)((yPos - 7 * scale) / scale) - 1;
		
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		
		if(withShadow) {
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, textX, textY, color);
		}else{
			Minecraft.getMinecraft().fontRenderer.drawString(text, textX, textY, color);
		}	
		
		GlStateManager.popMatrix();
	}
    public static void drawTexturedModalRect(float x, float y, float width, float height, float minU, float minV, float maxU, float maxV) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), 0.0).tex((double)((float)(minU)), (double)((float)(maxV))).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((float)(maxU)), (double)((float)(maxV))).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), 0.0).tex((double)((float)(maxU)), (double)((float)(minV))).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), 0.0).tex((double)((float)(minU)), (double)((float)(minV))).endVertex();
        tessellator.draw();
    }
    public static void drawTexturedModalRect(float x, float y, float width, float height, float minU, float minV,  float maxU, float maxV, float texetSize) {
    	drawTexturedModalRect(x, y, width, height, minU*texetSize, minV*texetSize, maxU*texetSize, maxV*texetSize);
    }
}
