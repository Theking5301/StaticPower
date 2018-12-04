package theking530.staticpower.machines.condenser;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import theking530.staticpower.handlers.crafting.registries.CondenserRecipeRegistry;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;

public class TileEntityCondenser extends TileEntityMachineWithTank  {

	public FluidTank fluidTankEthanol;
	
	public FluidStack processingStack;
	public FluidContainerComponent drainComponentEvaporatedMash;
	public FluidContainerComponent drainComponentEthanol;

	public TileEntityCondenser() {
		initializeBasicMachine(0, 0, 0, 0, 20);
		initializeTank(5000);
		initializeSlots(0, 2, 2);
		
		fluidTankEthanol = new FluidTank(5000);
		
		drainComponentEvaporatedMash = new FluidContainerComponent("LeftBucketDrain", slotsInput, 0, slotsOutput, 0, this, fluidTank);
		drainComponentEvaporatedMash.setMode(FluidContainerInteractionMode.FILL);
		drainComponentEthanol = new FluidContainerComponent("RightBucketDrain", slotsInput, 1, slotsOutput, 1, this, fluidTankEthanol);	
		
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1));
	}
	
	@Override
	public String getName() {
		return "Condenser";	
	}	
	public void process() {
		if(!getWorld().isRemote) {
			drainComponentEvaporatedMash.preProcessUpdate();
			drainComponentEthanol.preProcessUpdate();
			if(!isProcessing() && processingStack == null && canProcess()) {
				processingStack = fluidTank.drain(getInputFluidAmount(), true);
				processingTime = Math.max(getOutputCondensingTime(), 0);
				processingTimer++;
			}
			if(isProcessing()) {
				if(processingTimer < processingTime) {
					processingTimer++;
				}else{
					if(processingStack != null) {
						fluidTankEthanol.fill(getOutputFluid(), true);
						processingStack = null;
						processingTimer = 0;		
						updateBlock();
					}
				}
			}
		}
	}
	public boolean canProcess() {
		if(hasOutput()) {
			if(fluidTank.getFluid().amount >= getInputFluidAmount()) {
				if(fluidTankEthanol.getFluid() == null) {
					return true;
				}
				FluidStack tempOutput = getOutputFluid();
				if(fluidTankEthanol.getFluid().isFluidEqual(tempOutput)) {
					if(fluidTankEthanol.getFluid().amount + tempOutput.amount <= fluidTankEthanol.getCapacity()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean hasOutput() {
		return getOutputFluid() != null;
	}
	public int getInputFluidAmount() {
		if(fluidTank.getFluid() != null) {
			return CondenserRecipeRegistry.Condensing().getFluidInputAmount(fluidTank.getFluid());
		}
		return 0;
	}
	public FluidStack getOutputFluid() {
		if(processingStack != null) {
			return CondenserRecipeRegistry.Condensing().getFluidOutput(processingStack);
		}else if(fluidTank.getFluid() != null) {
			return CondenserRecipeRegistry.Condensing().getFluidOutput(fluidTank.getFluid());
		}
		return null;
	}
	public int getOutputCondensingTime() {
		if(fluidTank.getFluid() != null) {
			return CondenserRecipeRegistry.Condensing().getCondensingTime(fluidTank.getFluid());
		}
		return 0;
	}	
    @Override  
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        fluidTankEthanol.setFluid(FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("TANK2")));
        processingStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("PFLUID"));
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if(fluidTankEthanol.getFluid() != null) {
            NBTTagCompound fTag = new NBTTagCompound();
            fluidTankEthanol.getFluid().writeToNBT(fTag);
            nbt.setTag("TANK2", fTag);    	
        }
        if(processingStack != null) {
        	NBTTagCompound pTag = new NBTTagCompound();
        	processingStack.writeToNBT(pTag);
        	nbt.setTag("PFLUID", pTag);
        }
        return nbt;
	}	
	public void deserializeOnPlaced(NBTTagCompound nbt, World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)  {
		super.deserializeOnPlaced(nbt, world, pos, state, placer, stack);
        FluidStack tempStack = null;
        tempStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("TANK2"));
        fluidTankEthanol.setFluid(tempStack);
        tempStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("PFluid"));
        processingStack = tempStack;
	}
    
	@Override
	public int receiveEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return 0;	
	}
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return false;
	}
	@Override
	public int fill(FluidStack resource, boolean doFill, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		if(CondenserRecipeRegistry.Condensing().getFluidOutput(resource) != null){
			return fluidTank.fill(resource, doFill);	
		}
		return 0;
	}
	@Override	
	public FluidStack drain(FluidStack resource, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTankEthanol.drain(resource, doDrain);
	}
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTankEthanol.drain(maxDrain, doDrain);
	}
}
		

	


	
