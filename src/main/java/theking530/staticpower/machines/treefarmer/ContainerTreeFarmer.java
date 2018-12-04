package theking530.staticpower.machines.treefarmer;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerTreeFarmer extends BaseContainer {
	
	private Ingredient saplingIngredient;
	
	public ContainerTreeFarmer(InventoryPlayer invPlayer, TileEntityTreeFarm teFarmer) {
		saplingIngredient = Craft.ing("treeSapling");
		//Inputs
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1){
                this.addSlotToContainer(new StaticPowerContainerSlot(teFarmer.slotsInput, 1+i1 + l * 3, 30 + i1 * 18, 20 + l * 18));
            }
        }
        
        //Output
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1){
                this.addSlotToContainer(new OutputSlot(teFarmer.slotsOutput, i1 + l * 3, 90 + i1 * 18, 20 + l * 18));
            }
        }

        
		//FluidContainerSlots
		this.addSlotToContainer(new FluidContainerSlot(teFarmer.slotsInternal, 1, -24, 11));
		this.addSlotToContainer(new OutputSlot(teFarmer.slotsInternal, 2, -24, 43));
		
		//Axe
		this.addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(Items.IRON_AXE), teFarmer.slotsInput, 0, -24, 75) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
				return itemStack.getItem() instanceof ItemAxe;
		    }
		});
		
		//Battery
		this.addSlotToContainer(new BatterySlot(teFarmer.slotsInternal, 0, 8, 57));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 0, -24, 106));
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 1, -24, 124));
		this.addSlotToContainer(new UpgradeSlot(teFarmer.slotsUpgrades, 2, -24, 142));
		
		this.addPlayerInventory(invPlayer, 8, 90);
		this.addPlayerHotbar(invPlayer, 8, 148);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (saplingIngredient.apply(stack) && !mergeItemStack(stack, 0, 9, false)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 18)) {
        	return true;
        }
        if (stack.getItem() instanceof ItemAxe && !mergeItemStack(stack, 20)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 21)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 22, 25, false)) {
        	return true;
        }
		return false;	
	}
}

