package theking530.staticpower.integration.JEI.former;

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
import theking530.staticpower.handlers.crafting.registries.FormerRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FormerRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class FormerRecipeCategory extends BaseJEIRecipeCategory<JEIFormerRecipeWrapper>{
 	private final String locTitle;
    private IDrawable background;
    private int currentPower;
    
    public FormerRecipeCategory(IGuiHelper guiHelper) {
        locTitle = "Former";
        background = guiHelper.createDrawable(GuiTextures.FORMER_GUI, 30, 3, 118, 78, -5, 0, 25, 0);
    }
    public void initialize(@Nonnull IModRegistry registry) {
    	registry.handleRecipes(FormerRecipeWrapper.class, recipe -> new JEIFormerRecipeWrapper(recipe), PluginJEI.FORMER_UID);     
        registry.addRecipes(FormerRecipeRegistry.Forming().getFormingList().values(), PluginJEI.FORMER_UID);
        //registry.addRecipeClickArea(GuiPoweredGrinder.class, 111, 69, 26, 19, StaticPowerJEIPlugin.POWERED_GRINDER_UID);
    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.Former)), PluginJEI.FORMER_UID);
    	currentPower = 10000;
    }
    
    @Override
    @Nonnull
    public String getUid() {
        return PluginJEI.FORMER_UID;
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
    		temp.add(GuiUtilities.formatIntegerWithCommas(2000) + " RF/t");
    		return temp;
    	}
		return Collections.emptyList();
	}
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIFormerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        int slotId = 0;

        //Input
        guiStacks.init(slotId++, true, 31, 25);        
        guiStacks.init(slotId++, true, 53, 25);        
        
        //Output
        guiStacks.init(slotId++, false, 112, 25);
        	                  
        guiStacks.set(ingredients);
    }
}
