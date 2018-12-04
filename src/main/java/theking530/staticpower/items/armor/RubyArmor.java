package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class RubyArmor extends BaseArmor {

	public RubyArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Pay a Bodyguard: " + EnumTextFormatting.RED + "-12 Damage");
		tempList.add("Luck: " + EnumTextFormatting.DARK_AQUA + "+4 Luck");
		tempList.add("Don't Get Lost: " + EnumTextFormatting.GOLD + "Glowing");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Ouch. Sharp.";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.GLOWING, 2, 0), new PotionEffect(MobEffects.WEAKNESS, 4, 2)
				, new PotionEffect(MobEffects.LUCK, 4, 3)};
	}
}
