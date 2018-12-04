package theking530.staticpower.integration.thermalfoundation;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.assists.utilities.OreDictionaryUtilities;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.integration.ICompatibilityPlugin;
import theking530.staticpower.items.ItemMaterials;

public class PluginThermalFoundation implements ICompatibilityPlugin {

	private static boolean registered = false;
	
	@Override
	public void register() {
		if(isRegistered()) {
			return;
		}
		registered = true;
	}

	@Override
	public void preInit() {}	
	@Override
	public void init() {}	
	@Override
	public void postInit() {
		RegisterHelper.registerInfuserRecipe(OreDictionaryUtilities.getOreStack("ingotSignalum", 0), Craft.ing(ItemMaterials.ingotInertInfusion), new FluidStack(FluidRegistry.getFluid("redstone"), 250));
		RegisterHelper.registerInfuserRecipe(Craft.outputStack(ItemMaterials.ingotRedstoneAlloy), Craft.ing(ItemMaterials.ingotSilver), new FluidStack(FluidRegistry.getFluid("redstone"), 250));
		RegisterHelper.registerInfuserRecipe(OreDictionaryUtilities.getOreStack("ingotEnderium", 0), Craft.ing(ItemMaterials.ingotInertInfusion), new FluidStack(FluidRegistry.getFluid("ender"), 250));
	}
	
	@Override
	public boolean isRegistered() {
		return registered;
	}
	@Override
	public boolean shouldRegister() {
		return Loader.isModLoaded("thermalfoundation");
	}
	@Override
	public String getPluginName() {
		return "Thermal Foundation";
	}	
}
