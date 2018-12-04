package theking530.staticpower.handlers.crafting.registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.handlers.crafting.wrappers.SolderingRecipeWrapper;

public class SolderingRecipeRegistry {

    private ArrayList<SolderingRecipeWrapper> recipeList = new ArrayList<SolderingRecipeWrapper>();
    private static final SolderingRecipeRegistry SOLDERING_BASE = new SolderingRecipeRegistry();
	
    public static final SolderingRecipeRegistry Soldering() {
    	return SOLDERING_BASE;
    }
    public SolderingRecipeWrapper addRecipe(SolderingRecipeWrapper wrapper) {
        recipeList.add(wrapper);
        return wrapper;
    }
    public List<SolderingRecipeWrapper> getRecipeList(){
        return recipeList;
    }
    public SolderingRecipeWrapper getRecipe(IItemHandler inv, World worldIn, int craftingInvWidth, int craftingInvHeight) {
    	for(SolderingRecipeWrapper wrapper : recipeList) {
    		if(wrapper.matches(inv, worldIn, craftingInvWidth, craftingInvHeight)) {
    			return wrapper;
    		}
    	}
    	return null;
    }
}
