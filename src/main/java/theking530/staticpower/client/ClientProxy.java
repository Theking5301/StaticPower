package theking530.staticpower.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.client.render.conduit.TileEntityRenderFluidConduit;
import theking530.staticpower.client.render.conduit.TileEntityRenderItemConduit;
import theking530.staticpower.client.render.conduit.TileEntityRenderStaticConduit;
import theking530.staticpower.client.render.tileentitys.digistore.TileEntityRenderDigistore;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderAdder;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderAndGate;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderLED;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderNotGate;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderOrGate;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderPowerCell;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderSignalMultiplier;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderSubtractor;
import theking530.staticpower.client.render.tileentitys.logicgates.TileEntityRenderTimer;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderBattery;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderCentrifuge;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderChargingStation;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderChest;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderCondenser;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderCropSqueezer;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderDistillery;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderEsotericEnchanter;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFarmer;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFermenter;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFluidGenerator;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFluidInfuser;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFormer;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderFusionFurnace;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderLumberMill;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderMechanicalSqueezer;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderPoweredFurnace;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderPoweredGrinder;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderQuarry;
import theking530.staticpower.client.render.tileentitys.machines.TileEntityRenderTreeFarmer;
import theking530.staticpower.client.render.tileentitys.multiblock.TileEntityRenderFluidRefineryController;
import theking530.staticpower.client.render.tileentitys.multiblock.TileEntityRenderFluidRefineryInterface;
import theking530.staticpower.client.render.tileentitys.multiblock.TileEntityRenderFluidRefineryMixer;
import theking530.staticpower.client.render.tileentitys.multiblock.TileEntityRenderFluidRefineryReactor;
import theking530.staticpower.client.render.tileentitys.multiblock.TileEntityRenderFluidRefineryVent;
import theking530.staticpower.conduits.fluidconduit.TileEntityFluidConduit;
import theking530.staticpower.conduits.itemconduit.TileEntityItemConduit;
import theking530.staticpower.conduits.staticconduit.TileEntityStaticConduit;
import theking530.staticpower.logic.gates.adder.TileEntityAdder;
import theking530.staticpower.logic.gates.and.TileEntityAndGate;
import theking530.staticpower.logic.gates.led.TileEntityLED;
import theking530.staticpower.logic.gates.notgate.TileEntityNotGate;
import theking530.staticpower.logic.gates.or.TileEntityOrGate;
import theking530.staticpower.logic.gates.powercell.TileEntityPowerCell;
import theking530.staticpower.logic.gates.subtractor.TileEntitySubtractorGate;
import theking530.staticpower.logic.gates.timer.TileEntityTimer;
import theking530.staticpower.logic.gates.transducer.TileEntitySignalMultiplier;
import theking530.staticpower.machines.basicfarmer.TileEntityBasicFarmer;
import theking530.staticpower.machines.batteries.tileentities.TileEntityEnergizedBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityLumumBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityStaticBattery;
import theking530.staticpower.machines.centrifuge.TileEntityCentrifuge;
import theking530.staticpower.machines.chargingstation.TileEntityChargingStation;
import theking530.staticpower.machines.condenser.TileEntityCondenser;
import theking530.staticpower.machines.cropsqueezer.TileEntityCropSqueezer;
import theking530.staticpower.machines.distillery.TileEntityDistillery;
import theking530.staticpower.machines.esotericenchanter.TileEsotericEnchanter;
import theking530.staticpower.machines.fermenter.TileEntityFermenter;
import theking530.staticpower.machines.fluidgenerator.TileEntityFluidGenerator;
import theking530.staticpower.machines.fluidinfuser.TileEntityFluidInfuser;
import theking530.staticpower.machines.former.TileEntityFormer;
import theking530.staticpower.machines.fusionfurnace.TileEntityFusionFurnace;
import theking530.staticpower.machines.lumbermill.TileLumberMill;
import theking530.staticpower.machines.mechanicalsqueezer.TileEntityMechanicalSqueezer;
import theking530.staticpower.machines.poweredfurnace.TileEntityPoweredFurnace;
import theking530.staticpower.machines.poweredgrinder.TileEntityPoweredGrinder;
import theking530.staticpower.machines.quarry.TileEntityQuarry;
import theking530.staticpower.machines.refinery.controller.TileEntityFluidRefineryController;
import theking530.staticpower.machines.refinery.fluidinterface.TileEntityRefineryFluidInterface;
import theking530.staticpower.machines.refinery.mixer.TileEntityRefineryMixer;
import theking530.staticpower.machines.refinery.reactor.TileEntityRefineryReactor;
import theking530.staticpower.machines.refinery.vent.TileEntityRefineryVent;
import theking530.staticpower.machines.treefarmer.TileEntityTreeFarm;
import theking530.staticpower.tileentity.chest.energizedchest.TileEntityEnergizedChest;
import theking530.staticpower.tileentity.chest.lumumchest.TileEntityLumumChest;
import theking530.staticpower.tileentity.chest.staticchest.TileEntityStaticChest;
import theking530.staticpower.tileentity.digistorenetwork.digistore.TileEntityDigistore;

