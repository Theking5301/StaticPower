package theking530.staticpower.tileentity.digistorenetwork.digistore;

import java.text.DecimalFormat;
import java.util.Arrays;

import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.TextButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.GuiDrawItem;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.handlers.PacketHandler;

public class GuiDigistore extends BaseGuiContainer {
	
	private TileEntityDigistore barrel;
	private GuiInfoTab infoTab;
	private TextButton lockedButton;
	
	public GuiDigistore(InventoryPlayer invPlayer, TileEntityDigistore teBarrel) {
		super(new ContainerDigistore(invPlayer, teBarrel), 176, 150);
		barrel = teBarrel;	
		
		lockedButton = new TextButton(50, 18, 20, 20, "Locked");
		lockedButton.setToggleable(true);
		lockedButton.setText(barrel.isLocked() ? "Locked" : "Unlocked");	
		lockedButton.setToggled(barrel.isLocked());
		getButtonManager().registerButton(lockedButton);
		
		infoTab = new GuiInfoTab(100, 65);
		getTabManager().registerTab(infoTab);
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, barrel));
		//getTabManager().registerTab(new GuiSideConfigTab(80, 80, barrel));
		getTabManager().setInitiallyOpenTab(infoTab);
	}
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		if(barrel != null) {
			barrel.setLocked(!barrel.isLocked());
			button.setToggled(barrel.isLocked());
			lockedButton.setText(barrel.isLocked() ? "Locked" : "Unlocked");	

			IMessage msg = new PacketLockDigistore(barrel.isLocked(), barrel.getPos());
			PacketHandler.net.sendToServer(msg);
		}		
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(barrel.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		

	}	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
    	if(!barrel.getStoredItem().isEmpty()) {
    		if(mouseX >= guiLeft + 80 && mouseX <= guiLeft + 96 && mouseY >= guiTop + 25 && mouseY <= guiTop + 41) {
    			drawRect(guiLeft + 76, guiTop + 21, guiLeft + 100, guiTop + 45, GuiUtilities.getColor(255, 255, 255, 150));
        		renderToolTip(this.barrel.getStoredItem(), mouseX, mouseY);
    		}
    	}
	}
	@Override
	protected void drawExtra(float f, int i, int j) {			
		drawGenericBackground();
		drawPlayerInventorySlots(guiLeft+8, guiTop+ySize-83);

		drawSlot(guiLeft + 76, guiTop + 21, 24, 24);
		
		drawSlot(guiLeft + 152, guiTop + 9, 16, 16);
		drawSlot(guiLeft + 152, guiTop + 27, 16, 16);
		drawSlot(guiLeft + 152, guiTop + 45, 16, 16);
		

		RenderHelper.enableGUIStandardItemLighting();
    	GuiDrawItem.drawItem(barrel.getStoredItem(), guiLeft, guiTop, 80, 25, this.zLevel, 1.0f);
		RenderHelper.disableStandardItemLighting();
		
    	String storedAmountString = "" + barrel.getStoredAmount();
    	drawStringWithSize(storedAmountString, guiLeft + 100, guiTop + 45, 0.6f,  GuiUtilities.getColor(255, 255, 255), true);
    	
		DecimalFormat format = new DecimalFormat("##.###");
    	String text = ("Stores a large=amount of a single=item. ==" + EnumTextFormatting.RED + "Max: " + EnumTextFormatting.AQUA + format.format(barrel.getMaxStoredAmount()) + " Items");
    	String[] splitMsg = text.split("=");
		infoTab.setText(I18n.format(barrel.getName()), Arrays.asList(splitMsg));
	}	
}



