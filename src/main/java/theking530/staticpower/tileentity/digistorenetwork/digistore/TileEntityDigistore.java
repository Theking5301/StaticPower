package theking530.staticpower.tileentity.digistorenetwork.digistore;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.items.upgrades.BaseDigistoreCapacityUpgrade;
import theking530.staticpower.items.upgrades.DigistoreMiscUpgrades;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreTileEntity;

public class TileEntityDigistore extends BaseDigistoreTileEntity {
	public static final int DEFAULT_CAPACITY = 1024;
	
	private int storedAmount;
	private int maxStoredItems;
	private ItemStack storedItem;
	private boolean shouldVoid;
	private boolean locked;
	
	public TileEntityDigistore() {
		initializeSlots(0, 0, 1);
		storedItem = ItemStack.EMPTY;
		maxStoredItems = DEFAULT_CAPACITY;
		shouldVoid = false;
	}
	@Override
	public void process() {
		super.process();
		handleStorageUpgrades();
		handleMiscUpgradtes();
	}
	public void onBarrelRightClicked(EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!getWorld().isRemote) {
			if(canAcceptUpgrade(player.getHeldItem(EnumHand.MAIN_HAND))) {
				for(int i=0; i<slotsUpgrades.getSlots(); i++) {
					ItemStack remaining = slotsUpgrades.insertItem(i, player.getHeldItem(hand).copy(), false);
					player.getHeldItem(hand).setCount(remaining.getCount());
					if(remaining.getCount() <= 0) {
						world.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.15F, (float) (0.5F + Math.random()*2.0));
						break;
					}
				}
			}	
			
