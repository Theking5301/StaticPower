package theking530.staticpower.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import theking530.staticpower.StaticPower;

public class BaseBlock extends Block{

	public BaseBlock(Material materialIn, String name) {
		this(materialIn, name, "pickaxe", 0, 3.0f);
	}
	public BaseBlock(Material materialIn, String name, String tool, int level) {
		this(materialIn, name, tool, level, 3.0f);
	}
	public BaseBlock(Material materialIn, String name, float hardness) {
		this(materialIn, name, "pickaxe", 0, hardness);
	}
	public BaseBlock(Material materialIn, String name, String tool, int level, float hardness) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel(tool, level);
		setHardness(hardness);
		setCreativeTab(StaticPower.StaticPower);
	}
}
