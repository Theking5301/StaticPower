package api.gui;

import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;

public interface IInteractableGui {

	public default void buttonPressed(BaseButton button, ClickedState mouseButtn) {};
	public default void buttonHovered(BaseButton button) {};
	
	public int getGuiTop();
	public int getGuiLeft();
}
