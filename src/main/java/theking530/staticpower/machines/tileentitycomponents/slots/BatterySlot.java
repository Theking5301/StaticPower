package theking530.staticpower.machines.tileentitycomponents.slots;

import javax.annotation.Nonnull;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.items.ModItems;

public class BatterySlot extends StaticPowerContainerSlot {

	public BatterySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(new ItemStack(ModItems.BasicBattery), itemHandler, index, xPosition, yPosition);
	}
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof IEnergyContainerItem;	          
    }

}
