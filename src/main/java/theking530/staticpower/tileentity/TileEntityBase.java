package theking530.staticpower.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.INameable;
import theking530.staticpower.assists.utilities.RedstoneModeList.RedstoneMode;
import theking530.staticpower.assists.utilities.SideModeList;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.assists.utilities.SideUtilities.BlockSide;
import theking530.staticpower.machines.tileentitycomponents.ITileEntityComponent;

public class TileEntityBase extends TileEntity implements ITickable, IRedstoneConfigurable, ISideConfigurable, IUpgradeable, INameable, IBreakSerializeable {

	public static final int DEFAULT_UPDATE_TIME = 2;
	
	public TileEntityInventory  slotsInput;
	public TileEntityInventory  slotsOutput;
	public TileEntityInventory  slotsInternal;
	public TileEntityInventory  slotsUpgrades;
	
	private List<ITileEntityComponent> components;	
	private RedstoneMode redstoneMode = RedstoneMode.Ignore;
	private SideConfiguration ioSideConfiguration;

	@SuppressWarnings("unused")
	private int updateTimer = 0;
	@SuppressWarnings("unused")
	private int updateTime = DEFAULT_UPDATE_TIME;
	
	private int internalSlotsCount;
	private int inputSlotsCount;
	private int outputSlotsCount;
	
	private boolean updateQueued;
	protected boolean disableFaceInteraction;
	
	public boolean wasWrenchedDoNotBreak;
	public boolean wasPlaced;
	
	private String name;
	
	public TileEntityBase() {
		ioSideConfiguration = new SideConfiguration();
		components = new ArrayList<ITileEntityComponent>();
		wasPlaced = false;
		wasWrenchedDoNotBreak = false;
		updateQueued = false;
	}
	public void initializeSlots(int internalSlots, int inputSlots, int outputSlots, boolean disableFaceInteraction) {
		slotsInput = new TileEntityInventory(inputSlots);
		slotsOutput = new TileEntityInventory(outputSlots);
		slotsInternal = new TileEntityInventory(internalSlots);
		slotsUpgrades = new TileEntityInventory(3);
		
		internalSlotsCount = internalSlots;
		inputSlotsCount = inputSlots;
		outputSlotsCount = outputSlots;
		
		this.disableFaceInteraction = disableFaceInteraction;
	}
	public void initializeSlots(int internalSlots, int inputSlots, int outputSlots) {
		initializeSlots(internalSlots, inputSlots, outputSlots, true);
	}
	
