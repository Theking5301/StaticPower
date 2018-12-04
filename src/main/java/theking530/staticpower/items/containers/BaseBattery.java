package theking530.staticpower.items.containers;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import theking530.staticpower.items.BasePoweredItem;

public class BaseBattery extends BasePoweredItem { 
	
	public ItemStack battery;
	
	public BaseBattery(String name, int capacity) {
		super(name, capacity, capacity/50);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			battery = new ItemStack(this, 1, 0); 
			battery.setItemDamage(getMaxEnergyStored(battery)/damageDivisor);
	    	items.add(battery);
	        
	        ItemStack filledBattery = new ItemStack(this, 1, 1);     
	        this.setEnergy(filledBattery, capacity);
	        items.add(filledBattery);
    	}
    }
	
	@Override  
		public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Power Stored: " +  NumberFormat.getNumberInstance(Locale.US).format(getEnergyStored(itemstack)) +"/"
				+  NumberFormat.getNumberInstance(Locale.US).format(capacity) + "RF");
	}
}
