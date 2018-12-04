package theking530.staticpower.client.render.tileentitys.machines;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.RenderUtil;
import theking530.staticpower.machines.fluidgenerator.TileEntityFluidGenerator;

public class TileEntityRenderFluidGenerator extends BaseMachineTESR<TileEntityFluidGenerator> {

	private static float texel = 1F/64F;
	
	private static final ResourceLocation frontOn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/fluid_generator_front_on.png");
	private static final ResourceLocation frontOff = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/fluid_generator_front_off.png");
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return machineOn ? frontOn : frontOff;
	}

	@Override
	public void drawExtra(TileEntityFluidGenerator tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		if(tileentity.fluidTank.getFluid() != null) {	
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			drawLiquidBar(tileentity, tileentity.fluidTank.getFluid());
			GL11.glDisable(GL11.GL_BLEND);
		}
		drawEnergyBar(tileentity);
	}
	public static void drawLiquidBar(TileEntity tileentity, FluidStack fluidStack) {
		TileEntityFluidGenerator fGen = (TileEntityFluidGenerator)tileentity;
		RenderUtil.drawFluidInWorld(fluidStack, fGen.fluidTank.getCapacity(), 41.0F*texel, 12.0F*texel, 1.0005F, 8.0F*texel, 40.0f*texel);
	}
	public static void drawEnergyBar(TileEntity tileentity) {
		TileEntityFluidGenerator fGen = (TileEntityFluidGenerator)tileentity;
		float height = ((float)(fGen.energyStorage.getEnergyStored()/(float)fGen.energyStorage.getMaxEnergyStored())) > 0 ? ((float)(fGen.energyStorage.getEnergyStored()/(float)fGen.energyStorage.getMaxEnergyStored())) : 0;	
		RenderUtil.drawPowerBarInWorld(height, 15.0F*texel, 12.0F*texel, 1.0005F, 8.0F*texel, 40.0f*texel);
	}
}
