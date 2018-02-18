package theking530.staticpower.integration.JEI.fermenter;

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
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FermenterOutputWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;
import theking530.staticpower.machines.fermenter.GuiFermenter;

public class FermenterRecipeCategory extends BaseJEIRecipeCategory<JEIFermenterRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    
	    public FermenterRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Fermenter";
	        background = guiHelper.createDrawable(GuiTextures.FERMENTER_GUI, 54, 3, 116, 67, 0, 0, 30, 0);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(FermenterOutputWrapper.class, recipe -> new JEIFermenterRecipeWrapper(recipe), PluginJEI.FERMENTER_UID); 
	        registry.addRecipes(FermenterRecipeRegistry.Fermenting().getFermentingRecipes().values(), PluginJEI.FERMENTER_UID);
	        registry.addRecipeClickArea(GuiFermenter.class, 111, 69, 26, 19, PluginJEI.FERMENTER_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.Fermenter)), PluginJEI.FERMENTER_UID);  
	    	currentPower = 10000;
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.FERMENTER_UID;
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
	    public void setRecipe(IRecipeLayout recipeLayout, JEIFermenterRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        int slotId = 0;

	        //Input
	        guiStacks.init(slotId++, true, 62, 12);  
	        
	        //Output
	        guiFluidStacks.init(0, false, 13, 2, 16, 60, 300, false, null);
	        
	        guiStacks.set(ingredients);
	        guiFluidStacks.set(ingredients);
	    }
}
