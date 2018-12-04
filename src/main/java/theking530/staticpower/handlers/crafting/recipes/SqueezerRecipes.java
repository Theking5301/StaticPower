package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.blocks.crops.ModPlants;
import theking530.staticpower.fluids.ModFluids;

public class SqueezerRecipes {
	
	public static void registerSqueezingRecipes() {
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.StaticCrop), new ItemStack(ModPlants.DepletedCrop), new FluidStack(ModFluids.StaticFluid, 200));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.EnergizedCrop), new ItemStack(ModPlants.DepletedCrop), new FluidStack(ModFluids.EnergizedFluid, 200));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.LumumCrop), new ItemStack(ModPlants.DepletedCrop), new FluidStack(ModFluids.LumumFluid, 200));
		
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.StaticSeeds), new ItemStack(Items.WHEAT_SEEDS), new FluidStack(ModFluids.StaticFluid, 25));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.EnergizedSeeds), new ItemStack(Items.WHEAT_SEEDS), new FluidStack(ModFluids.EnergizedFluid, 25));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(ModPlants.LumumSeeds), new ItemStack(Items.WHEAT_SEEDS), new FluidStack(ModFluids.LumumFluid, 25));
		
		RegisterHelper.registerSqueezerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticWood)), new ItemStack(Item.getItemFromBlock(Blocks.LOG)), new FluidStack(ModFluids.StaticFluid, 150));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedWood)), new ItemStack(Item.getItemFromBlock(Blocks.LOG)), new FluidStack(ModFluids.EnergizedFluid, 150));
		RegisterHelper.registerSqueezerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumWood)), new ItemStack(Item.getItemFromBlock(Blocks.LOG)), new FluidStack(ModFluids.LumumFluid, 150));
	}
}
