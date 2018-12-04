package theking530.staticpower.client.render.conduit;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.conduits.TileEntityBaseConduit;
import theking530.staticpower.conduits.fluidconduit.TileEntityFluidConduit;

public class TileEntityRenderFluidConduit extends TileEntityRenderBaseConduit {
	
	ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/FluidConduit.png");
	ResourceLocation pullTexture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/FluidConduitPull.png");
	boolean drawInside = false;
	
	float pixel = 1F/16F;
	float texel = 1F/64F;
	static float width = .1F;

	@Override
	public void render(TileEntityBaseConduit tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		TileEntityFluidConduit conduit = (TileEntityFluidConduit)tileentity;
		FluidStack fluidStack = conduit.TANK.getFluid();	
		
		GL11.glTranslated(translationX, translationY, translationZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		
			//Draw
		if(conduit.getBlockMetadata() != 2) {
			for(int i = 0; i < conduit.receivers.length; i++){
				if(conduit.receivers[i] != null){
					if(conduit.getBlockMetadata() == 1) {
						this.bindTexture(pullTexture);
					}else{
						this.bindTexture(texture);
					}
					drawConnection(conduit.receivers[i], tileentity, 9.0f, 12.0f);
					if(fluidStack != null) {
						bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
						//drawConnectionLiquid(conduit.receivers[i], tileentity, fluidStack);
					}
				break;
				}
			}
		}
			if (!conduit.straightConnection(conduit.connections)) {
				this.bindTexture(texture);
				drawNode(tileentity, 11.0f); 			
				if(fluidStack != null) {
					bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					//drawNodeLiquid(tileentity, fluidStack);
				}
				for(int i = 0; i < conduit.connections.length; i++) {
					if(conduit.connections[i] != null) {
						this.bindTexture(texture);
						drawCore(conduit.connections[i], tileentity, 12.0f);
						if(fluidStack != null) {
							bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
							//drawCoreLiquid(conduit.connections[i], tileentity, fluidStack);
						}
					}
				}
			} else {
				for(int i = 0; i < conduit.connections.length; i++) 
					if(conduit.connections[i] != null) {	
						this.bindTexture(texture);
						drawStraight(conduit.connections[i], tileentity, 12.0f);
						if(fluidStack != null) {
							bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
							//drawStraightLiquid(conduit.connections[i], tileentity, fluidStack);
						}
						break;
					}
				}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
	}
	
	/**
	public void drawStraightLiquid(EnumFacing direction, TileEntity tileentity, FluidStack fluidStack) {	
		TileEntityFluidConduit conduit = (TileEntityFluidConduit) tileentity;
		float liquidHeight = (conduit.getAdjustedVolume(1) * 2*pixel/2) + 1*pixel/2;
		
		final Fluid fluid = fluidStack.getFluid();
		IIcon texture = fluid.getStillIcon();
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads(); 
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
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5-liquidHeight, uMin, vMax);		
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5-liquidHeight, uMin, vMin);			
			
			//Back
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5+liquidHeight, uMin, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5+liquidHeight, uMin, vMin);	
			
			//Left
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5+liquidHeight, uMin, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5-liquidHeight, uMin, vMin);	
			
			//Right
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5-liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5+liquidHeight, uMin, vMax);		
						
			//Top
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMin, vMax);		
			
			//Bottom
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 0, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5+liquidHeight, uMin, vMin);				
			tessellator.addVertexWithUV(.5-liquidHeight, 0, .5-liquidHeight, uMin, vMax);		
		}
		tessellator.draw();		
		GL11.glPopMatrix();
	}
	public void drawCoreLiquid(EnumFacing direction, TileEntity tileentity, FluidStack fluidStack) {
		TileEntityFluidConduit conduit = (TileEntityFluidConduit) tileentity;
		float liquidHeight = conduit.getAdjustedVolume(1) * 3*pixel/2;
		
		final Fluid fluid = fluidStack.getFluid();
		IIcon texture = fluid.getStillIcon();
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads(); 
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
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMax);		
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMin);			
			
			//Back
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMin);	
			
			//Left
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMin);	
			
			//Right
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMax);		

			//Top
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, 1, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5+liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, 1, .5-liquidHeight, uMin, vMax);		
			
			//Bottom
			tessellator.addVertexWithUV(.5+liquidHeight, .5, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5, .5+liquidHeight, uMin, vMin);				
			tessellator.addVertexWithUV(.5-liquidHeight, .5, .5-liquidHeight, uMin, vMax);
		}
		tessellator.draw();		
		GL11.glPopMatrix();
	}
	public void drawNodeLiquid(TileEntity tileentity, FluidStack fluidStack)  {
		TileEntityFluidConduit conduit = (TileEntityFluidConduit) tileentity;
		float liquidHeight = conduit.getAdjustedVolume(1) * 4*pixel/2;
		
		final Fluid fluid = fluidStack.getFluid();
		IIcon texture = fluid.getStillIcon();
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads(); 
		{		
			//Front
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5-liquidHeight, uMax, vMax);		
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5-liquidHeight, uMin, vMax);	
			
			//Right
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5+liquidHeight, uMax, vMax);		
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5-liquidHeight, uMin, vMax);	
			
			//Back
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5+liquidHeight, uMax, vMax);
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5+liquidHeight, uMax, vMin);
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5+liquidHeight, uMin, vMax);	
			
			//Left
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5-liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5+liquidHeight, uMin, vMax);		
			
			//Top
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5-liquidHeight, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5+liquidHeight, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5-liquidHeight, .5-liquidHeight, uMin, vMax);		
			
			//Bottom
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5-liquidHeight, uMax, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5+liquidHeight, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5+liquidHeight, uMin, vMin);				
			tessellator.addVertexWithUV(.5-liquidHeight, .5+liquidHeight, .5-liquidHeight, uMin, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight, .5+liquidHeight, .5-liquidHeight, uMax, vMax);	

		}
	tessellator.draw();
	}
	public void drawConnectionLiquid(EnumFacing direction, TileEntity tileentity, FluidStack fluidStack) {
		TileEntityFluidConduit conduit = (TileEntityFluidConduit) tileentity;
		float liquidHeight = conduit.getAdjustedVolume(1) * 4*pixel/2;
		
		Tessellator tessellator = Tessellator.instance;
	
		final Fluid fluid = fluidStack.getFluid();
		IIcon texture = fluid.getStillIcon();
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();
			
		tessellator.startDrawingQuads(); {	
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if (direction.equals(EnumFacing.DOWN)) {
				GL11.glRotatef(0, 0, 0, 0);
			}else if (direction.equals(EnumFacing.UP)) {
				GL11.glRotatef(180, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.NORTH)) {
				GL11.glRotatef(90, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.SOUTH)) {
				GL11.glRotatef(270, 1, 0, 0);
			}	
			else if (direction.equals(EnumFacing.EAST)) {
				GL11.glRotatef(90, 0, 0, 1);
			}	
			else if (direction.equals(EnumFacing.WEST)) {
				GL11.glRotatef(270, 0, 0, 1);
			}	
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			//Front
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5-liquidHeight/2, uMin, vMax);
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMin, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMax, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMax);		
			
			//Right
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5-liquidHeight/2, uMin, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMin, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMax, vMin);
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMax);		
	
			//Back
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5+liquidHeight/2, uMin, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMin, vMin);
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMax, vMin);
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMax);
	
			//Left
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5+liquidHeight/2, uMin, vMax);	
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMax);	
	
			//Top
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMin, vMax);
			tessellator.addVertexWithUV(.5+liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMin, vMin);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5+liquidHeight/2, uMax, vMin);
			tessellator.addVertexWithUV(.5-liquidHeight/2, 28*pixel/2, .5-liquidHeight/2, uMax, vMax);
			
			//Bottom
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5-liquidHeight/2, uMin, vMax);	
			tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5+liquidHeight/2, uMin, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMin);	
			tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMax);	

		//Front
		tessellator.addVertexWithUV(.5-liquidHeight/2, .5-liquidHeight/2, .5-liquidHeight/2, uMin, vMax);		
		tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMax);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMin);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, .5-liquidHeight/2, .5-liquidHeight/2, uMin, vMin);			
		
		//Back
		tessellator.addVertexWithUV(.5+liquidHeight/2, .5-liquidHeight/2, .5+liquidHeight/2, uMin, vMax);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMax);	
		tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMin);	
		tessellator.addVertexWithUV(.5-liquidHeight/2, .5-liquidHeight/2, .5+liquidHeight/2, uMin, vMin);	
		
		//Left
		tessellator.addVertexWithUV(.5-liquidHeight/2, .5-liquidHeight/2, .5+liquidHeight/2, uMin, vMax);	
		tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMax);	
		tessellator.addVertexWithUV(.5-liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMin);	
		tessellator.addVertexWithUV(.5-liquidHeight/2, .5-liquidHeight/2, .5-liquidHeight/2, uMin, vMin);	
		
		//Right
		tessellator.addVertexWithUV(.5+liquidHeight/2, .5-liquidHeight/2, .5-liquidHeight/2, uMin, vMin);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5-liquidHeight/2, uMax, vMin);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, 1, .5+liquidHeight/2, uMax, vMax);	
		tessellator.addVertexWithUV(.5+liquidHeight/2, .5-liquidHeight/2, .5+liquidHeight/2, uMin, vMax);	
		
	}
	tessellator.draw();
	GL11.glPopMatrix();
	}
		*/
}
