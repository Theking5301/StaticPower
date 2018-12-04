package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import theking530.staticpower.assists.MaterialSet;
import theking530.staticpower.assists.MaterialSets;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.blocks.crops.ModPlants;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemComponents;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.armor.ModArmor;

public class ShapedRecipes {

	@SuppressWarnings("all")
	private static void registerShapedRecipes() {		
		
		//Blocks ------------------------------------------------------------------------------------------------------------
		for(MaterialSet set : MaterialSets.MATERIALS) {
			if(set.getBlock() == null) { continue; }
			RegisterHelper.addShapedRecipe("StaticPower_" + set.getName() + "BlockFromIngot", "StaticPower", new ItemStack(Item.getItemFromBlock(set.getBlock())), new Object[]{"XXX","XXX","XXX",
					'X', Craft.ing(set.getIngot())});
		}	
		
		//Ingots ------------------------------------------------------------------------------------------------------------
		for(MaterialSet set : MaterialSets.MATERIALS) {
			if(set.getIngot() == null) { continue; }
			RegisterHelper.addShapedRecipe("StaticPower_" + set.getName() + "IngotFromNugget", "StaticPower", set.getIngot(), new Object[]{"XXX","XXX","XXX",
					'X', Craft.ing(set.getNugget())});
		}		
		
		//Gears ------------------------------------------------------------------------------------------------------------
		for(MaterialSet set : MaterialSets.MATERIALS) {
			if(set.getGear() == null) { continue; }
			RegisterHelper.addShapedRecipe("StaticPower_" + set.getName() + "GearFromIngot", "StaticPower", set.getGear(), new Object[]{" X ","XSX"," X ",
					'X', Craft.ing(set.getIngot()), 'S', ingredientFromItem(Items.STICK)});
		}	
		
		//Static Wrench --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_staticWrench", "StaticPower", new ItemStack(ModItems.StaticWrench), new Object[]{" IC"," SI","S  ",
		'S', ingredientOre("ingotSilver"), 'I', ingredientFromItem(Items.IRON_INGOT), 'C', ingredientFromItem(ModPlants.StaticCrop)});
		
		//Wire Cutter --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_wireCutter", "StaticPower", new ItemStack(ModItems.WireCutters), new Object[]{" I ","LLI"," L ",
		'L', Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 4)), 'I', ingredientFromItem(Items.IRON_INGOT)});
		
		//Soldering Iron --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_solderingIron1", "StaticPower", new ItemStack(ModItems.SolderingIron), new Object[]{"I  "," IL"," LR",
		'R', ingredientFromItem(Items.REDSTONE), 'I', ingredientFromItem(Items.IRON_INGOT), 'L', Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 4))});		
		RegisterHelper.addShapedRecipe("StaticPower_solderingIron2", "StaticPower", new ItemStack(ModItems.SolderingIron), new Object[]{"  I","LI ","RL ",
		'R', ingredientFromItem(Items.REDSTONE), 'I', ingredientFromItem(Items.IRON_INGOT), 'L', Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 4))});			
		
		//Coordinate Marker --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_coordinateMarker", "StaticPower", new ItemStack(ModItems.CoordinateMarker), new Object[]{"IGI","ICI","IOI",
		'C', ItemComponents.basicProcessor, 'I', ingredientFromItem(Items.IRON_INGOT), 'G', ingredientFromBlock(Blocks.GLASS), 'O', ingredientOre("ingotCopper")});	
		
		//Metal Hammer 
		RegisterHelper.addShapedRecipe("StaticPower_metalHammer", "StaticPower", new ItemStack(ModItems.MetalHammer), new Object[]{"ITI","ISI"," S ",
		'S', ingredientFromItem(Items.STICK), 'I', ingredientFromItem(Items.IRON_INGOT), 'T', ingredientOre("ingotTin")});		
		
		//Fluid Conduit
		RegisterHelper.addShapedRecipe("StaticPower_fluidConduit", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.FluidConduit), 8), new Object[]{" S ","SGS"," S ",
		'S', ingredientOre("ingotSilver"), 'G', ingredientFromBlock(Blocks.GLASS)});
				
		//Item Conduit
		RegisterHelper.addShapedRecipe("StaticPower_itemConduit", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.ItemConduit), 8), new Object[]{" T ","TGT"," T ",
		'T', ingredientOre("ingotTin"), 'G', ingredientFromBlock(Blocks.GLASS)});
		
		//Item Conduit
		RegisterHelper.addShapedRecipe("StaticPower_staticConduit", "StaticPower", new ItemStack(Item.getItemFromBlock(ModBlocks.StaticConduit), 8), new Object[]{" S ","SGS"," S ",
		'S', ingredientOre("ingotGold"), 'G', ingredientFromBlock(Blocks.GLASS)});
		
		RegisterHelper.addShapedRecipe("StaticPower_sapphireBlock", "StaticPower", new ItemStack(ModBlocks.BlockSapphire), new Object[]{"XXX","XXX","XXX",
		'X', Craft.ing(ItemMaterials.gemSapphire)});
		RegisterHelper.addShapedRecipe("StaticPower_rubyBlock", "StaticPower", new ItemStack(ModBlocks.BlockRuby), new Object[]{"XXX","XXX","XXX",
		'X', Craft.ing(ItemMaterials.gemRuby)});

		//Coils ------------------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_copperCoil", "StaticPower", ItemComponents.coilCopper, new Object[]{"XXX","XSX","XXX",
		'X', Craft.ing("wireCopper"), 'S', ingredientFromItem(Items.STICK)});
		RegisterHelper.addShapedRecipe("StaticPower_silverCoil", "StaticPower", ItemComponents.coilSilver, new Object[]{"XXX","XSX","XXX",
		'X',Craft.ing("wireSilver"), 'S', ingredientFromItem(Items.STICK)});
		RegisterHelper.addShapedRecipe("StaticPower_goldCoil", "StaticPower", ItemComponents.coilGold, new Object[]{"XXX","XSX","XXX",
		'X', Craft.ing("wireGold"), 'S', ingredientFromItem(Items.STICK)});
		
		//Energy Crystals ---------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_staticEnergyCrystal", "StaticPower", ItemMaterials.crystalStatic, new Object[]{" B ","BDB"," B ",
		'B', ItemMaterials.dustStaticInfusion, 'D', ingredientFromItem(Items.DIAMOND)});
		RegisterHelper.addShapedRecipe("StaticPower_energizedEnergyCrystal", "StaticPower", ItemMaterials.crystalEnergized, new Object[]{" B ","BDB"," B ",
		'B', ItemMaterials.dustEnergizedInfusion, 'D', ingredientFromItem(Items.DIAMOND)});
		RegisterHelper.addShapedRecipe("StaticPower_lumumEnergyCrystal", "StaticPower", ItemMaterials.crystalLumum, new Object[]{" B ","BDB"," B ",
		'B', ItemMaterials.dustLumumInfusion, 'D', ingredientFromItem(Items.DIAMOND)});
		
		//Machine Block --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_machineBlock", "StaticPower", new ItemStack(ModBlocks.MachineBlock), new Object[]{"TGT", "GCG", "TGT", 
		'G', ingredientFromBlock((Blocks.GLASS)), 'C', ItemComponents.basicProcessor, 'T', ingredientOre("plateTin")});
		
		//Heating Element --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_heatingElement", "StaticPower", new ItemStack(ModBlocks.HeatingElement), new Object[]{"WCW", "W W", "WCW", 
		'C', Craft.ing("plateCopper"), 'W', ItemComponents.coilCopper});
		
		//Fermenter --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_fermenter", "StaticPower", new ItemStack(ModBlocks.Fermenter), new Object[]{"IPI", "GMG", "IPI", 
		'G', ingredientFromBlock((Blocks.GLASS)) , 'I', Craft.ing("plateIron"), 'P', ingredientFromBlock(Blocks.PISTON), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});
		
		//Distillery --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_distillery", "StaticPower", new ItemStack(ModBlocks.Distillery), new Object[]{"GGG", "BMB", "CCC", 
		'G', ingredientFromBlock((Blocks.GLASS)) , 'C', Craft.ing("plateCopper"), 'B', ingredientFromItem(Items.BUCKET), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});
		
		//Condenser --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_condenser", "StaticPower", new ItemStack(ModBlocks.Condenser), new Object[]{"CCC", "BMB", "GGG", 
		'G', ingredientFromBlock((Blocks.GLASS)) , 'C', Craft.ing("plateCopper"), 'B', ingredientFromItem(Items.BUCKET), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});
		
		//Obsidian Glass --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_obsidianGlass", "StaticPower", new ItemStack(ModBlocks.ObsidianGlass, 4), new Object[]{"O O", " G ", "O O", 
		'G', ingredientFromBlock((Blocks.GLASS)), 'O', ingredientFromBlock(Blocks.OBSIDIAN)});
		
		//Soldering Table --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_solderingTable", "StaticPower", new ItemStack(ModBlocks.SolderingTable), new Object[]{"III","S S","S S",
		'I', ingredientFromItem(Items.IRON_INGOT), 'S', ingredientFromBlock(Blocks.STONE)});
		
		//I/O Port --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_iOPort", "StaticPower", Craft.outputStack(ItemComponents.ioPort, 2), new Object[]{" L ", "LGL", " L ", 
		'G', ingredientFromBlock((Blocks.GLASS)), 'L', ingredientFromBlock((Blocks.LEVER))});	
		
		//Powered Grinder --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_poweredGrinder", "StaticPower", new ItemStack(ModBlocks.PoweredGrinder), new Object[]{"FFF", "RBR", "COC", 
		'C', ItemComponents.basicProcessor, 'R', ingredientFromBlock(Blocks.PISTON), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'F', ingredientFromItem(Items.FLINT), 'O', ItemComponents.ioPort});
		
		//Advanced Earth --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_advancedEarth", "StaticPower", new ItemStack(ModBlocks.AdvancedEarth), new Object[]{" E ","EDE"," E ",
		'E', Craft.ing(ItemMaterials.plateEnergized), 'D', ingredientFromBlock(Blocks.DIRT)});
		
		//Powered Furnace --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_poweredFurnace", "StaticPower", new ItemStack(ModBlocks.PoweredFurnace), new Object[]{"IUI", "RBR", "COC", 
		'I', ingredientFromItem(Items.IRON_INGOT), 'R', Craft.ing("coilCopper"), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'U', ingredientFromItem(Items.BUCKET), 'C', ItemComponents.basicProcessor, 'O', ItemComponents.ioPort});
		
		//Quarry --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_quarry", "StaticPower", new ItemStack(ModBlocks.Quarry), new Object[]{"PHP", "EBE", "ELE", 
		'P', ingredientFromItem(Items.DIAMOND_PICKAXE), 'H', ingredientFromBlock(Blocks.HOPPER), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'E', ItemComponents.energizedProcessor, 'L', ItemComponents.lumumProcessor});
		
		//Fluid Infuser --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_fluidInfuser", "StaticPower", new ItemStack(ModBlocks.FluidInfuser), new Object[]{" U ", "PBP", "RIR", 
		'I', ItemComponents.ioPort, 'R', Craft.ing("coilSilver"), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'U', ingredientFromItem(Items.BUCKET), 'P', ingredientFromBlock(Blocks.PISTON)});
		
		//Crop Squeezer --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_cropSqueezer", "StaticPower", new ItemStack(ModBlocks.CropSqueezer), new Object[]{"FPF", "RBR", "IUI", 
		'I', ingredientFromItem(Items.IRON_INGOT), 'R', Craft.ing("coilSilver"), 'P', ingredientFromBlock(Blocks.PISTON), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'U', ingredientFromItem(Items.BUCKET), 'F', ingredientFromItem(Items.FLINT)});
		
		//Mechanical Squeezer --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_mechanicalSqueezer", "StaticPower", new ItemStack(ModBlocks.MechanicalSqueezer), new Object[]{"FFF", "PBP", "SSS", 
		'P', ingredientFromBlock(Blocks.PISTON), 'B', ingredientFromItem(Items.BUCKET), 'F', ingredientFromItem(Items.FLINT), 'S', ingredientFromBlock(Blocks.STONE)});
	
		//Fusion Furnace --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_fusionFurnace", "StaticPower", new ItemStack(ModBlocks.FusionFurnace), new Object[]{"FIF", "RBR", "CHC", 
		'F', ingredientFromBlock(ModBlocks.PoweredFurnace), 'R', Craft.ing("plateCopper"), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'C', ItemComponents.basicProcessor, 'I', ItemComponents.ioPort, 'H', ingredientFromBlock(ModBlocks.HeatingElement)});
		
		//Charging Station --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_chargingStation", "StaticPower", new ItemStack(ModBlocks.ChargingStation), new Object[]{" H ", "RMR", "CBC", 
		'C', ItemComponents.basicProcessor, 'H', Craft.ing("plateCopper"), 'R', Craft.ing("plateGold"), 'M', ingredientFromBlock(ModBlocks.MachineBlock), 'B', Craft.ing(ModItems.BasicBattery.battery, OreDictionary.WILDCARD_VALUE)});
		
		//Basic Farmer --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_basicFarmer", "StaticPower", new ItemStack(ModBlocks.BasicFarmer), new Object[]{" H ", "RMR", "DCD", 
		'C', ItemComponents.basicProcessor, 'H', ingredientFromItem(Items.IRON_HOE), 'R', ItemComponents.ioPort, 'M', ingredientFromBlock(ModBlocks.MachineBlock), 'D', ingredientFromBlock(Blocks.DIRT)});
		
		//Tree Farmer --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_treeFarmer", "StaticPower", new ItemStack(ModBlocks.TreeFarmer), new Object[]{" H ", "RMR", "DCD", 
		'C', ItemComponents.basicProcessor, 'H', ingredientFromItem(Items.IRON_AXE), 'R', ItemComponents.ioPort, 'M', ingredientFromBlock(ModBlocks.MachineBlock), 'D', ingredientOre("treeSapling")});
		
		//Fluid Generator  --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_fluidGenerator", "StaticPower", new ItemStack(ModBlocks.FluidGenerator), new Object[]{" U ", "CBC", "VIV", 
		'V', ItemComponents.ioPort, 'C', ItemComponents.basicProcessor, 'I', Craft.ing("coilGold"), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'U', ingredientFromItem(Items.BUCKET)});

		//Former  --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_former", "StaticPower", new ItemStack(ModBlocks.Former), new Object[]{" L ", "PBP", "IAI", 
		'P', ingredientFromBlock(Blocks.PISTON), 'L', ingredientOre("plateIron"), 'I', ingredientFromItem(Items.GOLD_INGOT), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'A', ingredientFromBlock(Blocks.IRON_BLOCK), 'I', ItemComponents.ioPort});
		
		//Enchanter  --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_enchanter", "StaticPower", new ItemStack(ModBlocks.EsotericEnchanter), new Object[]{" E ", "UBU", "ICI", 
		'E', ingredientFromBlock(Blocks.ENCHANTING_TABLE), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'U', ingredientFromItem(Items.BUCKET), 'I', ItemComponents.ioPort, 'C', ItemComponents.staticProcessor});
		
		//Fusion Furnace --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_lumberMill", "StaticPower", new ItemStack(ModBlocks.LumberMill), new Object[]{"FAF", "IBI", "TCT", 
		'F', Craft.ing("gearIron"), 'A', Craft.ing(Items.IRON_AXE), 'I', Craft.ing(ItemComponents.ioPort), 'B', ingredientFromBlock(ModBlocks.MachineBlock), 'C', ItemComponents.basicProcessor, 'T', Craft.ing("ingotLead")});
		
	
		//Batteries --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_staticBattery", "StaticPower", new ItemStack(ModBlocks.StaticBattery), new Object[]{"SSS", "BMB", "BBB", 
		'S', Craft.ing(ItemMaterials.plateStatic), 'B', Craft.ing(ModItems.StaticBattery.battery, OreDictionary.WILDCARD_VALUE), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});			
		RegisterHelper.addShapedRecipe("StaticPower_energizedBattery", "StaticPower", new ItemStack(ModBlocks.EnergizedBattery), new Object[]{"SSS", "BMB", "BBB", 
		'S', Craft.ing(ItemMaterials.plateEnergized), 'B', Craft.ing(ModItems.EnergizedBattery.battery, OreDictionary.WILDCARD_VALUE), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});		
		RegisterHelper.addShapedRecipe("StaticPower_lumumBattery", "StaticPower", new ItemStack(ModBlocks.LumumBattery), new Object[]{"SSS", "BMB", "BBB",  
		'S', Craft.ing(ItemMaterials.plateLumum), 'B', Craft.ing(ModItems.LumumBattery.battery, OreDictionary.WILDCARD_VALUE), 'M', ingredientFromBlock(ModBlocks.MachineBlock)});		
		
		//Digistore
		RegisterHelper.addShapedRecipe("StaticPower_digistore", "StaticPower", new ItemStack(ModBlocks.Digistore), new Object[]{"TTT","TCT","TMT",
		'M', ItemComponents.memoryChip, 'T', ingredientOre("plateTin"), 'C', ingredientOre("chestWood")});
		
		//Digistore Manager
		RegisterHelper.addShapedRecipe("StaticPower_digistoreManager", "StaticPower", new ItemStack(ModBlocks.DigistoreManager), new Object[]{"TTT","IDI","TMT",
		'M', ItemComponents.staticProcessor, 'T', ingredientOre("plateTin"), 'D', ingredientFromBlock(ModBlocks.Digistore), 'I', ItemComponents.ioPort});
		
		//Digistore Extender
		RegisterHelper.addShapedRecipe("StaticPower_digistoreNetworkExtender", "StaticPower", new ItemStack(ModBlocks.DigistoreNetworkExtender, 8), new Object[]{"TTT", "I I","TTT",
		'T', ingredientOre("plateTin"), 'I', ItemComponents.ioPort});
		
		//Digistore IOPort
		RegisterHelper.addShapedRecipe("StaticPower_digistoreIOPort", "StaticPower", new ItemStack(ModBlocks.DigistoreIOPort), new Object[]{"TIT", "IDI","TIT",
		'T', ingredientOre("plateTin"), 'I', ItemComponents.ioPort, 'D', ingredientFromBlock(ModBlocks.Digistore)});
		
		//Static Solar Panel --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_basicSolarPanel", "StaticPower", new ItemStack(ModBlocks.BasicSolarPanel), new Object[]{"   ", "LLL", "CIC", 
		'L', ingredientFromBlock(Blocks.LAPIS_BLOCK), 'C', ItemComponents.basicProcessor, 'I', ItemComponents.ioPort});
		
		//Gates --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_timer", "StaticPower", new ItemStack(ModBlocks.Timer), new Object[]{"   ", "RSV", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate), 'S', ItemComponents.logicGateServo, 'R', ItemComponents.logicGatePowerSync, 'V', ItemComponents.invertedLogicGatePowerSync});
		RegisterHelper.addShapedRecipe("StaticPower_signalMultiplier", "StaticPower", new ItemStack(ModBlocks.SignalMultiplier), new Object[]{"   ", "RSV", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate), 'S', ingredientFromItem(Items.COMPARATOR), 'R', ItemComponents.logicGatePowerSync, 'V', ItemComponents.invertedLogicGatePowerSync});
		RegisterHelper.addShapedRecipe("StaticPower_notGate", "StaticPower", new ItemStack(ModBlocks.NotGate), new Object[]{"   ", "R V", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate),'R', ItemComponents.logicGatePowerSync, 'V', ItemComponents.invertedLogicGatePowerSync});
		RegisterHelper.addShapedRecipe("StaticPower_powerCell", "StaticPower", new ItemStack(ModBlocks.PowerCell), new Object[]{"   ", "RQR", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate),'R', ItemComponents.logicGatePowerSync, 'Q', ingredientFromBlock(Blocks.REDSTONE_BLOCK)});
		RegisterHelper.addShapedRecipe("StaticPower_adder", "StaticPower", new ItemStack(ModBlocks.Adder), new Object[]{" T ", "RQR", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate),'R', ItemComponents.logicGatePowerSync, 'T', ItemComponents.transistor,'Q', ItemComponents.memoryChip});
		RegisterHelper.addShapedRecipe("StaticPower_andGate", "StaticPower", new ItemStack(ModBlocks.And), new Object[]{"   ", "RQR", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate),'R', ItemComponents.logicGatePowerSync, 'Q', ItemComponents.memoryChip});
		RegisterHelper.addShapedRecipe("StaticPower_orGate", "StaticPower", new ItemStack(ModBlocks.Or), new Object[]{"   ", "RQV", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate),'R', ItemComponents.logicGatePowerSync, 'V', ItemComponents.invertedLogicGatePowerSync, 'Q', ItemComponents.memoryChip});
		RegisterHelper.addShapedRecipe("StaticPower_subtractor", "StaticPower", new ItemStack(ModBlocks.Subtractor), new Object[]{" T ", "VQV", "PPP", 
		'P', ingredientFromBlock(ModBlocks.LogicGateBasePlate), 'V', ItemComponents.invertedLogicGatePowerSync, 'T', ItemComponents.transistor, 'Q', ItemComponents.memoryChip});
		RegisterHelper.addShapedRecipe("StaticPower_LED", "StaticPower", new ItemStack(ModBlocks.LED, 4), new Object[]{"GGG", "GDG", "SCS", 
		'G', ingredientFromBlock(Blocks.GLASS), 'D', ItemComponents.diode, 'S', Craft.ing("wireSilver"), 'C', ItemComponents.basicProcessor});
		
		//Static Chest --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_staticChest", "StaticPower", new ItemStack(ModBlocks.StaticChest), new Object[]{"SSS", "SCS", "SSS", 
		'S', Craft.ing(ItemMaterials.ingotStatic), 'C', ingredientFromBlock(Blocks.CHEST)});		
		
		//Fluid Capsules --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_baseFluidCapsule", "StaticPower", ModItems.BaseFluidCapsule.capsule, new Object[]{"PGP", "PGP", "PGP", 
		'P', Craft.ing("plateIron"), 'G', ingredientFromBlock(Blocks.GLASS_PANE)});			

		//Energized Chest --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_energizedChest", "StaticPower", new ItemStack(ModBlocks.EnergizedChest), new Object[]{"EEE", "ECE", "EEE", 
		'E', Craft.ing(ItemMaterials.ingotEnergized), 'C',  ingredientFromBlock(ModBlocks.StaticChest)});		
		
		//Lumum Chest --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_lumumChest", "StaticPower", new ItemStack(ModBlocks.LumumChest), new Object[]{"LLL", "LCL", "LLL", 
		'L', Craft.ing(ItemMaterials.ingotLumum), 'C',  ingredientFromBlock(ModBlocks.EnergizedChest)});		
		
		//Vacuum Chest
		RegisterHelper.addShapedRecipe("StaticPower_vacuumChest", "StaticPower", new ItemStack(ModBlocks.VacuumChest), new Object[]{"EHE", " C ", "IBI", 
		'H',  ingredientFromBlock(Blocks.HOPPER), 'C',  ingredientFromBlock(Blocks.CHEST), 'B', ItemComponents.staticProcessor, 'E', ingredientFromItem(Items.ENDER_PEARL), 'I', ingredientFromItem(Items.IRON_INGOT)});	
		
		//Static Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_staticHelmet", "StaticPower", new ItemStack(ModArmor.StaticHelmet), new Object[]{"EEE", "EBE", "   ",  'E', Craft.ing(ItemMaterials.ingotStatic), 'B', Craft.ing(ModItems.StaticBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_staticChestplate", "StaticPower", new ItemStack(ModArmor.StaticChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotStatic), 'B', Craft.ing(ModItems.StaticBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_staticLeggings", "StaticPower", new ItemStack(ModArmor.StaticLeggings), new Object[]{"EEE", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotStatic), 'B', Craft.ing(ModItems.StaticBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_staticBoots", "StaticPower", new ItemStack(ModArmor.StaticBoots), new Object[]{"   ", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotStatic), 'B', Craft.ing(ModItems.StaticBattery.battery, OreDictionary.WILDCARD_VALUE)});		
		//Energized Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_energizedHelmet", "StaticPower", new ItemStack(ModArmor.EnergizedHelmet), new Object[]{"EEE", "EBE", "   ",  'E', Craft.ing(ItemMaterials.ingotEnergized), 'B', Craft.ing(ModItems.EnergizedBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_energizedChestplate", "StaticPower", new ItemStack(ModArmor.EnergizedChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotEnergized), 'B', Craft.ing(ModItems.EnergizedBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_energizedLeggings", "StaticPower", new ItemStack(ModArmor.EnergizedLeggings), new Object[]{"EEE", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotEnergized), 'B', Craft.ing(ModItems.EnergizedBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_energizedBoots", "StaticPower", new ItemStack(ModArmor.EnergizedBoots), new Object[]{"   ", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotEnergized), 'B', Craft.ing(ModItems.EnergizedBattery.battery, OreDictionary.WILDCARD_VALUE)});		
		//Lumum Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_lumumHelmet", "StaticPower", new ItemStack(ModArmor.LumumHelmet), new Object[]{"EEE", "EBE", "   ",  'E', Craft.ing(ItemMaterials.ingotLumum), 'B', Craft.ing(ModItems.LumumBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_lumumChestplate", "StaticPower", new ItemStack(ModArmor.LumumChestplate), new Object[]{"EBE", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotLumum), 'B', Craft.ing(ModItems.LumumBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_lumumLeggings", "StaticPower", new ItemStack(ModArmor.LumumLeggings), new Object[]{"EEE", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotLumum), 'B', Craft.ing(ModItems.LumumBattery.battery, OreDictionary.WILDCARD_VALUE)});
		RegisterHelper.addShapedRecipe("StaticPower_lumumBoots", "StaticPower", new ItemStack(ModArmor.LumumBoots), new Object[]{"   ", "EBE", "E E",  'E', Craft.ing(ItemMaterials.ingotLumum), 'B', Craft.ing(ModItems.LumumBattery.battery, OreDictionary.WILDCARD_VALUE)});		
		
		//Copper Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_copperHelmet", "StaticPower", new ItemStack(ModArmor.CopperHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotCopper)});
		RegisterHelper.addShapedRecipe("StaticPower_copperChestplate", "StaticPower", new ItemStack(ModArmor.CopperChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotCopper)});
		RegisterHelper.addShapedRecipe("StaticPower_copperLeggings", "StaticPower", new ItemStack(ModArmor.CopperLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotCopper)});
		RegisterHelper.addShapedRecipe("StaticPower_copperBoots", "StaticPower", new ItemStack(ModArmor.CopperBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotCopper)});		
		//Tin Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_tinHelmet", "StaticPower", new ItemStack(ModArmor.TinHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotTin)});
		RegisterHelper.addShapedRecipe("StaticPower_tinChestplate", "StaticPower", new ItemStack(ModArmor.TinChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotTin)});
		RegisterHelper.addShapedRecipe("StaticPower_tinLeggings", "StaticPower", new ItemStack(ModArmor.TinLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotTin)});
		RegisterHelper.addShapedRecipe("StaticPower_tinBoots", "StaticPower", new ItemStack(ModArmor.TinBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotTin)});		
		//Lead Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_leadHelmet", "StaticPower", new ItemStack(ModArmor.LeadHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotLead)});
		RegisterHelper.addShapedRecipe("StaticPower_leadChestplate", "StaticPower", new ItemStack(ModArmor.LeadChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotLead)});
		RegisterHelper.addShapedRecipe("StaticPower_leadLeggings", "StaticPower", new ItemStack(ModArmor.LeadLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotLead)});
		RegisterHelper.addShapedRecipe("StaticPower_leadBoots", "StaticPower", new ItemStack(ModArmor.LeadBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotLead)});		
		//Silver Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_silverHelmet", "StaticPower", new ItemStack(ModArmor.SilverHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotSilver)});
		RegisterHelper.addShapedRecipe("StaticPower_silverChestplate", "StaticPower", new ItemStack(ModArmor.SilverChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotSilver)});
		RegisterHelper.addShapedRecipe("StaticPower_silverLeggings", "StaticPower", new ItemStack(ModArmor.SilverLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotSilver)});
		RegisterHelper.addShapedRecipe("StaticPower_silverBoots", "StaticPower", new ItemStack(ModArmor.SilverBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotSilver)});		
		//Platinum Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_platinumHelmet", "StaticPower", new ItemStack(ModArmor.PlatinumHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotPlatinum)});
		RegisterHelper.addShapedRecipe("StaticPower_platinumChestplate", "StaticPower", new ItemStack(ModArmor.PlatinumChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotPlatinum)});
		RegisterHelper.addShapedRecipe("StaticPower_platinumLeggings", "StaticPower", new ItemStack(ModArmor.PlatinumLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotPlatinum)});
		RegisterHelper.addShapedRecipe("StaticPower_platinumBoots", "StaticPower", new ItemStack(ModArmor.PlatinumBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotPlatinum)});		
		//Aluminium Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_aluminiumHelmet", "StaticPower", new ItemStack(ModArmor.AluminiumHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.ingotAluminium)});
		RegisterHelper.addShapedRecipe("StaticPower_aluminiumChestplate", "StaticPower", new ItemStack(ModArmor.AluminiumChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.ingotAluminium)});
		RegisterHelper.addShapedRecipe("StaticPower_aluminiumLeggings", "StaticPower", new ItemStack(ModArmor.AluminiumLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotAluminium)});
		RegisterHelper.addShapedRecipe("StaticPower_aluminiumBoots", "StaticPower", new ItemStack(ModArmor.AluminiumBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.ingotAluminium)});		
		//Sapphire Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_sapphireHelmet", "StaticPower", new ItemStack(ModArmor.SapphireHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.gemSapphire)});
		RegisterHelper.addShapedRecipe("StaticPower_sapphireChestplate", "StaticPower", new ItemStack(ModArmor.SapphireChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.gemSapphire)});
		RegisterHelper.addShapedRecipe("StaticPower_sapphireLeggings", "StaticPower", new ItemStack(ModArmor.SapphireLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.gemSapphire)});
		RegisterHelper.addShapedRecipe("StaticPower_sapphireBoots", "StaticPower", new ItemStack(ModArmor.SapphireBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.gemSapphire)});		
		//Ruby Armor --------------------------------------------------------------------------------------------------
		RegisterHelper.addShapedRecipe("StaticPower_rubyHelmet", "StaticPower", new ItemStack(ModArmor.RubyHelmet), new Object[]{"EEE", "E E", "   ",  'E', Craft.ing(ItemMaterials.gemRuby)});
		RegisterHelper.addShapedRecipe("StaticPower_rubyChestplate", "StaticPower", new ItemStack(ModArmor.RubyChestplate), new Object[]{"E E", "EEE", "EEE",  'E', Craft.ing(ItemMaterials.gemRuby)});
		RegisterHelper.addShapedRecipe("StaticPower_rubyLeggings", "StaticPower", new ItemStack(ModArmor.RubyLeggings), new Object[]{"EEE", "E E", "E E",  'E', Craft.ing(ItemMaterials.gemRuby)});
		RegisterHelper.addShapedRecipe("StaticPower_rubyBoots", "StaticPower", new ItemStack(ModArmor.RubyBoots), new Object[]{"   ", "E E", "E E",  'E', Craft.ing(ItemMaterials.gemRuby)});		
	}
	public static Ingredient ingredientFromBlock(Block block) {
		return ingredientFromItem(Item.getItemFromBlock(block));
	}
	public static Ingredient ingredientOre(String ore) {
		return new OreIngredient(ore);
	}
	public static Ingredient ingredientFromItem(Item item) {
		return Ingredient.fromItem(item);
	}
	public static void registerFullRecipes() {
		registerShapedRecipes();
	}
}
