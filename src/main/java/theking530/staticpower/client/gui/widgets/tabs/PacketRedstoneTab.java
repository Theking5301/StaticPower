package theking530.staticpower.client.gui.widgets.tabs;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theking530.staticpower.assists.utilities.RedstoneModeList.RedstoneMode;
import theking530.staticpower.tileentity.IRedstoneConfigurable;
 
public class PacketRedstoneTab implements IMessage{
    private int REDSTONE_MODE;
    private int x;
    private int y;
    private int z;

    public PacketRedstoneTab() {}
    
    public PacketRedstoneTab(int REDSTONE_MODE, BlockPos pos) {
      this.REDSTONE_MODE = REDSTONE_MODE;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    }   
    @Override
    public void fromBytes(ByteBuf buf) {
      this.REDSTONE_MODE = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(REDSTONE_MODE);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketRedstoneTab, IMessage> {
	    @Override
	    public IMessage onMessage(PacketRedstoneTab message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
			if(te != null && te instanceof IRedstoneConfigurable) {
				IRedstoneConfigurable entity = (IRedstoneConfigurable)te;
				entity.setRedstoneMode(RedstoneMode.getModeFromInt(message.REDSTONE_MODE));
			}
			return null;
    	}
    }
}
