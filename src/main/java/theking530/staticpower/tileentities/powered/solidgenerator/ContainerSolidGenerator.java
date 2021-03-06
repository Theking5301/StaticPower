package theking530.staticpower.tileentities.powered.solidgenerator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticpower.container.StaticPowerTileEntityContainer;
import theking530.staticpower.container.slots.StaticPowerContainerSlot;
import theking530.staticpower.container.slots.UpgradeItemSlot;

public class ContainerSolidGenerator extends StaticPowerTileEntityContainer<TileEntitySolidGenerator> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerSolidGenerator, GuiSolidGenerator> TYPE = new ContainerTypeAllocator<>("machine_solid_generator", ContainerSolidGenerator::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiSolidGenerator::new);
		}
	}

	public ContainerSolidGenerator(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntitySolidGenerator) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerSolidGenerator(int windowId, PlayerInventory playerInventory, TileEntitySolidGenerator owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		// Input
		this.addSlot(new StaticPowerContainerSlot(getTileEntity().inputInventory, 0, 80, 32));

		// Upgrades
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 0, 152, 16));
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 1, 152, 36));
		this.addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 2, 152, 56));

		this.addPlayerInventory(getPlayerInventory(), 8, 84);
		this.addPlayerHotbar(getPlayerInventory(), 8, 142);
	}

	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, PlayerEntity player, Slot slot, int slotIndex) {
		if (!mergeItemStack(stack, 0, 1, false)) {
			return true;
		}
		return false;
	}
}
