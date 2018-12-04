package theking530.staticpower.handlers.crafting.registries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;

public class SqueezerRecipeRegistry {

	private static final SqueezerRecipeRegistry SQUEEZER_BASE = new SqueezerRecipeRegistry();

	private Map<ItemStack, SqueezerOutputWrapper> SQUEEZE_MAP = new HashMap<ItemStack, SqueezerOutputWrapper>();
	
	public static SqueezerRecipeRegistry Squeezing() {
		return SQUEEZER_BASE;
	}	
	public void addRecipe(ItemStack input, ItemStack output, FluidStack outputFluidStack){
		SQUEEZE_MAP.put(input, new SqueezerOutputWrapper(input, output, outputFluidStack));
	}
	public Map<ItemStack, SqueezerOutputWrapper> getSqueezingRecipes() {
		return SQUEEZE_MAP;
	}
	public SqueezerOutputWrapper getSqueezingRecipe(ItemStack input) {
	    Iterator<Entry<ItemStack, SqueezerOutputWrapper>> iterator = SQUEEZE_MAP.entrySet().iterator();
	    Entry<ItemStack, SqueezerOutputWrapper> pair;
	    do {
	    	if(!iterator.hasNext()) {
	    		return null;
	    	}
	    	pair = iterator.next();
	    }while(!isValidRecipe(pair, input));
		return pair.getValue();
	}
	public boolean isValidRecipe(Entry<?, ?> entry, ItemStack input) {
		if(entry == null) {
			return false;
		}
		ItemStack recipeInput = (ItemStack)entry.getKey();
		
		if(input != null && recipeInput != null) {
			if(input.isItemEqual(recipeInput)) {
				return true;
			}
		}
		return false;
	}
}
