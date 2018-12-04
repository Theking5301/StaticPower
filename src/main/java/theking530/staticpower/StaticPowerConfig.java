package theking530.staticpower;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class StaticPowerConfig {
	
	public static boolean COPPER_ORE_GEN;
	public static boolean TIN_ORE_GEN;
	public static boolean LEAD_ORE_GEN;
	public static boolean SILVER_ORE_GEN;
	public static boolean PLATINUM_ORE_GEN;
	public static boolean NICKEL_ORE_GEN;
	public static boolean ALUMINIUM_ORE_GEN;
	public static boolean SAPPHIRE_ORE_GEN;
	public static boolean RUBY_ORE_GEN;	
	
	public static void updateConfig() {
		MinecraftForge.EVENT_BUS.register(StaticPower.instance);
		Configuration config = StaticPower.CONFIG;
		
		final String oreGen = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Ore Generation";
		config.addCustomCategoryComment(oreGen, "Disable ore generation by ore.");
		
		COPPER_ORE_GEN = config.getBoolean("Copper Ore", oreGen, true, "Disable or Enable Copper Ore Generation");
		TIN_ORE_GEN = config.getBoolean("Tin Ore", oreGen, true, "Disable or Enable Tin Ore Generation");
		LEAD_ORE_GEN = config.getBoolean("Lead Ore", oreGen, true, "Disable or Enable Lead Ore Generation");
		SILVER_ORE_GEN = config.getBoolean("Silver Ore", oreGen, true, "Disable or Enable Silver Ore Generation");
		PLATINUM_ORE_GEN = config.getBoolean("Platinum Ore", oreGen, true, "Disable or Enable Platinum Ore Generation");
		NICKEL_ORE_GEN = config.getBoolean("Nickel Ore", oreGen, true, "Disable or Enable Nickel Ore Generation");
		ALUMINIUM_ORE_GEN = config.getBoolean("Aluminium Ore", oreGen, true, "Disable or Enable Aluminium Ore Generation");
		SAPPHIRE_ORE_GEN = config.getBoolean("Sapphire Ore", oreGen, true, "Disable or Enable Sapphire Ore Generation");
		RUBY_ORE_GEN = config.getBoolean("Ruby Ore", oreGen, true, "Disable or Enable Ruby Ore Generation");
		
		if(StaticPower.CONFIG.hasChanged()) {
			StaticPower.CONFIG.save();
		}
	}	
}
