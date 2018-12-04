package theking530.staticpower.tileentity.digistorenetwork.networkwire;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreBlock;

public class BlockDigistoreNetworkWire extends BaseDigistoreBlock {

	public BlockDigistoreNetworkWire(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}
	public boolean isFullCube(IBlockState state) {
		return true;		
	}
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	this.shouldDropContents = false;
        super.breakBlock(worldIn, pos, state);    
    }
    @Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null; //new TileEntityDigistoreWire();
	}
    @Override
    public boolean hasTileEntity(IBlockState state) {
		return false;    	
    }
}
