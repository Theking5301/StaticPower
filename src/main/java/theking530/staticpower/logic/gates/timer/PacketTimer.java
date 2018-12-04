package theking530.staticpower.logic.gates.timer;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
  
public class PacketTimer implements IMessage{
    private int SPEED;
    private int x;
    private int y;
    private int z;
    
    public PacketTimer() {}
    
    public PacketTimer(int SPEED, BlockPos pos) {
      this.SPEED = SPEED;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    } 
    @Override
    public void fromBytes(ByteBuf buf) {
      this.SPEED = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }  
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(SPEED);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketTimer, IMessage> {
    @Override
    public IMessage onMessage(PacketTimer message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null) {
	    		TileEntityTimer sMultiplier = (TileEntityTimer)te;
	    		sMultiplier.SPEED = message.SPEED;
	    		sMultiplier.updateGate();
    		}
		return null;
    	}
    }
}
