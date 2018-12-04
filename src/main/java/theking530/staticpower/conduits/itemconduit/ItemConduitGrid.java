package theking530.staticpower.conduits.itemconduit;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.conduits.TileEntityNetwork;
import theking530.staticpower.conduits.TileEntityBaseConduit;
import theking530.staticpower.tileentity.chest.staticchest.TileEntityStaticChest;

public class ItemConduitGrid extends TileEntityNetwork<TileEntityBaseConduit, Capability<IItemHandler>, IItemHandler>{
	
	public ItemConduitGrid(World world) {
		super(world);
	}
	
	public boolean canSendItemTo(TileEntity reciever, EnumFacing insertDirection, ItemStack stack) {
		if(reciever == null) {
			return false;
		}
		if(!(reciever instanceof TileEntityStaticChest) && reciever.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertDirection)) {
			IItemHandler itemHandler = reciever.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertDirection);
			if(InventoryUtilities.canFullyInsertItemIntoInventory(itemHandler, stack)) {
				return true;
			}
		}
		return false;
	}
}
