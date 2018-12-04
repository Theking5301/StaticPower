package theking530.staticpower.logic.gates.or;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityOrGate extends TileEntityBaseLogicGate {
	
	public boolean isOn(){
		return addAllInputSignals() > 0;
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(getPoweredInputSignalCount() > 0) {
				setAllOutputs(15);		
				setAllExtraOutputs(0);	
			}
			if(getPoweredInputSignalCount() == 1) {
				setAllExtraOutputs(15);		
			}
			if(getPoweredInputSignalCount() == 0) {
				setAllExtraOutputs(0);	
				setAllOutputs(0);	
			}
			updateGate();
		}
	}	
	public int maxInputs(){
		return 3;
	}
	public int maxExtra(){
		return 3;
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
	public Mode[] getInitialModes(){
		return new Mode[]{Mode.Disabled, Mode.Disabled, Mode.Input, Mode.Output, Mode.Input, Mode.Output};
	}
}
