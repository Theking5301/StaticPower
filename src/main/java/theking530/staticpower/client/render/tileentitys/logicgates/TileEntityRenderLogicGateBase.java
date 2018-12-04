package theking530.staticpower.client.render.tileentitys.logicgates;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityRenderLogicGateBase extends TileEntitySpecialRenderer<TileEntityBaseLogicGate> {

    private final ModelBase MODEL;
    ResourceLocation TEXTURE_ON;
    ResourceLocation TEXTURE_OFF;
    ResourceLocation INPUT = new ResourceLocation(Reference.MOD_ID, "textures/blocks/logicgates/LogicGateInput.png");
    ResourceLocation OUTPUT = new ResourceLocation(Reference.MOD_ID, "textures/blocks/logicgates/LogicGateOutput.png");
    ResourceLocation EXTRA = new ResourceLocation(Reference.MOD_ID, "textures/blocks/logicgates/LogicGateExtraOutput.png");
    
    public TileEntityRenderLogicGateBase(ModelBase Model, ResourceLocation OnTexture, ResourceLocation OffTexture) {
    	MODEL = Model;
    	TEXTURE_ON = OnTexture;
    	TEXTURE_OFF = OffTexture;
    }
	@Override
	    public void render(TileEntityBaseLogicGate tileentity, double x, double y, double z, float f, int dest, float alpha) {
		TileEntityBaseLogicGate Gate = (TileEntityBaseLogicGate)tileentity;
		int orientation = tileentity.getBlockMetadata();
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		 GL11.glTranslatef(0.5F, 1.5F, 0.5F);		
		 GL11.glPushMatrix();
		 
		 switch(orientation) {
		 case 0:
			 GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);			 
			 GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			 break;
		 case 1:
			 GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
			 GL11.glTranslatef(0.0F, -1.0F, 1.0F);
			 break;
		 case 2:
			 GL11.glRotatef(90, 0.0F, 0.0F, 1.0F);
			 GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			 break;
		 case 3:
			 GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
			 GL11.glTranslatef(0.0F, -1.0F, -1.0F);
			 break;
		 case 4:
			 GL11.glRotatef(-90, 0.0F, 0.0F, 1.0F);
			 GL11.glTranslatef(1.0F, -1.0F, 0.0F);
			 break;
		 case 5: 
			 GL11.glTranslatef(0.0F, -2.0F, 0.0F);	
			 break;
		 }
		 
		 renderInputOutput(Gate, EnumFacing.NORTH);
		 renderInputOutput(Gate, EnumFacing.SOUTH);
		 renderInputOutput(Gate, EnumFacing.EAST);
		 renderInputOutput(Gate, EnumFacing.WEST);
		 
		 if(Gate.isOn()) { 
			 bindTexture(TEXTURE_ON);
			 MODEL.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		 }else{
			 bindTexture(TEXTURE_OFF);
			 MODEL.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		 }
		 GL11.glPopMatrix();
		 GL11.glPopMatrix();
	}
	public void renderInputOutput(TileEntityBaseLogicGate Gate, EnumFacing side) {
		if(Gate.SIDE_MODES[side.ordinal()] == Mode.Disabled){
			return;
        }
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        if(Gate.SIDE_MODES[side.ordinal()] == Mode.Input) {
			 bindTexture(INPUT);	
        }else if(Gate.SIDE_MODES[side.ordinal()] == Mode.Output){
			 bindTexture(OUTPUT);	
        }else if(Gate.SIDE_MODES[side.ordinal()] == Mode.Regular){
			 bindTexture(EXTRA);	
        }
		GL11.glDisable(GL11.GL_LIGHTING);		
        if(side == EnumFacing.NORTH) {
    		GL11.glPushMatrix();
    		vertexbuffer.pos(-.44, 1.37, .15).tex(1, 0).endVertex();	
    		vertexbuffer.pos(-.44, 1.37, -.15).tex(0, 0).endVertex();		
    		vertexbuffer.pos(-.27, 1.37, -.15).tex(0, 1).endVertex();		
    		vertexbuffer.pos(-.27, 1.37, .15).tex(1, 1).endVertex();	
    		GL11.glPopMatrix();	
        }else if(side == EnumFacing.SOUTH) {
    		GL11.glPushMatrix();
    		vertexbuffer.pos(.27, 1.37, .15).tex(0, 1).endVertex();	
    		vertexbuffer.pos(.27, 1.37, -.15).tex(1, 1).endVertex();		
    		vertexbuffer.pos(.44, 1.37, -.15).tex(1, 0).endVertex();		
    		vertexbuffer.pos(.44, 1.37, .15).tex(0, 0).endVertex();	
    		GL11.glPopMatrix();			
        }else if(side == EnumFacing.EAST) {
    		GL11.glPushMatrix();
    		vertexbuffer.pos(-.15, 1.37, .44).tex(1, 0).endVertex();	
    		vertexbuffer.pos(-.15, 1.37, .27).tex(1, 1).endVertex();		
    		vertexbuffer.pos(.15, 1.37, .27).tex(0, 1).endVertex();		
    		vertexbuffer.pos(.15, 1.37, .44).tex(0, 0).endVertex();	
    		GL11.glPopMatrix();			
        }else if(side == EnumFacing.WEST) {
    		GL11.glPushMatrix();
    		vertexbuffer.pos(-.15, 1.37, -.27).tex(0, 1).endVertex();	
    		vertexbuffer.pos(-.15, 1.37, -.44).tex(0, 0).endVertex();		
    		vertexbuffer.pos(.15, 1.37, -.44).tex(1, 0).endVertex();		
    		vertexbuffer.pos(.15, 1.37, -.27).tex(1, 1).endVertex();	
    		GL11.glPopMatrix();		
        }
		tessellator.draw();		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}



