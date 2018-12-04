package theking530.staticpower.integration.JEI.distillery;

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
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.handlers.crafting.registries.DistilleryRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.DistilleryRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class DistilleryRecipeCategory extends BaseJEIRecipeCategory<JEIDistilleryRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;

	    public DistilleryRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Distillery";
	        background = guiHelper.createBlankDrawable(176, 60);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(DistilleryRecipeWrapper.class, recipe -> new JEIDistilleryRecipeWrapper(recipe), PluginJEI.DISTILLERY_UID); 
	        registry.addRecipes(DistilleryRecipeRegistry.Distillery().getDistilleryRecipes().values(), PluginJEI.DISTILLERY_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.Distillery)), PluginJEI.DISTILLERY_UID);  
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.DISTILLERY_UID;
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
	    public void drawExtras(@Nonnull Minecraft minecraft) {
	    	GuiDrawUtilities.drawSlot(96, 2, 16, 60);
	    	GuiDrawUtilities.drawSlot(68, 2, 16, 60);
	    }
	    @Override
	    @Nonnull
	    public IDrawable getBackground() {
	        return background;
	    }
	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEIDistilleryRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        guiFluidStacks.init(0, false, 96, 2, 16, 60, 1, false, null);
	        guiFluidStacks.init(1, true, 68, 2, 16, 60, 1, false, null);
	        
	        guiFluidStacks.set(ingredients);
	    }
}
