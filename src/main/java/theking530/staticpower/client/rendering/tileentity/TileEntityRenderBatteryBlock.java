package theking530.staticpower.client.rendering.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import theking530.staticcore.utilities.Color;
import theking530.staticcore.utilities.Vector3D;
import theking530.staticcore.utilities.Vector4D;
import theking530.staticpower.client.StaticPowerSprites;
import theking530.staticpower.tileentities.powered.battery.TileEntityBattery;

@OnlyIn(Dist.CLIENT)
public class TileEntityRenderBatteryBlock extends StaticPowerTileEntitySpecialRenderer<TileEntityBattery> {

	public TileEntityRenderBatteryBlock(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void renderTileEntityBase(TileEntityBattery tileEntity, BlockPos pos, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight,
			int combinedOverlay) {
		// Get the energy percentage.
		float height = tileEntity.energyStorage.getStorage().getStoredEnergyPercentScaled(1.0f);

		// Calculate the UV to use when rendering.
		Vector4D uv = new Vector4D(0.0f, 1.0f - height, 1.0f, 1.0f);

		// Move the height into world space.
		height *= 0.55f;

		// For each of the four sides.
		for (Direction dir : Direction.values()) {
			// If this is a top or bottom side, do nothing.
			if (dir.getAxis() == Direction.Axis.Y) {
				continue;
			}

			// Rotate to face the direction.
			matrixStack.push();
			renderToFaceSide(dir, matrixStack);

			// Render the bar.
			drawTexturedQuadUnlit(StaticPowerSprites.BATTERY_BLOCK_BAR, matrixStack, buffer, new Vector3D(0.225f, 0.225f, -0.03f), new Vector3D(0.55f, height, 1.0f), uv, Color.WHITE);

			matrixStack.pop();
		}
	}
}
