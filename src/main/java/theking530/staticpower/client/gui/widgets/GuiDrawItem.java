package theking530.staticpower.client.gui.widgets;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class GuiDrawItem {
	
	public static RenderItem render = Minecraft.getMinecraft().getRenderItem();
	public static Vec3d COLOR;
	public static boolean SHOULD_DRAW = true;
	
	public GuiDrawItem(boolean ShouldDraw) {
		SHOULD_DRAW = ShouldDraw;
	}
	
	public void setShouldDraw(boolean ShouldDraw) {
		SHOULD_DRAW = ShouldDraw;
	}
	public static void drawItem(Item item, int guiLeft, int guiTop, int x, int y, float zLevel, float alpha) {
        if(SHOULD_DRAW) {
        	renderItemModelIntoGUI(new ItemStack(item), guiLeft+x, guiTop+y, zLevel, alpha, render.getItemModelWithOverrides(new ItemStack(item), Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
        }
    }
	public static void drawItem(ItemStack item, int guiLeft, int guiTop, int x, int y, float zLevel, float alpha) {
        if(SHOULD_DRAW) {
        	renderItemModelIntoGUI(item, guiLeft+x, guiTop+y, zLevel, alpha, render.getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
        }
    }
    protected static void renderItemModelIntoGUI(ItemStack stack, int x, int y, float zLevel, float alpha, IBakedModel bakedmodel) { 
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f-alpha);
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		GL11.glEnable(GL11.GL_BLEND);
        //Render overlay to fake transparency
        GlStateManager.color(0.5429f, 0.5429f, 0.5429f, 1.0f-alpha);
        GlStateManager.disableTexture2D();    
        GlStateManager.disableDepth();
        GlStateManager.enableBlend(); 

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder tes = tessellator.getBuffer();
        tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        tes.pos(x, y + 16, Minecraft.getMinecraft().getRenderItem().zLevel).endVertex();
        tes.pos(x + 16, y + 16, Minecraft.getMinecraft().getRenderItem().zLevel).endVertex();
        tes.pos(x + 16, y, Minecraft.getMinecraft().getRenderItem().zLevel).endVertex();
        tes.pos(x, y, Minecraft.getMinecraft().getRenderItem().zLevel).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D(); 
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		//GL11.glDisable(GL11.GL_BLEND);
    }
}
