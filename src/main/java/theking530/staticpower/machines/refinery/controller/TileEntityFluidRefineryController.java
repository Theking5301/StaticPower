package theking530.staticpower.machines.refinery.controller;

import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.machines.refinery.BaseRefineryTileEntity;
import theking530.staticpower.machines.refinery.RefineryNetwork;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;

public class TileEntityFluidRefineryController extends BaseRefineryTileEntity {

	private FluidTank staticInputTank;
	private FluidTank energizedInputTank;	
	private FluidTank lumumInputTank;
	
	private FluidTank outputTank;
	private RefineryNetwork network;
	
	private boolean allowStaticFlow;
	private boolean allowEnergizedFlow;
	private boolean allowLumumFlow;
	
	private short staticFlowRate;
	private short energizedFlowRate;
	private short lumumFlowRate;
	
	private boolean allowPurifierFlow;
	private boolean allowReagentFlow;
	private boolean allowNutralizerFlow;
	
	private short purifierFlowRate;
	private short reagentFlowRate;
	private short nutralizerFlowRate;
	
	private int purifiersAmount;
	private int reagentAmount;
	private int nutralizerAmount;
	
	public TileEntityFluidRefineryController() {
		initializeSlots(0, 3, 0);
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1, 2, 3, 4, 5, 6, 7, 8));
		
		staticInputTank = new FluidTank(10000);
		energizedInputTank = new FluidTank(10000);
		lumumInputTank = new FluidTank(10000);
		
		outputTank = new FluidTank(10000);
		
		allowPurifierFlow = false;
		allowReagentFlow = false;
		allowNutralizerFlow = false;
		
		purifiersAmount = 0;
		reagentAmount = 0;
		nutralizerAmount = 0;
		
		allowStaticFlow = false;
		allowEnergizedFlow = false;
		allowLumumFlow = false;
		
		staticFlowRate = 1;
		energizedFlowRate = 1;
		lumumFlowRate = 1;
		
		purifierFlowRate = 1;
		reagentFlowRate = 1;
		nutralizerFlowRate = 1;
	}
	@Override
	public void process() {
		if(!getWorld().isRemote) {
			updateGrid();
			processExistingFluid();
			processNewFluid();

			if(!slotsInput.getStackInSlot(0).isEmpty()) {
				if(purifiersAmount < 100) {
					purifiersAmount += 1;
					slotsInput.extractItem(0, 1, false);
				}
			}
			if(!slotsInput.getStackInSlot(1).isEmpty()) {
				if(reagentAmount < 100) {
					reagentAmount += 1;
					slotsInput.extractItem(1, 1, false);
				}
			}
			if(!slotsInput.getStackInSlot(2).isEmpty()) {
				if(nutralizerAmount < 100) {
					nutralizerAmount += 1;
					slotsInput.extractItem(2, 1, false);		
				}
			}
			updateBlock();
		}
	}
	
	private void processNewFluid() {
		FluidStack sDrained = staticInputTank.drain(staticFlowRate, false);
		FluidStack eDrained = energizedInputTank.drain(energizedFlowRate, false);
		FluidStack lDrained = lumumInputTank.drain(lumumFlowRate, false);

		if(staticFlowRate + energizedFlowRate + lumumFlowRate > outputTank.getCapacity() - outputTank.getFluidAmount()) {
			return;
		}
		FluidStack refinedFluid = createFluid(sDrained, eDrained, lDrained);
		outputTank.fill(refinedFluid, true);
		
		staticInputTank.drain(staticFlowRate, true);
		energizedInputTank.drain(energizedFlowRate, true);
		lumumInputTank.drain(lumumFlowRate, true);
	}
	private void processExistingFluid() {
		FluidStack fluid = outputTank.getFluid();
		
		if(fluid == null || fluid.amount == 0) {
			return;
		}
		if(fluid.tag == null) {
			fluid.tag = new NBTTagCompound();
			fluid.tag.setFloat("Purity", 0.0f);
			fluid.tag.setFloat("Reactivity", 0.0f);
			fluid.tag.setFloat("Balance", 50.0f);	
		}
		float purityDirection = getTargetPurity() - fluid.tag.getFloat("Purity");
		float reactivityDirection = getTargetReactivity() - fluid.tag.getFloat("Reactivity");
		float balanceDirection = getTargetBalance() - fluid.tag.getFloat("Balance");
		
		purityDirection = Math.max(Math.min(purityDirection, 1), -1)/40.0f;
		reactivityDirection = Math.max(Math.min(reactivityDirection, 1), -1)/40.0f;
		balanceDirection = Math.max(Math.min(balanceDirection, 1), -1)/40.0f;
		
		fluid.tag.setFloat("Purity", fluid.tag.getFloat("Purity") + purityDirection);
		fluid.tag.setFloat("Reactivity", fluid.tag.getFloat("Reactivity") + reactivityDirection);
		fluid.tag.setFloat("Balance", fluid.tag.getFloat("Balance") + balanceDirection);	
		
		if(Math.abs(fluid.tag.getFloat("Purity") - getTargetPurity()) <= 1.0f) {
			fluid.tag.setFloat("Purity", getTargetPurity());
		}
		if(Math.abs(fluid.tag.getFloat("Reactivity") - getTargetReactivity()) <= 1.0f) {
			fluid.tag.setFloat("Reactivity", getTargetReactivity());
		}
		if(Math.abs(fluid.tag.getFloat("Balance") - getTargetBalance()) <= 1.0f) {
			fluid.tag.setFloat("Balance", getTargetBalance());
		}
	}
	private FluidStack createFluid(FluidStack staticFluid, FluidStack energizedFluid, FluidStack lumumFluid) {
		int amount = 0;
		if(staticFluid != null) {
			amount += staticFluid.amount;
		}
		if(energizedFluid != null) {
			amount += energizedFluid.amount;
		}
		if(lumumFluid != null) {
			amount += lumumFluid.amount;
		}
		
		FluidStack fluid = new FluidStack(ModFluids.RefinedFluid, amount);
		
		fluid.tag = new NBTTagCompound();
		fluid.tag.setFloat("Purity", getTargetPurity());
		fluid.tag.setFloat("Reactivity", getTargetReactivity());
		fluid.tag.setFloat("Balance", getTargetBalance());	

		return fluid;
	}
	
	public float getTargetPurity() {
		return 90.5f;
	}
	public float getTargetReactivity() {
		return 85.2f;
	}
	public float getTargetBalance() {
		return 50.0f;
	}
	
	@Override
	public String getName() {
		return "container.FluidRefineryController";
	}
	public FluidTank getStaticInputTank() {
		return staticInputTank;
	}
	public FluidTank getEnergizedInputTank() {
		return energizedInputTank;
	}
	public FluidTank getLumumInputTank() {
		return lumumInputTank;
	}
	public FluidTank getOutputTank() {
		return outputTank; 
	}
	
	public short getStaticFlowRate() {
		return staticFlowRate;
	}
	public short getEnergizedFlowRate() {
		return energizedFlowRate;
	}
	public short getLumumFlowRate() {
		return lumumFlowRate;
	}
	public short getPurifierFlowRate() {
		return purifierFlowRate;
	}
	public short getReagentFlowRate() {
		return reagentFlowRate;
	}
	public short getNutralizerFlowRate() {
		return nutralizerFlowRate;
	}
	public int getPurifierAmount() {
		return purifiersAmount;
	}
	public int getReagentAmount() {
		return reagentAmount;
	}
	public int getNutralizerAmount() {
		return nutralizerAmount;
	}
	public boolean getAllowStaticFlow() {
		return allowStaticFlow;
	}
	public boolean getAllowEnergizedFlow() {
		return allowEnergizedFlow;
	}
	public boolean getAllowLumumFlow() {
		return allowLumumFlow;
	}
	public boolean getAllowPurifierFlow() {
		return allowPurifierFlow;
	}
	public boolean getAllowReagentFlow() {
		return allowReagentFlow;
	}
	public boolean getAllowNutralizerFlow() {
		return allowNutralizerFlow;
	}
	
	public void setStaticFlowRate(short newFlowRate) {
		staticFlowRate = (short) Math.max(0, newFlowRate);
	}
	public void setEnergizedFlowRate(short newFlowRate) {
		energizedFlowRate = (short) Math.max(0, newFlowRate);
	}
	public void setLumumFlowRate(short newFlowRate) {
		lumumFlowRate = (short) Math.max(0, newFlowRate);
	}
	public void setPurifierFlowRate(short newFlowRate) {
		purifierFlowRate = (short) Math.max(0, newFlowRate);
	}
	public void setReagentFlowRate(short newFlowRate) {
		reagentFlowRate = (short) Math.max(0, newFlowRate);
	}
	public void setNutralizerFlowRate(short newFlowRate) {
		nutralizerFlowRate = (short) Math.max(0, newFlowRate);
	}
	
	public void setAllowStaticFlow(boolean newState) {
		allowStaticFlow = newState;
	}
	public void setAllowEnergizedFlow(boolean newState) {
		allowEnergizedFlow = newState;
	}
	public void setAllowLumumFlow(boolean newState) {
		allowLumumFlow = newState;
	}
	public void setAllowPurifierFlow(boolean newState) {
		allowPurifierFlow = newState;
	}
	public void setAllowReagentFlow(boolean newState) {
		allowReagentFlow = newState;
	}
	public void setAllowNutralizerFlow(boolean newState) {
		allowNutralizerFlow = newState;
	}
	
	public RefineryNetwork getNetwork() {
		return network;
	}
	private void updateGrid() {
		network = new RefineryNetwork(this);
		network.updateGrid();
	}
	@Override
	public void onPlaced(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updateGrid();
    }
	@Override 
	public void onBroken(){
		for(Entry<BlockPos, BaseRefineryTileEntity> entry : network.getMasterList().entrySet()) {
			entry.getValue().setController(null);
		}
	}
	
	@Override
	public void deserializeData(NBTTagCompound nbt) {
		super.deserializeData(nbt);
		
		staticInputTank.readFromNBT(nbt.getCompoundTag("staticInputTank"));
		energizedInputTank.readFromNBT(nbt.getCompoundTag("energizedInputTank"));
		lumumInputTank.readFromNBT(nbt.getCompoundTag("lumumInputTank"));
		
		outputTank.readFromNBT(nbt.getCompoundTag("outputTankTag"));	
		
    	allowStaticFlow = nbt.getBoolean("staticFlow");
		allowEnergizedFlow = nbt.getBoolean("energizedFlow");
		allowLumumFlow = nbt.getBoolean("lumumFlow");
		
		staticFlowRate = nbt.getShort("staticFlowRate");
		energizedFlowRate = nbt.getShort("energizedFlowRate");
		lumumFlowRate = nbt.getShort("lumumFlowRate");
		
		allowPurifierFlow = nbt.getBoolean("purifierFlow");
		allowReagentFlow = nbt.getBoolean("reagentFlow");
		allowNutralizerFlow = nbt.getBoolean("nutralizerFlow");
		
		purifierFlowRate = nbt.getShort("purifierFlowRate");
		reagentFlowRate = nbt.getShort("reagerFlowRate");
		nutralizerFlowRate = nbt.getShort("nutralizerFlowRate");
		
		purifiersAmount = nbt.getInteger("purifiersAmount");
		reagentAmount = nbt.getInteger("reagentAmount");
		nutralizerAmount = nbt.getInteger("nutralizerAmount");
	}
	@Override
	public NBTTagCompound serializeData(NBTTagCompound nbt) {
		super.serializeData(nbt);
		
		NBTTagCompound staticInputTankTag = new NBTTagCompound(); 
		staticInputTank.writeToNBT(staticInputTankTag);
		nbt.setTag("staticInputTank", staticInputTankTag);
		
		NBTTagCompound energizedInputTankTag = new NBTTagCompound(); 
		energizedInputTank.writeToNBT(energizedInputTankTag);
		nbt.setTag("energizedInputTank", energizedInputTankTag);
		
		NBTTagCompound lumumInputTankTag = new NBTTagCompound(); 
		lumumInputTank.writeToNBT(lumumInputTankTag);
		nbt.setTag("lumumInputTank", lumumInputTankTag);
		
		NBTTagCompound outputTankTag = new NBTTagCompound(); 
		outputTank.writeToNBT(outputTankTag);
		nbt.setTag("outputTankTag", outputTankTag);
		
		nbt.setBoolean("staticFlow", allowStaticFlow);
		nbt.setBoolean("energizedFlow", allowEnergizedFlow);
		nbt.setBoolean("lumumFlow", allowLumumFlow);
		
		nbt.setShort("staticFlowRate", staticFlowRate);
		nbt.setShort("energizedFlowRate", energizedFlowRate);
		nbt.setShort("lumumFlowRate", lumumFlowRate);
		
		nbt.setBoolean("purifierFlow", allowPurifierFlow);
		nbt.setBoolean("reagentFlow", allowReagentFlow);
		nbt.setBoolean("nutralizerFlow", allowNutralizerFlow);
		
		nbt.setShort("purifierFlowRate", purifierFlowRate);
		nbt.setShort("reagerFlowRate", reagentFlowRate);
		nbt.setShort("nutralizerFlowRate", nutralizerFlowRate);
		
		nbt.setInteger("purifiersAmount", purifiersAmount);
		nbt.setInteger("reagentAmount", reagentAmount);
		nbt.setInteger("nutralizerAmount", nutralizerAmount);
		
		return nbt;
	}
	
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {
				@Override
				public IFluidTankProperties[] getTankProperties() {
					return outputTank.getTankProperties();
				}
				@Override
				public int fill(FluidStack resource, boolean doFill) {
					if(resource.getFluid() == ModFluids.StaticFluid) {
						return staticInputTank.fill(resource, doFill);
					}else if(resource.getFluid() == ModFluids.EnergizedFluid) {
						return energizedInputTank.fill(resource, doFill);
					}else if(resource.getFluid() == ModFluids.LumumFluid) {
						return lumumInputTank.fill(resource, doFill);
					}
					return 0;
				}
				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {
					return outputTank.drain(resource, doDrain);
				}
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {
					return outputTank.drain(maxDrain, doDrain);
				}			
    		});
    	}
    	return super.getCapability(capability, facing);
    }
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(capability==CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
}