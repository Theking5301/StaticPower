package theking530.staticpower.items.tools.basictools;

import net.minecraft.item.Item;
import theking530.staticpower.Registry;
import theking530.staticpower.items.ModMaterials;

public class ModTools {
	
	public static Item CopperPickaxe;
	public static Item CopperAxe;
	public static Item CopperShovel;
	public static Item CopperHoe;
	public static Item CopperSword;
	
	public static Item SilverPickaxe;
	public static Item SilverAxe;
	public static Item SilverShovel;
	public static Item SilverHoe;
	public static Item SilverSword;
	
	public static Item TinPickaxe;
	public static Item TinAxe;
	public static Item TinShovel;
	public static Item TinHoe;
	public static Item TinSword;
	
	public static Item LeadPickaxe;
	public static Item LeadAxe;
	public static Item LeadShovel;
	public static Item LeadHoe;
	public static Item LeadSword;
	
	public static Item PlatinumPickaxe;
	public static Item PlatinumAxe;
	public static Item PlatinumShovel;
	public static Item PlatinumHoe;
	public static Item PlatinumSword;
	
	public static Item StaticPickaxe;
	public static Item StaticAxe;
	public static Item StaticShovel;
	public static Item StaticHoe;
	public static Item StaticSword;
	
	public static Item EnergizedPickaxe;
	public static Item EnergizedAxe;
	public static Item EnergizedShovel;
	public static Item EnergizedHoe;
	public static Item EnergizedSword;
	
	public static Item LumumPickaxe;
	public static Item LumumAxe;
	public static Item LumumShovel;
	public static Item LumumHoe;
	public static Item LumumSword;
	
	public static Item SapphirePickaxe;
	public static Item SapphireAxe;
	public static Item SapphireShovel;
	public static Item SapphireHoe;
	public static Item SapphireSword;
	
	public static Item RubyPickaxe;
	public static Item RubyAxe;
	public static Item RubyShovel;
	public static Item RubyHoe;
	public static Item RubySword;
	
