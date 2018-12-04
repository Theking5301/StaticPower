package theking530.staticpower.potioneffects;

public class ModPotions {
	
	public static BasePotion GHOST;
	public static BasePotion SOUL_BOUND;
	
	public static void init() {
		SOUL_BOUND = new BasePotion("effect.SoulBound", false, 0);
		GHOST = new EffectGhost("effect.Ghost", false, 0);
	}
}
