package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.Craft;

public class FermenterRecipes {
	
	public static void registerFermenterRecipes() {
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.BEETROOT), mash(35));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.BEETROOT_SEEDS), mash(15));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.POTATO), mash(25));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.CARROT), mash(20));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.WHEAT), mash(15));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.WHEAT_SEEDS), mash(10));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.POISONOUS_POTATO), mash(100));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.MELON_SEEDS), mash(10));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.MELON), mash(50));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.PUMPKIN_SEEDS), mash(10));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Blocks.PUMPKIN), mash(50));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.SPIDER_EYE), mash(100));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.SUGAR), mash(50));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Items.REEDS), mash(40));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Blocks.WATERLILY), mash(100));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Blocks.BROWN_MUSHROOM), mash(100));
		RegisterHelper.registerFermenterRecipe(Craft.ing(Blocks.RED_MUSHROOM), mash(100));

		RegisterHelper.registerFermenterRecipe(Craft.ing("treeSapling"), mash(25));
		RegisterHelper.registerFermenterRecipe(Craft.ing("treeLeaves"), mash(10));
	}
	public static FluidStack mash(int amount) {
		return new FluidStack(ModFluids.Mash, amount);
	}
}
