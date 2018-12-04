package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BaseRangeUpgrade  extends BaseUpgrade implements IMachineUpgrade{
	
	public float RANGE_MULT = 1f;
	
	public  BaseRangeUpgrade(String name, Tier tier){
		super(name, tier);
		switch(tier) {
		case BASIC:
			RANGE_MULT = (0.75f);
			break;
		case STATIC:
			RANGE_MULT = (1.5f);
			break;
		case ENERGIZED:
			RANGE_MULT = (2.5f);
			break;
		case LUMUM:
			RANGE_MULT = (3f);
			break;
		default:
			break;
		}
		setMaxStackSize(1);
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		return RANGE_MULT;
	}
	@Override  
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(RANGE_MULT < 1) {
			list.add(EnumTextFormatting.RED +  "-" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "% " + EnumTextFormatting.WHITE + "Range");
		}else{
			list.add(EnumTextFormatting.GREEN +  "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "% " + EnumTextFormatting.WHITE + "Range");
		}
		
		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
