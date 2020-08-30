package theking530.staticpower.tileentities.powered.poweredfurnace;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import theking530.staticpower.container.StaticPowerTileEntityContainer;
import theking530.staticpower.container.slots.BatteryItemSlot;
import theking530.staticpower.container.slots.OutputSlot;
import theking530.staticpower.container.slots.StaticPowerContainerSlot;
import theking530.staticpower.container.slots.UpgradeItemSlot;
import theking530.staticpower.init.ModContainerTypes;

public class ContainerPoweredFurnace extends StaticPowerTileEntityContainer<TileEntityPoweredFurnace> {

	public ContainerPoweredFurnace(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityPoweredFurnace) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerPoweredFurnace(int windowId, PlayerInventory playerInventory, TileEntityPoweredFurnace owner) {
		super(ModContainerTypes.POWERED_FURNACE_CONTAINER, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		// Input
		this.addSlot(new StaticPowerContainerSlot(getTileEntity().inputInventory, 0, 50, 28));

		// Battery
		this.addSlot(new BatteryItemSlot(getTileEntity().batteryInventory, 0, 8, 64));

		// Output
		this.addSlot(new OutputSlot(getTileEntity().outputInventory, 0, 109, 32));

		// Upgrades
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 0, 152, 12));
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 1, 152, 32));
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 2, 152, 52));

		this.addPlayerInventory(getPlayerInventory(), 8, 84);
		this.addPlayerHotbar(getPlayerInventory(), 8, 142);
	}
}
