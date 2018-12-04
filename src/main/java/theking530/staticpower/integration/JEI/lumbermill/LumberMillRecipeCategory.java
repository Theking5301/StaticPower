package theking530.staticpower.integration.JEI.lumbermill;

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
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.LumberMillRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.LumberMillRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class LumberMillRecipeCategory extends BaseJEIRecipeCategory<JEILumberMillRecipeWrapper>{
 	private final String locTitle;
    private IDrawable background;
    private int currentPower;
    
    public LumberMillRecipeCategory(IGuiHelper guiHelper) {
        locTitle = "Lumber Mill";
        background = guiHelper.createBlankDrawable(176, 60);
    }
    public void initialize(@Nonnull IModRegistry registry) {
    	registry.handleRecipes(LumberMillRecipeWrapper.class, recipe -> new JEILumberMillRecipeWrapper(recipe), PluginJEI.LUMBER_MILL_UID);     
        registry.addRecipes(LumberMillRecipeRegistry.Milling().getMillingRecipes().values(), PluginJEI.LUMBER_MILL_UID);
    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.LumberMill)), PluginJEI.LUMBER_MILL_UID);
    	currentPower = 10000;
    }
    
    @Override
    @Nonnull
    public String getUid() {
        return PluginJEI.LUMBER_MILL_UID;
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
    	
		GuiDrawUtilities.drawSlot(41, 26, 16, 16);
		GuiDrawUtilities.drawSlot(102, 26, 16, 16);
		GuiDrawUtilities.drawSlot(128, 26, 16, 16);	
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
    public void setRecipe(IRecipeLayout recipeLayout, JEILumberMillRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        int slotId = 0;

        //Input
        guiStacks.init(slotId++, true, 40, 25);          
        
        //Output
        guiStacks.init(slotId++, false, 101, 25);
        guiStacks.init(slotId++, false, 127, 25);
        
        guiStacks.set(ingredients);
    }
}
