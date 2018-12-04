package theking530.staticpower.tileentity.solderingtable;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.client.gui.widgets.GuiDrawItem;
import theking530.staticpower.client.gui.widgets.tabs.GuiInfoTab;
import theking530.staticpower.client.gui.widgets.tabs.GuiSideConfigTab;
import theking530.staticpower.items.ModItems;

public class GuiSolderingTable extends BaseGuiContainer {

	private TileEntitySolderingTable solderingTable;
	private GuiInfoTab infoTab;
	
	public GuiSolderingTable(InventoryPlayer invPlayer, TileEntitySolderingTable teTable) {
		super(new ContainerSolderingTable(invPlayer, teTable), 176, 179);
		solderingTable = teTable;	

		infoTab = new GuiInfoTab(110, 80);
		getTabManager().registerTab(infoTab);
		getTabManager().registerTab(new GuiSideConfigTab(80, 80, false, teTable));
	}
	public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);
    	drawRect(guiLeft + 82, guiTop + 38, 176, 69, 3394815);
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.solderingTable.getName());	
		fontRenderer.drawString(name, this.xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		
		infoTab.setText("Soldering Table", "The soldering table=allows you to create=complex items by=combining multiple=basic items with a=soldering iron.");
	}
	
	@Override
	protected void drawExtra(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.SOLDERING_TABLE_GUI);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    	GuiDrawItem.drawItem(ModItems.SolderingIron, guiLeft, guiTop, 11, 17, this.zLevel, 0.5f);	  		
	}
}


