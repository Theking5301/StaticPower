package theking530.staticpower.integration.JEI.distillery;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.DistilleryRecipeWrapper;

public class JEIDistilleryRecipeWrapper implements IRecipeWrapper{
	
	private DistilleryRecipeWrapper recipe;
	
	public JEIDistilleryRecipeWrapper(DistilleryRecipeWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
	    ingredients.setInput(FluidStack.class, recipe.getInputFluid());
	    ingredients.setOutput(FluidStack.class, recipe.getOutputFluid());
	}
}
