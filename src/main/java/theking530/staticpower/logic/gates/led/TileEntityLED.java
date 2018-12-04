package theking530.staticpower.logic.gates.led;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import theking530.staticpower.assists.utilities.Color;
import theking530.staticpower.assists.utilities.StaticMath;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.logic.gates.TileEntityBaseLogicGate;

public class TileEntityLED extends TileEntityBaseLogicGate {
	
	public int LIGHT_LEVEL = 0;
	public Color COLOR = Color.BLACK;
	public boolean INVERTED = true;
	
	public boolean isOn(){
		return addAllInputSignals() > 0;
	}
	@Override
	public void gateTick() {
		if(!getWorld().isRemote) {
			if(INVERTED) {
				LIGHT_LEVEL = Math.max(0, 15-addAllInputSignals());
			}else{
				LIGHT_LEVEL = Math.min(15, addAllInputSignals());			
			}
			updateGate();
		}
	}	
	public void setColor(Color color) {
		if(!getWorld().isRemote) {
			COLOR = Color.values()[StaticMath.clamp(color.ordinal(), 15, 0)];
		}
	}
	public void cycleColor(boolean reverse) {
		if(!getWorld().isRemote) {
			if(!reverse) {
				if(COLOR.ordinal() + 1 < 15) {
					COLOR = Color.values()[COLOR.ordinal() + 1];
				}else{
					COLOR = Color.BLACK;
				}
			}else{
				if(COLOR.ordinal() - 1 < 0) {
					COLOR = Color.values()[COLOR.ordinal() - 1];
				}else{
					COLOR = Color.BLACK;
				}
			}
		}
	}
	public int maxInputs(){
		return 0;
	}
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        COLOR = Color.values()[nbt.getInteger("COLOR")];
        LIGHT_LEVEL = nbt.getInteger("LIGHTLEVEL");
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("COLOR", COLOR.ordinal());
        nbt.setInteger("LIGHTLEVEL", LIGHT_LEVEL);
		return nbt;
	}	
    public void onMachinePlaced(NBTTagCompound nbt) {
        COLOR = Color.values()[nbt.getInteger("COLOR")];
        LIGHT_LEVEL = nbt.getInteger("LIGHTLEVEL");
    }		
    public NBTTagCompound onMachineBroken(NBTTagCompound nbt) {
        nbt.setInteger("COLOR", COLOR.ordinal());
        nbt.setInteger("LIGHTLEVEL", LIGHT_LEVEL);
		return nbt;
	}	
	public Mode[] getInitialModes(){
		return new Mode[]{Mode.Disabled, Mode.Input, Mode.Input, Mode.Input, Mode.Input, Mode.Input};
	}
	@Override
	public void sideRightClicked(EnumFacing Side) {}
}
