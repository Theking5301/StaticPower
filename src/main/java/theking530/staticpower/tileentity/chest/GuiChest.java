package theking530.staticpower.tileentity.chest;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.client.gui.BaseGuiContainer;
import theking530.staticpower.client.gui.GuiTextures;

public class GuiChest extends BaseGuiContainer {

	private Tier tier;
	private TileEntityBaseChest chest;
	
	public GuiChest(Tier tier, InventoryPlayer invPlayer, TileEntityBaseChest teChest) {
		super(new ContainerChest(tier, invPlayer, teChest), 100, 100);
		this.tier = tier;
		this.chest = teChest;
		
		if(tier == Tier.STATIC) {
			this.setDesieredGuiSize(176, 205);
		}else if(tier == Tier.ENERGIZED) {
			this.setDesieredGuiSize(176, 256);
		}else{
			this.setDesieredGuiSize(230, 254);
		}
		
		this.setOutputSlotSize(16);
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.chest.getName());

		this.fontRenderer.drawString(name, 7, 6,4210752 );
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}	
	@Override
	protected void drawExtra(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if(tier == Tier.STATIC) {
			this.drawGenericBackground();
			this.drawContainerSlots(chest, this.inventorySlots.inventorySlots);
			this.drawPlayerInventorySlots();
		}else if(tier == Tier.ENERGIZED) {
			this.drawGenericBackground();
			this.drawContainerSlots(chest, this.inventorySlots.inventorySlots);
			this.drawPlayerInventorySlots();
		}else{
			Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.LCHEST_GUI);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
	}
}
