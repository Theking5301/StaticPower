package theking530.staticpower.logic.gates.transducer;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntitySignalMultiplier extends TileEntityBaseLogicGate {

	public int INPUT_SIGNAL_LIMIT;
	public int OUTPUT_SIGNAL_STRENGTH;
	public boolean IS_RESET = true;
	
	public boolean conditionsMet() {
		return INPUT_SIGNAL_LIMIT == getInputSignal(getInputSide());
	}
	public boolean isOn(){
		return INPUT_SIGNAL_LIMIT == getInputSignal(getInputSide());
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(conditionsMet()) {
				setAllOutputs(OUTPUT_SIGNAL_STRENGTH);	
				IS_RESET = false;
			}else{
				if(!IS_RESET) {
					reset();	
					IS_RESET = true;
				}
			}	
		}
	}	
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
		OUTPUT_SIGNAL_STRENGTH = nbt.getInteger("OUTPUT_SIGNAL_STRENGTH");
		INPUT_SIGNAL_LIMIT = nbt.getInteger("INPUT_SIGNAL_LIMIT");
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
		nbt.setInteger("OUTPUT_SIGNAL_STRENGTH", OUTPUT_SIGNAL_STRENGTH);
		nbt.setInteger("INPUT_SIGNAL_LIMIT", INPUT_SIGNAL_LIMIT);
		return nbt;
	}	
	public String getName() {
		return "container.SignalMultiplier";		
	}
}
