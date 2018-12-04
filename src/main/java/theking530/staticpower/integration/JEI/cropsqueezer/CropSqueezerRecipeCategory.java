package theking530.staticpower.integration.JEI.cropsqueezer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import api.gui.GuiDrawUtilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SqueezerOutputWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class CropSqueezerRecipeCategory extends BaseJEIRecipeCategory<CropSqueezerRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    
	    public CropSqueezerRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Squeezer";
	        background = guiHelper.createDrawable(GuiTextures.SQUEEZER_GUI, 57, 3, 113, 75, 0, 0, 30, 0);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	        registry.handleRecipes(SqueezerOutputWrapper.class, CropSqueezerRecipeWrapper.FACTORY, PluginJEI.SQUEEZER_UID);
	        registry.addRecipes(SqueezerRecipeRegistry.Squeezing().getSqueezingRecipes().values(), PluginJEI.SQUEEZER_UID);
	        //registry.addRecipeClickArea(GuiCropSqueezer.class, 111, 69, 26, 19, PluginJEI.SQUEEZER_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.CropSqueezer)), PluginJEI.SQUEEZER_UID);  
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.MechanicalSqueezer)), PluginJEI.SQUEEZER_UID);  
	    	currentPower = 10000;
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.SQUEEZER_UID;
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
	    	GuiDrawUtilities.drawSlot(13, 2, 16, 60);
	    	
	    	GuiPowerBarUtilities.drawPowerBar(0, 62, 6, 60, 1.0f, currentPower, 10000);
	    	GuiDrawUtilities.drawSlot(0, 2, 6, 60);
	    	
	    	currentPower -= 2;
	    	if(currentPower <= 0) {
	    		currentPower = 10000;
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
	    public void setRecipe(IRecipeLayout recipeLayout, CropSqueezerRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        int slotId = 0;

	        //Input
	        guiStacks.init(slotId++, true, 75, 12);  
	        guiStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
				if (slotIndex == 0) {
					tooltip.add(recipeWrapper.recipe.getOutputFluid().getLocalizedName());
					tooltip.add(recipeWrapper.recipe.getOutputFluid().amount + " mb");
				}
			});
	        
	        //Output
	        guiStacks.init(slotId++, false, 75, 52); 
	        guiFluidStacks.init(0, false, 13, 2, 16, 60, 800, false, null);
	        
	        guiStacks.set(ingredients);
	        guiFluidStacks.set(ingredients);
	    }
}
