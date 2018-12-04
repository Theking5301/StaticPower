package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.FluidInfuserOutputWrapper;

public class InfuserRecipeRegistry {

	private static final InfuserRecipeRegistry INFUSER_BASE = new InfuserRecipeRegistry();
	
	private Map<Ingredient, FluidInfuserOutputWrapper> infusionList = new HashMap<Ingredient, FluidInfuserOutputWrapper>();
	
	public static InfuserRecipeRegistry Infusing() {
		return INFUSER_BASE;
	}	
	private InfuserRecipeRegistry() {
		
	}
	public void addRecipe(Ingredient input, ItemStack output, FluidStack requiredFluidStack){
		FluidInfuserOutputWrapper tempWrapper = new FluidInfuserOutputWrapper(input, output, requiredFluidStack);
		infusionList.put(input, tempWrapper);
	}
    public Map<Ingredient, FluidInfuserOutputWrapper> getInfusingRecipes() {
        return this.infusionList;
    }
	public FluidInfuserOutputWrapper getInfusingRecipe(ItemStack inputItemstack, FluidStack infuserfluidstack, boolean ignoreFluid) {
		for(Entry<Ingredient, FluidInfuserOutputWrapper> entry : infusionList.entrySet()) {
			if(entry.getValue().isSatisfied(inputItemstack, infuserfluidstack, ignoreFluid)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
