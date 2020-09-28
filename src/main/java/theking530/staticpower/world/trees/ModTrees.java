package theking530.staticpower.world.trees;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import theking530.staticpower.StaticPowerConfig;

public class ModTrees {
	public static void addTreeFeatures(BiomeLoadingEvent event) {
		System.out.println("BIOME: " + event.getName());
		event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.TREE.withConfiguration(RubberTree.TREE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
				.withPlacement(Placement.field_242898_b.configure(new ChanceConfig(StaticPowerConfig.rubberWoodSpawnChance))));
	}
}
