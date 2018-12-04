package theking530.staticpower.machines.poweredgrinder;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.progressbars.GrinderProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;

public class GuiPoweredGrinder extends BaseGuiContainer{
	
	private TileEntityPoweredGrinder tileEntityGrinder;
	private GuiInfoTab infoTab;

	public GuiPoweredGrinder(InventoryPlayer invPlayer, TileEntityPoweredGrinder teGrinder) {
		super(new ContainerPoweredGrinder(invPlayer, teGrinder), 176, 166);
		tileEntityGrinder = teGrinder;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teGrinder, 8, 62, 16, 54));
		registerWidget(new GrinderProgressBar(tileEntityGrinder, 79, 38, 18, 17));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 60));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teGrinder));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teGrinder));
		
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, tileEntityGrinder).setTabSide(TabSide.LEFT));

		setOutputSlotSize(20);
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String NAME = I18n.format(this.tileEntityGrinder.getName());
		this.fontRenderer.drawString(NAME, this.xSize / 2 - this.fontRenderer.getStringWidth(NAME) / 2, 6, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawContainerSlots(tileEntityGrinder, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();
		
		String text = ("Grinds items into=their base components. ==" + "Bonus Chance: " + EnumTextFormatting.GREEN + tileEntityGrinder.getBonusOutputChance() * 100.0f + "%");
		infoTab.setText(tileEntityGrinder.getBlockType().getLocalizedName(), text);
	}	
}


