package theking530.staticpower.integration.JEI.fluidgenerator;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.handlers.crafting.wrappers.FluidGeneratorRecipeWrapper;

public class JEIFluidGeneratorRecipeWrapper implements IRecipeWrapper{
	
	private FluidGeneratorRecipeWrapper recipe;
	
	public JEIFluidGeneratorRecipeWrapper(FluidGeneratorRecipeWrapper recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
	    ingredients.setInput(FluidStack.class, recipe.getFluid());
	}
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {		
		String powerOutput = recipe.getPowerPerTick() + I18n.format("gui.RF/T");
		minecraft.fontRenderer.drawString(powerOutput, 30 - minecraft.fontRenderer.getStringWidth(powerOutput)/2, 40, GuiUtilities.getColor(50, 50, 50));
	}
}
