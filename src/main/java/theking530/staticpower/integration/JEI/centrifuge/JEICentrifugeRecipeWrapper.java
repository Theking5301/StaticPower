package theking530.staticpower.integration.JEI.centrifuge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.handlers.crafting.wrappers.CentrifugeRecipeWrapper;

public class JEICentrifugeRecipeWrapper implements IRecipeWrapper{

	public CentrifugeRecipeWrapper recipe;
	
	public JEICentrifugeRecipeWrapper(CentrifugeRecipeWrapper recipe) {
	    this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputLists = new ArrayList<List<ItemStack>>();
		
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for(ItemStack inputItem : recipe.getInput().getMatchingStacks()) {
			inputs.add(inputItem);
		}
		inputLists.add(inputs);
	    
		List<ItemStack> output = new LinkedList<ItemStack>();
		for(int i=0; i<recipe.getOutputItems().size(); i++) {
			output.add(recipe.getOutputItems().get(i));
		}	        
		
		ingredients.setInputLists(ItemStack.class, inputLists);
	    ingredients.setOutputs(ItemStack.class, output);
	}
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String minSpeed = recipe.getMinimumSpeed() + " " + I18n.format("gui.RPM");
		minecraft.fontRenderer.drawString( minSpeed, 50 - minecraft.fontRenderer.getStringWidth(minSpeed)/2, 24, GuiUtilities.getColor(50, 50, 50));
	}
}
