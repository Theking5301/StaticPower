package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.handlers.crafting.wrappers.GrinderOutputWrapper.GrinderOutput;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.MiscItems;

public class GrinderRecipes {

	public static void registerGrinderRecipes() {
		registerIngotToDustRecipes();
		registerOreToDustRecipes();
		
		//Overrides
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.WHEAT),  new GrinderOutput(Craft.outputStack(MiscItems.wheatFlour, 2)),  new GrinderOutput(Craft.outputStack(MiscItems.wheatFlour), 0.15f));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.POTATO),  new GrinderOutput(Craft.outputStack(MiscItems.potatoFlour, 2)),  new GrinderOutput(Craft.outputStack(MiscItems.potatoFlour), 0.15f));	

		RegisterHelper.registerGrinderRecipe(Craft.ing("oreCopper"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustCopper, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustGold, 1), 0.05f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreSilver"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustSilver, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustTin, 1), 0.05f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreTin"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustTin, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustSilver, 1), 0.05f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreLead"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustLead, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.gemRuby, 1), 0.02f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("orePlatinum"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustPlatinum, 2)), new GrinderOutput(new ItemStack(Items.DIAMOND, 1), 0.01f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreNickel"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustNickel, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustPlatinum, 1), 0.01f));	
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreAluminium"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustAluminium, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.gemSapphire, 1), 0.02f));	
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreAluminum"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustAluminium, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.gemSapphire, 1), 0.02f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreIron"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustIron, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustNickel, 1), 0.05f));			
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreGold"), new GrinderOutput(Craft.outputStack(ItemMaterials.dustGold, 2)), new GrinderOutput(Craft.outputStack(ItemMaterials.dustCopper, 1), 0.05f));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreRuby"), new GrinderOutput(Craft.outputStack(ItemMaterials.gemRuby, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing("oreSapphire"), new GrinderOutput(Craft.outputStack(ItemMaterials.gemSapphire, 3)));

		RegisterHelper.registerGrinderRecipe(Craft.ing(ItemMaterials.ingotStatic),  new GrinderOutput(Craft.outputStack(ItemMaterials.dustStatic, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(ItemMaterials.ingotEnergized),  new GrinderOutput(Craft.outputStack(ItemMaterials.dustEnergized, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(ItemMaterials.ingotLumum),  new GrinderOutput(Craft.outputStack(ItemMaterials.dustLumum, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(ItemMaterials.ingotInertInfusion),  new GrinderOutput(Craft.outputStack(ItemMaterials.dustInertInfusion, 1)));		
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.IRON_INGOT), new GrinderOutput(Craft.outputStack(ItemMaterials.dustIron, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.GOLD_INGOT), new GrinderOutput(Craft.outputStack(ItemMaterials.dustGold, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(ItemMaterials.ingotRedstoneAlloy), new GrinderOutput(Craft.outputStack(ItemMaterials.dustRedstoneAlloy, 1)));
		
		RegisterHelper.registerGrinderRecipe(Craft.ing("gemRuby"), new GrinderOutput(ItemMaterials.dustRuby));
		RegisterHelper.registerGrinderRecipe(Craft.ing("gemSapphire"), new GrinderOutput(ItemMaterials.dustSapphire));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Items.COAL, 1, 0)), new GrinderOutput(ItemMaterials.dustCoal));	
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Items.COAL, 1, 1)), new GrinderOutput(ItemMaterials.dustCharcoal));	
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.OBSIDIAN), new GrinderOutput(Craft.outputStack(ItemMaterials.dustObsidian, 8), 1.0f));	
		
		
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.COAL_ORE),  new GrinderOutput(new ItemStack(Items.COAL, 4)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.REDSTONE_ORE),  new GrinderOutput(new ItemStack(Items.REDSTONE, 5)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.DIAMOND_ORE),  new GrinderOutput(new ItemStack(Items.DIAMOND, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.EMERALD_ORE),  new GrinderOutput(new ItemStack(Items.EMERALD, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.QUARTZ_ORE),  new GrinderOutput(new ItemStack(Items.QUARTZ, 4)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.LAPIS_ORE),  new GrinderOutput(new ItemStack(Items.DYE, 4, 4)));
		

		for (int meta = 1; meta < 16; meta++) {
			RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.WOOL, 1, meta)), new GrinderOutput(new ItemStack(Items.STRING, 4)), new GrinderOutput(new ItemStack(Blocks.WOOL), 0.01f), new GrinderOutput(new ItemStack(Items.DYE, 1, 15 - meta), 0.5f));
		}
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.WOOL, 0)), new GrinderOutput(new ItemStack(Items.STRING, 4)), new GrinderOutput(new ItemStack(Blocks.WOOL), 0.01f));
		
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.YELLOW_FLOWER)), new GrinderOutput(new ItemStack(Items.DYE, 4, 11)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 0)), new GrinderOutput(new ItemStack(Items.DYE, 4, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 1)), new GrinderOutput(new ItemStack(Items.DYE, 4, 12)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 2)), new GrinderOutput(new ItemStack(Items.DYE, 4, 13)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 3)), new GrinderOutput(new ItemStack(Items.DYE, 4, 7)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 4)), new GrinderOutput(new ItemStack(Items.DYE, 4, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 5)), new GrinderOutput(new ItemStack(Items.DYE, 4, 14)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 6)), new GrinderOutput(new ItemStack(Items.DYE, 4, 7)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 7)), new GrinderOutput(new ItemStack(Items.DYE, 4, 9)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_FLOWER, 1, 8)), new GrinderOutput(new ItemStack(Items.DYE, 4, 7)));

		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0)), new GrinderOutput(new ItemStack(Items.DYE, 4, 11)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1)), new GrinderOutput(new ItemStack(Items.DYE, 4, 13)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4)), new GrinderOutput(new ItemStack(Items.DYE, 4, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5)), new GrinderOutput(new ItemStack(Items.DYE, 4, 9)));
		
		//Items
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Items.BLAZE_ROD)), new GrinderOutput(new ItemStack(Items.BLAZE_POWDER, 4)), new GrinderOutput(ItemMaterials.dustSulfur, 0.1f));
		
		oreDictionaryRecipe("oreSulfur", "dustSulfur", 4);
		oreDictionaryRecipe("oreSaltpeter", "dustSaltpeter", 4);
		oreDictionaryRecipe("orePotash", "dustPotash", 4);
		oreDictionaryRecipe("oreBitumen", "dustBitumen", 4);
		oreDictionaryRecipe("oreSalt", "dustSalt", 4);
		oreDictionaryRecipe(Craft.ing("oreQuartz"), new GrinderOutput(new ItemStack(Items.QUARTZ, 4)));
		oreDictionaryRecipe(Craft.ing("oreCertusQuartz"), new GrinderOutput("crystalCertusQuartz", 2), new GrinderOutput("dustCertusQuartz", 1, 0.25f));
		
		oreDictionaryRecipe("oreAquamarine", "gemAquamarine", 6);
		oreDictionaryRecipe("oreAmethyst", "gemAmethyst", 3);
		oreDictionaryRecipe("oreTopaz", "gemTopaz", 3);
		oreDictionaryRecipe("orePeridot", "gemPeridot", 3);
		oreDictionaryRecipe("oreTanzanite", "gemTanzanite", 3);
		oreDictionaryRecipe("oreMalachite", "gemMalachite", 3);
		oreDictionaryRecipe("oreAmber", "gemAmber", 3);
		oreDictionaryRecipe("oreProsperity", "shardProsperity", 4);
		oreDictionaryRecipe("oreNetherProsperity", "shardProsperity", 4);
		oreDictionaryRecipe("oreEndProsperity", "shardProsperity", 4);
		oreDictionaryRecipe("oreInferium", "dustInferium", 4);
		oreDictionaryRecipe("oreNetherInferium", "dustInferium", 4);
		oreDictionaryRecipe("oreEndInferium", "dustInferium", 4);
		
		oreDictionaryRecipe(Craft.ing("oreClathrateOilSand"), new GrinderOutput("clathrateOil", 3), new GrinderOutput(ItemMaterials.dustSaltpeter, 0.5f));
		oreDictionaryRecipe(Craft.ing("oreClathrateOilShale"), new GrinderOutput("clathrateOil", 3), new GrinderOutput(new ItemStack(Items.FLINT), 0.5f));
		oreDictionaryRecipe(Craft.ing("oreClathrateEnder"), new GrinderOutput("clathrateEnder", 3));
		oreDictionaryRecipe(Craft.ing("oreClathrateRedstone"), new GrinderOutput("clathrateRedstone", 3), new GrinderOutput("crystalCinnabar", 0.5f));
		oreDictionaryRecipe(Craft.ing("oreClathrateGlowstone"), new GrinderOutput("clathrateGlowstone", 3), new GrinderOutput("crystalCinnabar", 0.3f));
		
		oreDictionaryRecipe(Craft.ing("oreApatite"), new GrinderOutput("gemApatite", 14), new GrinderOutput(ItemMaterials.dustSaltpeter, 0.1f), new GrinderOutput(ItemMaterials.dustSulfur, 0.1f));
		
		oreDictionaryRecipe("oreManganese", "dustManganese", 2);
		oreDictionaryRecipe("oreMithril", "dustMithril", 2);
		oreDictionaryRecipe("oreZinc", "dustZinc", 2);
		oreDictionaryRecipe("oreAdamantine", "dustAdamantine", 2);
		oreDictionaryRecipe("oreAlduorite", "dustAlduorite", 2);
		oreDictionaryRecipe("oreAstralSilver", "dustAstralSilver", 2);
		oreDictionaryRecipe("oreAtlarus", "dustAtlarus", 2);
		oreDictionaryRecipe("oreCarmot", "dustCarmot", 2);
		oreDictionaryRecipe("oreCeruclase", "dustCeruclase", 2);
		oreDictionaryRecipe("oreDeepIron", "dustDeepIron", 2);
		oreDictionaryRecipe("oreEximite", "dustEximite", 2);
		oreDictionaryRecipe("oreIgnatius", "dustIgnatius", 2);
		oreDictionaryRecipe("oreInfuscolium", "dustInfuscolium", 2);
		oreDictionaryRecipe("oreKalendrite", "dustKalendrite", 2);
		oreDictionaryRecipe("oreLemurite", "dustLemurite", 2);
		oreDictionaryRecipe("oreMagnesium", "dustMagnesium", 2);
		oreDictionaryRecipe("oreMeutoite", "dustMeutoite", 2);
		oreDictionaryRecipe("oreMidasium", "dustMidasium", 2);
		oreDictionaryRecipe("oreOrichalcum", "dustOrichalcum", 2);
		oreDictionaryRecipe("oreOureclase", "dustOureclase", 2);
		oreDictionaryRecipe("orePrometheum", "dustPrometheum", 2);
		oreDictionaryRecipe("oreRubracium", "dustRubracium", 2);
		oreDictionaryRecipe("oreSanguinite", "dustSanguinite", 2);
		oreDictionaryRecipe("oreShadowIron", "dustShadowIron", 2);
		oreDictionaryRecipe("oreVulcanite", "dustVulcanite", 2);
		oreDictionaryRecipe("oreVyroxeres", "dustVyroxeres", 2);
		
		oreDictionaryRecipe("oreYellorium", "dustUranium", 2);
		oreDictionaryRecipe("oreCobalt", "dustCobalt", 2);
		oreDictionaryRecipe("oreArdite", "dustArdite", 2);
		oreDictionaryRecipe("oreTitanium", "dustTitanium", 2);
		oreDictionaryRecipe("oreQuartzBlack", "dustQuartzBlack", 2);
		oreDictionaryRecipe("oreAstralStarmetal", "dustAstralStarmetal", 2);
		oreDictionaryRecipe("oreDraconium", "dustDraconium", 2);
		
		oreDictionaryRecipe("gemPeridot", "dustPeridot", 1);

		//Utils
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.GRAVEL), new GrinderOutput(new ItemStack(Items.FLINT, 1)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.COBBLESTONE), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.SAND))), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.GRAVEL)), 0.25f));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.COBBLESTONE_WALL), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.SAND))), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.GRAVEL)), 0.25f));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.GLASS), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.SAND))));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.STONE), new GrinderOutput(new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE))));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.STAINED_HARDENED_CLAY), new GrinderOutput(new ItemStack(Items.CLAY_BALL, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.HARDENED_CLAY), new GrinderOutput(new ItemStack(Items.CLAY_BALL, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.BRICK_BLOCK), new GrinderOutput(new ItemStack(Items.BRICK, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.SAND),  new GrinderOutput(Craft.outputStack(ItemMaterials.silicon)),  new GrinderOutput(ItemMaterials.silicon, 0.1f));
		
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.BONE), new GrinderOutput(new ItemStack(Items.DYE, 6, 15)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.PRISMARINE_SHARD), new GrinderOutput(new ItemStack(Items.PRISMARINE_CRYSTALS)));
		
		/*Thanks to CoFH team for pointers on good items to add here! :) */
		
		for (int i = 0; i < 15; i++) {
			RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STAINED_GLASS, 1, i)), new GrinderOutput(new ItemStack(Blocks.SAND)), new GrinderOutput(new ItemStack(Items.DYE, 1, 15 - i), 0.1f));
		}
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.REDSTONE_LAMP), new GrinderOutput(new ItemStack(Items.REDSTONE, 4)), new GrinderOutput(new ItemStack(Items.GLOWSTONE_DUST, 2), 0.5f));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.BRICK_BLOCK), new GrinderOutput(new ItemStack(Items.BRICK, 4)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.NETHER_BRICK), new GrinderOutput(new ItemStack(Items.NETHERBRICK, 4)));

		for (int i = 0; i < 3; i++) {
			RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.QUARTZ_BLOCK, 1, i)), new GrinderOutput(new ItemStack(Items.QUARTZ, 4)));
			RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.SANDSTONE, 1, i)), new GrinderOutput(new ItemStack(Blocks.SAND, 4)));
			RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.RED_SANDSTONE, 1, i)), new GrinderOutput(new ItemStack(Blocks.SAND, 4, 1)));
		}

		/* STAIRS */
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STONE_SLAB, 1, 4)), new GrinderOutput(new ItemStack(Items.BRICK, 2)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STONE_SLAB, 1, 6)), new GrinderOutput(new ItemStack(Items.NETHERBRICK, 2)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STONE_SLAB, 1, 7)), new GrinderOutput(new ItemStack(Items.QUARTZ, 2)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STONE_SLAB, 1, 1)), new GrinderOutput(new ItemStack(Blocks.SAND)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(new ItemStack(Blocks.STONE_SLAB2, 1, 0)), new GrinderOutput(new ItemStack(Blocks.SAND, 1, 1)));
		
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.BRICK_STAIRS), new GrinderOutput(new ItemStack(Items.BRICK, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.NETHER_BRICK_STAIRS), new GrinderOutput(new ItemStack(Items.NETHERBRICK, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.QUARTZ_STAIRS), new GrinderOutput(new ItemStack(Items.QUARTZ, 3)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.SANDSTONE_STAIRS), new GrinderOutput(new ItemStack(Blocks.SAND, 2)));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.RED_SANDSTONE_STAIRS), new GrinderOutput(new ItemStack(Blocks.SAND, 2, 1)));

		/* MISC */
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.FLOWER_POT), new GrinderOutput(new ItemStack(Items.BRICK, 2)), new GrinderOutput(new ItemStack(Items.BRICK, 1), 0.5f));
		RegisterHelper.registerGrinderRecipe(Craft.ing(Items.GLASS_BOTTLE), new GrinderOutput(new ItemStack(Blocks.SAND)));		
		RegisterHelper.registerGrinderRecipe(Craft.ing(Blocks.STONEBRICK), new GrinderOutput(new ItemStack(Blocks.STONEBRICK, 1, 2)));
	}
	private static void registerIngotToDustRecipes() {
		for (String ore : OreDictionary.getOreNames()) {
			if(ore.startsWith("ingot")) {
				String output = "dust"+ore.substring(5);
				if(OreDictionary.doesOreNameExist(output)) {
					oreDictionaryRecipe(ore, output, 1);
				}
			}
		}
	}
	private static void registerOreToDustRecipes() {
		for(String ore : OreDictionary.getOreNames()) {
			if(ore.startsWith("ore")) {
				String output = "dust"+ore.substring(3);
				if(OreDictionary.doesOreNameExist(output)) {
					oreDictionaryRecipe(ore, output, 2);
				}
			}
		}
	}
	private static void oreDictionaryRecipe(String input, String output, int number) {
		if(OreDictionary.doesOreNameExist(input) && OreDictionary.getOres(output).size() > 0) {
			RegisterHelper.registerGrinderRecipe(Craft.ing(input), new GrinderOutput(ItemHandlerHelper.copyStackWithSize(OreDictionary.getOres(output).get(0).copy(), number)));	
		}
	}
	private static void oreDictionaryRecipe(Ingredient input, GrinderOutput... outputs) {
		if(input != null) {
			RegisterHelper.registerGrinderRecipe(input, outputs);
		}
	}
}
