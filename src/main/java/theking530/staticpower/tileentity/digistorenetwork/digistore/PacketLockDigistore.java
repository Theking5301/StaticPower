package theking530.staticpower.tileentity.digistorenetwork.digistore;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 
public class PacketLockDigistore implements IMessage{
    private boolean isLocked;
    private int x;
    private int y;
    private int z;

    public PacketLockDigistore() {}
    
    public PacketLockDigistore(boolean isLocked, BlockPos pos) {
      this.isLocked = isLocked;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    }   
    @Override
    public void fromBytes(ByteBuf buf) {
      this.isLocked = buf.readBoolean();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeBoolean(isLocked);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketLockDigistore, IMessage> {
	    @Override
	    public IMessage onMessage(PacketLockDigistore message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
			if(te != null && te instanceof TileEntityDigistore) {
				TileEntityDigistore entity = (TileEntityDigistore)te;
				entity.setLocked(message.isLocked);
			}
			return null;
    	}
    }
}
