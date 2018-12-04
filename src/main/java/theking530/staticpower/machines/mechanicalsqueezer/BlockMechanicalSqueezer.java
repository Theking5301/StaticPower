package theking530.staticpower.machines.mechanicalsqueezer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.machines.BlockMachineBase;

public class BlockMechanicalSqueezer extends BlockMachineBase{
	
	public BlockMechanicalSqueezer() {
		super("MechanicalSqueezer");
		setHardness(3.5f);
	    setResistance(5.0f);
		setCreativeTab(StaticPower.StaticPower);
	}
	@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
    		TileEntityMechanicalSqueezer entity = (TileEntityMechanicalSqueezer) world.getTileEntity(pos);
    		if (entity != null) {
    			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiMechanicalSqueezer, world, pos.getX(), pos.getY(), pos.getZ());
    		}
    		return true;
    	}else{
    		TileEntityMechanicalSqueezer entity = (TileEntityMechanicalSqueezer) world.getTileEntity(pos);
    		entity.rightClick();
    		return true;
    	}
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityMechanicalSqueezer();
	}
}
