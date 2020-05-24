package theking530.staticpower.utilities.customboundingbox;

import net.minecraft.entity.player.EntityPlayer;

public interface ICustomBoundingBox {
	
	boolean shouldRenderCustomHitBox(int subHit, EntityPlayer player);

	CustomBoundingBox getCustomBoundingBox(int subHit, EntityPlayer player);
}
