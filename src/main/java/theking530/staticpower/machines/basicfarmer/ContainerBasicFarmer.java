package theking530.staticpower.machines.basicfarmer;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerBasicFarmer extends BaseContainer {
	
	public ContainerBasicFarmer(InventoryPlayer invPlayer, TileEntityBasicFarmer teFarmer) {
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1){
                this.addSlotToContainer(new OutputSlot(teFarmer.slotsOutput, i1 + l * 3, 76 + i1 * 18, 20 + l * 18));
            }
        }

		//FluidContainerSlots
		this.addSlotToContainer(new FluidContainerSlot(teFarmer.slotsInternal, 1, -24, 11));
		this.addSlotToContainer(new OutputSlot(teFarmer.slotsInternal, 2, -24, 43));
		
		//Hoe
		this.addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(Items.IRON_HOE), teFarmer.slotsInput, 0, 48, 20) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
				return itemStack.getItem() instanceof ItemHoe;
		    }
		});
		
		//Axe
		this.addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(Items.IRON_AXE), teFarmer.slotsInput, 1, 48, 56) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
				return itemStack.getItem() instanceof ItemAxe;
		    }
		});
		
		//Battery
		this.addSlotToContainer(new BatterySlot(teFarmer.slotsInternal, 0, 8, 57));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 2, -24, 112));
		
		this.addPlayerInventory(invPlayer, 8, 90);
		this.addPlayerHotbar(invPlayer, 8, 148);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 9)) {
        	return true;
        }
        if (stack.getItem() instanceof ItemHoe && !mergeItemStack(stack, 11)) {
        	return true;
        }
        if (stack.getItem() instanceof ItemAxe && !mergeItemStack(stack, 12)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 13)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 14, 17, false)) {
        	return true;
        }
		return false;	
	}
}

