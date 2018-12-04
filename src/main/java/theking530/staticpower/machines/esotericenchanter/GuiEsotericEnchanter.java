package theking530.staticpower.machines.esotericenchanter;

import java.awt.Color;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.progressbars.SquareProgressBar;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiEsotericEnchanter extends BaseGuiContainer {
	
	private TileEsotericEnchanter esotericEnchanter;
	private GuiInfoTab infoTab;

	public GuiEsotericEnchanter(InventoryPlayer invPlayer, TileEsotericEnchanter enchanter) {
		super(new ContainerEsotericEnchanter(invPlayer, enchanter), 178, 166);
		esotericEnchanter = enchanter;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(enchanter, 8, 50, 16, 42));
		registerWidget(new GuiFluidBarFromTank(enchanter.fluidTank, 154, 68, 16, 60, Mode.Input, enchanter));
		
		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 70));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, enchanter));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, enchanter));
		
		GuiMachinePowerInfoTab powerInfoTab;
		getTabManager().registerTab(powerInfoTab = new GuiMachinePowerInfoTab(80, 80, enchanter));
		powerInfoTab.setTabSide(TabSide.LEFT);
		powerInfoTab.setOffsets(-31, 0);
		
		registerWidget(new SquareProgressBar(enchanter, 96, 40, 18, 5));
	}
	@Override
	public void initGui() {
		super.initGui();

	    this.buttonList.add(new ArrowButton(1, guiLeft-24, guiTop+30, 16, 10, "<"));
	    
	    if(esotericEnchanter.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(esotericEnchanter.getFluidInteractionComponent().getInverseMode(), esotericEnchanter.getComponents().indexOf(esotericEnchanter.getFluidInteractionComponent()), esotericEnchanter.getPos());
			PacketHandler.net.sendToServer(msg);
			esotericEnchanter.getFluidInteractionComponent().setMode(esotericEnchanter.getFluidInteractionComponent().getInverseMode());
			
		    if(esotericEnchanter.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.esotericEnchanter.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 8, GuiUtilities.getColor(50, 50, 50) );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3,  GuiUtilities.getColor(50, 50, 50));
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60, new Color(198, 198, 198), new Color(50, 50, 50));
		this.drawGenericBackground(-30, 70, 28, 64, new Color(198, 198, 198), new Color(50, 50, 50));
		this.drawGenericBackground(0, 0, xSize, ySize, new Color(198, 198, 198), new Color(50, 50, 50));
		this.drawPlayerInventorySlots(guiLeft+8, guiTop + 83);
		
		this.drawSlot(8+guiLeft, 8+guiTop, 16, 42);

    	this.drawContainerSlots(esotericEnchanter, this.inventorySlots.inventorySlots);
    			
		infoTab.setText("Esoteric Enchanter", "This machine can=convert the " + EnumTextFormatting.GREEN + "XP" + EnumTextFormatting.WHITE+ "=stored in liquid=form into written=text.");
	}
}


