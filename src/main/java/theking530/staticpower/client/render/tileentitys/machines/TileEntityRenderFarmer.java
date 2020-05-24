package theking530.staticpower.client.render.tileentitys.machines;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.utilities.RenderUtil;
import theking530.staticpower.assists.utilities.SideModeList;
import theking530.staticpower.client.model.ModelBlock;
import theking530.staticpower.machines.basicfarmer.TileEntityBasicFarmer;
import theking530.staticpower.utilities.Reference;

public class TileEntityRenderFarmer extends BaseMachineTESR<TileEntityBasicFarmer> {

	private static final ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_front.png");
	
	private static final ResourceLocation farmerSide = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side.png");
	private static final ResourceLocation farmerSideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_input.png");
	private static final ResourceLocation farmerSideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_output.png");
	private static final ResourceLocation farmerSideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_disabled.png");
	private static final ResourceLocation farmerSideExtra1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_extra1.png");
	private static final ResourceLocation farmerSideExtra2 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_extra2.png");
	private static final ResourceLocation farmerSideExtra3 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/farmer_side_extra3.png");
	
	private static final ModelBlock RADIUS_PREVIEW = new ModelBlock();
	
	private static float texel = 1F/64F;
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return front;
	}
	@Override
	protected ResourceLocation getSideTexture(SideModeList.Mode mode) {
		switch(mode) {
			case Regular: return farmerSide;
			case Input: return farmerSideIn;
			case Output: return farmerSideOut;
			case Disabled: return farmerSideDis;
			case Input2: return farmerSideExtra1;
			case Output2: return farmerSideExtra2;
			case Output3: return farmerSideExtra3;
			default: return farmerSide;
		}
	}
	
	@Override
	public void drawExtra(TileEntityBasicFarmer tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		if(tileentity.fluidTank.getFluid() != null) {	
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			drawLiquidBar(tileentity, tileentity.fluidTank.getFluid());
			GL11.glDisable(GL11.GL_BLEND);
		}
		if(tileentity.getShouldDrawRadiusPreview()) {
			float radius = tileentity.getRadius()*2+1;
			GL11.glPushMatrix();
			RADIUS_PREVIEW.drawPreviewCube(new Color(200, 25, 25, 150), radius, 1, radius);
			GL11.glPopMatrix();
		}
	}
	public static void drawLiquidBar(TileEntityBasicFarmer tileentity, FluidStack fluidStack) {
		RenderUtil.drawFluidInWorld(fluidStack, tileentity.fluidTank.getCapacity(), 44.0F*texel, 16.0F*texel, 1.0005F, 8.0F*texel, 33.0f*texel);
		
		RenderUtil.drawFluidInWorld(fluidStack, tileentity.fluidTank.getCapacity(), 12.0F*texel, 16.0F*texel, 1.0005F, 8.0F*texel, 33.0f*texel);
	}
}