package theking530.staticpower.machines;

import cofh.redstoneflux.api.IEnergyContainerItem;
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.energy.StaticEnergyStorage;
import theking530.staticpower.items.upgrades.BasePowerUpgrade;
import theking530.staticpower.items.upgrades.BaseSpeedUpgrade;
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.IEnergyUser;
import theking530.staticpower.tileentity.IProcessing;

/**
 * @author Amine
 *
 */
public class TileEntityMachine extends TileEntityBase implements IEnergyHandler, IEnergyReceiver, IEnergyProvider, IProcessing, IEnergyUser {
	
	public static final int DEFAULT_RF_CAPACITY = 100000;
	
	public int initialEnergyPerTick;
	public int initialEnergyCapacity;
	public StaticEnergyStorage energyStorage;
	
	public int initialPowerUse = 100;
	public float initialProcessingEnergyMult;
	public float processingEnergyMult = initialProcessingEnergyMult;
	public int initialProcessingTime;
	public int processingTime = initialProcessingTime;
	
	public int moveSpeed = 4;
	
	public int processingTimer = 0;
	public int moveTimer = 0;
	
	public int batterySlot = -1;
	public int previouslyStoredEnergyAmount = 0;
	public int currentEnergyPerTick = 0;
	
	public boolean isUpdateQueued = true;
	

	public TileEntityMachine() {
		initializeSlots(0, 0, 0, false);
	}
	
