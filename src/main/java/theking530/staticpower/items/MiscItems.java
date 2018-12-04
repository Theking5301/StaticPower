package theking530.staticpower.items;

import net.minecraft.item.ItemStack;

public class MiscItems extends MultiItem {
	
	public MiscItems() {
		super("misc");
	}

	public static ItemStack distilleryGrain;
	public static ItemStack wheatFlour;
	public static ItemStack potatoFlour;
	public static ItemStack potatoBread;
	
	public static ItemStack applePie;
	public static ItemStack staticPie;
	public static ItemStack energizedPie;
	public static ItemStack lumumPie;
	
	@Override
	protected void registerSubItems() {
		distilleryGrain = createSubOreItem(0, "distilleryGrain");
		wheatFlour = createSubOreItem(1, "flourWheat", "foodFlour");
		potatoFlour = createSubOreItem(2, "flourPotato", "foodPotatoFlour");
		potatoBread = createSubOreItem(3, "potatoBread", "foodPotatoBread");
		applePie = createSubItem(4, "applePie");
		staticPie = createSubItem(5, "staticPie");
		energizedPie = createSubItem(6, "energizedPie");
		lumumPie = createSubItem(7, "lumumPie");
	}
}
