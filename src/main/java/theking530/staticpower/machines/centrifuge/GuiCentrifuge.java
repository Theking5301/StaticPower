package theking530.staticpower.machines.centrifuge;

import api.gui.GuiDrawUtilities;
import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.CentrifugeProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiCentrifuge extends BaseGuiContainer{

	private TileEntityCentrifuge centrifuge;
	private GuiInfoTab infoTab;

	public GuiCentrifuge(InventoryPlayer invPlayer, TileEntityCentrifuge tileEntityCentrifuge) {
		super(new ContainerCentrifuge(invPlayer, tileEntityCentrifuge), 176, 166);
		centrifuge = tileEntityCentrifuge;
		registerWidget(new GuiPowerBarFromEnergyStorage(tileEntityCentrifuge, 8, 62, 16, 54));
		registerWidget(new CentrifugeProgressBar(tileEntityCentrifuge, 80, 39, 15, 15));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 70));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, tileEntityCentrifuge));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, tileEntityCentrifuge));
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, tileEntityCentrifuge).setTabSide(TabSide.LEFT));

		setOutputSlotSize(16);
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String formerName = I18n.format(this.centrifuge.getName());
		String rpmString = centrifuge.getRotationSpeed() + " RPM";
		this.fontRenderer.drawString(formerName, this.xSize / 2 - this.fontRenderer.getStringWidth(formerName) / 2, 6, 4210752);
		GuiDrawUtilities.drawStringWithSize(rpmString, this.xSize / 2 + 52, 51, 0.85f,  GuiUtilities.getColor(75, 75, 75), false);
	}
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawContainerSlots(centrifuge, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();		

		drawVerticalBar(60, 30, 2, 16, centrifuge.getMultiplier(), GuiUtilities.getColor(175, 0, 0, 255));
		
		String formerInfo = "The centrifuge seperates=items into other items=by spinning them in=a separation chamber.=Current Speed: " + centrifuge.getRotationSpeed() + " RPM";
		infoTab.setText("Centrifuge", formerInfo);
	}	
}


