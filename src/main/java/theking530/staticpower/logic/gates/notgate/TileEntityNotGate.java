package theking530.staticpower.logic.gates.notgate;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityNotGate extends TileEntityBaseLogicGate {
	
	public boolean isOn(){
		return getInputSignal(getInputSide()) == 0;
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(getInputSignal(getInputSide()) == 0) {
				setAllOutputs(15);
			}else{
				reset();
				setAllOutputs(0);
			}
			updateGate();
		}
	}	
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
		return nbt;
	}	
}
