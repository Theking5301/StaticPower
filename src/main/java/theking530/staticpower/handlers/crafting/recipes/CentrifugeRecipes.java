package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Items;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemMaterials;

public class CentrifugeRecipes {

	public static void registerCentrigureRecipes() {
		RegisterHelper.registerCentrifugeRecipe(Craft.ing(Items.GUNPOWDER), 500, ItemMaterials.dustCharcoal, Craft.outputStack(ItemMaterials.dustSaltpeter, 2), ItemMaterials.dustSulfur);
		RegisterHelper.registerCentrifugeRecipe(Craft.ing("dustElectrum"), 200, ItemMaterials.dustSilver, ItemMaterials.dustGold);
		RegisterHelper.registerCentrifugeRecipe(Craft.ing("dustInvar"), 300, Craft.outputStack(ItemMaterials.dustIron, 2), ItemMaterials.dustNickel);
		RegisterHelper.registerCentrifugeRecipe(Craft.ing("dustBronze"), 200, ItemMaterials.dustTin, ItemMaterials.dustCopper);
		
		RegisterHelper.registerCentrifugeRecipe(Craft.ing(ItemMaterials.dustStaticInfusion), 200, ItemMaterials.dustInertInfusion, ItemMaterials.dustStatic);
		RegisterHelper.registerCentrifugeRecipe(Craft.ing(ItemMaterials.dustEnergizedInfusion), 200, ItemMaterials.dustInertInfusion, ItemMaterials.dustEnergized);
		RegisterHelper.registerCentrifugeRecipe(Craft.ing(ItemMaterials.dustLumumInfusion), 200, ItemMaterials.dustInertInfusion, ItemMaterials.dustLumum);
	}
}
