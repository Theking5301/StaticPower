package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class FermenterOutputWrapper {

	private final FluidStack outputFluidStack;
	private final Ingredient inputIngredient;
	
	public FermenterOutputWrapper(Ingredient item, FluidStack fluid) {
		outputFluidStack = fluid;
		inputIngredient = item;
	}
	public FluidStack getOutputFluidStack(){
		return outputFluidStack;
	}
	public Ingredient getInputIngredient() {
		return inputIngredient;
	}
	public boolean isSatisfied(ItemStack input) {
		return inputIngredient.apply(input);
	}
}
