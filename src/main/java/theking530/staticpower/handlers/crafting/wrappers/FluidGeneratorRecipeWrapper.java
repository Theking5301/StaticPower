package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraftforge.fluids.FluidStack;

public class FluidGeneratorRecipeWrapper {

	private final FluidStack FLUIDSTACK;
	private final int POWER;
	
	public FluidGeneratorRecipeWrapper(FluidStack fluid, int powerPerTick) {
		FLUIDSTACK = fluid;
		POWER = powerPerTick;
	}

	public FluidStack getFluid(){
		return FLUIDSTACK;
	}
	public int getPowerPerTick() {
		return POWER;
	}
}
