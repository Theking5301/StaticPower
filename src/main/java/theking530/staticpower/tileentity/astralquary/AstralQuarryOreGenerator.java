package theking530.staticpower.tileentity.astralquary;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class AstralQuarryOreGenerator {
	public static final List<ItemStack> ORES = new ArrayList<ItemStack>();
	
	public static void initializeAstralQuaryOres() {
		String[] ores = OreDictionary.getOreNames();
		ORES.clear();
		
		for(String ore : ores) {
			if(ore.startsWith("ore")) {
				NonNullList<ItemStack> oreList = OreDictionary.getOres(ore, true);
				if(oreList.size() > 0) {
					ORES.add(oreList.get(oreList.size()-1));
				}
			}
		}	
	}
}
