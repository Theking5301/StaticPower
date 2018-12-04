package theking530.staticpower.items;

import net.minecraft.item.ItemStack;

public class ItemMaterials extends MultiItem {
	
	public static ItemStack ingotCopper;
	public static ItemStack ingotTin;	
	public static ItemStack ingotSilver;	
	public static ItemStack ingotLead;
	public static ItemStack ingotPlatinum;	
	public static ItemStack ingotNickel;
	public static ItemStack ingotAluminium;	
	public static ItemStack ingotStatic;
	public static ItemStack ingotEnergized;
	public static ItemStack ingotLumum;
	public static ItemStack ingotInertInfusion;
	public static ItemStack ingotRedstoneAlloy;
	
	public static ItemStack nuggetCopper;
	public static ItemStack nuggetTin;	
	public static ItemStack nuggetSilver;	
	public static ItemStack nuggetLead;
	public static ItemStack nuggetPlatinum;	
	public static ItemStack nuggetNickel;
	public static ItemStack nuggetAluminium;	
	public static ItemStack nuggetStatic;
	public static ItemStack nuggetEnergized;
	public static ItemStack nuggetLumum;
	public static ItemStack nuggetInertInfusion;
	public static ItemStack nuggetRedstoneAlloy;
	
	public static ItemStack plateIron;
	public static ItemStack plateGold;	
	public static ItemStack plateCopper;
	public static ItemStack plateTin;	
	public static ItemStack plateSilver;	
	public static ItemStack plateLead;
	public static ItemStack platePlatinum;	
	public static ItemStack plateNickel;
	public static ItemStack plateAluminium;	
	public static ItemStack plateStatic;
	public static ItemStack plateEnergized;
	public static ItemStack plateLumum;
	public static ItemStack plateInertInfusion;
	public static ItemStack plateRedstoneAlloy;
	
	public static ItemStack dustCopper;
	public static ItemStack dustTin;	
	public static ItemStack dustSilver;	
	public static ItemStack dustLead;
	public static ItemStack dustPlatinum;	
	public static ItemStack dustNickel;
	public static ItemStack dustAluminium;	
	public static ItemStack dustStatic;
	public static ItemStack dustEnergized;
	public static ItemStack dustLumum;
	public static ItemStack dustInertInfusion;
	public static ItemStack dustRedstoneAlloy;
	public static ItemStack dustCoal;
	public static ItemStack dustObsidian;
	public static ItemStack dustGold;
	public static ItemStack dustIron;
	public static ItemStack dustRuby;
	public static ItemStack dustSapphire;
	
	public static ItemStack dustCobalt;
	public static ItemStack dustArdite;
	
	public static ItemStack gearCopper;
	public static ItemStack gearTin;	
	public static ItemStack gearSilver;	
	public static ItemStack gearLead;
	public static ItemStack gearPlatinum;	
	public static ItemStack gearNickel;
	public static ItemStack gearAluminium;	
	public static ItemStack gearStatic;
	public static ItemStack gearEnergized;
	public static ItemStack gearLumum;
	public static ItemStack gearInertInfusion;
	public static ItemStack gearRedstoneAlloy;
	public static ItemStack gearIron;
	public static ItemStack gearGold;
	
	public static ItemStack gemRuby;
	public static ItemStack gemSapphire;
	
	public static ItemStack dustSulfur;
	public static ItemStack dustSaltpeter;
	public static ItemStack dustCharcoal;
	public static ItemStack dustStaticInfusion;
	public static ItemStack dustEnergizedInfusion;
	public static ItemStack dustLumumInfusion;
	public static ItemStack silicon;
	public static ItemStack crystalStatic;
	public static ItemStack crystalEnergized;
	public static ItemStack crystalLumum;
	public static ItemStack dustWood;
	
