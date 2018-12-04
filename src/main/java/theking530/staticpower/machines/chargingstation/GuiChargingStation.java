package theking530.staticpower.machines.chargingstation;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiChargingStation extends BaseGuiContainer {
		
	private TileEntityChargingStation chargingStation;
	
	public GuiChargingStation(InventoryPlayer invPlayer, TileEntityChargingStation teCharging) {
		super(new ContainerChargingStation(invPlayer, teCharging), 176, 166);
		
		chargingStation = teCharging;		
		registerWidget(new GuiPowerBarFromEnergyStorage(teCharging, 8, 50, 16, 42));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teCharging));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teCharging));
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teCharging).setTabSide(TabSide.LEFT).setOffsets(-31, 0));

		this.setOutputSlotSize(20);
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.chargingStation.getName());	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		this.drawGenericBackground(-30, 8, 28, 85);
		
    	this.drawContainerSlots(chargingStation, this.inventorySlots.inventorySlots);
    	
    	this.drawSlot(guiLeft-24, guiTop+14, 16, 16);
    	this.drawSlot(guiLeft-24, guiTop+33, 16, 16);
    	this.drawSlot(guiLeft-24, guiTop+52, 16, 16);
    	this.drawSlot(guiLeft-24, guiTop+71, 16, 16);
		//Progress Bar
		int j1 = chargingStation.getProgressScaled(24);
		drawTexturedModalRect(guiLeft + 73, guiTop + 32, 176, 69, j1, 14);	
	}
}


