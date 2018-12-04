package theking530.staticpower.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import theking530.staticpower.Registry;
import theking530.staticpower.items.ModMaterials;

public class ModArmor {

	
	public static Item StaticHelmet;
	public static Item StaticChestplate;
	public static Item StaticLeggings;
	public static Item StaticBoots;
	
	public static Item EnergizedHelmet;
	public static Item EnergizedChestplate;
	public static Item EnergizedLeggings;
	public static Item EnergizedBoots;
	
	public static Item LumumHelmet;
	public static Item LumumChestplate;
	public static Item LumumLeggings;
	public static Item LumumBoots;
	
	public static Item CopperHelmet;
	public static Item CopperChestplate;
	public static Item CopperLeggings;
	public static Item CopperBoots;
	
	public static Item TinHelmet;
	public static Item TinChestplate;
	public static Item TinLeggings;
	public static Item TinBoots;
	
	public static Item LeadHelmet;
	public static Item LeadChestplate;
	public static Item LeadLeggings;
	public static Item LeadBoots;
	
	public static Item SilverHelmet;
	public static Item SilverChestplate;
	public static Item SilverLeggings;
	public static Item SilverBoots;
	
	public static Item PlatinumHelmet;
	public static Item PlatinumChestplate;
	public static Item PlatinumLeggings;
	public static Item PlatinumBoots;
	
	public static Item AluminiumHelmet;
	public static Item AluminiumChestplate;
	public static Item AluminiumLeggings;
	public static Item AluminiumBoots;
	
	public static Item SapphireHelmet;
	public static Item SapphireChestplate;
	public static Item SapphireLeggings;
	public static Item SapphireBoots;
	
	public static Item RubyHelmet;
	public static Item RubyChestplate;
	public static Item RubyLeggings;
	public static Item RubyBoots;
	
	public static Item UndeadHelmet;
	public static Item UndeadChestplate;
	public static Item UndeadLeggings;
	public static Item UndeadBoots;
	
	public static Item SkeletonHelmet;
	public static Item SkeletonChestplate;
	public static Item SkeletonLeggings;
	public static Item SkeletonBoots;

