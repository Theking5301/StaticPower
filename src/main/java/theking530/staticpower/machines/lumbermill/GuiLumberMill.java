package theking530.staticpower.machines.lumbermill;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.ArrowProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiLumberMill extends BaseGuiContainer {
	
	private TileLumberMill lumberMillTileEntity;
	
	public GuiLumberMill(InventoryPlayer invPlayer, TileLumberMill teLumberMill) {
		super(new ContainerLumberMill(invPlayer, teLumberMill), 176, 166);
		lumberMillTileEntity = teLumberMill;
		registerWidget(new GuiPowerBarFromEnergyStorage(teLumberMill, 8, 62, 16, 54));
		registerWidget(new GuiFluidBarFromTank(teLumberMill.fluidTank, 154, 68, 16, 60, Mode.Output, teLumberMill));
		registerWidget(new ArrowProgressBar(lumberMillTileEntity, 63, 32, 32, 16));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teLumberMill));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teLumberMill));	
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, lumberMillTileEntity).setTabSide(TabSide.LEFT).setOffsets(-30, 0));	
		
		this.setOutputSlotSize(20);
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.lumberMillTileEntity.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawGenericBackground(-30, 5, 28, 60);
		drawGenericBackground(-30, 70, 28, 64);
		
		drawContainerSlots(lumberMillTileEntity, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();
	}
}


