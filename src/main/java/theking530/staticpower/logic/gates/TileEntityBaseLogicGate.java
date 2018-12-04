package theking530.staticpower.logic.gates;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import theking530.staticpower.assists.utilities.SideModeList.Mode;

public class TileEntityBaseLogicGate extends TileEntity implements ITickable{
	
	public int[] OUTPUT_SIGNALS = new int[]{0,0,0,0,0,0};
	public Mode SIDE_MODES[] = getInitialModes(); 
	private boolean placed = false;
	
	public TileEntityBaseLogicGate() {

	}
	@Override
	public void update() {
		if(!placed) {
			reset();
			updateGate();
			placed = true;
		}else{
			gateTick();
		}
	}
	public void gateTick() {
		
	}
    @Override  
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        for(int i=0; i<6; i++) {
            OUTPUT_SIGNALS[i] = nbt.getInteger("OUTPUT" + i);	
        }
        for(int i=0; i<6; i++) {
        	SIDE_MODES[i] = Mode.values()[nbt.getInteger("OUTPUT_MODE" + i)];	
        }
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for(int i=0; i<6; i++) {
            nbt.setInteger("OUTPUT" + i, OUTPUT_SIGNALS[i]);
        }
        for(int i=0; i<6; i++) {
            nbt.setInteger("OUTPUT_MODE" + i, SIDE_MODES[i].ordinal());
        }
		return nbt;	
	}
    public void onMachinePlaced(NBTTagCompound nbt) {
    }		
    public NBTTagCompound onMachineBroken(NBTTagCompound nbt) {
		return nbt;
	}	
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket(){
    	NBTTagCompound tag = new NBTTagCompound();
    	writeToNBT(tag);
    	return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
    }
	public boolean isOn(){
		return false;
	}
	public ArrayList<EnumFacing> getInputSides(){
		ArrayList<EnumFacing> facingList = new ArrayList<EnumFacing>();
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Input) {
				facingList.add(EnumFacing.values()[i]);
			}
		}
		return facingList;
	}
	public EnumFacing getInputSide(){
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Input) {
				return EnumFacing.values()[i];
			}
		}
		return null;
	}
	public int getInputSignal(EnumFacing side) {
		if(getWorld().isRemote) {
			return 0;
		}
		if(side != null) {
			int strength = 0;
			for(int i=0; i<6; i++) {
				int temp = getWorld().getStrongPower(pos.offset(side), EnumFacing.values()[i]);
				if(temp > strength) {
					strength = temp;
				}
			}

			return strength;			
		}
		return 0;
	}
	public int addAllInputSignals() {
		int sum = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Input) {
				sum += getInputSignal(EnumFacing.values()[i]);
			}
		}
		return sum;
	}
	public int getExtraSignal(EnumFacing side) {
		if(side != null) {
			int strength = 0;

			for(int i=0; i<6; i++) {
				int temp = getWorld().getStrongPower(pos.offset(side), EnumFacing.values()[i]);
				if(temp > strength) {
					strength = temp;
				}
			}

			return strength;			
		}
		return 0;
	}
	public int addAllExtraSignals() {
		int sum = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Regular) {
				sum += getInputSignal(EnumFacing.values()[i]);
			}
		}
		return sum;
	}
	public int getPoweredInputSignalCount() {
		int count = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Input) {
				if(getInputSignal(EnumFacing.values()[i]) > 0) {
					count++;
				}
			}
		}
		return count;
	}
	public void setOutputSignal(EnumFacing side, int strength) {
		if(side == getInputSide()) {
			return;
		}else{
			if(SIDE_MODES[side.ordinal()] == Mode.Output) {
				OUTPUT_SIGNALS[side.ordinal()] = strength;		
			}       
		}
	}	
	public void setAllOutputs(int strength) {
		for(int i=2; i<6; i++) {
			if(getInputSide() != null && i == getInputSide().ordinal()) {

			}else{
				setOutputSignal(EnumFacing.values()[i], strength);
			}
		}
	}	
	public void setExtraSignal(EnumFacing side, int strength) {
		if(side == getInputSide()) {
			return;
		}else{
			if(SIDE_MODES[side.ordinal()] == Mode.Regular) {
				OUTPUT_SIGNALS[side.ordinal()] = strength;	
			}       
		}
	}	
	public void setAllExtraOutputs(int strength) {
		for(int i=2; i<6; i++) {
			if(getInputSide() != null && i == getInputSide().ordinal()) {

			}else{
				setExtraSignal(EnumFacing.values()[i], strength);
			}
		}
	}
	public void reset(){
		for(int i=2; i<6; i++) {
			EnumFacing side = EnumFacing.values()[i];
			OUTPUT_SIGNALS[side.ordinal()] = 0;		
		}
	}
	public void updateGate() {
		getWorld().notifyNeighborsOfStateChange(pos, blockType, false);
        getWorld().markAndNotifyBlock(pos, getWorld().getChunkFromBlockCoords(pos), getWorld().getBlockState(pos), getWorld().getBlockState(pos), 2);
        //markDirty();
	}
	public int maxInputs(){
		return 1;
	}
	public int maxOutputs(){
		return 4;
	}
	public int maxExtra(){
		return 0;
	}
	public int getInputCount() {
		int inputCount = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Input) {
				inputCount++;
			}
		}
		return inputCount;
	}
	public int getOutputCount() {
		int inputCount = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Output) {
				inputCount++;
			}
		}
		return inputCount;
	}
	public int getExtraCount() {
		int inputCount = 0;
		for(int i=0; i<6; i++) {
			if(SIDE_MODES[i] == Mode.Regular) {
				inputCount++;
			}
		}
		return inputCount;
	}
	public void sideRightClicked(EnumFacing Side) {
		if(Side == null) {
			reset();
			updateGate();
			return;
		}
		if(SIDE_MODES[Side.ordinal()] == Mode.Disabled) {
			if(getInputCount() + 1 <= maxInputs()) {
				SIDE_MODES[Side.ordinal()] = Mode.Input;		
			}else{
				SIDE_MODES[Side.ordinal()] = Mode.Output;		
			}
		}else if(SIDE_MODES[Side.ordinal()] == Mode.Input){
			SIDE_MODES[Side.ordinal()] = Mode.Output;
		}else if(SIDE_MODES[Side.ordinal()] == Mode.Output){
			if(getExtraCount() + 1 <= maxExtra()) {
				SIDE_MODES[Side.ordinal()] = Mode.Regular;	
			}else{
				SIDE_MODES[Side.ordinal()] = Mode.Disabled;
			}
		}else{
			SIDE_MODES[Side.ordinal()] = Mode.Disabled;
		}
		reset();
		updateGate();
	}
	public Mode[] getInitialModes(){
		return new Mode[]{Mode.Disabled, Mode.Disabled, Mode.Input, Mode.Output, Mode.Output, Mode.Output};
	}
}
