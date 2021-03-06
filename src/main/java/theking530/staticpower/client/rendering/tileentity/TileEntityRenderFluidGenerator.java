package theking530.staticpower.client.rendering.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import theking530.staticcore.utilities.Color;
import theking530.staticcore.utilities.SDMath;
import theking530.staticcore.utilities.Vector3D;
import theking530.staticcore.utilities.Vector4D;
import theking530.staticpower.client.StaticPowerSprites;
import theking530.staticpower.tileentities.powered.fluidgenerator.TileEntityFluidGenerator;

@OnlyIn(Dist.CLIENT)
public class TileEntityRenderFluidGenerator extends StaticPowerTileEntitySpecialRenderer<TileEntityFluidGenerator> {

	public TileEntityRenderFluidGenerator(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void renderTileEntityBase(TileEntityFluidGenerator tileEntity, BlockPos pos, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		// Draw the fluid bar.
		if (!tileEntity.fluidTankComponent.isEmpty()) {
			float filledPercentage = tileEntity.fluidTankComponent.getVisualFillLevel();
			float firstSectionFilledPercent = Math.min(filledPercentage * 2.0f, 1.0f);
			float secondSectionFilledPercentage = SDMath.clamp((filledPercentage - 0.5f) * 2.0f, 0.0f, 1.0f);

			// Draw the bottom part of the bar.
			drawFluidQuadLit(tileEntity.fluidTankComponent.getFluid(), matrixStack, buffer, new Vector3D(0.2175f, 0.185f, 0.001f), new Vector3D(0.125f, firstSectionFilledPercent * 0.63f * 0.5f, 1.0f),
					new Vector4D(0.0f, 1.0f - firstSectionFilledPercent, 0.4f, 1.0f), getForwardFacingLightLevel(tileEntity));

			// Draw the top part of the bar.
			if (filledPercentage >= 0.5f) {
				drawFluidQuadLit(tileEntity.fluidTankComponent.getFluid(), matrixStack, buffer, new Vector3D(0.2175f, 0.5f, 0.001f),
						new Vector3D(0.125f, secondSectionFilledPercentage * 0.627f * 0.5f, 1.0f), new Vector4D(1.0f, 1.0f - secondSectionFilledPercentage, 0.6f, 1.0f),
						getForwardFacingLightLevel(tileEntity));
			}
		}

		// Draw the empty power bar.
		drawTexturedQuadUnlit(StaticPowerSprites.GUI_POWER_BAR_BG, matrixStack, buffer, new Vector3D(0.657f, 0.18f, 0.0001f), new Vector3D(0.125f, 0.635f, 1.0f), new Vector4D(0.0f, 0.0f, 1.0f, 1.0f),
				Color.WHITE);

		// Draw the filled power bar.
		if (tileEntity.energyStorage.getStorage().getStoredPower() > 0) {
			// Render the power bar.
			float height = tileEntity.energyStorage.getStorage().getStoredEnergyPercentScaled(1.0f);
			Vector4D uv = new Vector4D(0.0f, 1.0f - height, 1.0f, 1.0f);
			drawTexturedQuadUnlit(StaticPowerSprites.GUI_POWER_BAR_FG, matrixStack, buffer, new Vector3D(0.657f, 0.18f, 0.0005f), new Vector3D(0.125f, height * 0.635f, 1.0f), uv, Color.WHITE);
		}
	}
}
