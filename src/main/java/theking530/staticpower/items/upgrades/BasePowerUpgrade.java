package theking530.staticpower.items.upgrades;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class BasePowerUpgrade  extends BaseUpgrade implements IMachineUpgrade{
	
	public float CAPACITY = 1f;
	public float TICK_UPGRADE = 1f;
	
	public  BasePowerUpgrade(String name, Tier tier){
		super(name, tier);
		switch(tier) {
		case BASIC:
			CAPACITY = (1.25f/maxStackSize);
			TICK_UPGRADE = (2.0f/maxStackSize);
			break;
		case STATIC:
			CAPACITY = (1.5f/maxStackSize);
			TICK_UPGRADE = (3f/maxStackSize);
			break;
		case ENERGIZED:
			CAPACITY = (1.75f/maxStackSize);
			TICK_UPGRADE = (4f/maxStackSize);
			break;
		case LUMUM:
			CAPACITY = (2f/maxStackSize);
			TICK_UPGRADE = (6f/maxStackSize);
			break;
		default:
			break;
		}
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		if(upgradeNumber == 0) {
			return CAPACITY * stack.getCount();
		}else{
			return TICK_UPGRADE * stack.getCount();
		}
	}
	@Override  
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 0))*100) + "%" + EnumTextFormatting.GREEN + " RF Capacity");
		list.add(EnumTextFormatting.WHITE + "+" + new java.text.DecimalFormat("#").format((getUpgradeValueAtIndex(stack, 1))*100) + "%" + EnumTextFormatting.GREEN + " RF Per Tick");

		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
