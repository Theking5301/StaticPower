package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BasePowerUpgrade extends BaseUpgrade {

	public BasePowerUpgrade(String name, ResourceLocation tier) {
		super(name, tier, UpgradeType.POWER);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	protected void getBasicTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent(TextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format(getTier().getPowerUpgrade() * 100) + "%" + TextFormatting.GREEN + " RF Capacity"));
		tooltip.add(new StringTextComponent(TextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format(getTier().getPowerIoUpgrade() * 100) + "%" + TextFormatting.GREEN + " RF Per Tick"));
	}
}
