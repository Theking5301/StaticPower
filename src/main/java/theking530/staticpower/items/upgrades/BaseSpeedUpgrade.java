package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BaseSpeedUpgrade extends BaseUpgrade implements IMachineUpgrade{
	public float SPEED = 1f;
	public float POWER_MULT = 1f;
	
	public  BaseSpeedUpgrade(String name, Tier tier){
		super(name, tier);
		switch(tier) {
		case BASIC:
			SPEED = (1.05f/maxStackSize);
			POWER_MULT = (1.25f/maxStackSize);
			break;
		case STATIC:
			SPEED = (1.3f/maxStackSize);
			POWER_MULT = (1.5f/maxStackSize);
			break;
		case ENERGIZED:
			SPEED = (1.75f/maxStackSize);
			POWER_MULT = (2.0f/maxStackSize);
			break;
		case LUMUM:
			SPEED = (3.0f/maxStackSize);
			POWER_MULT = (2.5f/maxStackSize);
			break;
		case CREATIVE:
			SPEED = (10f/maxStackSize);
			POWER_MULT = (0f/maxStackSize);
			break;
		default:
			break;
		}
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		if(upgradeNumber == 0) {
			return (SPEED * stack.getCount());
		}else{
			return (POWER_MULT * stack.getCount());
		}
	}
	@Override  
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "%" + EnumTextFormatting.GREEN + " Processing Speed");
		list.add(EnumTextFormatting.WHITE + "+" +  new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 1))*100) + "%" + EnumTextFormatting.RED + " Power Use");
		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
