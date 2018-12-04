package theking530.staticpower.machines.fermenter;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerFermenter extends BaseContainer {
	
	public ContainerFermenter(InventoryPlayer invPlayer, TileEntityFermenter teFermenter) {
		//Input
        for (int i= 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j){
                this.addSlotToContainer(new StaticPowerContainerSlot(teFermenter.slotsInput, j + i * 3, 40 + j * 18, 21 + i * 18){
        			@Override
        	        public boolean isItemValid(ItemStack itemStack) {
        		          return FermenterRecipeRegistry.Fermenting().getFluidResult(itemStack) != null;
        		    }
        		});
            }
        }
        //Output
        this.addSlotToContainer(new OutputSlot(teFermenter.slotsOutput, 0, 115, 55));
        
        //Battery
		this.addSlotToContainer(new BatterySlot(teFermenter.slotsInternal, 1, 8, 54));
        
        //Container Input and Output
        this.addSlotToContainer(new FluidContainerSlot(teFermenter.slotsInternal, 2, -24, 11));
		this.addSlotToContainer(new OutputSlot(teFermenter.slotsInternal, 3, -24, 43));
        
        //Upgrades
		this.addSlotToContainer(new UpgradeSlot(teFermenter.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(teFermenter.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(teFermenter.slotsUpgrades, 2, -24, 112));
		
		
		this.addPlayerInventory(invPlayer, 8, 90);
		this.addPlayerHotbar(invPlayer, 8, 148);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (FermenterRecipeRegistry.Fermenting().getFluidResult(stack) != null&& !mergeItemStack(stack, 0, 9, false)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 10)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 11)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 13, 16, false)) {
        	return true;
        }
		return false;	
	}
}

