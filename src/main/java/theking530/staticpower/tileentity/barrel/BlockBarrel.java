package theking530.staticpower.tileentity.barrel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.machines.BaseMachineBlock;
import theking530.staticpower.tileentity.BaseTileEntity;

public class BlockBarrel extends BaseMachineBlock {

	public BlockBarrel(String name) {
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
    		TileEntityBarrel entity = (TileEntityBarrel) world.getTileEntity(pos);
    		if (entity != null) {
    			entity.onBarrelRightClicked(player, hand, facing, hitX, hitY, hitZ);
    		}
    		return true;
    	}else{
    		if(player.getHeldItem(hand).isEmpty()) {
        		TileEntityBarrel entity = (TileEntityBarrel) world.getTileEntity(pos);
        		if (entity != null) {
        			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDBarrel, world, pos.getX(), pos.getY(), pos.getZ());
        		}
    		}
    		return false;
    	}
	}
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	if(worldIn.getTileEntity(pos) instanceof BaseTileEntity) {
    		TileEntityBarrel barrel = (TileEntityBarrel) worldIn.getTileEntity(pos);
	        if(!barrel.wasWrenchedDoNotBreak) {
	        	int storedAmount = barrel.getStoredAmount();
	        	while(storedAmount > 0) {
	        		ItemStack droppedItem = barrel.getStoredItem().copy();
	        		droppedItem.setCount(Math.min(storedAmount, droppedItem.getMaxStackSize()));
	        		storedAmount -= droppedItem.getCount();
					WorldUtilities.dropItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), droppedItem);
	        	}
	        }
    	}     
        super.breakBlock(worldIn, pos, state);
    }
	@Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer playerIn) {
		TileEntityBarrel entity = (TileEntityBarrel) world.getTileEntity(pos);
		if (entity != null) {
			entity.onBarrelLeftClicked(playerIn);
		}
    }
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityBarrel();
	}
}
