package theking530.staticpower.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theking530.staticpower.StaticPower;

public class InfusedWood extends BlockLog {
	
    public InfusedWood(String name) {
        setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setHarvestLevel("axe", 0);
        setHardness(2.0F);
    	setResistance(0.5F);
    	setSoundType(SoundType.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(StaticPower.StaticPower);
    }  
    public IBlockState getStateFromMeta(int meta) {
    	int vanillaMeta = meta;
		if (meta > 3) vanillaMeta = meta - 4;
        IBlockState iblockstate = this.getDefaultState();

        switch (vanillaMeta & 12) {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }
        return iblockstate;
    }

    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        int meta = 0;
		if (meta > 3) meta = meta - 4;
        i = i | meta;

        switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS)) {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }
    protected ItemStack createStackedBlock(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1);
    }
    public int damageDropped(IBlockState state) {
        return 0;
    }
}