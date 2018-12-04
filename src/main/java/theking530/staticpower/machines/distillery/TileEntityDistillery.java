package theking530.staticpower.machines.distillery;

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
import theking530.staticpower.handlers.crafting.registries.DistilleryRecipeRegistry;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.condenser.TileEntityCondenser;
import theking530.staticpower.machines.heatingelement.HeatStorage;
import theking530.staticpower.machines.heatingelement.IHeatable;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent.FluidContainerInteractionMode;

public class TileEntityDistillery extends TileEntityMachineWithTank implements IHeatable{

	public HeatStorage heatStorage;
	public FluidTank fluidTank2;
	
	public FluidStack processingStack;
	public FluidContainerComponent drainComponentEvaporatedMash;
	public FluidContainerComponent drainComponentMash;
	
	public TileEntityDistillery() {
		initializeBasicMachine(0, 0, 0, 0, 2);
		initializeTank(5000);
		initializeSlots(0, 2, 2);
		
		heatStorage = new HeatStorage(150);
		fluidTank2 = new FluidTank(5000);
		
		drainComponentMash = new FluidContainerComponent("LeftBucketDrain", slotsInput, 0, slotsOutput, 0, this, fluidTank);
		drainComponentMash.setMode(FluidContainerInteractionMode.FILL);
		drainComponentEvaporatedMash = new FluidContainerComponent("RightBucketDrain", slotsInput, 1, slotsOutput, 1, this, fluidTank2);
		
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 0, 1));
	}
	@Override
	public String getName() {
		return "Distillery";	
	}	
	public void process() {
		if(!getWorld().isRemote) {
			drainComponentEvaporatedMash.preProcessUpdate();
			drainComponentMash.preProcessUpdate();
			if(!isProcessing() && canProcess() && processingStack == null) {
				processingStack = fluidTank.drain(getInputFluidAmount(), true);
				processingTimer++;
			}
			if(isProcessing()) {
				if(processingTimer < processingTime) {
					processingTimer++;
				}else{
					if(processingStack != null) {
						processingTimer = 0;
						fluidTank2.fill(getOutputFluid(), true);
						processingStack = null;	
						updateBlock();
					}
				}
			}
			if(fluidTank2.getFluid() != null) {
				if(CondenserRecipeRegistry.Condensing().getFluidOutput(fluidTank2.getFluid()) != null) {
					if(getWorld().getTileEntity(pos.offset(EnumFacing.UP)) instanceof TileEntityCondenser) {
						TileEntityCondenser te = (TileEntityCondenser) getWorld().getTileEntity(pos.offset(EnumFacing.UP));
						fluidTank2.drain(te.fill(fluidTank2.getFluid(), true, EnumFacing.DOWN), true);
					}	
				}
			}
		}
	}
	public boolean canProcess() {
		if(hasOutput()) {
			if(fluidTank.getFluid().amount >= getInputFluidAmount()) {
				if(getHeat() >= getOutputMinHeat()) {
					if(fluidTank2.getFluid() == null) {
						return true;
					}
					FluidStack tempOutput = getOutputFluid();
					if(fluidTank2.getFluid().isFluidEqual(tempOutput)) {
						if(fluidTank2.getFluid().amount + tempOutput.amount <= fluidTank2.getCapacity()) {
							return true;
						}
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
			return DistilleryRecipeRegistry.Distillery().getFluidInputAmount(fluidTank.getFluid(), getHeat());
		}
		return 0;
	}
	public FluidStack getOutputFluid() {
		if(fluidTank.getFluid() != null) {
			return DistilleryRecipeRegistry.Distillery().getFluidOutput(fluidTank.getFluid(), getHeat());
		}
		return null;
	}
	public int getOutputHeatCost() {
		if(fluidTank.getFluid() != null) {
			return DistilleryRecipeRegistry.Distillery().getHeatCost(fluidTank.getFluid(), getHeat());
		}
		return 0;
	}
	public int getOutputMinHeat() {
		if(fluidTank.getFluid() != null) {
			return DistilleryRecipeRegistry.Distillery().getHeatMin(fluidTank.getFluid(), getHeat());
		}
		return 0;
	}
    @Override  
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        heatStorage.readFromNBT(nbt);

        FluidStack tempStack = null;
        tempStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("TANK2"));
        fluidTank2.setFluid(tempStack);
        tempStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("PFluid"));
        processingStack = tempStack;
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        heatStorage.writeToNBT(nbt);
        if(fluidTank2.getFluid() != null) {
            NBTTagCompound fTag = new NBTTagCompound();
            fluidTank2.getFluid().writeToNBT(fTag);
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
		heatStorage.readFromNBT(nbt);

        FluidStack tempStack = null;
        tempStack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("TANK2"));
        fluidTank2.setFluid(tempStack);
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
	public int getHeat() {
		return heatStorage.getHeat();
	}
	@Override
	public int recieveHeat(int heat) {
		updateBlock();
		return heatStorage.recieveHeat(heat);
	}
	@Override
	public boolean canHeat() {
		return true;
	}
	@Override
	public int extractHeat(int heat) {
		updateBlock();
		return heatStorage.extractHeat(heat);
	}
	@Override
	public int getMaxHeat() {
		return heatStorage.getMaxHeat();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		if(DistilleryRecipeRegistry.Distillery().getFluidOutput(resource, 10000000) != null){
			return fluidTank.fill(resource, doFill);	
		}
		return 0;
	}
	@Override	
	public FluidStack drain(FluidStack resource, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTank2.drain(resource, doDrain);
	}
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain, EnumFacing facing) {
		if(!getWorld().isRemote) {
			updateBlock();
		}
		return fluidTank2.drain(maxDrain, doDrain);
	}
}
		

	


	
