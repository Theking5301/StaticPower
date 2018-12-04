package theking530.staticpower.machines.batteries;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.machines.BlockMachineBase;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBasicBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityEnergizedBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityLumumBattery;
import theking530.staticpower.machines.batteries.tileentities.TileEntityStaticBattery;

public class BlockBattery extends BlockMachineBase{

	public Tier TIER;
	
	public BlockBattery(String name, Tier tier) {
		super(name);
		setHardness(3.5f);
	    setResistance(5.0f);
	    TIER = tier;
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {	
		if (world.isRemote) {
    		return true;
    	}else if (!player.isSneaking()) {
    		TileEntityBattery entity = (TileEntityBattery) world.getTileEntity(pos);
    		if (entity != null) {
    			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDBattery, world, pos.getX(), pos.getY(), pos.getZ());
    		}
    		return true;
    	}
		return false;
	}
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
    	if(blockAccess.getTileEntity(pos) instanceof TileEntityBattery) {
        	TileEntityBattery battery = (TileEntityBattery) blockAccess.getTileEntity(pos);
        	return battery.shouldOutputRedstoneSignal() ? 15 : 0;
    	}
    	return 0;
    }
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
    	if(blockAccess.getTileEntity(pos) instanceof TileEntityBattery) {
        	TileEntityBattery battery = (TileEntityBattery) blockAccess.getTileEntity(pos);
        	return battery.shouldOutputRedstoneSignal() ? 15 : 0;
    	}
    	return 0;
    }
    @Override
    public boolean canProvidePower(IBlockState state){
        return true;
    }
    @Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	TileEntityBattery battry = (TileEntityBattery) world.getTileEntity(pos);
    	if(battry != null &&  battry.getSideConfiguration(side) != Mode.Disabled) {
    		return true;
    	}
    	return false;
    }
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch(TIER){
			case BASIC: return new TileEntityBasicBattery();
			case STATIC: return new TileEntityStaticBattery();
			case ENERGIZED: return new TileEntityEnergizedBattery();
			case LUMUM: return new TileEntityLumumBattery();
			default: return new TileEntityStaticBattery();
		}
	}
}
