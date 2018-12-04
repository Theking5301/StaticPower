package theking530.staticpower.handlers.crafting.recipes;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.handlers.crafting.Craft;
import theking530.staticpower.handlers.crafting.StaticPowerIngredient;

public class EsotericEnchanterRecipes {

	public static void registerEsotericEnchanterRecipes() {
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.AQUA_AFFINITY), Craft.ing(Items.BOOK), Craft.ing(Items.PRISMARINE_SHARD), Craft.ing(Items.GLASS_BOTTLE), new FluidStack(ModFluids.LiquidExperience, 500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.BANE_OF_ARTHROPODS), Craft.ing(Items.BOOK), Craft.ing(Items.FERMENTED_SPIDER_EYE), Craft.ing(Items.STRING), new FluidStack(ModFluids.LiquidExperience, 500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.BLAST_PROTECTION), Craft.ing(Items.BOOK), Craft.ing(Blocks.TNT), Craft.ing(Blocks.OBSIDIAN), new FluidStack(ModFluids.LiquidExperience, 650));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.BINDING_CURSE), Craft.ing(Items.BOOK), Craft.ing(Items.SLIME_BALL), Craft.ing(Items.FISHING_ROD), new FluidStack(ModFluids.LiquidExperience, 3000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.VANISHING_CURSE), Craft.ing(Items.BOOK), Craft.ing(Items.ENDER_EYE), new FluidStack(ModFluids.LiquidExperience, 3000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.DEPTH_STRIDER), Craft.ing(Items.BOOK), Craft.ing(Items.LEATHER_BOOTS), Craft.ing(Items.GOLDEN_BOOTS), new FluidStack(ModFluids.LiquidExperience, 2000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.EFFICIENCY), Craft.ing(Items.BOOK), Craft.ing(Blocks.REDSTONE_BLOCK), Craft.ing(Items.QUARTZ), new FluidStack(ModFluids.LiquidExperience, 1000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FEATHER_FALLING), Craft.ing(Items.BOOK), Craft.ing(Items.FEATHER), Craft.ing(Blocks.SLIME_BLOCK), new FluidStack(ModFluids.LiquidExperience, 1000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FIRE_ASPECT), Craft.ing(Items.BOOK), Craft.ing(Items.BLAZE_ROD), Craft.ing(Items.MAGMA_CREAM), new FluidStack(ModFluids.LiquidExperience, 1250));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FIRE_PROTECTION), Craft.ing(Items.BOOK), Craft.ing(Items.MAGMA_CREAM), Craft.ing(Items.NETHERBRICK), new FluidStack(ModFluids.LiquidExperience, 1500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FLAME), Craft.ing(Items.BOOK), Craft.ing(Items.BLAZE_POWDER), Craft.ing(Items.GHAST_TEAR), new FluidStack(ModFluids.LiquidExperience, 2000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FORTUNE), Craft.ing(Items.BOOK), Craft.ing(Items.DIAMOND), Craft.ing(new ItemStack(Items.DYE, 1, 4)), new FluidStack(ModFluids.LiquidExperience, 2000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.FROST_WALKER), Craft.ing(Items.BOOK), Craft.ing(Blocks.ICE), Craft.ing(Items.WATER_BUCKET), new FluidStack(ModFluids.LiquidExperience, 1000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.INFINITY), Craft.ing(Items.BOOK), Craft.ing(Items.NETHER_STAR), Craft.ing(Items.ARROW), new FluidStack(ModFluids.LiquidExperience, 4000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.KNOCKBACK), Craft.ing(Items.BOOK), Craft.ing(Blocks.PISTON), new FluidStack(ModFluids.LiquidExperience, 500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.LOOTING), Craft.ing(Items.BOOK), Craft.ing(Items.DIAMOND), Craft.ing(Items.EMERALD), new FluidStack(ModFluids.LiquidExperience, 1250));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.LUCK_OF_THE_SEA), Craft.ing(Items.BOOK), Craft.ing(Items.PRISMARINE_SHARD), Craft.ing(Items.GOLD_NUGGET), new FluidStack(ModFluids.LiquidExperience, 750));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.LURE), Craft.ing(Items.BOOK), Craft.ing(Items.FISHING_ROD), Craft.ing(Items.FISH), new FluidStack(ModFluids.LiquidExperience, 750));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.MENDING), Craft.ing(Items.BOOK), Craft.ing(Items.NETHER_STAR), Craft.ing(Items.GOLDEN_APPLE), new FluidStack(ModFluids.LiquidExperience, 2000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.PROTECTION), Craft.ing(Items.BOOK), Craft.ing(Items.IRON_CHESTPLATE), new FluidStack(ModFluids.LiquidExperience, 750));
		
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.POWER), Craft.ing(Items.BOOK), Craft.ing(Blocks.PISTON), Craft.ing(Blocks.TNT), new FluidStack(ModFluids.LiquidExperience, 1000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.PROJECTILE_PROTECTION), Craft.ing(Items.BOOK), Craft.ing(Items.IRON_CHESTPLATE), Craft.ing(Items.ARROW), new FluidStack(ModFluids.LiquidExperience, 3000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.PUNCH), Craft.ing(Items.BOOK), Craft.ing(Blocks.PISTON), Craft.ing(Blocks.SLIME_BLOCK), new FluidStack(ModFluids.LiquidExperience, 500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.RESPIRATION), Craft.ing(Items.BOOK), Craft.ing(Items.GLASS_BOTTLE), Craft.ing(Items.PRISMARINE_CRYSTALS), new FluidStack(ModFluids.LiquidExperience, 2000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.SHARPNESS), Craft.ing(Items.BOOK), Craft.ing(Items.DIAMOND), Craft.ing(Items.QUARTZ), new FluidStack(ModFluids.LiquidExperience, 1000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.SILK_TOUCH), Craft.ing(Items.BOOK), Craft.ing(Items.SLIME_BALL), Craft.ing(Blocks.GRASS), new FluidStack(ModFluids.LiquidExperience, 3000));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.SMITE), Craft.ing(Items.BOOK), Craft.ing(Items.DIAMOND_SWORD), new FluidStack(ModFluids.LiquidExperience, 2750));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.SWEEPING), Craft.ing(Items.BOOK), Craft.ing(Items.IRON_SWORD), Craft.ing(Items.IRON_SWORD), new FluidStack(ModFluids.LiquidExperience, 2500));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.THORNS), Craft.ing(Items.BOOK), Craft.ing(Blocks.CACTUS), new FluidStack(ModFluids.LiquidExperience, 2250));
		RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(Enchantments.UNBREAKING), Craft.ing(Items.BOOK), Craft.ing(Blocks.DIAMOND_BLOCK), Craft.ing(Blocks.OBSIDIAN), new FluidStack(ModFluids.LiquidExperience, 1000));
		
		
		java.util.Iterator<Enchantment> it = Enchantment.REGISTRY.iterator();
		do {
			registerCombinationRecipe(it.next());
		}while(it.hasNext());
	}
	public static void registerCombinationRecipe(Enchantment enchantment) {
		if(enchantment.getMaxLevel() <= 1) {
			return;
		}
		for(int i=enchantment.getMinLevel(); i<enchantment.getMaxLevel(); i++) {
			RegisterHelper.registerEsotericEnchanterRecipe(createEnchantedBook(enchantment, i+1), new StaticPowerIngredient(createEnchantedBook(enchantment, i)),new StaticPowerIngredient(createEnchantedBook(enchantment, i)), new FluidStack(ModFluids.LiquidExperience, 1000*(i+1)));
		}
	}
	public static ItemStack createEnchantedBook(Enchantment enchantment, int level) {
		ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
		ItemEnchantedBook.addEnchantment(book, new EnchantmentData(enchantment, level));
		return book;
	}
	public static ItemStack createEnchantedBook(Enchantment enchantment) {
		return createEnchantedBook(enchantment, 1);
	}
}
