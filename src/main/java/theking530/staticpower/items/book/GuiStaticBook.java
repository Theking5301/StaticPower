package theking530.staticpower.items.book;

import api.gui.GuiDrawUtilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.BaseGuiContainer;

public class GuiStaticBook extends BaseGuiContainer {
	
	public GuiStaticBook(InventoryPlayer invPlayer) {
		super(new ContainerStaticBook(), 200, 300);
		invPlayer.player.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0f, 1.0f);
	}

	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override			
	protected void actionPerformed(GuiButton B) {
		
	}
	public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);
    	GlStateManager.disableLighting();
    	
    	String text = "Static Power";
    	Gui.drawRect(guiLeft+55, guiTop+5, guiLeft+140, guiTop+22, GuiUtilities.getColor(100, 100, 100, 100));
    	GuiDrawUtilities.drawStringWithSize(text, guiLeft + 170 - fontRenderer.getStringWidth(text)/2, guiTop+20, 1.25f, GuiUtilities.getColor(255, 255, 255), true);
    	
    	String text2 = "WIP";
    	Gui.drawRect(guiLeft+55, guiTop+35, guiLeft+140, guiTop+52, GuiUtilities.getColor(100, 100, 100, 100));
    	GuiDrawUtilities.drawStringWithSize(text2, guiLeft + 120 - fontRenderer.getStringWidth(text2)/2, guiTop+50, 1.25f, GuiUtilities.getColor(255, 255, 255), true);

	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
	}

	@Override
	protected void drawExtra(float partialTicks, int mouseX, int mouseY) {
		drawGenericBackground();
	}	

}
