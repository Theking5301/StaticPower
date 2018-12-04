package theking530.staticpower.machines.fluidinfuser;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.InfuserRecipeRegistry;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.machines.tileentitycomponents.slots.BatterySlot;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.UpgradeSlot;

public class ContainerFluidInfuser extends BaseContainer {
	
	private TileEntityFluidInfuser infuserTileEntity;
	
	public ContainerFluidInfuser(InventoryPlayer invPlayer, TileEntityFluidInfuser teFluidInfuser) {
		infuserTileEntity = teFluidInfuser;
		
		//Input
		this.addSlotToContainer(new StaticPowerContainerSlot(teFluidInfuser.slotsInput, 0, 50, 32));
		
		//FluidContainerSlots
		this.addSlotToContainer(new FluidContainerSlot(infuserTileEntity.slotsInternal, 1, -24, 11));
		this.addSlotToContainer(new OutputSlot(infuserTileEntity.slotsInternal, 2, -24, 43));
		
		//Battery
		this.addSlotToContainer(new BatterySlot(infuserTileEntity.slotsInternal, 3, 8, 54));
		
		//Output
		this.addSlotToContainer(new OutputSlot(infuserTileEntity.slotsOutput, 0, 107, 32));
		
		//Upgrades
		this.addSlotToContainer(new UpgradeSlot(infuserTileEntity.slotsUpgrades, 0, -24, 76));
		this.addSlotToContainer(new UpgradeSlot(infuserTileEntity.slotsUpgrades, 1, -24, 94));
		this.addSlotToContainer(new UpgradeSlot(infuserTileEntity.slotsUpgrades, 2, -24, 112));
		
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (InfuserRecipeRegistry.Infusing().getInfusingRecipe(stack, infuserTileEntity.fluidTank.getFluid(), true) != null && !mergeItemStack(stack, 0)) {
        	return true;
        }
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !mergeItemStack(stack, 1)) {
        	return true;
        }
        if (stack.getItem() instanceof IEnergyContainerItem && !mergeItemStack(stack, 3)) {
        	return true;
        }
        if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 4, 8, false)) {
        	return true;
        }
		return false;	
	}
}

