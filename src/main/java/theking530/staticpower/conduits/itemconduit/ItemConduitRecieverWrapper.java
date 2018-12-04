package theking530.staticpower.conduits.itemconduit;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ItemConduitRecieverWrapper {
	private BlockPos RECIEVER;
	private EnumFacing DIRECTION;
	private ItemStack ITEM_STACK;
	
	public ItemConduitRecieverWrapper(BlockPos reciever, EnumFacing facing, ItemStack stack) {
		RECIEVER = reciever;
		DIRECTION = facing;
		ITEM_STACK = stack;
	}
	
	public BlockPos getReciever() {
		return RECIEVER;
	}
	public EnumFacing getInsertFacing() {
		return DIRECTION;
	}
	public ItemStack getItemStack() {
		return ITEM_STACK;
	}
}
