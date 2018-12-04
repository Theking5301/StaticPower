package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.items.ItemMaterials;
import theking530.staticpower.items.MiscItems;

public class SmeltingRecipes {

	@SuppressWarnings("all")
	private static void registerSmeltingRecipes() {
		
		//Food
		registerRecipe(MiscItems.wheatFlour, new ItemStack(Items.BREAD), 0.1f);
		registerRecipe(MiscItems.potatoFlour, Craft.outputStack(MiscItems.potatoBread), 0.1f);
		
		//Silver
		registerRecipe(ItemMaterials.dustSilver, ItemMaterials.ingotSilver, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.SilverOre)), ItemMaterials.ingotSilver, 0.1f);
		//Tin
		registerRecipe(ItemMaterials.dustTin, ItemMaterials.ingotTin, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.TinOre)), ItemMaterials.ingotTin, 0.1f);
		//Lead
		registerRecipe(ItemMaterials.dustLead, ItemMaterials.ingotLead, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.LeadOre)), ItemMaterials.ingotLead, 0.1f);
		//Copper
		registerRecipe(ItemMaterials.dustCopper, ItemMaterials.ingotCopper, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.CopperOre)), ItemMaterials.ingotCopper, 0.1f);
		//Platinum
		registerRecipe(ItemMaterials.dustPlatinum, ItemMaterials.ingotPlatinum, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.PlatinumOre)), ItemMaterials.ingotPlatinum, 0.1f);
		//Iron
		registerRecipe(ItemMaterials.dustIron, new ItemStack(Items.IRON_INGOT), 0.1f);
		//Gold
		registerRecipe(ItemMaterials.dustGold, new ItemStack(Items.GOLD_INGOT), 0.1f);
		//Static
		registerRecipe(ItemMaterials.dustStatic, ItemMaterials.ingotStatic, 0.1f);
		//Energized
		registerRecipe(ItemMaterials.dustEnergized, ItemMaterials.ingotEnergized, 0.1f);
		//Inert
		registerRecipe(ItemMaterials.dustInertInfusion, ItemMaterials.ingotInertInfusion, 0.1f);
		//Nickel
		registerRecipe(ItemMaterials.dustNickel, ItemMaterials.ingotNickel, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.NickelOre)), ItemMaterials.ingotNickel, 0.1f);
		//Aluminium
		registerRecipe(ItemMaterials.dustAluminium, ItemMaterials.ingotAluminium, 0.1f);
		registerRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.AluminiumOre)), ItemMaterials.ingotAluminium, 0.1f);
		//Redstone Alloy
		registerRecipe(ItemMaterials.dustRedstoneAlloy, ItemMaterials.ingotRedstoneAlloy, 0.1F);
	}
	public static void registerRecipe(ItemStack input, ItemStack output, float experience) {
		GameRegistry.addSmelting(input,  output, experience);
	}
	public static void registerFullRecipes() {
		registerSmeltingRecipes();
	}
}
