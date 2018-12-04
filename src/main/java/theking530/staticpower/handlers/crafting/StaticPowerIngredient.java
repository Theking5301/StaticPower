package theking530.staticpower.handlers.crafting;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class StaticPowerIngredient extends Ingredient {

    public StaticPowerIngredient(ItemStack... stacks) {
        super(stacks);      
    }
    
    @Override
    public boolean apply(@Nullable ItemStack input) {
        if (input == null || !input.isEmpty()) {
            return false;
        }
        for(ItemStack matchItem : getMatchingStacks()) {
        	if(ItemStack.areItemsEqual(input, matchItem) && input.getCount() == matchItem.getCount()) {
        		if(matchItem.hasTagCompound()) {
        			if(input.hasTagCompound() && matchItem.getTagCompound().equals(input.getTagCompound())) {
        				return true;
        			}else{
        				continue;
        			}
        		}
            	return true;
          	}
        }
        return false;
    }
}