package theking530.staticpower.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseFluidBlock extends BlockFluidClassic  {

protected Fluid fluid;

	public BaseFluidBlock(Fluid fluid, Material material, String name) {
		super(fluid, material);
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		IBlockState bs = world.getBlockState(pos);
		if(bs.getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}	

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		IBlockState bs = world.getBlockState(pos);
		if(bs.getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		super.onEntityCollidedWithBlock(world,pos, state, entity);
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		if (itemIn != null) {
			super.getSubBlocks(itemIn, items);
		}
	}
}