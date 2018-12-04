package theking530.staticpower.handlers.crafting.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import theking530.staticpower.handlers.crafting.wrappers.CentrifugeRecipeWrapper;

public class CentrifugeRecipeRegistry {
	
	private static final CentrifugeRecipeRegistry CENTRIFUGE_BASE = new CentrifugeRecipeRegistry();
	
	private Map<Ingredient, CentrifugeRecipeWrapper> centrifugingList = new HashMap<Ingredient, CentrifugeRecipeWrapper>();

	public static CentrifugeRecipeRegistry Centrifuging() {
		return CENTRIFUGE_BASE;
	}	
	private CentrifugeRecipeRegistry() {
		
	}
	public void addRecipe(Ingredient input, int minimumSpeed, ItemStack... outputs){
		if(outputs.length == 1) {
			putLists(input, new CentrifugeRecipeWrapper(input, minimumSpeed, outputs[0], ItemStack.EMPTY, ItemStack.EMPTY));
		}else if(outputs.length == 2) {
			putLists(input, new CentrifugeRecipeWrapper(input, minimumSpeed, outputs[0], outputs[1], ItemStack.EMPTY));
		}else if(outputs.length == 3){
			putLists(input, new CentrifugeRecipeWrapper(input, minimumSpeed, outputs[0], outputs[1], outputs[2]));
		}
	}
	public void putLists(Ingredient input, CentrifugeRecipeWrapper outputs){
		centrifugingList.put(input, outputs);
	}
    public Map<Ingredient, CentrifugeRecipeWrapper> getCentrifugingList() {
        return this.centrifugingList;
    }
	public ArrayList<ItemStack> getOutputs(ItemStack inputItem, int speed) {
		for(Entry<Ingredient, CentrifugeRecipeWrapper> entry : centrifugingList.entrySet()) {
			if(!inputItem.isEmpty() && entry.getValue().isSatisfied(inputItem, speed)) {
				return entry.getValue().getOutputItems();
			}
		}	
		return null;
	}
	public CentrifugeRecipeWrapper getRecipe(ItemStack inputItem, int speed) {
		for(Entry<Ingredient, CentrifugeRecipeWrapper> entry : centrifugingList.entrySet()) {
			if(!inputItem.isEmpty() && entry.getValue().isSatisfied(inputItem, speed)) {
				return entry.getValue();
			}
		}	
		return null;
	}
	public CentrifugeRecipeWrapper getRecipe(ItemStack inputItem) {
		return getRecipe(inputItem, Integer.MAX_VALUE);
	}
	public CentrifugeRecipeWrapper isValidInput(ItemStack inputItem) {
		for(Entry<Ingredient, CentrifugeRecipeWrapper> entry : centrifugingList.entrySet()) {
			if(!inputItem.isEmpty() && entry.getValue().getInput().apply((inputItem))) {
				return entry.getValue();
			}
		}	
		return null;
	}
}