package theking530.staticpower.handlers.crafting.wrappers;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class FusionFurnaceRecipeWrapper {

	protected Ingredient INPUT1;
	protected Ingredient INPUT2;
	protected Ingredient INPUT3;
	protected Ingredient INPUT4;
	protected Ingredient INPUT5;
	
	protected ItemStack OUTPUT;
	
	public FusionFurnaceRecipeWrapper(ItemStack output, Ingredient...inputs) {
		if(inputs.length > 0) {
			if(inputs[0] != null) {
				INPUT1 = inputs[0];
			}	
		}
		if(inputs.length > 1) {
			if(inputs[1] != null) {
				INPUT2 = inputs[1];
			}	
		}
		if(inputs.length > 2) {
			if(inputs[2] != null) {
				INPUT3 = inputs[2];
			}	
		}
		if(inputs.length > 3) {
			if(inputs[3] != null) {
				INPUT4 = inputs[3];
			}	
		}
		if(inputs.length > 4) {
			if(inputs[4] != null) {
				INPUT5 = inputs[4];
			}	
		}
		OUTPUT = output;
	}	
	public ItemStack getOutputItem() {
		return OUTPUT;
	}
	public int getInputItemCount() {
		int tempCount = 0;
		if(INPUT1 != null) {
			tempCount++;
		}
		if(INPUT2 != null) {
			tempCount++;
		}
		if(INPUT3 != null) {
			tempCount++;
		}
		if(INPUT4 != null) {
			tempCount++;
		}
		if(INPUT5 != null) {
			tempCount++;
		}
		return tempCount;
	}
	public ArrayList<Ingredient> getInputs(){
		ArrayList<Ingredient> tempList = new ArrayList<Ingredient>();
		if(INPUT1 != null) {
			tempList.add(INPUT1);
		}
		if(INPUT2 != null) {
			tempList.add(INPUT2);
		}
		if(INPUT3 != null) {
			tempList.add(INPUT3);
		}
		if(INPUT4 != null) {
			tempList.add(INPUT4);
		}
		if(INPUT5 != null) {
			tempList.add(INPUT5);
		}
		return tempList;
	}
	public boolean isSatisfied(ItemStack... inputs) {	
		if(inputs.length < getInputItemCount()) {
			return false;
		}
		ArrayList<ItemStack> tempInputs = new ArrayList<ItemStack>();
		for(ItemStack item : inputs) {
			tempInputs.add(item);
		}
		
		for(Ingredient ingredient : getInputs()) {
			for(int k=tempInputs.size()-1; k >= 0; k--) {
				if(ingredient.apply(tempInputs.get(k))) {
					tempInputs.remove(k);
				}
			}
		}
		
		return tempInputs.size() == 0;
	}
}
