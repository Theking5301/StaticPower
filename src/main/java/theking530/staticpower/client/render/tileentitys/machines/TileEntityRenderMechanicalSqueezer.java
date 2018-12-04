package theking530.staticpower.client.render.tileentitys.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.RenderUtil;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;
import theking530.staticpower.machines.mechanicalsqueezer.TileEntityMechanicalSqueezer;

public class TileEntityRenderMechanicalSqueezer extends BaseMachineTESR<TileEntityMechanicalSqueezer> {

	static float texel = 1/64F;

	private static final ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/mechanical_squeezer_front.png");
	
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return front;
	}
	@Override
	public void drawExtra(TileEntityMechanicalSqueezer te, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		SqueezerOutputWrapper recipe = SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(te.slotsInternal.getStackInSlot(0));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if(te.fluidTank.getFluid() != null) {
			drawLiquidTank(te, te.fluidTank.getFluid());
		}
		if(recipe != null) {
			drawLiquidPour(te, recipe.getOutputFluid());
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
	public static void drawLiquidTank(TileEntityMechanicalSqueezer squeezer, FluidStack fluidStack) {
		RenderUtil.drawFluidInWorld(fluidStack, squeezer.fluidTank.getCapacity(), 16.0f*texel, 14.0F*texel, 1.0001F, 32.0f*texel, texel*10.0f);
	}
	public static void drawLiquidPour(TileEntityMechanicalSqueezer squeezer, FluidStack fluidStack) {
		float progress = ((float)squeezer.processingTimer/(float)squeezer.processingTime)*0.5f;
		RenderUtil.drawFluidInWorld(fluidStack, fluidStack.amount, 28.0f*texel, 1-16.0f*texel-progress, 1.0001F, 8.0f*texel, progress);
	}
}
