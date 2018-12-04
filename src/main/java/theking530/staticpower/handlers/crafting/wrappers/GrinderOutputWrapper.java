package theking530.staticpower.handlers.crafting.wrappers;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;

public class GrinderOutputWrapper {

	private final GrinderOutput outputItem1;
	private final GrinderOutput outputItem2;
	private final GrinderOutput outputItem3;
	private final Ingredient inputItem;
	
	public GrinderOutputWrapper(Ingredient input, GrinderOutput output1, GrinderOutput output2, GrinderOutput output3) {
		outputItem1 = output1;
		outputItem2 = output2;
		outputItem3 = output3;
		inputItem = input;
	}
	
	public ArrayList<GrinderOutput> getOutputItems() {
		ArrayList<GrinderOutput> tempOutput = new ArrayList<GrinderOutput>();
		if(outputItem1.isValid()) {
			tempOutput.add(outputItem1);
		}
		if(outputItem2.isValid()) {
			tempOutput.add(outputItem2);
		}
		if(outputItem3.isValid()) {
			tempOutput.add(outputItem3);
		}
		return tempOutput;
	}
	public Ingredient getInputItem() {
		return inputItem;
	}
	public int getOutputItemCount() {
		int tempCount = 0;
		if(outputItem1.isValid()) {
			tempCount++;
		}
		if(outputItem2.isValid()) {
			tempCount++;
		}
		if(outputItem3.isValid()) {
			tempCount++;
		}
		return tempCount;
	}
	public float getItemChance(ItemStack item) {
		if(outputItem1.isValid() && outputItem1.getOutput().isItemEqual(item)) {
			return outputItem1.PERCENTAGE;
		}
		if(outputItem2.isValid() && outputItem2.getOutput().isItemEqual(item)) {
			return outputItem2.PERCENTAGE;
		}
		if(outputItem3.isValid() && outputItem3.getOutput().isItemEqual(item)) {
			return outputItem3.PERCENTAGE;
		}
		return 0f;
	}
	public boolean isSatisfied(ItemStack input) {
		return !input.isEmpty() && inputItem.apply(input);
	}
	public static GrinderOutput getnullOutput() {
		return new GrinderOutput(null, 0, 0.0f);
	}
	public static class GrinderOutput {
		private final ItemStack OUTPUT_ITEM;
		private final float PERCENTAGE;
		
		public GrinderOutput(ItemStack output) {
			this(output, 1.0f);
		}
		public GrinderOutput(ItemStack output, float percentage) {
			OUTPUT_ITEM = output;
			PERCENTAGE = percentage;
		}
		public GrinderOutput(String output) {
			this(output, 1, 1.0f);
		}
		public GrinderOutput(String output, float percentage) {
			this(output, 1, percentage);
		}
		public GrinderOutput(String output, int count) {
			this(output, count, 1.0f);
		}
		public GrinderOutput(String output, int count, float percentage) {
			if(OreDictionary.doesOreNameExist(output)) {
				OUTPUT_ITEM = OreDictionary.getOres(output).get(0).copy();
				OUTPUT_ITEM.setCount(count);
				PERCENTAGE = percentage;
			}else{
				PERCENTAGE = 0.0f;
				OUTPUT_ITEM = null;
			}
		}
		public boolean isValid() {
			if(OUTPUT_ITEM != null) {
				return true;
			}
			return false;
		}
		public ItemStack getOutput() {
			return OUTPUT_ITEM;
		}
		public float getPercentage() {
			return PERCENTAGE;
		}
	}
	
}