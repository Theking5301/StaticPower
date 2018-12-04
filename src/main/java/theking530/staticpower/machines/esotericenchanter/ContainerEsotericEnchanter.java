package theking530.staticpower.machines.esotericenchanter;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerEsotericEnchanter extends BaseContainer {
	
	private TileEsotericEnchanter esotericEnchanter;

	public ContainerEsotericEnchanter(InventoryPlayer invPlayer, TileEsotericEnchanter teEsotericEnchanter) {
		esotericEnchanter = teEsotericEnchanter;
		
		//InputSlots
		this.addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(Items.BOOK), esotericEnchanter.slotsInput, 0, 30, 35));
		this.addSlotToContainer(new StaticPowerContainerSlot(esotericEnchanter.slotsInput, 1, 52, 35));
		this.addSlotToContainer(new StaticPowerContainerSlot(esotericEnchanter.slotsInput, 2, 74, 35));
		
		//OutputSlots
		this.addSlotToContainer(new OutputSlot(esotericEnchanter.slotsOutput, 0, 126, 35));
			
		//BatterySlot
		this.addSlotToContainer(new BatterySlot(esotericEnchanter.slotsInternal, 3, 8, 54));
		
		//FluidContainerSlots
		this.addSlotToContainer(new FluidContainerSlot(esotericEnchanter.slotsInternal, 4, -24, 11));
		this.addSlotToContainer(new OutputSlot(esotericEnchanter.slotsInternal, 5, -24, 43));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(esotericEnchanter.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(esotericEnchanter.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(esotericEnchanter.slotsUpgrades, 2, -24, 112));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() == Items.BOOK && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 4)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 5)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 7, 10, false)) {
        	return true;
        }
        if (!mergeItemStack(stack, 1, 3, false)) {
        	return true;
        }
		return false;	
	}
}

