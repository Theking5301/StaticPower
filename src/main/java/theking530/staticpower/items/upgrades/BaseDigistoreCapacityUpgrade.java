package theking530.staticpower.items.upgrades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.IVariantItem;

public class BaseDigistoreCapacityUpgrade extends BaseUpgrade implements IVariantItem {

	private final Map<ItemStack, String> upgrades;
	
	public final ItemStack basicCapacityUpgrade;
	public final ItemStack ironCapacityUpgrade;
	public final ItemStack goldCapacityUpgrade;
	public final ItemStack leadCapacityUpgrade;
	public final ItemStack obsidianCapacityUpgrade;
	
	public final ItemStack staticCapacityUpgrade;
	public final ItemStack energizedCapacityUpgrade;
	public final ItemStack lumumCapacityUpgrade;
	
	public BaseDigistoreCapacityUpgrade(String name) {
		super(name, Tier.BASIC);
		setMaxStackSize(8);
		setHasSubtypes(true);
		
		upgrades = new HashMap<ItemStack, String>();
        
    	basicCapacityUpgrade = createUpgrade(Tier.BASIC, 0);
    	ironCapacityUpgrade = createUpgrade(Tier.IRON, 1);
    	goldCapacityUpgrade = createUpgrade(Tier.GOLD, 2);
    	leadCapacityUpgrade = createUpgrade(Tier.LEAD, 3);
    	obsidianCapacityUpgrade = createUpgrade(Tier.OBSIDIAN, 4);
    	staticCapacityUpgrade = createUpgrade(Tier.STATIC, 5);
    	energizedCapacityUpgrade = createUpgrade(Tier.ENERGIZED, 6);
    	lumumCapacityUpgrade = createUpgrade(Tier.LUMUM, 7); 
	}
	
	private ItemStack createUpgrade(Tier tier, int meta) {
    	ItemStack upgrade = new ItemStack(this, 1, meta);         
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setInteger("ITEM_COUNT", tier.getDigistoreItemCapacityAmount());
    	nbt.setString("ITEM_NAME", tier.getName() + "=item.DigistoreCapacityUpgrade");
    	upgrade.setTagCompound(nbt);
    	upgrades.put(upgrade, tier.toString());
		return upgrade;
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    	if(tab == StaticPower.StaticPower) {
    		for(Entry<ItemStack, String> upgrade : upgrades.entrySet()) {
    			items.add(upgrade.getKey());
    		}
    	}
    }
	@Override
	public Map<ItemStack, String> getSubItemMap() {
		return upgrades;
	}
	@Override
	public float getUpgradeValueAtIndex(ItemStack stack, int upgradeNumber) {
		if(stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("ITEM_COUNT")*stack.getCount();
		}
		return 0;
	}
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()) {
	    	String[] splitMsg = stack.getTagCompound().getString("ITEM_NAME").split("=");
	    	if(splitMsg.length == 2) {
				return I18n.format(splitMsg[0]) + " " + I18n.format(splitMsg[1]);
	    	}
		}
		return "*ERROR*";
    }
	@Override  
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(getUpgradeValueAtIndex(stack, 0) < 0) {
			list.add(EnumTextFormatting.RED + "" + new java.text.DecimalFormat("#").format(getUpgradeValueAtIndex(stack, 0)) + EnumTextFormatting.WHITE + "  Items");
			list.add(EnumTextFormatting.RED + "(" + new java.text.DecimalFormat("#").format(getUpgradeValueAtIndex(stack, 0)/64) + ") " + EnumTextFormatting.WHITE + "Stacks");
		}else{
			list.add(EnumTextFormatting.GREEN +  "+" + new java.text.DecimalFormat("#").format(getUpgradeValueAtIndex(stack, 0)) + EnumTextFormatting.WHITE + "  Items");
			list.add(EnumTextFormatting.GREEN +  "(+" + new java.text.DecimalFormat("#").format(getUpgradeValueAtIndex(stack, 0)/64) + ") " + EnumTextFormatting.WHITE + "Stacks");
		}
		
		if(showHiddenTooltips()) {
    		list.add(EnumTextFormatting.WHITE + "Stacks Up To: " + stack.getMaxStackSize());
    	}else{
        	list.add(EnumTextFormatting.ITALIC + "Hold Shift");
    	}
	}
}
