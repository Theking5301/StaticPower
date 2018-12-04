package theking530.staticpower.client.render.tileentitys.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.RenderUtil;
import theking530.staticpower.machines.fermenter.TileEntityFermenter;

public class TileEntityRenderFermenter extends BaseMachineTESR<TileEntityFermenter> {

	private static final ResourceLocation frontOn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/fermenter_front_on.png");
	private static final ResourceLocation frontOff = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/fermenter_front_off.png");
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return machineOn ? frontOn : frontOff;
	}
	public void drawExtra(TileEntityFermenter tileentity, double translationX, double translationY, double translationZ, float f, int dest, float alpha) {
		for(int i=0; i<tileentity.slotsInput.getSlots(); i++) {
			drawItem(tileentity.slotsInput.getStackInSlot(i), (i % 3)*21, (i / 3)*10);
		}
		float texel = 1F/32F;
		RenderUtil.drawFluidInWorld(tileentity.fluidTank.getFluid(), tileentity.fluidTank.getCapacity(), 7.0F*texel, 6.0F*texel, 1.0005F, 18.0F*texel, 11f*texel);
	}
	public void drawItem(ItemStack stack, float xOffset, float yOffset) {	
		if(!stack.isEmpty()) {
	        GlStateManager.enableTexture2D(); 
	        
	        GlStateManager.pushMatrix();
	        RenderHelper.enableStandardItemLighting();

	        double scale = 0.009;
	        GlStateManager.scale(scale, -scale, scale/10);
	        GlStateManager.translate(26+xOffset, -58+yOffset, 1012);
	        
	        try {
	            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(stack, 0, 0);
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        
	        GlStateManager.disableBlend(); 
	        GlStateManager.enableAlpha(); 
	        GlStateManager.popMatrix();
		}
	}
}