package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import theking530.staticpower.handlers.crafting.wrappers.FormerRecipeWrapper;

public class FormerRecipeRegistry {
	
	private static final FormerRecipeRegistry FORMER_BASE = new FormerRecipeRegistry();
	
	private Map<Ingredient, FormerRecipeWrapper> formingList = new HashMap<Ingredient, FormerRecipeWrapper>();

	public static FormerRecipeRegistry Forming() {
		return FORMER_BASE;
	}	
	private FormerRecipeRegistry() {
		
	}
	public void addRecipe(ItemStack output, Ingredient input, Ingredient mold){
		putLists(input, new FormerRecipeWrapper(output, input, mold));
	}
	public void putLists(Ingredient input, FormerRecipeWrapper outputs){
		formingList.put(input, outputs);
	}
    public Map<Ingredient, FormerRecipeWrapper> getFormingList() {
        return this.formingList;
    }
	public FormerRecipeWrapper getFormingResult(ItemStack inputItem, ItemStack inputMold) {
		for(Entry<Ingredient, FormerRecipeWrapper> entry : formingList.entrySet()) {
			if(!inputMold.isEmpty() && entry.getValue().satisfiesRecipe(inputItem, inputMold)) {
				return entry.getValue();
			}
		}	
		return null;
	}
	public boolean isValidMold(ItemStack inputMold) {
		for(Entry<Ingredient, FormerRecipeWrapper> entry : formingList.entrySet()) {
			if(!inputMold.isEmpty() && entry.getValue().isValidMold(inputMold)) {
				return true;
			}
		}	
		return false;
	}
}