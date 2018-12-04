package theking530.staticpower.items.upgrades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.Tier;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.IVariantItem;

public class DigistoreMiscUpgrades extends BaseUpgrade implements IVariantItem {

	private final Map<ItemStack, String> upgrades;
		
	public static ItemStack VoidUprgade;
	
	public DigistoreMiscUpgrades(String name) {
		super(name, Tier.BASIC);
		setMaxStackSize(1);
		setHasSubtypes(true);
		upgrades = new HashMap<ItemStack, String>();
    	
		VoidUprgade = new ItemStack(this, 1, 0).setTranslatableName("item.DigistoreVoidUpgrade");
    	upgrades.put(VoidUprgade, "void");
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(EnumTextFormatting.WHITE + "Voids all excess items");
		list.add(EnumTextFormatting.WHITE + "entering a Digistore.");
	}
}
