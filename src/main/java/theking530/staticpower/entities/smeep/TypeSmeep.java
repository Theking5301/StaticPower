package theking530.staticpower.entities.smeep;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import theking530.staticcore.utilities.Color;
import theking530.staticpower.StaticPowerConfig;
import theking530.staticpower.entities.AbstractSpawnableMobType;
import theking530.staticpower.init.ModEntities;

public class TypeSmeep extends AbstractSpawnableMobType<EntitySmeep> {
	public TypeSmeep(String name) {
		super(name, Color.EIGHT_BIT_WHITE, new Color(54, 239, 88), EntityType.Builder.create(EntitySmeep::new, EntityClassification.CREATURE).size(0.9f, 1.3f));
	}

	public void registerAttributes(RegistryEvent.Register<EntityType<?>> event) {
		GlobalEntityTypeAttributes.put(ModEntities.Smeep.getType(), EntitySmeep.getAttributes().create());
	}

	@OnlyIn(Dist.CLIENT)
	public void registerRenderers(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(getType(), RendererSmeep::new);
	}

	public void spawn(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.JUNGLE
				|| event.getCategory() == Biome.Category.MESA || event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.RIVER
				|| event.getCategory() == Biome.Category.SAVANNA || event.getCategory() == Biome.Category.TAIGA) {
			event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(getType(), StaticPowerConfig.SERVER.smeepSpawnWeight.get(),
					StaticPowerConfig.SERVER.smeepMinCount.get(), StaticPowerConfig.SERVER.smeepMaxCount.get()));
		}
	}
}
