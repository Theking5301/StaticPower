package api.gui.tab;

import org.lwjgl.opengl.GL11;

import api.RectangleBounds;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.utilities.StaticVertexBuffer;
import theking530.staticpower.client.gui.GuiTextures;

public abstract class BaseGuiTab extends Gui {
	
	public enum TabState {
		CLOSED, OPENING, OPEN, CLOSING;
		
		static TabState incrementState(TabState curr) {
			int newIndex = curr.ordinal() + 1;
			newIndex = newIndex % 3;
			return TabState.values()[newIndex];
		}
	}
	public enum TabSide {
		LEFT, RIGHT;
	}
	
	protected int tabWidth;
	protected int tabHeight;
	protected int xPosition;
	protected int yPosition;

	protected int xPositionOffset;
	protected int yPositionOffset;
	
	private float currentWidth;
	private float currentHeight;
	
	protected float animationTimer = 0;
	protected float animationTime = 4.0f;
	protected Item itemIcon;
	protected ResourceLocation tabTexture;
	
	protected TabState tabState;
	protected TabSide tabSide;
	
	private GuiTabManager owningManager;
	
	public BaseGuiTab(int tabWidth, int tabHeight, ResourceLocation texture, Item item) {
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;
		itemIcon = item;		
		tabTexture = texture;
		tabState = TabState.CLOSED;
		tabSide = TabSide.RIGHT;

		xPositionOffset = 0;
		yPositionOffset = 0;
	}
	public BaseGuiTab(int tabWidth, int tabHeight, ResourceLocation texture, Block block) {
		this(tabWidth, tabHeight, texture, Item.getItemFromBlock(block));
	}
	
