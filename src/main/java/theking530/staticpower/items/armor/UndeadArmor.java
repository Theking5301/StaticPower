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

public class UndeadArmor extends BaseArmor {
	
	public UndeadArmor(String name, ArmorType type, EquipmentMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, type, materialIn, equipmentSlotIn);
	}
	
	@Override
	public List<String>  getSetInfo() {
		List<String>  tempList = new ArrayList<String> ();
		tempList.add("Arise: " + EnumTextFormatting.DARK_GREEN + " When killed, temporarily");
		tempList.add("        " + EnumTextFormatting.DARK_GREEN + " transform into an undead,");
		tempList.add("        " + EnumTextFormatting.DARK_GREEN + " flying thrall.");
		tempList.add("Thrall: " + EnumTextFormatting.GOLD + "+10 Seconds Invunerability");
		tempList.add("         " + EnumTextFormatting.RED + "Unable to Place/Break Blocks.");
		tempList.add("         " + EnumTextFormatting.RED + "Unable to Attack.");
		return tempList;
	}
	@Override
	public String getTagline() {
		return "Rise, the Forsaken!";
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
		Item armorPiece = ModArmor.UndeadHelmet;
		
		switch(piece) {
		case 0: armorPiece = ModArmor.UndeadHelmet;
				break;
		case 1: armorPiece = ModArmor.UndeadChestplate;
				break;
		case 2: armorPiece = ModArmor.UndeadLeggings;
				break;
		case 3: armorPiece = ModArmor.UndeadBoots;
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
			if(player.getHealth() - (AdjustedDamage+1) <= 0) {
				if(isFullSet(player) && player.getActivePotionEffect(ModPotions.SOUL_BOUND) == null) {
					if(!player.getEntityWorld().isRemote) {
						effectPlayer(player, ModPotions.GHOST, 0, 200);
						for(int i=0; i<4; i++) {
							if(player.inventory.armorItemInSlot(i) != null) {
								player.inventory.armorItemInSlot(i).damageItem(10, player);
							}
						}
					}
				}
			}
		}
	}
}
