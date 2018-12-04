package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraftforge.fluids.FluidStack;

public class DistilleryRecipeWrapper {
	
	private final FluidStack INPUT_FLUIDSTACK;
	private final FluidStack OUTPUT_FLUIDSTACK;
	private final int MIN_HEAT;
	private final int HEAT_COST;

	public DistilleryRecipeWrapper(FluidStack inputFluid, FluidStack outputFluid, int heatMin, int heatCost) {
		INPUT_FLUIDSTACK = inputFluid;
		OUTPUT_FLUIDSTACK = outputFluid;
		MIN_HEAT = heatMin;
		HEAT_COST = heatCost;
	}

	public FluidStack getInputFluid(){
		return INPUT_FLUIDSTACK;
	}
	public FluidStack getOutputFluid(){
		return OUTPUT_FLUIDSTACK;
	}
	public int getMinHeat(){
		return MIN_HEAT;
	}
	public int getHeatCost(){
		return HEAT_COST;
	}
}
