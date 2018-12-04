package theking530.staticpower.tileentity.solarpanels;

import api.SneakWrenchMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.machines.BlockMachineBase;

public class BlockSolarPanel extends BlockMachineBase {

	public Tier TIER;
	static float PIXEL = 1F/16F;
    private static final AxisAlignedBB AA_BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 4*PIXEL, 1.0D);
    
	public BlockSolarPanel(String name, Tier tier) {
		super(name);
		TIER = tier;
		setCreativeTab(StaticPower.StaticPower);
	}
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
    public boolean isFullCube(IBlockState state){
        return false;
    }
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public void sneakWrenchBlock(EntityPlayer player, SneakWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {
		ItemStack machineStack = new ItemStack(Item.getItemFromBlock(this));
		EntityItem droppedItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), machineStack);
		world.spawnEntity(droppedItem);
		world.setBlockToAir(pos);		
	}
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AA_BB;
    }
	@Override
	public boolean canBeWrenched(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, boolean sneaking) {
		return sneaking;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch(TIER) {
			case BASIC:
				return new TileEntityBasicSolarPanel();
			case STATIC:
				return new TileEntityStaticSolarPanel();
			case ENERGIZED:
				return new TileEntityEnergizedSolarPanel();	
			case LUMUM:
				return new TileEntityLumumSolarPanel();
			case CREATIVE:
				return new TileEntityCreativeSolarPanel();
			default:
				return new TileEntityBasicSolarPanel();
		}
	}
}
