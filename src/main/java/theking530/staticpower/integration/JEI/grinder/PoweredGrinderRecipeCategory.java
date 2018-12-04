package theking530.staticpower.integration.JEI.grinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import api.gui.GuiDrawUtilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
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
import theking530.staticpower.handlers.crafting.registries.GrinderRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.GrinderOutputWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class PoweredGrinderRecipeCategory extends BaseJEIRecipeCategory<PoweredGrinderRecipeWrapper>{
 	private final String locTitle;
    private IDrawable background;
    private int currentPower;
    
    public PoweredGrinderRecipeCategory(IGuiHelper guiHelper) {
        locTitle = "Powered Grinder";
        background = guiHelper.createDrawable(GuiTextures.GRINDER_GUI, 30, 3, 118, 78, -5, 0, 0, 0);
    }
    public void initialize(@Nonnull IModRegistry registry) {
    	registry.handleRecipes(GrinderOutputWrapper.class, recipe -> new PoweredGrinderRecipeWrapper(recipe), PluginJEI.POWERED_GRINDER_UID);  
        registry.addRecipes(GrinderRecipeRegistry.Grinding().getGrindingList().values(), PluginJEI.POWERED_GRINDER_UID);
        //registry.addRecipeClickArea(GuiPoweredGrinder.class, 111, 69, 26, 19, StaticPowerJEIPlugin.POWERED_GRINDER_UID);
    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.PoweredGrinder)), PluginJEI.POWERED_GRINDER_UID);
    	currentPower = 10000;
    }
    
    @Override
    @Nonnull
    public String getUid() {
        return PluginJEI.POWERED_GRINDER_UID;
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
    	GuiDrawUtilities.drawSlot(0, 8, 16, 54);
    	GuiPowerBarUtilities.drawPowerBar(0, 62, 16, 54, 1.0f, currentPower, 10000);
    	currentPower -= 2;
    	if(currentPower <= 0) {
    		currentPower = 10000;
    	}
    }
    @Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
    	if(mouseX >= 0 && mouseX <= 0 + 16 && mouseY >= 8 && mouseY <= 62) {
    		List<String> temp = new ArrayList<String>();
    		temp.add("Energy:");
    		temp.add(GuiUtilities.formatIntegerWithCommas(1000) + " RF");
    		return temp;
    	}
		return Collections.emptyList();
	}
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PoweredGrinderRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        int slotId = 0;

        //Input
        guiStacks.init(slotId++, true, 49, 9);        
        
        //Output
        guiStacks.init(slotId++, false, 49, 51);
        guiStacks.init(slotId++, false, 75, 37);
        guiStacks.init(slotId++, false, 24, 37);
        	        
        guiStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex > 0) {
				tooltip.add("Output Chance: " + (int)(recipeWrapper.recipe.getOutputItems().get(slotIndex-1).getPercentage()*100) + "%");
			}
		});
        
        guiStacks.set(ingredients);
    }
}
