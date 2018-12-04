package theking530.staticpower.client.gui.widgets.tabs;

import org.lwjgl.opengl.GL11;

import api.gui.IInteractableGui;
import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.ButtonManager;
import api.gui.button.ItemButton;
import api.gui.tab.BaseGuiTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.RedstoneModeList.RedstoneMode;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.tileentity.IRedstoneConfigurable;

public class GuiRedstoneTab extends BaseGuiTab implements IInteractableGui {

	public TileEntity tileEntity;
	private FontRenderer fontRenderer;

	private ButtonManager buttonManager;
	
	public ItemButton ignoreRedstoneButton;
	public ItemButton lowRedstoneButton;
	public ItemButton highRedstoneButton;
	
	public GuiRedstoneTab(int width, int height, TileEntity te) {
		super(100, 85, GuiTextures.RED_TAB, Items.REDSTONE);
		fontRenderer = Minecraft.getMinecraft().fontRenderer;
		tileEntity = te;
		
		buttonManager = new ButtonManager(this);
		
		ignoreRedstoneButton = new ItemButton(Items.GUNPOWDER, 20, 20, 23, 30);
		lowRedstoneButton = new ItemButton(Items.REDSTONE, 20, 20, 53, 30);
		highRedstoneButton = new ItemButton(Item.getItemFromBlock(Blocks.REDSTONE_TORCH), 20, 20, 83, 30);
		
		ignoreRedstoneButton.setClickSoundPitch(0.75f);
		lowRedstoneButton.setClickSoundPitch(0.85f);
		
		buttonManager.registerButton(ignoreRedstoneButton);
		buttonManager.registerButton(lowRedstoneButton);
		buttonManager.registerButton(highRedstoneButton);
		
		if(tileEntity instanceof IRedstoneConfigurable) {
			RedstoneMode currentMode = ((IRedstoneConfigurable)tileEntity).getRedstoneMode();
			if(currentMode == RedstoneMode.Ignore) {
				ignoreRedstoneButton.setToggled(true);
			}else if(currentMode == RedstoneMode.Low) {
				lowRedstoneButton.setToggled(true);
			}else{
				highRedstoneButton.setToggled(true);
			}
		}
	}
	@Override
	public void drawExtra(int xPos, int yPos, float partialTicks) {
		if(isOpen()) {
			drawButtonBG(xPos-3, yPos-32);	
			buttonManager.drawButtons(xPos, yPos);
			drawText(xPos+5, yPos-35);
		}
	}
	public void drawText(int xPos, int yPos) {
		String tabName = EnumTextFormatting.YELLOW + "Redstone Config";
		String redstoneMode = "Mode: ";

		modeText(xPos, yPos);	
		fontRenderer.drawStringWithShadow(redstoneMode, xPos-this.fontRenderer.getStringWidth(redstoneMode)/2 + 24, yPos+95, 16777215);
		fontRenderer.drawStringWithShadow(tabName, xPos-this.fontRenderer.getStringWidth(tabName)/2 + 58, yPos+43, 16777215);		
	}
	public void modeText(int tabLeft, int tabTop) {
		if(tileEntity instanceof IRedstoneConfigurable) {
			IRedstoneConfigurable entity = (IRedstoneConfigurable)tileEntity;
			GlStateManager.disableLighting();
			if(entity.getRedstoneMode() == RedstoneMode.Low) {
				fontRenderer.drawStringWithShadow("Low", tabLeft + 37, tabTop+95, 16777215);
				fontRenderer.drawStringWithShadow("This machine will", tabLeft + 8, tabTop+110, 16777215);		
				fontRenderer.drawStringWithShadow("only operate with no", tabLeft + 8, tabTop+118, 16777215);	
				fontRenderer.drawStringWithShadow("signal.", tabLeft + 8, tabTop+127, 16777215);
			} else if(entity.getRedstoneMode() == RedstoneMode.High) {
				fontRenderer.drawStringWithShadow("High", tabLeft + 37, tabTop+95, 16777215);
				fontRenderer.drawStringWithShadow("This machine will", tabLeft + 8, tabTop+110, 16777215);		
				fontRenderer.drawStringWithShadow("only operate with a", tabLeft + 8, tabTop+118, 16777215);	
				fontRenderer.drawStringWithShadow("redstone signal.", tabLeft + 8, tabTop+127, 16777215);
			} else if(entity.getRedstoneMode() == RedstoneMode.Ignore) {
				fontRenderer.drawStringWithShadow("Ignore", tabLeft + 37, tabTop+95, 16777215);	
				fontRenderer.drawStringWithShadow("This machine will", tabLeft + 8, tabTop+110, 16777215);		
				fontRenderer.drawStringWithShadow("ignore any redstone", tabLeft + 8, tabTop+118, 16777215);	
				fontRenderer.drawStringWithShadow("signal.", tabLeft + 8, tabTop+127, 16777215);
			}
		}
	}
	public void drawButtonBG(int xPos, int yPos) {
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.BUTTON_BG);
		GlStateManager.color(1, 1, 1);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xPos+114, yPos+88, 0).tex(0,1).endVertex();
		vertexbuffer.pos(xPos+114, yPos+57, 0).tex(0,0).endVertex();
		vertexbuffer.pos(xPos+17, yPos+57, 0).tex(1,0).endVertex();
		vertexbuffer.pos(xPos+17, yPos+88, 0).tex(1,1).endVertex();	
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);

	}
	@Override
	protected void handleExtraMouseInteraction(int mouseX, int mouseY, int button) {
		ignoreRedstoneButton.handleMouseInteraction(mouseX, mouseY, button);
		lowRedstoneButton.handleMouseInteraction(mouseX, mouseY, button);
		highRedstoneButton.handleMouseInteraction(mouseX, mouseY, button);	
	}
	@Override
	protected void handleExtraKeyboardInteraction(char par1, int par2) {

	}
	@Override
	protected void handleExtraMouseMove(int x, int y) {
		buttonManager.handleMouseMoveInteraction(x, y);
	}
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		if(tileEntity != null) {
			if(tileEntity instanceof IRedstoneConfigurable) {
				
				ignoreRedstoneButton.setToggled(false);
				lowRedstoneButton.setToggled(false);
				highRedstoneButton.setToggled(false);
				button.setToggled(true);
				
				IRedstoneConfigurable entity = (IRedstoneConfigurable)tileEntity;		
				if(ignoreRedstoneButton == button) {
					entity.setRedstoneMode(RedstoneMode.Ignore);
					IMessage msg = new PacketRedstoneTab(0, tileEntity.getPos());
					PacketHandler.net.sendToServer(msg);
				}
				if(lowRedstoneButton == button) {
					entity.setRedstoneMode(RedstoneMode.Low);	
					IMessage msg = new PacketRedstoneTab(1, tileEntity.getPos());
					PacketHandler.net.sendToServer(msg);
				}
				if(highRedstoneButton == button) {
					entity.setRedstoneMode(RedstoneMode.High);	
					IMessage msg = new PacketRedstoneTab(2, tileEntity.getPos());
					PacketHandler.net.sendToServer(msg);
				}	
			}
		}		
	}
	@Override
	public void buttonHovered(BaseButton button) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public int getGuiTop() {
		return yPosition;
	}
	@Override
	public int getGuiLeft() {
		return xPosition;
	}
}