	public void update(int xPos, int yPos, float partialTicks) {
		updateAnimation(partialTicks);
		
		xPosition = xPos + xPositionOffset;
		yPosition = yPos + yPositionOffset;
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);	
		drawTab(xPosition, yPosition, partialTicks);
		drawExtra(getTabSide() == TabSide.RIGHT ? xPosition : xPosition - tabWidth, yPosition, partialTicks);	

	}
	public void mouseInteraction(int mouseX, int mouseY, int button) {
		if(mouseX >  xPosition && mouseX <  xPosition + 24) {
	    	if(mouseY >  yPosition && mouseY <  yPosition + 24) {
	    		if(tabState == TabState.CLOSED) {
	    			tabState = TabState.OPENING;
		    		owningManager.tabOpening(this);
	    		}
	    		if(tabState == TabState.OPEN) {
	    			tabState = TabState.CLOSING;
	    			owningManager.tabClosing(this);
	    		}
	    	}
	    }
		if(isOpen()) {
			handleExtraMouseInteraction(mouseX, mouseY, button);
		}
	}
	public void drawDarkBackground(int xPos, int yPos, int width, int height) {
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.BUTTON_BG);	
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(xPos, yPos, 0).tex(0,1).endVertex();
		vertexbuffer.pos(xPos, yPos+height, 0).tex(0,0).endVertex();
		vertexbuffer.pos(xPos+width, yPos+height, 0).tex(1,0).endVertex();
		vertexbuffer.pos(xPos+width, yPos, 0).tex(1,1).endVertex();	
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}
	public void keyboardInteraction(char par1, int par2) {
		if(isOpen()) {
			handleExtraKeyboardInteraction(par1, par2);
		}
	}
	public void mouseMoveIntraction(int x, int y) {
		if(isOpen()) {
			handleExtraMouseMove(x, y);
		}
	}
	public boolean isOpen() {
		return tabState == TabState.OPEN;
	}
	public boolean isClosed() {
		return tabState == TabState.CLOSED;
	}
	public TabState getTabState() {
		return tabState;
	}
	public TabSide getTabSide(){
		return tabSide;
	}
	public BaseGuiTab setTabSide(TabSide newSide) {
		this.tabSide = newSide;
		return this;
	}
	
	public boolean setTabState(TabState newState) {
		if(newState == TabState.CLOSED || newState == TabState.CLOSING) {
			if(getTabState() == TabState.OPEN) {
				tabState = TabState.CLOSING;
				return true;
			}
		}else if(newState == TabState.OPEN || newState == TabState.OPENING) {
			if(getTabState() == TabState.CLOSED) {
				tabState = TabState.OPENING;
				return true;
			}
		}
		return false;
	}
	public void setManager(GuiTabManager manager) {
		owningManager = manager;
	}
	public BaseGuiTab setOffsets(int xOffset, int yOffset) {
		xPositionOffset = xOffset;
		yPositionOffset = yOffset;
		return this;
	}
	public int getXOffset() {
		return xPositionOffset;
	}
	public int getYOffset() {
		return yPositionOffset;
	}
	protected abstract void drawExtra(int xPos, int yPos, float partialTicks);
	protected abstract void handleExtraMouseInteraction(int mouseX, int mouseY, int button);
	protected abstract void handleExtraKeyboardInteraction(char par1, int par2);
	protected abstract void handleExtraMouseMove(int mouseX, int mouseY);
	
	private void updateAnimation(float partialTicks) {
		if(tabState == TabState.OPENING && animationTimer < animationTime) {
			animationTimer = Math.min(animationTime, animationTimer + partialTicks*2);
			if(animationTimer == animationTime) {
				tabState = TabState.OPEN;
			}
		}
		if(tabState == TabState.CLOSING && animationTimer > 0) {
			animationTimer = Math.max(0, animationTimer - partialTicks*2);
			if(animationTimer == 0) {
				tabState = TabState.CLOSED;
			}
		}
		if(tabState == TabState.CLOSING && animationTimer <=  0) {
			tabState = TabState.CLOSED;
    		owningManager.tabClosed(this);
		}
		if(tabState == TabState.OPENING && animationTimer >= animationTime) {
			tabState = TabState.OPEN;
    		owningManager.tabOpened(this);
		}
	}
	private void drawTab(int xPos, int yPos, float partialTicks) {
		GL11.glEnable(GL11.GL_BLEND);
		drawBaseTab(xPos, yPos, partialTicks);
		drawButtonIcon(xPos, yPos, partialTicks);
	}
	private void drawButtonIcon(int xPos, int yPos, float partialTicks) {
		if(itemIcon != null) {
	        GlStateManager.disableDepth();
	        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(itemIcon), getTabSide() == TabSide.RIGHT ? xPos+3 : xPos+5, yPos+4);
		}
	}
	private void drawBaseTab(int xPos, int yPos, float partialTicks) {
		int tabLeft = xPos-(getTabSide() == TabSide.RIGHT ? 0 : 24);
		int tabTop = yPos;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder tes = tessellator.getBuffer();
        tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		Minecraft.getMinecraft().getTextureManager().bindTexture(tabTexture);

	    GlStateManager.enableBlend();
		currentWidth = ((tabWidth*animationTimer/animationTime));
		currentHeight = ((tabHeight*animationTimer/animationTime));

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GlStateManager.disableLighting();
		if(getTabSide() == TabSide.LEFT) {
			GL11.glTranslatef(xPos, yPos, 0.0f);
			GL11.glScalef(-1.0f, 1.0f, 1.0f);;
			GL11.glTranslatef(-xPos, -yPos, 0.0f);
		}
		//Top
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop+3, 0, .976, .03);
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop, 0, .976, 0);
		StaticVertexBuffer.pos(tabLeft+1, tabTop, 0, 0, 0);
		StaticVertexBuffer.pos(tabLeft+1, tabTop+3, 0, 0, .03);
		
		//Bottom
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+24+(tabHeight*animationTimer/animationTime), 0, .976, 1);
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+21+(tabHeight*animationTimer/animationTime), 0, .976, .965);
		StaticVertexBuffer.pos(tabLeft+1, tabTop+21+(tabHeight*animationTimer/animationTime), 0, 0, .965);
		StaticVertexBuffer.pos(tabLeft+1, tabTop+24+(tabHeight*animationTimer/animationTime), 0, 0, 1);
		
		//Right Side
		StaticVertexBuffer.pos(tabLeft+23.8+(tabWidth*animationTimer/animationTime), tabTop+22+(tabHeight*animationTimer/animationTime), 0, 1, 0.035);
		StaticVertexBuffer.pos(tabLeft+23.8+(tabWidth*animationTimer/animationTime), tabTop+4, 0, 1, .95);
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+4, 0, .976, .95);
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+22+(tabHeight*animationTimer/animationTime), 0, .976, 0.035);
		
		//Body
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+21+(tabHeight*animationTimer/animationTime), 0, .976, 0.035);
		StaticVertexBuffer.pos(tabLeft+21+(tabWidth*animationTimer/animationTime), tabTop+3, 0, .976, .965);
		StaticVertexBuffer.pos(tabLeft+1, tabTop+3, 0, 0, .965);
		StaticVertexBuffer.pos(tabLeft+1, tabTop+21+(tabHeight*animationTimer/animationTime), 0, 0, 0.035);
		
		//Bottom Corner
		StaticVertexBuffer.pos(tabLeft+24.1+(tabWidth*animationTimer/animationTime), tabTop+23+(tabHeight*animationTimer/animationTime), 0, 1, 1);
		StaticVertexBuffer.pos(tabLeft+24.1+(tabWidth*animationTimer/animationTime), tabTop+20+(tabHeight*animationTimer/animationTime), 0, 1, .965);
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop+20+(tabHeight*animationTimer/animationTime), 0, .976, .965);
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop+23+(tabHeight*animationTimer/animationTime), 0, .976, 1);
		
		//Top Corner
		StaticVertexBuffer.pos(tabLeft+24+(tabWidth*animationTimer/animationTime), tabTop+4, 0, 1, .03);
		StaticVertexBuffer.pos(tabLeft+24+(tabWidth*animationTimer/animationTime), tabTop, 0, 1, 0);
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop, 0, .976, 0);
		StaticVertexBuffer.pos(tabLeft+20+(tabWidth*animationTimer/animationTime), tabTop+4, 0, .9767, .03);

		tessellator.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}
	
	public RectangleBounds getBounds() {
		return new RectangleBounds(xPosition, yPosition, currentWidth+24, currentHeight+24);
	}
}

