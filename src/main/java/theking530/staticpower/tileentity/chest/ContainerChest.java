package theking530.staticpower.tileentity.chest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class ContainerChest extends BaseContainer {
	
	private TileEntityBaseChest chest;

	public ContainerChest(Tier tier, InventoryPlayer invPlayer, TileEntityBaseChest teChest) {
		chest = teChest;
		chest.onOpened();
		
		if(tier == Tier.STATIC) {
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 9; x++) {
					this.addSlotToContainer(new StaticPowerContainerSlot(teChest.slotsOutput, x + y * 9, 8 + x * 18, 19 + y * 18));
				}
			}
			this.addPlayerInventory(invPlayer, 8, 123);
			this.addPlayerHotbar(invPlayer, 8, 181);
		}else if(tier == Tier.ENERGIZED) {
			int yOff = 48;	
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 9; x++) {
					this.addSlotToContainer(new StaticPowerContainerSlot(teChest.slotsOutput, x + y * 9, 8 +  (x * 18), (19 + (y * 18))));
				}
			}
			this.addPlayerInventory(invPlayer, 8, yOff+126);
			this.addPlayerHotbar(invPlayer, 8, yOff+184);
		}else {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 12; x++) {
					this.addSlotToContainer(new StaticPowerContainerSlot(teChest.slotsOutput, x + y * 12, 8 + (x * 18), (19 + (y * 18))));
				}
			}
			this.addPlayerInventory(invPlayer, 35, 172);
			this.addPlayerHotbar(invPlayer, 35, 230);
		}		
	}
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (!mergeItemStack(stack, 0, this.playerInventoryStart, false)) {
        	return true;
        }
		return false;	
	}
    public void onContainerClosed(EntityPlayer playerIn) {
    	chest.onClosed();
    	super.onContainerClosed(playerIn);
    }
}
