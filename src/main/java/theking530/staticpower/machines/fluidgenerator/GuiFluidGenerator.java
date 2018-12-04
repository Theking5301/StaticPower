package theking530.staticpower.machines.fluidgenerator;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.tabs.GuiPowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiFluidGenerator extends BaseGuiContainer {
	
	private TileEntityFluidGenerator fGenerator;
		
	public GuiFluidGenerator(InventoryPlayer invPlayer, TileEntityFluidGenerator teFluidGenerator) {
		super(new ContainerFluidGenerator(invPlayer, teFluidGenerator), 176, 166);
		fGenerator = teFluidGenerator;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teFluidGenerator, 8, 68, 16, 60));
		registerWidget(new GuiFluidBarFromTank(teFluidGenerator.fluidTank, 154, 68, 16, 60, Mode.Input, teFluidGenerator));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teFluidGenerator));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teFluidGenerator));	
		getTabManager().registerTab(new GuiPowerInfoTab(80, 60, teFluidGenerator.getEnergyStorage()).setTabSide(TabSide.LEFT).setOffsets(-31, 0));
		
		setOutputSlotSize(16);
	}
	@Override
	public void initGui() {
		super.initGui();

		this.buttonList.add(new ArrowButton(1, guiLeft-24, guiTop+30, 16, 10, "<"));
	    
	    if(fGenerator.fluidContainerComponent.getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(fGenerator.fluidContainerComponent.getInverseMode(), fGenerator.getComponents().indexOf(fGenerator.fluidContainerComponent), fGenerator.getPos());
			PacketHandler.net.sendToServer(msg);
			fGenerator.fluidContainerComponent.setMode(fGenerator.fluidContainerComponent.getInverseMode());
			
		    if(fGenerator.fluidContainerComponent.getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.fGenerator.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 70, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(fGenerator, this.inventorySlots.inventorySlots);
	}
}


