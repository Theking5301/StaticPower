package theking530.staticpower.machines.fluidgenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerFluidGenerator extends BaseContainer {
	
	public ContainerFluidGenerator(InventoryPlayer invPlayer, TileEntityFluidGenerator teFluidGenerator) {	
		//Fluid Slots
		this.addSlotToContainer(new FluidContainerSlot(teFluidGenerator.slotsInput, 0, -24, 11));;
		this.addSlotToContainer(new OutputSlot(teFluidGenerator.slotsOutput, 0, -24, 43));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(teFluidGenerator.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(teFluidGenerator.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(teFluidGenerator.slotsUpgrades, 2, -24, 112));
		
		this.addPlayerHotbar(invPlayer, 8, 142);
		this.addPlayerInventory(invPlayer, 8, 84);		
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 2, 5, false)) {
        	return true;
        }
		return false;	
	}
}

