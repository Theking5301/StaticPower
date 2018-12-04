package theking530.staticpower.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class ModMaterials {

	public static EquipmentMaterial STATIC;
	public static EquipmentMaterial ENERGIZED;
	public static EquipmentMaterial LUMUM;
	
	public static EquipmentMaterial LEATHER;
	public static EquipmentMaterial IRON;
	public static EquipmentMaterial GOLD;
	public static EquipmentMaterial COPPER;
	public static EquipmentMaterial TIN;
	public static EquipmentMaterial SILVER;
	public static EquipmentMaterial LEAD;
	public static EquipmentMaterial PLATINUM;
	public static EquipmentMaterial ALUMINIUM;
	public static EquipmentMaterial SAPPHIRE;
	public static EquipmentMaterial RUBY;
	
	public static EquipmentMaterial UNDEAD;
	public static EquipmentMaterial SKELETON;
	
	public static void init() {
		STATIC = new EquipmentMaterial("Static", EnumTextFormatting.GREEN, null, true, true);
		ENERGIZED = new EquipmentMaterial("Energized", EnumTextFormatting.AQUA, null, true, true);
		LUMUM = new EquipmentMaterial("Lumum", EnumTextFormatting.YELLOW, null, true, true);
		
		LEATHER = new EquipmentMaterial("Leather", EnumTextFormatting.GRAY, null, true, false);
		IRON = new EquipmentMaterial("Iron", EnumTextFormatting.GRAY, null, true, true);
		GOLD = new EquipmentMaterial("Gold", EnumTextFormatting.YELLOW, null, true, true);
		
		COPPER = new EquipmentMaterial("Copper", EnumTextFormatting.GOLD, null, true, true);
		TIN = new EquipmentMaterial("Tin", EnumTextFormatting.GRAY, null, true, true);
		SILVER = new EquipmentMaterial("Silver", EnumTextFormatting.WHITE, null, true, true);
		LEAD = new EquipmentMaterial("Lead", EnumTextFormatting.DARK_PURPLE, null, true, true);
		PLATINUM = new EquipmentMaterial("Platinum", EnumTextFormatting.DARK_AQUA, null, true, true);
		ALUMINIUM = new EquipmentMaterial("Aluminium", EnumTextFormatting.WHITE, null, true, true);
		SAPPHIRE = new EquipmentMaterial("Sapphire", EnumTextFormatting.BLUE, null, true, true);
		RUBY = new EquipmentMaterial("Ruby", EnumTextFormatting.RED, null, true, true);
		
		UNDEAD = new EquipmentMaterial("Undead", EnumTextFormatting.DARK_GREEN, null, true, false);
		SKELETON = new EquipmentMaterial("Skeleton", EnumTextFormatting.WHITE, null, true, false);
		
		initArmorValues();
		initToolValues();
	}
	private static void initArmorValues() {
		STATIC.initArmorMaterial(EnumHelper.addArmorMaterial("StaticMaterial", Reference.MOD_ID + ":StaticArmor", 30, new int[] {3, 6, 6, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));
		ENERGIZED.initArmorMaterial(EnumHelper.addArmorMaterial("EnergizedMaterial", Reference.MOD_ID + ":EnergizedArmor", 50, new int[] {5, 10, 8, 7}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F));
		LUMUM.initArmorMaterial(EnumHelper.addArmorMaterial("LumumMaterial", Reference.MOD_ID + ":LumumArmor", 70, new int[] {8, 13, 11, 10}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F));
		
		LEATHER.initArmorMaterial(EnumHelper.addArmorMaterial("LeatherMaterial", Reference.MOD_ID + ":LeatherArmor", 30, new int[] {1, 2, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F));
		IRON.initArmorMaterial(EnumHelper.addArmorMaterial("IronMaterial", Reference.MOD_ID + ":IronArmor", 40, new int[] {2, 2, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F));
		GOLD.initArmorMaterial(EnumHelper.addArmorMaterial("GoldMaterial", Reference.MOD_ID + ":GoldArmor", 30, new int[] {1, 3, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F));
		
		COPPER.initArmorMaterial(EnumHelper.addArmorMaterial("CopperMaterial", Reference.MOD_ID + ":CopperArmor", 30, new int[] {1, 2, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F));
		TIN.initArmorMaterial(EnumHelper.addArmorMaterial("TinMaterial", Reference.MOD_ID + ":TinArmor", 28, new int[] {2, 2, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F));
		SILVER.initArmorMaterial(EnumHelper.addArmorMaterial("SilverMaterial", Reference.MOD_ID + ":SilverArmor", 28, new int[] {1, 3, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F));
		LEAD.initArmorMaterial(EnumHelper.addArmorMaterial("LeadMaterial", Reference.MOD_ID + ":LeadArmor", 68, new int[] {3, 4, 3, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));
		PLATINUM.initArmorMaterial(EnumHelper.addArmorMaterial("PlatinumMaterial", Reference.MOD_ID + ":PlatinumArmor", 48, new int[] {3, 5, 4, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));
		ALUMINIUM.initArmorMaterial(EnumHelper.addArmorMaterial("AluminiumMaterial", Reference.MOD_ID + ":AluminiumArmor", 18, new int[] {1, 2, 1, 1}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F));
		RUBY.initArmorMaterial(EnumHelper.addArmorMaterial("RubyMaterial", Reference.MOD_ID + ":RubyArmor", 48, new int[] {5, 7, 6, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F));
		SAPPHIRE.initArmorMaterial(EnumHelper.addArmorMaterial("SapphireMaterial", Reference.MOD_ID + ":SapphireArmor", 48, new int[] {5, 7, 6, 4}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F));
	
		UNDEAD.initArmorMaterial(EnumHelper.addArmorMaterial("UndeadMaterial", Reference.MOD_ID + ":UndeadArmor", 28, new int[] {3, 4, 3, 2}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));
		SKELETON.initArmorMaterial(EnumHelper.addArmorMaterial("SkeletonMaterial", Reference.MOD_ID + ":SkeletonArmor", 28, new int[] {3, 4, 3, 2}, 35, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));
	}
	private static void initToolValues() {
		STATIC.initToolMaterial(EnumHelper.addToolMaterial("STATIC", 2, 1500, 7.0F, 3.0F, 20));
		ENERGIZED.initToolMaterial(EnumHelper.addToolMaterial("ENERGIZED", 2, 2500, 4.0F, 4.0F, 30));
		LUMUM.initToolMaterial(EnumHelper.addToolMaterial("LUMUM", 3, 4000, 12.0F, 5.0F, 40));
		
		IRON.initToolMaterial(EnumHelper.addToolMaterial("IRON", 1, 500, 5.0F, 3.0F, 15));
		GOLD.initToolMaterial(EnumHelper.addToolMaterial("GOLD", 2, 1000, 6.0F, 3.0F, 20));
		
		COPPER.initToolMaterial(EnumHelper.addToolMaterial("COPPER", 1, 500, 5.0F, 3.0F, 15));
		TIN.initToolMaterial(EnumHelper.addToolMaterial("TIN", 1, 500, 5.0F, 3.0F, 15));
		SILVER.initToolMaterial(EnumHelper.addToolMaterial("SILVER", 2, 1000, 6.0F, 3.0F, 20));
		LEAD.initToolMaterial(EnumHelper.addToolMaterial("LEAD", 2, 1500, 3.0F, 3.0F, 20));
		PLATINUM.initToolMaterial(EnumHelper.addToolMaterial("PLATINUM", 3, 2000, 4.0F, 5.0F, 30));
		ALUMINIUM.initToolMaterial(EnumHelper.addToolMaterial("ALUMINIUM", 1, 500, 3.0F, 3.0F, 15));
		RUBY.initToolMaterial(EnumHelper.addToolMaterial("RUBY", 2, 1500, 5.0F, 7.0F, 30));
		SAPPHIRE.initToolMaterial(EnumHelper.addToolMaterial("SAPPHIRE", 2, 1500, 7.0F, 5.0F, 30));
	}
	public static TextFormatting getMaterialColor(EquipmentMaterial material) {
		return EnumTextFormatting.WHITE;
	}
	public static String getMaterialAsString(EquipmentMaterial material) {
		return "Text";
	}
}
