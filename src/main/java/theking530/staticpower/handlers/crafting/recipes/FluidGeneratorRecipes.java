package theking530.staticpower.handlers.crafting.recipes;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.fluids.ModFluids;

public class FluidGeneratorRecipes {

	public static void registerFluidGeneratorRecipes() {
		registerRecipe(ModFluids.StaticFluid, 512);
		registerRecipe(ModFluids.EnergizedFluid, 1024);
		registerRecipe(ModFluids.LumumFluid, 2048);
		registerRecipe(ModFluids.Mash, 60);
		registerRecipe(ModFluids.EvaporatedMash, 120);
		registerRecipe(ModFluids.Ethanol, 480);
		registerRecipe(ModFluids.RefinedFluid, 16384);
		registerRecipe(ModFluids.TreeSap, 120);
		registerRecipe(ModFluids.TreeOil, 512);
	}
	public static void registerRecipe(Fluid fluid, int output) {
		RegisterHelper.registerFluidGeneratorRecipe(new FluidStack(fluid, 1),  output);
	}
}
