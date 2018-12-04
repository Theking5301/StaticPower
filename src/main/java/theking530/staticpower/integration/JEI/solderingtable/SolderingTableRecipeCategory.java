package theking530.staticpower.integration.JEI.solderingtable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.handlers.crafting.registries.SolderingRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SolderingRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;
import theking530.staticpower.items.ModItems;

public class SolderingTableRecipeCategory extends BaseJEIRecipeCategory<JEISolderingTableRecipeWrapper>{
	 private final String locTitle;
	    private IDrawable background;

	    public SolderingTableRecipeCategory(IGuiHelper guiHelper) {
	        locTitle = "Soldering Table";
	        background = guiHelper.createDrawable(GuiTextures.SOLDERING_TABLE_GUI, 8, 8, 161, 86, 0, 0, 0, 0);
	    }
	    public void initialize(@Nonnull IModRegistry registry) {
	    	registry.handleRecipes(SolderingRecipeWrapper.class, recipe -> new JEISolderingTableRecipeWrapper(registry.getJeiHelpers(), recipe), PluginJEI.SOLDERING_TABLE_UID);
	        registry.addRecipes(SolderingRecipeRegistry.Soldering().getRecipeList(), PluginJEI.SOLDERING_TABLE_UID);
	        //registry.addRecipeClickArea(GuiSolderingTable.class, 111, 69, 26, 19, PluginJEI.SOLDERING_TABLE_UID);
	    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.SolderingTable)), PluginJEI.SOLDERING_TABLE_UID);        
	    }
	    @Override
	    @Nonnull
	    public String getUid() {
	        return PluginJEI.SOLDERING_TABLE_UID;
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
	    public void drawExtras(@Nonnull Minecraft minecraft) {}

	    @Override
	    public void setRecipe(IRecipeLayout recipeLayout, JEISolderingTableRecipeWrapper recipeWrapper, IIngredients ingredients) {
	        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
	        
	        List<ItemStack> ironList = new ArrayList<ItemStack>();
	        ironList.add(new ItemStack(ModItems.SolderingIron));      
	        ingredients.getInputs(ItemStack.class).add(ironList);
	        
	        int slotId = 0;

	        //Input Slots
	        for (int l = 0; l < 3; l++) {
	            for (int i1 = 0; i1 < 3; i1++){
	                guiStacks.init(slotId++, true, 53 + i1 * 18, 8 + l * 18);
	            }
	        }
            guiStacks.init(slotId++, true, 2, 8);
	        //Output Slot
	        guiStacks.init(slotId, false, 131, 30);
	        guiStacks.set(ingredients);
	    }
}
