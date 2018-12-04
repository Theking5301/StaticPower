package theking530.staticpower.tileentity.digistorenetwork;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.machines.BlockMachineBase;

public abstract class BaseDigistoreBlock extends BlockMachineBase {

	public BaseDigistoreBlock(String name) {
		super(name);
	}
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}
	public boolean isFullCube(IBlockState state) {
		return true;		
	}
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof BaseDigistoreTileEntity) {
    		((BaseDigistoreTileEntity)world.getTileEntity(pos)).onPlaced(world, pos, state, placer, stack);
    	}
    }
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	this.shouldDropContents = false;
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof BaseDigistoreTileEntity) {
    		((BaseDigistoreTileEntity)world.getTileEntity(pos)).onBroken();
    	}
    }
	@Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos){
		super.onNeighborChange(world, observerPos, changedBlockPos);
    	if(world.getTileEntity(observerPos) != null && world.getTileEntity(observerPos) instanceof BaseDigistoreTileEntity) {
    		((BaseDigistoreTileEntity)world.getTileEntity(observerPos)).onNeighborUpdated(observerState, world, changedBlock, changedBlockPos, world.getBlockState(changedBlockPos).getBlock());
    	}
    }
	
}
