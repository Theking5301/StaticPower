package theking530.staticpower.machines.batteries;

import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.TextButton;
import api.gui.tab.BaseGuiTab.TabSide;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiPowerControlTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiPowerInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.client.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;

public class GuiBattery extends BaseGuiContainer {
	
	private TileEntityBattery battery;
	private TextButton inputUp;
	private TextButton inputDown;
	private TextButton outputUp;
	private TextButton outputDown;
	private GuiInfoTab infoTab;
	
	public GuiBattery(InventoryPlayer invPlayer, TileEntityBattery teSBattery) {
		super(new ContainerBattery(invPlayer, teSBattery), 176, 166);
		battery = teSBattery;
		
		registerWidget(new GuiPowerBarFromEnergyStorage(teSBattery, 80, 71, 16, 51));
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, teSBattery));
		getTabManager().registerTab(new GuiPowerControlTab(100, 70, teSBattery));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, true, teSBattery));
		getTabManager().registerTab(infoTab = new GuiInfoTab(80, 60));
		getTabManager().registerTab(new GuiPowerInfoTab(80, 60, teSBattery.getEnergyStorage()).setTabSide(TabSide.LEFT));
		
		infoTab.setTabSide(TabSide.LEFT);
		
		inputUp = new TextButton(20, 20, 5, 23, "+");
		inputDown = new TextButton(20, 20, 5, 48, "-");
		outputUp = new TextButton(20, 20, 151, 23, "+");
		outputDown = new TextButton(20, 20, 151, 48, "-");
		
		getButtonManager().registerButton(inputUp);
		getButtonManager().registerButton(inputDown);
		getButtonManager().registerButton(outputUp);
		getButtonManager().registerButton(outputDown);	
	}	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String input = (String.valueOf(battery.getInputLimit()) + " " + I18n.format("gui.RF/T"));
		String output = (String.valueOf(battery.getOutputLimit()) + " " + I18n.format("gui.RF/T"));
		
		String name = I18n.format(this.battery.getName());
		String inputName = I18n.format("mode.Input");
		String outputName = I18n.format("mode.Output");

		this.fontRenderer.drawString(inputName, this.xSize/2-this.fontRenderer.getStringWidth(inputName)/2 - 35, 35, 4210752);
		this.fontRenderer.drawString(input, this.xSize/2-this.fontRenderer.getStringWidth(input)/2 - 35, 45, 4210752);
		this.fontRenderer.drawString(outputName, this.xSize/2-this.fontRenderer.getStringWidth(outputName)/2 + 35, 35, 4210752);
		this.fontRenderer.drawString(output, this.xSize/2-this.fontRenderer.getStringWidth(output)/2 + 35, 45, 4210752);
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);		
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
	}
	@Override
	protected void drawExtra(float f, int i, int j) {
		drawGenericBackground();
		drawPlayerInventorySlots();
		
		infoTab.setText(I18n.format(this.battery.getName()), "Regular Click: 50=Shift Click: 100=Cntrl Click: 1=Right Click: x2");
	}		

	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		int deltaValue = 0;
		boolean input = false;
		if(button == inputUp) {
			deltaValue = 1;
			input = true;
		}else if(button == inputDown) {
			deltaValue = -1;
			input = true;
		}else if(button == outputUp) {
			deltaValue = 1;
		}else if(button == outputDown) {
			deltaValue = -1;
		}		
		deltaValue *= mouseButton == ClickedState.LEFT ? 1 : 10;
		if (isShiftKeyDown()) {
			deltaValue = (deltaValue*100);
		} else if (!isCtrlKeyDown()) {
			deltaValue = (deltaValue*50);
		}
		
		if(input) {
			battery.setInputLimit(Math.max(0, Math.min(battery.getInputLimit()+deltaValue, battery.getMaximumPowerIO())));
		}else{
			battery.setOutputLimit(Math.max(0,Math.min(battery.getOutputLimit()+deltaValue, battery.getMaximumPowerIO())));
		}
		
		IMessage msg = new PacketGuiBattery(battery.getInputLimit(), battery.getOutputLimit(), battery.getPos());
		PacketHandler.net.sendToServer(msg);
	}
}
