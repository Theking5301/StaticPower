package theking530.staticpower.world.plants;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnergizedSeeds extends CropSeeds {
    
    public EnergizedSeeds(Block blockCrop, Block blockSoil) {
        super("EnergizedSeeds", blockCrop, blockSoil);
    }
    
    @Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	tooltip.add("These Seeds Radiate with");
    	tooltip.add("a Strange Energy...");
    }
}