package theking530.staticpower.logic.gates.powercell;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
  
public class PacketPowerCell implements IMessage{
    private int POWER;
    private int x;
    private int y;
    private int z;
    
    public PacketPowerCell() {}
    
    public PacketPowerCell(int POWER, BlockPos pos) {
      this.POWER = POWER;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    } 
    @Override
    public void fromBytes(ByteBuf buf) {
      this.POWER = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }  
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(POWER);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketPowerCell, IMessage> {
    @Override
    public IMessage onMessage(PacketPowerCell message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null) {
	    		TileEntityPowerCell sMultiplier = (TileEntityPowerCell)te;
	    		sMultiplier.POWER = message.POWER;
	    		sMultiplier.updateGate();
    		}
		return null;
    	}
    }
}
