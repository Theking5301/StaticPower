package theking530.staticpower.handlers.crafting.wrappers;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CentrifugeRecipeWrapper {

	private final ItemStack outputItem1;
	private final ItemStack outputItem2;
	private final ItemStack outputItem3;
	private final Ingredient inputItem;
	private final int minimumSpeed;
	
	public CentrifugeRecipeWrapper(Ingredient input, int minimumSpeed, ItemStack output1, ItemStack output2, ItemStack output3) {
		outputItem1 = output1;
		outputItem2 = output2;
		outputItem3 = output3;
		inputItem = input;
		this.minimumSpeed = minimumSpeed;
	}
	
	public ArrayList<ItemStack> getOutputItems() {
		ArrayList<ItemStack> tempOutput = new ArrayList<ItemStack>();
		if(!outputItem1.isEmpty()) {
			tempOutput.add(outputItem1);
		}
		if(!outputItem2.isEmpty()) {
			tempOutput.add(outputItem2);
		}
		if(!outputItem3.isEmpty()) {
			tempOutput.add(outputItem3);
		}
		return tempOutput;
	}
	public Ingredient getInput() {
		return inputItem;
	}
	public int getOutputItemCount() {
		int tempCount = 0;
		if(!outputItem1.isEmpty()) {
			tempCount++;
		}
		if(!outputItem2.isEmpty()) {
			tempCount++;
		}
		if(!outputItem3.isEmpty()) {
			tempCount++;
		}
		return tempCount;
	}
	public int getMinimumSpeed() {
		return minimumSpeed;
	}
	public boolean isSatisfied(ItemStack input, int speed) {
		return inputItem.apply(input) && speed >= minimumSpeed;
	}
}
