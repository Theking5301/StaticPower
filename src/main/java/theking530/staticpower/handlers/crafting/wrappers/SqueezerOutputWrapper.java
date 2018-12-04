package theking530.staticpower.handlers.crafting.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SqueezerOutputWrapper {
	
	private final ItemStack outputItem;
	private final FluidStack outputFluid;
	private final ItemStack inputItem;
	
	public SqueezerOutputWrapper(ItemStack input, ItemStack output, FluidStack fluid) {
		outputItem = output;
		outputFluid = fluid;
		inputItem = input;
	}
	
	public ItemStack getOutputItem() {
		return outputItem;
	}
	public ItemStack getInputItem() {
		return inputItem;
	}
	public FluidStack getOutputFluid() {
		return outputFluid;
	}
}
