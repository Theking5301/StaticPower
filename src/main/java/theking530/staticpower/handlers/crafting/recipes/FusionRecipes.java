package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemMaterials;

public class FusionRecipes {
	
	public static void registerFusionRecipes() {
		RegisterHelper.registerFusionRecipe(new ItemStack(Items.SADDLE), Craft.ing(Items.LEATHER), Craft.ing(Items.LEATHER), Craft.ing(Items.LEATHER), Craft.ing("ingotIron"), Craft.ing("ingotIron"));
		RegisterHelper.registerFusionRecipe(new ItemStack(Items.CAKE), Craft.ing(Items.EGG), Craft.ing(Items.SUGAR), Craft.ing(Items.WHEAT), Craft.ing(Items.WHEAT), Craft.ing(Items.MILK_BUCKET));
		RegisterHelper.registerFusionRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.ObsidianGlass), 8), Craft.ing("dustObsidian"), Craft.ing("dustObsidian"),  Craft.ing("gemQuartz"), Craft.ing("blockGlass"), Craft.ing("blockGlass"));
		
		RegisterHelper.registerFusionRecipe(Craft.outputStack(ItemMaterials.ingotRedstoneAlloy, 1), Craft.ing("ingotSilver"), Craft.ing(Items.REDSTONE));
		
		registerOreRecipe("ingotElectrum", 2, Craft.ing("ingotGold"), Craft.ing("ingotSilver"));
		registerOreRecipe("ingotInvar", 3, Craft.ing("ingotIron"), Craft.ing("ingotIron"), Craft.ing("ingotNickel"));
		registerOreRecipe("ingotConstantan", 2, Craft.ing("ingotCopper"), Craft.ing("ingotNickel"));
	}
	public static void registerOreRecipe(String output, int outputCount, Ingredient... inputs) {
		if(OreDictionary.getOres(output).size() > 0) {
			ItemStack stack = OreDictionary.getOres(output).get(0).copy();
			stack.setCount(outputCount);
			RegisterHelper.registerFusionRecipe(stack, inputs);
		}
	}
}
