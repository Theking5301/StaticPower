package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class LeadArmor extends BaseArmor {

	public LeadArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Heavy: " + EnumTextFormatting.RED + "-30% Speed");
		tempList.add("Dense: " + EnumTextFormatting.DARK_AQUA + "+10 Hearts");
		tempList.add("Tough: " + EnumTextFormatting.DARK_AQUA + "+20% Resistance");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Become the Tank";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.SLOWNESS, 2, 1), new PotionEffect(MobEffects.HEALTH_BOOST, 4, 3)
				, new PotionEffect(MobEffects.RESISTANCE, 4, 0)};
	}
}
