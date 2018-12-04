package theking530.staticpower.machines.poweredfurnace;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.SlotWithExperienceOutput;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerPoweredFurnace extends BaseContainer {
	
	public ContainerPoweredFurnace(InventoryPlayer invPlayer, TileEntityPoweredFurnace tePoweredSmelter) {
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(tePoweredSmelter.slotsInput, 0, 50, 28) {
			@Override
			public boolean isItemValid(ItemStack itemStack) {
				return !FurnaceRecipes.instance().getSmeltingResult(itemStack).isEmpty();	          
			}
		});
		
		//Battery
		this.addSlotToContainer(new BatterySlot(tePoweredSmelter.slotsInternal, 1, 8, 65));
		
		//Output
		this.addSlotToContainer(new SlotWithExperienceOutput(invPlayer.player, tePoweredSmelter.slotsOutput, 0, 109, 32));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(tePoweredSmelter.slotsUpgrades, 0, 152, 12));
		this.addSlotToContainer(new UpgradeSlot(tePoweredSmelter.slotsUpgrades, 1, 152, 32));
		this.addSlotToContainer(new UpgradeSlot(tePoweredSmelter.slotsUpgrades, 2, 152, 52));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);	
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty() && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 1)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 3, 6, false)) {
        	return true;
        }
		return false;	
	}
}

