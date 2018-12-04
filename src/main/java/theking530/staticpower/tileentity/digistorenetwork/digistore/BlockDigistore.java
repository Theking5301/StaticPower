package theking530.staticpower.tileentity.digistorenetwork.digistore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
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
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreBlock;

public class BlockDigistore extends BaseDigistoreBlock {

	public BlockDigistore(String name) {
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
    		TileEntityDigistore entity = (TileEntityDigistore) world.getTileEntity(pos);
    		if (entity != null) {
    			entity.onBarrelRightClicked(player, hand, facing, hitX, hitY, hitZ);
    		}
    		return true;
    	}else{
    		if(player.getHeldItem(hand).isEmpty()) {
        		TileEntityDigistore entity = (TileEntityDigistore) world.getTileEntity(pos);
        		if (entity != null) {
        			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDDigistore, world, pos.getX(), pos.getY(), pos.getZ());
        		}
    		}
    		return false;
    	}
	}
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
    	if(!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof TileEntityBase) {
    		TileEntityDigistore barrel = (TileEntityDigistore) worldIn.getTileEntity(pos);
	        if(!barrel.wasWrenchedDoNotBreak) {
	        	int storedAmount = barrel.getStoredAmount();
	        	while(storedAmount > 0) {
	        		ItemStack droppedItem = barrel.getStoredItem().copy();
	        		droppedItem.setCount(Math.min(storedAmount, droppedItem.getMaxStackSize()));
	        		storedAmount -= droppedItem.getCount();
					WorldUtilities.dropItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), droppedItem);
	        	}
		        for(int i=0; i<barrel.slotsUpgrades.getSlots(); i++) {
		        	if(!barrel.slotsUpgrades.getStackInSlot(i).isEmpty()) {
						WorldUtilities.dropItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), barrel.slotsUpgrades.getStackInSlot(i));
		        	}
		        }
	        }
    	}     
    }
	@Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer playerIn) {
		TileEntityDigistore entity = (TileEntityDigistore) world.getTileEntity(pos);
		if (entity != null) {
			entity.onBarrelLeftClicked(playerIn);
		}
    }
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityDigistore();
	}
	
	@Override
	public ItemBlock getItemBlock() {
		return new DigistoreItemBlock(this, getUnlocalizedName());
	}
}
