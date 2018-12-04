package theking530.staticpower.tileentity.digistorenetwork.manager;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;

public class GuiDigistoreManager extends BaseGuiContainer {
	
	private TileEntityDigistoreManager barrel;
	private GuiInfoTab infoTab;
	
	public GuiDigistoreManager(InventoryPlayer invPlayer, TileEntityDigistoreManager teBarrel) {
		super(new ContainerDigistoreManager(invPlayer, teBarrel), 176, 150);
		barrel = teBarrel;	
		
		infoTab = new GuiInfoTab(100, 65);
		getTabManager().registerTab(infoTab);
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, barrel));
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, barrel));
		getTabManager().setInitiallyOpenTab(infoTab);
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(barrel.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		

	}	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

	}
	@Override
	protected void drawExtra(float f, int i, int j) {			
		drawGenericBackground();
		drawPlayerInventorySlots(guiLeft+8, guiTop+ySize-83);
    
	}	
}



