package theking530.staticpower.machines.cropsqueezer;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.SquareProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiCropSqueezer extends BaseGuiContainer {	

	private TileEntityCropSqueezer cSqueezer;
	
	public GuiCropSqueezer(InventoryPlayer invPlayer, TileEntityCropSqueezer teCropSqueezer) {
		super(new ContainerCropSqueezer(invPlayer, teCropSqueezer), 176, 166);
		cSqueezer = teCropSqueezer;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teCropSqueezer, 8, 50, 16, 42));
		registerWidget(new GuiFluidBarFromTank(teCropSqueezer.fluidTank, 150, 68, 16, 60, Mode.Output, teCropSqueezer));
		registerWidget(new SquareProgressBar(teCropSqueezer, 83, 38, 10, 10));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teCropSqueezer));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teCropSqueezer));

		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teCropSqueezer).setTabSide(TabSide.LEFT).setOffsets(-31, 0));		
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.cSqueezer.getName());	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 70, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(cSqueezer, this.inventorySlots.inventorySlots);
	}
}


