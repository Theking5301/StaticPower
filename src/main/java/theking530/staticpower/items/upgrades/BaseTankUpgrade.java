package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BaseTankUpgrade extends BaseUpgrade implements IMachineUpgrade {
	
	public float CAPACITY = 1f;
	
	public  BaseTankUpgrade(String name, Tier tier){
		super(name, tier);
		switch(tier) {
		case BASIC:
			CAPACITY = (1.0f/maxStackSize);
			break;
		case STATIC:
			CAPACITY = (1.5f/maxStackSize);
			break;
		case ENERGIZED:
			CAPACITY = (2.0f/maxStackSize);
			break;
		case LUMUM:
			CAPACITY = (2.5f/maxStackSize);
			break;
		default:
			break;
		}
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		BaseTankUpgrade tempUpgrade = (BaseTankUpgrade) stack.getItem();
		return (tempUpgrade.CAPACITY * stack.getCount());
	}
	@Override  
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE +  "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "%" + EnumTextFormatting.GREEN + " Tank Capacity");
		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
