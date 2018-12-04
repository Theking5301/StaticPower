package theking530.staticpower.client.render.conduit;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.conduits.TileEntityBaseConduit;
import theking530.staticpower.conduits.itemconduit.TileEntityItemConduit;

public class TileEntityRenderItemConduit extends TileEntityRenderBaseConduit {
	
	ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/ItemConduit.png");
	ResourceLocation pullTexture = new ResourceLocation(Reference.MOD_ID, "textures/models/conduits/ItemConduitPull.png");
	boolean drawInside = false;
	
	float pixel = 1F/16F;
	float texel = 1F/64F;
	static float width = .1F;
	
	@Override
    public void render(TileEntityBaseConduit tileentity, double translationX, double translationY, double translationZ, float f, int dest, float deltaTime) {
		TileEntityItemConduit conduit = (TileEntityItemConduit)tileentity;	

		GL11.glTranslated(translationX, translationY, translationZ);

		GL11.glDisable(GL11.GL_LIGHTING);	
		//GL11.glDisable(GL11.GL_ALPHA_TEST);
		//GL11.glEnable(GL11.GL_BLEND);

		for(int i = 0; i < conduit.receivers.length; i++){
			if(conduit.receivers[i] != null) {
				if(conduit.SIDE_MODES[i] == 1) {
					this.bindTexture(pullTexture);
				}else{
					this.bindTexture(texture);
				}
				drawConnection(conduit.receivers[i], tileentity, 9.0f, 12.0f);				
			}
		}
		if (!conduit.straightConnection(conduit.connections)) {
			this.bindTexture(texture);
			drawNode(tileentity, 11.5f); 			
			for(int i = 0; i < conduit.connections.length; i++) {
				if(conduit.connections[i] != null) {
					this.bindTexture(texture);
					drawCore(conduit.connections[i], tileentity, 11.5f);
				}
			}
		} else {
			for(int i = 0; i < conduit.connections.length; i++) {
				if(conduit.connections[i] != null) {	
					this.bindTexture(texture);
					drawStraight(conduit.connections[i], tileentity, 11.5f);
					break;
				}
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glDisable(GL11.GL_BLEND);
		GL11.glTranslated(-translationX, -translationY, -translationZ);	
		
		renderItemInConduit(conduit, translationX, translationY, translationZ, deltaTime);
	}
	
	public void renderItemInConduit(TileEntityItemConduit conduit, double x, double y, double z, float deltaTime) {
		EntityItem entItem = null;

		if(conduit.PREVIEW_STACK != null && conduit.PREVIEW_STACK != ItemStack.EMPTY) {
			ItemStack tempStack = conduit.PREVIEW_STACK.copy();
			tempStack.setCount(1);
			entItem = new EntityItem(conduit.getWorld(), x, y, z,  tempStack);
		}
		if(entItem != null && conduit.IS_MOVING) {
			float partialTick = Minecraft.getMinecraft().getRenderPartialTicks();		
			float previousPosition =  (float)Math.max(0, conduit.MOVE_TIMER-1)/(float)conduit.MOVE_RATE;
			float currentPosition =  (float)conduit.MOVE_TIMER/(float)conduit.MOVE_RATE;			
			float alpha = (previousPosition + (currentPosition - previousPosition) * partialTick);
			
			float yOffset = conduit.PREVIEW_STACK.getItem() instanceof ItemBlock ? 0.05f : 0.02f;
			float scale = conduit.PREVIEW_STACK.getItem() instanceof ItemBlock ? 0.75f : 0.70f;
			scale /= 1.5;
			EnumFacing direction = conduit.PREVIEW_DIRECTION;
			if(direction != null) {
				GL11.glPushMatrix();
				
				GL11.glTranslatef(0.5f, 0.25f, 0.5f);

				if(direction == EnumFacing.EAST) {
					GL11.glTranslatef(alpha, yOffset, 0.0f);
				}else if(direction == EnumFacing.WEST) {
					GL11.glTranslatef(-alpha, yOffset, 0.0f);
				}else if(direction == EnumFacing.NORTH) {
					GL11.glTranslatef(0.0f, yOffset, -alpha);
				}else if(direction == EnumFacing.SOUTH) {
					GL11.glTranslatef(0.0f, yOffset, alpha);
				}else if(direction == EnumFacing.UP) {
					GL11.glTranslatef(0.0f, alpha+yOffset, 0.0f);
				}else if(direction == EnumFacing.DOWN) {
					GL11.glTranslatef(0.0f, -alpha+yOffset, 0.0f);
				}
				
				GL11.glTranslated(x, y, z);	
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslated(-x, -y, -z);	
				
				entItem.hoverStart = 0.0f; //conduit.RANDOM_ROTATION + conduit.getWorld().getWorldTime()/50.0f;	
				Minecraft.getMinecraft().getRenderManager().renderEntity(entItem, x, y, z, conduit.getWorld().getWorldTime(), 0.0f, false);
				GL11.glPopMatrix();
			}
		}
	}
}
