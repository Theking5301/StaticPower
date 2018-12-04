package theking530.staticpower.tileentity.astralquary.brain;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiRedstoneTab;

public class GuiAstralQuarryBrain extends BaseGuiContainer {
	
	private TileEntityAstralQuarryBrain astralQuary;
	private GuiInfoTab infoTab;

	public GuiAstralQuarryBrain(InventoryPlayer invPlayer, TileEntityAstralQuarryBrain teAstralQuary) {
		super(new ContainerAstralQuarryBrain(invPlayer, teAstralQuary), 176, 176);
		astralQuary = teAstralQuary;	
		
		
		infoTab = new GuiInfoTab(100, 65);
		getTabManager().registerTab(infoTab);
		getTabManager().registerTab(new GuiRedstoneTab(100, 85, astralQuary));
		//getTabManager().registerTab(new GuiSideConfigTab(80, 80, barrel));
		getTabManager().setInitiallyOpenTab(infoTab);
		
		this.setOutputSlotSize(16);
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(astralQuary.getName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
	}	
	@Override
	protected void drawExtra(float f, int i, int j) {			
		drawGenericBackground();
		drawContainerSlots(astralQuary, inventorySlots.inventorySlots);
		drawPlayerInventorySlots();

		infoTab.setText(I18n.format(astralQuary.getName()), "test");
	}	
}



