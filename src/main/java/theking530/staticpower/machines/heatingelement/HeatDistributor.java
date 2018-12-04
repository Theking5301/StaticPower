package theking530.staticpower.machines.heatingelement;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class HeatDistributor {

	public TileEntity TE;
	public HeatStorage STORAGE;
	
	public HeatDistributor(TileEntity te, HeatStorage storage) {
		TE = te;
		STORAGE = storage;
	}
 	public int provideHeat() {
 		for(EnumFacing facing : EnumFacing.values()) {
			if(STORAGE.getHeat() > 0) {
				TileEntity te = TE.getWorld().getTileEntity(TE.getPos().offset(facing));
				if(te != null && te instanceof IHeatable) {
					IHeatable heatable = (IHeatable)te;
					if(heatable.getHeat() < heatable.getMaxHeat()) {
						return STORAGE.extractHeat(heatable.recieveHeat(Math.min(10, STORAGE.getHeat())));
					}
				}	
			}
 		}
		return 0;	
	}
}
