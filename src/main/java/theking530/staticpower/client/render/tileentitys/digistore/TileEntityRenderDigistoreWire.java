package theking530.staticpower.client.render.tileentitys.digistore;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.StaticVertexBuffer;
import theking530.staticpower.tileentity.digistorenetwork.networkwire.TileEntityDigistoreWire;

public class TileEntityRenderDigistoreWire extends TileEntitySpecialRenderer<TileEntityDigistoreWire> {

	private static final ResourceLocation wireTexture = new ResourceLocation(Reference.MOD_ID, "textures/blocks/digistore/wire_texture.png");
	private static final float sideBrightness = 0.7f;
	private static final float topBrightness = 1.0f;
	private static final float bottomBrightness = 0.5f;

	private static final float pixel = 1F/16F;
	private static final float texel = 1F/64F;

	@Override
	public void render(TileEntityDigistoreWire wire, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {		
		GL11.glTranslated(translationX, translationY, translationZ);

		GL11.glDisable(GL11.GL_LIGHTING);	
		//GL11.glDisable(GL11.GL_ALPHA_TEST);
		//GL11.glEnable(GL11.GL_BLEND);

		for(int i = 0; i < wire.receivers.length; i++){
			if(wire.receivers[i] != null) {
				bindTexture(wireTexture);
				drawConnection(wire.receivers[i], wire, 6.0f, 14.0f);				
			}
		}
		if (!wire.straightConnection(wire.connections)) {
			bindTexture(wireTexture);
			drawNode(wire, 13f); 			
			for(int i = 0; i < wire.connections.length; i++) {
				if(wire.connections[i] != null) {
					bindTexture(wireTexture);
					drawCore(wire.connections[i], wire, 13f);
				}
			}
		} else {
			for(int i = 0; i < wire.connections.length; i++) {
				if(wire.connections[i] != null) {	
					bindTexture(wireTexture);
					drawStraight(wire.connections[i], wire, 13f);
					break;
				}
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
	}
	public void drawConnection(EnumFacing direction, TileEntityDigistoreWire tileentity, float radius, float innerRadius) {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
        
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.DOWN)) {
				GL11.glRotatef(180, 1, 0, 0);
			}else if (direction.equals(EnumFacing.UP)) {
				GL11.glRotatef(0, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.NORTH)) {
				GL11.glRotatef(-90, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.SOUTH)) {
				GL11.glRotatef(90, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.EAST)) {
				GL11.glRotatef(270, 0, 0, 1);
			}	
			else if (direction.equals(EnumFacing.WEST)) {
				GL11.glRotatef(90, 0, 0, 1);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			float depth = 1.f;
			
			//Front
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);		
			tessellator.draw();
			
			//Right
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 10*texel);		
			tessellator.draw();
			
			//Back
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, radius*pixel/2, 0*texel, 0*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 10*texel);
			tessellator.draw();
			
			//Left
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(topBrightness, topBrightness, topBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);	
			tessellator.draw();
			
			//Top
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 28*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-radius*pixel/2, 28*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);
			tessellator.draw();
			
			//Bottom
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, bottomBrightness, bottomBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);	
			tessellator.draw();
			
				//Front
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(topBrightness, topBrightness, topBrightness);
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 9.5*pixel, 1-innerRadius*pixel/2, 20*texel, 10*texel);		
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 1, 1-innerRadius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 1, 1-innerRadius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 9.5*pixel, 1-innerRadius*pixel/2, 20*texel, 0*texel);			
				tessellator.draw();	
				
				//Back
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(topBrightness, topBrightness, topBrightness);
				StaticVertexBuffer.pos(innerRadius*pixel/2, 9.5*pixel, innerRadius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 1, innerRadius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 1, innerRadius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 9.5*pixel, innerRadius*pixel/2, 20*texel, 0*texel);	
				tessellator.draw();	
				
				//Left
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 9.5*pixel, innerRadius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 1, innerRadius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 1, 1-innerRadius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(1-innerRadius*pixel/2, 9.5*pixel, 1-innerRadius*pixel/2, 20*texel, 0*texel);	
				tessellator.draw();	
				
				//Right
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
				StaticVertexBuffer.pos(innerRadius*pixel/2, 9.5*pixel, 1-innerRadius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 1, 1-innerRadius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 1, innerRadius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(innerRadius*pixel/2, 9.5*pixel, innerRadius*pixel/2, 20*texel, 10*texel);	
				tessellator.draw();	
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawStraight(EnumFacing direction, TileEntityDigistoreWire tileentity, float radius) {		
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();

		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.UP) || (direction.equals(EnumFacing.DOWN))) {
			}else if (direction.equals(EnumFacing.SOUTH) || (direction.equals(EnumFacing.NORTH))) {
				GL11.glRotatef(90, 1, 0, 0);
			}
			else if (direction.equals(EnumFacing.WEST) || (direction.equals(EnumFacing.EAST))) {
				GL11.glRotatef(90, 0, 0, 1);
				GL11.glRotatef(-90, 0, 1, 0);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);		
			

			//Front
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(bottomBrightness, bottomBrightness, bottomBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 10*texel);		
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);			
			tessellator.draw();	
			
			//Back
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(topBrightness, topBrightness, topBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 0, radius*pixel/2, 20*texel, 0*texel);	
			tessellator.draw();	
			
			//Left
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);	
			tessellator.draw();	
			
			//Right
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
			tessellator.draw();	
			
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawCore(EnumFacing direction, TileEntityDigistoreWire tileentity, float radius) {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();

		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.UP)) {
				GL11.glRotatef(180, 0, 1, 0);
			//ROTATE
			}else if (direction.equals(EnumFacing.DOWN)) {
				GL11.glRotatef(180, 1, 0, 0);

			}	
			else if (direction.equals(EnumFacing.SOUTH)) {
				GL11.glRotatef(90, 1, 0, 0);

			}	
			else if (direction.equals(EnumFacing.NORTH)) {
				GL11.glRotatef(270, 1, 0, 0);
				GL11.glRotatef(180, 0, 1, 0);
			}	
			else if (direction.equals(EnumFacing.WEST)) {
				GL11.glRotatef(90, 0, 0, 1);
				GL11.glRotatef(-90, 0, 1, 0);
			}	
			else if (direction.equals(EnumFacing.EAST)) {
				GL11.glRotatef(270, 0, 0, 1);
				GL11.glRotatef(90, 0, 1, 0);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			
			//Front
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);			
			tessellator.draw();	
			
			//Back
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(topBrightness, topBrightness, topBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
			tessellator.draw();	
			
			//Left
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			tessellator.draw();	
			
			//Right
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
			tessellator.draw();	
		}	
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawNode(TileEntityDigistoreWire tileentity, float radius) {
				
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
		{		
			//Front
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
			tessellator.draw();	
			
			//Right
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
			tessellator.draw();	
			
			//Back
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 10*texel);	
			tessellator.draw();	
			
			//Left
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 10*texel);		
			tessellator.draw();	
			
			//Top
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(topBrightness, topBrightness, topBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);		
			tessellator.draw();	
			
			//Bottom
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);				
			StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
			tessellator.draw();		
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}
}