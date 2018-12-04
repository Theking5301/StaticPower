package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;

public class SilverArmor extends BaseArmor {

	public SilverArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Holy Light: " + EnumTextFormatting.RED + "Blinded");
		tempList.add("Lucky: " + EnumTextFormatting.DARK_AQUA + "+10 Luck");
		tempList.add("Angelic: " + EnumTextFormatting.GOLD + "Visible Through Blocks");
		tempList.add("          " + EnumTextFormatting.GOLD + "Harder to Detect");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Unite with the Heavens";
	}
	@Override
	public PotionEffect[] getEffects() {
		return new PotionEffect[]{new PotionEffect(MobEffects.GLOWING, 2, 0), new PotionEffect(MobEffects.LUCK, 4, 9)
				, new PotionEffect(MobEffects.INVISIBILITY, 4, 0), new PotionEffect(MobEffects.BLINDNESS, 4, 0)};
	}
}
