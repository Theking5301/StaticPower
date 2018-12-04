package theking530.staticpower.handlers.crafting.wrappers;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class EsotericEnchanterRecipeWrapper {

	private final Ingredient inputIngredient1;
	private final Ingredient inputIngredient2;
	private final Ingredient inputIngredient3;
	private final FluidStack inputFluidStack;
	
	private final ItemStack outputItem;
	
	public EsotericEnchanterRecipeWrapper(ItemStack outputItem, Ingredient input1, Ingredient input2, FluidStack inputFluid) {
		this(outputItem, input1, input2, null, inputFluid);
	}
	public EsotericEnchanterRecipeWrapper(ItemStack outputItem, Ingredient input1, Ingredient input2, Ingredient input3, FluidStack inputFluid) {
		inputIngredient1 = input1;
		inputIngredient2 = input2;
		inputIngredient3 = input3;
		inputFluidStack = inputFluid;
		
		this.outputItem = outputItem;
	}

	public Ingredient getIngredient1() {
		return inputIngredient1;
	}
	public Ingredient getIngredient2() {
		return inputIngredient2;
	}
	public Ingredient getIngredient3() {
		return inputIngredient3;
	}
	public ItemStack getOutputItemStack() {
		return outputItem;
	}
	public FluidStack getInputFluidStack() {
		return inputFluidStack;
	}
	public boolean isSatisfied(ItemStack input1, ItemStack input2, ItemStack input3, FluidStack inputFluid, boolean ignoreFluid) {
		if(inputIngredient1 != null && !input1.isEmpty() && inputIngredient1.apply(input1)) {
			ArrayList<ItemStack> tempInputs = new ArrayList<ItemStack>();
			tempInputs.add(input1);
			tempInputs.add(input2);
			
			if(inputIngredient2 != null) {
				if(tempInputs.size() <= 0) {
					return false;
				}
				for(int k=tempInputs.size()-1; k >= 0; k--) {
					if(inputIngredient2.apply(tempInputs.get(k))) {
						tempInputs.remove(k);
						break;
					}
				}
			}
			if(inputIngredient3 != null) {
				if(tempInputs.size() <= 0) {
					return false;
				}
				for(int k=tempInputs.size()-1; k >= 0; k--) {
					if(inputIngredient3.apply(tempInputs.get(k))) {
						tempInputs.remove(k);
						break;
					}
				}
			}
			
			if(tempInputs.size() == 0 && inputFluid != null) {
				if(ignoreFluid || (inputFluid.isFluidEqual(inputFluidStack) && inputFluid.amount >= inputFluidStack.amount)) {
					return true;
				}
			}
		}
		return false;
	}
}
