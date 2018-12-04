package theking530.staticpower.client.gui.widgets.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import theking530.staticpower.client.gui.GuiTextures;

public class ArrowButton extends GuiButton{
	
	public ArrowButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);

	}
	
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiTextures.ARROW_TEXTURES);

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            if(i == 1) {
                GlStateManager.color(0.8F, 0.8F, 0.8F, 1.0F);
            }else if(i == 2) {
                GlStateManager.color(0.8F, 0.8F, 1.0F, 1.0F);
            }else{
                GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
            }
            
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            double vMin = 0.0;
            double vMax = 0.5;
            
            if(displayString == ">") {
            	vMin = 0.5;
                vMax = 1.0;
            }
            
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(x, y + height, (double)this.zLevel).tex(0, vMax).endVertex();
            vertexbuffer.pos(x + width, y + height, (double)this.zLevel).tex(1, vMax).endVertex();
            vertexbuffer.pos(x + width, y, (double)this.zLevel).tex(1, vMin).endVertex();
            vertexbuffer.pos(x, y, (double)this.zLevel).tex(0, vMin).endVertex();
            tessellator.draw();
            
            this.mouseDragged(mc, mouseX, mouseY);

            //drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, j);

        }
    }
}
