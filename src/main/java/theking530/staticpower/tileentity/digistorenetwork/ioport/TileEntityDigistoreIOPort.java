package theking530.staticpower.tileentity.digistorenetwork.ioport;

import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreTileEntity;

public class TileEntityDigistoreIOPort extends BaseDigistoreTileEntity {
	
	public TileEntityDigistoreIOPort() {

	}
	/*Capability Handling*/
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
        if(hasManager()) {
        	return getManager().hasCapability(capability, facing);
        }
		return super.hasCapability(capability, facing);
    }
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
        if(hasManager()) {
        	return getManager().getCapability(capability, facing);
        }
		return super.getCapability(capability, facing);
    }
}
