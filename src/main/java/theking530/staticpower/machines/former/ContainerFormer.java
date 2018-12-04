package theking530.staticpower.machines.former;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.FormerRecipeRegistry;
import theking530.staticpower.items.FormerMolds;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerFormer extends BaseContainer {
	
	private TileEntityFormer formerTileEntity;
	
	public ContainerFormer(InventoryPlayer invPlayer, TileEntityFormer tePoweredGrinder) {
		formerTileEntity = tePoweredGrinder;
		//Input Former
		addSlotToContainer(new StaticPowerContainerSlot(tePoweredGrinder.slotsInput, 0, 59, 34) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
				return FormerRecipeRegistry.Forming().getFormingResult(itemStack, tePoweredGrinder.slotsInput.getStackInSlot(1)) != null;	          
		    }
		});
		//Input Mold
		addSlotToContainer(new StaticPowerContainerSlot(FormerMolds.moldPlate, 0.3f, tePoweredGrinder.slotsInput, 1, 37, 34) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
				return FormerRecipeRegistry.Forming().isValidMold(itemStack);	          
		    }
		});
		//Output
		addSlotToContainer(new OutputSlot(tePoweredGrinder.slotsOutput, 0, 118, 35));
		

		//Battery
		addSlotToContainer(new BatterySlot(tePoweredGrinder.slotsInternal, 1, 8, 65));
		
		//Upgrades
		addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 0, 152, 12));
		addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 1, 152, 32));
		addSlotToContainer(new UpgradeSlot(tePoweredGrinder.slotsUpgrades, 2, 152, 52));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (FormerRecipeRegistry.Forming().getFormingResult(stack, formerTileEntity.slotsInput.getStackInSlot(1)) != null && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (FormerRecipeRegistry.Forming().isValidMold(stack) && !mergeItemStack(stack, 1)) {
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

