package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class TinArmor extends BaseArmor {

	public TinArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Glass: " + EnumTextFormatting.RED + "-1 Heart Per 5 Seconds");
		tempList.add("Cannon: " + EnumTextFormatting.DARK_AQUA + "+12 Damage");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Glass Cannon";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.STRENGTH, 2, 3), new PotionEffect(MobEffects.POISON, 4, 0)};
	}
}
