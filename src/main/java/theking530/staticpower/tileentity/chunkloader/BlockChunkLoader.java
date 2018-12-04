package theking530.staticpower.tileentity.chunkloader;

import org.apache.logging.log4j.Level;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.FMLLog;
import theking530.staticpower.StaticPower;

public class BlockChunkLoader extends BlockContainer{

	public Ticket TICKET;
	
	public BlockChunkLoader(String name) {
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(StaticPower.StaticPower);
		//RegisterHelper.registerItem(new BaseItemBlock(this, name));
	}
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!worldIn.isRemote) {
			TileEntityChunkLoader loader = (TileEntityChunkLoader) worldIn.getTileEntity(pos);
			loader.placedByPlayer(placer);
		}
    }
	@SuppressWarnings("deprecation")
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
	    super.onBlockAdded(world, pos, state);

	    if (!world.isRemote && TICKET == null) {
	        TICKET = ForgeChunkManager.requestTicket(StaticPower.instance, DimensionManager.getWorld(1), ForgeChunkManager.Type.NORMAL);
	        if (TICKET != null) {
	            ForgeChunkManager.forceChunk(TICKET, world.getChunkFromBlockCoords(pos).getPos());
	            FMLLog.log(Level.TRACE, "Static Power forcing chunk ( %d , %d )", world.getChunkFromBlockCoords(pos).getPos().x, world.getChunkFromBlockCoords(pos).getPos().z);
	        }
	    }
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChunkLoader();
	}
}
