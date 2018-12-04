package theking530.staticpower.blocks.decorative;


import net.minecraft.block.material.Material;
import theking530.staticpower.blocks.BaseBlock;

public class Lamp extends BaseBlock {

	public Lamp(String name) {
		super(Material.GLASS, name);
	    setLightLevel(1.0F);
	}
}
