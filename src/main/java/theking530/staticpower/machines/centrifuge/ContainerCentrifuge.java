package theking530.staticpower.machines.centrifuge;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.CentrifugeRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerCentrifuge extends BaseContainer {
	
	public ContainerCentrifuge(InventoryPlayer invPlayer, TileEntityCentrifuge teCentrifuge) {
		//Input
		addSlotToContainer(new StaticPowerContainerSlot(teCentrifuge.slotsInput, 0, 80, 20));
		addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(Items.FLINT), teCentrifuge.slotsInput, 1, 40, 30).setMode(Mode.Input2));
		
		//Battery
		addSlotToContainer(new BatterySlot(teCentrifuge.slotsInternal, 1, 8, 65));
		
		//Output
		addSlotToContainer(new OutputSlot(teCentrifuge.slotsOutput, 0, 61, 57));
		addSlotToContainer(new OutputSlot(teCentrifuge.slotsOutput, 1, 80, 57));
		addSlotToContainer(new OutputSlot(teCentrifuge.slotsOutput, 2, 99, 57));
		
		//Upgrades
		addSlotToContainer(new UpgradeSlot(teCentrifuge.slotsUpgrades, 0, 152, 12));
		addSlotToContainer(new UpgradeSlot(teCentrifuge.slotsUpgrades, 1, 152, 32));
		addSlotToContainer(new UpgradeSlot(teCentrifuge.slotsUpgrades, 2, 152, 52));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);

	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (CentrifugeRecipeRegistry.Centrifuging().getRecipe(stack) != null && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 2)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 6, 9, false)) {
        	return true;
        }
		return false;	
	}
}

