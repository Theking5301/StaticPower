package api.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.StaticVertexBuffer;
import theking530.staticpower.client.gui.widgets.GuiDrawItem;

public class StringButton extends Gui implements MouseListener{
	
	public int BUTTON_WIDTH;
	public int BUTTON_HEIGHT;
	public int BUTTON_XPOS;
	public int BUTTON_YPOS;
	private int GUI_LEFT;
	private int GUI_TOP;
	private Block BLOCK;
	public int GROWTH_STATE;
	public TileEntity TILE_ENTITY;
	private int WIDTH;
	private int HEIGHT;
	private int xSIZE;
	private int ySIZE;
	public int TIMER = 0;
	public boolean HIGHLIGHT;
	public boolean IS_VISIBLE = true;
	public boolean CLICKED = false;
	
	private ResourceLocation base = new ResourceLocation(Reference.MOD_ID + ":" + "/textures/gui/Button.png");
	private ResourceLocation hover = new ResourceLocation(Reference.MOD_ID + ":" + "/textures/gui/ButtonHover.png");
	
	public StringButton(int guiLeft, int guiTop, int width, int height, int xPos, int yPos, Block block) {
		this.GUI_LEFT = guiLeft;
		this.GUI_TOP = guiTop;
		this.BUTTON_WIDTH = width;
		this.BUTTON_HEIGHT = height;
		this.BUTTON_XPOS = xPos;
		this.BUTTON_YPOS = yPos;
		this.BLOCK = block;		
	}
	public void playSound() {
		if(TIMER == 1) {
			if(TILE_ENTITY != null) {
				//TILE_ENTITY.getWorldObj().playSound(TILE_ENTITY.xCoord, TILE_ENTITY.yCoord, TILE_ENTITY.zCoord, "gui.button.press", 0.4F, 1.0F, CLICKED); 
			}
		}
	}
	public void updateMethod(int width, int height, int xSize, int ySize, TileEntity te) {
		playSound();
		clickTime();
		this.WIDTH = width;
		this.HEIGHT = height;
		this.xSIZE = xSize;
		this.ySIZE = ySize;
		this.TILE_ENTITY = te;
	}
	public void drawButton() {
		if(IS_VISIBLE) {
		drawButtonBase();
		drawButtonIcon();
		}
	}
	public void drawButtonIcon() {
		int j = (WIDTH - xSIZE) / 2;
		int k = (HEIGHT - ySIZE) / 2;
		int buttonLeft = GUI_LEFT + j + BUTTON_XPOS;
		int buttonTop = GUI_TOP + k + BUTTON_YPOS;
		
		Item item = Item.getItemFromBlock(BLOCK);
		GuiDrawItem.drawItem(item, buttonLeft, buttonTop, 1, -2, this.zLevel, 1.0f);
	}
	public void drawButtonBase() {
		int j = (WIDTH - xSIZE) / 2;
		int k = (HEIGHT - ySIZE) / 2;
		int buttonLeft = GUI_LEFT + j + BUTTON_XPOS;
		int buttonTop = GUI_TOP + k + BUTTON_YPOS;
		GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder tes = tessellator.getBuffer();
        tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		if(CLICKED == true || HIGHLIGHT == true) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(hover);
		}else{
			Minecraft.getMinecraft().getTextureManager().bindTexture(base);
		}
	
		//Top
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop+3, 0, 0, .15);
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop, 0, 0, 0);
		StaticVertexBuffer.pos(buttonLeft, buttonTop, 0, 1, 0);
		StaticVertexBuffer.pos(buttonLeft, buttonTop+3, 0, 1, .15);
		
		//Bottom
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop+(BUTTON_HEIGHT), 0, 0, 1);
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop+(BUTTON_HEIGHT - 3), 0, 0, .85);
		StaticVertexBuffer.pos(buttonLeft, buttonTop+(BUTTON_HEIGHT - 3), 0, 1, .85);
		StaticVertexBuffer.pos(buttonLeft, buttonTop+(BUTTON_HEIGHT), 0, 1, 1);
	
		//Right
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop+(BUTTON_HEIGHT), 0, 0, 1);
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH, buttonTop, 0, 0, 0);
		StaticVertexBuffer.pos(buttonLeft-3+BUTTON_WIDTH, buttonTop, 0, .02, 0);
		StaticVertexBuffer.pos(buttonLeft-3+BUTTON_WIDTH, buttonTop+(BUTTON_HEIGHT), 0, .02, 1);

		//Left
		StaticVertexBuffer.pos(buttonLeft+3, buttonTop+(BUTTON_HEIGHT), 0, .98, 1);
		StaticVertexBuffer.pos(buttonLeft+3, buttonTop, 0, .98, 0);
		StaticVertexBuffer.pos(buttonLeft, buttonTop, 0, 1, 0);
		StaticVertexBuffer.pos(buttonLeft, buttonTop+(BUTTON_HEIGHT), 0, 1, 1);

		//Body
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH-3, buttonTop-3+(BUTTON_HEIGHT), 0, 0.2, .8);
		StaticVertexBuffer.pos(buttonLeft+BUTTON_WIDTH-3, buttonTop+3, 0, 0.2, 0.2);
		StaticVertexBuffer.pos(buttonLeft+3, buttonTop+3, 0, .8, 0.2);
		StaticVertexBuffer.pos(buttonLeft+3, buttonTop-3+(BUTTON_HEIGHT), 0, .8, .8);
		
		tessellator.draw();
		GL11.glEnable(GL11.GL_LIGHTING);	
	}	
    public void sound(SoundHandler soundHandler) {
    	//soundHandler.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }
	public void buttonMouseClick(int par1, int par2, int button) {
		int j = (WIDTH - xSIZE) / 2;
		int k = (HEIGHT - ySIZE) / 2;
		if(par1 > j + BUTTON_XPOS && par1 < j + BUTTON_XPOS + 24) {
	    	if(par2 > k + BUTTON_YPOS && par2 < k + BUTTON_YPOS + 24) {
	    		CLICKED = true; 
	    	}
	    }
	}
	public void clickTime() {
		if(CLICKED) {		
			TIMER++;
			if(TIMER == 2) {
			CLICKED = false;
			TIMER = 0;
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println(e.getX());
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
