package theking530.staticpower.machines.batteries;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;

  
public class PacketGuiBattery implements IMessage{
    private int INPUT_PER_TICK;
    private int OUTPUT_PER_TICK;
    private int x;
    private int y;
    private int z;

    
    public PacketGuiBattery() {}
    
    public PacketGuiBattery(int INPUT_PER_TICK, int OUTPUT_PER_TICK, BlockPos pos) {
      this.INPUT_PER_TICK = INPUT_PER_TICK;
      this.OUTPUT_PER_TICK = OUTPUT_PER_TICK;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
      // the order is important
      this.INPUT_PER_TICK = buf.readInt();
      this.OUTPUT_PER_TICK = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(INPUT_PER_TICK);
      buf.writeInt(OUTPUT_PER_TICK);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketGuiBattery, IMessage> {
	    @Override
	    public IMessage onMessage(PacketGuiBattery message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
			if(te != null && te instanceof TileEntityBattery) {
				TileEntityBattery battery = (TileEntityBattery)te;
				battery.setInputLimit(message.INPUT_PER_TICK);
				battery.setOutputLimit(message.OUTPUT_PER_TICK);
			}
			return null;
	    }
    }
}
