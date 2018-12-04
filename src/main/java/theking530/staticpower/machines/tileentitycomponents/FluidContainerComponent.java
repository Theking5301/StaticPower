package theking530.staticpower.machines.tileentitycomponents;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.tileentity.TileEntityBase;

public class FluidContainerComponent implements ITileEntityComponent{
	public static final int DEFAULT_FLUID_TO_CONTAINER_RATE = 15;
	
	private String componentName;
	
	private ItemStackHandler initialEmptyBucketHandler;
	private int initialEmptyBucketSlot;
	private ItemStackHandler initialFilledBucketHandler;
	private int initialFilledBucketSlot;
	
	private ItemStackHandler emptyBucketHandler;
	private int emptyBucketSlot;
	private ItemStackHandler fillterBucketHandler;
	private int filledBucketSlot;
	
	/**
	 * Must use a fluid handler reference and not a capability because capabilities for certain blocks are not allowed to fill depending on machine rules.
	 */
	private IFluidHandler fluidHandler;
	private TileEntityBase tileEntity;
	private int fluidToContainerRate;
	private FluidContainerInteractionMode interactionMode = FluidContainerInteractionMode.DRAIN;

	private int moveTime = 0;
	private int moveTimer = 0;
	
	private boolean isEnabled;
	
	public enum FluidContainerInteractionMode {
		FILL, DRAIN;
	}
	
