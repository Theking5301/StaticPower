package theking530.staticpower.integration.JEI.cropsqueezer;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;

public class CropSqueezerRecipeWrapper implements IRecipeWrapper{

	 public static final Factory FACTORY = new Factory();

	 	public ItemStack inputItem;
	 	public ItemStack outputItem;
	    public FluidStack outputFluid;
	    public SqueezerOutputWrapper recipe;
	    
	    public CropSqueezerRecipeWrapper(SqueezerOutputWrapper recipe) {
	    	inputItem = recipe.getInputItem();
	    	outputItem = recipe.getOutputItem();
	    	outputFluid = recipe.getOutputFluid();
	    	this.recipe = recipe;
	    }

	    @Override
	    public void getIngredients(IIngredients ingredients) {
	        ingredients.setInput(ItemStack.class, inputItem);
	        ingredients.setOutput(ItemStack.class, outputItem);
	        ingredients.setOutput(FluidStack.class, outputFluid);
	    }

	    private static class Factory implements IRecipeWrapperFactory<SqueezerOutputWrapper> {
	        @Override
	        public IRecipeWrapper getRecipeWrapper(SqueezerOutputWrapper recipe) {
	            return new CropSqueezerRecipeWrapper(recipe);
	        }
	    }
}
