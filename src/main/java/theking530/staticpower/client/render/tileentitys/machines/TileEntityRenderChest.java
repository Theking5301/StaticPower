package theking530.staticpower.client.render.tileentitys.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.tileentity.chest.TileEntityBaseChest;

@SideOnly(Side.CLIENT)
public class TileEntityRenderChest extends TileEntitySpecialRenderer<TileEntity> {
	
    private ResourceLocation texture;
    private ModelChest model = new ModelChest();
    private Tier TIER;
    
    public TileEntityRenderChest(Tier tier) {
    	TIER = tier;	
    	switch(TIER) {
    	case STATIC:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/StaticChest.png");
    		break;
    	case ENERGIZED:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/EnergizedChest.png");
    		break;
    	case LUMUM:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/LumumChest.png");
    		break;
    	default:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/StaticChest.png");
    		break;
    	}
    }
    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	TileEntityBaseChest staticChest = (TileEntityBaseChest)te;	
        
    	switch(TIER) {
    	case STATIC:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/StaticChest.png");
    		break;
    	case ENERGIZED:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/EnergizedChest.png");
    		break;
    	case LUMUM:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/LumumChest.png");
    		break;
    	default:
    		texture = new ResourceLocation(Reference.MOD_ID, "textures/models/tileentity/StaticChest.png");
    		break;
    	}

    	if (destroyStage >= 0)
        {
            bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(texture);
        }


		EnumFacing facing = staticChest.getFacingDirection();
		
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
		GL11.glRotated(180, 1, 0, 0);
		GL11.glTranslated(0, -1, -1);
		
        float f1 = staticChest.prevLidAngle + (staticChest.lidAngle - staticChest.prevLidAngle) * partialTicks;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        model.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        model.renderAll();
        GL11.glPopMatrix();
    }

}