package theking530.staticpower.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.items.IVariantItem;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.MultiItem;
import theking530.staticpower.items.MultiItem.MultiItemWrapper;

public class ItemRenderRegistry {
	
	public static final List<Block> blocks = new ArrayList<Block>();
	public static final List<Item> items = new ArrayList<Item>();
	public static final List<MultiItem> multiItems = new ArrayList<MultiItem>();
	
	public static void addItem(Item item) {
		items.add(item);
	}
	public static void addMultiItem(MultiItem multiItem){
		multiItems.add(multiItem);
	}
	public static void addBlock(Block block){
		blocks.add(block);
	}
	
	public static void initItemRenderers() {
		for(Item item : items) {
			registerItem(item);
		}
		for(MultiItem multiItem : multiItems) {
		    registerMultiItem(multiItem);
		}	
		for(Block block : blocks) {
			registerBlock(block);
		}
		
		registerTemporaryLogicGate(ModBlocks.Adder);
		registerTemporaryLogicGate(ModBlocks.Subtractor);
		registerTemporaryLogicGate(ModBlocks.Timer);
		registerTemporaryLogicGate(ModBlocks.And);
		registerTemporaryLogicGate(ModBlocks.Or);
		registerTemporaryLogicGate(ModBlocks.SignalMultiplier);
		registerTemporaryLogicGate(ModBlocks.PowerCell);
		registerTemporaryLogicGate(ModBlocks.LED);
		registerTemporaryLogicGate(ModBlocks.NotGate);
		
	    registerCannister(ModItems.BaseFluidCapsule, "basefluidcapsule");
	    registerCannister(ModItems.StaticFluidCapsule, "staticfluidcapsule");
	    registerCannister(ModItems.EnergizedFluidCapsule, "energizedfluidcapsule");
	    registerCannister(ModItems.LumumFluidCapsule, "lumumfluidcapsule");
	    
	    registerItemWithVariants(ModItems.DigistoreCapacityUpgrade);
	    registerItemWithVariants(ModItems.DigistoreMiscUpgrade);
	}
    public static void registerCannister(Item item, String blockstate) {
    	ModelResourceLocation location =  new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, blockstate), "inventory");
		ModelLoader.setCustomMeshDefinition(item, stack -> location);
	    ModelBakery.registerItemVariants(item, location);
    }
    public static void registerItem(Item item, int metadata) {
    	ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
    public static void registerItem(Item item) {
    	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
    public static void registerBlock(Block block, int metadata) {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
    public static void registerBlock(Block block) {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
    public static void registerTemporaryLogicGate(Block block) {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(ModBlocks.LogicGateBasePlate.getRegistryName(), "inventory"));
    }
    
    public static void registerItemWithVariants(Item baseItem) {
    	if(baseItem instanceof IVariantItem) {
    		IVariantItem variantItem = (IVariantItem)baseItem;
        	Map<ItemStack, String> subItems = variantItem.getSubItemMap();
        	for(Entry<ItemStack, String> entry : subItems.entrySet()) {
        		registerItemModelForMeta(baseItem, entry.getKey().getMetadata(), "model" + "=" + entry.getValue());
        	}
    	}
	}
    public static void registerMultiItem(Item baseItem) {
    	if(baseItem instanceof MultiItem) {
    		MultiItem variantItem = (MultiItem)baseItem;
        	Map<Integer, MultiItemWrapper> subItems = variantItem.getSubItemMap();
        	for(Entry<Integer, MultiItemWrapper> entry : subItems.entrySet()) {
        		registerItemModelForMeta(baseItem, entry.getValue().getMetadata(), "variant" + "=" + entry.getValue().getName());
        	}
    	}
	}
	private static void registerItemModelForMeta(Item item, int metadata, String variant) {
		ModelResourceLocation res = new ModelResourceLocation(item.getRegistryName(), variant);
		registerItemModelForMeta(item, metadata, res);
	}
	private static void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
	}
}
