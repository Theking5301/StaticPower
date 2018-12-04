package theking530.staticpower.logic.gates.subtractor;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntitySubtractorGate extends TileEntityBaseLogicGate {
	
	public boolean isOn(){
		return addAllInputSignals() > 0;
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(getInputCount() == 1 && getExtraCount() == 1) {
				int diff = addAllInputSignals() - addAllExtraSignals();
				setAllOutputs(Math.max(diff, 0));
			}
			updateGate();
		}
	}	
	public int maxInputs(){
		return 1;
	}
	public int maxExtra(){
		return 1;
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
		return new Mode[]{Mode.Disabled, Mode.Disabled, Mode.Input, Mode.Output, Mode.Regular, Mode.Output};
	}
}
