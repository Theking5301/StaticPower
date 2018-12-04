package theking530.staticpower.handlers.crafting.recipes;

import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.fluids.ModFluids;

public class DistilleryRecipes {

	public static void registerDistilleryRecipes() {
		RegisterHelper.registerDistilleryRecipe(new FluidStack(ModFluids.Mash, 1), new FluidStack(ModFluids.EvaporatedMash, 1), 100, 10);
		RegisterHelper.registerDistilleryRecipe(new FluidStack(ModFluids.TreeSap, 1), new FluidStack(ModFluids.TreeOil, 1), 100, 10);
	}
}
