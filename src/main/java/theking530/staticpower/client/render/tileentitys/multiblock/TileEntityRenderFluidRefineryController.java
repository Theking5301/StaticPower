package theking530.staticpower.client.render.tileentitys.multiblock;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.machines.refinery.controller.TileEntityFluidRefineryController;

public class TileEntityRenderFluidRefineryController extends BaseMultiblockTESR<TileEntityFluidRefineryController> {
	
	private static final ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/multiblock/fluid_refinery_controller.png");
		
    public void draw(TileEntityFluidRefineryController tileEnttiy, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		Minecraft.getMinecraft().getTextureManager().bindTexture(front);
		GL11.glPushMatrix();
		GL11.glColor3f(0.6F, 0.6F, 0.6F);
		vertexbuffer.pos(0, 1, 1.001).tex(0, 0).endVertex();
		vertexbuffer.pos(0, 0, 1.001).tex(0, 1).endVertex();	
		vertexbuffer.pos(1, 0, 1.001).tex(1, 1).endVertex();	
		vertexbuffer.pos(1, 1, 1.001).tex(1, 0).endVertex();	
		GL11.glPopMatrix();
		tessellator.draw();	
    }
}
