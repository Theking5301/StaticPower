package theking530.staticpower.integration.JEI.condenser;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.CondenserRecipeWrapper;

public class JEICondenserRecipeWrapper implements IRecipeWrapper{
	
	private CondenserRecipeWrapper recipe;
	
	public JEICondenserRecipeWrapper(CondenserRecipeWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
	    ingredients.setInput(FluidStack.class, recipe.getInputFluid());
	    ingredients.setOutput(FluidStack.class, recipe.getOutputFluid());
	}
}
