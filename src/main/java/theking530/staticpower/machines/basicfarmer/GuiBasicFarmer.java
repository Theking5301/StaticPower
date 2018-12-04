package theking530.staticpower.machines.basicfarmer;

import api.gui.button.BaseButton;
import api.gui.button.TextButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.buttons.ArrowButton;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiMachinePowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.machines.tileentitycomponents.PacketFluidContainerComponent;

public class GuiBasicFarmer extends BaseGuiContainer{
	
	private TileEntityBasicFarmer tileEntityFarmer;
	private GuiInfoTab infoTab;
	private TextButton drawPreviewButton;
	
	public GuiBasicFarmer(InventoryPlayer invPlayer, TileEntityBasicFarmer teFarmer) {
		super(new ContainerBasicFarmer(invPlayer, teFarmer), 176, 172);
		tileEntityFarmer = teFarmer;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teFarmer, 8, 54, 16, 46));
		registerWidget(new GuiFluidBarFromTank(teFarmer.fluidTank, 150, 68, 16, 60, Mode.Input, teFarmer));

		getTabManager().registerTab(infoTab = new GuiInfoTab(100, 100));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teFarmer));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teFarmer));			
		getTabManager().registerTab(new GuiMachinePowerInfoTab(80, 80, teFarmer).setTabSide(TabSide.LEFT).setOffsets(-31, 0));
		getTabManager().setInitiallyOpenTab(infoTab);
		
		drawPreviewButton = new TextButton(14, 14, 151, 72, "▦");
		drawPreviewButton.setTooltip("Draw Preview");
		drawPreviewButton.setToggleable(true);
		getButtonManager().registerButton(drawPreviewButton);
		setOutputSlotSize(16);
	    drawPreviewButton.setToggled(tileEntityFarmer.getShouldDrawRadiusPreview());
		
		setOutputSlotSize(16);
	}
	@Override
	public void initGui() {
		super.initGui();
	    buttonList.add(new ArrowButton(1, guiLeft-24, guiTop+30, 16, 10, "<"));
		updateButton();
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		IMessage msg = new PacketFluidContainerComponent(tileEntityFarmer.getFluidInteractionComponent().getInverseMode(), tileEntityFarmer.getComponents().indexOf(tileEntityFarmer.getFluidInteractionComponent()), tileEntityFarmer.getPos());
		PacketHandler.net.sendToServer(msg);
		tileEntityFarmer.getFluidInteractionComponent().setMode(tileEntityFarmer.getFluidInteractionComponent().getInverseMode());
		updateButton();
	}
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		tileEntityFarmer.setShouldDrawRadiusPreview(button.isToggled());
	}
	public void updateButton() {
	    if(tileEntityFarmer.getFluidInteractionComponent().getMode() == FluidContainerInteractionMode.FILL) {
	    	buttonList.get(0).displayString = ">";
	    }else{
	    	buttonList.get(0).displayString = "<";
	    }
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		super.drawGuiContainerForegroundLayer(i, j);
		String name = I18n.format(this.tileEntityFarmer.getName());
	
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);	
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {
		this.drawGenericBackground(-30, 5, 28, 60);
		this.drawGenericBackground(-30, 70, 28, 64);
		this.drawGenericBackground();
		this.drawPlayerInventorySlots();
		
    	this.drawContainerSlots(tileEntityFarmer, this.inventorySlots.inventorySlots);
    	
		infoTab.setText("Farmer", "Farms plants in a " + EnumTextFormatting.YELLOW +  tileEntityFarmer.getRadius() + " block=radius.==Requires " + EnumTextFormatting.DARK_AQUA + "water" + EnumTextFormatting.REGULAR + " to operate=but other fluids may yield=better growth results...==Current Growth Factor: " + EnumTextFormatting.GOLD + tileEntityFarmer.getGrowthBonus() + "%");
	}
}


