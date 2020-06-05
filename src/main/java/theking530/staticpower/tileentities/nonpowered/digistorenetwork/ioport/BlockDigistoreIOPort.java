package theking530.staticpower.tileentity.digistorenetwork.ioport;

import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreBlock;

public class BlockDigistoreIOPort extends BaseDigistoreBlock {

	public BlockDigistoreIOPort(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}	
	public EnumBlockRenderType getRenderType(BlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}
	public boolean isFullCube(BlockState state) {
		return false;		
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, EnumHand hand, Direction facing, float hitX, float hitY, float hitZ) {
    	if (world.isRemote) {
    		return true;
    	}
		return false;
	}
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
    	shouldDropContents = false;
        super.breakBlock(worldIn, pos, state);
    }
	@Override	
	public TileEntity createTileEntity(World world, BlockState state){
		return new TileEntityDigistoreIOPort();
	}
}
