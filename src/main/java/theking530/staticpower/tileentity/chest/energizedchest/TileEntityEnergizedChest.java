package theking530.staticpower.tileentity.chest.energizedchest;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.tileentity.chest.TileEntityBaseChest;

public class TileEntityEnergizedChest extends TileEntityBaseChest{
	
	public TileEntityEnergizedChest() {
		initializeSlots(0, 0, 72);
	}
	
	//NBT
	@Override
    public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
        return nbt;
	}	  	    

    public boolean receiveClientEvent(int i, int j) {
        if (i == 1) {
            this.numPlayersUsing = j;
            return true;
        }else{
            return super.receiveClientEvent(i, j);
        }
    }
    //IInventory
	@Override
	public String getName() {
		return "container.EnergizedChest";
		
	}					

}
