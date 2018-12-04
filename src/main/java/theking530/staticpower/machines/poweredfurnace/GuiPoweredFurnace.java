package theking530.staticpower.machines.poweredfurnace;

import org.lwjgl.opengl.GL11;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.client.gui.widgets.progressbars.ArrowProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiPoweredFurnace extends BaseGuiContainer {
	
	private TileEntityPoweredFurnace tileEntityFurnace;
	
	public GuiPoweredFurnace(InventoryPlayer invPlayer, TileEntityPoweredFurnace teSmelter) {
		super(new ContainerPoweredFurnace(invPlayer, teSmelter), 176, 166);
		tileEntityFurnace = teSmelter;
		registerWidget(new GuiPowerBarFromEnergyStorage(teSmelter, 8, 62, 16, 54));
		registerWidget(new ArrowProgressBar(tileEntityFurnace, 73, 32, 32, 16));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teSmelter));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teSmelter));	
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, tileEntityFurnace).setTabSide(TabSide.LEFT));	
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.tileEntityFurnace.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawContainerSlots(tileEntityFurnace, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();
		
		//Flames
		if(tileEntityFurnace.isProcessing()) {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.FURNACE_GUI);
			drawTexturedModalRect(guiLeft + 51, guiTop + 50, 176, 55, 14, 14);	
		}	
	}
}


