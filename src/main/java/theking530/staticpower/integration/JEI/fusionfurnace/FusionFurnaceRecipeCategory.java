package theking530.staticpower.integration.JEI.fusionfurnace;

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
import theking530.staticpower.handlers.crafting.registries.FusionRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FusionFurnaceRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class FusionFurnaceRecipeCategory extends BaseJEIRecipeCategory<JEIFusionFurnaceRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    
	    public FusionFurnaceRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Fusion Furnace";
	        background = guiHelper.createBlankDrawable(176, 60);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(FusionFurnaceRecipeWrapper.class, recipe -> new JEIFusionFurnaceRecipeWrapper(recipe), PluginJEI.FUSION_FURNACE_UID); 
	        registry.addRecipes(FusionRecipeRegistry.Fusing().getFusionList().values(), PluginJEI.FUSION_FURNACE_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.FusionFurnace)), PluginJEI.FUSION_FURNACE_UID);  
	    	currentPower = 10000;
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.FUSION_FURNACE_UID;
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

	    	GuiPowerBarUtilities.drawPowerBar(0, 62, 16, 60, 1.0f, currentPower, 10000);
	    	GuiDrawUtilities.drawSlot(0, 2, 16, 60);
	    	
	    	currentPower -= 2;
	    	if(currentPower <= 0) {
	    		currentPower = 10000;
	    	}
	    	
			GuiDrawUtilities.drawSlot(36, 25, 16, 16);
			GuiDrawUtilities.drawSlot(58, 13, 16, 16);
			GuiDrawUtilities.drawSlot(80, 2, 16, 16);	
			GuiDrawUtilities.drawSlot(102, 13, 16, 16);
			GuiDrawUtilities.drawSlot(124, 25, 16, 16);
			GuiDrawUtilities.drawSlot(78, 34, 20, 20);	

	    }
	    @Override
		public List<String> getTooltipStrings(int mouseX, int mouseY) {
	    	if(mouseX >= 0 && mouseX <= 0 + 16 && mouseY >= 2 && mouseY <= 62) {
	    		List<String> temp = new ArrayList<String>();
	    		temp.add("Energy:");
	    		temp.add(GuiUtilities.formatIntegerWithCommas(1000) + " RF");
	    		return temp;
	    	}
			return Collections.emptyList();
		}
	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEIFusionFurnaceRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        
	        int slotId = 0;

	        //Input
	        guiStacks.init(slotId++, true, 35, 24);  
	        guiStacks.init(slotId++, true, 57, 12);  
	        guiStacks.init(slotId++, true, 79, 1);  
	        guiStacks.init(slotId++, true, 101, 12);  
	        guiStacks.init(slotId++, true, 123, 24);  
	        
	        //Output
	        guiStacks.init(slotId++, false, 79, 35);  	     
	        
	        guiStacks.set(ingredients);
	    }
}
