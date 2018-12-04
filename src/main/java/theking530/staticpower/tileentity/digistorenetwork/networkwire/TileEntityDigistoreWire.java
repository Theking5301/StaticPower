package theking530.staticpower.tileentity.digistorenetwork.networkwire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreTileEntity;

public class TileEntityDigistoreWire extends TileEntity  implements ITickable{
	
	public EnumFacing[] connections = new EnumFacing[6];
	public EnumFacing[] receivers = new EnumFacing[6];
	
	@Override
	public void update() {
		updateConduitRenderConnections();
		updateRecieverRenderConnections();
	}
	
	public void updateConduitRenderConnections() {
		if(isConduit(EnumFacing.UP)) connections[0] = EnumFacing.UP;
		else connections[0] = null;
		if(isConduit(EnumFacing.DOWN)) connections[1] = EnumFacing.DOWN;
		else connections[1] = null;
		if(isConduit(EnumFacing.NORTH)) connections[2] = EnumFacing.NORTH;
		else connections[2] = null;
		if(isConduit(EnumFacing.SOUTH)) connections[3] = EnumFacing.SOUTH;
		else connections[3] = null;
		if(isConduit(EnumFacing.EAST)) connections[4] = EnumFacing.EAST;
		else connections[4] = null;
		if(isConduit(EnumFacing.WEST)) connections[5] = EnumFacing.WEST;
		else connections[5] = null;	
		
	}	
	public void updateRecieverRenderConnections() {
		if(isReciever(EnumFacing.UP)) receivers[0] = EnumFacing.UP;
		else receivers[0] = null;
		if(isReciever(EnumFacing.DOWN)) receivers[1] = EnumFacing.DOWN;
		else receivers[1] = null;
		if(isReciever(EnumFacing.NORTH)) receivers[3] = EnumFacing.NORTH;
		else receivers[3] = null;
		if(isReciever(EnumFacing.SOUTH)) receivers[2] = EnumFacing.SOUTH;
		else receivers[2] = null;
		if(isReciever(EnumFacing.EAST)) receivers[4] = EnumFacing.EAST;
		else receivers[4] = null;
		if(isReciever(EnumFacing.WEST)) receivers[5] = EnumFacing.WEST;
		else receivers[5] = null;		
	}
	public boolean isConduit(EnumFacing side) {
		return getWorld().getTileEntity(getPos().offset(side)) != null && getWorld().getTileEntity(getPos().offset(side)) instanceof TileEntityDigistoreWire;
	}		
	public boolean isReciever(EnumFacing side) {
		return getWorld().getTileEntity(getPos().offset(side)) != null && getWorld().getTileEntity(getPos().offset(side)) instanceof BaseDigistoreTileEntity;
	}	
	public boolean straightConnection(EnumFacing[] directions) {
		EnumFacing mainDirection = null;
		boolean isOpposite = false;
		
		for (int i = 0; i < directions.length; i++) {
			if(mainDirection == null && directions[i] != null) mainDirection = directions[i];
			
			if(directions[i] != null && mainDirection != directions[i]) {
				if(mainDirection.getOpposite() != directions[i]) {
					return false;
				} else {
					isOpposite = true;
				}
			}
		}	
		return isOpposite;
	}
}
