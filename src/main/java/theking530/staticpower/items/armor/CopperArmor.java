package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class CopperArmor extends BaseArmor {

	public CopperArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Black Lung: " + EnumTextFormatting.RED + "-1 Hunger Per 10 Seconds");
		tempList.add("Dying Industry: " + EnumTextFormatting.RED + "-4 Luck");
		tempList.add("Thanks Donald: " + EnumTextFormatting.DARK_AQUA + "+20% Mining Speed");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "The Black Lung";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.HASTE, 2, 0), new PotionEffect(MobEffects.UNLUCK, 4, 3)
				, new PotionEffect(MobEffects.HUNGER, 4, 7)};
	}
}
