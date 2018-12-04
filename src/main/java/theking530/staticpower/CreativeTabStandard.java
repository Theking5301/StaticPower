package theking530.staticpower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.blocks.crops.ModPlants;
 
public class CreativeTabStandard extends CreativeTabs {
 
    public CreativeTabStandard(int id, String unlocalizedName) {
 
        super(id, unlocalizedName);
    }
 
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(ModPlants.EnergizedCrop);
    }
}