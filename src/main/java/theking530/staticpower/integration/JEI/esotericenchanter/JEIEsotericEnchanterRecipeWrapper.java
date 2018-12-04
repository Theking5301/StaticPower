package theking530.staticpower.integration.JEI.esotericenchanter;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.EsotericEnchanterRecipeWrapper;

public class JEIEsotericEnchanterRecipeWrapper implements IRecipeWrapper{

	private EsotericEnchanterRecipeWrapper recipe;
	
	public JEIEsotericEnchanterRecipeWrapper(EsotericEnchanterRecipeWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs1 = new ArrayList<ItemStack>();
		for(ItemStack inputItem : recipe.getIngredient1().getMatchingStacks()) {
			inputs1.add(inputItem);
		}
		inputLists.add(inputs1);
		
		ArrayList<ItemStack> inputs2 = new ArrayList<ItemStack>();
		if(recipe.getIngredient2() != null) {
			for(ItemStack inputItem : recipe.getIngredient2().getMatchingStacks()) {
				inputs2.add(inputItem);
			}
		}else{
			inputs2.add(ItemStack.EMPTY);
		}
		inputLists.add(inputs2);
	
		ArrayList<ItemStack> inputs3 = new ArrayList<ItemStack>();
		if(recipe.getIngredient3() != null) {
			for(ItemStack inputItem : recipe.getIngredient3().getMatchingStacks()) {
				inputs3.add(inputItem);
			}
		}else{
			inputs3.add(ItemStack.EMPTY);
		}
		inputLists.add(inputs3);
		
	    ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setInput(FluidStack.class, recipe.getInputFluidStack());
	    ingredients.setOutput(ItemStack.class, recipe.getOutputItemStack());
	}
}
