package theking530.staticpower.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import api.gui.GuiDrawUtilities;
import api.gui.IGuiWidget;
import api.gui.IInteractableGui;
import api.gui.button.BaseButton;
import api.gui.button.BaseButton.ClickedState;
import api.gui.button.ButtonManager;
import api.gui.tab.GuiTabManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.Vec3i;
import theking530.staticpower.assists.utilities.GuiUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.gui.widgets.GuiDrawItem;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.tileentity.TileEntityBase;

public abstract class BaseGuiContainer extends GuiContainer implements IInteractableGui {

	private GuiTabManager tabManager;
	private ButtonManager buttonManager;
	private List<IGuiWidget> widgets;
	
	private int xSizeTarget;
	private int ySizeTarget;
	
	private int outputSlotSize;
	private int inputSlotSize;
	
	public BaseGuiContainer(Container inventorySlotsIn, int guiXSize, int guiYSize) {
		super(inventorySlotsIn);
		xSize = guiXSize;
		ySize = guiYSize;
		xSizeTarget = xSize;
		ySizeTarget = ySize;
		tabManager = new GuiTabManager(this);
		buttonManager = new ButtonManager(this);
		widgets = new ArrayList<IGuiWidget>();
		
		outputSlotSize = 24;
		inputSlotSize = 16;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.zLevel = -1.0f;
		this.drawDefaultBackground();	
		this.zLevel = 0.0f;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawExtra(partialTicks, mouseX, mouseY);
		
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
		for(int i=0; i<widgets.size(); i++) {
	        GL11.glColor3f(1.0f, 1.0f, 1.0f);
	        if(widgets.get(i).isVisible()) {
				widgets.get(i).renderBackground(mouseX, mouseY, partialTicks);
	        }
		}
		
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
		drawTabs(partialTicks);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
		drawButtons(mouseX, mouseY);
		
		animateScreenSize();
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

		for(int i=0; i<widgets.size(); i++) {
	        if(widgets.get(i).isVisible()) {
	        	widgets.get(i).mouseHover(mouseX, mouseY);
	        }
		}
		for(int i=0; i<widgets.size(); i++) {
	        GL11.glColor3f(1.0f, 1.0f, 1.0f);
	        if(widgets.get(i).isVisible()) {
				widgets.get(i).renderForeground(mouseX, mouseY, partialTicks);
	        }
		}
		for(int i=0; i<widgets.size(); i++) {
	        GL11.glColor3f(1.0f, 1.0f, 1.0f);
	        if(widgets.get(i).isVisible() && widgets.get(i).shouldDrawTooltip(mouseX, mouseY)) {
				drawHoveringText(widgets.get(i).getTooltip(), mouseX, mouseY, fontRenderer); 
	        }
		}
		getTabManager().handleMouseMoveInteraction(mouseX, mouseY);
	}
	protected void drawButtons(int mouseX, int mouseY) {
		buttonManager.handleMouseMoveInteraction(mouseX, mouseY);
		buttonManager.drawButtons(mouseX, mouseY);
	}
	protected void drawTabs(float partialTicks) {
		getTabManager().drawTabs(guiLeft+xSize-1, guiTop+10, xSize, ySize, partialTicks);
	}
	protected abstract void drawExtra(float partialTicks, int mouseX, int mouseY);
	
	public GuiTabManager getTabManager() {
		return tabManager;
	}
	public ButtonManager getButtonManager() {
		return buttonManager;
	}
	
