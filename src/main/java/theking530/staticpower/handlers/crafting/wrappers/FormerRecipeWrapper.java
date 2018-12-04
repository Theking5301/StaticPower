package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class FormerRecipeWrapper {

	private Ingredient inputIngredient;
	private Ingredient requiredMold;
	private ItemStack outputItemStack;
	
	public FormerRecipeWrapper(ItemStack output, Ingredient input, Ingredient mold) {
		inputIngredient = input;
		requiredMold = mold;
		outputItemStack = output;
	}
	
	public Ingredient getInputIngredient() {
		return inputIngredient;
	}
	public ItemStack getOutputItemStack() {
		return outputItemStack;
	}
	public Ingredient getRequiredMold() {
		return requiredMold;
	}
	public boolean satisfiesRecipe(ItemStack input, ItemStack mold) {
		if(inputIngredient.apply(input)) {
			if(requiredMold.apply(mold)) {
				return true;
			}
		}
		return false;
	}
	public boolean isValidMold(ItemStack mold) {
		return requiredMold.apply(mold);
	}
}