	/**
	 * @param InitialEnergyMult
	 * @param InitialPowerUse
	 * @param InitialEnergyCapacity
	 * @param InitialEntryPerTick
	 * @param InitialProcessingTime
	 */
	public void initializeBasicMachine(float InitialEnergyMult, int InitialPowerUse, int InitialEnergyCapacity, int InitialEntryPerTick, int InitialProcessingTime) {	
		initialProcessingEnergyMult = InitialEnergyMult;
		initialEnergyCapacity = InitialEnergyCapacity;
		initialEnergyPerTick = InitialEntryPerTick;
		initialProcessingTime = InitialProcessingTime;
		initialPowerUse = InitialPowerUse;
		
		energyStorage = new StaticEnergyStorage(InitialEnergyCapacity);
		energyStorage.setMaxReceive(InitialEntryPerTick);
		energyStorage.setMaxExtract(InitialEntryPerTick);
		energyStorage.setCapacity(InitialEnergyCapacity);	
		
		registerComponent(energyStorage);
	}
	@Override
	public void update(){
		currentEnergyPerTick = energyStorage.getEnergyStored() - previouslyStoredEnergyAmount;

		super.update();

		previouslyStoredEnergyAmount = energyStorage.getEnergyStored();
	}	
	@Override
	public void upgradeTick(){
		powerUpgrade();		
		processingUpgrade();
	}
	public void powerUpgrade() {
		boolean flag = false;
		int slot = 0;
		for(int i=0; i<3; i++) {
			if(slotsUpgrades.getStackInSlot(i) != null) {
				if(slotsUpgrades.getStackInSlot(i).getItem() instanceof BasePowerUpgrade) {
					flag = true;
					slot = i;
				}
			}
		}
		if(flag) {
			BasePowerUpgrade tempUpgrade = (BasePowerUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			energyStorage.setCapacity((int)(tempUpgrade.getValueMultiplied(initialEnergyCapacity, tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0))));
			energyStorage.setMaxReceive((int)(tempUpgrade.getValueMultiplied(initialEnergyPerTick, tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 1))));
		}else{
			energyStorage.setCapacity(initialEnergyCapacity);
			energyStorage.setMaxReceive(initialEnergyPerTick);
		}
	}
	public void processingUpgrade() {
		boolean flag = false;
		int slot = 0;
		for(int i=0; i<3; i++) {
			if(slotsUpgrades.getStackInSlot(i) != null) {
				if(slotsUpgrades.getStackInSlot(i).getItem() instanceof BaseSpeedUpgrade) {
					flag = true;
					slot = i;
				}
			}
		}
		if(flag) {
			BaseSpeedUpgrade tempUpgrade = (BaseSpeedUpgrade) slotsUpgrades.getStackInSlot(slot).getItem();
			processingTime = Math.max(1, (int) (initialProcessingTime/(1+(tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 0)))));
			processingEnergyMult = (int)(tempUpgrade.getValueMultiplied(initialProcessingEnergyMult, tempUpgrade.getUpgradeValueAtIndex(slotsUpgrades.getStackInSlot(slot), 1)));
		}else{
			processingEnergyMult = initialProcessingEnergyMult;
			processingTime = initialProcessingTime;
		}
	}

	public void setBatterySlot(int slot) {
		batterySlot = slot;
	}
	public void useBattery() {
		if(slotsInput.getStackInSlot(batterySlot) != ItemStack.EMPTY && slotsInput.getStackInSlot(batterySlot).getItem() instanceof IEnergyContainerItem && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
			IEnergyContainerItem batteryItem = (IEnergyContainerItem) slotsInput.getStackInSlot(batterySlot).getItem();
			if(batteryItem.getEnergyStored(slotsInput.getStackInSlot(batterySlot)) > 0) {
				int maxExtract = batteryItem.extractEnergy(slotsInput.getStackInSlot(batterySlot), energyStorage.getMaxReceive(), false);
				int recieved = energyStorage.receiveEnergy(maxExtract, false);
				batteryItem.extractEnergy(slotsInput.getStackInSlot(batterySlot), recieved, true);
			}
		}
	}
    @Override  
	public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        energyStorage.readFromNBT(nbt);
        processingTimer = nbt.getInteger("P_TIMER");
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
        energyStorage.writeToNBT(nbt);
        nbt.setInteger("P_TIMER", processingTimer);
        return nbt;
	}
    
	public void deserializeOnPlaced(NBTTagCompound nbt, World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.deserializeOnPlaced(nbt, world, pos, state, placer, stack);
		this.deserializeData(nbt);
		previouslyStoredEnergyAmount = energyStorage.getEnergyStored();
		updateBlock();
	}
	
	/* PROCESSING */ 
	public int getProcessingEnergy() {
		return (int) (initialPowerUse*processingEnergyMult);
	}
	@Override
	public boolean hasValidRecipe() {
		return false;
	}
	@Override
	public boolean canProcess() {
		return hasValidRecipe();
	}
	@Override
	public boolean isProcessing() {
		return processingTimer > 0 && evauluateRedstoneSettings();
	}
	@Override
	public int getProcessingTime() {
		return processingTime;
	}
	@Override
	public int getCurrentProgress() {
		return processingTimer;
	}
	@Override
	public float getProcessingPercentage() {
		return (float)processingTimer / (float)processingTime;
	}	
	
	
	public boolean isMoving() {
		return moveTimer > 0;
	}	
	public int getProgressScaled(int i) {
		return (processingTimer * i) / Math.max(processingTime, 1);
	}

	/* ENERGY */
	public float getEnergyPercent() {
		float amount = energyStorage.getEnergyStored();
		float capacity = energyStorage.getMaxEnergyStored();
		float volume = (amount/capacity);		
			return volume;	
	}	
	public boolean hasPower(){
		return energyStorage.getEnergyStored() > 0 ? true : false;
	}
	public int useEnergy(int energyCost) {
		return extractEnergy(null, energyCost, false);
	}
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}
	@Override
	public int getEnergyStored(EnumFacing from) {
		return energyStorage.getEnergyStored();
	}
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return energyStorage.getMaxEnergyStored();
	}
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		updateBlock();
		return energyStorage.extractEnergy(maxExtract, simulate);
	}
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		updateBlock();
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}
	
	/* ENERGY AND PROCESSING */
	@Override
	public boolean isUsingEnergy() {
		return isProcessing();
	}
	@Override
	public int maxEnergyUsagePerTick() {
		return getProcessingEnergy()/Math.max(1, getProcessingTime());
	}
	@Override
	public StaticEnergyStorage getEnergyStorage() {
		return energyStorage;
	}
	@Override
	public int getCurrentEnergyIO() {
		return currentEnergyPerTick;
	}
	
	/* CAPABILITIES */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing from) {
		if(capability == CapabilityEnergy.ENERGY && energyStorage != null) {
			if(from != null && getSideConfiguration(from) == Mode.Disabled) {
				return false;
			}
			return true;
		}
		return super.hasCapability(capability, from);
	}
	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing from) {
		if (capability == CapabilityEnergy.ENERGY) {
			if(energyStorage != null) {
				if(from != null && this.getSideConfiguration(from) == Mode.Disabled) {
					return null;
				}
				return CapabilityEnergy.ENERGY.cast(new net.minecraftforge.energy.IEnergyStorage() {

					@Override
					public int receiveEnergy(int maxReceive, boolean simulate) {

						return TileEntityMachine.this.receiveEnergy(from, maxReceive, simulate);
					}

					@Override
					public int extractEnergy(int maxExtract, boolean simulate) {

						return TileEntityMachine.this.extractEnergy(from, maxExtract, simulate);
					}

					@Override
					public int getEnergyStored() {

						return TileEntityMachine.this.getEnergyStored(from);
					}

					@Override
					public int getMaxEnergyStored() {

						return TileEntityMachine.this.getMaxEnergyStored(from);
					}

					@Override
					public boolean canExtract() {

						return false;
					}

					@Override
					public boolean canReceive() {

						return true;
					}
				});
			}
		}
		return super.getCapability(capability, from);
	}
}
