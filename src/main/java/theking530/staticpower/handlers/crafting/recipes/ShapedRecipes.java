package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.items.ModItems;

public class ShapedRecipes {

		@SuppressWarnings("all")
		private static void registerShapedRecipes() {
			
			//Static Wrench --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.StaticWrench), new Object[]{" IC"," SI","S  ",
			'S', "ingotSilver", 'I', Items.IRON_INGOT, 'C', ModItems.StaticCrop}));
			
			//Soldering Iron --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.SolderingIron), new Object[]{"I  "," IL"," LR",
			'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'L', new ItemStack(Items.DYE, 4, 4)}));		
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.SolderingIron), new Object[]{"  I","LI ","RL ",
			'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'L', new ItemStack(Items.DYE, 4, 4)}));			
			
			//Metal Hammer 
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.MetalHammer), new Object[]{"III","ISI"," S ",
			'S', Items.STICK, 'I', Items.IRON_INGOT}));		
			
			//Fluid Conduit
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.FluidConduit), 8), new Object[]{" S ","SGS"," S ",
			'S', "ingotSilver", 'G', Blocks.GLASS}));
			
			//Item Conduit
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.ItemConduit), 8), new Object[]{" T ","TGT"," T ",
			'T', "ingotTin", 'G', Blocks.GLASS}));
			
			//Static Block --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.StaticBlock), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.StaticIngot});
			
			//Energized Block --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.EnergizedBlock), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.EnergizedIngot});
			
			//Static Block --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.LumumBlock), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.LumumIngot});
			
			//Ingots ------------------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.StaticIngot), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.StaticNugget});
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedIngot), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.EnergizedNugget});
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumIngot), new Object[]{"XXX","XXX","XXX",
			'X', ModItems.LumumNugget});
			
			//Coils ------------------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.CopperCoil), new Object[]{"XXX","XSX","XXX",
			'X', ModItems.CopperWire, 'S', Items.STICK});
			GameRegistry.addRecipe(new ItemStack(ModItems.SilverCoil), new Object[]{"XXX","XSX","XXX",
			'X', ModItems.SilverWire, 'S', Items.STICK});
			GameRegistry.addRecipe(new ItemStack(ModItems.GoldCoil), new Object[]{"XXX","XSX","XXX",
			'X', ModItems.GoldWire, 'S', Items.STICK});
			
			//Energy Crystals ---------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedEnergyCrystal), new Object[]{" B ","BDB"," B ",
			'B', ModItems.EnergizedInfusionBlend, 'D', Items.DIAMOND});
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumEnergyCrystal), new Object[]{" B ","BDB"," B ",
			'B', ModItems.LumumInfusionBlend, 'D', Items.DIAMOND});
			
			//Machine Block --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.MachineBlock), new Object[]{"TGT", "GCG", "TGT", 
			'G', Blocks.GLASS, 'C', ModItems.BasicCircuit, 'T', ModItems.TinPlate});
			
			//Obsidian Glass --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.ObsidianGlass, 4), new Object[]{"O O", " G ", "O O", 
			'G', Blocks.GLASS, 'O', Blocks.OBSIDIAN});
			
			//Soldering Table --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.SolderingTable), new Object[]{"III","S S","S S",
			'I', Items.IRON_INGOT, 'S', Blocks.STONE});
			
			//I/O Port --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.IOPort, new Object[]{" G ", "RLR", " G ", 
			'G', Blocks.GLASS, 'L', Blocks.LEVER, 'R', Items.REDSTONE}));	
			
			//Powered Grinder --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.PoweredGrinder), new Object[]{"FFF", "RBR", "III", 
			'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'B', ModBlocks.MachineBlock, 'F', Items.FLINT});
			
			//Advanced Earth --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.AdvancedEarth), new Object[]{"GGG","GDG","GGG",
			'G', ModItems.GoldPlate, 'D', Blocks.DIRT});
			
			//Powered Furnace --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.PoweredFurnace), new Object[]{"IUI", "RBR", "CCC", 
			'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'B', ModBlocks.MachineBlock, 'U', Items.BUCKET, 'C', ModItems.CopperIngot});
			
			//Quarry --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.Quarry), new Object[]{"PHP", "EBE", "ELE", 
			'P', Items.DIAMOND_PICKAXE, 'H', Blocks.HOPPER, 'B', ModBlocks.MachineBlock, 'E', ModItems.EnergizedCircuit, 'L', ModItems.LumumCircuit});
			
			//Fluid Infuser --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.FluidInfuser), new Object[]{" U ", "PBP", "RIR", 
			'I', ModItems.IOPort, 'R', Items.REDSTONE, 'B', ModBlocks.MachineBlock, 'U', Items.BUCKET, 'P', Blocks.PISTON});
			
			//Crop Squeezer --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.CropSqueezer), new Object[]{"FPF", "RBR", "IUI", 
			'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'P', Blocks.PISTON, 'B', ModBlocks.MachineBlock, 'U', Items.BUCKET, 'F', Items.FLINT});
			
			//Fusion Furnace --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.FusionFurnace), new Object[]{"FIF", "RBR", "CCC", 
			'F', ModBlocks.PoweredFurnace, 'R', Items.REDSTONE, 'B', ModBlocks.MachineBlock, 'C', ModItems.BasicCircuit, 'I', ModItems.IOPort});
			
			//Charging Station --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.ChargingStation), new Object[]{" H ", "RMR", "CBC", 
			'C', ModItems.BasicCircuit, 'H', ModItems.CopperPlate, 'R', ModItems.GoldPlate, 'M', ModBlocks.MachineBlock, 'B', ModItems.BasicBattery});
			
			//Basic Farmer --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.BasicFarmer), new Object[]{" H ", "RMR", "DCD", 
			'C', ModItems.BasicCircuit, 'H', Items.IRON_HOE, 'R', ModItems.IronPlate, 'M', ModBlocks.MachineBlock, 'D', Blocks.DIRT});
			
			//Fluid Generator  --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.FluidGenerator), new Object[]{" U ", "CBC", "VIV", 
			'V', "ingotCopper", 'C', ModItems.BasicCircuit, 'I', Items.GOLD_INGOT, 'B', ModBlocks.MachineBlock, 'U', Items.BUCKET, 'G', Blocks.GLASS}));
			
			//Batteries --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.StaticBattery), new Object[]{"SSS", "BMB", "BBB", 
			'S', ModItems.StaticIngot, 'B', ModItems.StaticBattery, 'M', ModBlocks.MachineBlock});			
			GameRegistry.addRecipe(new ItemStack(ModBlocks.EnergizedBattery), new Object[]{"SSS", "BMB", "BBB", 
			'S', ModItems.EnergizedIngot, 'B', ModItems.EnergizedBattery, 'M', ModBlocks.MachineBlock});		
			GameRegistry.addRecipe(new ItemStack(ModBlocks.LumumBattery), new Object[]{"SSS", "BMB", "BBB",  
			'S', ModItems.LumumIngot, 'B', ModItems.LumumBattery, 'M', ModBlocks.MachineBlock});		
			
			//Static Solar Panel --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.StaticSolarPanel), new Object[]{"   ", "EEE", "CIC", 
			'E', ModItems.StaticIngot, 'C', ModItems.BasicCircuit, 'I', ModItems.IOPort});
			
			//Static Chest --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.StaticChest), new Object[]{"SSS", "SCS", "SSS", 
			'S', ModItems.StaticIngot, 'C', Blocks.CHEST});		
			
			//Energized Chest --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.EnergizedChest), new Object[]{"EEE", "ECE", "EEE", 
			'E', ModItems.EnergizedIngot, 'C', ModBlocks.StaticChest});		
			
			//Lumum Chest --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModBlocks.LumumChest), new Object[]{"LLL", "LCL", "LLL", 
			'L', ModItems.LumumIngot, 'C', ModBlocks.EnergizedChest});		
			
			//Vacuum Chest
			GameRegistry.addRecipe(new ItemStack(ModBlocks.VacuumChest), new Object[]{"EHE", " C ", "IBI", 
			'H', Blocks.HOPPER, 'C', Blocks.CHEST, 'B', ModItems.StaticCircuit, 'E', Items.ENDER_PEARL, 'I', Items.IRON_INGOT});	
			
			//Static Armor --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.StaticHelmet), new Object[]{"EEE", "EBE", "   ",  'E', ModItems.StaticIngot, 'B', ModItems.StaticBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.StaticChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', ModItems.StaticIngot, 'B', ModItems.StaticBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.StaticLeggings), new Object[]{"EEE", "EBE", "E E",  'E', ModItems.StaticIngot, 'B', ModItems.StaticBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.StaticBoots), new Object[]{"   ", "EBE", "E E",  'E', ModItems.StaticIngot, 'B', ModItems.StaticBattery});		
			//Energized Armor --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedHelmet), new Object[]{"EEE", "EBE", "   ",  'E', ModItems.EnergizedIngot, 'B', ModItems.EnergizedBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', ModItems.EnergizedIngot, 'B', ModItems.EnergizedBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedLeggings), new Object[]{"EEE", "EBE", "E E",  'E', ModItems.EnergizedIngot, 'B', ModItems.EnergizedBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.EnergizedBoots), new Object[]{"   ", "EBE", "E E",  'E', ModItems.EnergizedIngot, 'B', ModItems.EnergizedBattery});		
			//Lumum Armor --------------------------------------------------------------------------------------------------
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumHelmet), new Object[]{"EEE", "EBE", "   ",  'E', ModItems.LumumIngot, 'B', ModItems.LumumBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', ModItems.LumumIngot, 'B', ModItems.LumumBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumLeggings), new Object[]{"EEE", "EBE", "E E",  'E', ModItems.LumumIngot, 'B', ModItems.LumumBattery});
			GameRegistry.addRecipe(new ItemStack(ModItems.LumumBoots), new Object[]{"   ", "EBE", "E E",  'E', ModItems.LumumIngot, 'B', ModItems.LumumBattery});		
		}
	
	public static void registerFullRecipes() {
		registerShapedRecipes();
	}
}
