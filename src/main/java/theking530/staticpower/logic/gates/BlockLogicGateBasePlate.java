package theking530.staticpower.logic.gates;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import theking530.staticpower.blocks.BaseBlock;

public class BlockLogicGateBasePlate extends BaseBlock {

	static float PIXEL = 1F/16F;
    private static final AxisAlignedBB AA_BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2*PIXEL, 1.0D);
	
	public BlockLogicGateBasePlate(Material materialIn, String name) {
		super(Material.IRON, "LogicGateBasePlate");

	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
    public boolean isFullCube(IBlockState state){
        return false;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AA_BB;
    }
}
