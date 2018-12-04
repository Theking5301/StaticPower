package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemMaterials;

public class LumberMillRecipes {
	
	public static void registerLumberMillRecipes() {
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG, 0), new ItemStack(Blocks.PLANKS, 8, 0), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG, 1), new ItemStack(Blocks.PLANKS, 8, 1), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG, 2), new ItemStack(Blocks.PLANKS, 8, 2), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG, 3), new ItemStack(Blocks.PLANKS, 8, 3), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG2, 0), new ItemStack(Blocks.PLANKS, 8, 4), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		RegisterHelper.registerLumberMillRecipe(Craft.ing(Blocks.LOG2, 1), new ItemStack(Blocks.PLANKS, 8, 5), ItemMaterials.dustWood, new FluidStack(ModFluids.TreeSap, 50));
		
		RegisterHelper.registerLumberMillRecipe(Craft.ing(ModBlocks.StaticWood), new ItemStack(Item.getItemFromBlock(ModBlocks.StaticPlanks), 8), ItemMaterials.dustWood);
		RegisterHelper.registerLumberMillRecipe(Craft.ing(ModBlocks.EnergizedWood), new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedPlanks), 8), ItemMaterials.dustWood);
		RegisterHelper.registerLumberMillRecipe(Craft.ing(ModBlocks.LumumWood), new ItemStack(Item.getItemFromBlock(ModBlocks.LumumPlanks), 8), ItemMaterials.dustWood);
	}
}
