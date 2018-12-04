package theking530.staticpower.assists.utilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtilities {

	public static ItemStack getOreStack(String ore, int index) {
		return OreDictionary.getOres(ore).get(index).copy();
	}
}
