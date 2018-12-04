package theking530.staticpower.machines.refinery;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.machines.refinery.controller.TileEntityFluidRefineryController;
import theking530.staticpower.tileentity.TileEntityBase;

public class BaseRefineryTileEntity extends TileEntityBase{

	private TileEntityFluidRefineryController controller;
	
	public void setController(TileEntityFluidRefineryController newController) {
		controller = newController;
	}
	public TileEntityFluidRefineryController getContorller() {
		return controller;
	}
	public boolean hasController() {
		return getContorller() != null;
	}
	
	public void onBroken() {

	}
	public void onPlaced(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

    }
	public void onNeighborUpdated(IBlockState observerState, World world, Block oldBlock, BlockPos changedBlockPos, Block newBlock) {
	}
}
