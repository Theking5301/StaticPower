package theking530.staticpower.logic.gates.transducer;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
  
public class PacketSignalMultiplier implements IMessage{
    private int INT_INPUT;
    private int INT_OUTPUT;
    private int x;
    private int y;
    private int z;
    
    public PacketSignalMultiplier() {}
    
    public PacketSignalMultiplier(int INT_INPUT, int INT_OUTPUT, BlockPos pos) {
      this.INT_INPUT = INT_INPUT;
      this.INT_OUTPUT = INT_OUTPUT;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    } 
    @Override
    public void fromBytes(ByteBuf buf) {
      this.INT_INPUT = buf.readInt();
      this.INT_OUTPUT = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }  
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(INT_INPUT);
      buf.writeInt(INT_OUTPUT);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketSignalMultiplier, IMessage> {
    @Override
    public IMessage onMessage(PacketSignalMultiplier message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null) {
	    		TileEntitySignalMultiplier sMultiplier = (TileEntitySignalMultiplier)te;
	    		sMultiplier.INPUT_SIGNAL_LIMIT = message.INT_INPUT;
	    		sMultiplier.OUTPUT_SIGNAL_STRENGTH = message.INT_OUTPUT;
	    		sMultiplier.updateGate();
    		}
		return null;
    	}
    }
}
