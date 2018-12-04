package theking530.staticpower.blocks.crops;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseDoublePlant extends BaseSimplePlant  {

    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D)};
    public static final PropertyEnum<EnumCropHalf> HALF = PropertyEnum.<EnumCropHalf>create("half", EnumCropHalf.class);
    
    public BaseDoublePlant(String name, Item seeds, Item crop) {
    	super(name);
        setTickRandomly(true);
        setHardness(0.0F);
        disableStats();
        setDefaultState(blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)).withProperty(HALF, EnumCropHalf.LOWER));
        setSoundType(SoundType.PLANT);
    }
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        super.grow(worldIn, pos, state);

        if(getAge(state) == getMaxAge() && worldIn.getBlockState(pos).getValue(HALF) == EnumCropHalf.UPPER) {
        	if(worldIn.isAirBlock(pos.offset(EnumFacing.UP))) {
        		worldIn.setBlockState(pos.offset(EnumFacing.UP), blockState.getBaseState().withProperty(HALF, EnumCropHalf.UPPER));
        	}
        }
    }
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (state.getValue(HALF) == EnumCropHalf.UPPER) {
            if (worldIn.getBlockState(pos.down()).getBlock() == this) {
                if (!player.capabilities.isCreativeMode) {
                	dropBlockAsItem(worldIn, pos, state, 0); 
                }
                worldIn.setBlockToAir(pos);
            }
        }
        if (state.getValue(HALF)  == EnumCropHalf.LOWER) {
            if (worldIn.getBlockState(pos.up()).getBlock() == this) {
                if (!player.capabilities.isCreativeMode) {
                	dropBlockAsItem(worldIn, pos, state, 0); 
                	dropBlockAsItem(worldIn, pos.up(), state, 0); 
                }
                worldIn.setBlockToAir(pos);
                worldIn.setBlockToAir(pos.up());
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(isMaxAge(state) && world.getBlockState(pos).getValue(HALF)  == EnumCropHalf.LOWER) {
        	dropBlockAsItem(world, pos, state, 0); 
        	world.setBlockState(pos, withAge(0), 2);
        	if(world.getBlockState(pos.offset(EnumFacing.UP)).getValue(HALF)  == EnumCropHalf.UPPER) {
            	dropBlockAsItem(world, pos.offset(EnumFacing.UP), state, 0); 
            	world.setBlockState(pos.offset(EnumFacing.UP), withAge(0), 2);
        	}
    		return true;
        }
        if(isMaxAge(state) && world.getBlockState(pos).getValue(HALF) == EnumCropHalf.UPPER) {
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
    
    
    
    public static enum EnumCropHalf implements IStringSerializable {
        UPPER,
        LOWER;

        public String toString() {
            return this.getName();
        }

        public String getName() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}