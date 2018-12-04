package theking530.staticpower.tileentity.digistorenetwork.manager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreBlock;

public class BlockDigistoreManager extends BaseDigistoreBlock {

	public BlockDigistoreManager(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	public boolean isFullCube(IBlockState state) {
		return false;		
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (world.isRemote) {
    		return true;
    	}else if (!player.isSneaking()) {
    		TileEntityDigistoreManager entity = (TileEntityDigistoreManager) world.getTileEntity(pos);
    		if (entity != null) {
    			//FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDBarrel, world, pos.getX(), pos.getY(), pos.getZ());
    		}
    	}
		return false;
	}

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	shouldDropContents = false;
        super.breakBlock(worldIn, pos, state);
    }
	@Override	
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityDigistoreManager();
	}
}
