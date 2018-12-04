package theking530.staticpower.items;

import net.minecraft.item.ItemStack;

public class FormerMolds extends MultiItem {

	public static ItemStack moldBlank;
	public static ItemStack moldPlate;	
	public static ItemStack moldWire;	
	public static ItemStack moldGear;
	
	public FormerMolds() {
		super("mold");
	}
	@Override
	protected void registerSubItems() {
		moldBlank = createSubItem(0, "moldBlank");
		moldPlate = createSubItem(1, "moldPlate");
		moldWire = createSubItem(2, "moldWire");
		moldGear = createSubItem(3, "moldGear");
	}
}
