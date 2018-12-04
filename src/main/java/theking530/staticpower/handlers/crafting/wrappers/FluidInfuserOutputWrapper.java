package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class FluidInfuserOutputWrapper {
	private final Ingredient inputIngredient;
	private final ItemStack outputItem;
	private final FluidStack requiredFluid;
	
	public FluidInfuserOutputWrapper(Ingredient input, ItemStack output, FluidStack fluid) {
		inputIngredient = input;
		outputItem = output;
		requiredFluid = fluid;
	}
	public Ingredient getInputItemStack() {
		return inputIngredient;
	}
	public ItemStack getOutputItemStack() {
		return outputItem;
	}
	public FluidStack getRequiredFluidStack() {
		return requiredFluid;
	}
	
	public boolean isSatisfied(ItemStack inputItem, FluidStack inputFluidStack, boolean ignoreFluid) {
		if(inputIngredient.apply(inputItem)) {
			if(ignoreFluid || (inputFluidStack != null && inputFluidStack.isFluidEqual(requiredFluid) && inputFluidStack.amount >= requiredFluid.amount)) {
				return true;
			}
		}
		return false;
	}
}

