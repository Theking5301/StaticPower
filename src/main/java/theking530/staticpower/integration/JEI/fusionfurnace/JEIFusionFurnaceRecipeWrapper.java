package theking530.staticpower.integration.JEI.fusionfurnace;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import theking530.staticpower.handlers.crafting.wrappers.FusionFurnaceRecipeWrapper;

public class JEIFusionFurnaceRecipeWrapper implements IRecipeWrapper{
	
	private FusionFurnaceRecipeWrapper recipe;
	
	public JEIFusionFurnaceRecipeWrapper(FusionFurnaceRecipeWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		for(Ingredient ingredient : recipe.getInputs()) {
			ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
			for(ItemStack inputItem : ingredient.getMatchingStacks()) {
				inputs.add(inputItem);
			}
			inputLists.add(inputs);
		}
		
	    ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setOutput(ItemStack.class, recipe.getOutputItem());
	}
}
