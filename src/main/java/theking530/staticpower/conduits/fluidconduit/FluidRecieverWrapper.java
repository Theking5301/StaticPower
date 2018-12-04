package theking530.staticpower.conduits.fluidconduit;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidRecieverWrapper {
	
	private TileEntity TE;

	public FluidRecieverWrapper(TileEntity te, IFluidHandler handler) {
		TE = te;
	}
	
	public void update() {

	}
	public TileEntity getTileEntity() {
		return TE;
	}
}