			if(player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() || !ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(player.getHeldItem(EnumHand.MAIN_HAND), 1), storedItem)) {
				if(!storedItem.isEmpty()) {
					for(int i=0; i<player.inventory.getSizeInventory(); i++) {
						if(maxStoredItems - storedAmount <= 0 && !shouldVoid) {
							break;
						}
						if(player.inventory.getStackInSlot(i).isEmpty()) {
							continue;
						}
						ItemStack insertedItemstack = pushItem(player.inventory.getStackInSlot(i), false);
						player.inventory.getStackInSlot(i).setCount(insertedItemstack.getCount());
					}
				}
			}
			ItemStack insertedItemstack = pushItem(player.getHeldItem(EnumHand.MAIN_HAND), false);
			player.getHeldItem(EnumHand.MAIN_HAND).setCount(insertedItemstack.getCount());
		}
		updateBlock();
	}
    public void onBarrelLeftClicked(EntityPlayer playerIn) {
    	if(!getWorld().isRemote && !storedItem.isEmpty() && storedAmount > 0) {
    		int requestedDrop = playerIn.isSneaking() ? 1 : storedAmount;
    		int maxDropAmount = Math.min(storedItem.getMaxStackSize(), requestedDrop);
			WorldUtilities.dropItem(getWorld(), getFacingDirection(), getPos().offset(getFacingDirection()), pullItem(maxDropAmount, false));
    	}
		updateBlock();
    }
    
    /**
     * @param item - Item to Insert
     * @return Returns remaining itemstack after insert.
     */
    public ItemStack pushItem(ItemStack item, boolean simulate) {
    	if(storedItem.isEmpty()) {
    		if(!simulate) {
    			initializeNewItem(item.copy(), item.getCount());
    			slotsOutput.setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(storedItem, storedAmount));	
    		}
			return ItemStack.EMPTY;
		}else{
			if(ItemHandlerHelper.canItemStacksStack(item, storedItem)) {
				int maxTake = getRemainingStorage(true);
				int playerItemAmount = item.getCount();
				int remaining = Math.max(playerItemAmount-maxTake, 0);
				if(!simulate) {
		    		storedAmount = Math.min(storedAmount + (playerItemAmount - remaining), maxStoredItems);
					slotsOutput.setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(storedItem, Math.min(storedItem.getMaxStackSize(), storedAmount)));
				}
				return ItemHandlerHelper.copyStackWithSize(item, remaining);
			}
		}
		return item;
    }
    public ItemStack pullItem(int amount, boolean simulate) {
    	if(storedAmount == 0) {
    		return ItemStack.EMPTY;
    	}else{
    		int extractAmount = Math.min(storedAmount, amount);
    		ItemStack wouldBeExtracted = ItemHandlerHelper.copyStackWithSize(storedItem, extractAmount); //Cached in case storedItem is set to null in the ClearBarrel call
    		if(!simulate) {
	    		storedAmount -= extractAmount;
	    		if(storedAmount == 0) {
	    			clearStorage();
	    		}
		    	updateBlock();
    		}
    		return wouldBeExtracted;
    	}	
    }
    private void initializeNewItem(ItemStack item, int amount) {
		storedItem = item;
		storedItem.setCount(1);
		storedAmount = amount;
    }
    private void clearStorage() {
    	storedAmount = 0;
    	if(!isLocked()) {
        	storedItem = ItemStack.EMPTY;
    		slotsOutput.setStackInSlot(0, ItemStack.EMPTY);
    	}
    }
    public ItemStack getStoredItem() {
    	return storedItem;
    }
    public int getStoredAmount() {
    	return storedAmount;
    }
    public int getMaxStoredAmount() {
    	return maxStoredItems;
    }
    public boolean isFull() {
		return getStoredAmount() >= getMaxStoredAmount();
    }
    public float getFilledRatio() {
    	return (float)getStoredAmount()/(float)getMaxStoredAmount();
    }
    public boolean isVoidUpgradeInstalled() {
    	return shouldVoid;
    } 
    private int getRemainingStorage(boolean checkForVoidUpgrade) {
    	return checkForVoidUpgrade ? shouldVoid ? maxStoredItems :  maxStoredItems - storedAmount :  maxStoredItems - storedAmount;
    }
    public boolean isLocked() {
    	return locked;
    }
    
    public void setLocked(boolean locked) {
    	this.locked = locked;
    	if(!locked && storedAmount <= 0) {
    		storedItem = ItemStack.EMPTY;
    	}
    }
    
    @Override
    public String getName() {
    	return "container.Digistore";
    }
    
	@Override  
	public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        storedAmount = nbt.getInteger("STORED_AMOUNT");
        storedItem = new ItemStack(nbt.getCompoundTag("STORED_ITEM"));
        shouldVoid = nbt.getBoolean("VOID");
        locked = nbt.getBoolean("LOCKED");
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
        nbt.setInteger("STORED_AMOUNT", storedAmount);
        nbt.setTag("STORED_ITEM", storedItem.serializeNBT());
        nbt.setBoolean("VOID", shouldVoid);
        nbt.setBoolean("LOCKED", locked);
		return nbt;	
	}

	@Override
	public boolean isSideConfigurable() {
		return false;
	}
    
    /*Upgrade Handling*/
    @Override
	public boolean canAcceptUpgrade(ItemStack upgrade) {	
    	if(!upgrade.isEmpty()) {
    		return upgrade.getItem() instanceof BaseDigistoreCapacityUpgrade || upgrade.getItem() instanceof DigistoreMiscUpgrades;
    	}
		return false;
	}
    private void handleMiscUpgradtes() {
    	shouldVoid = hasUpgrade(DigistoreMiscUpgrades.VoidUprgade);
    }
    private void handleStorageUpgrades() {
		int additionalAmount = 0;
		for(ItemStack upgrade : getAllUpgrades()) {
			if(!upgrade.isEmpty() && upgrade.getItem() instanceof BaseDigistoreCapacityUpgrade) {
				BaseDigistoreCapacityUpgrade itemUpgrade = (BaseDigistoreCapacityUpgrade)upgrade.getItem();
				additionalAmount += itemUpgrade.getUpgradeValueAtIndex(upgrade, 0); 
			}
		}
		maxStoredItems = Math.max(0, DEFAULT_CAPACITY + additionalAmount);
		
		if(!getWorld().isRemote) {
			if(storedAmount > maxStoredItems && !shouldVoid) {
				storedAmount = maxStoredItems; //Important
	        	while(storedAmount > maxStoredItems) {
	        		ItemStack droppedItem = getStoredItem().copy();
	        		int maxDrop = storedAmount - maxStoredItems;
	        		droppedItem.setCount(Math.min(maxDrop, droppedItem.getMaxStackSize()));
	        		storedAmount -= droppedItem.getCount();
					WorldUtilities.dropItem(getWorld(), pos.getX(), pos.getY(), pos.getZ(), droppedItem);
	        	}
				updateBlock();
			}
		}
    }

	/*Capability Handling*/
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new IItemHandler() {
				public int getSlots() {
			    	return evauluateRedstoneSettings() ? 1 : 0;
			    }
			    @Nonnull
			    public ItemStack getStackInSlot(int slot) {
			    	return ItemHandlerHelper.copyStackWithSize(storedItem, Math.min(storedItem.getMaxStackSize(), storedAmount));
			    }
			    @Nonnull
			    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			    	int maxInsertAmount = getRemainingStorage(true);
			    	
		            if (!storedItem.isEmpty() & !ItemHandlerHelper.canItemStacksStack(stack, storedItem)) {
			    		return stack;
			    	}  
			    	if(maxInsertAmount == 0) {
			    		return stack;
			    	}

		    		int originalAmount = stack.getCount();
		    		int insertAmount = Math.min(maxInsertAmount, originalAmount);
		    		if(!simulate) {
				    	if(storedItem.isEmpty()) {
				    		initializeNewItem(stack, insertAmount);
				    	}else{
				    		storedAmount = Math.min(storedAmount + insertAmount, maxStoredItems);
				    	}
				    	updateBlock();
		    		}
		    		return ItemHandlerHelper.copyStackWithSize(stack, originalAmount - insertAmount);
			    }
			    @Nonnull
			    public ItemStack extractItem(int slot, int amount, boolean simulate) {
			    	return pullItem(amount, simulate);	
			    }
			    public int getSlotLimit(int slot) {
			    	return maxStoredItems;
			    }
			});
    	}
    	return super.getCapability(capability, facing);
    }
}
