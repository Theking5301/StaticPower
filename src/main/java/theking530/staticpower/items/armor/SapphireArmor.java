package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class SapphireArmor extends BaseArmor {

	public SapphireArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Pay a Bodyguard: " + EnumTextFormatting.RED + "-12 Damage");
		tempList.add("Night Vision: " + EnumTextFormatting.DARK_AQUA + "Can't you read?");
		tempList.add("Miner's Dream: " + EnumTextFormatting.DARK_AQUA + "+40% Swing Speed");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Ouch. Sharp.";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.NIGHT_VISION, 2, 1), new PotionEffect(MobEffects.WEAKNESS, 4, 2)
				, new PotionEffect(MobEffects.HASTE, 4, 1)};
	}
}
