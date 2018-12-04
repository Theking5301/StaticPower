package theking530.staticpower.client.render.tileentitys.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.model.ModelBlock;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;

public class TileEntityRenderBattery extends TileEntitySpecialRenderer<TileEntityBattery> {

	ResourceLocation top;
	ResourceLocation topIn;
	ResourceLocation topOut;
	ResourceLocation topDis;
	
	ResourceLocation side;
	ResourceLocation sideDis;
	ResourceLocation sideIn;
	ResourceLocation sideOut;
	
	ResourceLocation eBar = new ResourceLocation(Reference.MOD_ID, "textures/blocks/battery_energy_bar.png");
	
	public TileEntityRenderBattery(Tier tier) {	
		switch(tier) {
    	case STATIC:top = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top.png");
    				topIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_in.png");
    				topOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_out.png");
    				topDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_dis.png");
    				side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides.png");
    				sideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_dis.png");
    				sideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_in.png");
    				sideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_out.png");	
    		break;
    	case ENERGIZED:top = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_top.png");
					topIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_top_in.png");
					topOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_top_out.png");
					topDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_top_dis.png");
					side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_sides.png");
					sideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_sides_dis.png");
					sideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_sides_in.png");
					sideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/energized_battery_sides_out.png");	
			break;
    	case LUMUM:top = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_top.png");
					topIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_top_in.png");
					topOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_top_out.png");
					topDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_top_dis.png");
					side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_sides.png");
					sideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_sides_dis.png");
					sideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_sides_in.png");
					sideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/lumum_battery_sides_out.png");	
			break;
    	default:top = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top.png");
				topIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_in.png");
				topOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_out.png");
				topDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_top_dis.png");
				side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides.png");
				sideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_dis.png");
				sideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_in.png");
				sideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/static_battery_sides_out.png");	
				break;
    	}
	}
	
	@Override
	public void render(TileEntityBattery tileentity, double x, double y, double z, float f, int dest, float alpha) {	
		EnumFacing facing = tileentity.getFacingDirection();
			
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
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
		GL11.glDisable(GL11.GL_LIGHTING);			
		checkAndRenderSides(tileentity, 0);
		checkAndRenderSides(tileentity, 1);
		checkAndRenderSides(tileentity, 2);
		checkAndRenderSides(tileentity, 3);
		checkAndRenderSides(tileentity, 4);
		checkAndRenderSides(tileentity, 5);
		drawEnergy(tileentity);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-x, -y, -z);	
		GL11.glColor3f(1.0F, 1.0F, 1.0F);	
		GL11.glPopMatrix();
	}
	public void checkAndRenderSides(TileEntity tileentity, int side) {
		TileEntityBattery battery = (TileEntityBattery)tileentity;		
		ModelBlock block = new ModelBlock();
		if(side <= 1) {
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Regular) {
				this.bindTexture(top);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Input) {
				this.bindTexture(topIn);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Output) {
				this.bindTexture(topOut);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Disabled) {
				this.bindTexture(topDis);
			}
			block.drawBlock(side);
		}
		if(side >= 2) {
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Regular) {
				this.bindTexture(this.side);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Input) {
				this.bindTexture(sideIn);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Output) {
				this.bindTexture(sideOut);
			}
			if(battery.getSideConfiguration(EnumFacing.values()[side]) == Mode.Disabled) {
				this.bindTexture(sideDis);
			}
			block.drawBlock(side);
		}
	}
	public void drawEnergy(TileEntity tileentity) {		
		TileEntityBattery battery = (TileEntityBattery)tileentity;			
		float height = (battery.getEnergyLevelScaled(1) +.39F)*.72F;
		float texHeight = battery.getEnergyLevelScaled(1);
		this.bindTexture(eBar);
		GL11.glPushMatrix();
			
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertex = tessellator.getBuffer();
		vertex.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		//Back
		vertex.pos(.25, .218, -0.001).tex(0, 0).endVertex();
		vertex.pos(.25, height*.78125, -0.001).tex(0, texHeight).endVertex();
		vertex.pos(.75, height*.78125, -0.001).tex(1, texHeight).endVertex();	
		vertex.pos(.75, .218, -0.001).tex(1, 0).endVertex();		

		//Left
		vertex.pos(1.001, .218, .25).tex(0, 0).endVertex();
		vertex.pos(1.001, height*.78125, .25).tex(0, texHeight).endVertex();
		vertex.pos(1.001, height*.78125, .75).tex(1, texHeight).endVertex();	
		vertex.pos(1.001, .218, .75).tex(1, 0).endVertex();

		//Right
		vertex.pos(-0.001, .218, .75).tex(0, 0).endVertex();
		vertex.pos(-0.001, height*.78125, .75).tex(0, texHeight).endVertex();	
		vertex.pos(-0.001, height*.78125, .25).tex(1, texHeight).endVertex();	
		vertex.pos(-0.001, .218, .25).tex(1, 0).endVertex();	

		//Front
		vertex.pos(.25, height*.78125, 1.001).tex(0, 0).endVertex();
		vertex.pos(.25, .218, 1.001).tex(0, texHeight).endVertex();	
		vertex.pos(.75, .218, 1.001).tex(1, texHeight).endVertex();	
		vertex.pos(.75, height*.78125, 1.001).tex(1, 0).endVertex();			

		tessellator.draw();		
		GL11.glPopMatrix();
	}
}

