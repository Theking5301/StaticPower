package theking530.staticpower.tileentities.cables.item;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import theking530.staticpower.client.StaticPowerAdditionalModels;
import theking530.staticpower.client.rendering.blocks.CableBakedModel;
import theking530.staticpower.tileentities.cables.AbstractCableBlock;
import theking530.staticpower.tileentities.cables.network.CableBoundsCache;

public class BlockItemCable extends AbstractCableBlock {

	public BlockItemCable(String name) {
		super(name, "item", new CableBoundsCache(2.0D, 3.0D, 2.0D));
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityItemCable();
	}

	@Override
	public IBakedModel getModelOverride(BlockState state, @Nullable IBakedModel existingModel, ModelBakeEvent event) {
		IBakedModel extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_ITEM_EXTENSION);
		IBakedModel straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_ITEM_STRAIGHT);
		IBakedModel attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_ITEM_ATTACHMENT);
		return new CableBakedModel(existingModel, extensionModel, straightModel, attachmentModel);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public RenderType getRenderType() {
		return RenderType.getCutout();
	}
}