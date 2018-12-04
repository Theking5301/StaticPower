package api.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemButton extends StandardButton{
	
	private ItemStack itemIcon;
	
	public ItemButton(ItemStack icon, int width, int height, int xPos, int yPos) {
		super(width, height, xPos, yPos);
		itemIcon = icon;
	}
	public ItemButton(Item icon, int width, int height, int xPos, int yPos) {
		this(new ItemStack(icon), width, height, xPos, yPos);
	}
	
	@Override
	public void drawExtra() {
		if(isVisible()) {
			if(!itemIcon.isEmpty()) {
				drawButtonIcon();
			}
		}
	}
	public void drawButtonIcon() {
		int buttonLeft = owningGui.getGuiLeft() + xPosition+1;
		int buttonTop =  owningGui.getGuiTop() + yPosition+1;
		
		RenderItem customRenderer = Minecraft.getMinecraft().getRenderItem();
		customRenderer.renderItemIntoGUI(itemIcon, buttonLeft+1, buttonTop);
	}
}
