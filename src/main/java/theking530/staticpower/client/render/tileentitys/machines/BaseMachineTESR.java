package theking530.staticpower.client.render.tileentitys.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.SideModeList;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.client.model.ModelBlock;
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.IProcessing;

public class BaseMachineTESR <T extends TileEntityBase> extends TileEntitySpecialRenderer<T> {

	private static final ModelBlock BLOCK = new ModelBlock();
	private static final ResourceLocation side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side.png");
	private static final ResourceLocation sideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_input.png");
	private static final ResourceLocation sideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_output.png");
	private static final ResourceLocation sideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_disabled.png");
	private static final ResourceLocation sideExtra1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_extra1.png");
	private static final ResourceLocation sideExtra2 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_extra2.png");
	private static final ResourceLocation sideExtra3 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/machine_side_extra3.png");
	
	@Override
	public void render(T tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {		
		EnumFacing facing = tileentity.getFacingDirection();
		
		GL11.glPushMatrix();
		GL11.glTranslated(translationX, translationY, translationZ);
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
		drawExtra(tileentity, translationZ, translationZ, translationZ, alpha, dest, alpha);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
		GL11.glColor3f(1.0F, 1.0F, 1.0F);	
		GL11.glPopMatrix();
	}
	public void drawExtra(T tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		
	}
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return null;
	}
	protected ResourceLocation getSideTexture(SideModeList.Mode mode) {
		switch(mode) {
			case Regular: return side;
			case Input: return sideIn;
			case Output: return sideOut;
			case Disabled: return sideDis;
			case Input2: return sideExtra1;
			case Output2: return sideExtra2;
			case Output3: return sideExtra3;
			default: return side;
		}
	}
	public void checkAndRenderSides(T tileentity, int side) {
		EnumFacing adjustedSide = SideUtilities.getEnumFacingFromRenderingInt(side, tileentity.getFacingDirection());	
	
		if(side != 3) {
			bindTexture(getSideTexture(tileentity.getSideConfiguration(adjustedSide)));
		}else{
			if(tileentity instanceof IProcessing) {
				IProcessing processing = (IProcessing)tileentity;
				bindTexture(getFrontTexture(processing.isProcessing()));
			}else{
				bindTexture(getFrontTexture(false));
			}
		}
		BLOCK.drawBlock(side);
	}
}
