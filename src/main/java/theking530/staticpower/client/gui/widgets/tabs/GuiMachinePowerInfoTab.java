package theking530.staticpower.client.gui.widgets.tabs;

import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.tileentity.IEnergyUser;

public class GuiMachinePowerInfoTab extends GuiPowerInfoTab {

	private IEnergyUser energyUser;
	
	public GuiMachinePowerInfoTab(int width, int height, IEnergyUser machine) {
		super(80, 80, machine.getEnergyStorage());
		energyUser = machine;
	}
	@Override
	protected void setPowerInfoText() {
		String text = (EnumTextFormatting.GREEN + "Current I/O: =" + energyUser.getEnergyStorage().getEnergyIO() + " RF/t=" + EnumTextFormatting.AQUA + "Max Recieve:=" + energyUser.getEnergyStorage().getMaxReceive() + " RF/t=" + EnumTextFormatting.RED + "Max Usage:=" + energyUser.maxEnergyUsagePerTick()) + " RF/t";
		setText("Power Usage", text);
	}
}
