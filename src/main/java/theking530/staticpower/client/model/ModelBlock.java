package theking530.staticpower.client.model;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ModelBlock {
	@SuppressWarnings("unused")
	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);
	
	public void drawPreviewCube(Color tint, float xRadius, float yRadius, float zRadius) {
		float xOffset = -(xRadius-1)/2.0f;
		float yOffset = -(yRadius-1)/2.0f;
		float zOffset = -(zRadius-1)/2.0f;
		
		GL11.glTranslatef(xOffset, yOffset, zOffset);
		GL11.glScalef(xRadius, Math.max(yRadius, 1.01f), zRadius);
		GlStateManager.disableTexture2D();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		for(int i=0; i<6; i++) {
			drawPreviewSide(i, tint);
		}
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
	}
	public void drawPreviewSide(int side, Color tint) {		
		float red = tint.getRed()/255.0f;
		float blue = tint.getBlue()/255.0f;
		float green = tint.getGreen()/255.0f;
		float alpha = tint.getAlpha()/255.0f;
		
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		{

			if(side == 0) {
			//Bottom
				GL11.glPushMatrix();
				GL11.glColor4f(red/2.0f, green/2.0f, blue/2.0f, alpha);
				vertexbuffer.pos(0, 0, 1).endVertex();	
				vertexbuffer.pos(0, 0, 0).endVertex();		
				vertexbuffer.pos(1, 0, 0).endVertex();		
				vertexbuffer.pos(1, 0, 1).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 1) {
			//Top
				GL11.glPushMatrix();
				GL11.glColor4f(red, green, blue, alpha);
				vertexbuffer.pos(1, 1, 1).endVertex();			
				vertexbuffer.pos(1, 1, 0).endVertex();		
				vertexbuffer.pos(0, 1, 0).endVertex();		
				vertexbuffer.pos(0, 1, 1).endVertex();
				GL11.glPopMatrix();
			}
			if(side == 2) {
			//Back
				GL11.glPushMatrix();
				GL11.glColor4f(red/1.8f, green/1.8f, blue/1.8f, alpha);
				vertexbuffer.pos(0, 0, 0).endVertex();	
				vertexbuffer.pos(0, 1, 0).endVertex();	
				vertexbuffer.pos(1, 1, 0).endVertex();	
				vertexbuffer.pos(1, 0, 0).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 3) {
			//Front
				GL11.glPushMatrix();
				GL11.glColor4f(red/1.6f, green/1.6f, blue/1.6f, alpha);
				vertexbuffer.pos(0, 1, 1).endVertex();
				vertexbuffer.pos(0, 0, 1).endVertex();	
				vertexbuffer.pos(1, 0, 1).endVertex();	
				vertexbuffer.pos(1, 1, 1).endVertex();	
				GL11.glPopMatrix();
			}	
			if(side == 4) {
			//Right
				GL11.glPushMatrix();
				GL11.glColor4f(red/2.0f, green/2.0f, blue/2.0f, alpha);
				vertexbuffer.pos(0, 0, 1).endVertex();	
				vertexbuffer.pos(0, 1, 1).endVertex();	
				vertexbuffer.pos(0, 1, 0).endVertex();	
				vertexbuffer.pos(0, 0, 0).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 5) {
			//Left
				GL11.glPushMatrix();
				GL11.glColor4f(red/2.0f, green/2.0f, blue/2.0f, alpha);
				vertexbuffer.pos(1, 0, 0).endVertex();	
				vertexbuffer.pos(1, 1, 0).endVertex();	
				vertexbuffer.pos(1, 1, 1).endVertex();	
				vertexbuffer.pos(1, 0, 1).endVertex();	
				GL11.glPopMatrix();
			}
		}
		tessellator.draw();		
	}
	public void drawBlock(int side) {	
		drawBlock(side, 0.0f);
	}
	public void drawBlock(int side, float offset) {		
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		{
			if(side == 0) {
			//Bottom
				GL11.glPushMatrix();
				GL11.glColor3f(0.5F, 0.5F, 0.5F);
				vertexbuffer.pos(0, 0-offset, 1).tex(1, 0).endVertex();	
				vertexbuffer.pos(0, 0-offset, 0).tex(0, 0).endVertex();		
				vertexbuffer.pos(1, 0-offset, 0).tex(0, 1).endVertex();		
				vertexbuffer.pos(1, 0-offset, 1).tex(1, 1).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 1) {
			//Top
				GL11.glPushMatrix();
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				vertexbuffer.pos(1, 1+offset, 1).tex(0, 1).endVertex();			
				vertexbuffer.pos(1, 1+offset, 0).tex(1, 1).endVertex();		
				vertexbuffer.pos(0, 1+offset, 0).tex(1, 0).endVertex();		
				vertexbuffer.pos(0, 1+offset, 1).tex(0, 0).endVertex();
				GL11.glPopMatrix();
			}
			if(side == 2) {
			//Back
				GL11.glPushMatrix();
				GL11.glColor3f(0.55F, 0.55F, 0.55F);
				vertexbuffer.pos(0, 0, 0-offset).tex(1, 1).endVertex();	
				vertexbuffer.pos(0, 1, 0-offset).tex(1, 0).endVertex();	
				vertexbuffer.pos(1, 1, 0-offset).tex(0, 0).endVertex();	
				vertexbuffer.pos(1, 0, 0-offset).tex(0, 1).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 3) {
			//Front
				GL11.glPushMatrix();
				GL11.glColor3f(0.6F, 0.6F, 0.6F);
				vertexbuffer.pos(0, 1, 1+offset).tex(0, 0).endVertex();
				vertexbuffer.pos(0, 0, 1+offset).tex(0, 1).endVertex();	
				vertexbuffer.pos(1, 0, 1+offset).tex(1, 1).endVertex();	
				vertexbuffer.pos(1, 1, 1+offset).tex(1, 0).endVertex();	
				GL11.glPopMatrix();
			}	
			if(side == 4) {
			//Right
				GL11.glPushMatrix();
				GL11.glColor3f(0.5F, 0.5F, 0.5F);
				vertexbuffer.pos(0-offset, 0, 1).tex(1, 1).endVertex();	
				vertexbuffer.pos(0-offset, 1, 1).tex(1, 0).endVertex();	
				vertexbuffer.pos(0-offset, 1, 0).tex(0, 0).endVertex();	
				vertexbuffer.pos(0-offset, 0, 0).tex(0, 1).endVertex();	
				GL11.glPopMatrix();
			}
			if(side == 5) {
			//Left
				GL11.glPushMatrix();
				GL11.glColor3f(0.5F, 0.5F, 0.5F);
				vertexbuffer.pos(1+offset, 0, 0).tex(1, 1).endVertex();	
				vertexbuffer.pos(1+offset, 1, 0).tex(1, 0).endVertex();	
				vertexbuffer.pos(1+offset, 1, 1).tex(0, 0).endVertex();	
				vertexbuffer.pos(1+offset, 0, 1).tex(0, 1).endVertex();	
				GL11.glPopMatrix();
			}
		}
		tessellator.draw();		
	}
}
