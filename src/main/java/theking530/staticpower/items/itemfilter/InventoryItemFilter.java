package theking530.staticpower.items.itemfilter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryItemFilter extends ItemStackHandler {
	
	public final ItemStack owningItemStack;
	private boolean whiteListMode = true;
    private boolean checkMetadata = false;
    private boolean checkNBT = false;
    private boolean checkOreDictionary = false;
    private boolean checkModDomain = false;
	private FilterTier filterTier;
		
	public InventoryItemFilter(ItemStack stack, FilterTier tier) {
		super(tier.getSlotCount());
		owningItemStack = stack;
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}else{
			deserializeNBT(stack.getTagCompound());
		}
		filterTier = tier;
	}
	public FilterTier getFilterTier() {
		return filterTier;
	}
	public void setMatchMetadata(boolean mode) {
		checkMetadata = mode;
	}
	public boolean getMatchMetadata() {
		return checkMetadata;
	}
	public void setMatchNBT(boolean mode) {
		checkNBT = mode;
	}
	public boolean getMatchNBT() {
		return checkNBT;
	}
	
	public void setMatchOreDictionary(boolean mode) {
		checkOreDictionary = mode;
	}
	public boolean getMatchOreDictionary() {
		return checkOreDictionary;
	}
	public void setMatchModeID(boolean mode) {
		checkModDomain = mode;
	}
	public boolean getMatchModID() {
		return checkModDomain;
	}
	public void setWhiteListMode(boolean mode) {
		whiteListMode = mode;
	}
	public boolean getWhiteListMode() {
		return whiteListMode;
	}
	
	@Override
    public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = super.serializeNBT();
		compound.setBoolean("WHITE_LIST_MODE", whiteListMode);
		compound.setBoolean("MATCH_METADATA", checkMetadata);
		compound.setBoolean("MATCH_NBT", checkNBT);
		compound.setBoolean("MATCH_ORE_DICT", checkOreDictionary);	
		return compound;
	}
	@Override
	public void deserializeNBT(NBTTagCompound compound){
		super.deserializeNBT(compound);
		whiteListMode = compound.getBoolean("WHITE_LIST_MODE");
		checkMetadata = compound.getBoolean("MATCH_METADATA");
		checkNBT = compound.getBoolean("MATCH_NBT");
		checkOreDictionary = compound.getBoolean("MATCH_ORE_DICT");
	}
	@Override
    public int getSlotLimit(int slot) {
		return 1;
	}
	@Override
    protected void onContentsChanged(int slot) {
		owningItemStack.setTagCompound(serializeNBT());
    }
	public String getName() {
		String name = "ItemFilter";
		switch(filterTier) {
		case BASIC:
			name = "BasicItemFilter";
			break;
		case UPGRADED:
			name = "UpgradedItemFilter";
			break;
		case ADVANCED:
			name = "AdvancedItemFilter";
			break;
		}
		return "container." + name;
	}
}