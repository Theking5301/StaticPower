package theking530.staticpower.blocks.crops;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class EnergizedCrop extends ItemFood {
	
	public EnergizedCrop() {
		super(8, 1.0f, false);
		setUnlocalizedName("EnergizedCrop");
		setRegistryName("EnergizedCrop");
		setCreativeTab(StaticPower.StaticPower);
		setAlwaysEdible();
	}		
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("These Seem to Radiate");
		tooltip.add("Energy...Yummy");
    }
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	    super.onFoodEaten(stack, world, player);
	}
}