	public static void init(Registry registry) {
		CopperPickaxe = new BasePickaxe(ModMaterials.COPPER, "sCopperPickaxe");
		registry.PreRegisterItem(CopperPickaxe);	
		CopperAxe = new BaseAxe(ModMaterials.COPPER, "sCopperAxe");
		registry.PreRegisterItem(CopperAxe);	
		CopperHoe = new BaseHoe(ModMaterials.COPPER, "sCopperHoe");
		registry.PreRegisterItem(CopperHoe);	
		CopperShovel = new BaseShovel(ModMaterials.COPPER, "sCopperShovel");
		registry.PreRegisterItem(CopperShovel);	
		CopperSword = new BaseSword(ModMaterials.COPPER, "sCopperSword");
		registry.PreRegisterItem(CopperSword);	
		
		TinPickaxe = new BasePickaxe(ModMaterials.TIN, "sTinPickaxe");
		registry.PreRegisterItem(TinPickaxe);	
		TinAxe = new BaseAxe(ModMaterials.TIN, "sTinAxe");
		registry.PreRegisterItem(TinAxe);	
		TinHoe = new BaseHoe(ModMaterials.TIN, "sTinHoe");
		registry.PreRegisterItem(TinHoe);	
		TinShovel = new BaseShovel(ModMaterials.TIN, "sTinShovel");
		registry.PreRegisterItem(TinShovel);	
		TinSword = new BaseSword(ModMaterials.TIN, "sTinSword");
		registry.PreRegisterItem(TinSword);	
		
		SilverPickaxe = new BasePickaxe(ModMaterials.SILVER, "sSilverPickaxe");
		registry.PreRegisterItem(SilverPickaxe);	
		SilverAxe = new BaseAxe(ModMaterials.SILVER, "sSilverAxe");
		registry.PreRegisterItem(SilverAxe);	
		SilverHoe = new BaseHoe(ModMaterials.SILVER, "sSilverHoe");
		registry.PreRegisterItem(SilverHoe);	
		SilverShovel = new BaseShovel(ModMaterials.SILVER, "sSilverShovel");
		registry.PreRegisterItem(SilverShovel);	
		SilverSword = new BaseSword(ModMaterials.SILVER, "sSilverSword");
		registry.PreRegisterItem(SilverSword);	
		
		LeadPickaxe = new BasePickaxe(ModMaterials.LEAD, "sLeadPickaxe");
		registry.PreRegisterItem(LeadPickaxe);	
		LeadAxe = new BaseAxe(ModMaterials.LEAD, "sLeadAxe");
		registry.PreRegisterItem(LeadAxe);	
		LeadHoe = new BaseHoe(ModMaterials.LEAD, "sLeadHoe");
		registry.PreRegisterItem(LeadHoe);	
		LeadShovel = new BaseShovel(ModMaterials.LEAD, "sLeadShovel");
		registry.PreRegisterItem(LeadShovel);	
		LeadSword = new BaseSword(ModMaterials.LEAD, "sLeadSword");
		registry.PreRegisterItem(LeadSword);	
		
		PlatinumPickaxe = new BasePickaxe(ModMaterials.PLATINUM, "sPlatinumPickaxe");
		registry.PreRegisterItem(PlatinumPickaxe);	
		PlatinumAxe = new BaseAxe(ModMaterials.PLATINUM, "sPlatinumAxe");
		registry.PreRegisterItem(PlatinumAxe);	
		PlatinumHoe = new BaseHoe(ModMaterials.PLATINUM, "sPlatinumHoe");
		registry.PreRegisterItem(PlatinumHoe);	
		PlatinumShovel = new BaseShovel(ModMaterials.PLATINUM, "sPlatinumShovel");
		registry.PreRegisterItem(PlatinumShovel);	
		PlatinumSword = new BaseSword(ModMaterials.PLATINUM, "sPlatinumSword");
		registry.PreRegisterItem(PlatinumSword);	
		
		StaticPickaxe = new BasePickaxe(ModMaterials.STATIC, "sStaticPickaxe");
		registry.PreRegisterItem(StaticPickaxe);	
		StaticAxe = new BaseAxe(ModMaterials.STATIC, "sStaticAxe");
		registry.PreRegisterItem(StaticAxe);	
		StaticHoe = new BaseHoe(ModMaterials.STATIC, "sStaticHoe");
		registry.PreRegisterItem(StaticHoe);	
		StaticShovel = new BaseShovel(ModMaterials.STATIC, "sStaticShovel");
		registry.PreRegisterItem(StaticShovel);	
		StaticSword = new BaseSword(ModMaterials.STATIC, "sStaticSword");
		registry.PreRegisterItem(StaticSword);	
		
		EnergizedPickaxe = new BasePickaxe(ModMaterials.ENERGIZED, "sEnergizedPickaxe");
		registry.PreRegisterItem(EnergizedPickaxe);	
		EnergizedAxe = new BaseAxe(ModMaterials.ENERGIZED, "sEnergizedAxe");
		registry.PreRegisterItem(EnergizedAxe);	
		EnergizedHoe = new BaseHoe(ModMaterials.ENERGIZED, "sEnergizedHoe");
		registry.PreRegisterItem(EnergizedHoe);	
		EnergizedShovel = new BaseShovel(ModMaterials.ENERGIZED, "sEnergizedShovel");
		registry.PreRegisterItem(EnergizedShovel);	
		EnergizedSword = new BaseSword(ModMaterials.ENERGIZED, "sEnergizedSword");
		registry.PreRegisterItem(EnergizedSword);	
		
		LumumPickaxe = new BasePickaxe(ModMaterials.LUMUM, "sLumumPickaxe");
		registry.PreRegisterItem(LumumPickaxe);	
		LumumAxe = new BaseAxe(ModMaterials.LUMUM, "sLumumAxe");
		registry.PreRegisterItem(LumumAxe);	
		LumumHoe = new BaseHoe(ModMaterials.LUMUM, "sLumumHoe");
		registry.PreRegisterItem(LumumHoe);	
		LumumShovel = new BaseShovel(ModMaterials.LUMUM, "sLumumShovel");
		registry.PreRegisterItem(LumumShovel);	
		LumumSword = new BaseSword(ModMaterials.LUMUM, "sLumumSword");
		registry.PreRegisterItem(LumumSword);
		
		SapphirePickaxe = new BasePickaxe(ModMaterials.SAPPHIRE, "sSapphirePickaxe");
		registry.PreRegisterItem(SapphirePickaxe);	
		SapphireAxe = new BaseAxe(ModMaterials.SAPPHIRE, "sSapphireAxe");
		registry.PreRegisterItem(SapphireAxe);	
		SapphireHoe = new BaseHoe(ModMaterials.SAPPHIRE, "sSapphireHoe");
		registry.PreRegisterItem(SapphireHoe);	
		SapphireShovel = new BaseShovel(ModMaterials.SAPPHIRE, "sSapphireShovel");
		registry.PreRegisterItem(SapphireShovel);	
		SapphireSword = new BaseSword(ModMaterials.SAPPHIRE, "sSapphireSword");
		registry.PreRegisterItem(SapphireSword);	
		
		RubyPickaxe = new BasePickaxe(ModMaterials.RUBY, "sRubyPickaxe");
		registry.PreRegisterItem(RubyPickaxe);	
		RubyAxe = new BaseAxe(ModMaterials.RUBY, "sRubyAxe");
		registry.PreRegisterItem(RubyAxe);	
		RubyHoe = new BaseHoe(ModMaterials.RUBY, "sRubyHoe");
		registry.PreRegisterItem(RubyHoe);	
		RubyShovel = new BaseShovel(ModMaterials.RUBY, "sRubyShovel");
		registry.PreRegisterItem(RubyShovel);	
		RubySword = new BaseSword(ModMaterials.RUBY, "sRubySword");
		registry.PreRegisterItem(RubySword);			
	}
}
