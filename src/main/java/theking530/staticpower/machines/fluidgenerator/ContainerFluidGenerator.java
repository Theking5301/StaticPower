package theking530.staticpower.machines.fluidgenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFluidGenerator extends Container {
	
	private TileEntityFluidGenerator F_GENERATOR;
	private int ENERGY_STORED;
	
	public ContainerFluidGenerator(InventoryPlayer invPlayer, TileEntityFluidGenerator teFluidGenerator) {
		ENERGY_STORED = 0;
		F_GENERATOR = teFluidGenerator;
		
		//Fluid Slots
		this.addSlotToContainer(new SlotItemHandler(teFluidGenerator.SLOTS_INPUT, 0, 7, 17));
		this.addSlotToContainer(new SlotItemHandler(teFluidGenerator.SLOTS_OUTPUT, 0, 7, 47));
		
		//Upgrades
		this.addSlotToContainer(new SlotItemHandler(teFluidGenerator.SLOTS_UPGRADES, 0, 171, 12));
		this.addSlotToContainer(new SlotItemHandler(teFluidGenerator.SLOTS_UPGRADES, 1, 171, 32));
		this.addSlotToContainer(new SlotItemHandler(teFluidGenerator.SLOTS_UPGRADES, 2, 171, 52));
		
		//Inventory
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 9; j++) {
						this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 27 + j * 18, 84 + i * 18));
					}
				}
				
				//ActionBar
				for(int i = 0; i < 9; i++) {
					this.addSlotToContainer(new Slot(invPlayer, i, 27 + i * 18, 142));
			}
	}
	
	//Shift Click Functionality
	public ItemStack transferStackInSlot(EntityPlayer player, int p_82846_2_)
	    {
	        ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (p_82846_2_ == 2)
	            {
	                if (!this.mergeItemStack(itemstack1, 3, 39, true))
	                {
	                    return null;
	                }

	                slot.onSlotChange(itemstack1, itemstack);
	            }
	            else if (p_82846_2_ != 1 && p_82846_2_ != 0)
	            {
	                if (itemstack1.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN))
	                {
	                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (TileEntityFurnace.isItemFuel(itemstack1))
	                {
	                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (p_82846_2_ >= 3 && p_82846_2_ < 30)
	                {
	                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(player, itemstack1);
	        }

	        return itemstack;
	    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return F_GENERATOR.isUseableByPlayer(player);
	}
	public void detectAndSendChanges(){
        super.detectAndSendChanges();
       // F_GENERATOR.sync();
    }
}

