package theking530.staticpower.logic.gates.powercell;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityPowerCell extends TileEntityBaseLogicGate {
	
	public int POWER = 0;
	
	public boolean isOn(){
		return POWER > 0;
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(addAllInputSignals() <= 0) {
				setAllOutputs(POWER);		
			}
		}
	}	
	public int maxInputs(){
		return 3;
	}
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        POWER = nbt.getInteger("POWER");
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("POWER", POWER);
		return nbt;
	}	
	public String getName() {
		return "container.PowerCell";		
	}
	@Override
	public Mode[] getInitialModes(){
		return new Mode[]{Mode.Disabled, Mode.Disabled, Mode.Output, Mode.Output, Mode.Output, Mode.Output};
	}
}
