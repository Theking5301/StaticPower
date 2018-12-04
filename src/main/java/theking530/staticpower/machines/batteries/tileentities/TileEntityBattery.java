package theking530.staticpower.machines.batteries.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.energy.PowerDistributor;
import theking530.staticpower.machines.TileEntityMachine;

public class TileEntityBattery extends TileEntityMachine {
	
	private int minPowerThreshold;
	private int maxPowerThreshold;
	
	private int maxPowerIO;

	private int inputRFTick;
	private int outputRFTick;
	
	protected PowerDistributor powerDistributor;
	
	public TileEntityBattery() {
		initializeSlots(0,0,0, false);
		initializeBasicMachine(2, 0, 1000000, 0, 0);
		powerDistributor = new PowerDistributor(this, energyStorage);	
		inputRFTick = energyStorage.getMaxReceive();
		outputRFTick = energyStorage.getMaxExtract();
		energyStorage.setMaxExtract(getOutputLimit());
		energyStorage.setMaxReceive(getInputLimit());
	}
	
	public void process() {
		if(!getWorld().isRemote) {
			powerDistributor.distributePower();
			energyStorage.setMaxExtract(getOutputLimit());
			energyStorage.setMaxReceive(getInputLimit());
			getWorld().notifyNeighborsOfStateChange(pos, blockType, true);
		}
	}
	public void setMinimumPowerThreshold(int newThreshold) {
		minPowerThreshold = newThreshold;
	}
	public void setMaximumPowerThreshold(int newThreshold) {
		maxPowerThreshold = newThreshold;
	}
	public int getMinimumPowerThreshold() {
		return minPowerThreshold;
	}
	public int getMaximumPowerThreshold() {
		return maxPowerThreshold;
	}

	public int getInputLimit() {
		return inputRFTick;
	}
	public int getOutputLimit() {
		return outputRFTick;
	}
	public void setInputLimit(int newLimit) {
		inputRFTick = newLimit;
	}
	public void setOutputLimit(int newLimit) {
		outputRFTick = newLimit;
	}
	
	public void setMaximumPowerIO(int newMaxIO) {
		maxPowerIO = newMaxIO;
		setInputLimit(maxPowerIO);
		setOutputLimit(maxPowerIO);
	}
	public int getMaximumPowerIO() {
		return maxPowerIO;
	}
	
    @Override  
	public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        energyStorage.readFromNBT(nbt);

    	minPowerThreshold = nbt.getInteger("MIN_THRESH");
    	maxPowerThreshold = nbt.getInteger("MAX_THRESH");
    	
    	inputRFTick = nbt.getInteger("inputLimit");
    	outputRFTick = nbt.getInteger("outputLimit");
    	
    	maxPowerIO = nbt.getInteger("MAX_IO");
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);

        nbt.setInteger("MAX_THRESH", maxPowerThreshold);
        nbt.setInteger("MIN_THRESH", minPowerThreshold);
        
        nbt.setInteger("inputLimit", inputRFTick);
        nbt.setInteger("outputLimit", outputRFTick);
        
        nbt.setInteger("MAX_IO", maxPowerIO);
        
        return nbt;
	}
	
	//Tab Integration
	public boolean shouldOutputRedstoneSignal() {	
		if(minPowerThreshold == 0 && maxPowerThreshold == 0) {
			return false;
		}
		if(getEnergyLevelScaled(100) > this.minPowerThreshold && getEnergyLevelScaled(100) < this.maxPowerThreshold) {
			return true;
		}
		return false;
	}
	@Override
	public String getName() {
		return "Battery";	
	}
	@Override
	public void onSidesConfigUpdate() {

	}
	
	//Upgrades
	@Override
	public boolean isUpgradeable() {
		return false;
	}
	
	//Energy
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if(getSideConfiguration(from) == Mode.Disabled) {
			return false;
		}else{
			return true;
		}
	}
	public float getEnergyLevelScaled(int i) {
		float amount = energyStorage.getEnergyStored();
		float percentFilled = (amount/energyStorage.getMaxEnergyStored());	
		return percentFilled * (float)i;
	}	
	
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		if(from != null && getSideConfiguration(from) != Mode.Output) {
			return 0;
		}
		return energyStorage.extractEnergy(maxExtract, simulate);
	}
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		if(from != null && getSideConfiguration(from) != Mode.Input) {
			return 0;
		}
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Output);
		modes.add(Mode.Disabled);
		return modes;
	}
}


	
