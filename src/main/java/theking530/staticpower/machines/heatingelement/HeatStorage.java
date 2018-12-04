package theking530.staticpower.machines.heatingelement;

import net.minecraft.nbt.NBTTagCompound;

public class HeatStorage implements IHeatable{

	protected int CURRENT_HEAT;
	protected int MAX_HEAT;
	
	public HeatStorage(int maxHeat) {
		MAX_HEAT = maxHeat;
		CURRENT_HEAT = 0;
	}
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("heat", CURRENT_HEAT);
		return nbt;
	}
	public void readFromNBT(NBTTagCompound nbt) {
		CURRENT_HEAT = nbt.getInteger("heat");
	}
	@Override
	public int getHeat() {
		return CURRENT_HEAT;
	}
	@Override
	public int recieveHeat(int heat) {
		if(CURRENT_HEAT + heat <= MAX_HEAT) {
			CURRENT_HEAT += heat;
			return heat;
		}else{
			int temp = CURRENT_HEAT - MAX_HEAT;
			CURRENT_HEAT = MAX_HEAT;
			return temp;
		}
	}
	@Override
	public boolean canHeat() {
		return false;
	}
	@Override
	public int extractHeat(int heat) {
		if(CURRENT_HEAT - heat >= 0) {
			CURRENT_HEAT -= heat;
			return heat;
		}else{
			int temp = CURRENT_HEAT;
			CURRENT_HEAT = 0;
			return temp;
		}
	}
	@Override
	public int getMaxHeat() {
		return MAX_HEAT;
	}
	public void setHeat(int heat) {
		CURRENT_HEAT = heat;
	}
	public float getHeatedPercent() {
		return (float)CURRENT_HEAT/(float)MAX_HEAT;
	}

}
