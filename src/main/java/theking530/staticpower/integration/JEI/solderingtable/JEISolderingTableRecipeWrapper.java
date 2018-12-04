package theking530.staticpower.integration.JEI.solderingtable;

import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import theking530.staticpower.handlers.crafting.wrappers.SolderingRecipeWrapper;

public class JEISolderingTableRecipeWrapper implements IRecipeWrapper{

    private SolderingRecipeWrapper recipe;
    private IJeiHelpers jeiHelpers;
    
    public JEISolderingTableRecipeWrapper(IJeiHelpers jeiHelpers, SolderingRecipeWrapper recipe) {
    	this.recipe = recipe;
    	this.jeiHelpers = jeiHelpers;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
		ItemStack recipeOutput = recipe.getRecipeOutput();
		IStackHelper stackHelper = jeiHelpers.getStackHelper();

		List<List<ItemStack>> inputLists = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
		ingredients.setInputLists(ItemStack.class, inputLists);
		ingredients.setOutput(ItemStack.class, recipeOutput);
    }
}
