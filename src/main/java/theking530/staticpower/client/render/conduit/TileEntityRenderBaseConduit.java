package theking530.staticpower.client.render.conduit;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import theking530.staticpower.assists.utilities.StaticVertexBuffer;
import theking530.staticpower.conduits.TileEntityBaseConduit;

public class TileEntityRenderBaseConduit extends TileEntitySpecialRenderer<TileEntityBaseConduit> {

	boolean drawInside = true;
	
	float pixel = 1F/16F;
	float texel = 1F/64F;

	float sideBrightness = 0.7f;
	float topBrightness = 1.0f;
	float bottomBrightness = 0.5f;
	float backBrightness = 0.7f;
	
	
	public void drawConnection(EnumFacing direction, TileEntityBaseConduit tileentity, float radius, float innerRadius) {
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
			
			if(tileentity.GRID != null) {
				//GL11.glColor3f(tileentity.GRID.getColor()[0], tileentity.GRID.getColor()[1], tileentity.GRID.getColor()[2]);
			}
			float depth = 1.f;
			
			//Front
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);		
			tessellator.draw();
			
			//Right
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 10*texel);		
			tessellator.draw();
			
			//Back
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, radius*pixel/2, 0*texel, 0*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 10*texel);
			tessellator.draw();
			
			//Left
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(topBrightness, topBrightness, topBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);	
			tessellator.draw();
			
			//Top
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(radius*pixel/2, 25*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, radius*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-radius*pixel/2, 25*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);
			tessellator.draw();
			
			//Bottom
	        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			GL11.glColor3f(sideBrightness, bottomBrightness, bottomBrightness);
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-radius*pixel/2, 1+pixel*depth, radius*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, radius*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(radius*pixel/2, 1+pixel*depth, 1-radius*pixel/2, 10*texel, 10*texel);	
			tessellator.draw();
			
	    vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);	
		GL11.glColor3f(sideBrightness, sideBrightness, sideBrightness);
		//Front
		StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 10*texel);		
		StaticVertexBuffer.pos(1-12*pixel/2, 1, 1-12*pixel/2, 20*texel, 10*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);			
		
		//Back
		StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
		StaticVertexBuffer.pos(1-12*pixel/2, 1, 12*pixel/2, 20*texel, 0*texel);	
		StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 0*texel);	
		
		//Left
		StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
		StaticVertexBuffer.pos(1-12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
		StaticVertexBuffer.pos(1-12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);	
		StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);	
		
		//Right
		StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
		StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
		
		if(drawInside) {
			//Front
			StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12*pixel/2, 1, 1-12*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 10*texel);		

			//Back
			StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12*pixel/2, 1, 12*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
	
			//Left
			StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);
			StaticVertexBuffer.pos(1-12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
		
			//Right
			StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 12*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1, 12*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1, 1-12*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(12*pixel/2, 1-12*pixel/2, 1-12*pixel/2, 10*texel, 0*texel);	
			
			/*
			//Bottom
			StaticVertexBuffer.pos(11*pixel/2, 1, 1-8*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1, 8*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 1, 8*pixel/2, 0*texel, 0*texel);				
			StaticVertexBuffer.pos(1-11*pixel/2, 1, 1-8*pixel/2, 0*texel, 10*texel);
			*/
		}
		tessellator.draw();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawStraight(EnumFacing direction, TileEntityBaseConduit tileentity, float radius) {
		
		if(tileentity.GRID != null) {
			//GL11.glColor3f(tileentity.GRID.getColor()[0]*tileentity.SELECTED, tileentity.GRID.getColor()[1]*tileentity.SELECTED, tileentity.GRID.getColor()[2]*tileentity.SELECTED);
		}

		
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
			
			if(drawInside) {
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				//Front
				StaticVertexBuffer.pos(radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 10*texel);		
	
				//Back
				StaticVertexBuffer.pos(1-radius*pixel/2, 0, radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 52*texel, 0*texel);
				StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
				
				//Left
				StaticVertexBuffer.pos(1-radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
	
				//Right
				StaticVertexBuffer.pos(radius*pixel/2, 0, radius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 52*texel, 10*texel);
				StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 52*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 0, 1-radius*pixel/2, 20*texel, 0*texel);	
				tessellator.draw();	
			}
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawCore(EnumFacing direction, TileEntityBaseConduit tileentity, float radius) {
		
		if(tileentity.GRID != null) {
			//GL11.glColor3f(tileentity.GRID.getColor()[0]*tileentity.SELECTED, tileentity.GRID.getColor()[1]*tileentity.SELECTED, tileentity.GRID.getColor()[2]*tileentity.SELECTED);
		}

		
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
			
			if(drawInside) {
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				//Front
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);		

				//Back
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
		
				//Left
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);
				StaticVertexBuffer.pos(1-radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
			
				//Right
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, radius*pixel/2, 20*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1, 1-radius*pixel/2, 20*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
				tessellator.draw();	
			}
		}	
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawNode(TileEntityBaseConduit tileentity, float radius) {
		
		if(tileentity.GRID != null) {
			//GL11.glColor3f(tileentity.GRID.getColor()[0]*tileentity.SELECTED, tileentity.GRID.getColor()[1]*tileentity.SELECTED, tileentity.GRID.getColor()[2]*tileentity.SELECTED);
		}

		
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
			
			if(drawInside) {
		        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				//Front
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);		
				
				//Right
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 0*texel);
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);		

				//Back
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 10*texel);
	
				//Left
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 10*texel);		
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	

				//Top
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, 1-radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	
				
				//Bottom
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 0*texel, 10*texel);	
				StaticVertexBuffer.pos(1-radius*pixel/2, radius*pixel/2, radius*pixel/2, 0*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, radius*pixel/2, 10*texel, 0*texel);	
				StaticVertexBuffer.pos(radius*pixel/2, radius*pixel/2, 1-radius*pixel/2, 10*texel, 10*texel);	
				tessellator.draw();	
				
			}
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}
}
