package theking530.staticpower.machines.poweredgrinder;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.GrinderRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerPoweredGrinder extends BaseContainer {
	
	public ContainerPoweredGrinder(InventoryPlayer invPlayer, TileEntityPoweredGrinder tePoweredGrinder) {		
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(tePoweredGrinder.slotsInput, 0, 80, 18) {
			@Override
			public boolean isItemValid(ItemStack itemStack) {
				return GrinderRecipeRegistry.Grinding().getGrindingRecipe(itemStack) != null;	          
			}
		});
		
		//Battery
		this.addSlotToContainer(new BatterySlot(tePoweredGrinder.slotsInternal, 1, 8, 65));
		
		//Output
		this.addSlotToContainer(new OutputSlot(tePoweredGrinder.slotsOutput, 0, 80, 60));
		this.addSlotToContainer(new OutputSlot(tePoweredGrinder.slotsOutput, 1, 106, 46));
		this.addSlotToContainer(new OutputSlot(tePoweredGrinder.slotsOutput, 2, 54, 46));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 0, 152, 12));
		this.addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 1, 152, 32));
		this.addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 2, 152, 52));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (GrinderRecipeRegistry.Grinding().getGrindingRecipe(stack) != null && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 1)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 5, 8, false)) {
        	return true;
        }
		return false;	
	}
}

