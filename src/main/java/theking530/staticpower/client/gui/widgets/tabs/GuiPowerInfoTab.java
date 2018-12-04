package theking530.staticpower.client.gui.widgets.tabs;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import api.gui.tab.BaseGuiTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.energy.StaticEnergyStorage;
import theking530.staticpower.items.ModItems;

public class GuiPowerInfoTab extends BaseGuiTab {

	private FontRenderer fontRenderer;
	
	private List<String> info;
	private String tabTitle;
	private StaticEnergyStorage energyStorage;

	public GuiPowerInfoTab(int width, int height, StaticEnergyStorage storage){
		super(width, height, GuiTextures.PURPLE_TAB, ModItems.BasicPowerUpgrade);
		fontRenderer = Minecraft.getMinecraft().fontRenderer;
		energyStorage = storage;
	}
	protected void setText(String title, String text) {
		info = Arrays.asList(text.split("="));
		int stringLengthMax = 0;
		for(int i=0; i<info.size(); i++) {
			if(fontRenderer.getStringWidth(info.get(i)) > stringLengthMax) {
				stringLengthMax = fontRenderer.getStringWidth(info.get(i));
			}
		}
		this.tabWidth = stringLengthMax+8;
		tabTitle = title;
	}	
	
	protected void setPowerInfoText() {
		int energyPerTick = energyStorage.getEnergyIO();
		String text = (EnumTextFormatting.GREEN + "Current I/O: =" + energyPerTick + " RF/t=" + EnumTextFormatting.AQUA + "Max Recieve:=" + energyStorage.getMaxReceive() + " RF/t");
		setText("Power I/O", text);
	}
	
	@Override
	protected void drawExtra(int xPos, int yPos, float partialTicks) {
		if(isOpen()) {	
			setPowerInfoText();
			if(info != null && info.size() > 0) {
				drawTextBG(xPos, yPos);	
				drawText(xPos, yPos);	
			}
    	}	
	}
	private void drawText(int xPos, int yPos) {
		fontRenderer.drawStringWithShadow(tabTitle, xPos+11, yPos+8, GuiUtilities.getColor(242, 0, 255));	
		float fontScale = 1.0f;
		int scaleBasedXOffset = 0;
		int scaleBasedYOffset = 0;
		GlStateManager.scale(fontScale, fontScale, fontScale);
		for(int i = 0; i < info.size(); i++) {
			String string = (String) info.get(i);
    		fontRenderer.drawStringWithShadow(string, xPos + 17 + scaleBasedXOffset, (yPos+27)+scaleBasedYOffset+11*i, 16777215);
		}	
		GlStateManager.scale(1.0f/fontScale, 1.0f/fontScale, 1.0f/fontScale);
	}
	public void drawTextBG(int xPos, int yPos) {
		int height = 0;
		if(info != null) {
			height = info.size()*11;
		}
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.BUTTON_BG);	
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xPos+tabWidth+15, yPos+height+28, 0).tex(0,1).endVertex();
		vertexbuffer.pos(xPos+tabWidth+15, yPos+22, 0).tex(0,0).endVertex();
		vertexbuffer.pos(xPos+10, yPos+22, 0).tex(1,0).endVertex();
		vertexbuffer.pos(xPos+10, yPos+height+28, 0).tex(1,1).endVertex();	
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}
	@Override
	protected void handleExtraMouseInteraction(int mouseX, int mouseY, int button) {

	}
	@Override
	protected void handleExtraKeyboardInteraction(char par1, int par2) {
		
	}

	@Override
	protected void handleExtraMouseMove(int mouseX, int mouseY) {
		
	}
}
