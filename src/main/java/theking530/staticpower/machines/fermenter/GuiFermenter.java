package theking530.staticpower.machines.fermenter;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarUtilities;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;

public class GuiFermenter extends BaseGuiContainer {	

	private TileEntityFermenter fermenter;
	
	public GuiFermenter(InventoryPlayer invPlayer, TileEntityFermenter teCropSqueezer) {
		super(new ContainerFermenter(invPlayer, teCropSqueezer), 176, 172);
		fermenter = teCropSqueezer;

		registerWidget(new GuiPowerBarFromEnergyStorage(teCropSqueezer, 8, 50, 16, 42));
		registerWidget(new GuiFluidBarFromTank(teCropSqueezer.fluidTank, 150, 73, 16, 60, Mode.Input, teCropSqueezer));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teCropSqueezer));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teCropSqueezer));
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teCropSqueezer).setTabSide(TabSide.LEFT).setOffsets(-31, 0));

		this.setOutputSlotSize(20);
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.fermenter.getName());	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2 + 7, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 70, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(fermenter, this.inventorySlots.inventorySlots);
		this.drawSlot(guiLeft+97, guiTop+40, 48, 5);
		if(!fermenter.slotsInternal.getStackInSlot(0).isEmpty()) {
			int progress = fermenter.getProgressScaled(48);
			FluidStack fluid = FermenterRecipeRegistry.Fermenting().getFluidResult(fermenter.slotsInternal.getStackInSlot(0));
			GuiFluidBarUtilities.drawFluidBar(fluid, 1000, 1000, guiLeft + 97, guiTop + 45, 1, progress, 5, false);
		}	
	}
}


