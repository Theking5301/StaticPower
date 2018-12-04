package theking530.staticpower.integration.JEI.centrifuge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import api.gui.GuiDrawUtilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticpower.handlers.crafting.registries.CentrifugeRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.CentrifugeRecipeWrapper;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.integration.JEI.PluginJEI;

public class CentrifugeRecipeCategory extends BaseJEIRecipeCategory<JEICentrifugeRecipeWrapper>{
 	private final String locTitle;
    private IDrawable background;
    private int currentPower;
    private int currentProgress;
    private int maxProgress;
    
    public CentrifugeRecipeCategory(IGuiHelper guiHelper) {
        locTitle = I18n.format("container.Centrifuge");
        background = guiHelper.createBlankDrawable(176, 60);
    }
    public void initialize(@Nonnull IModRegistry registry) {
    	registry.handleRecipes(CentrifugeRecipeWrapper.class, recipe -> new JEICentrifugeRecipeWrapper(recipe), PluginJEI.CENTRIFUGE_UID);  
        registry.addRecipes(CentrifugeRecipeRegistry.Centrifuging().getCentrifugingList().values(), PluginJEI.CENTRIFUGE_UID);
    	registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.Centrifuge)), PluginJEI.CENTRIFUGE_UID);
    	currentPower = 10000;
        maxProgress = 100;
    }
    
    @Override
    @Nonnull
    public String getUid() {
        return PluginJEI.CENTRIFUGE_UID;
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
    	GuiPowerBarUtilities.drawPowerBar(0, 60, 16, 54, 1.0f, currentPower, 10000);
    	renderProgressBar(80, 19, 16, 16);
    	
    	currentPower -= 2;
    	currentProgress++;
    	if(currentPower <= 0) {
    		currentPower = 10000;
    	}
    	if(currentProgress >= maxProgress) {
    		currentProgress = 0;
    	}
    	GuiDrawUtilities.drawSlot(81, 1, 16, 16);
    	GuiDrawUtilities.drawSlot(62, 38, 16, 16);
    	GuiDrawUtilities.drawSlot(81, 38, 16, 16);
    	GuiDrawUtilities.drawSlot(100, 38, 16, 16);
    }

	public void renderProgressBar(float xPosition, float yPosition, float xSize, float ySize) {
		currentProgress = Math.min(currentProgress, maxProgress);
		float adjustedProgress = (float)currentProgress/(float)maxProgress;
		
		GlStateManager.disableLighting();
        GlStateManager.enableBlend();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.CENTRIFUGE_PROGRESS_BAR);	
		
		GL11.glPushMatrix();
		float offset = 0.5f;
		GL11.glTranslatef(xPosition+offset+xSize/2, yPosition+offset+ySize/2, yPosition+offset+ySize/2);
		GL11.glRotatef(adjustedProgress*720, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-(xPosition+offset+xSize/2), -(yPosition+offset+ySize/2), -(yPosition+offset+ySize/2));
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize, ySize, 0.0f, 0.5f, 1.0f, 1.0f);
        GuiDrawUtilities.drawTexturedModalRect(xPosition, yPosition, xSize, ySize*adjustedProgress, 0.0f, 0.0f, 1.0f, (0.5f * adjustedProgress));
        GL11.glPopMatrix();
        
		GlStateManager.enableLighting();
        GlStateManager.disableBlend();
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
    public void setRecipe(IRecipeLayout recipeLayout, JEICentrifugeRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        int slotId = 0;

        //Input
        guiStacks.init(slotId++, true, 80, 0);        
        
        //Output
        guiStacks.init(slotId++, false, 61, 37);
        guiStacks.init(slotId++, false, 80, 37);
        guiStacks.init(slotId++, false, 99, 37);
        	        
        
        guiStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 0) {
				tooltip.add("Minimum Speed: " + (recipeWrapper.recipe.getMinimumSpeed() + " " + I18n.format("gui.RPM")));
			}
		});
        
        guiStacks.set(ingredients);
    }
}
