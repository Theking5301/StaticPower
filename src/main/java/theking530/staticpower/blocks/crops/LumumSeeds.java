package theking530.staticpower.blocks.crops;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LumumSeeds extends CropSeeds {

    public LumumSeeds(Block blockCrop, Block blockSoil) {
        super("LumumSeeds", blockCrop, blockSoil);     
    }  
    @Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    	tooltip.add("These Seeds Radiate with");
    	tooltip.add("a Strange Energy...");
    }
}