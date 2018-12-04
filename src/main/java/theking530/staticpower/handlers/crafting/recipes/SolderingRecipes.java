package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemComponents;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.ModItems;

public class SolderingRecipes {

	@SuppressWarnings("static-access")
	public static void registerSolderingRecipes() {
		
		//Circuit Recipes
		RegisterHelper.registerSolderingRecipe(ItemComponents.basicProcessor, new Object[]{"WWW", "RIR", "WWW", 
		'I', Craft.ing("plateIron"), 'R', Items.REDSTONE, 'W', Craft.ing("wireCopper")});	
		RegisterHelper.registerSolderingRecipe(ItemComponents.staticProcessor, new Object[]{"WWW", "RSR", "WWW", 
		'R', Items.REDSTONE, 'S', ItemMaterials.plateStatic, 'W', Craft.ing("wireCopper")});		
		RegisterHelper.registerSolderingRecipe(ItemComponents.energizedProcessor, new Object[]{"WWW", "RER", "WWW", 
		'R', Items.REDSTONE, 'E', ItemMaterials.plateEnergized, 'W', Craft.ing("wireSilver")});		
		RegisterHelper.registerSolderingRecipe(ItemComponents.lumumProcessor, new Object[]{"WWW", "RLR", "WWW", 
		'R', Items.REDSTONE, 'L', ItemMaterials.plateLumum, 'W', Craft.ing("wireGold")});
		
		//Batteries --------------------------------------------------------------------------------------------------
		RegisterHelper.registerSolderingRecipe(ModItems.BasicBattery.battery, new Object[]{" C ", "IRI", "IRI", 
		'I', Craft.ing("plateIron"), 'R', Blocks.REDSTONE_BLOCK, 'C', Items.IRON_NUGGET});		
		RegisterHelper.registerSolderingRecipe(ModItems.StaticBattery.battery, new Object[]{" S ", "IDI", "IRI", 
		'I', ItemMaterials.plateStatic, 'R', Blocks.REDSTONE_BLOCK, 'S', ItemMaterials.nuggetStatic, 'D', ItemMaterials.crystalStatic});	
		RegisterHelper.registerSolderingRecipe(ModItems.EnergizedBattery.battery, new Object[]{" E ", "IDI", "IRI", 
		'I', ItemMaterials.plateEnergized, 'R', Blocks.REDSTONE_BLOCK, 'E', ItemMaterials.nuggetEnergized, 'D', ItemMaterials.crystalEnergized});	
		RegisterHelper.registerSolderingRecipe(ModItems.LumumBattery.battery, new Object[]{" L ", "IDI", "IRI", 
		'I', ItemMaterials.plateLumum, 'R', Blocks.REDSTONE_BLOCK, 'L', ItemMaterials.nuggetLumum, 'D', ItemMaterials.crystalLumum});	
		
		//Tools -------------------------------------------------------------------------------------------------------
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.ElectricSolderingIron), new Object[]{"I  "," IL"," LR",
		'R', Craft.ing(ModItems.BasicBattery.battery,  OreDictionary.WILDCARD_VALUE), 'I', Items.IRON_INGOT, 'L', new ItemStack(Items.DYE, 1, 4)});		
	
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.CoordinateMarker), new Object[]{"IIR","IGI","CBC",
		'B', Craft.ing(ModItems.BasicBattery.battery,  OreDictionary.WILDCARD_VALUE), 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'G', Craft.ing("blockGlass"), 'C', ItemComponents.basicProcessor});	
		
		//Upgrades --------------------------------------------------------------------------------------------------
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicTankUpgrade), new Object[]{" B ","LGL","I I",
		'B', Items.BUCKET, 'I', Craft.ing("plateTin"), 'L', new ItemStack(Items.DYE, 1, 4), 'G', ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicPowerUpgrade), new Object[]{"R R"," G ","I I",
		'I', Craft.ing("plateTin"), 'R', Items.REDSTONE, 'G',  ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicSpeedUpgrade), new Object[]{" P "," G ","I I",
		'I', Craft.ing("plateTin"), 'P', Items.GLOWSTONE_DUST, 'G',  ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicRangeUpgrade), new Object[]{"PPP"," G ","PPP",
		'P', Craft.ing("plankWood"), 'G',  ItemComponents.basicProcessor});
		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticTankUpgrade), new Object[]{" B ","LGL","I I",
		'B', Items.BUCKET, 'I', Craft.ing("plateTin"), 'L', new ItemStack(Items.DYE, 1, 4), 'G', ItemComponents.staticProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticPowerUpgrade), new Object[]{"R R"," G ","I I",
		'I', Craft.ing("plateTin"), 'R', Items.REDSTONE, 'G',  ItemComponents.staticProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticSpeedUpgrade), new Object[]{" P "," G ","I I",
		'I', Craft.ing("plateTin"), 'P', Items.GLOWSTONE_DUST, 'G',  ItemComponents.staticProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticQuarryingUpgrade), new Object[]{" P "," G ","P P",
		'P', Items.IRON_PICKAXE, 'G', ItemComponents.staticProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticRangeUpgrade), new Object[]{"PPP"," G ","PPP",
		'P', ItemMaterials.plateStatic, 'G',  ItemComponents.staticProcessor});
			
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedTankUpgrade), new Object[]{" B ","LGL","I I",
		'B', Items.BUCKET, 'I', Craft.ing("plateTin"), 'L', new ItemStack(Items.DYE, 1, 4), 'G', ItemComponents.energizedProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedPowerUpgrade), new Object[]{"R R"," G ","I I",
		'I', Craft.ing("plateTin"), 'R', Items.REDSTONE, 'G',  ItemComponents.energizedProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedSpeedUpgrade), new Object[]{" P "," G ","I I",
		'I', Craft.ing("plateTin"), 'P', Items.GLOWSTONE_DUST, 'G',  ItemComponents.energizedProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedQuarryingUpgrade), new Object[]{" P "," G ","P P",
		'P', Items.GOLDEN_PICKAXE, 'G', ItemComponents.energizedProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedRangeUpgrade), new Object[]{"PPP"," G ","PPP",
		'P', ItemMaterials.plateEnergized, 'G',  ItemComponents.energizedProcessor});
		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumTankUpgrade), new Object[]{" B ","LGL","I I",
		'B', Items.BUCKET, 'I', Craft.ing("plateTin"), 'L', Blocks.LAPIS_BLOCK, 'G', ItemComponents.lumumProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumPowerUpgrade), new Object[]{"R R"," G ","I I",
		'I', Craft.ing("plateTin"), 'R', Blocks.REDSTONE_BLOCK, 'G',  ItemComponents.lumumProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumSpeedUpgrade), new Object[]{" P "," G ","I I",
		'I', Craft.ing("plateTin"), 'P', Blocks.GLOWSTONE, 'G',  ItemComponents.lumumProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumQuarryingUpgrade), new Object[]{" P "," G ","P P",
		'P', Items.DIAMOND_PICKAXE, 'G', ItemComponents.lumumProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumRangeUpgrade), new Object[]{"PPP"," G ","PPP",
		'P', ItemMaterials.plateLumum, 'G',  ItemComponents.lumumProcessor});
		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.basicCapacityUpgrade, new Object[]{"SCS"," G ","SCS",
		'S', Items.STICK, 'C',  Craft.ing("chestWood"), 'G',  ItemComponents.basicProcessor});	
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.ironCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', Craft.ing("plateIron"), 'U', ModItems.DigistoreCapacityUpgrade.basicCapacityUpgrade});		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.goldCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', Craft.ing("plateGold"), 'U', ModItems.DigistoreCapacityUpgrade.ironCapacityUpgrade});	
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.leadCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', Craft.ing("plateLead"), 'U', ModItems.DigistoreCapacityUpgrade.basicCapacityUpgrade});	
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.obsidianCapacityUpgrade, new Object[]{"C C"," G ","C C",
		'C', Blocks.OBSIDIAN, 'G',  ItemComponents.basicProcessor});
		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.staticCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', ItemMaterials.plateStatic, 'U', ModItems.DigistoreCapacityUpgrade.goldCapacityUpgrade});		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.energizedCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', ItemMaterials.plateEnergized, 'U', ModItems.DigistoreCapacityUpgrade.staticCapacityUpgrade});		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreCapacityUpgrade.lumumCapacityUpgrade, new Object[]{" P ","UPU"," P ",
		'P', ItemMaterials.plateLumum, 'U', ModItems.DigistoreCapacityUpgrade.energizedCapacityUpgrade});
		
		RegisterHelper.registerSolderingRecipe(ModItems.DigistoreMiscUpgrade.VoidUprgade, new Object[]{"OOO","OBO","OOO",
		'O', Blocks.OBSIDIAN, 'B', ItemComponents.basicProcessor});
		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicOutputMultiplierUpgrade), new Object[]{" F ","FGF","F F",
		'F', Item.getItemFromBlock(Blocks.GRAVEL), 'G', ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticOutputMultiplierUpgrade), new Object[]{" F ","FGF","F F",
		'F', Items.FLINT,  'G', ItemComponents.staticProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedOutputMultiplierUpgrade), new Object[]{" F ","FGF","F F",
		'F', Craft.ing("itemSilicon"), 'G', ItemComponents.energizedProcessor});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumOutputMultiplierUpgrade), new Object[]{" F ","FGF","F F",
		'F', Item.getItemFromBlock(Blocks.PISTON), 'G', ItemComponents.lumumProcessor});
		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.TeleportUpgrade), new Object[]{"EHE"," P ","EHE",
		'H', Item.getItemFromBlock(Blocks.HOPPER), 'E', Items.ENDER_PEARL, 'P',  ItemComponents.energizedProcessor});
		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.ExperienceVacuumUpgrade), new Object[]{"CGC","GPG","CGC",
		'G', Craft.ing("blockGlass"), 'C', Craft.ing(ModItems.BaseFluidCapsule.capsule, OreDictionary.WILDCARD_VALUE), 'P',  ItemComponents.staticProcessor});
		
		//Filters --------------------------------------------------------------------------------------------------
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.BasicItemFilter), new Object[]{" P ","PGP"," C ",
		'P', Items.PAPER, 'C', ItemComponents.basicProcessor, 'G', Items.IRON_NUGGET});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.UpgradedItemFilter), new Object[]{" P ","PGP"," C ",
		'P', Items.PAPER, 'C', ItemComponents.staticProcessor, 'G', ItemMaterials.nuggetStatic});
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.AdvancedItemFilter), new Object[]{" P ","PGP"," C ",
		'P', Items.PAPER, 'C', ItemComponents.energizedProcessor, 'G', ItemMaterials.nuggetEnergized});
		
		//Gate Components
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModBlocks.LogicGateBasePlate, 2), new Object[]{"   ", "   ", "SBS", 
		'S', Craft.ing("plateIron"), 'B', ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.logicGateServo, 4), new Object[]{"S S", " C ", "QBQ", 
		'S', Craft.ing("plateIron"), 'B', Craft.ing(ModItems.BasicBattery.battery,  OreDictionary.WILDCARD_VALUE), 'C',  Craft.ing("coilCopper"), 'Q', Craft.ing("wireSilver")});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.logicGatePowerSync, 2), new Object[]{"   ", " R ", " S ", 
		'S', ItemComponents.basicProcessor, 'R', Blocks.REDSTONE_TORCH});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.invertedLogicGatePowerSync, 2), new Object[]{" Q ", " R ", " S ", 
		'S', ItemComponents.basicProcessor, 'R', Blocks.REDSTONE_TORCH, 'Q', Items.REDSTONE});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.diode, 8), new Object[]{"   ", "SRS", "QCQ", 
		'S', Craft.ing("plateIron"), 'Q', Craft.ing("wireSilver"), 'R', Item.getItemFromBlock(Blocks.UNPOWERED_REPEATER), 'C', ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.transistor, 8), new Object[]{" S ", "SRS", "QCQ", 
		'S', Craft.ing("plateIron"), 'Q', Craft.ing("wireSilver"), 'R', Item.getItemFromBlock(Blocks.LEVER), 'C', ItemComponents.basicProcessor});
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.internalClock, 2), new Object[]{" S ", "SRS", "QCQ", 
		'S', Craft.ing("plateIron"), 'Q', Craft.ing("wireSilver"), 'R', Items.QUARTZ, 'C', ItemComponents.basicProcessor});
		
		//Fluid Canisters
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.StaticFluidCapsule), new Object[]{"PPP", "CPC", "PPP", 
		'P', ItemMaterials.plateStatic, 'C', Craft.ing(ModItems.BaseFluidCapsule.capsule, OreDictionary.WILDCARD_VALUE)});		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.EnergizedFluidCapsule), new Object[]{"PPP", "CPC", "PPP", 
		'P', ItemMaterials.plateEnergized, 'C', Craft.ing(ModItems.StaticFluidCapsule.capsule, OreDictionary.WILDCARD_VALUE)});		
		RegisterHelper.registerSolderingRecipe(new ItemStack(ModItems.LumumFluidCapsule), new Object[]{"PPP", "CPC", "PPP", 
		'P', ItemMaterials.plateLumum, 'C', Craft.ing(ModItems.EnergizedFluidCapsule.capsule, OreDictionary.WILDCARD_VALUE)});	
		
		RegisterHelper.registerSolderingRecipe(Craft.outputStack(ItemComponents.memoryChip, 8), new Object[]{"PPP", "SSS", "QCQ", 
		'P', Craft.ing("plateIron"), 'Q', Craft.ing("wireSilver"), 'S', Craft.ing("itemSilicon"), 'C', ItemComponents.basicProcessor});
	}
}
