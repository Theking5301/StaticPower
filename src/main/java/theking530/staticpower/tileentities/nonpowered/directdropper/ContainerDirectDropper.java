package theking530.staticpower.tileentities.nonpowered.directdropper;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.StaticPowerTileEntityContainer;
import theking530.staticpower.container.slots.StaticPowerContainerSlot;

public class ContainerDirectDropper extends StaticPowerTileEntityContainer<TileEntityDirectDropper> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerDirectDropper, GuiDirectDropper> TYPE = new ContainerTypeAllocator<>("direct_dropper", ContainerDirectDropper::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiDirectDropper::new);
		}
	}

	public ContainerDirectDropper(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityDirectDropper) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerDirectDropper(int windowId, PlayerInventory playerInventory, TileEntityDirectDropper owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		// Upgrades
		this.addSlotsInGrid(getTileEntity().inventory, 0, 89, 20, 3, (index, x, y) -> {
			return new StaticPowerContainerSlot(getTileEntity().inventory, index, x, y);
		});

		addPlayerInventory(getPlayerInventory(), 8, 84);
		addPlayerHotbar(getPlayerInventory(), 8, 142);
	}
}
