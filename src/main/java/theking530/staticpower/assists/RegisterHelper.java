package theking530.staticpower.assists;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import theking530.staticpower.handlers.crafting.registries.CentrifugeRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.CondenserRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.DistilleryRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.EsotericEnchanterRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.FarmerRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.FluidGeneratorRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.FormerRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.FusionRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.GrinderRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.InfuserRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.LumberMillRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.SolderingRecipeRegistry;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.GrinderOutputWrapper.GrinderOutput;
import theking530.staticpower.handlers.crafting.wrappers.SolderingRecipeWrapper;

public class RegisterHelper  {
	
	public static void registerFermenterRecipe(Ingredient input, FluidStack output) {
		FermenterRecipeRegistry.Fermenting().addRecipe(input, output);
	}
	public static void registerGrinderRecipe(Ingredient ingredient, GrinderOutput... outputs) {
		GrinderRecipeRegistry.Grinding().addRecipe(ingredient, outputs);
	}
	public static void registerInfuserRecipe(ItemStack output, Ingredient input, FluidStack fluidStack) {
		InfuserRecipeRegistry.Infusing().addRecipe(input, output, fluidStack);			
	}
	public static void registerFusionRecipe(ItemStack output, Ingredient...inputs) {
		if(output != null && inputs[0] != null) {
			FusionRecipeRegistry.Fusing().addRecipe(output, inputs);			
		}
	}
	public static void registerCentrifugeRecipe(Ingredient input, int minimumSpeed, ItemStack... outputs) {
		CentrifugeRecipeRegistry.Centrifuging().addRecipe(input, minimumSpeed, outputs);
	}
	public static void registerFormerRecipe(ItemStack output, Ingredient input, Ingredient mold) {
		FormerRecipeRegistry.Forming().addRecipe(output, input, mold);
	}
	public static void registerFarmingRecipe(FluidStack input, float output) {
		FarmerRecipeRegistry.Farming().addRecipe(input, output);
	}
	public static void registerSqueezerRecipe(ItemStack input, ItemStack result, FluidStack outputFluid) {
		SqueezerRecipeRegistry.Squeezing().addRecipe(input, result, outputFluid);
	}
	
	public static void registerLumberMillRecipe(Ingredient input, ItemStack output1, FluidStack outputFluid) {
		LumberMillRecipeRegistry.Milling().addRecipe(input, output1, outputFluid);
	}
	public static void registerLumberMillRecipe(Ingredient input, ItemStack output1, ItemStack output2, FluidStack outputFluid) {
		LumberMillRecipeRegistry.Milling().addRecipe(input, output1, output2, outputFluid);
	}
	public static void registerLumberMillRecipe(Ingredient input, ItemStack output1) {
		LumberMillRecipeRegistry.Milling().addRecipe(input, output1);
	}
	public static void registerLumberMillRecipe(Ingredient input, ItemStack output1, ItemStack output2) {
		LumberMillRecipeRegistry.Milling().addRecipe(input, output1, output2);
	}
	
	public static void registerSolderingRecipe(ItemStack outputStack, Object ... inputParams) {
        ShapedPrimer primer = CraftingHelper.parseShaped(inputParams);
		SolderingRecipeRegistry.Soldering().addRecipe(new SolderingRecipeWrapper("", primer.width, primer.height, primer.input, outputStack));
	}
	
	public static void registerFluidGeneratorRecipe(FluidStack inputFluid, int powerPerTick) {
		FluidGeneratorRecipeRegistry.Generating().addRecipe(inputFluid, powerPerTick);
	}
	public static void registerEsotericEnchanterRecipe(ItemStack output, Ingredient input1, Ingredient input2, Ingredient input3, FluidStack inputFluidStack) {
		EsotericEnchanterRecipeRegistry.Enchanting().addRecipe(output, input1, input2, input3, inputFluidStack);
	}
	public static void registerEsotericEnchanterRecipe(ItemStack output, Ingredient input1, Ingredient input2,FluidStack inputFluidStack) {
		EsotericEnchanterRecipeRegistry.Enchanting().addRecipe(output, input1, input2, inputFluidStack);
	}
	public static void registerCondenserRecipe(FluidStack fluidInput, FluidStack fluidOutput, int condensingTime) {
		CondenserRecipeRegistry.Condensing().addRecipe(fluidInput, fluidOutput, condensingTime);
	}
	public static void registerDistilleryRecipe(FluidStack fluidInput, FluidStack fluidOutput, int heatMin, int heatCost) {
		DistilleryRecipeRegistry.Distillery().addRecipe(fluidInput, fluidOutput, heatMin, heatCost);
	}
	public static void addShapedRecipe(String name, String group, ItemStack stack, Object... recipeComponents) {	
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, name), new ResourceLocation(group), stack, recipeComponents);
	}
	public static void addShapelessRecipe(String name, String group, ItemStack stack, Ingredient... recipeComponents) {	
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, name), new ResourceLocation(group), stack, recipeComponents);
	}
}
