package theking530.staticpower.conduits.itemconduit;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.conduits.ConduitPath;

public class ItemConduitWrapper {
	private ItemStack ITEM;
	private BlockPos SOURCE;
	private ConduitPath PATH;
	private float RANDOM_ROTATION;
	private int CURRENT_INDEX;
	
	private int MOVE_TIMER;
	
	public ItemConduitWrapper(ItemStack item, BlockPos source, float randomRotation) {
		ITEM = item;
		SOURCE = source;
		CURRENT_INDEX = 0;
		RANDOM_ROTATION = randomRotation;
		MOVE_TIMER = 0;
	}
	public void setPath(ConduitPath path) {
		PATH = path;
		CURRENT_INDEX=0;
	}
	public void incrementPath() {
		CURRENT_INDEX++;
	}
	public BlockPos getNextBlockPos() {
		if(PATH == null || CURRENT_INDEX >= PATH.size()) {
			return null;
		}
		return PATH.get(CURRENT_INDEX);
	}
	public BlockPos getCurrentBlockPos() {
		if(PATH == null || CURRENT_INDEX >= PATH.size()) {
			return null;
		}
		return CURRENT_INDEX == 0 ? PATH.get(CURRENT_INDEX) : PATH.get(CURRENT_INDEX-1);
	}
	public boolean hasPath() {
		if(PATH == null) {
			return false;
		}else{
			return PATH.size() > 2;
		}
	}
	public ConduitPath getPath() {
		return PATH;
	}
	public BlockPos getSourceLocation() {
		return SOURCE;
	}
	public ItemStack getItemStack() {
		return ITEM;
	}
	public EnumFacing getCurrentDirection() {
		return WorldUtilities.getFacingFromPos(getCurrentBlockPos(), getNextBlockPos());
	}	
	public float getRotationOffset() {
		return RANDOM_ROTATION;
	}
	public int getMoveTimer() {
		return MOVE_TIMER;
	}
	public void incrementMoveTimer() {
		MOVE_TIMER++;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
	}		
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		return nbt;
	}
}
