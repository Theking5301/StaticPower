package theking530.staticpower.tileentity.digistorenetwork.manager;

import java.util.Map.Entry;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreTileEntity;
import theking530.staticpower.tileentity.digistorenetwork.DigistoreNetwork;
import theking530.staticpower.tileentity.digistorenetwork.digistore.TileEntityDigistore;

public class TileEntityDigistoreManager extends BaseDigistoreTileEntity {
	
	private DigistoreNetwork network;
	
	public TileEntityDigistoreManager() {

	}

	@Override
	public void process() {
		updateGrid();
	}
	public DigistoreNetwork getNetwork() {
		return network;
	}
	
	private void updateGrid() {
		network = new DigistoreNetwork(this);
		network.updateGrid();
	}
	@Override
	public void onPlaced(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updateGrid();
    }
	@Override 
	public void onBroken(){
		for(Entry<BlockPos, BaseDigistoreTileEntity> entry : network.getMasterList().entrySet()) {
			entry.getValue().setManager(null);
		}
	}
	
	@Override
	public boolean isSideConfigurable() {
		return false;
	}
	
	/*Capability Handling*/
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(facing == null) {
    		return false;
    	}
    	if(getSideConfiguration(facing) == Mode.Disabled) {
    		return false;
    	}
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(facing == null) {
    		return null;
    	}
       	if(getSideConfiguration(facing) == Mode.Disabled) {
    		return null;
    	}
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new IItemHandler() {
				public int getSlots() {
			    	return evauluateRedstoneSettings() ? network.getDigistoreList().size() : 0;
			    }
			    @Nonnull
			    public ItemStack getStackInSlot(int slot) {	
			    	return network.getDigistoreList().get(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(slot);
			    }
			    @Nonnull
			    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			    	for(TileEntityDigistore digistore : network.getDigistoreList()) {
			    		if(ItemHandlerHelper.canItemStacksStack(digistore.getStoredItem(), stack) && !digistore.isFull()) {
				    		return digistore.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).insertItem(slot, stack, simulate);
			    		}
			    	}
			    	return network.getDigistoreList().get(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).insertItem(slot, stack, simulate);
			    }
			    @Nonnull
			    public ItemStack extractItem(int slot, int amount, boolean simulate) {  		
			    	return network.getDigistoreList().get(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).extractItem(slot, amount, simulate);  	
			    }
			    public int getSlotLimit(int slot) {	
			    	return network.getDigistoreList().get(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getSlotLimit(slot);
			    }
			});
    	}
    	return super.getCapability(capability, facing);
    }
}
