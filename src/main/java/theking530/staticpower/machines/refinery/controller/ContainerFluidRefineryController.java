package theking530.staticpower.machines.refinery.controller;

import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class ContainerFluidRefineryController extends BaseContainer {
	
	public ContainerFluidRefineryController(InventoryPlayer invPlayer, TileEntityFluidRefineryController fluidRefinery) {
		this.addSlotToContainer(new StaticPowerContainerSlot(fluidRefinery.slotsInput, 0, 8, 20));
		this.addSlotToContainer(new StaticPowerContainerSlot(fluidRefinery.slotsInput, 1, 8, 44));
		this.addSlotToContainer(new StaticPowerContainerSlot(fluidRefinery.slotsInput, 2, 8, 68));
		
        
		addPlayerHotbar(invPlayer, 8, 156);
		addPlayerInventory(invPlayer, 8, 98);

	}
}
	

