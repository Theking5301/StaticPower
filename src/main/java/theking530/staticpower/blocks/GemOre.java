package theking530.staticpower.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GemOre extends BaseBlock {
	
	private ItemStack droppedGem;
	private int dropMin;
	private int dropMax;
	
	public GemOre(String name, String tool, int level, ItemStack gemDrop, int dropMin, int dropMax, float hardness) {
		super(Material.ROCK, name, tool, level, hardness);
		this.droppedGem = gemDrop;
		this.dropMin = dropMin;
		this.dropMax = dropMax;
	}
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return droppedGem.getItem();
    }
    public int damageDropped(IBlockState state)
    {
        return droppedGem.getItemDamage();
    }
    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random) {
        return (dropMin) + random.nextInt(Math.max(1, dropMax-dropMin));
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        if (this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this)) {
            return 1 + RANDOM.nextInt(5);
        }
        return 0;
    }
}