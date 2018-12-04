package theking530.staticpower.machines.former;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.ArrowProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiFormer extends BaseGuiContainer{

	private TileEntityFormer former;
	private GuiInfoTab infoTab;

	public GuiFormer(InventoryPlayer invPlayer, TileEntityFormer tileEntityFormer) {
		super(new ContainerFormer(invPlayer, tileEntityFormer), 176, 166);
		former = tileEntityFormer;
		registerWidget(new GuiPowerBarFromEnergyStorage(tileEntityFormer, 8, 62, 16, 54));
		registerWidget(new ArrowProgressBar(tileEntityFormer, 83, 34, 32, 16));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 60));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, tileEntityFormer));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, tileEntityFormer));
		
		GuiMachinePowerInfoTab powerInfoTab;
		getTabManager().registerTab(powerInfoTab = new GuiMachinePowerInfoTab(80, 80, tileEntityFormer));
		powerInfoTab.setTabSide(TabSide.LEFT);	
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String formerName = I18n.format(this.former.getName());
		this.fontRenderer.drawString(formerName, this.xSize / 2 - this.fontRenderer.getStringWidth(formerName) / 2, 6, 4210752);
	}
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawContainerSlots(former, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();		

		String formerInfo = "The former transforms=items into other items=by shaping them against=moulds.";
		infoTab.setText("Former", formerInfo);
	}	
}


