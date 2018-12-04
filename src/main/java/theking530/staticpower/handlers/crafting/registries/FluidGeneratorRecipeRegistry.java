package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FluidGeneratorRecipeWrapper;

public class FluidGeneratorRecipeRegistry {

	private static final FluidGeneratorRecipeRegistry FGENERATOR_BASE = new FluidGeneratorRecipeRegistry();
	private Map<FluidStack, FluidGeneratorRecipeWrapper> fGenerating_list = new HashMap<FluidStack, FluidGeneratorRecipeWrapper>();
	
	public static FluidGeneratorRecipeRegistry Generating() {
		return FGENERATOR_BASE;
	}	
	private FluidGeneratorRecipeRegistry() {
		
	}
	public void addRecipe(FluidStack fluidInput, int powerOutput){
		FluidGeneratorRecipeWrapper tempWrapper = new FluidGeneratorRecipeWrapper(fluidInput, powerOutput);
		fGenerating_list.put(fluidInput, tempWrapper);
	}
    public Map<FluidStack, FluidGeneratorRecipeWrapper> getGeneratingRecipes() {
        return this.fGenerating_list;
    }
    /** Given input item stack and the fluidstack in the infuser */
	public int getPowerOutput(FluidStack fluidInput) {
		Iterator<Entry<FluidStack, FluidGeneratorRecipeWrapper>> iterator = this.fGenerating_list.entrySet().iterator();
		Entry<FluidStack, FluidGeneratorRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return -1;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput));
		FluidGeneratorRecipeWrapper tempWrapper = (FluidGeneratorRecipeWrapper)entry.getValue();
		return tempWrapper.getPowerPerTick();
	}
	private boolean isValidCombination(Entry<FluidStack, FluidGeneratorRecipeWrapper> entry, FluidStack fluidInput) {
		FluidGeneratorRecipeWrapper tempWrapper = (FluidGeneratorRecipeWrapper)entry.getValue();
		
		if(tempWrapper.getFluid() != null && fluidInput != null && tempWrapper.getFluid().getFluid() == fluidInput.getFluid()) {
			return true;
		}
		return false;
	}
	
}
