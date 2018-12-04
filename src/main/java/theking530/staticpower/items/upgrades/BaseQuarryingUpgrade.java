package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BaseQuarryingUpgrade  extends BaseUpgrade implements IMachineUpgrade{

	public int BLOCKS_PER_TICK = 1;
	public float POWER_MULT = 1f;
	
	public BaseQuarryingUpgrade(String name, Tier tier){
		super(name, tier);	
		switch(tier) {
			case STATIC:
				BLOCKS_PER_TICK = 2;
				POWER_MULT = 2f;
				break;
			case ENERGIZED:
				BLOCKS_PER_TICK = 3;
				POWER_MULT = 3f;
				break;
			case LUMUM:
				BLOCKS_PER_TICK = 5;
				POWER_MULT = 5f;
				break;
		default:
			break;
		}
		setMaxStackSize(1);
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		if(upgradeNumber == 1) {
			return BLOCKS_PER_TICK;
		}else{
			return POWER_MULT;
		}
	}
	@Override  
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE +  "Mines " + getUpgradeValueAtIndex(stack, 0) + EnumTextFormatting.GREEN + " Blocks Per Tick");
		list.add(EnumTextFormatting.WHITE + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 1))*100) + "%" + EnumTextFormatting.RED + " Power Use");
		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
	
}
