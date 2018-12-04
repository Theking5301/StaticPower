package theking530.staticpower.integration.JEI.fermenter;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FermenterOutputWrapper;

public class JEIFermenterRecipeWrapper implements IRecipeWrapper{
	
	public FermenterOutputWrapper recipe;
	
	public JEIFermenterRecipeWrapper(FermenterOutputWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack inputItem : recipe.getInputIngredient().getMatchingStacks()) {
			inputs.add(inputItem);
		}
		inputLists.add(inputs);
		
	    ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setOutput(FluidStack.class, recipe.getOutputFluidStack());
	}
}
