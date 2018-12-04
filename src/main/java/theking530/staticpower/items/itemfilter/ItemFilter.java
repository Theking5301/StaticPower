package theking530.staticpower.items.itemfilter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.ItemUtilities;
import theking530.staticpower.blocks.ModBlocks;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.items.ItemBase;

public class ItemFilter extends ItemBase {
	
	public FilterTier filterTier;
	
	public ItemFilter(String name, FilterTier tier) {
		super(name);
		filterTier = tier;
	}
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
	ItemStack itemStack = player.getHeldItem(hand);
		if (world.isRemote) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
		}else if (!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, StaticPower.staticpower, GuiIDRegistry.guiIDItemFilter, world, 0, 0, 0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		}else{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
		}    
    }
	public void onCreated(ItemStack item, World world, EntityPlayer player) {
		super.onCreated(item, world, player);
	}
	public boolean evaluateFilter(ItemStack filter, ItemStack itemstack) {
		List<ItemStack> slots = new ArrayList<ItemStack>();

		if(filter.hasTagCompound()) {
			ItemStackHandler tempHandler = new ItemStackHandler();
			tempHandler.deserializeNBT(filter.getTagCompound());
			
			for(int i=0; i<tempHandler.getSlots(); i++) {
				if(!tempHandler.getStackInSlot(i).isEmpty()) {
					slots.add(tempHandler.getStackInSlot(i).copy());
				}
			}
			
			boolean whitelist = true;
			boolean matchNBT = false;
			boolean matchMetadata = true;
			boolean matchOreDict = false;
			
			if(filter.getTagCompound().hasKey("WHITE_LIST_MODE")) {
				whitelist = filter.getTagCompound().getBoolean("WHITE_LIST_MODE");
			}
			if(filter.getTagCompound().hasKey("MATCH_NBT")) {
				matchNBT = filter.getTagCompound().getBoolean("MATCH_NBT");
			}
			if(filter.getTagCompound().hasKey("MATCH_METADATA")) {
				matchMetadata = filter.getTagCompound().getBoolean("MATCH_METADATA");
			}
			if(filter.getTagCompound().hasKey("MATCH_ORE_DICT")) {
				matchOreDict = filter.getTagCompound().getBoolean("MATCH_ORE_DICT");
			}
			return ItemUtilities.filterItems(slots, itemstack, whitelist, matchMetadata, matchNBT, matchOreDict, false);
		}
		return false;
	}
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; 
	}
	@Override  
	public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {	
    	if(showHiddenTooltips()) {
    		boolean empty = true;
    		if(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Items")) {
        		ItemStackHandler tempHandler = new ItemStackHandler();
        		tempHandler.deserializeNBT(itemstack.getTagCompound());
        	
        		for(int i=0; i<tempHandler.getSlots(); i++) {
        			if(!tempHandler.getStackInSlot(i).isEmpty()) {
        				list.add("Slot " + (i+1) + ": " + tempHandler.getStackInSlot(i).getDisplayName());
        				empty=false;
        			}
        		}
    		}
    		if(empty) {
    			list.add(EnumTextFormatting.ITALIC + "Empty");
    		}
    	}else{
    		list.add(EnumTextFormatting.ITALIC + "Hold Shift");
	    }
	}
	public static void writePremadeFilter(ItemStack stack, ItemStack...filterItems) {
		ItemStackHandler tempHandler = new ItemStackHandler(filterItems.length);
		NBTTagCompound tagCompound = tempHandler.serializeNBT();
		
		for (int i = 0; i < filterItems.length; ++i){
			if (!filterItems[i].isEmpty()){
				tempHandler.insertItem(i, filterItems[i], false);
			}
		}
		stack.setTagCompound(tagCompound);
	}
	public static void writeQuarryFilter(ItemStack stack) {
		ItemStack[] tempItems = new ItemStack[7];
		
		tempItems[0] = new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE));
		tempItems[1] = new ItemStack(Item.getItemFromBlock(Blocks.DIRT));
		tempItems[2] = new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 1);
		tempItems[3] = new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 3);
		tempItems[4] = new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 5);
		tempItems[5] = new ItemStack(Item.getItemFromBlock(Blocks.GRAVEL));
		tempItems[6] = new ItemStack(Items.FLINT);
		writePremadeFilter(stack, tempItems);
		stack.getTagCompound().setBoolean("WHITE_LIST_MODE", false);
		stack.setStackDisplayName("Quarry Filter");
	}
	public static void writeOreFilter(ItemStack stack) {
		ItemStack[] tempItems = new ItemStack[7];
		
		tempItems[0] = new ItemStack(Item.getItemFromBlock(Blocks.IRON_ORE));
		tempItems[1] = new ItemStack(Item.getItemFromBlock(Blocks.GOLD_ORE));
		tempItems[2] = new ItemStack(Item.getItemFromBlock(ModBlocks.CopperOre));
		tempItems[3] = new ItemStack(Item.getItemFromBlock(ModBlocks.TinOre));
		tempItems[4] = new ItemStack(Item.getItemFromBlock(ModBlocks.SilverOre));
		tempItems[5] = new ItemStack(Item.getItemFromBlock(ModBlocks.LeadOre));
		tempItems[6] = new ItemStack(Item.getItemFromBlock(ModBlocks.NickelOre));

		writePremadeFilter(stack, tempItems);
		stack.getTagCompound().setBoolean("WHITE_LIST_MODE", true);
		stack.getTagCompound().setBoolean("MATCH_ORE_DICT", true);
		stack.setStackDisplayName("Ore Filter");
	}
	public static void writeAdvancedOreFilter(ItemStack stack) {
		ItemStack[] tempItems = new ItemStack[9];
		
		tempItems[0] = new ItemStack(Item.getItemFromBlock(Blocks.IRON_ORE));
		tempItems[1] = new ItemStack(Item.getItemFromBlock(Blocks.GOLD_ORE));
		tempItems[2] = new ItemStack(Item.getItemFromBlock(ModBlocks.CopperOre));
		tempItems[3] = new ItemStack(Item.getItemFromBlock(ModBlocks.TinOre));
		tempItems[4] = new ItemStack(Item.getItemFromBlock(ModBlocks.SilverOre));
		tempItems[5] = new ItemStack(Item.getItemFromBlock(ModBlocks.LeadOre));
		tempItems[6] = new ItemStack(Item.getItemFromBlock(ModBlocks.NickelOre));
		tempItems[7] = new ItemStack(Item.getItemFromBlock(ModBlocks.RubyOre));
		tempItems[8] = new ItemStack(Item.getItemFromBlock(ModBlocks.SapphireOre));		
		
		writePremadeFilter(stack, tempItems);
		stack.getTagCompound().setBoolean("WHITE_LIST_MODE", true);
		stack.getTagCompound().setBoolean("MATCH_ORE_DICT", true);
		stack.setStackDisplayName("Advanced Ore Filter");
	}
}
