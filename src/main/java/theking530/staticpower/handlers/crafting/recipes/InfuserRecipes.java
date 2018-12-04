package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.blocks.crops.ModPlants;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.MiscItems;
import theking530.staticpower.items.armor.ModArmor;

public class InfuserRecipes {

	public static void registerInfusionRecipes() {
		//StaticFluid
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticWood)), Craft.ing("logWood"), new FluidStack(ModFluids.StaticFluid, 150));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticCobblestone)), Craft.ing(Blocks.COBBLESTONE), new FluidStack(ModFluids.StaticFluid, 50));	
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticLamp)), Craft.ing(ModBlocks.ObsidianGlass), new FluidStack(ModFluids.StaticFluid, 1000));
		RegisterHelper.registerInfuserRecipe(ItemMaterials.ingotStatic, Craft.ing(Items.IRON_INGOT), new FluidStack(ModFluids.StaticFluid, 250));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.dustStatic),  Craft.ing("dustIron"), new FluidStack(ModFluids.StaticFluid, 250));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.plateStatic), Craft.ing("plateIron"), new FluidStack(ModFluids.StaticFluid, 250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticBlock)), Craft.ing(Item.getItemFromBlock(Blocks.IRON_BLOCK)), new FluidStack(ModFluids.StaticFluid, 2250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.StaticHelmet), Craft.ing(Items.IRON_HELMET), new FluidStack(ModFluids.StaticFluid, 1250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.StaticChestplate), Craft.ing(Items.IRON_CHESTPLATE), new FluidStack(ModFluids.StaticFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.StaticLeggings), Craft.ing(Items.IRON_LEGGINGS), new FluidStack(ModFluids.StaticFluid, 1750));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.StaticBoots), Craft.ing(Items.IRON_BOOTS), new FluidStack(ModFluids.StaticFluid, 1000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModPlants.EnergizedCrop), Craft.ing(ModPlants.StaticCrop), new FluidStack(ModFluids.StaticFluid, 10000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticGrass)), Craft.ing(Item.getItemFromBlock(Blocks.DIRT)), new FluidStack(ModFluids.StaticFluid, 500));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticChest)), Craft.ing(Item.getItemFromBlock(Blocks.CHEST)), new FluidStack(ModFluids.StaticFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.StaticSolarPanel)), Craft.ing(Item.getItemFromBlock(ModBlocks.BasicSolarPanel)), new FluidStack(ModFluids.StaticFluid, 1000));
		RegisterHelper.registerInfuserRecipe(MiscItems.staticPie, Craft.ing(MiscItems.applePie), new FluidStack(ModFluids.StaticFluid, 50));
		
		//EnergizedFluid	
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedWood)), Craft.ing("logWood"), new FluidStack(ModFluids.EnergizedFluid, 150));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedCobblestone)), Craft.ing(Blocks.COBBLESTONE), new FluidStack(ModFluids.EnergizedFluid, 50));	
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedLamp)), Craft.ing(ModBlocks.ObsidianGlass), new FluidStack(ModFluids.EnergizedFluid, 1000));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.ingotEnergized), Craft.ing(ItemMaterials.ingotStatic), new FluidStack(ModFluids.EnergizedFluid, 250));		
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.dustEnergized), Craft.ing(ItemMaterials.dustStatic), new FluidStack(ModFluids.EnergizedFluid, 250));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.plateEnergized), Craft.ing(ItemMaterials.plateStatic), new FluidStack(ModFluids.EnergizedFluid, 250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedBlock)), Craft.ing(Item.getItemFromBlock(Blocks.IRON_BLOCK)), new FluidStack(ModFluids.EnergizedFluid, 2250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.EnergizedHelmet), Craft.ing(ModArmor.StaticHelmet), new FluidStack(ModFluids.EnergizedFluid, 1250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.EnergizedChestplate), Craft.ing(ModArmor.StaticChestplate), new FluidStack(ModFluids.EnergizedFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.EnergizedLeggings), Craft.ing(ModArmor.StaticLeggings), new FluidStack(ModFluids.EnergizedFluid, 1750));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.EnergizedBoots), Craft.ing(ModArmor.StaticBoots), new FluidStack(ModFluids.EnergizedFluid, 1000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModPlants.LumumCrop), Craft.ing(ModPlants.EnergizedCrop), new FluidStack(ModFluids.EnergizedFluid, 20000));	
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedSolarPanel)), Craft.ing(Item.getItemFromBlock(ModBlocks.StaticSolarPanel)), new FluidStack(ModFluids.EnergizedFluid, 1000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedChest)), Craft.ing(Item.getItemFromBlock(ModBlocks.StaticChest)), new FluidStack(ModFluids.EnergizedFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.EnergizedGrass)), Craft.ing(Item.getItemFromBlock(Blocks.DIRT)), new FluidStack(ModFluids.EnergizedFluid, 500));
		RegisterHelper.registerInfuserRecipe(MiscItems.energizedPie, Craft.ing(MiscItems.staticPie), new FluidStack(ModFluids.EnergizedFluid, 50));
		
		//LumumFluid
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumWood)), Craft.ing("logWood"), new FluidStack(ModFluids.LumumFluid, 150));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumCobblestone)), Craft.ing(Blocks.COBBLESTONE), new FluidStack(ModFluids.LumumFluid, 50));	
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumLamp)), Craft.ing(ModBlocks.ObsidianGlass), new FluidStack(ModFluids.LumumFluid, 1000));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.ingotLumum), Craft.ing(ItemMaterials.ingotEnergized), new FluidStack(ModFluids.LumumFluid, 250));		
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.dustLumum), Craft.ing(ItemMaterials.dustEnergized), new FluidStack(ModFluids.LumumFluid, 250));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.plateLumum), Craft.ing(ItemMaterials.plateEnergized), new FluidStack(ModFluids.LumumFluid, 250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumSolarPanel)), Craft.ing(ModBlocks.EnergizedSolarPanel), new FluidStack(ModFluids.LumumFluid, 1000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumBlock)), Craft.ing(ModBlocks.EnergizedBlock), new FluidStack(ModFluids.LumumFluid, 2250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LumumChest)), Craft.ing(ModBlocks.EnergizedChest), new FluidStack(ModFluids.LumumFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.LumumHelmet), Craft.ing(ModArmor.EnergizedHelmet), new FluidStack(ModFluids.LumumFluid, 1250));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.LumumChestplate), Craft.ing(ModArmor.EnergizedChestplate), new FluidStack(ModFluids.LumumFluid, 2000));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.LumumLeggings), Craft.ing(ModArmor.EnergizedLeggings), new FluidStack(ModFluids.LumumFluid, 1750));
		RegisterHelper.registerInfuserRecipe(new ItemStack(ModArmor.LumumBoots), Craft.ing(ModArmor.EnergizedBoots), new FluidStack(ModFluids.LumumFluid, 1000));
		RegisterHelper.registerInfuserRecipe(MiscItems.lumumPie, Craft.ing(MiscItems.energizedPie), new FluidStack(ModFluids.LumumFluid, 50));
		
		//Water
		RegisterHelper.registerInfuserRecipe(new ItemStack(Items.CLAY_BALL), Craft.ing(Blocks.GRAVEL), new FluidStack(FluidRegistry.WATER, 1000));	
	}
}