	public FluidContainerComponent(String componentName, ItemStackHandler EmptyBucketHandler, int EmptyBucketSlot, ItemStackHandler FilledBucketHandler, int FilledBucketSlot, TileEntityBase tileEntity, IFluidHandler fluidHandler) {
		this(componentName, EmptyBucketHandler, EmptyBucketSlot, FilledBucketHandler, FilledBucketSlot, tileEntity, fluidHandler, DEFAULT_FLUID_TO_CONTAINER_RATE);
	}
	public FluidContainerComponent(String componentName, ItemStackHandler EmptyBucketHandler, int EmptyBucketSlot, ItemStackHandler FilledBucketHandler, int FilledBucketSlot,
			TileEntityBase tileEntity, IFluidHandler fluidHandler, int drainRate) {

		this.componentName = componentName;
		this.initialEmptyBucketHandler = EmptyBucketHandler;
		this.initialEmptyBucketSlot = EmptyBucketSlot;
		this.initialFilledBucketHandler = FilledBucketHandler;
		this.initialFilledBucketSlot = FilledBucketSlot;	 
		
		this.emptyBucketHandler = EmptyBucketHandler;
		this.emptyBucketSlot = EmptyBucketSlot;
		this.fillterBucketHandler = FilledBucketHandler;
		this.filledBucketSlot = FilledBucketSlot;	
		
		this.fluidHandler = fluidHandler;
		this.tileEntity = tileEntity;
		this.fluidToContainerRate = drainRate;
	}
	public void preProcessUpdate() {
		if(!tileEntity.getWorld().isRemote) {
			if(moveTimer >= moveTime) {
				if(interactionMode == FluidContainerInteractionMode.FILL) {
					fillFromContainer();
				}else{
					drainToContainer();
				}
				moveTimer = 0;
			}else{
				moveTimer++;
			}
		}
	}
	public FluidContainerComponent setMode(FluidContainerInteractionMode NewMode) {
		interactionMode = NewMode;
		if(interactionMode == FluidContainerInteractionMode.FILL) {
			fillterBucketHandler = initialEmptyBucketHandler;
			filledBucketSlot = initialEmptyBucketSlot;
			emptyBucketHandler = initialFilledBucketHandler;
			emptyBucketSlot = initialFilledBucketSlot;
		}else{
			fillterBucketHandler = initialFilledBucketHandler;
			filledBucketSlot = initialFilledBucketSlot;
			emptyBucketHandler = initialEmptyBucketHandler;
			emptyBucketSlot = initialEmptyBucketSlot;
		}
		return this;
	}
	private void drainToContainer() {
		if(emptyBucketHandler.getStackInSlot(emptyBucketSlot) !=  ItemStack.EMPTY && emptyBucketHandler.getStackInSlot(emptyBucketSlot).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && emptyBucketHandler.getStackInSlot(emptyBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) instanceof FluidHandlerItemStack) {
			FluidHandlerItemStack tempContainer = (FluidHandlerItemStack)emptyBucketHandler.getStackInSlot(emptyBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			FluidStack testStack = fluidHandler.getTankProperties()[0].getContents();
			if(testStack != null) {
				if(tempContainer.getFluid() == null || tempContainer.getFluid().isFluidEqual(testStack)) {
					FluidStack drained = null;				
					if(!tileEntity.getWorld().isRemote) {
						drained = fluidHandler.drain(fluidToContainerRate, false);	
						int filled = tempContainer.fill(drained, true);
						fluidHandler.drain(filled, true);
						tileEntity.updateBlock();
					}
					if(drained != null && drained.amount > 0) {
						tileEntity.getWorld().playSound(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 0.25f, 1, false);						
					}
				}
			}
		}	
		if(emptyBucketHandler.getStackInSlot(emptyBucketSlot) !=  ItemStack.EMPTY && emptyBucketHandler.getStackInSlot(emptyBucketSlot).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && emptyBucketHandler.getStackInSlot(emptyBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) instanceof FluidHandlerItemStack) {
			FluidHandlerItemStack tempContainer = (FluidHandlerItemStack)emptyBucketHandler.getStackInSlot(emptyBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(tempContainer.getFluid() == null) {
				return;
			}
			FluidStack testStack = fluidHandler.drain(1, false);
			if(testStack == null || tempContainer.getFluid().amount >= tempContainer.getTankProperties()[0].getCapacity()) {
				if(fillterBucketHandler.getStackInSlot(filledBucketSlot) != null) {
					if(fillterBucketHandler.getStackInSlot(filledBucketSlot).isItemEqual(emptyBucketHandler.getStackInSlot(emptyBucketSlot))) {
						if(fillterBucketHandler.getStackInSlot(filledBucketSlot).getCount() < fillterBucketHandler.getStackInSlot(filledBucketSlot).getMaxStackSize()) {
							fillterBucketHandler.insertItem(filledBucketSlot, emptyBucketHandler.extractItem(emptyBucketSlot, 1, false), false);
						}
					}
				}else{
					fillterBucketHandler.insertItem(filledBucketSlot, emptyBucketHandler.extractItem(emptyBucketSlot, 1, false), false);
				}
			}
		}
	}
	private void fillFromContainer() {
		if(!fillterBucketHandler.getStackInSlot(filledBucketSlot).isEmpty() && fillterBucketHandler.getStackInSlot(filledBucketSlot).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && fillterBucketHandler.getStackInSlot(filledBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) instanceof FluidHandlerItemStack) {
			FluidHandlerItemStack tempContainer = (FluidHandlerItemStack)fillterBucketHandler.getStackInSlot(filledBucketSlot).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(tempContainer.getFluid() != null) {
				FluidStack testStack = fluidHandler.drain(Integer.MAX_VALUE, false);
				if(testStack == null || (testStack.amount < fluidHandler.getTankProperties()[0].getCapacity() && testStack.isFluidEqual(tempContainer.getFluid()))) {
					FluidStack drained = null;				
					if(!tileEntity.getWorld().isRemote) {
						drained = tempContainer.drain(Math.min(fluidToContainerRate, fluidHandler.getTankProperties()[0].getCapacity()), false);
						int filled = fluidHandler.fill(drained, true);
						tempContainer.drain(filled, true);		
						tileEntity.updateBlock();
					}
					if(drained != null && drained.amount > 0) {
						tileEntity.getWorld().playSound(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 0.25f, 1, false);						
					}
				}
			}	
			if(tempContainer.getFluid() == null) {
				if(emptyBucketHandler.getStackInSlot(emptyBucketSlot) !=  ItemStack.EMPTY) {
					if(emptyBucketHandler.getStackInSlot(emptyBucketSlot).isItemEqual(fillterBucketHandler.getStackInSlot(filledBucketSlot))) {
						if(emptyBucketHandler.getStackInSlot(emptyBucketSlot).getCount() < emptyBucketHandler.getStackInSlot(emptyBucketSlot).getMaxStackSize()) {
							emptyBucketHandler.insertItem(emptyBucketSlot, fillterBucketHandler.extractItem(filledBucketSlot, 1, false), false);
						}
					}
				}else{
					emptyBucketHandler.insertItem(emptyBucketSlot, fillterBucketHandler.extractItem(filledBucketSlot, 1, false), false);
				}
			}
		}
	}
	public FluidContainerInteractionMode getMode() {
		return interactionMode;
	}
	public FluidContainerInteractionMode getInverseMode() {
		if(interactionMode == FluidContainerInteractionMode.DRAIN) {
			return FluidContainerInteractionMode.FILL;
		}else{
			return FluidContainerInteractionMode.DRAIN;
		}
	}
	@Override
	public String getComponentName() {
		return componentName;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	@Override
	public void postProcessUpdate() {
		// TODO Auto-generated method stub
		
	}
}
