package theking530.staticpower.integration.JEI.lumbermill;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import theking530.staticpower.handlers.crafting.wrappers.LumberMillRecipeWrapper;

public class JEILumberMillRecipeWrapper implements IRecipeWrapper{

    private LumberMillRecipeWrapper recipe;

    public JEILumberMillRecipeWrapper(LumberMillRecipeWrapper recipe) {
    	this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
    	List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack input : recipe.getInput().getMatchingStacks()) {
			inputs.add(input);
		}
		inputLists.add(inputs);
		
		ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.add(recipe.getMainOutput());
		if(!recipe.getSecondaryOutput().isEmpty()) {
			outputs.add(recipe.getSecondaryOutput());
		}

		ingredients.setInputLists(ItemStack.class, inputLists);
		ingredients.setOutputs(ItemStack.class, outputs);
    }	    
}
