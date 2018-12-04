package theking530.staticpower.machines.quarry;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.itemfilter.ItemFilter;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FilterSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerQuarry extends BaseContainer {
	
	public ContainerQuarry(InventoryPlayer invPlayer, TileEntityQuarry teQuarry) {
		//Filter
		this.addSlotToContainer(new FilterSlot(teQuarry.slotsInternal, 0, -24, 72));
		
		//Fluid Slots
		this.addSlotToContainer(new FluidContainerSlot(teQuarry.slotsInternal, 1, -24, 11));
		this.addSlotToContainer(new OutputSlot(teQuarry.slotsInternal, 2, -24, 43));
		
		//Battery
		this.addSlotToContainer(new BatterySlot(teQuarry.slotsInternal, 3, 8, 54));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teQuarry.slotsUpgrades, 0, -24, 101));
		this.addSlotToContainer(new UpgradeSlot(teQuarry.slotsUpgrades, 1, -24, 119));
		this.addSlotToContainer(new UpgradeSlot(teQuarry.slotsUpgrades, 2, -24, 137));
				
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof ItemFilter && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 1)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 3)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 4, 7, false)) {
        	return true;
        }
		return false;	
	}	
}

