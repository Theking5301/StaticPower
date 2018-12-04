package theking530.staticpower.machines.fusionfurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class ContainerFusionFurnace extends BaseContainer {
	
	public ContainerFusionFurnace(InventoryPlayer invPlayer, TileEntityFusionFurnace teFusionFurnace) {
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsInput, 0, 36, 40));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsInput, 1, 58, 28));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsInput, 2, 80, 17));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsInput, 3, 102, 28));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsInput, 4, 124, 40));
		
		//Output
		this.addSlotToContainer(new OutputSlot(teFusionFurnace.slotsOutput, 0, 80, 59));

		//Upgrades
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsUpgrades, 0, 152, 12));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsUpgrades, 1, 152, 32));
		this.addSlotToContainer(new StaticPowerContainerSlot(teFusionFurnace.slotsUpgrades, 2, 152, 52));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);		
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 6, 9, false)) {
        	return true;
        }
        if (!mergeItemStack(stack, 0, 5, false)) {
        	return true;
        }
		return false;	
	}
}

