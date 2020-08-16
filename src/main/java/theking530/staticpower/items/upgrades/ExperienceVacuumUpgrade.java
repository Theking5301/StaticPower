package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import theking530.staticpower.data.StaticPowerTiers;

public class ExperienceVacuumUpgrade extends BaseUpgrade implements IUpgradeItem {

	public ExperienceVacuumUpgrade(String name) {
		super(name, StaticPowerTiers.STATIC, new Properties().maxStackSize(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	protected void getAdvancedTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent(TextFormatting.GREEN + "Allows objects with"));
		tooltip.add(new StringTextComponent(TextFormatting.GREEN + "vacuum effects to"));
		tooltip.add(new StringTextComponent(TextFormatting.GREEN + "vacuum experience."));

		tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize()));
	}
}