	@Override
	public void update() {
		if(isUpgradeable()) {
			upgradeTick();	
		}
		if(evauluateRedstoneSettings()) {
			preProcessUpdateComponents();
			process();
			postProcessUpdateComponents();
		}
//		if(updateTimer < updateTime) {
//			updateTimer++;
//		}else{
			if(updateQueued) {
				if(!getWorld().isRemote) {
					getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 2);	
				}
			}
			markDirty();
			updateTimer = 0;
//		}
		if(!wasPlaced) {
			onPlaced();
			wasPlaced = true;
		}
	}		
	public void process() {}
	public void upgradeTick() {
		
	}
	public void updateBlock() {
		updateQueued = true;
	}
	
	public void onPlaced() {
		if(isSideConfigurable()) {
			if(disableFaceInteraction) {
				setDefaultSideConfiguration(ioSideConfiguration);
			}else{
				setSideConfiguration(Mode.Disabled, BlockSide.FRONT);
			}

		}
	}
	
	public ItemStack getInputStack(int slot) {
		if(slot < slotsInput.getSlots()) {
			return slotsInput.getStackInSlot(slot);	
		}else{
			return ItemStack.EMPTY;
		}
	}
	public ItemStack getOutputStack(int slot) {
		if(slot < slotsOutput.getSlots()) {
			return slotsOutput.getStackInSlot(slot);	
		}else{
			return ItemStack.EMPTY;
		}
	}
	public ItemStack getInternalStack(int slot) {
		if(slot < slotsInternal.getSlots()) {
			return slotsInternal.getStackInSlot(slot);	
		}else{
			return ItemStack.EMPTY;
		}
	}
	
	public void setInternalStack(int slot, ItemStack stack) {
		slotsInternal.setStackInSlot(slot, stack);
	}

	@Override  
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        deserializeData(nbt);
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        return serializeData(nbt);
	}
	
    public void deserializeData(NBTTagCompound nbt) {
        if(this.isRedstoneControllable()) {
        	redstoneMode = RedstoneMode.getModeFromInt(nbt.getShort("REDSTONE_MODE"));
        }
		if(isSideConfigurable()) {
	        for(int i=0; i<6; i++) {
	        	setSideConfiguration(SideModeList.Mode.values()[nbt.getInteger("SIDEMODE" + i)], EnumFacing.values()[i]);
	        }    
	        disableFaceInteraction = nbt.getBoolean("DISABLE_FACE");
		}
        if(slotsInput != null && slotsInput.getSlots() > 0 && nbt.hasKey("INPUTS")) {
        	slotsInput.deserializeNBT((NBTTagCompound) nbt.getTag("INPUTS"));	
        	if(slotsInput.getSlots() != inputSlotsCount) {
        		slotsInput.setSize(inputSlotsCount);
        	}
        }
        if(slotsOutput != null && slotsOutput.getSlots() > 0 && nbt.hasKey("OUTPUTS")) {
            slotsOutput.deserializeNBT((NBTTagCompound) nbt.getTag("OUTPUTS"));
        	if(slotsOutput.getSlots() != outputSlotsCount) {
        		slotsOutput.setSize(outputSlotsCount);
        	}
        }
        if(slotsInternal != null && slotsInternal.getSlots() > 0 && nbt.hasKey("INTERNAL")) {
            slotsInternal.deserializeNBT((NBTTagCompound) nbt.getTag("INTERNAL"));
        	if(slotsInternal.getSlots() != internalSlotsCount) {
        		slotsInternal.setSize(internalSlotsCount);
        	}
        }
        if(slotsUpgrades != null && slotsUpgrades.getSlots() > 0 && nbt.hasKey("UPGRADES")) {
            slotsUpgrades.deserializeNBT((NBTTagCompound) nbt.getTag("UPGRADES"));
        }
        if(nbt.hasKey("placed")) {
        	wasPlaced = nbt.getBoolean("placed");
        }
    }
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        if(this.isRedstoneControllable()) {
    		nbt.setShort("REDSTONE_MODE", (short)redstoneMode.ordinal());	
        }
		if(isSideConfigurable()) {
	        for(int i=0; i<6; i++) {
	        	nbt.setInteger("SIDEMODE" + i, getSideConfiguration(EnumFacing.values()[i]).ordinal());
	        }
	        nbt.setBoolean("DISABLE_FACE", disableFaceInteraction);
		}

        if(slotsInput != null && slotsInput.getSlots() > 0) {
        	nbt.setTag("INPUTS", slotsInput.serializeNBT());	
        }
        if(slotsOutput != null && slotsOutput.getSlots() > 0) {
        	nbt.setTag("OUTPUTS", slotsOutput.serializeNBT());
        }
        if(slotsInternal != null && slotsInternal.getSlots() > 0) {
        	nbt.setTag("INTERNAL", slotsInternal.serializeNBT());
        }
        if(slotsUpgrades != null && slotsUpgrades.getSlots() > 0) {
        	nbt.setTag("UPGRADES", slotsUpgrades.serializeNBT());
        }
        nbt.setBoolean("placed", wasPlaced);
		return nbt;	
    }
    
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), getBlockMetadata(), this.getUpdateTag());
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (getWorld().getTileEntity(pos) != this) {
			return false;
		}else{
			return player.getDistanceSq((double)pos.getX() + 0.25D, (double)pos.getY() + 0.25D, (double)pos.getZ() + 0.25D) <= Math.pow(getBlockReachDistance(player), 2);
		}
	}
	public static double getBlockReachDistance(EntityPlayer player) {

		return player.world.isRemote ? getBlockReachDistanceClient() : player instanceof EntityPlayerMP ? getBlockReachDistanceServer((EntityPlayerMP) player) : 5D;
	}
	private static double getBlockReachDistanceServer(EntityPlayerMP player) {

		return player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
	}
	@SideOnly (Side.CLIENT)
	private static double getBlockReachDistanceClient() {

		return Minecraft.getMinecraft().playerController.getBlockReachDistance();
	}
	
	public void transferItemInternally(ItemStackHandler fromInv, int fromSlot, ItemStackHandler toInv, int toSlot) {
		toInv.insertItem(toSlot, fromInv.extractItem(fromSlot, 1, false), false);
	}
    public EnumFacing getFacingDirection() {
    	if(getWorld().getBlockState(pos).getProperties().containsKey(BlockHorizontal.FACING)) {
        	return getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
    	}else{
        	return EnumFacing.UP;
    	}
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
    
    /*Nameable*/
	@Override
    public String getName(){
    	return name;
    }
	public void setName(String name) {
		this.name = name;
	}
    
    /*Serializeable*/
    public NBTTagCompound serializeOnBroken(NBTTagCompound nbt) {
    	serializeData(nbt);
    	return nbt;
	}
	public void deserializeOnPlaced(NBTTagCompound nbt, World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		deserializeData(nbt);
	}	
	@Override
	public boolean shouldSerializeWhenBroken() {
		return true;
	}
	@Override
	public boolean shouldDeserializeWhenPlace(NBTTagCompound nbt, World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		return true;
	}
    
	/*Capability Handling*/
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
    	if(facing != null && getSideConfiguration(facing) == Mode.Disabled) {
    		return false;
    	}
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && SideUtilities.getBlockSide(facing, getFacingDirection()) != BlockSide.FRONT) {
    		Mode sideMode = facing == null ? Mode.Regular : getSideConfiguration(facing);	
    		if(sideMode == Mode.Regular) {
    			return slotsOutput != null || slotsInput != null;
    		}else if(sideMode.isOutputMode()) {
        		return slotsOutput != null;
    		}else if(sideMode.isInputMode()) {
        		return slotsInput != null;
    		}
    	}
        return super.hasCapability(capability, facing);
    }
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
       	if(getSideConfiguration(facing) == Mode.Disabled || (SideUtilities.getBlockSide(facing, getFacingDirection()) == BlockSide.FRONT && this.disableFaceInteraction)) {
    		return null;
    	}
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {		
    		Mode sideMode = facing == null ? Mode.Regular : getSideConfiguration(facing);		
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new IItemHandler() {
				TileEntityInventory handler = sideMode.isInputMode() ? slotsInput : slotsOutput;
				public int getSlots() {
					if(sideMode == Mode.Regular) {
						return slotsInput.getSlots() + slotsOutput.getSlots();
					}
			    	return handler.getSlots();
			    }
			    @Nonnull
			    public ItemStack getStackInSlot(int slot) {
					if(sideMode == Mode.Regular) {
						return slot <= slotsInput.getSlots()-1 ? slotsInput.getStackInSlot(slot) : slotsOutput.getStackInSlot(slot-slotsInput.getSlots());
					}
			    	return handler.getStackInSlot(slot);
			    }
			    @Nonnull
			    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {	
					if(sideMode == Mode.Regular) {
						return slot <= slotsInput.getSlots()-1 ? slotsInput.insertItem(slot, stack, simulate) : slotsOutput.insertItem(slot-slotsInput.getSlots(), stack, simulate);
					}
			    	return handler.insertItemToSide(sideMode, slot, stack, simulate);
			    }
			    @Nonnull
			    public ItemStack extractItem(int slot, int amount, boolean simulate) {
					if(sideMode == Mode.Regular) {
						return slot <= slotsInput.getSlots()-1 ? slotsInput.extractItem(slot, amount, simulate) : slotsOutput.extractItem(slot-slotsInput.getSlots(), amount, simulate);
					}
			    	return handler.extractItem(slot, amount, simulate);
			    }
			    public int getSlotLimit(int slot) {
					if(sideMode == Mode.Regular) {
						return slot <= slotsInput.getSlots()-1 ? slotsInput.getSlotLimit(slot) : slotsOutput.getSlotLimit(slot-slotsInput.getSlots());
					}
			    	return handler.getSlotLimit(slot);
			    }
			});
    	}
    	return super.getCapability(capability, facing);
    }
    
	/*Upgrade Handling*/
	public boolean hasUpgrade(Item upgradeBase) {
		return !getUpgrade(upgradeBase).isEmpty();
	}
	@Override
	public boolean hasUpgrade(ItemStack upgrade) {
		return !getUpgrade(upgrade).isEmpty();
	}
	@Override
	public ItemStack getUpgrade(ItemStack upgrade) {
		for(int i=0; i<slotsUpgrades.getSlots(); i++) {
			if(ItemHandlerHelper.canItemStacksStack(upgrade, slotsUpgrades.getStackInSlot(i))) {
				return slotsUpgrades.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	public ItemStack getUpgrade(Item upgradeBase) {
		for(int i=0; i<slotsUpgrades.getSlots(); i++) {
			if(!slotsUpgrades.getStackInSlot(i).isEmpty() && slotsUpgrades.getStackInSlot(i).getItem() == upgradeBase) {
				return slotsUpgrades.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	@Override
	public List<ItemStack> getAllUpgrades() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack upgrade = ItemStack.EMPTY;
		int slot = -1;
		for(int i=0; i<slotsUpgrades.getSlots(); i++) {
			slot = i;
			upgrade = slotsUpgrades.getStackInSlot(slot);
			if(!upgrade.isEmpty()) {
				list.add(upgrade);
			}
		}
		return list;
	}
	@Override
	public boolean canAcceptUpgrade(ItemStack upgrade) {
		return true;
	}
	@Override
	public boolean isUpgradeable() {
		return true;
	}
	
	/*Components*/
	public void registerComponent(ITileEntityComponent component) {
		components.add(component);
	}
	public boolean removeComponents(ITileEntityComponent component) {
		return components.remove(component);
	}
	public List<ITileEntityComponent> getComponents() {
		return components;
	}
	private void preProcessUpdateComponents() {
		try{
			for(int i=0; i<components.size(); i++) {
				components.get(i).preProcessUpdate();
			}
		}catch(Exception e) {
			StaticPower.LOGGER.warn(this.getName() + " at position " + getPos() + ": " + e.getMessage());
		}
	}
	private void postProcessUpdateComponents() {
		try{
			for(int i=0; i<components.size(); i++) {
				components.get(i).postProcessUpdate();
			}	
		}catch(Exception e) {
			StaticPower.LOGGER.warn(this.getName() + " at position " + getPos() + ": " + e.getMessage());
		}
	}
	
	/*Redstone Control*/
	@Override
	public boolean isRedstoneControllable() {
		return true;
	}
	@Override
    public RedstoneMode getRedstoneMode() {
    	return redstoneMode;
    } 
	@Override
	public void setRedstoneMode(RedstoneMode newMode) {
		redstoneMode = newMode;
	}
	public boolean evauluateRedstoneSettings() {
		int redstoneSignal = getWorld().getStrongPower(pos);
		if(getRedstoneMode() == RedstoneMode.Ignore) {
			return true;
		}
		if(getRedstoneMode() == RedstoneMode.Low) {
			if(redstoneSignal <= 0) {
				return true;	
			}
		}
		if(getRedstoneMode() == RedstoneMode.High) {
			if(redstoneSignal > 0) {
				return true;	
			}
		}
		return false;
	}
	
	/*Side Control*/
	@Override
	public boolean isSideConfigurable() {
		return true;
	}
	@Override
	public Mode getSideConfiguration(BlockSide side) {
		EnumFacing facing = SideUtilities.getEnumFacingFromSide(side, getFacingDirection());
		return getSideConfiguration(facing);
	}
	@Override
	public Mode getSideConfiguration(EnumFacing facing) {
		return ioSideConfiguration.getSideConfiguration(facing);
	}
	@Override
	public Mode[] getSideConfigurations() {
		return ioSideConfiguration.getConfiguration();
	}
	@Override
	public void setSideConfiguration(Mode newMode, BlockSide side) {
		EnumFacing facing = SideUtilities.getEnumFacingFromSide(side, getFacingDirection());
		setSideConfiguration(newMode, facing);
	}
	@Override
	public void setSideConfiguration(Mode newMode, EnumFacing facing) {
		ioSideConfiguration.setSideConfiguration(newMode, facing);
	}
	public void onSidesConfigUpdate() {
		
	}
	public void resetSideConfiguration(){
		ioSideConfiguration.reset();
		onSidesConfigUpdate();
	}
	@Override
	public void incrementSideConfiguration(EnumFacing side, SideIncrementDirection direction){
		BlockSide blockSideEquiv = SideUtilities.getBlockSide(side, getFacingDirection());
		if(blockSideEquiv == BlockSide.FRONT && disableFaceInteraction) {
			setSideConfiguration(SideModeList.Mode.Disabled, side);
		}
		
		int newIndex = 0;
		do {
			SideModeList.Mode currentSideMode = getSideConfiguration(side);
			newIndex = direction == SideIncrementDirection.FORWARD ? currentSideMode.ordinal() + 1 : currentSideMode.ordinal() - 1;
			if(newIndex < 0) {
				newIndex += Mode.values().length;
			}
			newIndex %= Mode.values().length;
			setSideConfiguration(SideModeList.Mode.values()[newIndex], side);
		}while(!getValidSideConfigurations().contains(SideModeList.Mode.values()[newIndex]));

		onSidesConfigUpdate();
		updateBlock();
	}
	@Override
	public int getSideWithModeCount(Mode mode) {
		int count = 0;
		for(Mode sideMode : getSideConfigurations()) {
			if(sideMode == mode) {
				count++;
			}
		}
		return count;
	}
	public void setDefaultSideConfiguration(SideConfiguration configuration) {
		configuration.setToDefault();
		if(disableFaceInteraction) {
			setSideConfiguration(Mode.Disabled, BlockSide.FRONT);
		}
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Output);
		modes.add(Mode.Regular);
		modes.add(Mode.Disabled);
		return modes;
	}
}