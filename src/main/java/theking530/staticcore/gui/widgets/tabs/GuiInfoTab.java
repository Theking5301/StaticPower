package theking530.staticcore.gui.widgets.tabs;

import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import theking530.staticcore.gui.drawables.ItemDrawable;
import theking530.staticcore.utilities.Color;
import theking530.staticpower.client.gui.GuiTextures;

public class GuiInfoTab extends AbstractInfoTab {
	public GuiInfoTab(ITextComponent title, int width) {
		this(title.getFormattedText(), width);
	}

	public GuiInfoTab(int width) {
		this("Info", width);
	}

	public GuiInfoTab(String title, int width) {
		super(title, width, GuiTextures.GREEN_TAB, new ItemDrawable(Items.PAPER), new Color(0, 242, 255));
	}
}