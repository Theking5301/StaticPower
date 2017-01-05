package theking530.staticpower.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.tools.basictools.ModTools;
import theking530.staticpower.world.plants.ModPlants;

public class ItemRenderRegistry {

	public static void initItemRenderers() {
		ItemRenderRegistry.registerItem(ModItems.StaticHelmet);
		ItemRenderRegistry.registerItem(ModItems.StaticChestplate);
		ItemRenderRegistry.registerItem(ModItems.StaticLeggings);
		ItemRenderRegistry.registerItem(ModItems.StaticBoots);
		ItemRenderRegistry.registerItem(ModItems.EnergizedHelmet);
		ItemRenderRegistry.registerItem(ModItems.EnergizedChestplate);
		ItemRenderRegistry.registerItem(ModItems.EnergizedLeggings);
		ItemRenderRegistry.registerItem(ModItems.EnergizedBoots);
		ItemRenderRegistry.registerItem(ModItems.LumumHelmet);
		ItemRenderRegistry.registerItem(ModItems.LumumChestplate);
		ItemRenderRegistry.registerItem(ModItems.LumumLeggings);
		ItemRenderRegistry.registerItem(ModItems.LumumBoots);
		
		ItemRenderRegistry.registerItem(ModItems.BaseFluidCapsule);
		ItemRenderRegistry.registerItem(ModItems.StaticFluidCapsule);
		ItemRenderRegistry.registerItem(ModItems.EnergizedFluidCapsule);
		ItemRenderRegistry.registerItem(ModItems.LumumFluidCapsule);
		//FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor)ModItems.EnergizedFluidCapsule, 
				//ModItems.BaseFluidCapsule, ModItems.StaticFluidCapsule, ModItems.EnergizedFluidCapsule, ModItems.LumumFluidCapsule);
		
		ItemRenderRegistry.registerItem(ModItems.SilverDust);
		ItemRenderRegistry.registerItem(ModItems.CopperDust);
		ItemRenderRegistry.registerItem(ModItems.IronDust);
		ItemRenderRegistry.registerItem(ModItems.GoldDust);
		ItemRenderRegistry.registerItem(ModItems.SilverDust);
		ItemRenderRegistry.registerItem(ModItems.TinDust);
		ItemRenderRegistry.registerItem(ModItems.LeadDust);
		ItemRenderRegistry.registerItem(ModItems.PlatinumDust);
		ItemRenderRegistry.registerItem(ModItems.NickelDust);
		ItemRenderRegistry.registerItem(ModItems.AluminiumDust);
		ItemRenderRegistry.registerItem(ModItems.StaticDust);
		ItemRenderRegistry.registerItem(ModItems.EnergizedDust);
		ItemRenderRegistry.registerItem(ModItems.LumumDust);
		ItemRenderRegistry.registerItem(ModItems.InertInfusionBlend);
		ItemRenderRegistry.registerItem(ModItems.RedstoneAlloyDust);
		
		ItemRenderRegistry.registerItem(ModItems.StaticNugget);
		ItemRenderRegistry.registerItem(ModItems.EnergizedNugget);
		ItemRenderRegistry.registerItem(ModItems.LumumNugget);
		ItemRenderRegistry.registerItem(ModItems.IronNugget);
		
		ItemRenderRegistry.registerItem(ModItems.EnergizedInfusionBlend);
		ItemRenderRegistry.registerItem(ModItems.LumumInfusionBlend);	
		ItemRenderRegistry.registerItem(ModItems.EnergizedEnergyCrystal);
		ItemRenderRegistry.registerItem(ModItems.LumumEnergyCrystal);
		ItemRenderRegistry.registerItem(ModItems.CopperCoil);
		ItemRenderRegistry.registerItem(ModItems.SilverCoil);
		ItemRenderRegistry.registerItem(ModItems.GoldCoil);
		ItemRenderRegistry.registerItem(ModItems.CopperWire);
		ItemRenderRegistry.registerItem(ModItems.SilverWire);
		ItemRenderRegistry.registerItem(ModItems.GoldWire);
		ItemRenderRegistry.registerItem(ModItems.IronPlate);
		ItemRenderRegistry.registerItem(ModItems.CopperPlate);
		ItemRenderRegistry.registerItem(ModItems.TinPlate);
		ItemRenderRegistry.registerItem(ModItems.SilverPlate);
		ItemRenderRegistry.registerItem(ModItems.GoldPlate);
		ItemRenderRegistry.registerItem(ModItems.LeadPlate);
		ItemRenderRegistry.registerItem(ModItems.StaticPlate);
		ItemRenderRegistry.registerItem(ModItems.EnergizedPlate);
		ItemRenderRegistry.registerItem(ModItems.LumumPlate);
		
		ItemRenderRegistry.registerItem(ModItems.SilverIngot);
		ItemRenderRegistry.registerItem(ModItems.CopperIngot);
		ItemRenderRegistry.registerItem(ModItems.TinIngot);
		ItemRenderRegistry.registerItem(ModItems.LeadIngot);
		ItemRenderRegistry.registerItem(ModItems.PlatinumIngot);
		ItemRenderRegistry.registerItem(ModItems.StaticIngot);
		ItemRenderRegistry.registerItem(ModItems.EnergizedIngot);
		ItemRenderRegistry.registerItem(ModItems.LumumIngot);
		ItemRenderRegistry.registerItem(ModItems.InertIngot);
		ItemRenderRegistry.registerItem(ModItems.RedstoneAlloyIngot);
		ItemRenderRegistry.registerItem(ModItems.NickelIngot);
		ItemRenderRegistry.registerItem(ModItems.AluminiumIngot);
		ItemRenderRegistry.registerItem(ModItems.SapphireGem);
		ItemRenderRegistry.registerItem(ModItems.RubyGem);
		
		ItemRenderRegistry.registerItem(ModItems.StaticWrench);
		ItemRenderRegistry.registerItem(ModItems.SolderingIron);
		ItemRenderRegistry.registerItem(ModItems.CoordinateMarker);
		ItemRenderRegistry.registerItem(ModItems.StaticBook);
		ItemRenderRegistry.registerItem(ModItems.WireCutters);
		ItemRenderRegistry.registerItem(ModItems.MetalHammer);
		ItemRenderRegistry.registerItem(ModItems.ElectricSolderingIron);
		
		ItemRenderRegistry.registerItem(ModItems.BasicCircuit);
		ItemRenderRegistry.registerItem(ModItems.StaticCircuit);
		ItemRenderRegistry.registerItem(ModItems.EnergizedCircuit);
		ItemRenderRegistry.registerItem(ModItems.LumumCircuit);
		
		ItemRenderRegistry.registerItem(ModItems.BasicCircuit);
		ItemRenderRegistry.registerItem(ModItems.StaticCircuit);
		ItemRenderRegistry.registerItem(ModItems.EnergizedCircuit);
		ItemRenderRegistry.registerItem(ModItems.LumumCircuit);
		
		ItemRenderRegistry.registerItem(ModItems.BasicBattery);
		ItemRenderRegistry.registerItem(ModItems.StaticBattery);
		ItemRenderRegistry.registerItem(ModItems.EnergizedBattery);
		ItemRenderRegistry.registerItem(ModItems.LumumBattery);
		//FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor)ModItems.BasicBattery, 
				//ModItems.BasicBattery, ModItems.StaticBattery, ModItems.EnergizedBattery, ModItems.LumumBattery);
		
		ItemRenderRegistry.registerItem(ModItems.Rubber);
		ItemRenderRegistry.registerItem(ModItems.IOPort);
		
		ItemRenderRegistry.registerItem(ModItems.BasicPowerUpgrade);
		ItemRenderRegistry.registerItem(ModItems.BasicSpeedUpgrade);
		ItemRenderRegistry.registerItem(ModItems.BasicTankUpgrade);
		ItemRenderRegistry.registerItem(ModItems.BasicRangeUpgrade);
		ItemRenderRegistry.registerItem(ModItems.StaticPowerUpgrade);
		ItemRenderRegistry.registerItem(ModItems.StaticSpeedUpgrade);
		ItemRenderRegistry.registerItem(ModItems.StaticTankUpgrade);
		ItemRenderRegistry.registerItem(ModItems.StaticQuarryingUpgrade);
		ItemRenderRegistry.registerItem(ModItems.StaticRangeUpgrade);
		ItemRenderRegistry.registerItem(ModItems.EnergizedPowerUpgrade);
		ItemRenderRegistry.registerItem(ModItems.EnergizedSpeedUpgrade);
		ItemRenderRegistry.registerItem(ModItems.EnergizedTankUpgrade);
		ItemRenderRegistry.registerItem(ModItems.EnergizedQuarryingUpgrade);
		ItemRenderRegistry.registerItem(ModItems.EnergizedRangeUpgrade);
		ItemRenderRegistry.registerItem(ModItems.LumumPowerUpgrade);
		ItemRenderRegistry.registerItem(ModItems.LumumSpeedUpgrade);
		ItemRenderRegistry.registerItem(ModItems.LumumTankUpgrade);
		ItemRenderRegistry.registerItem(ModItems.LumumQuarryingUpgrade);
		ItemRenderRegistry.registerItem(ModItems.LumumRangeUpgrade);
		ItemRenderRegistry.registerItem(ModItems.BasicUpgradePlate);
		ItemRenderRegistry.registerItem(ModItems.StaticUpgradePlate);
		ItemRenderRegistry.registerItem(ModItems.EnergizedUpgradePlate);
		ItemRenderRegistry.registerItem(ModItems.LumumUpgradePlate);
		
		ItemRenderRegistry.registerItem(ModPlants.StaticCrop);
		ItemRenderRegistry.registerItem(ModPlants.EnergizedCrop);
		ItemRenderRegistry.registerItem(ModPlants.LumumCrop);
		ItemRenderRegistry.registerItem(ModPlants.DepletedCrop);
		ItemRenderRegistry.registerItem(ModPlants.StaticSeeds);
		ItemRenderRegistry.registerItem(ModPlants.EnergizedSeeds);
		ItemRenderRegistry.registerItem(ModPlants.LumumSeeds);
		
		ItemRenderRegistry.registerItem(ModItems.BasicItemFilter);
		ItemRenderRegistry.registerItem(ModItems.UpgradedItemFilter);
		ItemRenderRegistry.registerItem(ModItems.AdvancedItemFilter);
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.MachineBlock));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticBattery));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedBattery));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumBattery));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.CropSqueezer));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.MechanicalSqueezer));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.FluidGenerator));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.FluidInfuser));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.FusionFurnace));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.PoweredFurnace));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.PoweredGrinder));
		
		//ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.SolderingTable));

        OBJLoader.INSTANCE.addDomain(Reference.MODID.toLowerCase());
        Item item2 = Item.getItemFromBlock(ModBlocks.SolderingTable);
        ModelLoader.setCustomModelResourceLocation(item2, 0, new ModelResourceLocation(Reference.MODID.toLowerCase() + ":" + ModBlocks.SolderingTable.getUnlocalizedName().substring(5), "inventory"));
        
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.Quarry));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BasicFarmer));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.ChargingStation));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.AdvancedEarth));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.Fermenter));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.HeatingElement));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.Distillery));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.Condenser));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.SilverOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.CopperOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.TinOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LeadOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.PlatinumOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.NickelOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.AluminiumOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.SapphireOre));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.RubyOre));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticPlanks));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedPlanks));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumPlanks));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticCobblestone));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedCobblestone));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumCobblestone));

		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticBlock));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedBlock));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumBlock));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockCopper));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockTin));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockSilver));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockLead));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockPlatinum));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockNickel));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockAluminium));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockSapphire));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.BlockRuby));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticLamp));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LogicGateBasePlate));
		ItemRenderRegistry.registerItem(ModItems.LogicGatePowerSync);
		ItemRenderRegistry.registerItem(ModItems.InvertedLogicGatePowerSync);
		ItemRenderRegistry.registerItem(ModItems.LogicGateServo);
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.VacuumChest));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticChest));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedChest));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumChest));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.ItemConduit));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticConduit));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.FluidConduit));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.ObsidianGlass));
		
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.StaticWood));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.EnergizedWood));
		ItemRenderRegistry.registerItem(Item.getItemFromBlock(ModBlocks.LumumWood));
		
		ItemRenderRegistry.registerItem(ModTools.CopperAxe);
		ItemRenderRegistry.registerItem(ModTools.CopperPickaxe);
		ItemRenderRegistry.registerItem(ModTools.CopperShovel);
		ItemRenderRegistry.registerItem(ModTools.CopperHoe);
		ItemRenderRegistry.registerItem(ModTools.CopperSword);
		
		ItemRenderRegistry.registerItem(ModTools.TinAxe);
		ItemRenderRegistry.registerItem(ModTools.TinPickaxe);
		ItemRenderRegistry.registerItem(ModTools.TinShovel);
		ItemRenderRegistry.registerItem(ModTools.TinHoe);
		ItemRenderRegistry.registerItem(ModTools.TinSword);
		
		ItemRenderRegistry.registerItem(ModTools.SilverAxe);
		ItemRenderRegistry.registerItem(ModTools.SilverPickaxe);
		ItemRenderRegistry.registerItem(ModTools.SilverShovel);
		ItemRenderRegistry.registerItem(ModTools.SilverHoe);
		ItemRenderRegistry.registerItem(ModTools.SilverSword);
		
		ItemRenderRegistry.registerItem(ModTools.LeadAxe);
		ItemRenderRegistry.registerItem(ModTools.LeadPickaxe);
		ItemRenderRegistry.registerItem(ModTools.LeadShovel);
		ItemRenderRegistry.registerItem(ModTools.LeadHoe);
		ItemRenderRegistry.registerItem(ModTools.LeadSword);
		
		ItemRenderRegistry.registerItem(ModTools.PlatinumAxe);
		ItemRenderRegistry.registerItem(ModTools.PlatinumPickaxe);
		ItemRenderRegistry.registerItem(ModTools.PlatinumShovel);
		ItemRenderRegistry.registerItem(ModTools.PlatinumHoe);
		ItemRenderRegistry.registerItem(ModTools.PlatinumSword);
		
		ItemRenderRegistry.registerItem(ModTools.StaticAxe);
		ItemRenderRegistry.registerItem(ModTools.StaticPickaxe);
		ItemRenderRegistry.registerItem(ModTools.StaticShovel);
		ItemRenderRegistry.registerItem(ModTools.StaticHoe);
		ItemRenderRegistry.registerItem(ModTools.StaticSword);
		
		ItemRenderRegistry.registerItem(ModTools.EnergizedAxe);
		ItemRenderRegistry.registerItem(ModTools.EnergizedPickaxe);
		ItemRenderRegistry.registerItem(ModTools.EnergizedShovel);
		ItemRenderRegistry.registerItem(ModTools.EnergizedHoe);
		ItemRenderRegistry.registerItem(ModTools.EnergizedSword);
		
		ItemRenderRegistry.registerItem(ModTools.LumumAxe);
		ItemRenderRegistry.registerItem(ModTools.LumumPickaxe);
		ItemRenderRegistry.registerItem(ModTools.LumumShovel);
		ItemRenderRegistry.registerItem(ModTools.LumumHoe);
		ItemRenderRegistry.registerItem(ModTools.LumumSword);
		
		ItemRenderRegistry.registerItem(ModTools.SapphireAxe);
		ItemRenderRegistry.registerItem(ModTools.SapphirePickaxe);
		ItemRenderRegistry.registerItem(ModTools.SapphireShovel);
		ItemRenderRegistry.registerItem(ModTools.SapphireHoe);
		ItemRenderRegistry.registerItem(ModTools.SapphireSword);
		
		ItemRenderRegistry.registerItem(ModTools.RubyAxe);
		ItemRenderRegistry.registerItem(ModTools.RubyPickaxe);
		ItemRenderRegistry.registerItem(ModTools.RubyShovel);
		ItemRenderRegistry.registerItem(ModTools.RubyHoe);
		ItemRenderRegistry.registerItem(ModTools.RubySword);
		
	}
    public static void registerItem(Item item, int metadata) {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(item, metadata, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
    public static void registerItem(Item item) {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
    public static void registerBlock(Block block, int metadata) {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(Reference.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
    }
    public static void registerBlock(Block block) {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
    }
}
