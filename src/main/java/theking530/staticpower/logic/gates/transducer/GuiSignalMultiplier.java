package theking530.staticpower.logic.gates.transducer;

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

public class GuiSignalMultiplier extends GuiContainer{
	
	private TileEntitySignalMultiplier sMultiplier;
	
	public GuiSignalMultiplier(InventoryPlayer invPlayer, TileEntitySignalMultiplier teSignalMultiplier) {
		super(new ContainerMultiplier(invPlayer, teSignalMultiplier));
		sMultiplier = teSignalMultiplier;		
		this.xSize = 138;
		this.ySize = 86;		
	}
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.sMultiplier.getName());
		String input = String.valueOf(sMultiplier.INPUT_SIGNAL_LIMIT);
		String output = String.valueOf(sMultiplier.OUTPUT_SIGNAL_STRENGTH);
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6,4210752 );
		this.fontRenderer.drawString(input, this.xSize / 2 - 40, 41, 4210752 );
		this.fontRenderer.drawString(output, this.xSize / 2 + 37, 41, 4210752 );
		this.fontRenderer.drawString("Input", this.xSize / 2 - 49, 71, 4210752 );
		this.fontRenderer.drawString("Output", this.xSize / 2 + 25, 71, 4210752 );
	}
	@Override
	public void initGui() {
		super.initGui();
	 	int j = (width - xSize) / 2;
	    int k = (height - ySize) / 2;

	    this.buttonList.add(new GuiButton( 1, j+23, k+20, 18, 18, "+"));
	    this.buttonList.add(new GuiButton( 2, j+23, k+50, 18, 18, "-"));
	    this.buttonList.add(new GuiButton( 3, j+100, k+20, 18, 18, "+"));
	    this.buttonList.add(new GuiButton( 4, j+100, k+50, 18, 18, "-"));
	    this.buttonList.add(new GuiButton( 5, j+47, k+20, 46, 18, "Confirm"));
	    this.buttonList.add(new GuiButton( 6, j+47, k+50, 46, 18, "Clear"));
	}
	public boolean isShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	@Override
	protected void actionPerformed(GuiButton B) {
		int INPUT = sMultiplier.INPUT_SIGNAL_LIMIT;
		int OUTPUT = sMultiplier.OUTPUT_SIGNAL_STRENGTH;
		 if(B.id == 1) {
			 if(sMultiplier.INPUT_SIGNAL_LIMIT < 15) {
				 if(isShiftDown() && sMultiplier.INPUT_SIGNAL_LIMIT + 5 <= 15){
					 sMultiplier.INPUT_SIGNAL_LIMIT += 5;
				 }else if(isShiftDown() && sMultiplier.INPUT_SIGNAL_LIMIT + 5 > 15){
					 sMultiplier.INPUT_SIGNAL_LIMIT = 15;
				 }else{
					 sMultiplier.INPUT_SIGNAL_LIMIT ++;
				 }
			 }
	 		}	
		 if(B.id == 2) {
			 if(sMultiplier.INPUT_SIGNAL_LIMIT > 0) {
				 if(isShiftDown() && sMultiplier.INPUT_SIGNAL_LIMIT - 5 >= 0){
					 sMultiplier.INPUT_SIGNAL_LIMIT -= 5;
				 }else if(isShiftDown() && sMultiplier.INPUT_SIGNAL_LIMIT - 5 < 0){
					 sMultiplier.INPUT_SIGNAL_LIMIT = 0;
				 }else{
				 sMultiplier.INPUT_SIGNAL_LIMIT --;
				 }
			 }
		 	}	
		 if(B.id == 3) {
			 if(sMultiplier.OUTPUT_SIGNAL_STRENGTH < 15) {
				 if(isShiftDown() && sMultiplier.OUTPUT_SIGNAL_STRENGTH + 5 <= 15){
					 sMultiplier.OUTPUT_SIGNAL_STRENGTH += 5;
				 }else if(isShiftDown() && sMultiplier.OUTPUT_SIGNAL_STRENGTH + 5 > 15){
					 sMultiplier.OUTPUT_SIGNAL_STRENGTH = 15;
				 }else{
				 sMultiplier.OUTPUT_SIGNAL_STRENGTH ++;
				 }
			 }
	 		}	
		 if(B.id == 4) {
			 if(sMultiplier.OUTPUT_SIGNAL_STRENGTH > 0) {
				 if(isShiftDown() && sMultiplier.OUTPUT_SIGNAL_STRENGTH - 5 >= 0){
					 sMultiplier.OUTPUT_SIGNAL_STRENGTH -= 5;
				 }else if(isShiftDown() && sMultiplier.OUTPUT_SIGNAL_STRENGTH - 5 < 0){
					 sMultiplier.OUTPUT_SIGNAL_STRENGTH = 0;
				 }else{
				 sMultiplier.OUTPUT_SIGNAL_STRENGTH --;
				 }
			 }
		 	}	
		 if(B.id == 5) {
			IMessage msg = new PacketSignalMultiplier(INPUT, OUTPUT, sMultiplier.getPos());
			PacketHandler.net.sendToServer(msg);
		 }
		 if(B.id == 6) {
			 sMultiplier.OUTPUT_SIGNAL_STRENGTH = 0; 
			 sMultiplier.INPUT_SIGNAL_LIMIT = 0;
			 IMessage msg = new PacketSignalMultiplier(INPUT, OUTPUT, sMultiplier.getPos());
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


