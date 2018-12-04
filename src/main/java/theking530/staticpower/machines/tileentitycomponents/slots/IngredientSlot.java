package theking530.staticpower.machines.tileentitycomponents.slots;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

public class IngredientSlot extends StaticPowerContainerSlot {

	private Ingredient filter;
	
	public IngredientSlot(ItemStack preview, Ingredient ingredient, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(preview, itemHandler, index, xPosition, yPosition);
		filter = ingredient;
	}
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
		return !stack.isEmpty() && filter.apply(stack);	          
    }

}
