package theking530.staticpower.integration.JEI.fluidgenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import api.gui.GuiDrawUtilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.FluidGeneratorRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FluidGeneratorRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class FluidGeneratorRecipeCategory extends BaseJEIRecipeCategory<JEIFluidGeneratorRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    
	    public FluidGeneratorRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Fluid Generator";
	        background = guiHelper.createBlankDrawable(176, 60);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(FluidGeneratorRecipeWrapper.class, recipe -> new JEIFluidGeneratorRecipeWrapper(recipe), PluginJEI.FLUID_GENETATOR_UID); 
	        registry.addRecipes(FluidGeneratorRecipeRegistry.Generating().getGeneratingRecipes().values(), PluginJEI.FLUID_GENETATOR_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.FluidGenerator)), PluginJEI.FLUID_GENETATOR_UID);  
	    	currentPower = 0;
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.FLUID_GENETATOR_UID;
	    }

	    @Override
	    @Nonnull
	    public String getTitle() {
	        return locTitle;
	    }

	    @Override
	    public String getModName() {
	        return Reference.MOD_NAME;
	    }

	    @Override
	    @Nonnull
	    public IDrawable getBackground() {
	        return background;
	    }
	    @Override
	    public void drawExtras(@Nonnull Minecraft minecraft) {
	    	GuiDrawUtilities.drawSlot(96, 2, 16, 60);
	    	
	    	GuiPowerBarUtilities.drawPowerBar(68, 62, 16, 60, 1.0f, currentPower, 10000);
	    	GuiDrawUtilities.drawSlot(68, 2, 16, 60);
	    	
	    	currentPower += 2;
	    	if(currentPower >= 10000) {
	    		currentPower = 0;
	    	}
	    }
	    @Override
		public List<String> getTooltipStrings(int mouseX, int mouseY) {
	    	if(mouseX >= 0 && mouseX <= 0 + 6 && mouseY >= 2 && mouseY <= 62) {
	    		List<String> temp = new ArrayList<String>();
	    		temp.add("Energy:");
	    		temp.add(GuiUtilities.formatIntegerWithCommas(1000) + " RF");
	    		return temp;
	    	}
			return Collections.emptyList();
		}
	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEIFluidGeneratorRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        //Output
	        guiFluidStacks.init(0, true, 96, 2, 16, 60, 1, false, null);
	        
	        guiFluidStacks.set(ingredients);
	    }
}
