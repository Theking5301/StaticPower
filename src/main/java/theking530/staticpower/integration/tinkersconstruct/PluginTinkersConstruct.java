package theking530.staticpower.integration.tinkersconstruct;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.handlers.crafting.recipes.FusionRecipes;
import theking530.staticpower.handlers.crafting.recipes.SmeltingRecipes;
import theking530.staticpower.integration.ICompatibilityPlugin;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.ModItems;

public class PluginTinkersConstruct implements ICompatibilityPlugin {

	private boolean registered;
	private static final Material staticMetal = new Material("staticmetal", TextFormatting.GREEN);
	private static final Material energizedMetal = new Material("energizedMetal", TextFormatting.AQUA);
	private static final Material lumumMetal = new Material("lumumMetal", TextFormatting.YELLOW);
	
	@Override
	public boolean isRegistered() {
		return registered;
	}
	@Override
	public boolean shouldRegister() {
		return Loader.isModLoaded("tconstruct");
	}
	@Override
	public void register() {
		if(isRegistered()) {
			return;
		}
		registered = true;
	
	}
	
	@Override
	public void preInit() {
		initializeTinkersMaterials();	
		
		ItemMaterials.dustCobalt = ModItems.Materials.createSubOreItem(168, "dustCobalt");
		ItemMaterials.dustArdite = ModItems.Materials.createSubOreItem(169, "dustArdite");
	
	}	
	@Override
	public void init() {
		staticMetal.setCastable(true);
		staticMetal.setCraftable(false);	
		staticMetal.addItem("nuggetStatic", 1, Material.VALUE_Nugget);
		staticMetal.addItem("ingotStatic", 1, Material.VALUE_Ingot);
		staticMetal.addItem("blockStatic", 1, Material.VALUE_Block);
		
		energizedMetal.setCastable(true);
		energizedMetal.setCraftable(false);
		energizedMetal.addItem("nuggetenergized", 1, Material.VALUE_Nugget);
		energizedMetal.addItem("ingotenergized", 1, Material.VALUE_Ingot);
		energizedMetal.addItem("blockenergized", 1, Material.VALUE_Block);
		
		lumumMetal.setCastable(true);
		lumumMetal.setCraftable(false);
		lumumMetal.addItem("nuggetlumum", 1, Material.VALUE_Nugget);
		lumumMetal.addItem("ingotlumum", 1, Material.VALUE_Ingot);
		lumumMetal.addItem("blocklumum", 1, Material.VALUE_Block);
	}	
	@Override
	public void postInit() {
		FusionRecipes.registerOreRecipe("dustManyullyn", 1, Craft.ing("dustCobalt"), Craft.ing("dustArdite"));
		FusionRecipes.registerOreRecipe("ingotManyullyn", 1, Craft.ing("dustCobalt"), Craft.ing("ingotArdite"));
		FusionRecipes.registerOreRecipe("ingotManyullyn", 1, Craft.ing("ingotCobalt"), Craft.ing("dustArdite"));
		FusionRecipes.registerOreRecipe("ingotManyullyn", 1, Craft.ing("ingotCobalt"), Craft.ing("ingotArdite"));
		FusionRecipes.registerOreRecipe("blockManyullyn", 1, Craft.ing("blockCobalt"), Craft.ing("blockArdite"));
		FusionRecipes.registerOreRecipe("ingotManyullyn", 2, Craft.ing("oreCobalt"), Craft.ing("oreArdite"));
		
		SmeltingRecipes.registerRecipe(ItemMaterials.dustCobalt, OreDictionary.getOres("ingotCobalt").get(0), 1.0f);
		SmeltingRecipes.registerRecipe(ItemMaterials.dustArdite, OreDictionary.getOres("dustArdite").get(0), 1.0f);
	}
	
	@Override
	public String getPluginName() {
		return "Tinkers' Construct";
	}
	
	public void initializeTinkersMaterials() {
		sendFluidForMelting("Static", ModFluids.StaticFluid);
		sendFluidForMelting("Energized", ModFluids.EnergizedFluid);
		sendFluidForMelting("Lumum", ModFluids.LumumFluid);
				
		TinkerRegistry.addMaterialStats(staticMetal, new HeadMaterialStats(200, 2.5f, 9.5f, HarvestLevels.COBALT), new HandleMaterialStats(.75f, 50), new ExtraMaterialStats(-25));
		TinkerRegistry.addMaterialStats(energizedMetal, new HeadMaterialStats(400, 9.5f, 2.0f, HarvestLevels.COBALT), new HandleMaterialStats(.85f, 75), new ExtraMaterialStats(50));
		TinkerRegistry.addMaterialStats(lumumMetal, new HeadMaterialStats(1000, 7.21f, 8.75f, HarvestLevels.COBALT), new HandleMaterialStats(.95f, 180), new ExtraMaterialStats(85));
		
		TinkerRegistry.integrate(staticMetal).preInit();
		TinkerRegistry.integrate(energizedMetal).preInit();
		TinkerRegistry.integrate(lumumMetal).preInit();
	}	
	public static Fluid sendFluidForMelting(String ore, Fluid fluid){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", fluid.getName());
		tag.setString("ore", ore);
		tag.setBoolean("toolforge", true);
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
		return fluid;
	}
}
