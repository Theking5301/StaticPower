package theking530.staticpower.machines.esotericenchanter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.registries.EsotericEnchanterRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.EsotericEnchanterRecipeWrapper;
import theking530.staticpower.machines.TileEntityMachineWithTank;
import theking530.staticpower.machines.tileentitycomponents.BatteryInteractionComponent;
import theking530.staticpower.machines.tileentitycomponents.FluidContainerComponent;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.tileentity.SideConfiguration;

public class TileEsotericEnchanter extends TileEntityMachineWithTank {

	private ItemStack nextOutputItemStack;
	private int fluidLeftToExtract;
	private int fluidRequired;
	
	private FluidContainerComponent fluidContainerComponent;
	
	public TileEsotericEnchanter() {
		this.initializeBasicMachine(1.0f, 5000, 100000, 120, 200);
		this.initializeSlots(6, 3, 1);
		this.initializeTank(5000);
		
		this.registerComponent(new BatteryInteractionComponent("Battery Component", slotsInternal, 3, getEnergyStorage()));
		
		nextOutputItemStack = ItemStack.EMPTY;
		fluidContainerComponent = new FluidContainerComponent("Bucket Component", slotsInternal, 4, slotsInternal, 5, this, fluidTank, 40);
		registerComponent(fluidContainerComponent);
		
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0));
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, Mode.Input, 0, 1, 2));
		
		setName("container.EsotericEnchanter");
	}
	
	@Override
	public boolean hasValidRecipe() {
		return EsotericEnchanterRecipeRegistry.Enchanting().getEnchantingRecipe(getInputStack(0), getInputStack(1), getInputStack(2), null, true) != null;
	}
	@Override
	public boolean canProcess() {
		EsotericEnchanterRecipeWrapper recipe =  EsotericEnchanterRecipeRegistry.Enchanting().getEnchantingRecipe(getInputStack(0), getInputStack(1), getInputStack(2), fluidTank.getFluid(), false);
		if(recipe != null && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, recipe.getOutputItemStack())) {
			if(getEnergyStorage().getEnergyStored() > getProcessingEnergy()) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void process() {
		if(!getWorld().isRemote) {
			if(!isProcessing() && !isMoving() && canProcess()) {
				moveTimer = 1;
			}	
			if(!isProcessing() && isMoving() && canProcess()) {
				if(moveTimer < moveSpeed) {
					moveTimer++;
				}else{
					EsotericEnchanterRecipeWrapper recipe =  EsotericEnchanterRecipeRegistry.Enchanting().getEnchantingRecipe(getInputStack(0), getInputStack(1), getInputStack(2), fluidTank.getFluid(), false);
					nextOutputItemStack = recipe.getOutputItemStack().copy();
					fluidLeftToExtract = recipe.getInputFluidStack().amount;
						
					transferItemInternally(slotsInput, 0, slotsInternal, 0);
					transferItemInternally(slotsInput, 1, slotsInternal, 1);
					transferItemInternally(slotsInput, 2, slotsInternal, 2);
					
					fluidRequired = fluidLeftToExtract;
					processingTimer = 1;
					moveTimer = 0;
				}
			}else{
				moveTimer = 0;
			}
			if(isProcessing() && !isMoving()) {
				if(processingTimer < processingTime) {
					useEnergy(getProcessingEnergy() / processingTime);
					int drainAmount = fluidRequired/processingTime;
					fluidLeftToExtract -= drainAmount;
					fluidTank.drain(drainAmount, true);
					processingTimer++;
					updateBlock();
				}else{
					processingTimer=0;
					fluidTank.drain(fluidLeftToExtract, true);
					updateBlock();
					if(!nextOutputItemStack.isEmpty() && InventoryUtilities.canFullyInsertStackIntoSlot(slotsOutput, 0, nextOutputItemStack)) {
						InventoryUtilities.insertItemIntoInventory(slotsOutput, nextOutputItemStack, 0, 0);
						setInternalStack(0, ItemStack.EMPTY);
						setInternalStack(1, ItemStack.EMPTY);
						setInternalStack(2, ItemStack.EMPTY);
					}
				}
			}
		}
	}
	public FluidContainerComponent getFluidInteractionComponent() {
		return fluidContainerComponent;
	}

	public void deserializeData(NBTTagCompound nbt) {
		super.deserializeData(nbt);
		fluidLeftToExtract = nbt.getInteger("FLUID_LEFT");
		fluidRequired = nbt.getInteger("FLUID_REQ");
		nextOutputItemStack = new ItemStack(nbt.getCompoundTag("OUTPUT"));
    }
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
    	super.serializeData(nbt);
    	nbt.setInteger("FLUID_REQ", fluidRequired);
    	nbt.setInteger("FLUID_LEFT", fluidLeftToExtract);
    	NBTTagCompound outItem = new NBTTagCompound();
    	nextOutputItemStack.writeToNBT(outItem);
    	nbt.setTag("OUTPUT", outItem);
		return nbt;	
    }
    
    @Nonnull
    public ItemStack insertItem(Mode sideMode, int slot, @Nonnull ItemStack stack, boolean simulate) {
    	if(sideMode == Mode.Output) {
    		return stack;
    	}
    	if(stack.getItem() != null && stack.getItem() == Items.BOOK) {
    		return slotsInput.insertItem(0, stack, simulate);
    	}
    	if(slot != 0) {
        	return slotsInput.insertItem(slot, stack, simulate);
    	}
    	return stack;
    }
    @Override
	public int fill(FluidStack resource, boolean doFill, EnumFacing facing) {
    	updateBlock();
		if(resource != null && resource.getFluid() == ModFluids.LiquidExperience) {
			return fluidTank.fill(resource, doFill);
		}
		return 0;
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Input2);
		modes.add(Mode.Output);
		modes.add(Mode.Regular);
		modes.add(Mode.Disabled);
		return modes;
	}
	public void setDefaultSideConfiguration(SideConfiguration configuration) {
		configuration.setToDefault();
		configuration.setSideConfiguration(Mode.Input2, SideUtilities.getEnumFacingFromSide(BlockSide.BACK, getFacingDirection()));
	}
}
