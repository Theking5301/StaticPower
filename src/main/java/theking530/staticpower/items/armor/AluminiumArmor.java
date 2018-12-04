package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class AluminiumArmor extends BaseArmor {

	public AluminiumArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Weak: " + EnumTextFormatting.RED + "-4 Attack");
		tempList.add("Quick: " + EnumTextFormatting.DARK_AQUA + "+20% Speed");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Run Like the Wind";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 2, 0), new PotionEffect(MobEffects.WEAKNESS, 4, 0)};
	}
}
