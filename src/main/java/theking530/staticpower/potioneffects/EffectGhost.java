package theking530.staticpower.potioneffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import theking530.staticpower.assists.Reference;

public class EffectGhost extends BasePotion {

	public static final ResourceLocation GHOST_OVERLAY = new ResourceLocation(Reference.MOD_ID + ":" + "textures/gui/GhostOverlay.png");
	
	protected EffectGhost(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(name, isBadEffectIn, liquidColorIn);
	}

	@Override
	public void potionEffectApplied(EntityLivingBase entityLivingBaseIn) {
		entityLivingBaseIn.setEntityInvulnerable(true);
		entityLivingBaseIn.setGlowing(true);
		entityLivingBaseIn.setInvisible(true);
		entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
		BlockPos tempPos = entityLivingBaseIn.getPosition();
		entityLivingBaseIn.setNoGravity(true);
		entityLivingBaseIn.getEntityWorld().playSound(tempPos.getX(), tempPos.getY(), tempPos.getZ(), SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.HOSTILE, 1.0f, 1.0f, false);
	}
	@Override
	public void potionEffectRemoved(EntityLivingBase entityLivingBaseIn) {
		entityLivingBaseIn.setEntityInvulnerable(false);
		entityLivingBaseIn.setGlowing(false);
		entityLivingBaseIn.setInvisible(true);
		BlockPos tempPos = entityLivingBaseIn.getPosition();
		entityLivingBaseIn.setNoGravity(false);
		entityLivingBaseIn.getEntityWorld().playSound(tempPos.getX(), tempPos.getY(), tempPos.getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.HOSTILE, 1.0f, 1.0f, false);	
		entityLivingBaseIn.addPotionEffect(new PotionEffect(ModPotions.SOUL_BOUND, 2000000, 0));
	}
	public void potionEffectTick(EntityLivingBase entityLivingBaseIn, int remainingDuration) {

	}
	public static void removeExperience(EntityPlayer player, int amount){
		player.addScore((-1) * amount);
        int j = Integer.MIN_VALUE + player.experienceTotal;

        if (amount < j) {
            amount = j;
        }

        player.experience -= (float)amount / (float)player.xpBarCap();

        for (player.experienceTotal -= amount; player.experience <= 0.0F; player.experience /= (float)player.xpBarCap()) {
        	player.experience = (player.experience + 1.0F) * (float)player.xpBarCap();
        	player.addExperience(-1);
        }
	}
}