	public void drawGenericBackground()  {
		drawGenericBackground(xSize, ySize);
	}
	public void drawGenericBackground(int width, int height) {		
		GuiDrawUtilities.drawGenericBackground(width, height, guiLeft, guiTop, zLevel);
	}
	public void drawGenericBackground(int xPos, int yPos, int width, int height) {		
		GuiDrawUtilities.drawGenericBackground(width, height, xPos+guiLeft, yPos+guiTop, zLevel);
	}
	public void drawGenericBackground(int xPos, int yPos, int width, int height, Color mainTint, Color rimTint) {		
		GuiDrawUtilities.drawGenericBackground(width, height, xPos+guiLeft, yPos+guiTop, zLevel, mainTint, rimTint);
	}
	public void drawPlayerInventorySlots() {
		drawPlayerInventorySlots(guiLeft+(xSize-162)/2+1, guiTop+ySize-83);
	}
	public void drawPlayerInventorySlots(int xPos, int yPos) {
		GuiDrawUtilities.drawPlayerInventorySlots(xPos, yPos);
	}
	public void drawSlot(int xPos, int yPos, int width, int height, Mode slotMode) {
		if(slotMode == Mode.Regular) {
			GuiDrawUtilities.drawSlot(xPos, yPos, width, height);
		}else{
			GuiDrawUtilities.drawSlot(xPos, yPos, width, height, slotMode.getBorderColor());
		}
	}
	public void drawSlot(int xPos, int yPos, int width, int height) {
		GuiDrawUtilities.drawSlot(xPos, yPos, width, height);
	}
	public void drawStringWithSize(String text, int xPos, int yPos, float scale, int color, boolean withShadow) {
		GuiDrawUtilities.drawStringWithSize(text, xPos, yPos, scale, color, withShadow);
	}
	public void drawContainerSlots(TileEntityBase te, List<Slot> slots) {
		for(Slot slot : slots) {
			if(slot instanceof StaticPowerContainerSlot) {
				StaticPowerContainerSlot handlerSlot = (StaticPowerContainerSlot)slot;
				Mode intendedMode = handlerSlot.getMode() != null ? handlerSlot.getMode() : handlerSlot.getItemHandler() == te.slotsInput ? Mode.Input : handlerSlot.getItemHandler() == te.slotsOutput ? Mode.Output : Mode.Regular;
				int slotSize = intendedMode.isInputMode() ? inputSlotSize : outputSlotSize;
				int adjustment = (slotSize - 16)/2;
				if(intendedMode != Mode.Regular) {
					drawSlot(slot.xPos + guiLeft - adjustment, slot.yPos + guiTop - adjustment, slotSize, slotSize, te.getSideWithModeCount(intendedMode) > 0 ? intendedMode : Mode.Regular);
				}else{
					drawSlot(slot.xPos + guiLeft, slot.yPos + guiTop, 16, 16);
				}
				if(!handlerSlot.getPreviewItem().isEmpty()) {
					GuiDrawItem.drawItem(handlerSlot.getPreviewItem(), guiLeft, guiTop, slot.xPos, slot.yPos, zLevel, handlerSlot.getPreviewAlpha());	
				}
			}
		}
	}
    public void drawCustomBackground(Vec3i startColor, Vec3i endColor) {
        this.drawGradientRect(0, 0, this.width, this.height, GuiUtilities.getColor(startColor.getX(), startColor.getY(), startColor.getZ()), GuiUtilities.getColor(endColor.getX(), endColor.getY(), endColor.getZ()));
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));
    }
	@Override
    public void drawDefaultBackground() {
        this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));
    }
	@Override
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    public void drawTexturedGenericRect(int xCoord, int yCoord, int width, int height, double minU, double minV, double maxU, double maxV) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(xCoord, yCoord+height, this.zLevel).tex(minU, maxV).endVertex();
        bufferbuilder.pos(xCoord+width, yCoord+height, this.zLevel).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(xCoord+width, yCoord, this.zLevel).tex(maxU, minV).endVertex();
        bufferbuilder.pos(xCoord, yCoord, this.zLevel).tex(minU, minV).endVertex();
        tessellator.draw();
    }
    
    public void drawVerticalBar(int xPos, int yPos, int width, int height, float fillAmount, int color) {
		drawSlot(guiLeft+xPos, guiTop+yPos, width, height);
		int filledHeight = (int) (fillAmount * height);
		Gui.drawRect(guiLeft+xPos, guiTop+yPos+(height-filledHeight), guiLeft+xPos+width, guiTop+yPos+height, color);
		
    }
    
	public void setInputSlotSize(int size) {
		inputSlotSize = size;
	}
	public void setOutputSlotSize(int size) {
		outputSlotSize = size;
	}
	public void setGuiSizeTarget(int xSizeTarget, int ySizeTarget) {
		this.xSizeTarget = xSizeTarget;
		this.ySizeTarget = ySizeTarget;
	}
	public void setDesieredGuiSize(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.xSizeTarget = xSize;
		this.ySizeTarget = ySize;
	}
	
	@Override	
	protected void mouseClicked(int x, int y, int button) throws IOException{
	    super.mouseClicked(x, y, button);
	    tabManager.handleMouseInteraction(x, y, button);
	    buttonManager.handleMouseInteraction(x, y, button);
		for(int i=0; i<widgets.size(); i++) {
			widgets.get(i).mouseClick(x, y, button);
		}
	}	
	@Override
	protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
	    tabManager.handleKeyboardInteraction(par1, par2);
    }
	public void registerWidget(IGuiWidget widget){
		widgets.add(widget);
		widget.setOwningGui(this);
	}

	@Override
	public void buttonPressed(BaseButton button, ClickedState mouseButton) {
		
	}

	private void animateScreenSize() {
		if(Math.abs(xSize - xSizeTarget) > 0) {
			int minimumAnimationVal = xSizeTarget-xSize > 0 ? 1 : -1;
			if(minimumAnimationVal == 1) {
				xSize = xSize + Math.max(minimumAnimationVal, (xSizeTarget-xSize)/20);
			}else{
				xSize = xSize + Math.min(minimumAnimationVal, (xSizeTarget-xSize)/20);
			}
		}
		if(Math.abs(ySize - ySizeTarget) > 0) {
			int minimumAnimationVal = ySizeTarget-ySize > 0 ? 1 : -1;
			if(minimumAnimationVal == 1) {
				ySize = ySize + Math.max(minimumAnimationVal, (ySizeTarget-ySize)/20);
			}else{
				ySize = ySize + Math.min(minimumAnimationVal, (ySizeTarget-ySize)/20);
			}
		}
	}
}
