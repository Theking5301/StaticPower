package theking530.staticpower.client.rendering.tileentity;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import theking530.api.utilities.Color;
import theking530.staticpower.StaticPower;
import theking530.staticpower.tileentities.TileEntityBase;

@SuppressWarnings("deprecation")
public abstract class StaticPowerTileEntitySpecialRenderer<T extends TileEntityBase> extends TileEntityRenderer<T> {
	protected ItemRenderer ItemRenderer;

	public StaticPowerTileEntitySpecialRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	/**
	 * Performs the proper rotation to ensure the rendering is rotated to follow the
	 * underlying block.
	 */
	@Override
	public void render(T tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		Direction facing = tileEntity.getFacingDirection();
		BlockPos tileEntityPos = tileEntity.getPos();
		ItemRenderer = Minecraft.getInstance().getItemRenderer();
		matrixStack.push();

		if (facing == Direction.WEST) {
			matrixStack.rotate(new Quaternion(new Vector3f(0, 1, 0), -90, true));
			matrixStack.translate(0, 0, -1);
		} else if (facing == Direction.NORTH) {
			matrixStack.rotate(new Quaternion(new Vector3f(0, 1, 0), 180, true));
			matrixStack.translate(-1, 0, -1);
		} else if (facing == Direction.EAST) {
			matrixStack.rotate(new Quaternion(new Vector3f(0, 1, 0), 90, true));
			matrixStack.translate(-1, 0, 0);
		}

		// Draw the tile entity.
		try {
			renderTileEntityBase(tileEntity, tileEntityPos, partialTicks, matrixStack, buffer, combinedLight, combinedOverlay);
		} catch (Exception e) {
			StaticPower.LOGGER.error("An error occured when attempting to draw tile entity base: %1$s.", tileEntity, e);
		}

		matrixStack.push();

		ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
		matrixStack.translate(-tileEntityPos.getX(), -tileEntityPos.getY(), -tileEntityPos.getZ()); 
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("textures/block/stone.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(-100, 10, -100).tex(1.0f, 1.0f).endVertex();
		vertexbuffer.pos(-100, 10, 100).tex(1.0f, 0.0f).endVertex();
		vertexbuffer.pos(100, 10, 100).tex(0.0f, 0.0f).endVertex();
		vertexbuffer.pos(100, 10, -100).tex(0.0f, 1.0f).endVertex();
		tessellator.draw();
		matrixStack.pop();
		
		matrixStack.pop();

	}

	/**
	 * This method is where the main drawing should occur.
	 * 
	 * @param tileEntity      The {@link TileEntity} being drawn.
	 * @param pos             The position of the {@link TileEntity} .
	 * @param partialTicks    The partial ticks.
	 * @param matrixStack     The matrix stack to draw with.
	 * @param buffer          The buffer type.
	 * @param combinedLight   The combined light at the block this
	 *                        {@link TileEntity} is rendering at.
	 * @param combinedOverlay The combined overlay.
	 */
	protected abstract void renderTileEntityBase(T tileEntity, BlockPos pos, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay);

	/**
	 * Renders the provided {@link ItemStack} in the world.
	 * 
	 * @param item            The {@link ItemStack} to render.
	 * @param offset          The offset to apply in world space.
	 * @param partialTicks    The partial ticks.
	 * @param matrixStack     The matrix stack to use for transformations.
	 * @param buffer          The buffer type.
	 * @param combinedLight   The combined light at the block this
	 *                        {@link TileEntity} is rendering at.
	 * @param combinedOverlay The combined overlay.
	 */
	protected void drawItemInWorld(T tileEntity, ItemStack item, Vector3f offset, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		matrixStack.push();
		matrixStack.translate(offset.getX(), offset.getY(), offset.getZ());
		matrixStack.scale(0.4f, 0.4f, 0.01f);
		IBakedModel itemModel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(item, tileEntity.getWorld(), null);
		ItemRenderer.renderItem(item, ItemCameraTransforms.TransformType.GUI, false, matrixStack, buffer, 15728880, combinedOverlay, itemModel);
		matrixStack.pop();
	}

	/**
	 * Renders a string in the world.
	 * 
	 * @param text            The text to render.
	 * @param tileEntity
	 * @param color
	 * @param offset
	 * @param scale
	 * @param partialTicks
	 * @param matrixStack
	 * @param buffer
	 * @param combinedLight
	 * @param combinedOverlay
	 */
	protected void drawTextInWorld(String text, T tileEntity, Color color, Vector3f offset, float scale, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight,
			int combinedOverlay) {
		if (text == null || text.isEmpty())
			return;

		FontRenderer fontRenderer = this.renderDispatcher.getFontRenderer();
		int textWidth = fontRenderer.getStringWidth(text);

		matrixStack.push();
		matrixStack.translate(offset.getX(), offset.getY(), offset.getZ());
		matrixStack.rotate(new Quaternion(new Vector3f(0, 1, 0), 180, true));
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, 1), 180, true));
		matrixStack.scale(scale, scale, scale);

		fontRenderer.renderString(text, -textWidth / 2f, 0.5f, color.encodeInInteger(), false, matrixStack.getLast().getMatrix(), buffer, false, 0, 15728880); // 15728880

		matrixStack.pop();
	}
}
