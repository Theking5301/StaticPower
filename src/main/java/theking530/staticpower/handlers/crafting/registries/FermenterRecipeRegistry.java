package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FermenterOutputWrapper;

public class FermenterRecipeRegistry {

	private static final FermenterRecipeRegistry FERENTER_BASE = new FermenterRecipeRegistry();
	
	private Map<Ingredient, FermenterOutputWrapper> fermenting_list = new HashMap<Ingredient, FermenterOutputWrapper>();
	
	public static FermenterRecipeRegistry Fermenting() {
		return FERENTER_BASE;
	}	
	private FermenterRecipeRegistry() {
		
	}
	public void addRecipe(Ingredient input, FluidStack output){
		FermenterOutputWrapper tempWrapper = new FermenterOutputWrapper(input, output);
		fermenting_list.put(input, tempWrapper);
	}
    public Map<Ingredient, FermenterOutputWrapper> getFermentingRecipes() {
        return this.fermenting_list;
    }
	public FermenterOutputWrapper getRecipe(ItemStack inputItemstack) {
		for(Entry<Ingredient, FermenterOutputWrapper> entry : fermenting_list.entrySet()) {
			if(entry.getValue().isSatisfied(inputItemstack)) {
				return entry.getValue();
			}
		}
		return null;
	}
	public FluidStack getFluidResult(ItemStack inputItemstack) {
		for(Entry<Ingredient, FermenterOutputWrapper> entry : fermenting_list.entrySet()) {
			if(entry.getValue().isSatisfied(inputItemstack)) {
				return entry.getValue().getOutputFluidStack();
			}
		}
		return null;
	}
}
