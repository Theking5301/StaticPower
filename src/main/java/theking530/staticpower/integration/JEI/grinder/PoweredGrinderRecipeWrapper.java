package theking530.staticpower.integration.JEI.grinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.handlers.crafting.wrappers.GrinderOutputWrapper;

public class PoweredGrinderRecipeWrapper implements IRecipeWrapper{

	public GrinderOutputWrapper recipe;
	
	public PoweredGrinderRecipeWrapper(GrinderOutputWrapper recipe) {
	    this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack inputItem : recipe.getInputItem().getMatchingStacks()) {
			inputs.add(inputItem);
		}
		inputLists.add(inputs);
	    
		List<ItemStack> output = new LinkedList<ItemStack>();
		for(int i=0; i<recipe.getOutputItems().size(); i++) {
			output.add(recipe.getOutputItems().get(i).getOutput());
		}	        
		
		ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setOutputs(ItemStack.class, output);
	}
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		for(int i=0; i<recipe.getOutputItems().size(); i++) {
			if (recipe.getOutputItems().get(i).getPercentage() > 0) {
				String dispChance = GuiUtilities.formatIntegerWithCommas((int) (recipe.getOutputItems().get(i).getPercentage()*100.0f)) + "%";
				int xPos = 0;
				int yPos = 0;
				
				if(i==0) {
					xPos = 59; 
					yPos = 73;
				}else if(i==1) {
					xPos = 85;
					yPos = 59;
				}else{
					xPos = 34;
					yPos = 59;
				}		
				minecraft.fontRenderer.drawString(dispChance, xPos - minecraft.fontRenderer.getStringWidth(dispChance)/2, yPos, GuiUtilities.getColor(150, 150, 150));
			}
		}
	}
}
