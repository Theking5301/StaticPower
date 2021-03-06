package theking530.staticpower.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import theking530.staticcore.item.ICustomModelSupplier;
import theking530.staticpower.cables.digistore.crafting.EncodedDigistorePattern;
import theking530.staticpower.client.StaticPowerAdditionalModels;
import theking530.staticpower.client.rendering.items.PatternCardItemModel;

public class DigistorePatternCard extends StaticPowerItem implements ICustomModelSupplier {
	public static final String ENCODED_PATTERN_TAG = "pattern";

	public DigistorePatternCard(String name) {
		super(name);
	}

	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		// Get the item name.
		IFormattableTextComponent cardName = (IFormattableTextComponent) super.getDisplayName(stack);

		// If we have a pattern, mark this card as encoded.
		if (stack.hasTag() && stack.getTag().contains(ENCODED_PATTERN_TAG)) {
			cardName.append(new StringTextComponent(" (Encoded)"));
		}

		// Return the final name.
		return cardName;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, boolean showAdvanced) {
		if (showAdvanced) {
			// Try to get the pattern.
			EncodedDigistorePattern pattern = EncodedDigistorePattern.readFromPatternCard(stack);

			// If there is no pattern, do nothing.
			if (pattern == null) {
				return;
			}

			// Add inputs.
			tooltip.add(new StringTextComponent("Inputs: "));
			for (ItemStack input : pattern.getInputs()) {
				if (!input.isEmpty()) {
					tooltip.add(new StringTextComponent("  �").append(input.getDisplayName()));
				}
			}

			// Add outputs.
			tooltip.add(new StringTextComponent("Output: "));
			if (!pattern.getOutput().isEmpty()) {
				IFormattableTextComponent outputTooltip = new StringTextComponent("  �").append(pattern.getOutput().getDisplayName());
				if (pattern.getOutput().getCount() > 1) {
					outputTooltip.append(new StringTextComponent(" x" + pattern.getOutput().getCount()));
				}
				tooltip.add(outputTooltip);
			}
		}
	}

	public ItemStack getBlankEncodedCardForPreview() {
		ItemStack output = new ItemStack(this);
		output.setTag(new CompoundNBT());
		output.getTag().put(ENCODED_PATTERN_TAG, new CompoundNBT());
		return output;
	}

	public ItemStack getPatternForRecipe(EncodedDigistorePattern recipe) {
		// Create the pattern stack.
		ItemStack output = new ItemStack(this);

		if (!recipe.isValid()) {
			return output;
		}

		// Give it a tag.
		output.setTag(new CompoundNBT());

		// Put the pattern on the itemstack.
		output.getTag().put(ENCODED_PATTERN_TAG, recipe.serialize());

		// Return the encoded itemstack.
		return output;
	}

	public static boolean hasPattern(ItemStack card) {
		return card.getItem() instanceof DigistorePatternCard && card.hasTag() && card.getTag().contains(DigistorePatternCard.ENCODED_PATTERN_TAG);
	}

	@Override
	public boolean hasModelOverride(BlockState state) {
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IBakedModel getModelOverride(BlockState state, IBakedModel existingModel, ModelBakeEvent event) {
		IBakedModel encodedModel = event.getModelRegistry().get(StaticPowerAdditionalModels.DIGISTORE_PATTERN_CARD_ENCODED);
		return new PatternCardItemModel(existingModel, encodedModel);
	}

}
