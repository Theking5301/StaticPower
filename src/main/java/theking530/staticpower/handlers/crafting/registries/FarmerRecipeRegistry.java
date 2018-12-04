package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FarmerRecipeWrapper;

public class FarmerRecipeRegistry {

	private static final FarmerRecipeRegistry FARMER_BASE = new FarmerRecipeRegistry();
	
	private Map<FluidStack, FarmerRecipeWrapper> farmeing_list = new HashMap<FluidStack, FarmerRecipeWrapper>();
	
	public static FarmerRecipeRegistry Farming() {
		return FARMER_BASE;
	}	
	private FarmerRecipeRegistry() {
		
	}
	public void addRecipe(FluidStack input, float multiplier){
		FarmerRecipeWrapper tempWrapper = new FarmerRecipeWrapper(input, multiplier);
		farmeing_list.put(input, tempWrapper);
	}
    public Map<FluidStack, FarmerRecipeWrapper> getFarmingRecipes() {
        return this.farmeing_list;
    }

	public FarmerRecipeWrapper getOutput(FluidStack fluidStack) {
		for(Entry<FluidStack, FarmerRecipeWrapper> entry : farmeing_list.entrySet()) {
			if(entry.getValue().isSatisfied(fluidStack)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
