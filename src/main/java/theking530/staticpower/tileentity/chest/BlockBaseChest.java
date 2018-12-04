package theking530.staticpower.tileentity.chest;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import theking530.staticpower.machines.BlockMachineBase;

public class BlockBaseChest extends BlockMachineBase{

	//private final Random rand = new Random();
    private static final AxisAlignedBB AA_BB = new AxisAlignedBB(0.05D, 0.0D, 0.05D, 0.95D, 0.9D, 0.95D);
	public BlockBaseChest(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AA_BB;
    }
    public boolean isFullCube(IBlockState state){
        return false;
    }
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
    public int getRenderType() {
        return -1;
    }
}
