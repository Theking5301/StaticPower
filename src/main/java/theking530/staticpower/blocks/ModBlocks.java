package theking530.staticpower.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import theking530.staticpower.Registry;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.blocks.decorative.Lamp;
import theking530.staticpower.blocks.decorative.ObsidianGlass;
import theking530.staticpower.conduits.fluidconduit.BlockFluidConduit;
import theking530.staticpower.conduits.itemconduit.BlockItemConduit;
import theking530.staticpower.conduits.staticconduit.BlockStaticConduit;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.logic.gates.BlockLogicGateBasePlate;
import theking530.staticpower.logic.gates.adder.BlockAdder;
import theking530.staticpower.logic.gates.and.BlockAndGate;
import theking530.staticpower.logic.gates.led.BlockLED;
import theking530.staticpower.logic.gates.notgate.BlockNotGate;
import theking530.staticpower.logic.gates.or.BlockOrGate;
import theking530.staticpower.logic.gates.powercell.BlockPowerCell;
import theking530.staticpower.logic.gates.subtractor.BlockSubtractorGate;
import theking530.staticpower.logic.gates.timer.BlockTimer;
import theking530.staticpower.logic.gates.transducer.BlockSignalMultiplier;
import theking530.staticpower.machines.basicfarmer.BlockBasicFarmer;
import theking530.staticpower.machines.batteries.BlockBattery;
import theking530.staticpower.machines.centrifuge.BlockCentrifuge;
import theking530.staticpower.machines.chargingstation.BlockChargingStation;
import theking530.staticpower.machines.condenser.BlockCondenser;
import theking530.staticpower.machines.cropsqueezer.BlockCropSqueezer;
import theking530.staticpower.machines.distillery.BlockDistillery;
import theking530.staticpower.machines.esotericenchanter.BlockEsotericEnchanter;
import theking530.staticpower.machines.fermenter.BlockFermenter;
import theking530.staticpower.machines.fluidgenerator.BlockFluidGenerator;
import theking530.staticpower.machines.fluidinfuser.BlockFluidInfuser;
import theking530.staticpower.machines.former.BlockFormer;
import theking530.staticpower.machines.fusionfurnace.BlockFusionFurnace;
import theking530.staticpower.machines.heatingelement.BlockHeatingElement;
import theking530.staticpower.machines.lumbermill.BlockLumberMill;
import theking530.staticpower.machines.mechanicalsqueezer.BlockMechanicalSqueezer;
import theking530.staticpower.machines.poweredfurnace.BlockPoweredFurnace;
import theking530.staticpower.machines.poweredgrinder.BlockPoweredGrinder;
import theking530.staticpower.machines.quarry.BlockQuarry;
import theking530.staticpower.machines.refinery.BlockFluidRefineryCasing;
import theking530.staticpower.machines.refinery.controller.BlockFluidRefineryController;
import theking530.staticpower.machines.refinery.fluidinterface.BlockRefineryFluidInterface;
import theking530.staticpower.machines.refinery.mixer.BlockRefineryMixer;
import theking530.staticpower.machines.refinery.reactor.BlockRefineryReactor;
import theking530.staticpower.machines.refinery.vent.BlockRefineryVent;
import theking530.staticpower.machines.treefarmer.BlockTreeFarmer;
import theking530.staticpower.tileentity.astralquary.brain.BlockAstralQuarryBrain;
import theking530.staticpower.tileentity.chest.energizedchest.BlockEnergizedChest;
import theking530.staticpower.tileentity.chest.lumumchest.BlockLumumChest;
import theking530.staticpower.tileentity.chest.staticchest.BlockStaticChest;
import theking530.staticpower.tileentity.chunkloader.BlockChunkLoader;
import theking530.staticpower.tileentity.digistorenetwork.digistore.BlockDigistore;
import theking530.staticpower.tileentity.digistorenetwork.ioport.BlockDigistoreIOPort;
import theking530.staticpower.tileentity.digistorenetwork.manager.BlockDigistoreManager;
import theking530.staticpower.tileentity.digistorenetwork.networkwire.BlockDigistoreNetworkWire;
import theking530.staticpower.tileentity.solarpanels.BlockSolarPanel;
import theking530.staticpower.tileentity.solderingtable.BlockSolderingTable;
import theking530.staticpower.tileentity.vacuumchest.BlockVacuumChest;

public class ModBlocks {

	public static Block BaseConduitBlock;
	public static Block ChunkLoader;
	
