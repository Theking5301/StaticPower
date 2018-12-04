package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.LumberMillRecipeWrapper;

public class LumberMillRecipeRegistry {

	private static final LumberMillRecipeRegistry LUMBER_MILL_BASE = new LumberMillRecipeRegistry();
	
	private Map<Ingredient, LumberMillRecipeWrapper> lumberMillList = new HashMap<Ingredient, LumberMillRecipeWrapper>();
	
	public static LumberMillRecipeRegistry Milling() {
		return LUMBER_MILL_BASE;
	}	
	private LumberMillRecipeRegistry() {
		
	}
	
	public void addRecipe(Ingredient input, ItemStack output1, ItemStack output2, FluidStack fluid){
		LumberMillRecipeWrapper tempWrapper = new LumberMillRecipeWrapper(input, output1, output2, fluid);
		lumberMillList.put(input, tempWrapper);
	}
	public void addRecipe(Ingredient input, ItemStack output1, FluidStack fluid){
		LumberMillRecipeWrapper tempWrapper = new LumberMillRecipeWrapper(input, output1, ItemStack.EMPTY, fluid);
		lumberMillList.put(input, tempWrapper);
	}
	public void addRecipe(Ingredient input, ItemStack output1, ItemStack output2){
		LumberMillRecipeWrapper tempWrapper = new LumberMillRecipeWrapper(input, output1, output2, null);
		lumberMillList.put(input, tempWrapper);
	}
	public void addRecipe(Ingredient input, ItemStack output1){
		LumberMillRecipeWrapper tempWrapper = new LumberMillRecipeWrapper(input, output1, ItemStack.EMPTY, null);
		lumberMillList.put(input, tempWrapper);
	}
    public Map<Ingredient, LumberMillRecipeWrapper> getMillingRecipes() {
        return this.lumberMillList;
    }
	public LumberMillRecipeWrapper getMillingRecipe(ItemStack inputItemstack) {
		for(Entry<Ingredient, LumberMillRecipeWrapper> entry : lumberMillList.entrySet()) {
			if(entry.getValue().isSatisfied(inputItemstack)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
