package theking530.staticpower.potioneffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class BasePotion extends Potion {

	protected BasePotion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		setPotionName(name);
	}

	@Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
	@Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

    }
	
	public void potionEffectApplied(EntityLivingBase entityLivingBaseIn) {
		
	}
	public void potionEffectRemoved(EntityLivingBase entityLivingBaseIn) {
	
	}
	public void potionEffectTick(EntityLivingBase entityLivingBaseIn, int remainingDuration) {
		
	}
}
