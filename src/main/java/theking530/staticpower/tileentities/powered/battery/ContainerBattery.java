package theking530.staticpower.tileentities.powered.battery;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.StaticPowerTileEntityContainer;

public class ContainerBattery extends StaticPowerTileEntityContainer<TileEntityBattery> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerBattery, GuiBattery> TYPE = new ContainerTypeAllocator<>("battery", ContainerBattery::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiBattery::new);
		}
	}

	public ContainerBattery(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityBattery) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerBattery(int windowId, PlayerInventory playerInventory, TileEntityBattery owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		addPlayerInventory(getPlayerInventory(), 8, 84);
		addPlayerHotbar(getPlayerInventory(), 8, 142);
	}
}
