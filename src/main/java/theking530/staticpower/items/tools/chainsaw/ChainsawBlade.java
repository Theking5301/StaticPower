package theking530.staticpower.items.tools.chainsaw;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import theking530.api.attributes.capability.AttributeableHandler;
import theking530.api.attributes.capability.CapabilityAttributable;
import theking530.api.attributes.capability.IAttributable;
import theking530.api.attributes.defenitions.DiamondHardenedDefenition;
import theking530.api.attributes.defenitions.EmeraldHardenedDefenition;
import theking530.api.attributes.defenitions.HasteAttributeDefenition;
import theking530.api.attributes.defenitions.RubyHardenedDefenition;
import theking530.api.attributes.defenitions.SapphireHardenedDefenition;
import theking530.api.attributes.defenitions.PromotedAttributeDefenition;
import theking530.api.attributes.defenitions.SmeltingAttributeDefenition;
import theking530.api.attributes.rendering.AttributableItemRenderLayers;
import theking530.api.attributes.rendering.BasicAttributeRenderLayer;
import theking530.api.multipart.AbstractToolPart;
import theking530.staticcore.utilities.ItemTierUtilities;
import theking530.staticpower.StaticPowerConfig;
import theking530.staticpower.client.StaticPowerAdditionalModels;
import theking530.staticpower.client.rendering.items.ChainsawBladeItemModel;
import theking530.staticpower.utilities.MetricConverter;

public class ChainsawBlade extends AbstractToolPart {
	public final ItemTier miningTier;

	public ChainsawBlade(String name, ItemTier miningTier, ResourceLocation tier) {
		super(name, tier, new Item.Properties().maxStackSize(1).maxDamage(1));
		this.miningTier = miningTier;
	}

	@Override
	protected void initializeAttributes(AttributeableHandler handler) {
		handler.addAttribute(HasteAttributeDefenition.ID);
		handler.addAttribute(DiamondHardenedDefenition.ID);
		handler.addAttribute(RubyHardenedDefenition.ID);
		handler.addAttribute(SapphireHardenedDefenition.ID);
		handler.addAttribute(EmeraldHardenedDefenition.ID);
		handler.addAttribute(SmeltingAttributeDefenition.ID);
		handler.addAttribute(PromotedAttributeDefenition.ID);
	}

	@Override
	protected void initializeRenderLayers(AttributableItemRenderLayers renderLayers) {
		renderLayers.addLayer(SmeltingAttributeDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.BLADE_SMELTING, 2));
		renderLayers.addLayer(HasteAttributeDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.BLADE_HASTE, 2));

		renderLayers.addLayer(DiamondHardenedDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.CHAINSAW_BLADE_HARDENED_DIAMOND, 3));
		renderLayers.addLayer(RubyHardenedDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.CHAINSAW_BLADE_HARDENED_RUBY, 3));
		renderLayers.addLayer(SapphireHardenedDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.CHAINSAW_BLADE_HARDENED_SAPPHIRE, 3));
		renderLayers.addLayer(EmeraldHardenedDefenition.ID, new BasicAttributeRenderLayer(StaticPowerAdditionalModels.CHAINSAW_BLADE_HARDENED_EMERALD, 3));
	}

	@Override
	protected int getBaseDurability() {
		return StaticPowerConfig.getTier(tier).chainsawBladeUses.get();
	}

	public ItemTier getMiningTier(ItemStack stack) {
		// Get the drill bit attributes, check if it has the promoted attribute. If it
		// does, promote the item.
		IAttributable drillBitAttributes = stack.getCapability(CapabilityAttributable.ATTRIBUTABLE_CAPABILITY).orElse(null);
		if (drillBitAttributes != null && drillBitAttributes.hasAttribute(PromotedAttributeDefenition.ID)) {
			PromotedAttributeDefenition defenition = (PromotedAttributeDefenition) drillBitAttributes.getAttribute(PromotedAttributeDefenition.ID);
			return defenition.modifyItemTier(miningTier);
		}
		return miningTier;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, boolean showAdvanced) {
		// Add the mining tier.
		tooltip.add(new TranslationTextComponent("gui.staticpower.mining_tier").appendString(": ").append(ItemTierUtilities.getNameForItemTier(getMiningTier(stack))));

		// Add the durability.
		int remaining = getMaxDamage(stack) - getDamage(stack);
		String remainingString = new MetricConverter(remaining).getValueAsString(true);
		tooltip.add(new TranslationTextComponent("gui.staticpower.durability").appendString(" ").appendString(remainingString));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IBakedModel getModelOverride(BlockState state, IBakedModel existingModel, ModelBakeEvent event) {
		return new ChainsawBladeItemModel(existingModel);
	}

}
