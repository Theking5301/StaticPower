package theking530.staticpower.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ModelBlock {
	
	public void drawBlock(int side) {		
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		{

			if(side == 0) {
			//Bottom
			GL11.glPushMatrix();
			GL11.glColor3f(0.5F, 0.5F, 0.5F);
			vertexbuffer.pos(0, 0, 1).tex(1, 0).endVertex();	
			vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();		
			vertexbuffer.pos(1, 0, 0).tex(0, 1).endVertex();		
			vertexbuffer.pos(1, 0, 1).tex(1, 1).endVertex();	
			GL11.glPopMatrix();
			}
			if(side == 1) {
			//Top
			GL11.glPushMatrix();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			vertexbuffer.pos(1, 1, 1).tex(0, 1).endVertex();			
			vertexbuffer.pos(1, 1, 0).tex(1, 1).endVertex();		
			vertexbuffer.pos(0, 1, 0).tex(1, 0).endVertex();		
			vertexbuffer.pos(0, 1, 1).tex(0, 0).endVertex();
			GL11.glPopMatrix();
			}
			if(side == 2) {
			//Back
			GL11.glPushMatrix();
			GL11.glColor3f(0.8F, 0.8F, 0.8F);
			vertexbuffer.pos(0, 0, 0).tex(1, 1).endVertex();	
			vertexbuffer.pos(0, 1, 0).tex(1, 0).endVertex();	
			vertexbuffer.pos(1, 1, 0).tex(0, 0).endVertex();	
			vertexbuffer.pos(1, 0, 0).tex(0, 1).endVertex();	
			GL11.glPopMatrix();
			}
			if(side == 3) {
			//Front
			GL11.glPushMatrix();
			GL11.glColor3f(0.8F, 0.8F, 0.8F);
			vertexbuffer.pos(0, 1, 1).tex(0, 0).endVertex();
			vertexbuffer.pos(0, 0, 1).tex(0, 1).endVertex();	
			vertexbuffer.pos(1, 0, 1).tex(1, 1).endVertex();	
			vertexbuffer.pos(1, 1, 1).tex(1, 0).endVertex();	
			GL11.glPopMatrix();
			}	
			if(side == 4) {
			//Right
			GL11.glPushMatrix();
			GL11.glColor3f(0.6F, 0.6F, 0.6F);
			vertexbuffer.pos(0, 0, 1).tex(1, 1).endVertex();	
			vertexbuffer.pos(0, 1, 1).tex(1, 0).endVertex();	
			vertexbuffer.pos(0, 1, 0).tex(0, 0).endVertex();	
			vertexbuffer.pos(0, 0, 0).tex(0, 1).endVertex();	
			GL11.glPopMatrix();
			}
			if(side == 5) {
			//Left
			GL11.glPushMatrix();
			GL11.glColor3f(0.6F, 0.6F, 0.6F);
			vertexbuffer.pos(1, 0, 0).tex(1, 1).endVertex();	
			vertexbuffer.pos(1, 1, 0).tex(1, 0).endVertex();	
			vertexbuffer.pos(1, 1, 1).tex(0, 0).endVertex();	
			vertexbuffer.pos(1, 0, 1).tex(0, 1).endVertex();	
			GL11.glPopMatrix();
			}
		}
		tessellator.draw();		
	}
}
