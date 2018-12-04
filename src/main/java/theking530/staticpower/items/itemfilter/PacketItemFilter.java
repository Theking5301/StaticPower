package theking530.staticpower.items.itemfilter;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketItemFilter implements IMessage{
    private boolean WHITE_LIST_MODE;
    private boolean MATCH_METADATA;
    private boolean MATCH_NBT;
    private boolean MATCH_ORE_DICT;
    
    public PacketItemFilter() {}
    
    public PacketItemFilter(boolean listMode, boolean checkMeta, boolean checkNBT, boolean checkOreDict) {
    	WHITE_LIST_MODE = listMode;
        MATCH_METADATA = checkMeta;
        MATCH_NBT = checkNBT;
        MATCH_ORE_DICT = checkOreDict;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
      WHITE_LIST_MODE = buf.readBoolean();
      MATCH_METADATA = buf.readBoolean();
      MATCH_NBT = buf.readBoolean();
      MATCH_ORE_DICT = buf.readBoolean();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
      buf.writeBoolean(WHITE_LIST_MODE);
      buf.writeBoolean(MATCH_METADATA);
      buf.writeBoolean(MATCH_NBT);
      buf.writeBoolean(MATCH_ORE_DICT);
    }
    public static class Message implements IMessageHandler<PacketItemFilter, IMessage> {
	    @Override
	    public IMessage onMessage(PacketItemFilter message, MessageContext ctx) {
	    	if(ctx.getServerHandler().player.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY && ctx.getServerHandler().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFilter) {
	    		ItemStack itemstack = ctx.getServerHandler().player.getHeldItem(EnumHand.MAIN_HAND);
	    		if(itemstack.hasTagCompound()) {
	    			itemstack.getTagCompound().setBoolean("WHITE_LIST_MODE", message.WHITE_LIST_MODE);
	    			itemstack.getTagCompound().setBoolean("MATCH_METADATA", message.MATCH_METADATA);
	    			itemstack.getTagCompound().setBoolean("MATCH_NBT", message.MATCH_NBT);
	    			itemstack.getTagCompound().setBoolean("MATCH_ORE_DICT", message.MATCH_ORE_DICT);
	    		}
	    	}
			return null;
    	}
    }
}
