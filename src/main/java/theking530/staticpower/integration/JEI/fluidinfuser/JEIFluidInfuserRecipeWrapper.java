package theking530.staticpower.integration.JEI.fluidinfuser;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FluidInfuserOutputWrapper;

public class JEIFluidInfuserRecipeWrapper implements IRecipeWrapper{

	private FluidInfuserOutputWrapper recipe;
	
	public JEIFluidInfuserRecipeWrapper(FluidInfuserOutputWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack inputItem : recipe.getInputItemStack().getMatchingStacks()) {
			inputs.add(inputItem);
		}
		inputLists.add(inputs);
		
	    ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setInput(FluidStack.class, recipe.getRequiredFluidStack());
	    ingredients.setOutput(ItemStack.class, recipe.getOutputItemStack());
	}
}
