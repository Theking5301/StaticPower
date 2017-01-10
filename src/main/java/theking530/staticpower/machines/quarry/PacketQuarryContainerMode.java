package theking530.staticpower.machines.quarry;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theking530.staticpower.machines.machinecomponents.DrainToBucketComponent.FluidContainerInteractionMode;

public class PacketQuarryContainerMode implements IMessage{
	
    private static FluidContainerInteractionMode MODE;
    private static int x;
    private static int y;
    private static int z;

    public PacketQuarryContainerMode() {}
    
    public PacketQuarryContainerMode(FluidContainerInteractionMode mode, BlockPos pos) {
      MODE = mode;
      x = pos.getX();
      y = pos.getY();
      z = pos.getZ();
    }   
    @Override
    public void fromBytes(ByteBuf buf) {
      this.MODE = FluidContainerInteractionMode.values()[buf.readInt()];
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(MODE.ordinal());
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketQuarryContainerMode, IMessage> {
    @Override
    public IMessage onMessage(PacketQuarryContainerMode message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null && te instanceof TileEntityQuarry) {
    			TileEntityQuarry entity = (TileEntityQuarry)te;
    			entity.DRAIN_COMPONENT.setMode(message.MODE);
    			entity.updateBlock();
    		}
		return null;
    	}
    }
}
