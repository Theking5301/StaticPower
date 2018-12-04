package theking530.staticpower.blocks.crops;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class StaticCrop extends ItemFood {
	
	public StaticCrop() {
		super(4, 0.5f, false);
	}
	public static Item StaticCrop;{
		setMaxStackSize(64);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName("StaticCrop");
		setRegistryName("StaticCrop");
		setAlwaysEdible();
	}
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("These Seem to Radiate");
		tooltip	.add("Energy...Yummy");
	}
}
		


