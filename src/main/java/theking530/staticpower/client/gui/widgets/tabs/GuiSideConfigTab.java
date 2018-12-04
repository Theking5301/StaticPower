package theking530.staticpower.client.gui.widgets.tabs;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import api.gui.IInteractableGui;
import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.ButtonManager;
import api.gui.button.TextButton;
import api.gui.tab.BaseGuiTab;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.ISideConfigurable;
import theking530.staticpower.tileentity.ISideConfigurable.SideIncrementDirection;

public class GuiSideConfigTab extends BaseGuiTab implements IInteractableGui {

	public TileEntity tileEntity;
	private FontRenderer fontRenderer;

	private ButtonManager buttonManager;
	
	private TextButton topButton;
	private TextButton bottomButton;
	private TextButton leftButton;
	private TextButton rightButton;
	private TextButton backButton;
	private TextButton frontButton;
	
	private boolean allowFaceInteraction;
	
	public GuiSideConfigTab(int width, int height, boolean faceInteraction, TileEntity te){
		super(80, 80, GuiTextures.BLUE_TAB, te.getBlockType());
		tileEntity = te;
		fontRenderer = Minecraft.getMinecraft().fontRenderer;
		allowFaceInteraction = faceInteraction;
		buttonManager = new ButtonManager(this);
		
		int xOffset = 3;
		int yOffset = 8;	
		topButton = new TextButton(20, 20, xOffset + tabWidth/2, yOffset+15, "T");
		bottomButton = new TextButton(20, 20, xOffset + tabWidth/2, yOffset+tabHeight-15, "B");
		rightButton = new TextButton(20, 20, xOffset + tabWidth-15, yOffset+tabHeight/2, "L");
		leftButton = new TextButton(20, 20, xOffset + 15, yOffset+tabHeight/2, "R");
		backButton = new TextButton(20, 20, xOffset + tabWidth/2, yOffset+tabHeight/2, "B");
		frontButton = new TextButton(20, 20, xOffset + 15, yOffset+15, "F");
		frontButton.setVisible(allowFaceInteraction);
		
		updateTooltips();
		
		buttonManager.registerButton(topButton);
		buttonManager.registerButton(bottomButton);
		buttonManager.registerButton(leftButton);
		buttonManager.registerButton(rightButton);
		buttonManager.registerButton(backButton);
		buttonManager.registerButton(frontButton);
	}
	@Override
	public void drawExtra(int xPos, int yPos, float partialTicks) {
		if(isOpen()) {
	    	drawText(xPos+10, yPos+8);
			drawButtonBG(xPos, yPos);
			buttonManager.drawButtons(xPos, yPos);
		}
	}
	public void drawText(int xPos, int yPos) {
		String tabName = "Side Config";
		modeText(xPos, yPos);	
		fontRenderer.drawStringWithShadow(tabName, xPos-this.fontRenderer.getStringWidth(tabName)/2 + 45, yPos, 16777215);	
	}
	public void modeText(int tabLeft, int tabTop) {

	}
	public void drawButtonBG(int xPos, int yPos) {
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.BUTTON_BG);
		GlStateManager.color(1, 1, 1);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xPos+95, yPos+95, 0).tex(0,1).endVertex();
		vertexbuffer.pos(xPos+95, yPos+20, 0).tex(0,0).endVertex();
		vertexbuffer.pos(xPos+10, yPos+20, 0).tex(1,0).endVertex();
		vertexbuffer.pos(xPos+10, yPos+95, 0).tex(1,1).endVertex();	
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}
    @Override
    public void handleExtraMouseInteraction(int par1, int par2, int button) {
    	buttonManager.handleMouseInteraction(par1, par2, button);
	}	
	@Override
	protected void handleExtraKeyboardInteraction(char par1, int par2) {
		
	}
	@Override
	protected void handleExtraMouseMove(int mouseX, int mouseY) {
		buttonManager.handleMouseMoveInteraction(mouseX, mouseY);
	}
	
	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		if(!(tileEntity instanceof ISideConfigurable)) {
			return;
		}
		ISideConfigurable sideConfigurable = (ISideConfigurable)tileEntity;
		SideIncrementDirection direction = mouseButton == ClickedState.LEFT ? SideIncrementDirection.FORWARD : SideIncrementDirection.BACKWARDS;
		if(button == topButton) {
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.TOP, getFacingDirection()), direction);
		}else if(button == bottomButton) {
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.BOTTOM, getFacingDirection()), direction);
		}else if(button == leftButton) {
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.LEFT, getFacingDirection()), direction);
		}else if(button == rightButton) {
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.RIGHT, getFacingDirection()), direction);
		}else if(button == frontButton && allowFaceInteraction) {
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.FRONT, getFacingDirection()), direction);
		}else{
			sideConfigurable.incrementSideConfiguration(SideUtilities.getEnumFacingFromSide(BlockSide.BACK, getFacingDirection()), direction);
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				for(EnumFacing facing : EnumFacing.values()) {
					sideConfigurable.setSideConfiguration(Mode.Regular, facing);
				}
			}
		}
		updateTooltips();
		IMessage msg = new PacketSideConfigTab(sideConfigurable.getSideConfigurations(), tileEntity.getPos());
		PacketHandler.net.sendToServer(msg);
	}
	@Override
	public void buttonHovered(BaseButton button) {

	}
    public EnumFacing getFacingDirection() {
    	if(tileEntity.getWorld().getBlockState(tileEntity.getPos()).getProperties().containsKey(BlockHorizontal.FACING)) {
        	return tileEntity.getWorld().getBlockState(tileEntity.getPos()).getValue(BlockHorizontal.FACING);
    	}else{
        	return EnumFacing.UP;
    	}
    }
    public void updateTooltips() {
		if(!(tileEntity instanceof ISideConfigurable)) {
			return;
		}
		ISideConfigurable sideConfigurable = (ISideConfigurable)tileEntity;
		
		for(BlockSide side : BlockSide.values()) {
			TextButton button = null;
			switch(side) {
			case TOP: button = topButton;
				break;
			case BOTTOM: button = bottomButton;
				break;
			case LEFT: button = leftButton;
				break;
			case RIGHT: button = rightButton;
				break;
			case FRONT: button = frontButton;
				break;
			case BACK: button = backButton;
				break;
			default: button = topButton;
				break;
			}
			
			button.setText(sideConfigurable.getSideConfiguration(side).getFontColor() + side.getLocalizedName().substring(0, 1));
			button.setTooltip(side.getLocalizedName() + "=" + sideConfigurable.getSideConfiguration(side).getFontColor() +sideConfigurable.getSideConfiguration(side).getLocalizedName() + "=" + I18n.format(conditionallyGetCardinal(side)));		
		}
    }
    public String conditionallyGetCardinal(BlockSide side) {
    	if(tileEntity instanceof TileEntityBase) {
    		TileEntityBase te = (TileEntityBase)tileEntity;
    		return "gui." + SideUtilities.getEnumFacingFromSide(side, te.getFacingDirection()).toString();
    	}
		return "";
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