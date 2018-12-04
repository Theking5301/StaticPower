package theking530.staticpower.client.gui.widgets.tabs;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;
  
public class PacketPowerControlTab implements IMessage{
    private int minPowerThreshold;
    private int maxPowerThreshold;
    private int x;
    private int y;
    private int z;

    public PacketPowerControlTab() {}
    
    public PacketPowerControlTab(int minPowerThreshold, int maxPowerThreshold, BlockPos pos) {
		this.maxPowerThreshold = minPowerThreshold;
		this.minPowerThreshold = maxPowerThreshold;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
      // the order is important
		this.maxPowerThreshold = buf.readInt();
		this.minPowerThreshold = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
		buf.writeInt(maxPowerThreshold);
		buf.writeInt(minPowerThreshold);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketPowerControlTab, IMessage> {
    	@Override
    	public IMessage onMessage(PacketPowerControlTab message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null && te instanceof TileEntityBattery) {
    			TileEntityBattery entity = (TileEntityBattery)te;
    			entity.setMinimumPowerThreshold(message.minPowerThreshold);
    			entity.setMaximumPowerThreshold(message.maxPowerThreshold);
    		}
		return null;
    	}
    }
}
