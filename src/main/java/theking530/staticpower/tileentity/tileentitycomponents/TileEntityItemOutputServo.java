package theking530.staticpower.tileentity.tileentitycomponents;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.tileentity.BlockMachineBase;
import theking530.staticpower.tileentity.ISideConfigurable;
import theking530.staticpower.tileentity.SideModeList.Mode;
import theking530.staticpower.tileentity.SideUtilities;
import theking530.staticpower.tileentity.SideUtilities.BlockSide;
import theking530.staticpower.tileentity.TileEntityInventory;
import theking530.staticpower.utilities.InventoryUtilities;

public class TileEntityItemOutputServo implements ITileEntityComponent {

	private Random randomGenerator = new Random();

	private int outputTimer;
	private int outputTime;
	private ISideConfigurable sideConfigurable;
	private TileEntity tileEntity;
	private TileEntityInventory inventory;
	private int[] slots;
	private boolean isEnabled;
	private Mode outputMode;

	public TileEntityItemOutputServo(TileEntity tileEntity, int outputTime, TileEntityInventory inventory, Mode mode, int... slots) {
		this.tileEntity = tileEntity;
		this.outputTime = outputTime;
		this.inventory = inventory;
		this.slots = slots;
		this.outputMode = mode;

		if (tileEntity instanceof ISideConfigurable) {
			sideConfigurable = (ISideConfigurable) tileEntity;
		}
	}

	public TileEntityItemOutputServo(TileEntity tileEntity, int outputTime, TileEntityInventory inventory, int... slots) {
		this(tileEntity, outputTime, inventory, Mode.Output, slots);
	}

	@Override
	public String getComponentName() {
		return "Output Servo";
	}

	@Override
	public void preProcessUpdate() {
		if (!tileEntity.getWorld().isRemote) {
			if (inventory != null && inventory.getSlots() > 0 && tileEntity != null) {
				int randomIndex = randomGenerator.nextInt(slots.length);
				int slot = slots[randomIndex];
				if (inventory.getStackInSlot(slot) != ItemStack.EMPTY) {
					outputTimer++;
					if (outputTimer > 0) {
						int rand = randomGenerator.nextInt(5);
						if (outputTimer >= outputTime) {
							switch (rand) {
							case 0:
								outputItem(slot, BlockSide.TOP, 0, false);
								break;
							case 1:
								outputItem(slot, BlockSide.BOTTOM, 0, false);
								break;
							case 2:
								outputItem(slot, BlockSide.RIGHT, 0, false);
								break;
							case 3:
								outputItem(slot, BlockSide.LEFT, 0, false);
								break;
							case 4:
								outputItem(slot, BlockSide.BACK, 0, false);
								break;
							default:
								break;
							}
							outputTimer = 0;
						}
					}
				}
			}
		}
	}

	public void outputItem(int fromSlot, BlockSide blockSide, int startSlot, boolean backwards) {
		if (sideConfigurable == null || sideConfigurable.getSideConfiguration(blockSide) == outputMode) {
			ItemStack stack = inventory.getStackInSlot(fromSlot);
			if (!stack.isEmpty()) {
				Direction facing = tileEntity.getWorld().getBlockState(tileEntity.getPos()).get(BlockMachineBase.FACING);
				TileEntity te = tileEntity.getWorld().getTileEntity(tileEntity.getPos().offset(SideUtilities.getDirectionFromSide(blockSide, facing)));
				if (te != null) {
					te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, SideUtilities.getDirectionFromSide(blockSide, facing).getOpposite()).ifPresent((IItemHandler instance) -> {
						if (te instanceof ISideConfigurable) {
							ISideConfigurable configurable = (ISideConfigurable) te;
							if (configurable.isSideConfigurable()) {
								if (configurable.getSideConfiguration(blockSide.getOpposite()).isOutputMode()) {
									return;
								}
							}
						}
						inventory.setStackInSlot(fromSlot, InventoryUtilities.insertItemIntoInventory(instance, stack));
					});
				}
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public void postProcessUpdate() {
		// TODO Auto-generated method stub

	}
}
