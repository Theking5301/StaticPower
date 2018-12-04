package theking530.staticpower.handlers.crafting.wrappers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class LumberMillRecipeWrapper {

	private Ingredient input;
	private ItemStack output1;
	private ItemStack output2;
	private FluidStack outputFluid;
	
	public LumberMillRecipeWrapper(Ingredient input, ItemStack output1, ItemStack output2, FluidStack outputFluid) {
		this.input = input;
		this.output1 = output1;
		this.output2 = output2;
		this.outputFluid = outputFluid;
	}
	public Ingredient getInput() {
		return input;
	}
	public List<ItemStack> getOutputs() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		if(!output1.isEmpty()) {
			list.add(output1);
		}
		if(!output2.isEmpty()) {
			list.add(output2);
		}
		return list;
	}
	public ItemStack getMainOutput() {
		return output1;
	}
	public ItemStack getSecondaryOutput() {
		return output2;
	}
	public FluidStack getOutputFluid() {
		return outputFluid;
	}
	public boolean hasOutputFluid() {
		return outputFluid != null;
	}
	public boolean isSatisfied(ItemStack input) {
		if(input == null || input.isEmpty()) {
			return false;
		}
		return this.input.apply(input);
	}
}