public class ClientProxy extends CommonProxy {

	public void registerProxies(){		
		//Fluid Infuser
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidInfuser.class, new TileEntityRenderFluidInfuser());		
		//Crop Squeezer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCropSqueezer.class, new TileEntityRenderCropSqueezer());
		//Fluid Generator
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidGenerator.class, new TileEntityRenderFluidGenerator());
		//Fermenter
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenter.class, new TileEntityRenderFermenter());
		//Smelter
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredFurnace.class, new TileEntityRenderPoweredFurnace());
		//Grinder
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredGrinder.class, new TileEntityRenderPoweredGrinder());
		//SConduit
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStaticConduit.class, new TileEntityRenderStaticConduit());
		//FConduit
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidConduit.class, new TileEntityRenderFluidConduit());
		//IConduit
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemConduit.class, new TileEntityRenderItemConduit());
		//Mechanical Squeezer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMechanicalSqueezer.class, new TileEntityRenderMechanicalSqueezer());
		//Digistore
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDigistore.class, new TileEntityRenderDigistore());
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDigistoreWire.class, new TileEntityRenderDigistoreWire());
		//Former
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFormer.class, new TileEntityRenderFormer());			
		//Quarry
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuarry.class, new TileEntityRenderQuarry());		
		//Enchanter
		ClientRegistry.bindTileEntitySpecialRenderer(TileEsotericEnchanter.class, new TileEntityRenderEsotericEnchanter());	
		//Farmer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicFarmer.class, new TileEntityRenderFarmer());	
		//Tree Farmer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreeFarm.class, new TileEntityRenderTreeFarmer());	
		//Centrifuge
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCentrifuge.class, new TileEntityRenderCentrifuge());	
		//Centrifuge
		ClientRegistry.bindTileEntitySpecialRenderer(TileLumberMill.class, new TileEntityRenderLumberMill());	
		
		//FluidRefineryController
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidRefineryController.class, new TileEntityRenderFluidRefineryController());	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRefineryFluidInterface.class, new TileEntityRenderFluidRefineryInterface());	
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRefineryReactor.class, new TileEntityRenderFluidRefineryReactor());	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRefineryMixer.class, new TileEntityRenderFluidRefineryMixer());	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRefineryVent.class, new TileEntityRenderFluidRefineryVent());	
		
		
		
		
		//Signal Multiplier
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySignalMultiplier.class, new TileEntityRenderSignalMultiplier());	
		//Not Gate
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNotGate.class, new TileEntityRenderNotGate());	
		//Power Cell
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPowerCell.class, new TileEntityRenderPowerCell());	
		//Timer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTimer.class, new TileEntityRenderTimer());	
		//Adder
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdder.class, new TileEntityRenderAdder());	
		//And
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAndGate.class, new TileEntityRenderAndGate());	
		//Or
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOrGate.class, new TileEntityRenderOrGate());	
		//Subtractor
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySubtractorGate.class, new TileEntityRenderSubtractor());	
		//LED
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLED.class, new TileEntityRenderLED());	
		
		//Batteries
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStaticBattery.class, new TileEntityRenderBattery(Tier.STATIC));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedBattery.class, new TileEntityRenderBattery(Tier.ENERGIZED));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLumumBattery.class, new TileEntityRenderBattery(Tier.LUMUM));
		//Charging Station
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChargingStation.class, new TileEntityRenderChargingStation());	
		
		//Chests		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStaticChest.class, new TileEntityRenderChest(Tier.STATIC));	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedChest.class, new TileEntityRenderChest(Tier.ENERGIZED));	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLumumChest.class, new TileEntityRenderChest(Tier.LUMUM));		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFusionFurnace.class, new TileEntityRenderFusionFurnace());
		
		//Distillery
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDistillery.class, new TileEntityRenderDistillery());
		
		//Condenser
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCondenser.class, new TileEntityRenderCondenser());
	}
	public static void registerModelLoader(Item item, ResourceLocation modelLocation) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modelLocation, "inventory"));
	}
}