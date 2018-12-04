package theking530.staticpower.logic.gates.timer;

import net.minecraft.nbt.NBTTagCompound;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityTimer extends TileEntityBaseLogicGate {
	
	public int TIMER = 0;
	public int SPEED = 20;
	
	@Override
	public boolean isOn(){
		return TIMER >= SPEED;		
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(TIMER < SPEED) {
				TIMER++;
				//reset();
				setAllOutputs(0);
				updateGate();
			}else{
				setAllOutputs(15);
				TIMER = 0;
				updateGate();
			}
		}
	}	
	public int maxInputs(){
		return 0;
	}
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        TIMER = nbt.getInteger("TIMER");
        SPEED = nbt.getInteger("SPEED");
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("TIMER", TIMER);
        nbt.setInteger("SPEED", SPEED);
		return nbt;
	}	
	public String getName() {
		return "container.Timer";		
	}
	public Mode[] getInitialModes(){
		return new Mode[]{Mode.Disabled, Mode.Disabled, Mode.Output, Mode.Output, Mode.Output, Mode.Output};
	}
}
