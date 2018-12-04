package theking530.staticpower.items;

import org.lwjgl.input.Keyboard;

import net.minecraft.item.Item;
import theking530.staticpower.StaticPower;
import theking530.staticpower.assists.utilities.EnumTextFormatting;

public class ItemBase extends Item implements EnumTextFormatting {
	
	public String NAME = "";
	public ItemBase(String name) {
		NAME = name;
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName(name);
		setRegistryName(name);
		//setMaxStackSize(64);
	}
	public boolean showHiddenTooltips() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
}
