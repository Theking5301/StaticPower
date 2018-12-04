package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Items;
import theking530.staticpower.assists.MaterialSet;
import theking530.staticpower.assists.MaterialSets;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.FormerMolds;
import theking530.staticpower.items.ItemComponents;

public class FormerRecipes {
	
	public static void registerFusionRecipes() {
		for(MaterialSet set : MaterialSets.MATERIALS) {
			RegisterHelper.registerFormerRecipe(Craft.outputStack(set.getPlate(), 2),Craft.ing(set.getIngot()),Craft.ing(FormerMolds.moldPlate));
			RegisterHelper.registerFormerRecipe(Craft.outputStack(set.getGear()),Craft.ing(set.getIngot()),Craft.ing(FormerMolds.moldGear));
			
			if(set.getBlock() != null) {
				RegisterHelper.registerFormerRecipe(Craft.outputStack(set.getPlate(), 18), Craft.ing(set.getBlock()),Craft.ing(FormerMolds.moldPlate));
			}
		}
		
		//Wire From Plate
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireCopper, 8), Craft.ing("plateCopper"),Craft.ing(FormerMolds.moldWire));
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireSilver, 8), Craft.ing("plateSilver"),Craft.ing(FormerMolds.moldWire));
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireGold, 8), Craft.ing("plateGold"),Craft.ing(FormerMolds.moldWire));	
		
		//Wire From Ingot
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireCopper, 8), Craft.ing("ingotCopper"),Craft.ing(FormerMolds.moldWire));
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireSilver, 8), Craft.ing("ingotSilver"),Craft.ing(FormerMolds.moldWire));
		RegisterHelper.registerFormerRecipe(Craft.outputStack(ItemComponents.wireGold, 8), Craft.ing(Items.GOLD_INGOT),Craft.ing(FormerMolds.moldWire));	
			
		//Molds
		RegisterHelper.registerFormerRecipe(Craft.outputStack(FormerMolds.moldBlank), Craft.multiOre("plateTin"), Craft.ing("plateIron"));	
		RegisterHelper.registerFormerRecipe(Craft.outputStack(FormerMolds.moldPlate), Craft.multiOre("plateTin", "plateCopper", "plateSilver", "plateLead", "plateGold"),Craft.ing(FormerMolds.moldBlank));	
		RegisterHelper.registerFormerRecipe(Craft.outputStack(FormerMolds.moldWire), Craft.multiOre("wireCopper", "wireSilver", "wireGold"),Craft.ing(FormerMolds.moldBlank));	
		RegisterHelper.registerFormerRecipe(Craft.outputStack(FormerMolds.moldGear), Craft.multiOre("gearTin", "gearCopper", "gearSilver", "gearLead", "gearGold", "gearPlatinum", "gearInertInfusion","gearRedstoneAlloy"),Craft.ing(FormerMolds.moldBlank));	
	}
}
