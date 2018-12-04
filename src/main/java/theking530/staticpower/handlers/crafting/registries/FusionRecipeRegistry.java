package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import theking530.staticpower.handlers.crafting.wrappers.FusionFurnaceRecipeWrapper;

public class FusionRecipeRegistry {

	private static final FusionRecipeRegistry FUSION_BASE = new FusionRecipeRegistry();
	
	private Map<ItemStack, FusionFurnaceRecipeWrapper> fusionList = new HashMap<ItemStack, FusionFurnaceRecipeWrapper>();

	public static FusionRecipeRegistry Fusing() {
		return FUSION_BASE;
	}	
	public void addRecipe(ItemStack output, Ingredient... inputs){
		FusionFurnaceRecipeWrapper tempWrapper = new FusionFurnaceRecipeWrapper(output, inputs);	
		fusionList.put(output, tempWrapper);
	}
    public Map<ItemStack, FusionFurnaceRecipeWrapper> getFusionList() {
        return this.fusionList;
    }
	public FusionFurnaceRecipeWrapper getFusionResult(ItemStack... inputs) {
		for(FusionFurnaceRecipeWrapper wrapper : fusionList.values()) {
			if(wrapper.isSatisfied(inputs)) {
				return wrapper;
			}
		}
		return null;
	}
}
