package theking530.staticpower.machines.distillery;

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
import theking530.staticpower.client.gui.widgets.valuebars.GuiHeatBarFromStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiDistillery extends BaseGuiContainer {

	private GuiHeatBarFromStorage heatbar;
	private TileEntityDistillery distillery;
	
	public GuiDistillery(InventoryPlayer invPlayer, TileEntityDistillery teFluidGenerator) {
		super(new ContainerDistillery(invPlayer, teFluidGenerator), 176, 176);
		distillery = teFluidGenerator;
		
		heatbar = new GuiHeatBarFromStorage(teFluidGenerator.heatStorage);
		registerWidget(new GuiFluidBarFromTank(teFluidGenerator.fluidTank, 50, 77, 16, 60, Mode.Input, teFluidGenerator));
		registerWidget(new GuiFluidBarFromTank(teFluidGenerator.fluidTank2, 110, 77, 16, 60, Mode.Output, teFluidGenerator));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teFluidGenerator));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teFluidGenerator));
		
		setOutputSlotSize(20);
	}
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new ArrowButton(1, guiLeft+11, guiTop+37, 16, 10, "<"));
	    
	    if(distillery.drainComponentMash.getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(distillery.drainComponentMash.getInverseMode(), distillery.getComponents().indexOf(distillery.drainComponentMash), distillery.getPos());
			PacketHandler.net.sendToServer(msg);
			distillery.drainComponentMash.setMode(distillery.drainComponentMash.getInverseMode());
			
		    if(distillery.drainComponentMash.getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}
	public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);
		
		int var1 = (this.width - this.xSize) / 2;
		int var2 = (this.height - this.ySize) / 2;    
		if(par1 >= 71 + var1 && par2 >= 60 + var2 && par1 <= 105 + var1 && par2 <= 77 + var2) {	
			drawHoveringText(heatbar.drawText(), par1, par2, fontRenderer); 
		}	  
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.distillery.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {   	
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(distillery, this.inventorySlots.inventorySlots);
    	
		this.drawSlot(guiLeft+71, guiTop+49, 34, 5);
		if(distillery.processingStack != null) {
			int j1 = distillery.getProgressScaled(34);
			GuiFluidBarUtilities.drawFluidBar(distillery.processingStack, 1000, 1000, guiLeft + 71, guiTop + 49, 1, j1, 5, true);
		}
		this.drawSlot(guiLeft+71, guiTop+60, 34, 17);
		heatbar.drawHeatBar(guiLeft+71, guiTop+77, this.zLevel, 34, 17);
	}
}


