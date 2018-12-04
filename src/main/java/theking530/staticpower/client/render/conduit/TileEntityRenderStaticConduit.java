package theking530.staticpower.client.render.conduit;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.StaticVertexBuffer;
import theking530.staticpower.conduits.TileEntityBaseConduit;
import theking530.staticpower.conduits.staticconduit.TileEntityStaticConduit;

public class TileEntityRenderStaticConduit extends TileEntityRenderBaseConduit {
	
	ResourceLocation energy = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/ConduitEnergy.png");
	ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/FluidConduit.png");
	ResourceLocation pullTexture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/FluidConduitPull.png");
	
	boolean drawInside = true;
	
	float pixel = 1F/16F;
	float texel = 1F/64F;

	@Override
	public void render(TileEntityBaseConduit tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		TileEntityStaticConduit conduit = (TileEntityStaticConduit) tileentity;

		GL11.glTranslated(translationX, translationY, translationZ);
		GL11.glDisable(GL11.GL_LIGHTING);	
		
		for(int i = 0; i < conduit.receivers.length; i++){
			if(conduit.receivers[i] != null) {
				if(conduit.SIDE_MODES[i] == 1) {
					
				}else{
				this.bindTexture(energy);
				drawConnectionEnergy(conduit.receivers[i], tileentity);
					if(conduit.SIDE_MODES[i] == 1) {
						this.bindTexture(pullTexture);
					}else{
						this.bindTexture(texture);
					}
				drawConnection(conduit.receivers[i], tileentity, 9.0f, 12.0f);
				}		
			}
		}
		if (!conduit.straightConnection(conduit.connections)) {
			this.bindTexture(energy);
			drawNodeEnergy(conduit);
			this.bindTexture(texture);
			drawNode(tileentity, 11.0f); 		
			for(int i = 0; i < conduit.connections.length; i++) {
				if(conduit.connections[i] != null) {
					this.bindTexture(energy);
					drawCoreEnergy(conduit.connections[i], conduit);
					this.bindTexture(texture);
					drawCore(conduit.connections[i], tileentity, 12.0f);
				}
			}
		} else {
			for(int i = 0; i < conduit.connections.length; i++) 
				if(conduit.connections[i] != null) {	
					this.bindTexture(energy);
					drawStraightEnergy(conduit.connections[i], conduit);
					this.bindTexture(texture);
					drawStraight(conduit.connections[i], tileentity, 12.0f);
					break;
				}
			}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
	}

	public void drawConnectionEnergy(EnumFacing direction, TileEntity tileentity) {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
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
			
			
			//Front
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 1-9.5*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 1-9.5*pixel/2, 10*texel, 10*texel);		
			
			//Right
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 1-9.5*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 9.5*pixel/2, 10*texel, 10*texel);		
	
			//Back
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 9.5*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 0*texel, 0*texel);
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 9.5*pixel/2, 10*texel, 10*texel);
	
			//Left
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 9.5*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 1-9.5*pixel/2, 10*texel, 10*texel);	
	
			//Top
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 0*texel, 10*texel);
			StaticVertexBuffer.pos(9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 9.5*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-9.5*pixel/2, 25.5*pixel/2, 1-9.5*pixel/2, 10*texel, 10*texel);
			
			//Bottom
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 1-9.5*pixel/2, 0*texel, 10*texel);	
			StaticVertexBuffer.pos(1-9.5*pixel/2, 1, 9.5*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 9.5*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(9.5*pixel/2, 1, 1-9.5*pixel/2, 10*texel, 10*texel);	

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
		

		tessellator.draw();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
	public void drawStraightEnergy(EnumFacing direction, TileEntityBaseConduit tileentity) {	
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.UP) || (direction.equals(EnumFacing.DOWN))) {
			}else if (direction.equals(EnumFacing.SOUTH) || (direction.equals(EnumFacing.NORTH))) {
				GL11.glRotatef(90, 1, 0, 0);
			}
			else if (direction.equals(EnumFacing.WEST) || (direction.equals(EnumFacing.EAST))) {
				GL11.glRotatef(90, 0, 0, 1);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);		
			
			//Front
			StaticVertexBuffer.pos(1-12.1*pixel/2, 0, 1-12.1*pixel/2, 20*texel, 10*texel);		
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 1-12.1*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 1-12.1*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 0, 1-12.1*pixel/2, 20*texel, 0*texel);			
			
			//Back
			StaticVertexBuffer.pos(12.1*pixel/2, 0, 12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 12.1*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 12.1*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 0, 12.1*pixel/2, 20*texel, 0*texel);	
			
			//Left
			StaticVertexBuffer.pos(1-12.1*pixel/2, 0, 12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 12.1*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 1-12.1*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 0, 1-12.1*pixel/2, 20*texel, 0*texel);	
			
			//Right
			StaticVertexBuffer.pos(12.1*pixel/2, 0, 1-12.1*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 1-12.1*pixel/2, 52*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 12.1*pixel/2, 52*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 0, 12.1*pixel/2, 20*texel, 10*texel);	
		}
		tessellator.draw();	
		GL11.glPopMatrix();
	}
	public void drawCoreEnergy(EnumFacing direction, TileEntityBaseConduit tileentity) {	
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.UP)) {
			//ROTATE
			}else if (direction.equals(EnumFacing.DOWN)) {
				GL11.glRotatef(180, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.SOUTH)) {
				GL11.glRotatef(90, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.NORTH)) {
				GL11.glRotatef(270, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.WEST)) {
				GL11.glRotatef(90, 0, 0, 1);
			}	
			else if (direction.equals(EnumFacing.EAST)) {
				GL11.glRotatef(270, 0, 0, 1);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			
			//Front
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1-12.1*pixel/2, 1-12.1*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 1-12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 1-12.1*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1-12.1*pixel/2, 1-12.1*pixel/2, 10*texel, 0*texel);			
			
			//Back
			StaticVertexBuffer.pos(12.1*pixel/2, 1-12.1*pixel/2, 12.1*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 12.1*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1-12.1*pixel/2, 12.1*pixel/2, 10*texel, 0*texel);	
			
			//Left
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1-12.1*pixel/2, 12.1*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1, 1-12.1*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(1-12.1*pixel/2, 1-12.1*pixel/2, 1-12.1*pixel/2, 10*texel, 0*texel);	
			
			//Right
			StaticVertexBuffer.pos(12.1*pixel/2, 1-12.1*pixel/2, 1-12.1*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 1-12.1*pixel/2, 20*texel, 0*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1, 12.1*pixel/2, 20*texel, 10*texel);	
			StaticVertexBuffer.pos(12.1*pixel/2, 1-12.1*pixel/2, 12.1*pixel/2, 10*texel, 10*texel);	
		}
		tessellator.draw();		
		GL11.glPopMatrix();
	}
	public void drawNodeEnergy(TileEntityBaseConduit tileentity)  {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertextBuffer = tessellator.getBuffer();
        vertextBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		{		
			//Front
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texel, 10*texel);	
			
			//Right
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 10*texel, 10*texel);		
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texel, 10*texel);	
			
			//Back
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 11*pixel/2, 10*texel, 10*texel);
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 10*texel, 0*texel);
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texel, 10*texel);	
			
			//Left
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texel, 10*texel);		
			
			//Top
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texel, 0*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texel, 10*texel);		
			
			//Bottom
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 10*texel, 10*texel);	
			StaticVertexBuffer.pos(11*pixel/2, 11*pixel/2, 11*pixel/2, 10*texel, 0*texel);	
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texel, 0*texel);				
			StaticVertexBuffer.pos(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texel, 10*texel);	

		}
		tessellator.draw();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}
}
