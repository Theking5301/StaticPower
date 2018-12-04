package theking530.staticpower.client.gui.widgets.tabs;

import org.lwjgl.opengl.GL11;

import api.gui.IInteractableGui;
import api.gui.TextField;
import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.ButtonManager;
import api.gui.button.TextButton;
import api.gui.tab.BaseGuiTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;

public class GuiPowerControlTab extends BaseGuiTab implements IInteractableGui{
	
	public TileEntityBattery batteryTileEntity;
	private FontRenderer fontRenderer;

	private TextField minimumValueTextField;
	private TextField maximumValueTextField;
	private TextButton confirmButton;
	
	private ButtonManager buttonManager;
	
	public GuiPowerControlTab(int guiLeft, int guiTop, TileEntityBattery te){
		super(guiLeft, guiTop, GuiTextures.PURPLE_TAB, ModItems.StaticWrench);
		fontRenderer = Minecraft.getMinecraft().fontRenderer;
		batteryTileEntity = te;
	
		buttonManager = new ButtonManager(this);
		
		minimumValueTextField = new TextField(30, 15);
		maximumValueTextField = new TextField(30, 15);
		confirmButton = new TextButton(48, 20, 40, 67, I18n.format("Confirm"));
		
		buttonManager.registerButton(confirmButton);
		
		minimumValueTextField.setText(batteryTileEntity.getMinimumPowerThreshold() + "%");
		maximumValueTextField.setText(batteryTileEntity.getMaximumPowerThreshold() + "%");
	}
	@Override
	public void drawExtra(int xPos, int yPos, float partialTicks) {
		if(isOpen()) {
			drawButtonBG(xPos, yPos);
			maximumValueTextField.updateMethod();
			maximumValueTextField.setMaxStringLength(3);		
			minimumValueTextField.updateMethod();
			minimumValueTextField.setMaxStringLength(3);
			

			maximumValueTextField.drawTextBox(xPos+60, yPos+45);
			minimumValueTextField.drawTextBox(xPos+60, yPos+25);
			
			function();
			drawText(xPos, yPos);
			buttonManager.drawButtons(xPos, yPos);
		}	
	}
	public void drawText(int xPos, int yPos) {
		String tabName = I18n.format("gui.PowerControl");
		this.fontRenderer.drawStringWithShadow(EnumTextFormatting.GREEN + tabName, xPos-this.fontRenderer.getStringWidth(tabName)/2 + 64, yPos+8, 16777215);	
		
		String min = I18n.format("gui.Minimum");
		String max = I18n.format("gui.Maximum");
		String percent = "%";

		this.fontRenderer.drawStringWithShadow(EnumTextFormatting.RED + min + ":", xPos+15, yPos+29, 16777215);				
		this.fontRenderer.drawStringWithShadow(EnumTextFormatting.WHITE + max + ":", xPos+15, yPos+48, 16777215);	
		this.fontRenderer.drawStringWithShadow(EnumTextFormatting.BOLD + percent, xPos+95, yPos+29, 16777215);	
		this.fontRenderer.drawStringWithShadow(EnumTextFormatting.BOLD + percent, xPos+95, yPos+48, 16777215);
	}
	public void drawButtonBG(int xPos, int yPos) {
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.BUTTON_BG);	
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xPos+10, yPos+20, 0).tex(0,1).endVertex();
		vertexbuffer.pos(xPos+10, yPos+64, 0).tex(0,0).endVertex();
		vertexbuffer.pos(xPos+110, yPos+64, 0).tex(1,0).endVertex();
		vertexbuffer.pos(xPos+110, yPos+20, 0).tex(1,1).endVertex();	
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}
	public void function() {

	}
	@Override
	public void handleExtraMouseInteraction(int x, int y, int button) {	
		maximumValueTextField.mouseClicked(x, y, button);
		minimumValueTextField.mouseClicked(x, y, button);
		buttonManager.handleMouseInteraction(x, y, button);
	}	
	@Override
	public void handleExtraKeyboardInteraction(char par1, int par2) {
		maximumValueTextField.textboxKeyTyped(par1, par2);	
		minimumValueTextField.textboxKeyTyped(par1, par2);	
	}
	@Override
	protected void handleExtraMouseMove(int mouseX, int mouseY) {
		buttonManager.handleMouseMoveInteraction(mouseX, mouseY);
	}
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		int minValue = 0;
		int maxValue = 0;
		
		if(minimumValueTextField.getText() != null) {	
			try { 
				minValue = Integer.valueOf(minimumValueTextField.getText().replaceFirst(".*?(\\d+).*", "$1"));			
			} catch(NumberFormatException e) {}				
		}
		if(maximumValueTextField.getText() != null) {			
			try { 
				maxValue = Integer.valueOf(maximumValueTextField.getText().replaceFirst(".*?(\\d+).*", "$1"));				
			} catch(NumberFormatException e) {}				
		}
		
		minValue = Math.max(Math.min(minValue, 100), 0);	
		maxValue = Math.max(Math.min(maxValue, 100), 0);

		minimumValueTextField.setText(minValue + "%");
		maximumValueTextField.setText(maxValue + "%");
		
		batteryTileEntity.setMinimumPowerThreshold(minValue);
		batteryTileEntity.setMaximumPowerThreshold(maxValue);
		IMessage msg = new PacketPowerControlTab(maxValue, minValue, batteryTileEntity.getPos());
		PacketHandler.net.sendToServer(msg);	
	}
	@Override
	public void buttonHovered(BaseButton button) {

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
