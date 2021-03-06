package theking530.staticpower.tileentities.powered.fluidgenerator;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.StaticPowerTileEntityContainer;

public class ContainerFluidGenerator extends StaticPowerTileEntityContainer<TileEntityFluidGenerator> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerFluidGenerator, GuiFluidGenerator> TYPE = new ContainerTypeAllocator<>("machine_fluid_generator", ContainerFluidGenerator::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiFluidGenerator::new);
		}
	}

	public ContainerFluidGenerator(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityFluidGenerator) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerFluidGenerator(int windowId, PlayerInventory playerInventory, TileEntityFluidGenerator owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		this.addPlayerInventory(getPlayerInventory(), 8, 84);
		this.addPlayerHotbar(getPlayerInventory(), 8, 142);
	}
}
