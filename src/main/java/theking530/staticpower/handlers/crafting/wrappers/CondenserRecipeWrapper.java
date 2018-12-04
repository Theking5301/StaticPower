package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraftforge.fluids.FluidStack;

public class CondenserRecipeWrapper {
	
	private final FluidStack INPUT_FLUIDSTACK;
	private final FluidStack OUTPUT_FLUIDSTACK;
	private final int CONDENSING_TIME;

	public CondenserRecipeWrapper(FluidStack inputFluid, FluidStack outputFluid, int condensingTime) {
		INPUT_FLUIDSTACK = inputFluid;
		OUTPUT_FLUIDSTACK = outputFluid;
		CONDENSING_TIME = condensingTime;
	}

	public FluidStack getInputFluid(){
		return INPUT_FLUIDSTACK;
	}
	public FluidStack getOutputFluid(){
		return OUTPUT_FLUIDSTACK;
	}
	public int getCondensingTime(){
		return CONDENSING_TIME;
	}

}
