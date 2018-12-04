package theking530.staticpower.logic.gates.powercell;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import theking530.staticpower.client.gui.GuiTextures;
import theking530.staticpower.handlers.PacketHandler;
import theking530.staticpower.logic.gates.timer.PacketTimer;

public class GuiPowerCell extends GuiContainer{
	
	private TileEntityPowerCell P_CELL;
	private int CURRENT_SIGNAL;
	
	public GuiPowerCell(InventoryPlayer invPlayer, TileEntityPowerCell tePowerCell) {
		super(new ContainerPowerCell(invPlayer, tePowerCell));
		P_CELL = tePowerCell;		
		CURRENT_SIGNAL = P_CELL.POWER;
		this.xSize = 138;
		this.ySize = 86;		
	}
	public void drawScreen(int par1, int par2, float par3) {
    	super.drawScreen(par1, par2, par3);   
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.P_CELL.getName());
		String info = "Power: " + P_CELL.POWER;
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(info, this.xSize / 2 - this.fontRenderer.getStringWidth(info)/2 - 1, 57, 4210752 );
	}
	@Override
	public void initGui() {
		super.initGui();
	 	int j = (width - xSize) / 2;
	    int k = (height - ySize) / 2;

	    this.buttonList.add(new GuiButton( 1, j+96, k+30, 18, 18, "+"));
	    this.buttonList.add(new GuiButton( 2, j+24, k+30, 18, 18, "-"));

	    this.buttonList.add(new GuiButton( 5, j+46, k+30, 46, 18, "Confirm"));
	}
	public boolean isShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		if(B.id == 1) {
			if(isShiftDown()){
				CURRENT_SIGNAL += 5;
			}else{
				CURRENT_SIGNAL++;
			}
	 	}	
		if(B.id == 2) {
			if(isShiftDown() && CURRENT_SIGNAL - 5 >= 1){
				CURRENT_SIGNAL -= 5;
			}else if(CURRENT_SIGNAL - 1 > 0){
				CURRENT_SIGNAL--;
			}
		}
		if(B.id == 5) {
			IMessage msg = new PacketTimer(CURRENT_SIGNAL, P_CELL.getPos());
			P_CELL.POWER = CURRENT_SIGNAL;
			PacketHandler.net.sendToServer(msg);
		}
	}	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    	this.drawDefaultBackground();
    	
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiTextures.MULTIPLIER_GUI);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
	}


