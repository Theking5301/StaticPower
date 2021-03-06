package theking530.staticpower.tileentities.powered.mixer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.StaticPowerTileEntityContainer;
import theking530.staticpower.container.slots.BatteryItemSlot;
import theking530.staticpower.container.slots.StaticPowerContainerSlot;

public class ContainerMixer extends StaticPowerTileEntityContainer<TileEntityMixer> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerMixer, GuiMixer> TYPE = new ContainerTypeAllocator<>("machine_mixer", ContainerMixer::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiMixer::new);
		}
	}

	public ContainerMixer(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityMixer) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerMixer(int windowId, PlayerInventory playerInventory, TileEntityMixer owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		// Input
		addSlot(new StaticPowerContainerSlot(getTileEntity().input1Inventory, 0, 60, 22));
		addSlot(new StaticPowerContainerSlot(getTileEntity().input2Inventory, 0, 60, 60));

		// Battery
		addSlot(new BatteryItemSlot(getTileEntity().batteryInventory, 0, 8, 64));

		addPlayerInventory(getPlayerInventory(), 8, 84);
		addPlayerHotbar(getPlayerInventory(), 8, 142);
	}
}
