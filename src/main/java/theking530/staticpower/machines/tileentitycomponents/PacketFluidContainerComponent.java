package theking530.staticpower.machines.tileentitycomponents;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;
import theking530.staticpower.tileentity.TileEntityBase;

public class PacketFluidContainerComponent implements IMessage{
	
    private FluidContainerInteractionMode mode;
    private int componentIndex;
    private int x;
    private int y;
    private int z;

    public PacketFluidContainerComponent() {}
    
    public PacketFluidContainerComponent(FluidContainerInteractionMode mode, int componentIndex, BlockPos pos) {
      this.mode = mode;
      this.componentIndex = componentIndex;
      this.x = pos.getX();
      this.y = pos.getY();
      this.z = pos.getZ();
    }   
    @Override
    public void fromBytes(ByteBuf buf) {
      this.mode = FluidContainerInteractionMode.values()[buf.readInt()];
      this.componentIndex = buf.readInt();
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
    }    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeInt(mode.ordinal());
      buf.writeInt(componentIndex);
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketFluidContainerComponent, IMessage> {
    @Override
    public IMessage onMessage(PacketFluidContainerComponent message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null && te instanceof TileEntityBase) {
    			TileEntityBase entity = (TileEntityBase)te;
    			if(message.componentIndex > 0 && entity.getComponents().get(message.componentIndex) instanceof FluidContainerComponent) {
        			((FluidContainerComponent)entity.getComponents().get(message.componentIndex)).setMode(message.mode);
        			entity.updateBlock();
    			}
    		}
		return null;
    	}
    }
}

