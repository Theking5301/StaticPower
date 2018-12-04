package theking530.staticpower.machines.refinery;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theking530.staticpower.machines.BlockMachineBase;

public class BaseRefineryBlock extends BlockMachineBase { 

    public static final PropertyBool CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static final PropertyBool CONNECTED_UP = PropertyBool.create("connected_up");
    public static final PropertyBool CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static final PropertyBool CONNECTED_SOUTH = PropertyBool.create("connected_south");
    public static final PropertyBool CONNECTED_WEST = PropertyBool.create("connected_west");
    public static final PropertyBool CONNECTED_EAST = PropertyBool.create("connected_east");
    
	public BaseRefineryBlock(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}	
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof BaseRefineryTileEntity) {
    		((BaseRefineryTileEntity)world.getTileEntity(pos)).onPlaced(world, pos, state, placer, stack);
    	}
    }
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof BaseRefineryTileEntity) {
    		((BaseRefineryTileEntity)world.getTileEntity(pos)).onBroken();
    	}
    }
	@Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos){
		super.onNeighborChange(world, observerPos, changedBlockPos);
    	if(world.getTileEntity(observerPos) != null && world.getTileEntity(observerPos) instanceof BaseRefineryTileEntity) {
    		((BaseRefineryTileEntity)world.getTileEntity(observerPos)).onNeighborUpdated(observerState, world, changedBlock, changedBlockPos, world.getBlockState(changedBlockPos).getBlock());
    	}
    	if(world.getBlockState(observerPos).getBlock() != Blocks.AIR) {
        	setDefaultFacing(world, observerPos, world.getBlockState(observerPos));
    	}
    }
	
	
	
	
	
	
	
	
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING,enumfacing).withProperty(CONNECTED_DOWN, this.isSideConnectable(worldIn,  pos, EnumFacing.DOWN)).withProperty(CONNECTED_EAST, this.isSideConnectable(worldIn,  pos, EnumFacing.EAST))
            		.withProperty(CONNECTED_NORTH, this.isSideConnectable(worldIn,  pos, EnumFacing.NORTH)).withProperty(CONNECTED_SOUTH, this.isSideConnectable(worldIn,  pos, EnumFacing.SOUTH)).withProperty(CONNECTED_UP, this.isSideConnectable(worldIn,  pos, EnumFacing.UP))
            		.withProperty(CONNECTED_WEST, this.isSideConnectable(worldIn,  pos, EnumFacing.WEST)), 2);
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        setDefaultFacing(worldIn, pos, state);
    }
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(CONNECTED_DOWN, this.isSideConnectable(worldIn,  pos, EnumFacing.DOWN)).withProperty(CONNECTED_EAST, this.isSideConnectable(worldIn,  pos, EnumFacing.EAST))
        		.withProperty(CONNECTED_NORTH, this.isSideConnectable(worldIn,  pos, EnumFacing.NORTH)).withProperty(CONNECTED_SOUTH, this.isSideConnectable(worldIn,  pos, EnumFacing.SOUTH)).withProperty(CONNECTED_UP, this.isSideConnectable(worldIn,  pos, EnumFacing.UP))
        		.withProperty(CONNECTED_WEST, this.isSideConnectable(worldIn,  pos, EnumFacing.WEST)).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(FACING, enumfacing);
    }
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    public IBlockState withRotation(IBlockState state, Rotation rot)  {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { CONNECTED_DOWN, CONNECTED_UP, CONNECTED_NORTH, CONNECTED_SOUTH, CONNECTED_WEST, CONNECTED_EAST, FACING});
    }	
    private boolean isSideConnectable (IBlockAccess world, BlockPos pos, EnumFacing side) {       
        final IBlockState state = world.getBlockState(pos.offset(side));
        return (state == null) ? false : state.getBlock() instanceof BaseRefineryBlock;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
		return false;    	
    }
}
