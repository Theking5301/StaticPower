package theking530.staticpower.machines.fusionfurnace;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.GrinderProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiFusionFurnace extends BaseGuiContainer {
	
	private TileEntityFusionFurnace tileEntityFusionFurnace;
	private GuiInfoTab infoTab;

	public GuiFusionFurnace(InventoryPlayer invPlayer, TileEntityFusionFurnace teFurnace) {
		super(new ContainerFusionFurnace(invPlayer, teFurnace), 176, 166);
		tileEntityFusionFurnace = teFurnace;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teFurnace, 8, 68, 16, 60));
		registerWidget(new GrinderProgressBar(teFurnace, 79, 36, 18, 17));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 60));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teFurnace));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teFurnace));
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teFurnace).setTabSide(TabSide.LEFT));

	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.tileEntityFusionFurnace.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawContainerSlots(tileEntityFusionFurnace, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();
		
		String text = ("Uses ancient knowledge=to combine multiple items=into more powerful ones.");
		infoTab.setText(tileEntityFusionFurnace.getBlockType().getLocalizedName(), text);
	}	
}


