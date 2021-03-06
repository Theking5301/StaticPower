package theking530.staticpower.cables.digistore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import theking530.api.digistore.IDigistoreInventory;
import theking530.staticpower.cables.attachments.digistore.craftinginterface.DigistoreCraftingInterfaceAttachment;
import theking530.staticpower.cables.attachments.digistore.patternencoder.DigistorePatternEncoder.RecipeEncodingType;
import theking530.staticpower.cables.attachments.digistore.terminalbase.DigistoreInventorySortType;
import theking530.staticpower.cables.digistore.crafting.CraftingInterfaceWrapper;
import theking530.staticpower.cables.digistore.crafting.EncodedDigistorePattern;
import theking530.staticpower.tileentities.digistorenetwork.patternstorage.TileEntityPatternStorage;
import theking530.staticpower.utilities.InventoryUtilities;
import theking530.staticpower.utilities.ItemUtilities;

public class DigistoreInventorySnapshot implements IItemHandler {
	public static final String METADATA_TAG = "digistore_meta";
	public static final String CRAFTABLE_TAG = "craftable";

	public enum DigistoreItemCraftableState {
		NONE, CRAFTABLE, ONLY_CRAFTABLE
	}

	public static final DigistoreInventorySnapshot EMPTY = new DigistoreInventorySnapshot();
	private final List<ItemStack> stacks;
	private final Map<ItemStack, List<EncodedDigistorePattern>> craftingItemPatterns;
	private final String filterString;
	private final DigistoreInventorySortType sortType;
	private final boolean sortDescending;
	private final boolean simulated;
	private boolean isEmpty;

	private DigistoreNetworkModule module;

	public DigistoreInventorySnapshot(DigistoreNetworkModule module, String filter, DigistoreInventorySortType sortType, boolean sortDescending) {
		this(module, filter, sortType, sortDescending, true);
	}

	public DigistoreInventorySnapshot(DigistoreInventorySnapshot otherSnapshot) {
		this(null, "", DigistoreInventorySortType.COUNT, true);

		// Copy the stacks.
		for (ItemStack stack : otherSnapshot.stacks) {
			stacks.add(stack.copy());
		}

		// But just add references to the recipes.
		craftingItemPatterns.putAll(otherSnapshot.craftingItemPatterns);
	}

	public DigistoreInventorySnapshot(DigistoreNetworkModule module, String filter, DigistoreInventorySortType sortType, boolean sortDescending, boolean simulated) {
		this.module = module;
		this.sortType = sortType;
		this.sortDescending = sortDescending;
		this.simulated = simulated;
		stacks = new ArrayList<ItemStack>();
		filterString = filter.toLowerCase();
		craftingItemPatterns = new HashMap<ItemStack, List<EncodedDigistorePattern>>();

		// Perform an initial update when first created.
		if (module != null) {
			update();
			isEmpty = false;
		} else {
			isEmpty = true;
		}
	}