	//Blocks
	public static Block EnergizedBlock;
	public static Block StaticBlock;
	public static Block LumumBlock;
	public static Block BlockCopper;
	public static Block BlockTin;
	public static Block BlockSilver;
	public static Block BlockLead;
	public static Block BlockPlatinum;
	public static Block BlockNickel;
	public static Block BlockAluminium;
	public static Block BlockSapphire;
	public static Block BlockRuby;
	
	public static Block StaticLamp;
	public static Block EnergizedLamp;
	public static Block LumumLamp;
	public static Block StaticGrass;
	public static Block EnergizedGrass;
	public static Block MachineBlock;
	public static Block ObsidianGlass;
	public static Block LaserFence;
	public static Block LaserLines;
	public static Block AdvancedEarth;
	public static Block ControlPanel;
	public static Block FusionFurnace;
	
	//Machines
	public static Block FluidGenerator;	
	public static Block FluidInfuser;	
	public static Block CropSqueezer;	
	public static Block PoweredGrinder;
	public static Block PoweredFurnace;	
	public static Block ChargingStation;	
	public static Block BasicFarmer;	
	public static Block BasicTank;
	public static Block AdvancedTank;	
	public static Block Farmer;
	public static Block MechanicalSqueezer;
	public static Block Fermenter;
	public static Block HeatingElement;
	public static Block Distillery;
	public static Block Condenser;
	public static Block Former;
	public static Block EsotericEnchanter;
	public static Block TreeFarmer;
	public static Block Centrifuge;
	public static Block LumberMill;
	
	//Astral Quary
	public static Block AstralQuary;
	
	//Fluid Refinery
	public static Block FluidRefineryController;
	public static Block FluidRefineryCasing;
	public static Block FluidRefineryFluidInterface;
	public static Block FluidRefineryVent;
	public static Block FluidRefineryMixer;
	public static Block FluidRefineryReactor;
	
	//Gates
	public static Block LogicGateBasePlate;
	public static Block SignalMultiplier;
	public static Block NotGate;
	public static Block PowerCell;
	public static Block Timer;
	public static Block Adder;
	public static Block And;
	public static Block Or;
	public static Block Subtractor;
	public static Block LED;
	
	//Solar Panels
	public static Block BasicSolarPanel;
	public static Block StaticSolarPanel;
	public static Block EnergizedSolarPanel;
	public static Block LumumSolarPanel;
	public static Block CreativeSolarPanel;
	
	//Batteries
	public static Block BasicBattery;
	public static Block StaticBattery;
	public static Block EnergizedBattery;
	public static Block LumumBattery;
	
	//Conduits
	public static Block StaticConduit;
	public static Block FluidConduit;
	public static Block ItemConduit;
	
	//Ores
	public static Block SilverOre;
	public static Block TinOre;
	public static Block LeadOre;
	public static Block CopperOre;
	public static Block PlatinumOre;
	public static Block NickelOre;
	public static Block AluminiumOre;
	public static Block RubyOre;
	public static Block SapphireOre;
	
	//Misc
	public static Block Quarry;
	public static Block StaticChest;
	public static Block EnergizedChest;
	public static Block LumumChest;
	public static Block VacuumChest;
	public static Block SolderingTable;

	public static Block Digistore;
	public static Block DigistoreManager;
	public static Block DigistoreNetworkExtender;
	public static Block DigistoreIOPort;
	
	//Decorative
	public static Block CrackedEnergizedBrick;	
	public static Block StaticWood;
	public static Block EnergizedWood;
	public static Block LumumWood;	
	public static Block StaticPlanks;
	public static Block EnergizedPlanks;
	public static Block LumumPlanks;	
	public static Block StaticCobblestone;
	public static Block EnergizedCobblestone;
	public static Block LumumCobblestone;
	
