package theking530.staticpower.items.book;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.items.ItemBase;

public class StaticPowerBook extends ItemBase{

	public StaticPowerBook(String name) {
		super(name);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
	ItemStack itemStack = player.getHeldItem(hand);
		if (world.isRemote) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
		}else if (!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDStaticBook, world, 0, 0, 0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		}else{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
		}    
    }
}
