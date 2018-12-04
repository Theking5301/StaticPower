package theking530.staticpower.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.ItemBase;

public class SolderingIron extends ItemBase implements ISolderingIron{
	
	public SolderingIron(String name, int maxUses) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(maxUses);
		setNoRepair();
	}

	@Override  
		public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
	    	if(showHiddenTooltips()) {
	    		list.add("Max Uses: " + getMaxDamage(itemstack));
	    		list.add("Uses Remaining: " + (getMaxDamage(itemstack) - getDamage(itemstack)));
	    	}else{
	    		list.add(EnumTextFormatting.ITALIC + "Hold Shift");
	    }
	}

	@Override
	public boolean useSolderingItem(ItemStack stack) {
		stack.setItemDamage(stack.getItemDamage() + 1);
		if(stack.getItemDamage() >= stack.getMaxDamage()) {
			return true;
		}
		return false;
	}
	@Override
	public boolean canSolder(ItemStack solderingIron) {
		if(solderingIron.getItemDamage() < solderingIron.getMaxDamage()) {
			return true;
		}
		return false;
	}
}