	public static void init(Registry registry) {
		
		ChunkLoader = new BlockChunkLoader("ChunkLoader");
		registry.PreRegisterBlock(ChunkLoader);		
		HeatingElement = new BlockHeatingElement();
		registry.PreRegisterBlock(HeatingElement);
			
		StaticWood = new InfusedWood("StaticWood");
		registry.PreRegisterBlock(StaticWood);		
		EnergizedWood = new InfusedWood("EnergizedWood");
		registry.PreRegisterBlock(EnergizedWood);
		LumumWood = new InfusedWood("LumumWood");
		registry.PreRegisterBlock(LumumWood);
		
		StaticPlanks = new BaseBlock(Material.WOOD, "StaticPlanks");
		registry.PreRegisterBlock(StaticPlanks);
		EnergizedPlanks = new BaseBlock(Material.WOOD, "EnergizedPlanks");
		registry.PreRegisterBlock(EnergizedPlanks);		
		LumumPlanks = new BaseBlock(Material.WOOD, "LumumPlanks");
		registry.PreRegisterBlock(LumumPlanks);

		StaticCobblestone = new BaseBlock(Material.ROCK, "StaticCobblestone");
		registry.PreRegisterBlock(StaticCobblestone);		
		EnergizedCobblestone = new BaseBlock(Material.ROCK, "EnergizedCobblestone");
		registry.PreRegisterBlock(EnergizedCobblestone);		
		LumumCobblestone = new BaseBlock(Material.ROCK, "LumumCobblestone");
		registry.PreRegisterBlock(LumumCobblestone);

		StaticChest = new BlockStaticChest("StaticChest").setUnlocalizedName("StaticChest");
		registry.PreRegisterBlock(StaticChest);		
		EnergizedChest = new BlockEnergizedChest("EnergizedChest").setUnlocalizedName("EnergizedChest");
		registry.PreRegisterBlock(EnergizedChest);		
		LumumChest = new BlockLumumChest("LumumChest").setUnlocalizedName("LumumChest");
		registry.PreRegisterBlock(LumumChest);		
		VacuumChest = new BlockVacuumChest("VacuumChest").setUnlocalizedName("VacuumChest");
		registry.PreRegisterBlock(VacuumChest);
		
		Digistore = new BlockDigistore("Digistore").setUnlocalizedName("Digistore");
		registry.PreRegisterBlock(Digistore);
		
		DigistoreManager = new BlockDigistoreManager("DigistoreManager").setUnlocalizedName("DigistoreManager");
		registry.PreRegisterBlock(DigistoreManager);
			
		DigistoreNetworkExtender = new BlockDigistoreNetworkWire("DigistoreNetworkExtender").setUnlocalizedName("DigistoreNetworkExtender");
		registry.PreRegisterBlock(DigistoreNetworkExtender);
		
		DigistoreIOPort = new BlockDigistoreIOPort("DigistoreIOPort").setUnlocalizedName("DigistoreIOPort");
		registry.PreRegisterBlock(DigistoreIOPort);
		
		BasicBattery = new BlockBattery("BasicBattery", Tier.BASIC);
		registry.PreRegisterBlock(BasicBattery);
		StaticBattery = new BlockBattery("StaticBattery", Tier.STATIC);
		registry.PreRegisterBlock(StaticBattery);	
		LumumBattery = new BlockBattery("LumumBattery", Tier.LUMUM);
		registry.PreRegisterBlock(LumumBattery);			
		EnergizedBattery = new BlockBattery("EnergizedBattery", Tier.ENERGIZED);
		registry.PreRegisterBlock(EnergizedBattery);
		
		FluidInfuser = new BlockFluidInfuser().setUnlocalizedName("FluidInfuser");
		registry.PreRegisterBlock(FluidInfuser);		
		FusionFurnace = new BlockFusionFurnace();
		registry.PreRegisterBlock(FusionFurnace);		
		ChargingStation = new BlockChargingStation();
		registry.PreRegisterBlock(ChargingStation);		
		Quarry = new BlockQuarry();
		registry.PreRegisterBlock(Quarry);		
		Distillery = new BlockDistillery().setUnlocalizedName("Distillery");
		registry.PreRegisterBlock(Distillery);		
		Condenser = new BlockCondenser().setUnlocalizedName("Condenser");
		registry.PreRegisterBlock(Condenser);		
		FluidGenerator = new BlockFluidGenerator().setUnlocalizedName("FluidGenerator");
		registry.PreRegisterBlock(FluidGenerator);		
		BasicFarmer = new BlockBasicFarmer().setUnlocalizedName("BasicFarmer");
		registry.PreRegisterBlock(BasicFarmer);		
		TreeFarmer = new BlockTreeFarmer().setUnlocalizedName("TreeFarmer");
		registry.PreRegisterBlock(TreeFarmer);	
		CropSqueezer = new BlockCropSqueezer().setUnlocalizedName("CropSqueezer");
		registry.PreRegisterBlock(CropSqueezer);
		MechanicalSqueezer = new BlockMechanicalSqueezer().setUnlocalizedName("MechanicalSqueezer");
		registry.PreRegisterBlock(MechanicalSqueezer);		
		Fermenter = new BlockFermenter().setUnlocalizedName("Fermenter");
		registry.PreRegisterBlock(Fermenter);
		PoweredGrinder = new BlockPoweredGrinder().setUnlocalizedName("PoweredGrinder");
		registry.PreRegisterBlock(PoweredGrinder);	
		PoweredFurnace = new BlockPoweredFurnace().setUnlocalizedName("PoweredFurnace");
		registry.PreRegisterBlock(PoweredFurnace);
		Former = new BlockFormer();
		registry.PreRegisterBlock(Former);
		Centrifuge = new BlockCentrifuge();
		registry.PreRegisterBlock(Centrifuge);
		LumberMill = new BlockLumberMill();
		registry.PreRegisterBlock(LumberMill);
		
		EsotericEnchanter = new BlockEsotericEnchanter("EsotericEnchanter");
		registry.PreRegisterBlock(EsotericEnchanter);
		
		FluidRefineryController = new BlockFluidRefineryController("FluidRefineryController");
		registry.PreRegisterBlock(FluidRefineryController);
		FluidRefineryCasing = new BlockFluidRefineryCasing("FluidRefineryCasing");
		registry.PreRegisterBlock(FluidRefineryCasing);
		FluidRefineryFluidInterface = new BlockRefineryFluidInterface("FluidRefineryFluidInterface");
		registry.PreRegisterBlock(FluidRefineryFluidInterface);
		
		FluidRefineryVent  = new BlockRefineryVent("FluidRefineryVent");
		registry.PreRegisterBlock(FluidRefineryVent);
		FluidRefineryMixer  = new BlockRefineryMixer("FluidRefineryMixer");
		registry.PreRegisterBlock(FluidRefineryMixer);
		FluidRefineryReactor  = new BlockRefineryReactor("FluidRefineryReactor");
		registry.PreRegisterBlock(FluidRefineryReactor);
		
		LogicGateBasePlate = new BlockLogicGateBasePlate(Material.IRON, "LogicGateBasePlate");
		registry.PreRegisterBlock(LogicGateBasePlate);
		SignalMultiplier = new BlockSignalMultiplier("SignalMultiplier");
		registry.PreRegisterBlock(SignalMultiplier);
		NotGate = new BlockNotGate("NotGate");
		registry.PreRegisterBlock(NotGate);
		PowerCell = new BlockPowerCell("PowerCell");
		registry.PreRegisterBlock(PowerCell);
		Timer = new BlockTimer("Timer");
		registry.PreRegisterBlock(Timer);
		Adder = new BlockAdder("Adder");
		registry.PreRegisterBlock(Adder);
		And = new BlockAndGate("And");
		registry.PreRegisterBlock(And);
		Or = new BlockOrGate("Or");
		registry.PreRegisterBlock(Or	);
		Subtractor = new BlockSubtractorGate("Subtractor");
		registry.PreRegisterBlock(Subtractor);
		LED = new BlockLED("LED");
		registry.PreRegisterBlock(LED);
		
		AstralQuary = new BlockAstralQuarryBrain("AstralQuary");
		registry.PreRegisterBlock(AstralQuary);
		
		SolderingTable = new BlockSolderingTable().setUnlocalizedName("SolderingTable");
		registry.PreRegisterBlock(SolderingTable);
		
		BasicSolarPanel = new BlockSolarPanel("BasicSolarPanel", Tier.BASIC);
		registry.PreRegisterBlock(BasicSolarPanel);
		StaticSolarPanel = new BlockSolarPanel("StaticSolarPanel", Tier.STATIC);
		registry.PreRegisterBlock(StaticSolarPanel);
		EnergizedSolarPanel = new BlockSolarPanel("EnergizedSolarPanel", Tier.ENERGIZED);
		registry.PreRegisterBlock(EnergizedSolarPanel);	
		LumumSolarPanel = new BlockSolarPanel("LumumSolarPanel", Tier.LUMUM);
		registry.PreRegisterBlock(LumumSolarPanel);
		CreativeSolarPanel = new BlockSolarPanel("CreativeSolarPanel", Tier.CREATIVE);
		registry.PreRegisterBlock(CreativeSolarPanel);
		
		StaticLamp = new Lamp("StaticLamp").setUnlocalizedName("StaticLamp");
		registry.PreRegisterBlock(StaticLamp);		
		EnergizedLamp = new Lamp("EnergizedLamp").setUnlocalizedName("EnergizedLamp");
		registry.PreRegisterBlock(EnergizedLamp);		
		LumumLamp = new Lamp("LumumLamp").setUnlocalizedName("LumumLamp");
		registry.PreRegisterBlock(LumumLamp);	
		
		StaticBlock = new BaseBlock(Material.IRON, "StaticBlock").setHardness(4.5f);
		registry.PreRegisterBlock(StaticBlock);	
		EnergizedBlock = new BaseBlock(Material.IRON, "EnergizedBlock").setHardness(4.5f);
		registry.PreRegisterBlock(EnergizedBlock);	
		LumumBlock = new BaseBlock(Material.IRON, "LumumBlock").setHardness(4.5f);
		registry.PreRegisterBlock(LumumBlock);
		BlockCopper = new BaseBlock(Material.IRON, "BlockCopper").setHardness(2.5f);
		registry.PreRegisterBlock(BlockCopper);	
		BlockTin = new BaseBlock(Material.IRON, "BlockTin").setHardness(2.5f);
		registry.PreRegisterBlock(BlockTin);	
		BlockSilver = new BaseBlock(Material.IRON, "BlockSilver").setHardness(3.5f);
		registry.PreRegisterBlock(BlockSilver);
		BlockLead = new BaseBlock(Material.IRON, "BlockLead").setHardness(2.0f);
		registry.PreRegisterBlock(BlockLead);	;
		BlockPlatinum = new BaseBlock(Material.IRON, "BlockPlatinum").setHardness(4.5f);
		registry.PreRegisterBlock(BlockPlatinum);
		BlockNickel = new BaseBlock(Material.IRON, "BlockNickel").setHardness(3.5f);
		registry.PreRegisterBlock(BlockNickel);	
		BlockAluminium = new BaseBlock(Material.IRON, "BlockAluminium").setHardness(2.75f);
		registry.PreRegisterBlock(BlockAluminium);
		BlockSapphire = new BaseBlock(Material.IRON, "BlockSapphire").setHardness(5.5f);
		registry.PreRegisterBlock(BlockSapphire);	
		BlockRuby = new BaseBlock(Material.IRON, "BlockRuby").setHardness(5.5f);
		registry.PreRegisterBlock(BlockRuby);
		
		StaticGrass = new StaticGrass();
		registry.PreRegisterBlock(StaticGrass);	
		EnergizedGrass = new EnergizedGrass();
		registry.PreRegisterBlock(EnergizedGrass);
		
		MachineBlock = new BaseBlock(Material.IRON, "MachineBlock");
		registry.PreRegisterBlock(MachineBlock);
	
		ObsidianGlass = new ObsidianGlass();
		registry.PreRegisterBlock(ObsidianGlass);
	
		AdvancedEarth = new AdvancedEarth().setUnlocalizedName("AdvancedEarth");
		registry.PreRegisterBlock(AdvancedEarth);
	
		StaticConduit = new BlockStaticConduit();
		registry.PreRegisterBlock(StaticConduit);		
		FluidConduit = new BlockFluidConduit();
		registry.PreRegisterBlock(FluidConduit);			
		ItemConduit = new BlockItemConduit();
		registry.PreRegisterBlock(ItemConduit);	
				
		CopperOre = new Ore("CopperOre", "pickaxe", 1, 2.5f);
		registry.PreRegisterBlock(CopperOre);
		TinOre = new Ore("TinOre", "pickaxe", 1, 2.1f);
		registry.PreRegisterBlock(TinOre);
		SilverOre = new Ore("SilverOre", "pickaxe", 2, 3.4f);
		registry.PreRegisterBlock(SilverOre);
		LeadOre = new Ore("LeadOre", "pickaxe", 2, 2.5f);
		registry.PreRegisterBlock(LeadOre);			
		PlatinumOre = new Ore("PlatinumOre", "pickaxe", 2, 4f);
		registry.PreRegisterBlock(PlatinumOre);	
		NickelOre = new Ore("NickelOre", "pickaxe", 1, 3f);
		registry.PreRegisterBlock(NickelOre);
		AluminiumOre = new Ore("AluminiumOre", "pickaxe", 2, 2.5f);
		registry.PreRegisterBlock(AluminiumOre);
		SapphireOre = new GemOre("SapphireOre", "pickaxe", 2, ItemMaterials.gemSapphire, 1, 2, 4.0f);
		registry.PreRegisterBlock(SapphireOre);
		RubyOre = new GemOre("RubyOre", "pickaxe", 2, ItemMaterials.gemRuby, 1, 2, 4.0f);
		registry.PreRegisterBlock(RubyOre);
	}
}
