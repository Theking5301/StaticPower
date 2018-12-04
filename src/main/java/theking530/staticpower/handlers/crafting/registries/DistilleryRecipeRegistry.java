package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.DistilleryRecipeWrapper;

public class DistilleryRecipeRegistry {

private static final DistilleryRecipeRegistry DISTILLERY_BASE = new DistilleryRecipeRegistry();

	private Map<FluidStack, DistilleryRecipeWrapper> distillery_list = new HashMap<FluidStack, DistilleryRecipeWrapper>();
	
	public static DistilleryRecipeRegistry Distillery() {
		return DISTILLERY_BASE;
	}	
	private DistilleryRecipeRegistry() {
		
	}
	public void addRecipe(FluidStack fluidInput, FluidStack fluidOutput, int heatMin, int heatCost){
		DistilleryRecipeWrapper tempWrapper = new DistilleryRecipeWrapper(fluidInput, fluidOutput, heatMin, heatCost);
		distillery_list.put(fluidInput, tempWrapper);
	}
    public Map<FluidStack, DistilleryRecipeWrapper> getDistilleryRecipes() {
        return this.distillery_list;
    }   
	public int getFluidInputAmount(FluidStack fluidInput, int currentHeat) {
		Iterator<Entry<FluidStack, DistilleryRecipeWrapper>> iterator = this.distillery_list.entrySet().iterator();
		Entry<FluidStack, DistilleryRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput, currentHeat));
		DistilleryRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getInputFluid().amount;
	}
	public FluidStack getFluidOutput(FluidStack fluidInput, int currentHeat) {
		Iterator<Entry<FluidStack, DistilleryRecipeWrapper>> iterator = this.distillery_list.entrySet().iterator();
		Entry<FluidStack, DistilleryRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput, currentHeat));
		DistilleryRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getOutputFluid();
	}
	public int getHeatCost(FluidStack fluidInput, int currentHeat) {
		Iterator<Entry<FluidStack, DistilleryRecipeWrapper>> iterator = this.distillery_list.entrySet().iterator();
		Entry<FluidStack, DistilleryRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput, currentHeat));
		DistilleryRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getHeatCost();
	}

	public int getHeatMin(FluidStack fluidInput, int currentHeat) {
		Iterator<Entry<FluidStack, DistilleryRecipeWrapper>> iterator = this.distillery_list.entrySet().iterator();
		Entry<FluidStack, DistilleryRecipeWrapper> entry;
		do {
			if (!iterator.hasNext()) {
				return 0;
			}
			entry = iterator.next();
		} while (!isValidCombination(entry, fluidInput, currentHeat));
		DistilleryRecipeWrapper tempWrapper = entry.getValue();
		return tempWrapper.getMinHeat();
	}
	private boolean isValidCombination(Entry<FluidStack, DistilleryRecipeWrapper> entry, FluidStack fluidInput, int currentHeat) {
		DistilleryRecipeWrapper tempWrapper = entry.getValue();
		
		if(tempWrapper.getInputFluid() != null && fluidInput != null && tempWrapper.getInputFluid().isFluidEqual(fluidInput)) {
			if(currentHeat >= tempWrapper.getMinHeat() && currentHeat >= tempWrapper.getHeatCost()) {
				return true;
			}
		}
		return false;
	}
	
}
