package theking530.staticpower.blocks.crops;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class LumumCrop extends ItemFood {
	
	public LumumCrop() {
		super(12, 1.5f, false);
		setMaxStackSize(64);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName("LumumCrop");
		setRegistryName("LumumCrop");
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
	    
        if (!world.isRemote) {
        	//world.playAuxSFX(2002, (int)Math.round(player.posX), (int)Math.round(player.posY), (int)Math.round(player.posZ), 0);
            int i = 3 + world.rand.nextInt(5) + world.rand.nextInt(5);

            while (i > 0) {
                int j = EntityXPOrb.getXPSplit(i);
        	   // world.playSoundAtEntity(player, "random.orb", 0.5F, 0.4F / (itemRand.nextFloat()));
                i -= j;
                world.spawnEntity(new EntityXPOrb(world, player.posX, player.posY, player.posZ, j));
            }
        }
	}
}
		


