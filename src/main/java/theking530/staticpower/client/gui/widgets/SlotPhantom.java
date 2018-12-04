package theking530.staticpower.client.gui.widgets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class SlotPhantom extends StaticPowerContainerSlot{
	private ItemStackHandler itemHandler;
	
	public SlotPhantom(ItemStackHandler inv, int index, int x, int y) {
		super(inv, index, x, y);
		itemHandler = inv;
	}
    @Override
    public boolean canTakeStack(EntityPlayer player) {
    	super.canTakeStack(player);
    	itemHandler.setStackInSlot(slotNumber, ItemStack.EMPTY);
        return false;
    }
    public boolean isItemValid(ItemStack itemStack){
    	ItemStack tempItemStack = itemStack.copy();
    	tempItemStack.setCount(1);
    	itemHandler.setStackInSlot(slotNumber, tempItemStack);
        return false;
    }
}
