package theking530.staticpower.conduits.itemconduit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.conduits.ConduitPath;
import theking530.staticpower.conduits.TileEntityBaseConduit;

public class TileEntityItemConduit extends TileEntityBaseConduit {
	
	public ItemConduitWrapper SLOT;
	public int PULL_RATE = 20;
	public int PULL_TIMER = 0;
	
	public int PULL_ITEMS = 16;
	
	public int MOVE_RATE = 10;
	public int MOVE_TIMER = 0;
	
	
	public Random RANDOM;
	
	
	public ItemStack PREVIEW_STACK;
	public EnumFacing PREVIEW_DIRECTION;
	public boolean IS_MOVING;
	public float RANDOM_ROTATION;
	
	public TileEntityItemConduit() {	
		RANDOM = new Random();
		SLOT = null;
	}
	@Override
	public void update() {
		super.update();
		if(IS_MOVING && MOVE_TIMER < MOVE_RATE) {
			MOVE_TIMER++;
		}
		if(!getWorld().isRemote) {
			if(SLOT == null) {
				if(PULL_TIMER < PULL_RATE) {
					PULL_TIMER++;
					return;
				}
				PULL_TIMER = 0;
				setSlot(attemptPullItem());
				if(SLOT != null) {
					ConduitPath path = getClosestPath(SLOT);
					if(path != null && path.size() > 2) {
						SLOT.setPath(path);
						SLOT.incrementPath();
						MOVE_TIMER = 0;
						IS_MOVING = true;
						RANDOM_ROTATION = SLOT.getRotationOffset();
						world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					}
				}
			}else{
				if(!SLOT.hasPath() || !canPlaceInInventory(SLOT.getPath().getDestination(), SLOT.getPath().getPenultimateFacing()) || SLOT.getNextBlockPos() == null) {
					ConduitPath path = getClosestPath(SLOT);
					if(path != null) {
						SLOT.setPath(path);
						incrementSlotPath();
					}else{
						path = GRID.gatherPath(getPos(), SLOT.getSourceLocation());
						if(path != null) {
							SLOT.setPath(path);
							incrementSlotPath();
							IS_MOVING = true;
							world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
						}
					}
				}else{
					if(MOVE_TIMER < MOVE_RATE) {
						IS_MOVING = true;
						world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					}else{
						if(!moveContentsForward()) {
							SLOT.setPath(null);
						}
						MOVE_TIMER = 0;
						IS_MOVING = false;
						world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					}
				}						
			}
		}
	}	
	public void setSlot(ItemConduitWrapper newSlot) {
		SLOT = newSlot;
		if(SLOT != null) {
			PREVIEW_STACK = SLOT.getItemStack();	
		}
	}
	public void clearSlot() {
		SLOT = null;
		PREVIEW_STACK = null;
	}
	public void incrementSlotPath() {
		SLOT.incrementPath();
		EnumFacing dir = SLOT.getCurrentDirection();
		if(dir != null) {
			PREVIEW_DIRECTION = SLOT.getCurrentDirection();	
		}
	}
	
