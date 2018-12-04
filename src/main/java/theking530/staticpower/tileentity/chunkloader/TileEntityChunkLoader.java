package theking530.staticpower.tileentity.chunkloader;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChunkLoader extends TileEntity {

	private UUID placerUUID;
	
	public void placedByPlayer(EntityLivingBase player) {
		placerUUID = player.getUniqueID();
	}
	public UUID getPlacer() {
		return placerUUID;
	}
}
