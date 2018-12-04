package theking530.staticpower.items.armor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.items.EquipmentMaterial;
import theking530.staticpower.potioneffects.ModPotions;

public class SkeletonArmor extends BaseArmor {
	
	public SkeletonArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Fleshless: " + EnumTextFormatting.DARK_GREEN + "Your lack of flesh");
		tempList.add(EnumTextFormatting.DARK_GREEN + "makes it 33% harder for");
		tempList.add(EnumTextFormatting.DARK_GREEN + "arrow to hit.");
		tempList.add("Gross: " + EnumTextFormatting.RED + "That's so gross dude.");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "I feel...holy";
	}
	@Override
	public PotionEffect[] getEffects() {
		return null;
	}
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
	} 
	@Override
	public void onEquipped(EntityPlayer player, ItemStack itemstack, BaseArmor tempArmor) {
		super.onEquipped(player, itemstack, tempArmor);
	}
	@Override
	public void onUnequipped(EntityPlayer player, ItemStack itemstack, BaseArmor tempArmor) {
		super.onUnequipped(player, itemstack, tempArmor);
		if(player.getActivePotionEffect(ModPotions.GHOST) != null) {
			player.removePotionEffect(ModPotions.GHOST);
		}
	}
	@Override 
	public ItemStack getRandomDrop(double dropRate) {
		int piece = RANDOM.nextInt(4);
		Item armorPiece = ModArmor.SkeletonHelmet;
		
		switch(piece) {
		case 0: armorPiece = ModArmor.SkeletonHelmet;
				break;
		case 1: armorPiece = ModArmor.SkeletonChestplate;
				break;
		case 2: armorPiece = ModArmor.SkeletonLeggings;
				break;
		case 3: armorPiece = ModArmor.SkeletonBoots;
				break;	
		}
		
		if(diceRoll(dropRate)) {
			return new ItemStack(armorPiece, 1);
		}
		return null;
	}
	@Override
	public void onWearerDamaged(LivingAttackEvent event, float AdjustedDamage, EntityPlayer player, EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		if(equipmentSlot == EntityEquipmentSlot.CHEST) {
			if(isFullSet(player) && event.getSource().damageType == "arrow") {
				if(diceRoll(0.33f)) {
					event.setCanceled(true);
				}
			}
		}
	}
}
