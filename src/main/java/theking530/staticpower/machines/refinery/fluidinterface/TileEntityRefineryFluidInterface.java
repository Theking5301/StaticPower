package theking530.staticpower.machines.refinery.fluidinterface;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.machines.refinery.BaseRefineryTileEntity;
import theking530.staticpower.machines.refinery.controller.TileEntityFluidRefineryController;
import theking530.staticpower.tileentity.SideConfiguration;

public class TileEntityRefineryFluidInterface extends BaseRefineryTileEntity {	

	public TileEntityRefineryFluidInterface() {
		super();
		disableFaceInteraction = false;
	}
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
		Mode sideConfig = facing == null ? Mode.Regular : getSideConfiguration(facing);
    	if(hasController() && sideConfig != Mode.Disabled && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		TileEntityFluidRefineryController controller = getContorller();
    		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {
				@Override
				public IFluidTankProperties[] getTankProperties() {
					return controller.getOutputTank().getTankProperties();
				}
				@Override
				public int fill(FluidStack resource, boolean doFill) {
					if(sideConfig != Mode.Input) {
						return 0;
					}
					return ((IFluidHandler)controller.getCapability(capability, facing)).fill(resource, doFill);
				}
				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {
					if(sideConfig != Mode.Output) {
						return null;
					}
					return controller.getOutputTank().drain(resource, doDrain);
				}
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {
					if(sideConfig != Mode.Output) {
						return null;
					}
					return controller.getOutputTank().drain(maxDrain, doDrain);
				}			
    		});
    	}
    	return super.getCapability(capability, facing);
    }
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
		Mode sideConfig = facing == null ? Mode.Regular : getSideConfiguration(facing);
    	if(hasController() && sideConfig != Mode.Disabled && capability==CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return getContorller().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
    	}
        return super.hasCapability(capability, facing);
    }
	public void setDefaultSideConfiguration(SideConfiguration configuration) {
		configuration.setAllToMode(Mode.Input);
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Output);
		return modes;
	}
}

