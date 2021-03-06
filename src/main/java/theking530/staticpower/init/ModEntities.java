package theking530.staticpower.init;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import theking530.staticpower.StaticPowerRegistry;
import theking530.staticpower.entities.AbstractEntityType;
import theking530.staticpower.entities.AbstractSpawnableMobType;
import theking530.staticpower.entities.CauldronContainedEntity;
import theking530.staticpower.entities.CauldronContainedEntity.CauldronContainedEntityType;
import theking530.staticpower.entities.ConveyorBeltEntity;
import theking530.staticpower.entities.ConveyorBeltEntity.ConveyorBeltEntityType;
import theking530.staticpower.entities.enox.TypeEnox;
import theking530.staticpower.entities.smeep.TypeSmeep;

public class ModEntities {
	public static TypeSmeep Smeep;
	public static TypeEnox Enox;
	public static AbstractEntityType<CauldronContainedEntity> CauldronContainedEntity;
	public static AbstractEntityType<ConveyorBeltEntity> ConveyorBeltEntity;

	public static void init() {
		StaticPowerRegistry.preRegisterEntity(Smeep = new TypeSmeep("smeep"));
		StaticPowerRegistry.preRegisterEntity(Enox = new TypeEnox("enox"));
		StaticPowerRegistry.preRegisterEntity(CauldronContainedEntity = new CauldronContainedEntityType("cauldron_contained_entity"));
		StaticPowerRegistry.preRegisterEntity(ConveyorBeltEntity = new ConveyorBeltEntityType("conveyor_belt_entity"));
	}

	public static void addSpawns(BiomeLoadingEvent event) {
		for (AbstractEntityType<?> type : StaticPowerRegistry.ENTITIES) {
			if (type instanceof AbstractSpawnableMobType) {
				((AbstractSpawnableMobType<?>) type).spawn(event);
			}
		}
	}
}
