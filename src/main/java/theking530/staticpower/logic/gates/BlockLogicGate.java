package theking530.staticpower.logic.gates;

import api.IWrenchable;
import api.RegularWrenchMode;
import api.SneakWrenchMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.blocks.IItemBlockProvider;

public class BlockLogicGate extends Block implements IWrenchable, IItemBlockProvider{

	static float PIXEL = 1F/16F;
    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2*PIXEL, 1.0D), 
    		new AxisAlignedBB(0.0D, 0.0D, 1-2*PIXEL, 1.0D, 1.0D, 1.0D), 
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 2*PIXEL, 1.0D, 1.0D), 
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 2*PIXEL), 
    		new AxisAlignedBB(1-2*PIXEL, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),  
    		new AxisAlignedBB(0.0D, 1-2*PIXEL, 0.0D, 1.0D, 1.0D, 1.0D)
    };
    
    /**
     * 0 = On the Ground } 1-4 = On the side ordererd North, East, West, South | 5 = On the ceiling
     */
    public static final PropertyInteger ORIENTATION = PropertyInteger.create("orientation", 0, 6);
    private int GUI_ID;
    
    protected BlockLogicGate(String name, int guiID) {
		super(Material.ROCK);
		setCreativeTab(StaticPower.StaticPower);
		setRegistryName(name);
		setUnlocalizedName(name);
		GUI_ID = guiID;
	}
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
    public boolean isFullCube(IBlockState state){
        return false;
    }
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
    		return true;
    	}else if (!player.isSneaking()) {
    		TileEntityBaseLogicGate entity = (TileEntityBaseLogicGate) world.getTileEntity(pos);
    		if(entity != null) {
    			if(hitX > .25 && hitX < .75 && hitZ > .25 && hitZ < .75) {
    				if(GUI_ID != 0) {
                		FMLNetworkHandler.openGui(player, StaticPower.staticpower, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
                		return true;					
    				}
        		}else{
        			EnumFacing direction = null;
        			if(hitX > .75) {
        				direction=EnumFacing.EAST;	     
        			}else if(hitX < .25) {
        				direction=EnumFacing.WEST;	
        			}else if(hitZ > .75) {
        				direction=EnumFacing.SOUTH;
        			}else if(hitZ < .25) {
        				direction=EnumFacing.NORTH;	  
        			}else if(hitY > .75) {
        				direction=EnumFacing.UP;
        			}else if(hitY < .25) {
        				direction=EnumFacing.DOWN;	  
        			}
        			System.out.println("Hit X: " + hitX + "Hit Y: " + hitY + "Hit Z: " + hitZ + '\n');
     				entity.sideRightClicked(direction);	
        			return true;
        		}	
    		} 	
    		return false;
    	}else{
    		return false;
    	}
	}
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
    	TileEntityBaseLogicGate tempGate = (TileEntityBaseLogicGate) blockAccess.getTileEntity(pos);
    	return tempGate.OUTPUT_SIGNALS[side.getOpposite().ordinal()];
    }
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
    	TileEntityBaseLogicGate tempGate = (TileEntityBaseLogicGate) blockAccess.getTileEntity(pos);
        return tempGate.OUTPUT_SIGNALS[side.getOpposite().ordinal()];
    }
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AABB[state.getValue(ORIENTATION)];
    }
    @Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	TileEntityBaseLogicGate tempGate = (TileEntityBaseLogicGate) world.getTileEntity(pos);
    	if(tempGate != null && tempGate.SIDE_MODES != null && side != null && tempGate.SIDE_MODES[side.getOpposite().ordinal()] != Mode.Disabled) {
    		return true;
    	}
    	return false;
    }
	
	@Override
	public boolean canBeWrenched(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, boolean sneaking){
		return true;
	}
	@Override
	public void sneakWrenchBlock(EntityPlayer player, SneakWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {
		NBTTagCompound nbt = new NBTTagCompound();
		ItemStack machineStack = new ItemStack(Item.getItemFromBlock(this));
		if(world.getTileEntity(pos) instanceof TileEntityBaseLogicGate) {
			TileEntityBaseLogicGate tempMachine = (TileEntityBaseLogicGate)world.getTileEntity(pos);
			machineStack.setTagCompound(tempMachine.writeToNBT(nbt));
		}
		EntityItem droppedItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), machineStack);
		world.spawnEntity(droppedItem);
		world.setBlockToAir(pos);
	}
	public String getDescrption(ItemStack stack){
		return null;	
	}
	public String getInputDescrption(ItemStack stack){
		return null;
	}
	public String getOutputDescrption(ItemStack stack){
		return null;
	}
	public String getExtraDescrption(ItemStack stack){
		return null;
	}
    @Override
    public boolean hasTileEntity(IBlockState state) {
		return true;    	
    }
    @Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}
	@Override
	public void wrenchBlock(EntityPlayer player, RegularWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {
	}
	
	
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultOrientation(worldIn, pos, state);
    }
    private void setDefaultOrientation(World worldIn, BlockPos pos, IBlockState state)
    {
    	 if (!worldIn.isRemote)
         {
             int enumfacing = state.getValue(ORIENTATION);
             worldIn.setBlockState(pos, state.withProperty(ORIENTATION, enumfacing), 2);
         }
    }
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(ORIENTATION, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ORIENTATION);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	if(facing == EnumFacing.UP) {
            return this.getDefaultState().withProperty(ORIENTATION, 0);
    	}
    	if(facing == EnumFacing.DOWN) {
            return this.getDefaultState().withProperty(ORIENTATION, 5);
    	}
    	if(facing == EnumFacing.NORTH) {
            return this.getDefaultState().withProperty(ORIENTATION, 1);
    	}
    	if(facing == EnumFacing.EAST) {
            return this.getDefaultState().withProperty(ORIENTATION, 2);
    	}
    	if(facing == EnumFacing.SOUTH) {
            return this.getDefaultState().withProperty(ORIENTATION, 3);
    	}
    	if(facing == EnumFacing.WEST) {
            return this.getDefaultState().withProperty(ORIENTATION, 4);
    	}
        return this.getDefaultState().withProperty(ORIENTATION, 0);
    }
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    	if(facing == EnumFacing.UP) {
            return this.getDefaultState().withProperty(ORIENTATION, 0);
    	}
    	if(facing == EnumFacing.DOWN) {
            return this.getDefaultState().withProperty(ORIENTATION, 5);
    	}
    	if(facing == EnumFacing.NORTH) {
            return this.getDefaultState().withProperty(ORIENTATION, 1);
    	}
    	if(facing == EnumFacing.EAST) {
            return this.getDefaultState().withProperty(ORIENTATION, 2);
    	}
    	if(facing == EnumFacing.SOUTH) {
            return this.getDefaultState().withProperty(ORIENTATION, 3);
    	}
    	if(facing == EnumFacing.WEST) {
            return this.getDefaultState().withProperty(ORIENTATION, 4);
    	}
        return this.getDefaultState().withProperty(ORIENTATION, 0);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {ORIENTATION});
    }
	@Override
	public ItemBlock getItemBlock() {
		return new ItemLogicGate(this, this.getUnlocalizedName());
	}
}
