package theking530.staticpower.blocks.crops;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import theking530.staticpower.Registry;

public class ModPlants {

	public static Block StaticCropPlant;
	public static Block EnergizedCropPlant;
	public static Block LumumCropPlant;
	public static Block TestDoublePlant;
	
	public static Item DepletedCrop;
	public static Item EnergizedSeeds;
	public static Item EnergizedCrop;
	public static Item StaticSeeds;
	public static Item StaticCrop;
	public static Item LumumSeeds;
	public static Item LumumCrop;
	
	public static void init(Registry registry) {		
		DepletedCrop = new DepletedCrop("DepletedCrop");
		registry.PreRegisterItem(DepletedCrop);
		
		StaticCropPlant = new StaticPlant("StaticCropPlant");
		registry.PreRegisterBlock(StaticCropPlant);	
		StaticSeeds = new StaticSeeds(StaticCropPlant, Blocks.FARMLAND).setUnlocalizedName("StaticSeeds");
		registry.PreRegisterItem(StaticSeeds);	
		MinecraftForge.addGrassSeed(new ItemStack(StaticSeeds), 20);
		StaticCrop = new StaticCrop().setUnlocalizedName("StaticCrop");
		registry.PreRegisterItem(StaticCrop);	
			
		EnergizedCropPlant = new EnergizedPlant("EnergizedCropPlant");
		registry.PreRegisterBlock(EnergizedCropPlant);	
		EnergizedSeeds = new EnergizedSeeds(EnergizedCropPlant, Blocks.FARMLAND).setUnlocalizedName("EnergizedSeeds");
		registry.PreRegisterItem(EnergizedSeeds);			
		EnergizedCrop = new EnergizedCrop().setUnlocalizedName("EnergizedCrop");
		registry.PreRegisterItem(EnergizedCrop);	
	
		LumumCropPlant = new LumumPlant("LumumCropPlant");
		registry.PreRegisterBlock(LumumCropPlant);
		LumumSeeds = new LumumSeeds(LumumCropPlant, Blocks.FARMLAND).setUnlocalizedName("LumumSeeds");
		registry.PreRegisterItem(LumumSeeds);	
		LumumCrop = new LumumCrop().setUnlocalizedName("LumumCrop");
		registry.PreRegisterItem(LumumCrop);		
		
		//TestDoublePlant = new BaseDoublePlant("TestDoublePlant", DepletedCrop, DepletedCrop).setLightLevel(1.0F);
		//-RegisterHelper.registerBlock(TestDoublePlant);	

	}
}
