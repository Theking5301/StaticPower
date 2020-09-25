package theking530.staticpower.cables.power;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import theking530.api.power.StaticVoltTooltipUtilities;
import theking530.staticcore.utilities.Vector3D;
import theking530.staticpower.cables.AbstractCableBlock;
import theking530.staticpower.cables.CableBoundsCache;
import theking530.staticpower.client.StaticPowerAdditionalModels;
import theking530.staticpower.client.rendering.blocks.CableBakedModel;
import theking530.staticpower.data.StaticPowerTiers;
import theking530.staticpower.data.TierReloadListener;

public class BlockPowerCable extends AbstractCableBlock {
	public final ResourceLocation tier;

	public BlockPowerCable(String name, ResourceLocation tier) {
		super(name, new CableBoundsCache(2.0D, new Vector3D(3.0f, 3.0f, 3.0f)));
		this.tier = tier;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected void getBasicTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip) {
		super.getBasicTooltip(stack, worldIn, tooltip);
		tooltip.add(StaticVoltTooltipUtilities.getPowerPerTickTooltip(TierReloadListener.getTier(tier).getCablePowerCapacity()));
	}

	@Override
	@OnlyIn(Dist.CLIENT)	
	public IBakedModel getModelOverride(BlockState state, @Nullable IBakedModel existingModel, ModelBakeEvent event) {
		IBakedModel extensionModel = null;
		IBakedModel straightModel = null;
		IBakedModel attachmentModel = null;

		if (tier == StaticPowerTiers.BASIC) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_BASIC_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_BASIC_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_BASIC_ATTACHMENT);
		} else if (tier == StaticPowerTiers.ADVANCED) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ADVANCED_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ADVANCED_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ADVANCED_ATTACHMENT);
		} else if (tier == StaticPowerTiers.STATIC) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_STATIC_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_STATIC_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_STATIC_ATTACHMENT);
		} else if (tier == StaticPowerTiers.ENERGIZED) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ENERGIZED_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ENERGIZED_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_ENERGIZED_ATTACHMENT);
		} else if (tier == StaticPowerTiers.LUMUM) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_LUMUM_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_LUMUM_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_LUMUM_ATTACHMENT);
		} else if (tier == StaticPowerTiers.CREATIVE) {
			extensionModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_CREATIVE_EXTENSION);
			straightModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_CREATIVE_STRAIGHT);
			attachmentModel = event.getModelRegistry().get(StaticPowerAdditionalModels.CABLE_POWER_CREATIVE_ATTACHMENT);
		}

		return new CableBakedModel(existingModel, extensionModel, straightModel, attachmentModel);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		if (tier == StaticPowerTiers.BASIC) {
			return TileEntityPowerCable.TYPE_BASIC.create();
		} else if (tier == StaticPowerTiers.ADVANCED) {
			return TileEntityPowerCable.TYPE_ADVANCED.create();
		} else if (tier == StaticPowerTiers.STATIC) {
			return TileEntityPowerCable.TYPE_STATIC.create();
		} else if (tier == StaticPowerTiers.ENERGIZED) {
			return TileEntityPowerCable.TYPE_ENERGIZED.create();
		} else if (tier == StaticPowerTiers.LUMUM) {
			return TileEntityPowerCable.TYPE_LUMUM.create();
		} else if (tier == StaticPowerTiers.CREATIVE) {
			return TileEntityPowerCable.TYPE_CREATIVE.create();
		}
		return null;
	}
}
