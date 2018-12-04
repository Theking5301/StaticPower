package theking530.staticpower.client.render.tileentitys.multiblock;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import theking530.staticpower.client.model.ModelBlock;
import theking530.staticpower.tileentity.TileEntityBase;

public class BaseMultiblockTESR <T extends TileEntityBase> extends TileEntitySpecialRenderer<T> {
	
	protected static final ModelBlock BLOCK = new ModelBlock();
	
    public void render(T tileEnttiy, double translationX, double translationY, double translationZ, float partialTicks, int destroyStage, float alpha) {
		EnumFacing facing = tileEnttiy.getFacingDirection();
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
		draw(tileEnttiy, translationX, translationY, translationZ, alpha, destroyStage, alpha);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
		GL11.glColor3f(1.0F, 1.0F, 1.0F);	
		GL11.glPopMatrix();
    }
    public void draw(T tileEnttiy, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

    }
}
