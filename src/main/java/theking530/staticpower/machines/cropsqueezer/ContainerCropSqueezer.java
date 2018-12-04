package theking530.staticpower.machines.cropsqueezer;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.SqueezerRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerCropSqueezer extends BaseContainer {
	
	public ContainerCropSqueezer(InventoryPlayer invPlayer, TileEntityCropSqueezer teCropSqueezer) {
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(teCropSqueezer.slotsInput, 0, 80, 18) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(itemStack) != null;
		    }
		});
		
		//Fluid Slots
		this.addSlotToContainer(new FluidContainerSlot(teCropSqueezer.slotsInternal, 1, -24, 12));
		this.addSlotToContainer(new OutputSlot(teCropSqueezer.slotsInternal, 2, -24, 42));
		
		//BatterySlot
		this.addSlotToContainer(new BatterySlot(teCropSqueezer.slotsInternal, 3, 8, 54));
		
		//Output
		this.addSlotToContainer(new OutputSlot(teCropSqueezer.slotsOutput, 0, 80, 57));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teCropSqueezer.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(teCropSqueezer.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(teCropSqueezer.slotsUpgrades, 2, -24, 112));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (SqueezerRecipeRegistry.Squeezing().getSqueezingRecipe(stack) != null && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 1)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 3)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 5, 8, false)) {
        	return true;
        }
		return false;	
	}
}

