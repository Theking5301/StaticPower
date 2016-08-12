package theking530.staticpower.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.RegisterHelper;
import theking530.staticpower.blocks.BaseItemBlock;

public class ObsidianGlass extends Block{

	public ObsidianGlass(Material material) {
		super(material.GLASS);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName("ObsidianGlass");	
		setRegistryName("ObsidianGlass");
		setSoundType(SoundType.GLASS);
		RegisterHelper.registerItem(new BaseItemBlock(this, "ObsidianGlass"));
	}
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
    public boolean isFullCube(IBlockState state){
        return false;
    }
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}

