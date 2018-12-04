package theking530.staticpower.client.render.tileentitys.logicgates;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.assists.utilities.Vector3;
import theking530.staticpower.logic.gates.led.TileEntityLED;

public class TileEntityRenderLED extends TileEntitySpecialRenderer<TileEntityLED> {

    private final float PIXEL = 1f/16f;
    ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/blank_texture.png");
    
	@Override
	public void render(TileEntityLED LED, double x, double y, double z, float f, int dest, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);		
		GL11.glPushMatrix();

		int orientation = LED.getBlockMetadata();
		
		switch(orientation) {
		 case 0:			 
			 break;
		 case 1:
			 GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
			 GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			 break;
		 case 2:
			 GL11.glRotatef(-90, 0.0F, 0.0F, 1.0F);
			 GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
			 break;
		 case 3:
			 GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
			 GL11.glTranslatef(0.0F, 0.0F-2*PIXEL, 0.0F);
			 break;
		 case 4:
			 GL11.glRotatef(-90, 0.0F, 0.0F, 1.0F);
			 GL11.glTranslatef(-1.0F, 1.0F-2*PIXEL, 0.0F);
			 break;
		 case 5: 
			 GL11.glTranslatef(0.0F, 1.0F-2*PIXEL, 0.0F);	
			 break;
		 }
		
		GL11.glDisable(GL11.GL_LIGHTING);
		bindTexture(TEXTURE);
		renderBlock(LED);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	public void renderBlock(TileEntityLED LED) {
		Vector3 vec3 = GuiUtilities.getColor(LED.COLOR);
		float POWER = ((float)LED.LIGHT_LEVEL+1f)/16f;
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		//Bottom
		GL11.glPushMatrix();
		GL11.glColor3f(((float)vec3.getX()/255f)*POWER, ((float)vec3.getY()/255f)*POWER, ((float)vec3.getZ()/255f)*POWER);
		vertexbuffer.pos(0, 0, 1).tex(1, 0).endVertex();	
		vertexbuffer.pos(0, 0, 0).tex(0, 0).endVertex();		
		vertexbuffer.pos(1, 0, 0).tex(0, 1).endVertex();		
		vertexbuffer.pos(1, 0, 1).tex(1, 1).endVertex();	
		//Top
		vertexbuffer.pos(1, PIXEL*2, 1).tex(0, 1).endVertex();			
		vertexbuffer.pos(1, PIXEL*2, 0).tex(1, 1).endVertex();		
		vertexbuffer.pos(0, PIXEL*2, 0).tex(1, 0).endVertex();		
		vertexbuffer.pos(0, PIXEL*2, 1).tex(0, 0).endVertex();
		//Back
		vertexbuffer.pos(0, 0, 0).tex(1, 1).endVertex();	
		vertexbuffer.pos(0, PIXEL*2, 0).tex(1, 0).endVertex();	
		vertexbuffer.pos(1, PIXEL*2, 0).tex(0, 0).endVertex();	
		vertexbuffer.pos(1, 0, 0).tex(0, 1).endVertex();	
		//Front
		vertexbuffer.pos(0, PIXEL*2, 1).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 0, 1).tex(0, 1).endVertex();	
		vertexbuffer.pos(1, 0, 1).tex(1, 1).endVertex();	
		vertexbuffer.pos(1, PIXEL*2, 1).tex(1, 0).endVertex();	
		//Right
		vertexbuffer.pos(0, 0, 1).tex(1, 1).endVertex();	
		vertexbuffer.pos(0, PIXEL*2, 1).tex(1, 0).endVertex();	
		vertexbuffer.pos(0, PIXEL*2, 0).tex(0, 0).endVertex();	
		vertexbuffer.pos(0, 0, 0).tex(0, 1).endVertex();	
		//Left
		vertexbuffer.pos(1, 0, 0).tex(1, 1).endVertex();	
		vertexbuffer.pos(1, PIXEL*2, 0).tex(1, 0).endVertex();	
		vertexbuffer.pos(1, PIXEL*2, 1).tex(0, 0).endVertex();	
		vertexbuffer.pos(1, 0, 1).tex(0, 1).endVertex();	
		GL11.glPopMatrix();
		tessellator.draw();	
		GL11.glColor3f(1f, 1f, 1f);
	}
}



