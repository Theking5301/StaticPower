package theking530.staticpower.client.gui.widgets.valuebars;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import api.gui.GuiDrawUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import theking530.staticpower.client.gui.GuiTextures;

public class GuiPowerBarUtilities {

	public static List<String> getTooltip(int currentEnergy, int maxEnergy, int energyPerTick, int powerUse) {
    	String text = ("Input: " + energyPerTick + " RF/t");
    	if(powerUse > 0) {
    		text += "=" + "Usage: " + powerUse + " RF/t";
    	}
    	text += "=" + NumberFormat.getNumberInstance(Locale.US).format(currentEnergy)  + "/" + NumberFormat.getNumberInstance(Locale.US).format(maxEnergy) + " " + "RF";
    	String[] splitMsg = text.split("=");
		return Arrays.asList(splitMsg);
	}
	public static void drawPowerBar(int xpos, int ypos, int width, int height, float zLevel, int currentEnergy, int maxEnergy) {
		float u1 = (float)currentEnergy/(float)maxEnergy;
		float k1 = u1 * height;

		
		GuiDrawUtilities.drawSlot(xpos, ypos-height, width, height);
		
		float glowState = getPowerBarGlow();
		GlStateManager.disableLighting();
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.POWER_BAR_BG);
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xpos + width, ypos, zLevel).tex(1,0).endVertex();
		vertexbuffer.pos(xpos + width, ypos - height, zLevel).tex(1,1.0).endVertex();
		vertexbuffer.pos(xpos, ypos - height, zLevel).tex(0,1.0).endVertex();
		vertexbuffer.pos(xpos, ypos, zLevel).tex(0,0).endVertex();	
		tessellator.draw();

		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.POWER_BAR_FG);
		GlStateManager.color(glowState, glowState, glowState);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xpos + width, ypos, zLevel).tex(1,0).endVertex();
		vertexbuffer.pos(xpos + width, ypos - k1, zLevel).tex(1,u1).endVertex();
		vertexbuffer.pos(xpos, ypos - k1, zLevel).tex(0,u1).endVertex();
		vertexbuffer.pos(xpos, ypos, zLevel).tex(0,0).endVertex();	
		tessellator.draw();

		GlStateManager.color(1.0f, 1.0f, 1.0f);

		GlStateManager.enableLighting();
	}
	private static float getPowerBarGlow() {
		float sin = (float)(Math.sin((float)Minecraft.getSystemTime() / 1000.0f));

		sin = Math.abs(sin);
		sin += 3;
		sin /= 6.0f;
		return sin*1.5f;
	}
}	