	//Functionality
	public void acceptNewItem(ItemConduitWrapper newItem) {
		newItem.incrementPath();
		MOVE_TIMER = 0;
		SLOT = newItem;
	}
	public boolean moveContentsForward() {
		if(SLOT != null) {
			BlockPos nextConduit = SLOT.getNextBlockPos();
			TileEntity te = getWorld().getTileEntity(nextConduit);
			if(te != null) {
				if(te instanceof TileEntityItemConduit) {
					TileEntityItemConduit cond = (TileEntityItemConduit)te;
					if(cond.SIDE_MODES[SLOT.getCurrentDirection().ordinal()] == 0) {
						if(cond.SLOT == null) {
							cond.acceptNewItem(SLOT);
							clearSlot();
							return true;
						}
					}
				}else{
					if(canPlaceInInventory(nextConduit, SLOT.getPath().getPenultimateFacing())) {
						placeInInventory(nextConduit, SLOT.getPath().getPenultimateFacing());
						clearSlot();
						return true;
					}
				}
			}
		}
		return false;
	}
	public ConduitPath getClosestPath(ItemConduitWrapper slot) {	
	    //Generate a list of items already in transport
		Map<BlockPos, List<ItemConduitRecieverWrapper>> mappedPaths = new HashMap<BlockPos, List<ItemConduitRecieverWrapper>>();	
	    Iterator<Entry<BlockPos, TileEntityBaseConduit>> pathsIt = GRID.GRID_MAP.entrySet().iterator();
	    while (pathsIt.hasNext()) {
	        Map.Entry<BlockPos, TileEntityBaseConduit> pair = (Map.Entry<BlockPos, TileEntityBaseConduit>)pathsIt.next();
	        if(pair.getValue() != null && pair.getValue() instanceof TileEntityItemConduit) {
	        	TileEntityItemConduit cond = (TileEntityItemConduit)pair.getValue();
	        	if(cond.SLOT != null && cond.SLOT != slot && cond.SLOT.hasPath()) {
	        		if(!mappedPaths.containsKey(cond.getPos())) {
	        			mappedPaths.put(cond.getPos(), new ArrayList<ItemConduitRecieverWrapper>());
	        		}
	        		mappedPaths.get(cond.getPos()).add(new ItemConduitRecieverWrapper(cond.getPos(), cond.SLOT.getPath().getPenultimateFacing(), cond.SLOT.getItemStack()));
	        	}
	        }	        
	    }   
	   
	    //Generate a list of potential receivers
		Map<BlockPos, ConduitPath> potentialRecievers = new HashMap<BlockPos, ConduitPath>();	
	    Iterator<Entry<BlockPos, TileEntity>> it = GRID.RECIEVER_STORAGE_MAP.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<BlockPos, TileEntity> pair = (Map.Entry<BlockPos, TileEntity>)it.next();
	        TileEntity te = getWorld().getTileEntity(pair.getKey());
	        ConduitPath path = GRID.gatherPath(getPos(), pair.getKey());
	        if(path.size() > 1) {
		    	EnumFacing facing = path.getPenultimateFacing();
	        	BlockPos finalPos = path.getDestination();
	        	
	        	if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing) && !pair.getKey().equals(slot.getSourceLocation())) {
					IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);			
		        	List<ItemStack> items = new ArrayList<ItemStack>();

		    	    Iterator<Entry<BlockPos, List<ItemConduitRecieverWrapper>>> itemGatherIt = mappedPaths.entrySet().iterator();
		    	    while (itemGatherIt.hasNext()) {
		    	        Map.Entry<BlockPos, List<ItemConduitRecieverWrapper>> itemGatherPair = (Map.Entry<BlockPos, List<ItemConduitRecieverWrapper>>)itemGatherIt.next();  	        
		    	        for(int i=0; i<itemGatherPair.getValue().size(); i++) {
		    	        	ItemConduitRecieverWrapper wrapper = itemGatherPair.getValue().get(i);
		    	        	if(wrapper.getReciever() == finalPos && wrapper.getInsertFacing() == facing) {
		    	        		items.add(wrapper.getItemStack());
		    	        	}
		    	        }
		    	    } 		
		    	    