	private DigistoreInventorySnapshot() {
		this(null, "", DigistoreInventorySortType.COUNT, true);
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void update() {
		// Clear the stacks list.
		stacks.clear();

		// Populate the stacks.
		for (IDigistoreInventory digistore : module.getAllDigistores()) {
			for (int i = 0; i < digistore.getUniqueItemCapacity(); i++) {
				// Stack in slot.
				ItemStack stackInSlot = digistore.getDigistoreStack(i).getStoredItem();

				// Skip empty slots.
				if (stackInSlot.isEmpty()) {
					continue;
				}

				// Cache the item in the hash set.
				ItemStack stackToCache = stackInSlot.copy();
				stackToCache.setCount(digistore.getDigistoreStack(i).getCount());
				cacheOrIncreaseItemCount(stackToCache);
			}
		}

		// Add the craftable items.
		for (TileEntityPatternStorage constructor : module.getConstructors()) {
			// Iterate through all the patterns.
			for (ItemStack pattern : constructor.patternInventory) {
				// If we're able to get the encoded pattern and it is for a crafting table.
				EncodedDigistorePattern encodedPattern = EncodedDigistorePattern.readFromPatternCard(pattern);
				if (encodedPattern != null && encodedPattern.getRecipeType() == RecipeEncodingType.CRAFTING_TABLE) {
					// Get the first output (as that's the only one that matters).
					ItemStack output = encodedPattern.getOutput();

					// Cache the craftable.
					cacheCraftable(output, encodedPattern);
				}
			}
		}

		// Add the machine craftable items.
		for (CraftingInterfaceWrapper craftingInterface : module.getCraftingInterfaces()) {
			List<EncodedDigistorePattern> patterns = DigistoreCraftingInterfaceAttachment.getAllPaternsInInterface(craftingInterface.getAttachment());
			for (EncodedDigistorePattern pattern : patterns) {
				// Get the first output (as that's the only one that matters).
				ItemStack output = pattern.getOutput();

				// Cache the craftable.
				cacheCraftable(output, pattern);
			}
		}

		// Only sort if requested.
		if (sortType != null) {
			// Get the sort direction modifier.
			int sortModifier = sortDescending ? 1 : -1;

			// Filter the stacks based on the filter string.
			for (int i = stacks.size() - 1; i >= 0; i--) {
				ItemStack stack = stacks.get(i);
				// Skip items that don't match the filter.
				if (filterString.length() > 0) {
					if (filterString.startsWith("@") && filterString.length() > 1) {
						if (!stack.getItem().getRegistryName().getNamespace().toLowerCase().contains(filterString.substring(1))) {
							stacks.remove(i);
						}
					} else if (filterString.startsWith("$") && filterString.length() > 1) {
						// Set up a flag to indicate if it was found by tag.
						boolean found = false;

						// Loop through the tags and indicate if we find a match.
						for (ResourceLocation tag : stack.getItem().getTags()) {
							if (tag.getPath().toLowerCase().contains(filterString.substring(1))) {
								found = true;
								break;
							}
						}

						// If no match is found, skip this item.
						if (!found) {
							stacks.remove(i);
						}
					} else if (!stack.getDisplayName().getString().toLowerCase().contains(this.filterString)) {
						stacks.remove(i);
					}
				}
			}

			// Sort by the requested sort type.
			if (sortType == DigistoreInventorySortType.COUNT) {
				// Sort by stack size.
				stacks.sort(new Comparator<ItemStack>() {
					@Override
					public int compare(ItemStack o1, ItemStack o2) {
						return (o2.getCount() - o1.getCount()) * sortModifier;
					}
				});
			} else {
				// Sort by stack name.
				stacks.sort(new Comparator<ItemStack>() {
					@Override
					public int compare(ItemStack o1, ItemStack o2) {
						return (o1.getDisplayName().getString().compareToIgnoreCase(o2.getDisplayName().getString())) * sortModifier;
					}
				});
			}
		}

		// Safety check here. After capturing all the data, we set the module to null to
		// ENSURE we crash if we attempt to modify the module in any way.
		if (simulated) {
			module = null;
		}
	}

	@Override
	public int getSlots() {
		return stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!simulated) {
			return module.insertItem(stack, simulate);
		} else {
			cacheOrIncreaseItemCount(stack);
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack stackInSlot = stacks.get(slot);
		if (!simulated) {
			return module.extractItem(stackInSlot, amount, simulate);
		} else {
			ItemStack output = stackInSlot.copy();
			if (getCraftableStateOfItem(output) == DigistoreItemCraftableState.ONLY_CRAFTABLE) {
				return ItemStack.EMPTY;
			}

			output.setCount(Math.min(output.getCount(), amount));
			if (simulate) {
				stackInSlot.shrink(amount);
			}
			return output;
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}

	public List<EncodedDigistorePattern> getAllPatternsForIngredient(Ingredient ingredient) {
		List<EncodedDigistorePattern> output = new ArrayList<EncodedDigistorePattern>();
		for (ItemStack key : craftingItemPatterns.keySet()) {
			if (ingredient.test(key)) {
				output.addAll(craftingItemPatterns.get(key));
			}
		}
		return output;
	}

	public List<EncodedDigistorePattern> getAllPatternsForItem(ItemStack item) {
		for (ItemStack key : craftingItemPatterns.keySet()) {
			if (ItemUtilities.areItemStacksStackable(key, item)) {
				return craftingItemPatterns.get(key);
			} else {
				for (ResourceLocation resource : item.getItem().getTags()) {
					if (key.getItem().getTags().contains(resource)) {
						return craftingItemPatterns.get(key);
					}
				}
			}
		}
		return Collections.emptyList();
	}

	public int getSlotForItem(ItemStack stack) {
		for (int i = 0; i < stacks.size(); i++) {
			if (ItemUtilities.areItemStacksStackable(stack, stacks.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public int extractWithIngredient(Ingredient ingredient, int amount, boolean simulate) {
		return extractWithIngredient(ingredient, amount, null, simulate);
	}

	public int extractWithIngredient(Ingredient ingredient, int amount, List<ItemStack> items, boolean simulate) {
		if (amount == 0) {
			return 0;
		}

		int remaining = amount;
		for (int i = 0; i < getSlots(); i++) {
			if (ingredient.test(getStackInSlot(i))) {
				ItemStack extracted = extractItem(i, remaining, simulate);
				remaining -= extracted.getCount();
				if (items != null && !extracted.isEmpty()) {
					items.add(extracted);
				}

				if (remaining == 0) {
					break;
				}
			}
		}
		return amount - remaining;
	}

	public ItemStack insertItemStack(ItemStack stack, boolean simulate) {
		return InventoryUtilities.insertItemIntoInventory(this, stack, simulate);
	}

	protected void cacheCraftable(ItemStack stack, EncodedDigistorePattern pattern) {
		// Create a copy of the output item to cache, and set the count to 1.
		ItemStack stackToCache = stack.copy();
		stackToCache.setCount(1);

		// Check if we have already cached this item. If we already have cached this,
		// just add this new pattern to the existing map entry.
		boolean patternCached = false;
		for (ItemStack key : craftingItemPatterns.keySet()) {
			if (ItemUtilities.areItemStacksStackable(key, stackToCache)) {
				craftingItemPatterns.get(key).add(pattern);
				patternCached = true;
			}
		}

		// Otherwise, create a new map entry.
		if (!patternCached) {
			LinkedList<EncodedDigistorePattern> patternList = new LinkedList<EncodedDigistorePattern>();
			patternList.add(pattern);
			craftingItemPatterns.put(stackToCache.copy(), patternList);
		}

		// Iterate through all the craftables to mark them with their craftable state.
		for (ItemStack cached : stacks) {
			// Get the stack stripped.
			ItemStack strippedCached = stripMetadataTags(cached.copy());

			// If the item is already cached, mark it as craftable.
			if (ItemUtilities.areItemStacksStackable(strippedCached, stackToCache)) {
				// If we already have a craftable stack for this item, return early.
				if (getCraftableStateOfItem(cached) == DigistoreItemCraftableState.ONLY_CRAFTABLE) {
					return;
				}

				addCraftableMetadata(cached, DigistoreItemCraftableState.CRAFTABLE);
				return;
			}
		}

		// If we made it this far, we never found the item. Add it and mark it as
		// craftable. If it does not have a tag, add one to store the craftable state.
		if (!stackToCache.hasTag()) {
			stackToCache.setTag(new CompoundNBT());
		}
		// Mark the craftable state and add it to the list.
		addCraftableMetadata(stackToCache, DigistoreItemCraftableState.ONLY_CRAFTABLE);
		stacks.add(stackToCache);
	}

	protected void cacheOrIncreaseItemCount(ItemStack stack) {
		for (ItemStack test : stacks) {
			if (ItemUtilities.areItemStacksStackable(test, stack)) {
				test.grow(stack.getCount());
				return;
			}
		}

		stacks.add(stack);
	}

	protected static void addCraftableMetadata(ItemStack stack, DigistoreItemCraftableState craftableState) {
		// If the stack does not have a tag, add it.
		if (!stack.hasTag()) {
			stack.setTag(new CompoundNBT());
		}

		// If the stack does not have a metadata tag, add it.
		if (!stack.getTag().contains(METADATA_TAG)) {
			stack.getTag().put(METADATA_TAG, new CompoundNBT());
		}

		// Put the craftable state.
		stack.getTag().getCompound(METADATA_TAG).putInt(CRAFTABLE_TAG, craftableState.ordinal());
	}

	protected static @Nullable CompoundNBT getMetadataTag(ItemStack stack) {
		// If the stack does not have a tag, return null.
		if (!stack.hasTag()) {
			return null;
		}

		// If the stack does not have a metadata tag, return null.
		if (!stack.getTag().contains(METADATA_TAG)) {
			return null;
		}

		// Return the metadata tag.
		return stack.getTag().getCompound(METADATA_TAG);
	}

	public static ItemStack stripMetadataTags(ItemStack stack) {
		if (stack.hasTag()) {
			stack.getTag().remove(METADATA_TAG);
			if (stack.getTag().keySet().size() == 0) {
				stack.setTag(null);
			}
		}
		return stack;
	}

	public static DigistoreItemCraftableState getCraftableStateOfItem(ItemStack stack) {
		// Get the metadata.
		CompoundNBT metadata = getMetadataTag(stack);

		// If metadata does not exist, then this must not be craftable.
		if (metadata == null) {
			return DigistoreItemCraftableState.NONE;
		}

		// Otherwise, get the craftable tag.
		return DigistoreItemCraftableState.values()[metadata.getInt(CRAFTABLE_TAG)];
	}
}
