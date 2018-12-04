package theking530.staticpower.integration.JEI.esotericenchanter;

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
import net.minecraft.client.gui.Gui;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.EsotericEnchanterRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.EsotericEnchanterRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class EsotericEnchanterRecipeCategory extends BaseJEIRecipeCategory<JEIEsotericEnchanterRecipeWrapper>{
	 private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    private int currentProgress;
	    
	    public EsotericEnchanterRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Esoteric Enchanter";
	        background = guiHelper.createBlankDrawable(176, 60);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(EsotericEnchanterRecipeWrapper.class, recipe -> new JEIEsotericEnchanterRecipeWrapper(recipe), PluginJEI.ESOTERIC_ENCHANTER_UID); 
	        registry.addRecipes(EsotericEnchanterRecipeRegistry.Enchanting().getEnchantingRecipes(), PluginJEI.ESOTERIC_ENCHANTER_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.EsotericEnchanter)), PluginJEI.ESOTERIC_ENCHANTER_UID);   
	        
	    	currentPower = 10000;
	    	currentProgress = 0;
	    }
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.ESOTERIC_ENCHANTER_UID;
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
	    	GuiDrawUtilities.drawSlot(8, 0, 16, 60);
	       	GuiDrawUtilities.drawSlot(152, 0, 16, 60);
	    	GuiPowerBarUtilities.drawPowerBar(8, 60, 16, 60, 1.0f, currentPower, 10000);

			GuiDrawUtilities.drawSlot(32, 22, 16, 16);
			GuiDrawUtilities.drawSlot(52, 22, 16, 16);
			GuiDrawUtilities.drawSlot(72, 22, 16, 16);	
			
			GuiDrawUtilities.drawSlot(118, 18, 24, 24);
			
			GuiDrawUtilities.drawSlot(92, 28, 20, 5);	
	    	float progress = (float)currentProgress/260.0f;
	    	Gui.drawRect(92, 28, 92+(int)(progress*20), 33, GuiUtilities.getColor(255, 255, 255));
	    	
	    	currentPower -= 3;
	    	currentProgress++;
	    	
	    	if(currentPower <= 0) {
	    		currentPower = 10000;
	    	}
	    	if(currentProgress >= 280) {
	    		currentProgress = 0;
	    	}
	    }
	    @Override
		public List<String> getTooltipStrings(int mouseX, int mouseY) {
	    	if(mouseX >= 8 && mouseX <= 8 + 16 && mouseY >= 8 && mouseY <= 60) {
	    		List<String> temp = new ArrayList<String>();
	    		temp.add("Energy:");
	    		temp.add(GuiUtilities.formatIntegerWithCommas(1000) + " RF");
	    		return temp;
	    	}
			return Collections.emptyList();
		}
	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEIEsotericEnchanterRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        int slotId = 0;

	        //Input
	        guiStacks.init(slotId++, true, 31, 21);  
	        guiStacks.init(slotId++, true, 51, 21);      
	        guiStacks.init(slotId++, true, 71, 21);  
	        guiFluidStacks.init(0, true, 152, 0, 16, 60, 5000, false, null);
	        
	        //Output
	        guiStacks.init(slotId, false, 121, 21);
	        
	        guiStacks.set(ingredients);
	        guiFluidStacks.set(ingredients);
	    }
}
