package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraftforge.fluids.FluidStack;

public class FarmerRecipeWrapper {

	private FluidStack inputFluid;
	private float treeFarmerBoneMealChance;
	
	public FarmerRecipeWrapper(FluidStack input, float mult) {
		inputFluid = input;
		treeFarmerBoneMealChance = mult;
	}
	
	public FluidStack getInputFluid() {
		return inputFluid;
	}
	public float getTreeFarmerBonemealChance() {
		return treeFarmerBoneMealChance;
	}
	public boolean isSatisfied(FluidStack fluid) {
		return fluid != null && fluid.isFluidEqual(inputFluid);
	}
}
