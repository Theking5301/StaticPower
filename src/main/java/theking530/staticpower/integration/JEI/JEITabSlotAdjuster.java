package theking530.staticpower.integration.JEI;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import api.RectangleBounds;
import api.gui.tab.BaseGuiTab;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import theking530.staticpower.client.gui.BaseGuiContainer;

public class JEITabSlotAdjuster implements IAdvancedGuiHandler<BaseGuiContainer>{
	@Override
	public Class<BaseGuiContainer> getGuiContainerClass() {

		return BaseGuiContainer.class;
	}

	@Override
	public List<Rectangle> getGuiExtraAreas(BaseGuiContainer guiContainer) {

		List<Rectangle> tabBoxes = new ArrayList<>();

		for (BaseGuiTab tab : guiContainer.getTabManager().getRegisteredTabs()) {
			RectangleBounds rect = tab.getBounds();
			tabBoxes.add(new Rectangle((int)rect.getX(), (int)rect.getY(), (int)rect.getW(), (int)rect.getH()));
		}
		return tabBoxes;
	}

	@Nullable
	@Override
	public Object getIngredientUnderMouse(BaseGuiContainer guiContainer, int mouseX, int mouseY) {

		return null;
	}

}
