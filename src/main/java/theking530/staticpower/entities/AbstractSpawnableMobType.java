package theking530.staticpower.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import theking530.staticcore.utilities.Color;
import theking530.staticpower.StaticPowerRegistry;
import theking530.staticpower.items.StaticPowerMobSpawnEgg;

public abstract class AbstractSpawnableMobType<T extends Entity> extends AbstractEntityType<T> {
	private final StaticPowerMobSpawnEgg spawnEgg;

	public AbstractSpawnableMobType(String name, Color primaryEggColor, Color secondaryEggColor, EntityType.Builder<T> builder) {
		super(name, builder);
		StaticPowerRegistry.preRegisterItem(spawnEgg = new StaticPowerMobSpawnEgg("egg_" + name, getType(), primaryEggColor, secondaryEggColor));
	}

	public abstract void registerAttributes(RegistryEvent.Register<EntityType<?>> event);

	public abstract void registerRenderers(FMLClientSetupEvent event);

	public abstract void spawn(BiomeLoadingEvent event);

	public StaticPowerMobSpawnEgg getEgg() {
		return spawnEgg;
	}
}
