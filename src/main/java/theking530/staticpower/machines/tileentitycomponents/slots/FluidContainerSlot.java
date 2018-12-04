package theking530.staticpower.machines.tileentitycomponents.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.items.ModItems;

public class FluidContainerSlot extends StaticPowerContainerSlot {

	public FluidContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(new ItemStack(ModItems.BaseFluidCapsule), itemHandler, index, xPosition, yPosition);
	}
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
		return !stack.isEmpty() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);        
    }
}
