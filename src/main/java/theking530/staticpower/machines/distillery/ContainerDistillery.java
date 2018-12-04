package theking530.staticpower.machines.distillery;

import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;

public class ContainerDistillery extends BaseContainer {
	
	public ContainerDistillery(InventoryPlayer invPlayer, TileEntityDistillery teDistillery) {
		//Input Left
		//Input Left
		this.addSlotToContainer(new FluidContainerSlot(teDistillery.slotsInput, 0, 10, 17));
		this.addSlotToContainer(new OutputSlot(teDistillery.slotsOutput, 0, 10, 53));
		
		//Input Right
		this.addSlotToContainer(new FluidContainerSlot(teDistillery.slotsInput, 1, 150, 17));
		this.addSlotToContainer(new OutputSlot(teDistillery.slotsOutput, 1, 150, 53));

		this.addPlayerInventory(invPlayer, 8, 94);
		this.addPlayerHotbar(invPlayer, 8, 152);
	}
}

