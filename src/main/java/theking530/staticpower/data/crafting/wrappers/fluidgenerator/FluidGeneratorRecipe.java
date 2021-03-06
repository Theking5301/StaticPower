package theking530.staticpower.data.crafting.wrappers.fluidgenerator;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.data.crafting.AbstractStaticPowerRecipe;
import theking530.staticpower.data.crafting.RecipeMatchParameters;

public class FluidGeneratorRecipe extends AbstractStaticPowerRecipe {
	public static final IRecipeType<FluidGeneratorRecipe> RECIPE_TYPE = IRecipeType.register("fluid_generator");

	private final FluidStack fluid;
	private final int powerGeneration;

	public FluidGeneratorRecipe(ResourceLocation name, FluidStack fluid, int powerGeneration) {
		super(name);
		this.fluid = fluid;
		this.powerGeneration = powerGeneration;
	}

	public FluidStack getFluid() {
		return fluid;
	}

	public int getPowerGeneration() {
		return powerGeneration;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return FluidGeneratorRecipeSerializer.INSTANCE;
	}

	@Override
	public IRecipeType<?> getType() {
		return RECIPE_TYPE;
	}

	public boolean isValid(RecipeMatchParameters matchParams) {
		return fluid.isFluidEqual(matchParams.getFluids()[0]) && matchParams.getFluids()[0].getAmount() >= fluid.getAmount();
	}

	@Override
	public boolean equals(Object otherRecipe) {
		if (otherRecipe instanceof FluidGeneratorRecipe) {
			// Get the other bottle recipe.
			FluidGeneratorRecipe otherBottleRecipe = (FluidGeneratorRecipe) otherRecipe;

			// Check the fluids.
			if (fluid.getAmount() != otherBottleRecipe.getFluid().getAmount()
					|| !fluid.equals(otherBottleRecipe.getFluid()) && !FluidStack.areFluidStackTagsEqual(fluid, otherBottleRecipe.getFluid())) {
				return false;
			}

			// If all the above criteria are valid, return true.
			return true;
		}
		return false;
	}
}
