package theking530.staticpower.integration.JEI.condenser;

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
import theking530.staticpower.handlers.crafting.registries.CondenserRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.CondenserRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class CondenserRecipeCategory extends BaseJEIRecipeCategory<JEICondenserRecipeWrapper>{
	 	private final String locTitle;
	    private IDrawable background;

	    public CondenserRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Condenser";
	        background = guiHelper.createBlankDrawable(176, 60);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(CondenserRecipeWrapper.class, recipe -> new JEICondenserRecipeWrapper(recipe), PluginJEI.CONDENSER_UID); 
	        registry.addRecipes(CondenserRecipeRegistry.Condensing().getCondenserRecipes().values(), PluginJEI.CONDENSER_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.Condenser)), PluginJEI.CONDENSER_UID);  
	    }
	    
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.CONDENSER_UID;
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
	    public void setRecipe(IRecipeLayout recipeLayout, JEICondenserRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	        guiFluidStacks.init(0, false, 96, 2, 16, 60, 1, false, null);
	        guiFluidStacks.init(1, true, 68, 2, 16, 60, 1, false, null);
	        
	        guiFluidStacks.set(ingredients);
	    }
}
