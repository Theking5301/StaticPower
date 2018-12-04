package theking530.staticpower.machines.refinery.controller;

import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.TextButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.handlers.PacketHandler;

public class GuiFluidRefineryController extends BaseGuiContainer {
	
	private TileEntityFluidRefineryController refineryController;
	private GuiInfoTab infoTab;

	private TextButton increaseStaticFluidFlow;
	private TextButton increaseEnergizedFluidFlow;
	private TextButton increaseLumumFluidFlow;

	private TextButton decreaseStaticFluidFlow;
	private TextButton decreaseEnergizedFluidFlow;
	private TextButton decreaseLumumFluidFlow;
	
	private TextButton disableStaticFluidFlow;
	private TextButton disableEnergizedFluidFlow;
	private TextButton disableLumumFluidFlow;
	
	public GuiFluidRefineryController(InventoryPlayer invPlayer, TileEntityFluidRefineryController teRefineryController) {
		super(new ContainerFluidRefineryController(invPlayer, teRefineryController), 176, 180);
		refineryController = teRefineryController;	
		
		registerWidget(new GuiFluidBarFromTank(teRefineryController.getStaticInputTank(), -126, 80, 16, 60, Mode.Input, teRefineryController));
		registerWidget(new GuiFluidBarFromTank(teRefineryController.getEnergizedInputTank(), -86, 80, 16, 60, Mode.Input, teRefineryController));
		registerWidget(new GuiFluidBarFromTank(teRefineryController.getLumumInputTank(), -46, 80, 16, 60, Mode.Input, teRefineryController));
		
		registerWidget(new GuiFluidBarFromTank(teRefineryController.getOutputTank(), -125, 170, 110, 40, Mode.Output, teRefineryController));
		
		infoTab = new GuiInfoTab(100, 65);
		getTabManager().registerTab(infoTab);
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, refineryController));
		getTabManager().setInitiallyOpenTab(infoTab);
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, refineryController));
		
		getButtonManager().registerButton(increaseStaticFluidFlow = new TextButton(16, 16, -106, 28, "^"));
		getButtonManager().registerButton(increaseEnergizedFluidFlow = new TextButton(16, 16, -66, 28, "^"));
		getButtonManager().registerButton(increaseLumumFluidFlow = new TextButton(16, 16, -26, 28, "^"));
		
		getButtonManager().registerButton(decreaseStaticFluidFlow = new TextButton(16, 16, -106, 58, "v"));
		getButtonManager().registerButton(decreaseEnergizedFluidFlow = new TextButton(16, 16, -66, 58, "v"));
		getButtonManager().registerButton(decreaseLumumFluidFlow = new TextButton(16, 16, -26, 58, "v"));
		
		getButtonManager().registerButton(disableStaticFluidFlow = new TextButton(16, 16, -126, 84, "O"));
		getButtonManager().registerButton(disableEnergizedFluidFlow = new TextButton(16, 16, -86, 84, "O"));
		getButtonManager().registerButton(disableLumumFluidFlow = new TextButton(16, 16, -46, 84, "O"));
		
		disableStaticFluidFlow.setToggleable(true);
		disableEnergizedFluidFlow.setToggleable(true);
		disableLumumFluidFlow.setToggleable(true);
		
		this.setOutputSlotSize(16);
	}
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		if(button == increaseStaticFluidFlow) {
			refineryController.setStaticFlowRate((short) (refineryController.getStaticFlowRate()+1));
		}else if(button == increaseEnergizedFluidFlow) {
			refineryController.setEnergizedFlowRate((short) (refineryController.getEnergizedFlowRate()+1));
		}else if(button == increaseLumumFluidFlow) {
			refineryController.setLumumFlowRate((short) (refineryController.getLumumFlowRate()+1));
		}else if(button == decreaseStaticFluidFlow) {
			refineryController.setStaticFlowRate((short) (refineryController.getStaticFlowRate()-1));
		}else if(button == decreaseEnergizedFluidFlow) {
			refineryController.setEnergizedFlowRate((short) (refineryController.getEnergizedFlowRate()-1));
		}else if(button == decreaseLumumFluidFlow) {
			refineryController.setLumumFlowRate((short) (refineryController.getLumumFlowRate()-1));
		}else if(button == disableStaticFluidFlow) {
			refineryController.setAllowStaticFlow(!refineryController.getAllowStaticFlow());
		}else if(button == disableEnergizedFluidFlow) {
			refineryController.setAllowEnergizedFlow(!refineryController.getAllowEnergizedFlow());
		}else if(button == disableLumumFluidFlow) {
			refineryController.setAllowLumumFlow(!refineryController.getAllowLumumFlow());
		}
		
		IMessage msg = new PacketFluidRefineryController(refineryController, refineryController.getPos());
		PacketHandler.net.sendToServer(msg);
		
		updateButtonStates();
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(refineryController.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		
		String staticFlow = refineryController.getStaticFlowRate() + "";
		String energizedFlow = refineryController.getEnergizedFlowRate() + "";
		String lumumFlow =  refineryController.getLumumFlowRate() + "";
		
		int leftPanelXOffset = -108;
		int color = GuiUtilities.getColor(255, 255, 255);
		fontRenderer.drawStringWithShadow(lumumFlow, this.xSize / 2 - this.fontRenderer.getStringWidth(lumumFlow) / 2 + leftPanelXOffset+2 , 47, color);
		fontRenderer.drawStringWithShadow(energizedFlow, this.xSize / 2 - this.fontRenderer.getStringWidth(energizedFlow) / 2 + leftPanelXOffset-38 , 47, color);
		fontRenderer.drawStringWithShadow(staticFlow, this.xSize / 2 - this.fontRenderer.getStringWidth(staticFlow) / 2 + leftPanelXOffset-78 , 47, color);
		
		fontRenderer.drawStringWithShadow("L", this.xSize / 2 + leftPanelXOffset-20 , 8, color);
		fontRenderer.drawStringWithShadow("E", this.xSize / 2 + leftPanelXOffset-61 , 8, color);
		fontRenderer.drawStringWithShadow("S", this.xSize / 2 + leftPanelXOffset-101 , 8, color);
		
		fontRenderer.drawString("Mixing Chamber", this.xSize / 2 + leftPanelXOffset-85 , 118, 4210752);
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {			
		drawGenericBackground();
		this.drawGenericBackground(-135, 0, 130, 110);
		this.drawGenericBackground(-135, 112, 130, 68);
		drawContainerSlots(refineryController, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();

		
		drawVerticalBar(28, 20, 8, 16, refineryController.getPurifierAmount()/100.0f, GuiUtilities.getColor(150, 75, 255, 255));		
		drawVerticalBar(28, 44, 8, 16, refineryController.getReagentAmount()/100.0f, GuiUtilities.getColor(0, 255, 0, 255));		
		drawVerticalBar(28, 68, 8, 16, refineryController.getNutralizerAmount()/100.0f, GuiUtilities.getColor(200, 0, 50, 255));
		
		infoTab.setText(I18n.format(refineryController.getName()), "Refinery");
	}	
	private void updateButtonStates() {
		disableStaticFluidFlow.setToggled(refineryController.getAllowStaticFlow());
		disableEnergizedFluidFlow.setToggled(refineryController.getAllowEnergizedFlow());
		disableLumumFluidFlow.setToggled(refineryController.getAllowLumumFlow());
	}
}



