package theking530.staticpower.integration.JEI.fluidinfuser;

import javax.annotation.Nonnull;

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
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.InfuserRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.FluidInfuserOutputWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class FluidInfuserRecipeCategory extends BaseJEIRecipeCategory<JEIFluidInfuserRecipeWrapper>{
	 private final String locTitle;
	    private IDrawable background;
	    private int currentPower;
	    
	    public FluidInfuserRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Fluid Infuser";
	        background = guiHelper.createDrawable(GuiTextures.FLUID_INFUSER_GUI, 24, 6, 144, 65);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(FluidInfuserOutputWrapper.class, recipe -> new JEIFluidInfuserRecipeWrapper(recipe), PluginJEI.FLUID_INFUSER_UID); 
	        registry.addRecipes(InfuserRecipeRegistry.Infusing().getInfusingRecipes().values(), PluginJEI.FLUID_INFUSER_UID);
	        //registry.addRecipeClickArea(GuiSolderingTable.class, 111, 69, 26, 19, PluginJEI.FLUID_INFUSER_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.FluidInfuser)), PluginJEI.FLUID_INFUSER_UID);
	    	currentPower = 10000;     
	    }
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.FLUID_INFUSER_UID;
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
	    	GuiPowerBarUtilities.drawPowerBar(26, 62, 6, 60, 1.0f, currentPower, 10000);
	    	
	    	currentPower -= 2;
	    	if(currentPower <= 0) {
	    		currentPower = 10000;
	    	}
	    }

	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEIFluidInfuserRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        int slotId = 0;

	        //Input
	        guiStacks.init(slotId++, true, 49, 25);        
	        guiFluidStacks.init(0, true, 6, 2, 16, 60, 5000, false, null);
	        
	        //Output
	        guiStacks.init(slotId, false, 106, 25);
	        
	        guiStacks.set(ingredients);
	        guiFluidStacks.set(ingredients);
	    }
}
