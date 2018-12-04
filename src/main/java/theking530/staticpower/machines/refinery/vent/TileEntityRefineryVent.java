package theking530.staticpower.machines.refinery.vent;

import net.minecraft.util.EnumParticleTypes;
import theking530.staticpower.machines.refinery.BaseRefineryTileEntity;

public class TileEntityRefineryVent extends BaseRefineryTileEntity {	

	public TileEntityRefineryVent() {
		super();
	}
	@Override
	public void process() {
		getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 1.0D, 0.0D, 0.0D, 0.0D, new int[0]);
		getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 1.0D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
		getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
		getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.0D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
	}
}