	public ItemMaterials() {
		super("material");
	}
	@Override
	protected void registerSubItems() {
		ingotCopper = createSubOreItem(0, "ingotCopper");
		ingotTin = createSubOreItem(1, "ingotTin");
		ingotSilver = createSubOreItem(2, "ingotSilver");
		ingotLead = createSubOreItem(3, "ingotLead");
		ingotPlatinum = createSubOreItem(4, "ingotPlatinum");
		ingotNickel = createSubOreItem(5, "ingotNickel");
		ingotAluminium = createSubOreItem(6, "ingotAluminium");
		ingotStatic = createSubOreItem(7, "ingotStatic");
		ingotEnergized = createSubOreItem(8, "ingotEnergized");
		ingotLumum = createSubOreItem(9, "ingotLumum");
		ingotInertInfusion = createSubOreItem(10, "ingotInertInfusion");
		ingotRedstoneAlloy = createSubOreItem(11, "ingotRedstoneAlloy");
		
		nuggetCopper = createSubOreItem(50, "nuggetCopper");
		nuggetTin = createSubOreItem(51, "nuggetTin");
		nuggetSilver = createSubOreItem(52, "nuggetSilver");
		nuggetLead = createSubOreItem(53, "nuggetLead");
		nuggetPlatinum = createSubOreItem(54, "nuggetPlatinum");
		nuggetNickel = createSubOreItem(55, "nuggetNickel");
		nuggetAluminium = createSubOreItem(56, "nuggetAluminium");
		nuggetStatic = createSubOreItem(57, "nuggetStatic");
		nuggetEnergized = createSubOreItem(58, "nuggetEnergized");
		nuggetLumum = createSubOreItem(59, "nuggetLumum");
		nuggetInertInfusion = createSubOreItem(60, "nuggetInertInfusion");
		nuggetRedstoneAlloy = createSubOreItem(61, "nuggetRedstoneAlloy");
		
		plateCopper = createSubOreItem(100, "plateCopper");
		plateTin = createSubOreItem(101, "plateTin");
		plateSilver = createSubOreItem(102, "plateSilver");
		plateLead = createSubOreItem(103, "plateLead");
		platePlatinum = createSubOreItem(104, "platePlatinum");
		plateNickel = createSubOreItem(105, "plateNickel");
		plateAluminium = createSubOreItem(106, "plateAluminium");
		plateStatic = createSubOreItem(107, "plateStatic");
		plateEnergized = createSubOreItem(108, "plateEnergized");
		plateLumum = createSubOreItem(109, "plateLumum");
		plateInertInfusion = createSubOreItem(110, "plateInertInfusion");
		plateRedstoneAlloy = createSubOreItem(111, "plateRedstoneAlloy");
		plateIron = createSubOreItem(112, "plateIron");
		plateGold = createSubOreItem(113, "plateGold");
		
		dustCopper = createSubOreItem(150, "dustCopper");
		dustTin = createSubOreItem(151, "dustTin");
		dustSilver = createSubOreItem(152, "dustSilver");
		dustLead = createSubOreItem(153, "dustLead");
		dustPlatinum = createSubOreItem(154, "dustPlatinum");
		dustNickel = createSubOreItem(155, "dustNickel");
		dustAluminium = createSubOreItem(156, "dustAluminium");
		dustStatic = createSubOreItem(157, "dustStatic");
		dustEnergized = createSubOreItem(158, "dustEnergized");
		dustLumum = createSubOreItem(159, "dustLumum");
		dustInertInfusion = createSubOreItem(160, "dustInertInfusion");
		dustRedstoneAlloy = createSubOreItem(161, "dustRedstoneAlloy");
		dustIron = createSubOreItem(162, "dustIron");
		dustGold = createSubOreItem(163, "dustGold");
		dustCoal = createSubOreItem(164, "dustCoal");
		dustObsidian = createSubOreItem(165, "dustObsidian");
		dustRuby = createSubOreItem(166, "dustRuby");
		dustSapphire = createSubOreItem(167, "dustSapphire");

		gearCopper = createSubOreItem(200, "gearCopper");
		gearTin = createSubOreItem(201, "gearTin");
		gearSilver = createSubOreItem(202, "gearSilver");
		gearLead = createSubOreItem(203, "gearLead");
		gearPlatinum = createSubOreItem(204, "gearPlatinum");
		gearNickel = createSubOreItem(205, "gearNickel");
		gearAluminium = createSubOreItem(206, "gearAluminium");
		gearStatic = createSubOreItem(207, "gearStatic");
		gearEnergized = createSubOreItem(208, "gearEnergized");
		gearLumum = createSubOreItem(209, "gearLumum");
		gearInertInfusion = createSubOreItem(2010, "gearInertInfusion");
		gearRedstoneAlloy = createSubOreItem(211, "gearRedstoneAlloy");
		gearIron = createSubOreItem(212, "gearIron");
		gearGold = createSubOreItem(213, "gearGold");
		
		gemRuby = createSubOreItem(250, "gemRuby");
		gemSapphire = createSubOreItem(251, "gemSapphire");
		
		dustSulfur = createSubOreItem(280, "dustSulfur");
		dustSaltpeter = createSubOreItem(281, "dustSaltpeter");
		dustCharcoal = createSubOreItem(282, "dustCharcoal");
		dustStaticInfusion = createSubOreItem(283, "dustStaticInfusion");
		dustEnergizedInfusion = createSubOreItem(284, "dustEnergizedInfusion");
		dustLumumInfusion = createSubOreItem(285, "dustLumumInfusion");
		silicon = createSubOreItem(286, "itemSilicon");
		crystalStatic = createSubItem(287, "crystalStatic");
		crystalEnergized = createSubItem(288, "crystalEnergized");
		crystalLumum = createSubItem(289, "crystalLumum");
		dustWood = createSubItem(290, "dustWood");
	}
}
