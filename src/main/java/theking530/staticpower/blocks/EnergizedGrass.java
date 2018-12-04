package theking530.staticpower.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergizedGrass extends BaseBlock{
	
	public EnergizedGrass() {
		super(Material.GRASS, "EnergizedGrass");
		setSoundType(SoundType.GROUND);
		setLightLevel(0.5F);
		setTickRandomly(true);
		setHardness(0.6F);
		setHarvestLevel("shovel",0);
	}	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
	    list.add("Walk On Top.");
	    list.add("I Dare You.");
	}	
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }	
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if(entity instanceof EntityLivingBase){
			EntityLivingBase livingEntity = (EntityLivingBase)entity;
			livingEntity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 1));
		}
	}
}
