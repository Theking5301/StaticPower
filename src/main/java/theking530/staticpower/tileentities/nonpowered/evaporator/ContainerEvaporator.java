package theking530.staticpower.tileentities.nonpowered.evaporator;

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
import theking530.staticpower.container.slots.UpgradeItemSlot;
import theking530.staticpower.items.upgrades.BaseUpgrade;
import theking530.staticpower.items.utilities.EnergyHandlerItemStackUtilities;

public class ContainerEvaporator extends StaticPowerTileEntityContainer<TileEntityEvaporator> {
	@ContainerTypePopulator
	public static final ContainerTypeAllocator<ContainerEvaporator, GuiEvaporator> TYPE = new ContainerTypeAllocator<>("machine_evaporator", ContainerEvaporator::new);
	static {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			TYPE.setScreenFactory(GuiEvaporator::new);
		}
	}

	public ContainerEvaporator(int windowId, PlayerInventory inv, PacketBuffer data) {
		this(windowId, inv, (TileEntityEvaporator) resolveTileEntityFromDataPacket(inv, data));
	}

	public ContainerEvaporator(int windowId, PlayerInventory playerInventory, TileEntityEvaporator owner) {
		super(TYPE, windowId, playerInventory, owner);
	}

	@Override
	public void initializeContainer() {
		// Upgrades
		addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 0, 152, 17));
		addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 1, 152, 37));
		addSlot(new UpgradeItemSlot(getTileEntity().upgradesInventory, 2, 152, 57));

		addPlayerInventory(getPlayerInventory(), 8, 84);
		addPlayerHotbar(getPlayerInventory(), 8, 142);
	}

	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, PlayerEntity player, Slot slot, int slotIndex) {
		if (EnergyHandlerItemStackUtilities.isEnergyContainer(stack) && !mergeItemStack(stack, 5, 6, false)) {
			return true;
		}
		if (stack.getItem() instanceof BaseUpgrade && !mergeItemStack(stack, 2, 5, false)) {
			return true;
		}
		return false;
	}
}
