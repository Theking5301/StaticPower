package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BaseOutputMultiplierUpgrade  extends BaseUpgrade implements IMachineUpgrade{
	
	public float multiplier = 1f;

	public  BaseOutputMultiplierUpgrade(String name, Tier tier){
		super(name, tier);
		switch(tier) {
		case BASIC:
			multiplier = 0.01f;
			break;
		case STATIC:
			multiplier = 0.02f;
			break;
		case ENERGIZED:
			multiplier = 0.03f;
			break;
		case LUMUM:
			multiplier = 0.04f;
			break;
		default:
			break;
		}
		this.setMaxStackSize(8);
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		return multiplier * stack.getCount();
	}
	@Override  
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "%" + EnumTextFormatting.GREEN + " Output Chance");
		list.add(EnumTextFormatting.WHITE + "+" + new java.text.DecimalFormat("#.#").format((getUpgradeValueAtIndex(stack, 0)/2)*100) + "%" + EnumTextFormatting.RED + " Power Usage");

		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
