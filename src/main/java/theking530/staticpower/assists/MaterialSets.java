package theking530.staticpower.assists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.items.ItemMaterials;

public class MaterialSets {

	public static MaterialSet IRON;
	public static MaterialSet GOLD;
	public static MaterialSet TIN;
	public static MaterialSet COPPER;
	public static MaterialSet SILVER;
	public static MaterialSet LEAD;
	public static MaterialSet NICKEL;
	public static MaterialSet PLATINUM;
	public static MaterialSet ALUMINIUM;
	public static MaterialSet INERT_INFUSION;
	public static MaterialSet REDSTONE_ALLOY;
	
	public static MaterialSet STATIC;
	public static MaterialSet ENERGIZED;
	public static MaterialSet LUMUM;
	
	public static List<MaterialSet> MATERIALS;
	
	public static void initialize() {
		MATERIALS = new ArrayList<MaterialSet>();
		
		MATERIALS.add(IRON = new MaterialSet("Iron", new ItemStack(Items.IRON_INGOT), ItemMaterials.dustIron, new ItemStack(Items.IRON_NUGGET), Blocks.IRON_BLOCK, ItemMaterials.gearIron, ItemMaterials.plateIron));
		MATERIALS.add(GOLD = new MaterialSet("Gold",new ItemStack(Items.GOLD_INGOT), ItemMaterials.dustGold, new ItemStack(Items.GOLD_NUGGET), Blocks.GOLD_BLOCK, ItemMaterials.gearGold, ItemMaterials.plateGold));
		MATERIALS.add(TIN = new MaterialSet("Tin",ItemMaterials.ingotTin, ItemMaterials.dustTin, ItemMaterials.nuggetTin, ModBlocks.BlockTin, ItemMaterials.gearTin, ItemMaterials.plateTin));
		MATERIALS.add(COPPER = new MaterialSet("Copper",ItemMaterials.ingotCopper, ItemMaterials.dustCopper, ItemMaterials.nuggetCopper, ModBlocks.BlockCopper, ItemMaterials.gearCopper, ItemMaterials.plateCopper));	
		MATERIALS.add(SILVER = new MaterialSet("Silver",ItemMaterials.ingotSilver, ItemMaterials.dustSilver, ItemMaterials.nuggetSilver, ModBlocks.BlockSilver, ItemMaterials.gearSilver, ItemMaterials.plateSilver));
		MATERIALS.add(LEAD = new MaterialSet("Lead",ItemMaterials.ingotLead, ItemMaterials.dustLead, ItemMaterials.nuggetLead, ModBlocks.BlockLead, ItemMaterials.gearLead, ItemMaterials.plateLead));
		MATERIALS.add(NICKEL = new MaterialSet("Nickel",ItemMaterials.ingotNickel, ItemMaterials.dustNickel, ItemMaterials.nuggetNickel, ModBlocks.BlockNickel, ItemMaterials.gearNickel, ItemMaterials.plateNickel));	
		MATERIALS.add(PLATINUM = new MaterialSet("Platinum",ItemMaterials.ingotPlatinum, ItemMaterials.dustPlatinum, ItemMaterials.nuggetPlatinum, ModBlocks.BlockPlatinum, ItemMaterials.gearPlatinum, ItemMaterials.platePlatinum));	
		MATERIALS.add(ALUMINIUM = new MaterialSet("Aluminium",ItemMaterials.ingotAluminium, ItemMaterials.dustAluminium, ItemMaterials.nuggetAluminium, ModBlocks.BlockAluminium, ItemMaterials.gearAluminium, ItemMaterials.plateAluminium));	
		MATERIALS.add(INERT_INFUSION = new MaterialSet("InertInfusion",ItemMaterials.ingotInertInfusion, ItemMaterials.dustInertInfusion, ItemMaterials.nuggetInertInfusion, null, ItemMaterials.gearInertInfusion, ItemMaterials.plateInertInfusion));	
		MATERIALS.add(REDSTONE_ALLOY = new MaterialSet("RedstoneAlloy",ItemMaterials.ingotRedstoneAlloy, ItemMaterials.dustRedstoneAlloy, ItemMaterials.nuggetRedstoneAlloy, null, ItemMaterials.gearRedstoneAlloy, ItemMaterials.plateRedstoneAlloy));	
		
		MATERIALS.add(STATIC = new MaterialSet("Static",ItemMaterials.ingotStatic, ItemMaterials.dustStatic, ItemMaterials.nuggetStatic, ModBlocks.StaticBlock, ItemMaterials.gearStatic, ItemMaterials.plateStatic));
		MATERIALS.add(ENERGIZED = new MaterialSet("Energized",ItemMaterials.ingotEnergized, ItemMaterials.dustEnergized, ItemMaterials.nuggetEnergized, ModBlocks.EnergizedBlock, ItemMaterials.gearEnergized, ItemMaterials.plateEnergized));
		MATERIALS.add(LUMUM = new MaterialSet("Lumum",ItemMaterials.ingotLumum, ItemMaterials.dustLumum, ItemMaterials.nuggetLumum, ModBlocks.LumumBlock, ItemMaterials.gearLumum, ItemMaterials.plateLumum));	
	}
	public List<String> getOredictionarySet(String material) {
		List<String> temp = new ArrayList<String>();
		temp.add("ingot"+material);
		temp.add("dust"+material);
		temp.add("nugget"+material);
		temp.add("block"+material);
		temp.add("gear"+material);
		temp.add("plate"+material);
		return temp;
	}
}
