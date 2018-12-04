package theking530.staticpower.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.ItemBase;

public class WireCutters extends ItemBase {

	public WireCutters(int maxUses) {
		super("WireCutters");
		setMaxDamage(maxUses);
		setMaxStackSize(1);
	}
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if(repair.getItem() == Items.IRON_INGOT) {
			return true;
		}
		return false;
	}
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return stack.getItemDamage() < stack.getMaxDamage() ? new ItemStack(stack.getItem(), 1,stack.getItemDamage() + 1) : ItemStack.EMPTY;  	
	}
	@Override
	public boolean hasContainerItem(){
		return true;
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
}
