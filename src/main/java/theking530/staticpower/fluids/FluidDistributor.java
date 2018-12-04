package theking530.staticpower.fluids;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.tileentity.TileEntityBase;

public class FluidDistributor {

	private TileEntity TE;
	private FluidTank TANK;
	private int FLUID_OUTPUT_RATE = 15;
	private int FLUID_INPUT_RATE = 15;
	
	public FluidDistributor(TileEntity tileEntity, FluidTank tank) {
		TE = tileEntity;
		TANK = tank;
	}
	
	public void distributeFluid() {
		if(TANK != null && TE != null) { 
			if(!TE.getWorld().isRemote) {
				for(int i=0; i<6; i++) {
					EnumFacing facing = EnumFacing.values()[i];
					if(TE instanceof TileEntityBase) {
						TileEntityBase tempTe = (TileEntityBase)TE;
						if(tempTe != null) {
							if(tempTe.getSideConfiguration(EnumFacing.values()[i]) == Mode.Output && TANK.getFluidAmount() > 0) {
								if(TANK.getFluidAmount() > FLUID_OUTPUT_RATE) {
									pushFluid(facing, FLUID_OUTPUT_RATE);
								}else{
									pushFluid(facing, TANK.getFluidAmount());
								}
							}else if(tempTe.getSideConfiguration(facing) == Mode.Input) {	
								if(TANK.getFluidAmount() < TANK.getCapacity()) {
									pullFluid(facing, TANK.getFluidAmount());
								}else{
									return;
								}
							}
						}
					}	
				}	
			}
		}
	}
	public void pushFluid(EnumFacing facing, int amount) {
		if(TE.getWorld().getTileEntity(TE.getPos().offset(facing)) != null) {
			if(TE.getWorld().getTileEntity(TE.getPos().offset(facing)).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
				IFluidHandler tempTank = TE.getWorld().getTileEntity(TE.getPos().offset(facing)).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
				int fluidProvided = tempTank.fill(new FluidStack(TANK.getFluid().getFluid(), amount), true);
				TANK.drain(fluidProvided, true);
			}
		}
	}	
	public void pullFluid(EnumFacing facing, int amount) {
		if(TE.getWorld().getTileEntity(TE.getPos().offset(facing)) != null) {
			if(TE.getWorld().getTileEntity(TE.getPos().offset(facing)).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
				IFluidHandler tempTank = TE.getWorld().getTileEntity(TE.getPos().offset(facing)).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
				IFluidTankProperties[] tankInfo = tempTank.getTankProperties();
				if(tankInfo.length > 0) {
					if(tankInfo[0].getContents() != null && tankInfo[0].getContents().amount > 0) {
						if(tempTank.drain(1, false).isFluidEqual(TANK.getFluid())) {
							FluidStack provided = tempTank.drain(FLUID_INPUT_RATE, true);
							TANK.fill(provided, true);		
						}else if(TANK.getFluid() == null){
							FluidStack provided = tempTank.drain(FLUID_INPUT_RATE, true);
							TANK.fill(provided, true);		
						}
					}
				}
			}
		}
	}
}

