package theking530.staticpower.tileentities.powered.autosolderingtable;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.slots.BatteryItemSlot;
import theking530.staticpower.container.slots.OutputSlot;
import theking530.staticpower.tileentities.nonpowered.solderingtable.AbstractContainerSolderingTable;

public class ContainerAutoSolderingTable extends AbstractContainerSolderingTable<TileEntityAutoSolderingTable> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerAutoSolderingTable, GuiAutoSolderingTable> TYPE = new ContainerTypeAllocator<>("machine_industrial_soldering_table",
			ContainerAutoSolderingTable::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiAutoSolderingTable::new);
		}
	}

	public ContainerAutoSolderingTable(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityAutoSolderingTable) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerAutoSolderingTable(int windowId, PlayerInventory playerInventory, TileEntityAutoSolderingTable owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		enableSolderingIronSlot = false;
		craftingGridXOffset = -18;
		super.initializeContainer();

		// Add the soldering iron slot.
		addSlot(new BatteryItemSlot(getTileEntity().batteryInventory, 0, 8, 57));
	}

	@Override
	protected void addOutputSlot() {
		addSlot(new OutputSlot(getTileEntity().outputInventory, 0, 129, 38));
	}
}
