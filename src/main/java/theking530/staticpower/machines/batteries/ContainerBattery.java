package theking530.staticpower.machines.batteries;

import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;


public class ContainerBattery extends BaseContainer {

	public ContainerBattery(InventoryPlayer invPlayer, TileEntityBattery teBattery) {
		this.addPlayerInventory(invPlayer, 8, 84);
		this.addPlayerHotbar(invPlayer, 8, 142);
	}
}

