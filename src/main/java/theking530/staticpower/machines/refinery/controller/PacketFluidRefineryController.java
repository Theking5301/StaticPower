package theking530.staticpower.machines.refinery.controller;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFluidRefineryController implements IMessage{
	
    private int x;
    private int y;
    private int z;

	private boolean allowStaticFlow;
	private boolean allowEnergizedFlow;
	private boolean allowLumumFlow;
	
	private short staticFlowRate;
	private short energizedFlowRate;
	private short lumumFlowRate;
	
	private boolean allowPurifierFlow;
	private boolean allowReagentFlow;
	private boolean allowNutralizerFlow;
	
	private short purifierFlowRate;
	private short reagentFlowRate;
	private short nutralizerFlowRate;
    
    public PacketFluidRefineryController() {}
    
    public PacketFluidRefineryController(TileEntityFluidRefineryController controller, BlockPos pos) {
    	allowStaticFlow = controller.getAllowStaticFlow();
    	allowEnergizedFlow = controller.getAllowEnergizedFlow();
    	allowLumumFlow = controller.getAllowLumumFlow();
		
    	allowPurifierFlow = controller.getAllowPurifierFlow();
    	allowReagentFlow = controller.getAllowReagentFlow();
    	allowNutralizerFlow = controller.getAllowNutralizerFlow();
		
    	staticFlowRate = controller.getStaticFlowRate();
    	energizedFlowRate = controller.getEnergizedFlowRate();
    	lumumFlowRate = controller.getLumumFlowRate();
		
    	purifierFlowRate = controller.getPurifierFlowRate();
    	reagentFlowRate = controller.getReagentFlowRate();
    	nutralizerFlowRate = controller.getNutralizerFlowRate();
	
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
    }   
    @Override
    public void fromBytes(ByteBuf buf) {
    	allowStaticFlow = buf.readBoolean();
		allowEnergizedFlow = buf.readBoolean();
		allowLumumFlow = buf.readBoolean();
		
		staticFlowRate = buf.readShort();
		energizedFlowRate = buf.readShort();
		lumumFlowRate = buf.readShort();
		
		allowPurifierFlow = buf.readBoolean();
		allowReagentFlow = buf.readBoolean();
		allowNutralizerFlow = buf.readBoolean();
		
		purifierFlowRate = buf.readShort();
		reagentFlowRate = buf.readShort();
		nutralizerFlowRate = buf.readShort();
    	
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
    }    
    @Override
    public void toBytes(ByteBuf buf) {
		buf.writeBoolean(allowStaticFlow);
		buf.writeBoolean(allowEnergizedFlow);
		buf.writeBoolean(allowLumumFlow);
		
		buf.writeShort(staticFlowRate);
		buf.writeShort(energizedFlowRate);
		buf.writeShort(lumumFlowRate);
		
		buf.writeBoolean(allowPurifierFlow);
		buf.writeBoolean(allowReagentFlow);
		buf.writeBoolean(allowNutralizerFlow);
		
		buf.writeShort(purifierFlowRate);
		buf.writeShort(reagentFlowRate);
		buf.writeShort(nutralizerFlowRate);
		
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
    }
    public static class Message implements IMessageHandler<PacketFluidRefineryController, IMessage> {
    @Override
    public IMessage onMessage(PacketFluidRefineryController message, MessageContext ctx) {
    		TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));
    		if(te != null && te instanceof TileEntityFluidRefineryController) {
    			TileEntityFluidRefineryController controller = (TileEntityFluidRefineryController)te;
    			controller.setAllowStaticFlow(message.allowStaticFlow);
    			controller.setAllowEnergizedFlow(message.allowEnergizedFlow);
    			controller.setAllowLumumFlow(message.allowLumumFlow);
    			
    			controller.setAllowPurifierFlow(message.allowPurifierFlow);
    			controller.setAllowReagentFlow(message.allowReagentFlow);
    			controller.setAllowNutralizerFlow(message.allowNutralizerFlow);
    			
    			controller.setStaticFlowRate(message.staticFlowRate);
       			controller.setEnergizedFlowRate(message.energizedFlowRate);
       			controller.setLumumFlowRate(message.lumumFlowRate);
       			
       			controller.setPurifierFlowRate(message.purifierFlowRate);
       			controller.setReagentFlowRate(message.reagentFlowRate);
       			controller.setNutralizerFlowRate(message.nutralizerFlowRate);
    		}
		return null;
    	}
    }
}

