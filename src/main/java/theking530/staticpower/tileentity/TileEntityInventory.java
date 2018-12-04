package theking530.staticpower.tileentity;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.assists.utilities.SideModeList.Mode;

public class TileEntityInventory extends ItemStackHandler {
	
	private ItemStackHandlerFilter filter;
	
    public TileEntityInventory(int size) {
        super(size);
    }
    
    public ItemStack insertItemToSide(Mode sideMode, int slot, @Nonnull ItemStack stack, boolean simulate) {
    	if(filter != null) {
    		if(!filter.canInsertItem(sideMode, slot, stack)) {
    			return stack;
    		}
    	}
        return insertItem(slot, stack, simulate);
    }
    public ItemStack extractItemFromSide(Mode sideMode, int slot, int amount, boolean simulate) {
    	if(filter != null) {
    		if(!filter.canExtractItem(sideMode, slot, amount)) {
    			return ItemStack.EMPTY;
    		}
    	}
    	return extractItem(slot, amount, simulate);
    }

}
