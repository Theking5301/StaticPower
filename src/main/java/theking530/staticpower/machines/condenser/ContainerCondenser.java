package theking530.staticpower.machines.condenser;

import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.FluidContainerSlot;
import theking530.staticpower.machines.tileentitycomponents.slots.OutputSlot;

public class ContainerCondenser extends BaseContainer {

	public ContainerCondenser(InventoryPlayer invPlayer, TileEntityCondenser teCondenser) {
		//Input Left
		this.addSlotToContainer(new FluidContainerSlot(teCondenser.slotsInput, 0, 10, 17));
		this.addSlotToContainer(new OutputSlot(teCondenser.slotsOutput, 0, 10, 53));
		
		//Input Right
		this.addSlotToContainer(new FluidContainerSlot(teCondenser.slotsInput, 1, 150, 17));
		this.addSlotToContainer(new OutputSlot(teCondenser.slotsOutput, 1, 150, 53));
		
		this.addPlayerInventory(invPlayer, 8, 91);
		this.addPlayerHotbar(invPlayer, 8, 149);
	}	
}

