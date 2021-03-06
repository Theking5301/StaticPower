package theking530.staticpower.tileentities.digistorenetwork.patternstorage;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import theking530.staticcore.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.StaticPowerTileEntityGui;
import theking530.staticpower.utilities.MetricConverter;

public class GuiPatternStorage extends StaticPowerTileEntityGui<ContainerPatternStorage, TileEntityPatternStorage> {

	private GuiInfoTab infoTab;

	public GuiPatternStorage(ContainerPatternStorage container, PlayerInventory invPlayer, ITextComponent name) {
		super(container, invPlayer, name, 176, 150);
	}

	@Override
	public void initializeGui() {
		getTabManager().registerTab(infoTab = new GuiInfoTab(getTileEntity().getDisplayName().getString(), 100));
	}

	@Override
	public void updateData() {
		// Update the info tab.
		infoTab.clear();
		infoTab.addLine("desc", new StringTextComponent("Stores crafting patterns that can be atomically reconstructed into items and blocks!."));
		infoTab.addLineBreak();

		// Pass the itemstack count through the metric converter.
		MetricConverter count = new MetricConverter(getTileEntity().patternInventory.getSlots());
		infoTab.addKeyValueLine("max", new StringTextComponent("Max Patterns"),
				new StringTextComponent(TextFormatting.WHITE.toString()).append(new StringTextComponent(count.getValueAsString(true))), TextFormatting.RED);
	}
}
