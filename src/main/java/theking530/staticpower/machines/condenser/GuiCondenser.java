package theking530.staticpower.machines.condenser;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarUtilities;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiCondenser extends BaseGuiContainer{
		
	private TileEntityCondenser condenser;
	
	public GuiCondenser(InventoryPlayer invPlayer, TileEntityCondenser teFluidGenerator) {
		super(new ContainerCondenser(invPlayer, teFluidGenerator), 176, 173);
		condenser = teFluidGenerator;
		
		registerWidget(new GuiFluidBarFromTank(teFluidGenerator.fluidTank, 50, 77, 16, 60, Mode.Input, teFluidGenerator));
		registerWidget(new GuiFluidBarFromTank(teFluidGenerator.fluidTankEthanol, 110, 77, 16, 60, Mode.Output, teFluidGenerator));

		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teFluidGenerator));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teFluidGenerator));
		
		setOutputSlotSize(20);
	}
	@Override
	public void initGui() {
		super.initGui();

		this.buttonList.add(new ArrowButton(1, guiLeft+11, guiTop+37, 16, 10, "<"));
	    
	    if(condenser.drainComponentEvaporatedMash.getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(condenser.drainComponentEvaporatedMash.getInverseMode(), condenser.getComponents().indexOf(condenser.drainComponentEvaporatedMash), condenser.getPos());
			PacketHandler.net.sendToServer(msg);
			condenser.drainComponentEvaporatedMash.setMode(condenser.drainComponentEvaporatedMash.getInverseMode());
			
		    if(condenser.drainComponentEvaporatedMash.getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.condenser.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		this.drawContainerSlots(condenser, inventorySlots.inventorySlots);
		this.drawSlot(guiLeft+71, guiTop+49, 34, 5);
		
		if(condenser.processingStack != null) {
			int j1 = condenser.getProgressScaled(34);
			GuiFluidBarUtilities.drawFluidBar(condenser.processingStack, 1000, 1000, guiLeft + 71, guiTop + 49, 1, j1, 5, true);
		};
	}
}


