package theking530.staticpower.client.gui.widgets.valuebars;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidRegistry;
import theking530.staticpower.assists.utilities.RenderUtil;

public class GuiHeatBar {
	
	public List<String> drawText(int temperature, int maxTemperature) {
		String text = ("Heat" + "=" + NumberFormat.getNumberInstance(Locale.US).format(temperature) + "/" + NumberFormat.getNumberInstance(Locale.US).format(maxTemperature));
		String[] splitMsg = text.split("=");
		return Arrays.asList(splitMsg);
	}

	public static void drawHeatBar(int maxHeat, int heat, double x, double y, double zLevel, double width, double height) {
		    TextureAtlasSprite icon = RenderUtil.getStillTexture(FluidRegistry.LAVA);

		    float ratio = ((float)heat/(float)maxHeat);
		    float renderAmount = ratio * (float) height;
		    RenderUtil.bindBlockTexture();	    
		    GlStateManager.enableBlend();    
			GlStateManager.color(1.0f, 1.0f, 1.0f);
	        double minU = icon.getMinU();
	        double maxU = icon.getMaxU();
	        double minV = icon.getMinV();
	        double maxV = icon.getMaxV();
	        double diffV = maxV - minV;
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder tes = tessellator.getBuffer();
	        tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			//Minecraft.getMinecraft().getTextureManager().bindTexture(powerBar);
			tes.pos(x + width, y, zLevel).tex(maxU,minV).endVertex();
			tes.pos(x + width, y - renderAmount, zLevel).tex(maxU, minV+(diffV*ratio)).endVertex();
			tes.pos(x, y - renderAmount, zLevel).tex(minU, minV+(diffV*ratio)).endVertex();
			tes.pos(x, y, zLevel).tex(minU,minV).endVertex();	
	        tessellator.draw();


		    GlStateManager.disableBlend();
	}
}
