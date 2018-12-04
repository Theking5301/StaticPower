package theking530.staticpower.items;

import net.minecraft.item.ItemFood;
import theking530.staticpower.StaticPower;

public class BaseFood extends ItemFood {

	public String NAME = "";
	
	public BaseFood(String name, int hungerShanks, float saturation) {
		super(hungerShanks, saturation, false);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	public BaseFood(String name, int hungerShanks) {
		this(name, hungerShanks, 0.6f);
	}

}
