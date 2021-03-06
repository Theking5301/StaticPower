package theking530.staticpower.data.crafting.wrappers.cauldron;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.data.crafting.AbstractStaticPowerRecipe;
import theking530.staticpower.data.crafting.ProbabilityItemStackOutput;
import theking530.staticpower.data.crafting.RecipeMatchParameters;
import theking530.staticpower.data.crafting.StaticPowerIngredient;

public class CauldronRecipe extends AbstractStaticPowerRecipe {
	public static final IRecipeType<CauldronRecipe> RECIPE_TYPE = IRecipeType.register("cauldron");

	private final StaticPowerIngredient input;
	private final ProbabilityItemStackOutput output;
	private final FluidStack inputFluid;
	private final FluidStack outputFluid;
	private final int timeInCauldron;
	private final boolean shouldDrainCauldron;

	public CauldronRecipe(ResourceLocation name, StaticPowerIngredient input, ProbabilityItemStackOutput output, FluidStack inputFluid, FluidStack outputFluid, boolean shouldDrainCauldron,
			int timeInCauldron) {
		super(name);
		this.input = input;
		this.output = output;
		this.inputFluid = inputFluid;
		this.outputFluid = outputFluid;
		this.timeInCauldron = timeInCauldron;
		this.shouldDrainCauldron = shouldDrainCauldron;
	}

	public StaticPowerIngredient getInput() {
		return input;
	}

	public ProbabilityItemStackOutput getOutput() {
		return output;
	}

	public boolean hasItemOutput() {
		return !output.isEmpty();
	}

	public FluidStack getRequiredFluid() {
		return inputFluid;
	}

	public FluidStack getOutputFluid() {
		return outputFluid;
	}

	public int getRequiredTimeInCauldron() {
		return timeInCauldron;
	}

	public boolean shouldDrainCauldron() {
		return shouldDrainCauldron;
	}

	@Override
	public boolean isValid(RecipeMatchParameters matchParams) {
		boolean matched = true;

		// Check fluids.
		if (!inputFluid.isEmpty() && matchParams.shouldVerifyFluids()) {
			matched &= matchParams.hasFluids() && matchParams.getFluids()[0].equals(inputFluid);
			if (matched && matchParams.shouldVerifyFluidAmounts()) {
				matched &= matchParams.hasFluids() && matchParams.getFluids()[0].getAmount() >= 1000; // ALWAYS we check for a full bucket.
			}
		}

		// Check items.
		if (matched && matchParams.shouldVerifyItems()) {
			if (matchParams.shouldVerifyItemCounts()) {
				matched &= matchParams.hasItems() && input.testWithCount(matchParams.getItems()[0]);
			} else {
				matched &= matchParams.hasItems() && input.test(matchParams.getItems()[0]);
			}
		}

		return matched;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return CauldronRecipeSerializer.INSTANCE;
	}

	@Override
	public IRecipeType<?> getType() {
		return RECIPE_TYPE;
	}
}
