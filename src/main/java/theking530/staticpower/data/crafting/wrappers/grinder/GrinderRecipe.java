package theking530.staticpower.data.crafting.wrappers.grinder;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.data.crafting.AbstractMachineRecipe;
import theking530.staticpower.data.crafting.ProbabilityItemStackOutput;
import theking530.staticpower.data.crafting.RecipeMatchParameters;
import theking530.staticpower.data.crafting.StaticPowerIngredient;

public class GrinderRecipe extends AbstractMachineRecipe {
	public static final IRecipeType<GrinderRecipe> RECIPE_TYPE = IRecipeType.register("grinder");

	private final ProbabilityItemStackOutput[] outputs;
	/**
	 * This is a helper datatype to use whenever you need access just to the items
	 * (for example, to see if an inventory can take the items).
	 */
	private final ItemStack[] outputItems;
	private final StaticPowerIngredient inputItem;

	public GrinderRecipe(ResourceLocation name, int processingTime, long powerCost, StaticPowerIngredient input, ProbabilityItemStackOutput... outputs) {
		super(name, processingTime, powerCost);
		this.inputItem = input;
		this.outputs = outputs;
		// Cache the output items.
		this.outputItems = new ItemStack[outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputItems[i] = outputs[i].getItem();
		}
	}

	public ProbabilityItemStackOutput[] getOutputItems() {
		return outputs;
	}

	public ItemStack[] getRawOutputItems() {
		return outputItems;
	}

	public StaticPowerIngredient getInputIngredient() {
		return inputItem;
	}

	@Override
	public boolean isValid(RecipeMatchParameters matchParams) {
		boolean matched = true;

		// Check items.
		if (matchParams.shouldVerifyItems()) {
			if (matchParams.shouldVerifyItemCounts()) {
				matched &= matchParams.hasItems() && inputItem.testWithCount(matchParams.getItems()[0]);
			} else {
				matched &= matchParams.hasItems() && inputItem.test(matchParams.getItems()[0]);
			}
		}

		return matched;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return GrinderRecipeSerializer.INSTANCE;
	}

	@Override
	public IRecipeType<?> getType() {
		return RECIPE_TYPE;
	}
}