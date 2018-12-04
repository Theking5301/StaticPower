package theking530.staticpower.tileentity;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.SideModeList.Mode;

public interface ItemStackHandlerFilter {

    public boolean canInsertItem(Mode sideMode, int slot, ItemStack stack);
    public boolean canExtractItem(Mode sideMode, int slot, int amount);
    
}
