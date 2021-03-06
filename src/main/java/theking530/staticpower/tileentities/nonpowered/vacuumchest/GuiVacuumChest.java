package theking530.staticpower.tileentities.nonpowered.vacuumchest;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import theking530.staticcore.gui.widgets.tabs.BaseGuiTab.TabSide;
import theking530.staticcore.gui.widgets.tabs.GuiInfoTab;
import theking530.staticcore.gui.widgets.tabs.GuiMachineFluidTab;
import theking530.staticcore.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticcore.gui.widgets.tabs.redstonecontrol.GuiTileEntityRedstoneTab;
import theking530.staticcore.gui.widgets.tabs.slottabs.GuiFluidContainerTab;
import theking530.staticcore.gui.widgets.valuebars.GuiFluidBarFromTank;
import theking530.staticpower.client.gui.StaticPowerTileEntityGui;
import theking530.staticpower.init.ModFluids;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;

public class GuiVacuumChest extends StaticPowerTileEntityGui<ContainerVacuumChest, TileEntityVacuumChest> {

	private GuiInfoTab infoTab;
	private GuiFluidBarFromTank fluidBar;
	private GuiMachineFluidTab fluidTab;
	private GuiFluidContainerTab fluidContainerTab;

	public GuiVacuumChest(ContainerVacuumChest container, PlayerInventory invPlayer, ITextComponent name) {
		super(container, invPlayer, name, 176, 185);
	}

	@Override
	public void initializeGui() {
		getTabManager().registerTab(infoTab = new GuiInfoTab(getTitle(), 100));
		getTabManager().registerTab(new GuiTileEntityRedstoneTab(getTileEntity().redstoneControlComponent));
		getTabManager().registerTab(new GuiSideConfigTab(false, getTileEntity()));

		registerWidget(fluidBar = new GuiFluidBarFromTank(getTileEntity().fluidTankComponent, 176, 20, 16, 74, MachineSideMode.Output, getTileEntity()));

		fluidTab = (GuiMachineFluidTab) new GuiMachineFluidTab(getTileEntity().fluidTankComponent).setTabSide(TabSide.LEFT);
		fluidContainerTab = (GuiFluidContainerTab) new GuiFluidContainerTab(this.container, getTileEntity().fluidContainerComponent, Items.BUCKET, ModFluids.Mash.getBucket())
				.setTabSide(TabSide.LEFT);
		setOutputSlotSize(16);

		if (getTileEntity().showTank()) {
			xSize = 200;
			this.setDesieredGuiSize(200, ySize);
		} else {
			fluidBar.setVisible(false);
		}
	}

	@Override
	public void updateData() {
		// Update the input tab.
		infoTab.clear();
		infoTab.addLine("desc", new StringTextComponent("Vacuums items in a nearby radius"));
		infoTab.addKeyValueTwoLiner("radius", new StringTextComponent("Radius"), new StringTextComponent(String.valueOf(getTileEntity().getRadius())), TextFormatting.AQUA);

		// Change the size of the GUI depending on if there is a XP upgrade present.
		if (!getTileEntity().showTank()) {
			setGuiSizeTarget(176, 185);
			fluidBar.setVisible(false);
			getTabManager().removeTab(fluidTab);
			getTabManager().removeTab(fluidContainerTab);
		} else {
			setGuiSizeTarget(200, 185);
			fluidBar.setVisible(true);
			getTabManager().registerTab(fluidTab);
			getTabManager().registerTab(fluidContainerTab);
		}
	}
}
