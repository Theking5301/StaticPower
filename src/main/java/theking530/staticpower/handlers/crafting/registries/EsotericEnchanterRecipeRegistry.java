package theking530.staticpower.handlers.crafting.registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.EsotericEnchanterRecipeWrapper;

public class EsotericEnchanterRecipeRegistry {

	private static final EsotericEnchanterRecipeRegistry ENCHANTER_BASE = new EsotericEnchanterRecipeRegistry();
	
	private List<EsotericEnchanterRecipeWrapper> enchantingList = new ArrayList<EsotericEnchanterRecipeWrapper>();
	
	public static EsotericEnchanterRecipeRegistry Enchanting() {
		return ENCHANTER_BASE;
	}	
	private EsotericEnchanterRecipeRegistry() {
		
	}
	public void addRecipe(ItemStack output, Ingredient input1, Ingredient input2, Ingredient input3, FluidStack requiredFluidStack){
		EsotericEnchanterRecipeWrapper tempWrapper = new EsotericEnchanterRecipeWrapper(output, input1, input2, input3, requiredFluidStack);
		enchantingList.add(tempWrapper);
	}
	public void addRecipe(ItemStack output, Ingredient input1, Ingredient input2, FluidStack requiredFluidStack){
		EsotericEnchanterRecipeWrapper tempWrapper = new EsotericEnchanterRecipeWrapper(output, input1, input2, requiredFluidStack);
		enchantingList.add(tempWrapper);
	}
    public List<EsotericEnchanterRecipeWrapper> getEnchantingRecipes() {
        return enchantingList;
    }
	public EsotericEnchanterRecipeWrapper getEnchantingRecipe(ItemStack input1, ItemStack input2, ItemStack input3, FluidStack inputFluidStack, boolean ignoreFluid) {
		for(EsotericEnchanterRecipeWrapper recipe : enchantingList) {
			if(recipe.isSatisfied(input1, input2, input3, inputFluidStack, ignoreFluid)) {
				return recipe;
			}
		}
		return null;
	}
}
