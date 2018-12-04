package theking530.staticpower.integration.JEI.former;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import theking530.staticpower.handlers.crafting.wrappers.FormerRecipeWrapper;

public class JEIFormerRecipeWrapper implements IRecipeWrapper{

    private FormerRecipeWrapper recipe;

    public JEIFormerRecipeWrapper(FormerRecipeWrapper recipe) {
    	this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
		ItemStack recipeOutput = recipe.getOutputItemStack();

		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> molds = new ArrayList<ItemStack>();
		for(ItemStack mold : recipe.getRequiredMold().getMatchingStacks()) {
			molds.add(mold);
		}
		inputLists.add(molds);
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack input : recipe.getInputIngredient().getMatchingStacks()) {
			inputs.add(input);
		}
		inputLists.add(inputs);
		
		ingredients.setInputLists(ItemStack.class, inputLists);
		ingredients.setOutput(ItemStack.class, recipeOutput);
    }	    
}
