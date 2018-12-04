package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.CondenserRecipeWrapper;

public class CondenserRecipeRegistry {

private static final CondenserRecipeRegistry CONDENSER_BASE = new CondenserRecipeRegistry();
	
	private Map<FluidStack, CondenserRecipeWrapper> condensing_list = new HashMap<FluidStack, CondenserRecipeWrapper>();
	
	public static CondenserRecipeRegistry Condensing() {
		return CONDENSER_BASE;
	}	
	private CondenserRecipeRegistry() {
		
	}
	public void addRecipe(FluidStack fluidInput, FluidStack fluidOutput, int condensingTime){
		CondenserRecipeWrapper tempWrapper = new CondenserRecipeWrapper(fluidInput, fluidOutput, condensingTime);
		condensing_list.put(fluidInput, tempWrapper);
	}
    public Map<FluidStack, CondenserRecipeWrapper> getCondenserRecipes() {
        return this.condensing_list;
    }
	public int getFluidInputAmount(FluidStack fluidInput) {
		Iterator<Entry<FluidStack, CondenserRecipeWrapper> >iterator = this.condensing_list.entrySet().iterator();
		Entry<FluidStack, CondenserRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput));
		CondenserRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getInputFluid().amount;
	}
	public FluidStack getFluidOutput(FluidStack fluidInput) {
		Iterator<Entry<FluidStack, CondenserRecipeWrapper>> iterator = this.condensing_list.entrySet().iterator();
		Entry<FluidStack, CondenserRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput));
		CondenserRecipeWrapper tempWrapper = (CondenserRecipeWrapper)entry.getValue();
		return tempWrapper.getOutputFluid();
	}
	public int getCondensingTime(FluidStack fluidInput) {
		Iterator<Entry<FluidStack, CondenserRecipeWrapper>> iterator = this.condensing_list.entrySet().iterator();
		Entry<FluidStack, CondenserRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput));
		CondenserRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getCondensingTime();
	}
	private boolean isValidCombination(Entry<FluidStack, CondenserRecipeWrapper> entry, FluidStack fluidInput) {
		CondenserRecipeWrapper tempWrapper = entry.getValue();
		
		if(tempWrapper.getInputFluid() != null && fluidInput != null && tempWrapper.getInputFluid().isFluidEqual(fluidInput)) {
			return true;
		}
		return false;
	}
	
}
