package theking530.staticpower.tileentities.powered.battery;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import theking530.api.power.CapabilityStaticVolt;
import theking530.staticcore.gui.widgets.button.StandardButton;
import theking530.staticcore.gui.widgets.button.StandardButton.MouseButton;
import theking530.staticcore.gui.widgets.button.TextButton;
import theking530.staticcore.gui.widgets.tabs.BaseGuiTab.TabSide;
import theking530.staticcore.gui.widgets.tabs.GuiInfoTab;
import theking530.staticcore.gui.widgets.tabs.GuiPowerInfoTab;
import theking530.staticcore.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticcore.gui.widgets.tabs.redstonecontrol.GuiTileEntityRedstoneTab;
import theking530.staticcore.gui.widgets.valuebars.GuiPowerBarFromEnergyStorage;
import theking530.staticpower.client.gui.StaticPowerTileEntityGui;
import theking530.staticpower.client.utilities.GuiTextUtilities;
import theking530.staticpower.network.NetworkMessage;
import theking530.staticpower.network.StaticPowerMessageHandler;

public class GuiBattery extends StaticPowerTileEntityGui<ContainerBattery, TileEntityBattery> {
	private TextButton inputUp;
	private TextButton inputDown;
	private TextButton outputUp;
	private TextButton outputDown;

	public GuiBattery(ContainerBattery container, PlayerInventory invPlayer, ITextComponent name) {
		super(container, invPlayer, name, 176, 166);
	}

	@Override
	public void initializeGui() {
		registerWidget(new GuiPowerBarFromEnergyStorage(getTileEntity().energyStorage.getStorage(), 75, 20, 26, 51));

		GuiInfoTab infoTab;
		getTabManager().registerTab(infoTab = new GuiInfoTab("Battery", 120));
		infoTab.addLine("desc1", new StringTextComponent("A Battery stores power for later usage."));
		infoTab.addLineBreak();
		infoTab.addLine("desc2", new StringTextComponent("Holding alt and shift while left/right clicking on the buttons will change the rates the limits are altered."));

		getTabManager().registerTab(new GuiTileEntityRedstoneTab(getTileEntity().redstoneControlComponent));
		getTabManager().registerTab(new GuiSideConfigTab(true, getTileEntity()));

		getTabManager().registerTab(new GuiPowerInfoTab(getTileEntity().energyStorage).setTabSide(TabSide.LEFT), true);

		registerWidget(inputUp = new TextButton(52, 23, 20, 20, "+", this::buttonPressed));
		registerWidget(inputDown = new TextButton(52, 48, 20, 20, "-", this::buttonPressed));
		registerWidget(outputUp = new TextButton(104, 23, 20, 20, "+", this::buttonPressed));
		registerWidget(outputDown = new TextButton(104, 48, 20, 20, "-", this::buttonPressed));
	}

	@Override
	protected void drawForegroundExtras(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawForegroundExtras(stack, partialTicks, mouseX, mouseY);

		// Render the input rate string.
		font.drawString(stack, "Input", 15, 32, 4210752);
		String inputRateString = GuiTextUtilities.formatEnergyRateToString(getTileEntity().getInputLimit()).getString();
		font.drawString(stack, inputRateString, 28 - (font.getStringWidth(inputRateString) / 2), 42, 4210752);

		// Render the output rate string.
		font.drawString(stack, "Output", 132, 32, 4210752);
		String outputRateString = GuiTextUtilities.formatEnergyRateToString(getTileEntity().getOutputLimit()).getString();
		font.drawString(stack, outputRateString, 149 - (font.getStringWidth(outputRateString) / 2), 42, 4210752);

		// Add tooltip for the actual value of the input.
		List<ITextComponent> tooltips = new ArrayList<ITextComponent>();

		// Render the tooltips.
		this.func_243308_b(stack, tooltips, mouseX, mouseY);
	}

	@Override
	protected void getExtraTooltips(List<ITextComponent> tooltips, MatrixStack stack, int mouseX, int mouseY) {
		String inputRateString = GuiTextUtilities.formatEnergyRateToString(getTileEntity().getInputLimit()).getString();
		String outputRateString = GuiTextUtilities.formatEnergyRateToString(getTileEntity().getOutputLimit()).getString();

		if (mouseX > guiLeft + 28 - (font.getStringWidth(inputRateString) / 2) && mouseX < guiLeft + 28 + (font.getStringWidth(inputRateString) / 2) && mouseY > this.guiTop + 41
				&& mouseY < this.guiTop + 50) {
			tooltips.add(new StringTextComponent(inputRateString));
		}

		// Add tooltip for the actual value of the output.
		if (mouseX > guiLeft + 149 - (font.getStringWidth(outputRateString) / 2) && mouseX < guiLeft + 149 + (font.getStringWidth(outputRateString) / 2) && mouseY > this.guiTop + 41
				&& mouseY < this.guiTop + 50) {
			tooltips.add(new StringTextComponent(outputRateString));
		}
	}

	public void buttonPressed(StandardButton button, MouseButton mouseButton) {
		long deltaValue = 0;
		boolean input = false;
		if (button == inputUp) {
			deltaValue = 1;
			input = true;
		} else if (button == inputDown) {
			deltaValue = -1;
			input = true;
		} else if (button == outputUp) {
			deltaValue = 1;
		} else if (button == outputDown) {
			deltaValue = -1;
		}

		int maxIOAdjustment = getTileEntity().getMaximumPowerIO() > 100 ? 50 : 5;

		deltaValue *= mouseButton == MouseButton.LEFT ? 1 : 10;
		if (Screen.hasShiftDown()) {
			deltaValue = (deltaValue * maxIOAdjustment * 2);
		} else if (!Screen.hasAltDown()) {
			deltaValue = (deltaValue * maxIOAdjustment);
		}

		deltaValue = CapabilityStaticVolt.convertSVtomSV(deltaValue);

		if (input) {
			getTileEntity().setInputLimit(Math.max(0, Math.min(getTileEntity().getInputLimit() + deltaValue, getTileEntity().getMaximumPowerIO())));
		} else {
			getTileEntity().setOutputLimit(Math.max(0, Math.min(getTileEntity().getOutputLimit() + deltaValue, getTileEntity().getMaximumPowerIO())));
		}

		// Create the packet.
		NetworkMessage msg = new BatteryControlSyncPacket(getTileEntity().getInputLimit(), getTileEntity().getOutputLimit(), getTileEntity().getPos());
		// Send a packet to the server with the updated values.
		StaticPowerMessageHandler.MAIN_PACKET_CHANNEL.sendToServer(msg);
	}
}
