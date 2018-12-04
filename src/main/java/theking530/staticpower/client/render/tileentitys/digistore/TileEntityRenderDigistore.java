package theking530.staticpower.client.render.tileentitys.digistore;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.tileentity.digistorenetwork.digistore.TileEntityDigistore;

public class TileEntityRenderDigistore extends TileEntitySpecialRenderer<TileEntityDigistore> {

	private static final ResourceLocation voidIndicator = new ResourceLocation(Reference.MOD_ID, "textures/blocks/digistore/void_indicator.png");
	private static final ResourceLocation lockedIndicator = new ResourceLocation(Reference.MOD_ID, "textures/blocks/digistore/locked_indicator.png");
	private static final ResourceLocation fillBar = new ResourceLocation(Reference.MOD_ID, "textures/blocks/digistore/digistore_fill_bar.png");
	
	@Override
	public void render(TileEntityDigistore tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {		
		EnumFacing facing = tileentity.getFacingDirection();
		
		GL11.glPushMatrix();
		GL11.glTranslated(translationX, translationY, translationZ);
		if(facing == EnumFacing.WEST) {
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glTranslated(0, 0, -1);
			}
		if(facing == EnumFacing.NORTH) {
			GL11.glRotated(180, 0, 1, 0);
			GL11.glTranslated(-1, 0, -1);
			}
		if(facing == EnumFacing.EAST) {
			GL11.glRotated(90, 0, 1, 0);
			GL11.glTranslated(-1, 0, 0);
		}
		drawItem(tileentity, translationX, translationY, translationZ, f, dest, alpha);
		drawFillBar(tileentity, translationX, translationY, translationZ, f, dest, alpha);
		drawText(tileentity, translationX, translationY, translationZ, f, dest, alpha);
		drawIndicators(tileentity, translationX, translationY, translationZ, f, dest, alpha);
		
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
		GL11.glColor3f(1.0F, 1.0F, 1.0F);	
		GL11.glPopMatrix();
	}
	
	public void drawItem(TileEntityDigistore barrel, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {	
		if(!barrel.getStoredItem().isEmpty()) {
	        GlStateManager.enableTexture2D(); 
	        
	        GlStateManager.pushMatrix();
	        RenderHelper.enableStandardItemLighting();
	        GlStateManager.disableLighting();
	        double scale = 0.02;
	        GlStateManager.scale(scale, -scale, scale/10);
	        GlStateManager.translate(17, -37, 404);
	        
	        try {
	            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(barrel.getStoredItem(), 0, 0);
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        
	        GlStateManager.disableBlend(); 
	        GlStateManager.enableAlpha(); 
	        GlStateManager.popMatrix();
		}
	}
	public void drawIndicators(TileEntityDigistore barrel, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		float offset = 0.0f;
		if(barrel.isLocked()) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPushMatrix();
			Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder vertexbuffer = tessellator.getBuffer();
	        
			Minecraft.getMinecraft().getTextureManager().bindTexture(lockedIndicator);
	        
	        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glScaled(1, 1, -1);
			GL11.glTranslated(-1, 0, 0);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			vertexbuffer.pos(0.11+offset, 0.99, 1.005).tex(1.0, 0.0).endVertex();
			vertexbuffer.pos(0.11+offset, 0.89, 1.005).tex(1.0, 1.0).endVertex();
			vertexbuffer.pos(0.01+offset, 0.89, 1.005).tex(0.0, 1.0).endVertex();
			vertexbuffer.pos(0.01+offset, 0.99, 1.005).tex(0.0, 0.0).endVertex();		
			tessellator.draw();		
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			offset += 0.11f;
		}
		if(barrel.isVoidUpgradeInstalled()) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPushMatrix();
			Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder vertexbuffer = tessellator.getBuffer();
	        
			Minecraft.getMinecraft().getTextureManager().bindTexture(voidIndicator);
	        
	        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glScaled(1, 1, -1);
			GL11.glTranslated(-1, 0, 0);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			vertexbuffer.pos(0.11+offset, 0.99, 1.005).tex(1.0, 1.0).endVertex();
			vertexbuffer.pos(0.11+offset, 0.89, 1.005).tex(1.0, 0.0).endVertex();
			vertexbuffer.pos(0.01+offset, 0.89, 1.005).tex(0.0, 0.0).endVertex();
			vertexbuffer.pos(0.01+offset, 0.99, 1.005).tex(0.0, 1.0).endVertex();		
			tessellator.draw();		
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			offset += 0.11f;
		}
	}
	public void drawItemBackground(TileEntityDigistore barrel, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.disableTexture2D();
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(-1, 0, 0);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		vertexbuffer.pos(0.75, 0.80, 1.001).color(0.2f, 0.3f, 0.4f, 1.0f).endVertex();
		vertexbuffer.pos(0.75, 0.35, 1.001).color(0.2f, 0.3f, 0.4f, 1.0f).endVertex();
		vertexbuffer.pos(0.25, 0.35, 1.001).color(0.2f, 0.3f, 0.4f, 1.0f).endVertex();
		vertexbuffer.pos(0.25, 0.80, 1.001).color(0.2f, 0.3f, 0.4f, 1.0f).endVertex();		
		tessellator.draw();		
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	public void drawFillBar(TileEntityDigistore barrel, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		float filledRatio = barrel.getFilledRatio();
		
		//BG Portion
		GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        
		Minecraft.getMinecraft().getTextureManager().bindTexture(fillBar);
        
		GL11.glPushMatrix();
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(-1, 0, 0);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		vertexbuffer.pos(.75, 0.203125, 1.0001).tex(0.0f, 0.0f).endVertex();
		vertexbuffer.pos(.75, 0.125, 1.0001).tex(0.0f, 1.0f).endVertex();
		vertexbuffer.pos(0.75-filledRatio*0.5, 0.125, 1.0001).tex(filledRatio, 1.0f).endVertex();
		vertexbuffer.pos(0.75-filledRatio*0.5, 0.203125, 1.0001).tex(filledRatio, 0.0f).endVertex();		
		tessellator.draw();		
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	public void drawText(TileEntityDigistore barrel, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.doPolygonOffset(-1, -20);

        double scale = 0.01;
        GlStateManager.scale(scale, -scale, scale);
        GlStateManager.translate(50, -30, 100);
        
        String text = "" + barrel.getStoredAmount();
        getFontRenderer().drawString(text, -getFontRenderer().getStringWidth(text) / 2, 0, (int)(255 * alpha) << 24 | 255 << 16 | 255 << 8 | 255);

        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
	}
}