	      		    items.add(slot.getItemStack());	      		
					if(InventoryUtilities.canInsertItemsIntoInventory(itemHandler, items)) {
			        	potentialRecievers.put(pair.getKey(), path);
					}
				} 
	        }
	    }    
	    
	    //Attempt to satisfy all receivers.
    	Map.Entry<BlockPos, ConduitPath>  shortestPath = null;
    	int shortestPathLength = 100000000;
    	
    	//Determine closets receiver and store it in 'shortestPath'.
	    Iterator<Entry<BlockPos, ConduitPath>> testIt = potentialRecievers.entrySet().iterator();
	    while (testIt.hasNext()) {
	        Map.Entry<BlockPos, ConduitPath> pair = (Map.Entry<BlockPos, ConduitPath>)testIt.next();

	        //Confirm that this path is the shortest and that is is possible to insert given all the items already in transport.
	        if(pair.getValue().size() < shortestPathLength && !pair.getValue().get(pair.getValue().size()-1).equals(slot.getSourceLocation())) {	        	shortestPathLength = pair.getValue().size();	        	shortestPath = pair;  
	        }
	    }   
	    
	    if(shortestPath == null) {
	    	return null;
	    }
	    //Satisfy towards reciever.
	    return shortestPath.getValue();		    
	}
	public ItemConduitWrapper attemptPullItem() {
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == 1) {
				EnumFacing facing = EnumFacing.values()[i];
				TileEntity te = getWorld().getTileEntity(pos.offset(facing));
				if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
					IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
					for(int j=0; j<itemHandler.getSlots(); j++) {
						if(!itemHandler.getStackInSlot(j).isEmpty()) {
							return new ItemConduitWrapper(itemHandler.extractItem(j, PULL_ITEMS, false), getPos().offset(facing), RANDOM.nextFloat()*20);
						}
					}
				}
			}
		}
		return null;
	}
	public boolean canPlaceInInventory(BlockPos inventory, EnumFacing facing) {
		TileEntity te = getWorld().getTileEntity(inventory);
		if(te == null) {
			return false;
		}
		if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
			IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
			if(InventoryUtilities.canFullyInsertItemIntoInventory(itemHandler, SLOT.getItemStack())) {
				return true;
			}
		}
		return false;
	}
	public boolean placeInInventory(BlockPos inventory, EnumFacing facing) {
		TileEntity te = getWorld().getTileEntity(inventory);
		if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
			IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
			if(InventoryUtilities.canFullyInsertItemIntoInventory(itemHandler, SLOT.getItemStack())) {
				InventoryUtilities.insertItemIntoInventory(itemHandler, SLOT.getItemStack());
				clearSlot();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isConduit(EnumFacing side) {
		if(SIDE_MODES[side.ordinal()] == 2) {
			return false;
		}
		if(getWorld().getTileEntity(pos.offset(side)) instanceof TileEntityItemConduit) {
			return ((TileEntityItemConduit)getWorld().getTileEntity(pos.offset(side))).SIDE_MODES[side.getOpposite().ordinal()] != 1;
		}
		return false;
	}		
	@Override
	public boolean isReciever(EnumFacing side) {
		if(SIDE_MODES[side.ordinal()] == 2) {
			return false;
		}
		if(getWorld().getTileEntity(pos.offset(side)) != null && !(getWorld().getTileEntity(pos.offset(side)) instanceof TileEntityItemConduit) && getWorld().getTileEntity(pos.offset(side)).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())) {
			return true;
		}
		return false;		
	}
	public void conduitWrenched(EnumFacing side, int subHit) {	
		EnumFacing adjustedSide = subHit == 0 ? side : EnumFacing.values()[subHit-1];	

		if(receivers[adjustedSide.getOpposite().ordinal()] == adjustedSide) {
			SIDE_MODES[adjustedSide.ordinal()] = SIDE_MODES[adjustedSide.ordinal()] + 1;
			if(SIDE_MODES[adjustedSide.ordinal()] > 2) {
				SIDE_MODES[adjustedSide.ordinal()] = 0;
			}
		}else{
			SIDE_MODES[adjustedSide.ordinal()] = SIDE_MODES[adjustedSide.ordinal()] == 2 ? 0 : 2;
		}
		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
		
		if(SLOT != null && SLOT.hasPath() && SLOT.getCurrentDirection() == side) {
			EntityItem droppedItem = new EntityItem(world, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, SLOT.getItemStack());
			world.spawnEntity(droppedItem);
			SLOT = null;
		}
	}	
	
	//NBT and Sync
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		MOVE_TIMER = nbt.getInteger("MOVE_TIMER");
		IS_MOVING = nbt.getBoolean("MOVING");
		if(nbt.hasKey("ITEM")) {
			PREVIEW_STACK = new ItemStack(nbt.getCompoundTag("ITEM"));
		}
		if(nbt.hasKey("DIR")) {
			PREVIEW_DIRECTION = EnumFacing.values()[nbt.getInteger("DIR")];
		}
		RANDOM_ROTATION = nbt.getFloat("RAND");
	}		
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("MOVE_TIMER", MOVE_TIMER);
		nbt.setBoolean("MOVING", MOVE_TIMER > 0);

		if(SLOT != null) {
			nbt.setTag("ITEM", SLOT.getItemStack().serializeNBT());
			nbt.setInteger("DIR", SLOT.getCurrentDirection().ordinal());
			nbt.setFloat("RAND",  SLOT.getRotationOffset());
		}
		return nbt;
	}
}
