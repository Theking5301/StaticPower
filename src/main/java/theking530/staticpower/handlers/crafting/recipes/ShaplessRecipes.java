package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.blocks.crops.ModPlants;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemComponents;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.MiscItems;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.itemfilter.ItemFilter;

public class ShaplessRecipes {

	public static void registerShapelessRecipes() {
		
		//Food --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_potatoFlour", "StaticPower", MiscItems.potatoFlour, new Ingredient[]{
			Ingredient.fromItem(Items.POTATO)});
		RegisterHelper.addShapelessRecipe("StaticPower_wheatFlour", "StaticPower", MiscItems.wheatFlour, new Ingredient[]{
				Ingredient.fromItem(Items.WHEAT)});
		RegisterHelper.addShapelessRecipe("StaticPower_applePie", "StaticPower", MiscItems.applePie, new Ingredient[]{
				Craft.ing(Items.APPLE), Craft.ing(Items.EGG), Craft.ing(Items.SUGAR), Craft.ing(MiscItems.wheatFlour)});
		
		//Static Book --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_staticBook", "StaticPower", new ItemStack(ModItems.StaticBook), new Ingredient[]{
				Ingredient.fromItem(ModPlants.StaticSeeds), Ingredient.fromItem(Items.BOOK)});

		//Infusion Blends
		RegisterHelper.addShapelessRecipe("StaticPower_energizedInfusionBlend", "StaticPower", ItemMaterials.dustStaticInfusion, new Ingredient[]{
				Craft.ing(ItemMaterials.dustInertInfusion), Craft.ing(ItemMaterials.dustStatic)});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedInfusionBlend", "StaticPower", ItemMaterials.dustEnergizedInfusion, new Ingredient[]{
				Craft.ing(ItemMaterials.dustInertInfusion), Craft.ing(ItemMaterials.dustEnergized)});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumInfusionBlend", "StaticPower", ItemMaterials.dustStaticInfusion, new Ingredient[]{
				Craft.ing(ItemMaterials.dustInertInfusion), Craft.ing(ItemMaterials.dustLumum)});
		
		//Wires ---------------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_copperWire", "StaticPower", Craft.outputStack(ItemComponents.wireCopper, 4), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.WireCutters, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotCopper")});
		RegisterHelper.addShapelessRecipe("StaticPower_silverWire", "StaticPower", Craft.outputStack(ItemComponents.wireSilver, 4), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.WireCutters, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotSilver")});
		RegisterHelper.addShapelessRecipe("StaticPower_goldWire", "StaticPower", Craft.outputStack(ItemComponents.wireGold, 4), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.WireCutters, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromItem(Items.GOLD_INGOT)});
		
		//Plates ---------------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_ironPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateIron, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromItem(Items.IRON_INGOT)});
		RegisterHelper.addShapelessRecipe("StaticPower_goldPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateGold, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), Ingredient.fromItem(Items.GOLD_INGOT)});
		RegisterHelper.addShapelessRecipe("StaticPower_copperPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateCopper, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotCopper")});
		RegisterHelper.addShapelessRecipe("StaticPower_tinPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateTin, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotTin")});
		RegisterHelper.addShapelessRecipe("StaticPower_silverPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateSilver, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotSilver")});
		RegisterHelper.addShapelessRecipe("StaticPower_leadPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateLead, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotLead")});
		RegisterHelper.addShapelessRecipe("StaticPower_platinumPlate", "StaticPower", Craft.outputStack(ItemMaterials.platePlatinum, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotPlatinum")});
		RegisterHelper.addShapelessRecipe("StaticPower_copperPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateAluminium, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotAluminium")});
		RegisterHelper.addShapelessRecipe("StaticPower_tinPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateNickel, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotNickel")});
		RegisterHelper.addShapelessRecipe("StaticPower_silverPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateInertInfusion, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotInertInfusion")});
		RegisterHelper.addShapelessRecipe("StaticPower_leadPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateRedstoneAlloy, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), new OreIngredient("ingotRedstoneAlloy")});
		
		RegisterHelper.addShapelessRecipe("StaticPower_staticPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateStatic, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), Craft.ing(ItemMaterials.ingotStatic)});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateEnergized, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), Craft.ing(ItemMaterials.ingotEnergized)});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumPlate", "StaticPower", Craft.outputStack(ItemMaterials.plateLumum, 1), new Ingredient[]{
				Ingredient.fromStacks(new ItemStack(ModItems.MetalHammer, 1, OreDictionary.WILDCARD_VALUE)), Craft.ing(ItemMaterials.ingotLumum)});
		
		//Metal BLocks --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_staticIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotStatic, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.StaticBlock))});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotEnergized, 9), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.EnergizedBlock))});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotLumum, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.LumumBlock))});
		RegisterHelper.addShapelessRecipe("StaticPower_copperIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotCopper, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockCopper))});
		RegisterHelper.addShapelessRecipe("StaticPower_tinIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotTin, 9), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockTin))});
		RegisterHelper.addShapelessRecipe("StaticPower_silverIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotSilver, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockSilver))});
		RegisterHelper.addShapelessRecipe("StaticPower_leadIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotLead, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockLead))});
		RegisterHelper.addShapelessRecipe("StaticPower_platinumIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotPlatinum, 9), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockPlatinum))});
		RegisterHelper.addShapelessRecipe("StaticPower_nickelIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotNickel, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockNickel))});
		RegisterHelper.addShapelessRecipe("StaticPower_aluminiumIngotFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.ingotAluminium, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockAluminium))});
		RegisterHelper.addShapelessRecipe("StaticPower_sapphireGemFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.gemSapphire, 9), new Ingredient[]{
				Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockSapphire))});
		RegisterHelper.addShapelessRecipe("StaticPower_rubyGemFromBlock", "StaticPower", Craft.outputStack(ItemMaterials.gemRuby, 9), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.BlockRuby))});
		
		//Seeds --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_staticSeeds", "StaticPower", new ItemStack(ModPlants.StaticSeeds), new Ingredient[]{
			Ingredient.fromItem(ModPlants.StaticCrop)});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedSeeds", "StaticPower", new ItemStack(ModPlants.EnergizedSeeds), new Ingredient[]{
			Ingredient.fromItem(ModPlants.EnergizedCrop)});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumSeeds", "StaticPower", new ItemStack(ModPlants.LumumSeeds), new Ingredient[]{
			Ingredient.fromItem(ModPlants.LumumCrop)});
		
		//Upgrade Plates ---------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_basicUpgradePlate", "StaticPower", Craft.outputStack(ItemComponents.basicUpgradePlate), new Ingredient[]{
				Craft.ing(ItemMaterials.plateIron), Craft.ing(ItemComponents.basicProcessor)});
		RegisterHelper.addShapelessRecipe("StaticPower_staticUpgradePlate", "StaticPower", Craft.outputStack(ItemComponents.staticUpgradePlate), new Ingredient[]{
				Craft.ing(ItemMaterials.plateStatic), Craft.ing(ItemComponents.staticProcessor)});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedUpgradePlate", "StaticPower", Craft.outputStack(ItemComponents.energizedUpgradePlate), new Ingredient[]{
				Craft.ing(ItemMaterials.plateEnergized), Craft.ing(ItemComponents.energizedProcessor)});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumUpgradePlate", "StaticPower", Craft.outputStack(ItemComponents.lumumUpgradePlate), new Ingredient[]{
				Craft.ing(ItemMaterials.plateLumum), Craft.ing(ItemComponents.lumumProcessor)});
		
		//Inert Infusion Blend --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_inertInfusionBlend", "StaticPower", Craft.outputStack(ItemMaterials.dustInertInfusion, 4), new Ingredient[]{
				new OreIngredient("dustTin"), new OreIngredient("dustPlatinum"), new OreIngredient("dustSilver"), new OreIngredient("dustSilver")});
		
		//Nuggets --------------------------------------------------------------------------------------------------

		RegisterHelper.addShapelessRecipe("StaticPower_staticNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetStatic, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotStatic)});
		RegisterHelper.addShapelessRecipe("StaticPower_energizedNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetEnergized, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotEnergized)});
		RegisterHelper.addShapelessRecipe("StaticPower_lumumNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetLumum, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotLumum)});
		
		RegisterHelper.addShapelessRecipe("StaticPower_copperNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetCopper, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotCopper)});
		RegisterHelper.addShapelessRecipe("StaticPower_tinNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetTin, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotTin)});
		RegisterHelper.addShapelessRecipe("StaticPower_silverNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetSilver, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotSilver)});
		RegisterHelper.addShapelessRecipe("StaticPower_leadNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetLead, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotLead)});
		RegisterHelper.addShapelessRecipe("StaticPower_nickelNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetNickel, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotNickel)});
		RegisterHelper.addShapelessRecipe("StaticPower_platinumNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetPlatinum, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotPlatinum)});
		RegisterHelper.addShapelessRecipe("StaticPower_aluminiumNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetAluminium, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotAluminium)});
		RegisterHelper.addShapelessRecipe("StaticPower_redstoneAlloyNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetRedstoneAlloy, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotRedstoneAlloy)});
		RegisterHelper.addShapelessRecipe("StaticPower_inertInfusionNugget", "StaticPower", Craft.outputStack(ItemMaterials.nuggetInertInfusion, 9), new Ingredient[]{
				Craft.ing(ItemMaterials.ingotInertInfusion)});
		
		//Static Planks --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_staticPlanks", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.StaticPlanks), 4), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.StaticWood))});
		
		//Energized Planks --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_energizedPlanks", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedPlanks), 4), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.EnergizedWood))});
		
		//Lumum Planks --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapelessRecipe("StaticPower_lumumPlanks", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.LumumPlanks), 4), new Ingredient[]{
			Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.LumumWood))});
		

		//Gunpowder
		RegisterHelper.addShapelessRecipe("StaticPower_gunpowder", "StaticPower", new ItemStack(Items.GUNPOWDER), new Ingredient[]{ 
			Craft.ing(ItemMaterials.dustSaltpeter), Craft.ing(ItemMaterials.dustSaltpeter), Craft.ing(ItemMaterials.dustCoal), Craft.ing(ItemMaterials.dustSulfur)});
		RegisterHelper.addShapelessRecipe("StaticPower_gunpowder", "StaticPower", new ItemStack(Items.GUNPOWDER), new Ingredient[]{ 
				Craft.ing(ItemMaterials.dustSaltpeter), Craft.ing(ItemMaterials.dustSaltpeter), Craft.ing(ItemMaterials.dustCharcoal), Craft.ing(ItemMaterials.dustSulfur)});
			
		//Filters --------------------------------------------------------------------------------------------------
		ItemStack quarryFilter = new ItemStack(ModItems.UpgradedItemFilter, 1);
		ItemFilter.writeQuarryFilter(quarryFilter);
		RegisterHelper.addShapelessRecipe("StaticPower_quarryFilter", "StaticPower", quarryFilter, new Ingredient[]{
				Ingredient.fromItem(ModItems.UpgradedItemFilter), Ingredient.fromItem(Item.getItemFromBlock(Blocks.COBBLESTONE))});
		
		ItemStack quarryFilter2 = new ItemStack(ModItems.UpgradedItemFilter, 1);
		ItemFilter.writeOreFilter(quarryFilter2);
		RegisterHelper.addShapelessRecipe("StaticPower_oreFilter", "StaticPower", quarryFilter2, new Ingredient[]{
				Ingredient.fromItem(ModItems.UpgradedItemFilter), Ingredient.fromItem(Item.getItemFromBlock(Blocks.IRON_ORE))});
		
		ItemStack quarryFilter3 = new ItemStack(ModItems.AdvancedItemFilter, 1);
		ItemFilter.writeAdvancedOreFilter(quarryFilter3);
		RegisterHelper.addShapelessRecipe("StaticPower_advancedOreFilter", "StaticPower", quarryFilter3, new Ingredient[]{
				Ingredient.fromItem(ModItems.AdvancedItemFilter), Ingredient.fromItem(Item.getItemFromBlock(Blocks.GOLD_ORE))});
	}
}
