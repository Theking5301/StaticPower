package theking530.staticpower.fluids;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.Registry;
import theking530.staticpower.assists.Reference;

public class ModFluids {
	
	private static final Map<String, Fluid> fluid_map = new HashMap<String, Fluid>();
	
	public static Fluid StaticFluid;
	public static Fluid EnergizedFluid;
	public static Fluid LumumFluid;
	public static Fluid RefinedFluid;	
	public static Fluid Steam;
	public static Fluid Ethanol;
	public static Fluid Mash;
	public static Fluid EvaporatedMash;
	public static Fluid LiquidExperience;
	public static Fluid TreeOil;
	public static Fluid TreeSap;
	
	public static void init(Registry registry) {
		StaticFluid = registerFluid(registry, "StaticFluid", MapColor.EMERALD, 1500, 3000, 100);

		EnergizedFluid = registerFluid(registry, "EnergizedFluid", MapColor.DIAMOND, 1500, 3000, 300);

		LumumFluid = registerFluid(registry, "LumumFluid", MapColor.GOLD, 1500, 3000, 500);

		RefinedFluid = registerFluid(registry, "RefinedFluid", MapColor.PURPLE, 1500, 3000, 800);

		Steam = registerFluid(registry, "Steam", MapColor.WHITE_STAINED_HARDENED_CLAY, 1500, 3000, 100);

		Ethanol = registerFluid(registry, "Ethanol", MapColor.CYAN, 150, 1000, 72);

		Mash = registerFluid(registry, "Mash", MapColor.WOOD, 1500, 1000, 72);

		EvaporatedMash = registerFluid(registry, "EvaporatedMash", MapColor.WOOD, 1500, 1000, 110);

		LiquidExperience = registerFluid(registry, "LiquidExperience", MapColor.EMERALD, 150, 1000, 80);
		
		TreeSap = registerFluid(registry, "TreeSap", MapColor.WOOD, 200, 2000, 72);

		TreeOil = registerFluid(registry, "TreeOil", MapColor.SAND, 150, 1000, 72);
		
	}
	public static void initBlockRendering() {
		for(Entry<String, Fluid> entry : fluid_map.entrySet()) {
		    registerFluidBlockRendering(entry.getValue(), entry.getKey());
		}
	}
	public static void initItemRendering() {
		for(Entry<String, Fluid> entry : fluid_map.entrySet()) {
			registerFluidItemRendering(entry.getValue(), entry.getKey());
		}
	}
	
	private static Fluid registerFluid(Registry registry, String fluidName, MapColor color, int density, int viscosity, int temperature) {
	    Fluid f  = new Fluid(fluidName, getStill(fluidName), getFlowing(fluidName)).setDensity(density).setViscosity(viscosity).setTemperature(temperature);
	    FluidRegistry.registerFluid(f);
	    f = FluidRegistry.getFluid(f.getName());
	    Block fluidBlock = new BaseFluidBlock(f, new MaterialLiquid(color), fluidName);
		registry.PreRegisterBlock(fluidBlock);
		FluidRegistry.addBucketForFluid(f);    
	    fluid_map.put(fluidName, f);
	    return f;
	}
	private static ResourceLocation getStill(String fluidName) {
		return new ResourceLocation(Reference.MOD_ID + ":blocks/fluids/" + fluidName + "Still");
	}
	private static ResourceLocation getFlowing(String fluidName) {
		return new ResourceLocation(Reference.MOD_ID  + ":blocks/fluids/" + fluidName + "Flow");
	}
	@SideOnly(Side.CLIENT)
	private static void registerFluidBlockRendering(Fluid fluid, String name) {
	    FluidStateMapper mapper = new FluidStateMapper(fluid);
	    Block block = fluid.getBlock();

	    if (block != null) {
	      ModelLoader.setCustomStateMapper(block, mapper);
	    }
	}
	@SideOnly(Side.CLIENT)
	private static void registerFluidItemRendering(Fluid fluid, String name) {
	    FluidStateMapper mapper = new FluidStateMapper(fluid);
	    Block block = fluid.getBlock();
	    Item item = Item.getItemFromBlock(block);

	    if (item != null) {
	      ModelLoader.registerItemVariants(item);
	      ModelLoader.setCustomMeshDefinition(item, mapper);
	    }
	}
	private static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

	    public final ModelResourceLocation location;

	    public FluidStateMapper(Fluid fluid) {
	    	location = new ModelResourceLocation(Reference.MOD_ID + ":Fluid", fluid.getName());
	    }

	    @Override
	    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
	      return location;
	    }

	    @Override
	    public ModelResourceLocation getModelLocation(ItemStack stack) {
	      return location;
	    }
	}
}

