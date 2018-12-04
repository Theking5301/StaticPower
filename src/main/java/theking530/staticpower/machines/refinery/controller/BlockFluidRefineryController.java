package theking530.staticpower.machines.refinery.controller;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.machines.refinery.BaseRefineryBlock;

public class BlockFluidRefineryController extends BaseRefineryBlock {

	public BlockFluidRefineryController(String name) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	}	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (world.isRemote) {
    		return true;
    	}else if (!player.isSneaking()) {
    		TileEntityFluidRefineryController entity = (TileEntityFluidRefineryController) world.getTileEntity(pos);
    		if (entity != null) {
    			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDFluidRefineryController, world, pos.getX(), pos.getY(), pos.getZ());
    		}
    		return true;
    	}else{
    		return false;
    	}
	}
	
    @Override
    public boolean hasTileEntity(IBlockState state) {
		return true;    	
    }
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityFluidRefineryController();
	}
}
