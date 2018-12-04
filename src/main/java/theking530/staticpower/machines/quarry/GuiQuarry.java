package theking530.staticpower.machines.quarry;

import org.lwjgl.opengl.GL11;

import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiQuarry extends BaseGuiContainer {
	
	private TileEntityQuarry quarryTileEntity;

	public GuiQuarry(InventoryPlayer invPlayer, TileEntityQuarry teQuarry) {
		super(new ContainerQuarry(invPlayer, teQuarry), 176, 166);
		quarryTileEntity = teQuarry;	
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teQuarry, 8, 50, 16, 42));
		registerWidget(new GuiFluidBarFromTank(teQuarry.fluidTank, 154, 68, 16, 60, Mode.Input, teQuarry));
		
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teQuarry));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teQuarry));
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teQuarry).setTabSide(TabSide.LEFT).setOffsets(-31, 0));	
	}
	@Override
	public void initGui() {
		super.initGui();
	    this.buttonList.add(new ArrowButton(1, guiLeft-24, guiTop+30, 16, 10, "<"));
	    
	    if(quarryTileEntity.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			IMessage msg = new PacketFluidContainerComponent(quarryTileEntity.getFluidInteractionComponent().getInverseMode(), quarryTileEntity.getComponents().indexOf(quarryTileEntity.getFluidInteractionComponent()), quarryTileEntity.getPos());
			PacketHandler.net.sendToServer(msg);
			quarryTileEntity.getFluidInteractionComponent().setMode(quarryTileEntity.getFluidInteractionComponent().getInverseMode());
			
		    if(quarryTileEntity.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
		    	buttonList.get(0).displayString = ">";
		    }else{
		    	buttonList.get(0).displayString = "<";
		    }
		}
	}	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.quarryTileEntity.getName());
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 10, 255 << 24 | 255 << 16 | 255 << 8 | 255);

		float scale = 0.7F;
		String currentPosition = quarryTileEntity.getCurrentPosition().toString().substring(9, quarryTileEntity.getCurrentPosition().toString().length()-1);
		String area = "Area: " + WorldUtilities.getAreaBetweenCorners(quarryTileEntity.getStartingPosition(), quarryTileEntity.getEndPosition());
		String tutorial = "• Right click on the Quarry";
		String tutorial2 = "block with a populated";
		String tutorial3 = "Coordinate Marker to start";
		String tutorial4 = "quarrying!";
		String tutorial5 = "• Place a chest on";
		String tutorial6 = "top to collect items!";
		String tutorial7 = "• Power with RF Power.";
		String speed = "Ticks per Operation: " + quarryTileEntity.processingTime;
		String blocks = "Blocks per Operation: " + quarryTileEntity.getBlocksMinedPerTick();
		String energy = "RF per Block: " + quarryTileEntity.processingEnergyMult * 100;
		String fortune = "Fortune Level: " + quarryTileEntity.getFortuneMultiplier();
		
		int xOffset = 40;
		
		GL11.glScalef(scale, scale, scale);
		if(!quarryTileEntity.isAbleToMine()) {
			fontRenderer.drawString(tutorial, xSize / 2 - xOffset, 30, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial2, xSize / 2 - xOffset, 40, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial3, xSize / 2 - xOffset, 50, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial4, xSize / 2 - xOffset, 60, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial5, xSize / 2 - xOffset, 75, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial6, xSize / 2 - xOffset, 85, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString(tutorial7, xSize / 2 - xOffset, 100, GuiUtilities.getColor(200, 200, 200));
		}else if(quarryTileEntity.isDoneMining()) {
			fontRenderer.drawString("Quarrying Completed!", xSize / 2 - 26, 30, GuiUtilities.getColor(200, 200, 200));
		}else{
			fontRenderer.drawString("Current Position: ", xSize / 2 - fontRenderer.getStringWidth("Current Position: ") / 2+1, 30, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + currentPosition, xSize / 2 - xOffset, 40, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + area, xSize / 2 - xOffset, 55, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + speed, xSize / 2 - xOffset, 65, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + blocks, xSize / 2 - xOffset, 75, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + energy, xSize / 2 - xOffset, 85, GuiUtilities.getColor(200, 200, 200));
			fontRenderer.drawString("• " + fortune, xSize / 2 - xOffset, 95, GuiUtilities.getColor(200, 200, 200));
		}
		GL11.glScalef(1/scale, 1/scale, 1/scale);
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 65, 28, 30);
		this.drawGenericBackground(-30, 95, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
		this.drawSlot(guiLeft+30, guiTop+8, 116, 71);
		Gui.drawRect(guiLeft+30, guiTop+8, guiLeft+xSize-30, guiTop+ySize-87, GuiUtilities.getColor(0, 0, 0));
		
    	this.drawContainerSlots(quarryTileEntity, this.inventorySlots.inventorySlots);
    	
	}	
}


