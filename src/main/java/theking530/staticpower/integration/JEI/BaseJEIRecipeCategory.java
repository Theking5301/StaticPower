package theking530.staticpower.integration.JEI;


import javax.annotation.Nonnull;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseJEIRecipeCategory <E extends IRecipeWrapper>implements IRecipeCategory<E>{

    public void initialize(@Nonnull IModRegistry registry) {
    	
    
    }

}