	public static void init(Registry registry) {		
		
		SkeletonHelmet = new SkeletonArmor("SkeletonHelmet", BaseArmor.ArmorType.LIGHT, ModMaterials.SKELETON, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(SkeletonHelmet);	
		SkeletonChestplate = new SkeletonArmor("SkeletonChestplate", BaseArmor.ArmorType.LIGHT, ModMaterials.SKELETON, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(SkeletonChestplate);		
		SkeletonLeggings = new SkeletonArmor("SkeletonLeggings", BaseArmor.ArmorType.LIGHT, ModMaterials.SKELETON, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(SkeletonLeggings);		
		SkeletonBoots = new SkeletonArmor("SkeletonBoots", BaseArmor.ArmorType.LIGHT, ModMaterials.SKELETON, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(SkeletonBoots);
		
		
		UndeadHelmet = new UndeadArmor("UndeadHelmet", BaseArmor.ArmorType.LIGHT, ModMaterials.UNDEAD, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(UndeadHelmet);	
		UndeadChestplate = new UndeadArmor("UndeadChestplate", BaseArmor.ArmorType.LIGHT, ModMaterials.UNDEAD, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(UndeadChestplate);		
		UndeadLeggings = new UndeadArmor("UndeadLeggings", BaseArmor.ArmorType.LIGHT, ModMaterials.UNDEAD, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(UndeadLeggings);		
		UndeadBoots = new UndeadArmor("UndeadBoots", BaseArmor.ArmorType.LIGHT, ModMaterials.UNDEAD, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(UndeadBoots);
		
		StaticHelmet = new BaseArmor("StaticHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.STATIC, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(StaticHelmet);	
		StaticChestplate = new BaseArmor("StaticChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.STATIC, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(StaticChestplate);		
		StaticLeggings = new BaseArmor("StaticLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.STATIC, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(StaticLeggings);		
		StaticBoots = new BaseArmor("StaticBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.STATIC, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(StaticBoots);
		
		EnergizedHelmet = new BaseArmor("EnergizedHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.ENERGIZED, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(EnergizedHelmet);		
		EnergizedChestplate = new BaseArmor("EnergizedChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.ENERGIZED, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(EnergizedChestplate);		
		EnergizedLeggings = new BaseArmor("EnergizedLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.ENERGIZED, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(EnergizedLeggings);		
		EnergizedBoots = new BaseArmor("EnergizedBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.ENERGIZED,EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(EnergizedBoots);
		
		LumumHelmet = new BaseArmor("LumumHelmet", BaseArmor.ArmorType.HEAVY, ModMaterials.LUMUM, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(LumumHelmet);		
		LumumChestplate = new BaseArmor("LumumChestplate", BaseArmor.ArmorType.HEAVY, ModMaterials.LUMUM, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(LumumChestplate);		
		LumumLeggings = new BaseArmor("LumumLeggings", BaseArmor.ArmorType.HEAVY, ModMaterials.LUMUM,EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(LumumLeggings);		
		LumumBoots = new BaseArmor("LumumBoots", BaseArmor.ArmorType.HEAVY, ModMaterials.LUMUM, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(LumumBoots);
		
		CopperHelmet = new CopperArmor("sCopperHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.COPPER, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(CopperHelmet);		
		CopperChestplate = new CopperArmor("sCopperChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.COPPER, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(CopperChestplate);		
		CopperLeggings = new CopperArmor("sCopperLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.COPPER, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(CopperLeggings);		
		CopperBoots = new CopperArmor("sCopperBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.COPPER, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(CopperBoots);
		
		TinHelmet = new TinArmor("sTinHelmet", BaseArmor.ArmorType.LIGHT, ModMaterials.TIN,  EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(TinHelmet);		
		TinChestplate = new TinArmor("sTinChestplate", BaseArmor.ArmorType.LIGHT, ModMaterials.TIN, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(TinChestplate);		
		TinLeggings = new TinArmor("sTinLeggings", BaseArmor.ArmorType.LIGHT, ModMaterials.TIN, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(TinLeggings);		
		TinBoots = new TinArmor("sTinBoots", BaseArmor.ArmorType.LIGHT, ModMaterials.TIN, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(TinBoots);
		
		LeadHelmet = new LeadArmor("sLeadHelmet", BaseArmor.ArmorType.HEAVY, ModMaterials.LEAD, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(LeadHelmet);		
		LeadChestplate = new LeadArmor("sLeadChestplate", BaseArmor.ArmorType.HEAVY, ModMaterials.LEAD, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(LeadChestplate);		
		LeadLeggings = new LeadArmor("sLeadLeggings", BaseArmor.ArmorType.HEAVY, ModMaterials.LEAD, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(LeadLeggings);		
		LeadBoots = new LeadArmor("sLeadBoots", BaseArmor.ArmorType.HEAVY, ModMaterials.LEAD, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(LeadBoots);
		
		SilverHelmet = new SilverArmor("sSilverHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.SILVER, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(SilverHelmet);		
		SilverChestplate = new SilverArmor("sSilverChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.SILVER, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(SilverChestplate);		
		SilverLeggings = new SilverArmor("sSilverLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.SILVER, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(SilverLeggings);		
		SilverBoots = new SilverArmor("sSilverBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.SILVER,  EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(SilverBoots);
		
		PlatinumHelmet = new PlatinumArmor("sPlatinumHelmet", BaseArmor.ArmorType.HEAVY, ModMaterials.PLATINUM, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(PlatinumHelmet);		
		PlatinumChestplate = new PlatinumArmor("sPlatinumChestplate", BaseArmor.ArmorType.HEAVY, ModMaterials.PLATINUM, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(PlatinumChestplate);		
		PlatinumLeggings = new PlatinumArmor("sPlatinumLeggings", BaseArmor.ArmorType.HEAVY, ModMaterials.PLATINUM,  EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(PlatinumLeggings);		
		PlatinumBoots = new PlatinumArmor("sPlatinumBoots", BaseArmor.ArmorType.HEAVY, ModMaterials.PLATINUM, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(PlatinumBoots);
		
		AluminiumHelmet = new AluminiumArmor("sAluminiumHelmet", BaseArmor.ArmorType.LIGHT, ModMaterials.ALUMINIUM, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(AluminiumHelmet);		
		AluminiumChestplate = new AluminiumArmor("sAluminiumChestplate", BaseArmor.ArmorType.LIGHT, ModMaterials.ALUMINIUM, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(AluminiumChestplate);		
		AluminiumLeggings = new AluminiumArmor("sAluminiumLeggings", BaseArmor.ArmorType.LIGHT, ModMaterials.ALUMINIUM, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(AluminiumLeggings);		
		AluminiumBoots = new AluminiumArmor("sAluminiumBoots", BaseArmor.ArmorType.LIGHT, ModMaterials.ALUMINIUM, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(AluminiumBoots);
		
		SapphireHelmet = new SapphireArmor("sSapphireHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.SAPPHIRE, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(SapphireHelmet);		
		SapphireChestplate = new SapphireArmor("sSapphireChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.SAPPHIRE,  EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(SapphireChestplate);		
		SapphireLeggings = new SapphireArmor("sSapphireLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.SAPPHIRE, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(SapphireLeggings);		
		SapphireBoots = new SapphireArmor("sSapphireBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.SAPPHIRE, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(SapphireBoots);
		
		RubyHelmet = new RubyArmor("sRubyHelmet", BaseArmor.ArmorType.MEDIUM, ModMaterials.RUBY, EntityEquipmentSlot.HEAD);
		registry.PreRegisterItem(RubyHelmet);		
		RubyChestplate = new RubyArmor("sRubyChestplate", BaseArmor.ArmorType.MEDIUM, ModMaterials.RUBY, EntityEquipmentSlot.CHEST);
		registry.PreRegisterItem(RubyChestplate);		
		RubyLeggings = new RubyArmor("sRubyLeggings", BaseArmor.ArmorType.MEDIUM, ModMaterials.RUBY, EntityEquipmentSlot.LEGS);
		registry.PreRegisterItem(RubyLeggings);		
		RubyBoots = new RubyArmor("sRubyBoots", BaseArmor.ArmorType.MEDIUM, ModMaterials.RUBY, EntityEquipmentSlot.FEET);
		registry.PreRegisterItem(RubyBoots);
		
	}
}
