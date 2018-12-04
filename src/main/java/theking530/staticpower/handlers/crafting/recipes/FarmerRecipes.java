package theking530.staticpower.handlers.crafting.recipes;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.fluids.ModFluids;

public class FarmerRecipes {

	public static void registerFarmerRecipes() {
		RegisterHelper.registerFarmingRecipe(createFluidStack(FluidRegistry.WATER), 0.0f);
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.StaticFluid), .05f);
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.EnergizedFluid), .1f);
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.LumumFluid), .2f);
		
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.Mash), .3f);
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.EvaporatedMash), .35f);
		RegisterHelper.registerFarmingRecipe(createFluidStack(ModFluids.Ethanol), .5f);
	}
	public static FluidStack createFluidStack(Fluid fluid) {
		return new FluidStack(fluid, 1000); //Amount doesnt matter.
	}
}
