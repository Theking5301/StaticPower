package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.utilities.EnumTextFormatting;
import theking530.staticpower.utilities.Tier;

public class ExperienceVacuumUpgrade extends BaseUpgrade implements IMachineUpgrade{

	public  ExperienceVacuumUpgrade(String name){
		super(name, Tier.STATIC);
		setMaxStackSize(1);
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		return 0.0f;
	}
	@Override  
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {

		if(showHiddenTooltips()) {
			list.add(EnumTextFormatting.GREEN + "Allows objects with");
			list.add(EnumTextFormatting.GREEN + "vacuum effects to");
			list.add(EnumTextFormatting.GREEN + "vacuum experience.");
			
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
