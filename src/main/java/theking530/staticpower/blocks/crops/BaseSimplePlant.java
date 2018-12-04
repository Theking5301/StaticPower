package theking530.staticpower.blocks.crops;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theking530.staticpower.blocks.IItemBlockProvider;

public class BaseSimplePlant extends BlockCrops implements IGrowable, IItemBlockProvider {

    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D)};
    
    public BaseSimplePlant(String name) {
        setTickRandomly(true);
        setHardness(0.0F);
        disableStats();
        //setLightLevel(1.0F);
		setUnlocalizedName(name);
		setRegistryName(name);
    }
	
	public int quantityDropped (Random random) {
		return 1;
	}
    protected Item getSeed(){
		return getSeeds();
    }
    protected Item getCrop(){
		return getCrops();
    }
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return CROPS_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(isMaxAge(state)) {
        	dropBlockAsItem(world, pos, state, 0); 
        	world.setBlockState(pos, withAge(0), 2);
    		return true;
        }
        return false;
    }
    public Item getSeeds() {
    	return ModPlants.StaticSeeds;
    }
    public Item getCrops() {
    	return ModPlants.StaticCrop;
    }

	@Override
	public ItemBlock getItemBlock() {
		return null;
	}
}