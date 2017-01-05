package theking530.staticpower.machines.fermenter;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.handlers.crafting.registries.FermenterRecipeRegistry;
import theking530.staticpower.machines.BaseMachineWithTank;
import theking530.staticpower.machines.machinecomponents.DrainToBucketComponent;
import theking530.staticpower.machines.machinecomponents.DrainToBucketComponent.FluidContainerInteractionMode;

public class TileEntityFermenter extends BaseMachineWithTank {

	public DrainToBucketComponent DRAIN_COMPONENT;
	
	public TileEntityFermenter() {
		initializeBaseMachineWithTank(4, 500, 100000, 160, 45, 1, 11, 2, 5000);
		DRAIN_COMPONENT = new DrainToBucketComponent("BucketDrain", SLOTS_INPUT, 10, SLOTS_OUTPUT, 0, this, TANK, FLUID_TO_CONTAINER_RATE);
		setBatterySlot(10);
		//setFluidContainerSlot(9, FluidContainerMode.FILL);
	}
	@Override
	public String getName() {
		return "container.Fermenter";		
	}		
	
    //Process
	public FluidStack getFermentingResult(ItemStack itemStack) {
		return FermenterRecipeRegistry.Fermenting().getFluidResult(itemStack);
	}
	@Override 
	public boolean hasResult(ItemStack itemstack) {
		if(itemstack != null && getFermentingResult(itemstack) != null) {
			return true;
		}
		return false;
	}
	@Override
	public boolean canProcess(ItemStack itemstack) {
		if(itemstack == null) {
			return false;
		}
		FluidStack fluidstack = FermenterRecipeRegistry.Fermenting().getFluidResult(itemstack);
		if(hasResult(itemstack) && fluidstack != null) {
			if(fluidstack.amount + TANK.getFluidAmount() > TANK.getCapacity()) {
				return false;
			}
			if(STORAGE.getEnergyStored() < getProcessingCost()) {
				return false;
			}
			if (TANK.getFluid() != null && !fluidstack.isFluidEqual(TANK.getFluid())) {
				return false;
			}
			if(TANK.getFluid() == null) {
				return true;
			}
			if(TANK.getFluidAmount() + fluidstack.amount <= TANK.getCapacity()) {
				return true;
			}
			if (TANK.getFluid() != null && fluidstack.isFluidEqual(TANK.getFluid())) {
				return true;
			}				
		}
		return false;
	}
	@Override
	public int getProcessingEnergy(ItemStack itemStack) {
		if(getResult(itemStack) != null) {
			return INITIAL_POWER_USE*PROCESSING_ENERGY_MULT;
		}
		return 0;
	}	
	public void process() {
		if(!worldObj.isRemote) {
			DRAIN_COMPONENT.update();
			if(!isProcessing() && !isMoving()) {
				for(int i=0; i<9; i++) {
					if(SLOTS_INPUT.getStackInSlot(i) != null && canProcess(SLOTS_INPUT.getStackInSlot(i))) {
						moveItem(SLOTS_INPUT, i, SLOTS_INTERNAL, 0);
						MOVE_TIMER = 1;
						break;
					}
				}	
			}else{
				if(!isProcessing() && SLOTS_INTERNAL.getStackInSlot(0) != null) {
					PROCESSING_TIMER++;
				}
				if(isProcessing()) {
					if(PROCESSING_TIMER < PROCESSING_TIME) {
						PROCESSING_TIMER++;
						STORAGE.extractEnergy(getProcessingCost()/PROCESSING_TIME, false);
						updateBlock();
					}else{
						TANK.fill(getFermentingResult(SLOTS_INTERNAL.getStackInSlot(0)), true);
						SLOTS_INTERNAL.setStackInSlot(0, null);
						PROCESSING_TIMER = 0;
						MOVE_TIMER = 0;
						markDirty();
						updateBlock();
					}
				}
			}			
		}
	}
}


	
