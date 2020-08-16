package theking530.staticpower.init;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.utilities.Reference;

public class ModTags {
	public static final Tag<Item> LOG = ItemTags.getCollection().getOrCreate(new ResourceLocation("minecraft", "logs"));
	public static final Tag<Item> LEAVES = ItemTags.getCollection().getOrCreate(new ResourceLocation("minecraft", "leaves"));
	public static final Tag<Item> SAPLING = ItemTags.getCollection().getOrCreate(new ResourceLocation("minecraft", "saplings"));
	public static final Tag<Item> COVER_SAW = ItemTags.getCollection().getOrCreate(new ResourceLocation(Reference.MOD_ID, "saw"));
	public static final Tag<Item> FARMING_AXE = ItemTags.getCollection().getOrCreate(new ResourceLocation(Reference.MOD_ID, "farming_axe"));
	public static final Tag<Item> FARMING_HOE = ItemTags.getCollection().getOrCreate(new ResourceLocation(Reference.MOD_ID, "farming_hoe"));
	public static final Tag<Item> MINER_ORE = ItemTags.getCollection().getOrCreate(new ResourceLocation(Reference.MOD_ID, "miner_ores"));
}