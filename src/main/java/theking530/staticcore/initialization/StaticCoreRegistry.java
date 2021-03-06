package theking530.staticcore.initialization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import theking530.api.attributes.defenitions.AbstractAttributeDefenition;
import theking530.api.attributes.modifiers.AbstractAttributeModifier;
import theking530.api.attributes.registration.AttributeModifierRegistration;
import theking530.api.attributes.registration.AttributeModifierRegistry;
import theking530.api.attributes.registration.AttributeRegistration;
import theking530.api.attributes.registration.AttributeRegistry;
import theking530.staticcore.initialization.container.ContainerTypeAllocator;
import theking530.staticcore.initialization.container.ContainerTypePopulator;
import theking530.staticcore.initialization.tileentity.TileEntityTypeAllocator;
import theking530.staticcore.initialization.tileentity.TileEntityTypePopulator;

@SuppressWarnings("deprecation")
public class StaticCoreRegistry {
	protected static final Logger LOGGER = LogManager.getLogger("StaticCore");
	protected static final List<TileEntityTypeAllocator<? extends TileEntity>> TILE_ENTITY_ALLOCATORS = new LinkedList<>();
	protected static final List<ContainerTypeAllocator<? extends Container, ? extends Screen>> CONTAINER_ALLOCATORS = new LinkedList<>();
	private static boolean preInitialized;
	private static boolean initialized;

	public static void preInitialize() {
		// Don't preinitialize more than once.
		if (preInitialized) {
			throw new RuntimeException("Attempted to pre-initialize StaticCore more than once!");
		}

		LOGGER.info("Pre-Initializing StaticCore.");

		// Register first the modifiers, then the defenitions.
		LOGGER.info("Pre-Initializing Registry Attributes and Modifiers.");
		registerAttributeModifiers();
		registerAttributeDefenitions();
		LOGGER.info(String.format("Pre-Initialized: %1$d Attribute Defenitions and %2$d Attribute Modifiers.", AttributeRegistry.getRegisteredAttributeCount(),
				AttributeModifierRegistry.getRegisteredAttributeModifierCount()));

		preInitialized = true;
		LOGGER.info("StaticCore Pre-Initialized.");
	}

	public static void postInitialize() {
		// Don't initialize more than once.
		if (initialized) {
			throw new RuntimeException("Attempted to initialize StaticCore more than once!");
		}

		LOGGER.info("Initializing StaticCore.");

		processTileEntityTypeAllocators((teAllocator) -> {
			TILE_ENTITY_ALLOCATORS.add(teAllocator);
		});
		processContainerTypeAllocators((containerAllocator) -> {
			CONTAINER_ALLOCATORS.add(containerAllocator);
		});

		LOGGER.info(String.format("Initialized: %1$d Tile Entity Allocators and %2$d Container Type Allocators.", TILE_ENTITY_ALLOCATORS.size(), CONTAINER_ALLOCATORS.size()));

		initialized = true;
		LOGGER.info("StaticCore Initialized.");
	}

	public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
		for (TileEntityTypeAllocator<?> allocator : StaticCoreRegistry.TILE_ENTITY_ALLOCATORS) {
			allocator.register(event);
		}
	}

	public static void registerAttributeDefenitions() {
		// Process the attributes.
		for (AnnotationData annotation : getAnnotationsOfType(AttributeRegistration.class)) {
			try {
				// Get the class the annotation is on.
				Class<?> act = Class.forName(annotation.getClassType().getClassName());

				// Get the constructor on the class that takes a resource location.
				Constructor<?> cons = act.getConstructor(ResourceLocation.class);

				// Get the ID for the annotation.
				ResourceLocation id = new ResourceLocation(annotation.getAnnotationData().get("value").toString());

				// Register the attribute defenition.
				AttributeRegistry.registerAttribute(id, (idIn) -> {
					try {
						return (AbstractAttributeDefenition<?, ?>) cons.newInstance(idIn);
					} catch (Exception e) {
						throw new RuntimeException(String.format("An error occured when attempting to register attribute defenition: %1$s.", id.toString()), e);
					}
				});
			} catch (Exception e) {
				LOGGER.error(String.format("An error occured when attempting to process tile entity allocator: %1$s.", annotation.getMemberName()), e);
			}
		}
	}

	public static void registerAttributeModifiers() {
		// Process the attributes.
		for (AnnotationData annotation : getAnnotationsOfType(AttributeModifierRegistration.class)) {
			try {
				// Get the class the annotation is on.
				Class<?> act = Class.forName(annotation.getClassType().getClassName());

				// Get the constructor on the class that takes a resource location.
				Constructor<?> cons = act.getConstructor();

				// Get the ID for the annotation.
				String id = annotation.getAnnotationData().get("value").toString();

				// Register the attribute defenition.
				AttributeModifierRegistry.registerAttributeType(id, () -> {
					try {
						return (AbstractAttributeModifier<?>) cons.newInstance();
					} catch (Exception e) {
						throw new RuntimeException(String.format("An error occured when attempting to register attribute modifier: %1$s.", id.toString()), e);
					}
				});
			} catch (Exception e) {
				LOGGER.error(String.format("An error occured when attempting to process tile entity allocator: %1$s.", annotation.getMemberName()), e);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerTileEntitySpecialRenderers() {
		StaticCoreRegistry.processTileEntityTypeAllocators((allocator) -> {
			if (allocator.requiresTileEntitySpecialRenderer()) {
				ClientRegistry.bindTileEntityRenderer(allocator.getType(), allocator.getTileEntitySpecialRenderer());
			}
		});
	}

	public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
		for (ContainerTypeAllocator<? extends Container, ? extends Screen> container : CONTAINER_ALLOCATORS) {
			container.registerContainer(event);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerScreenFactories() {
		DeferredWorkQueue.runLater(() -> {
			for (ContainerTypeAllocator<? extends Container, ? extends Screen> container : CONTAINER_ALLOCATORS) {
				container.registerScreen();
			}
			LOGGER.info("Registered all Static Power container types.");
		});
	}

	@SuppressWarnings("unchecked")
	public static void processTileEntityTypeAllocators(Consumer<TileEntityTypeAllocator<TileEntity>> allocatorConsumer) {
		// Process the allocators.
		for (AnnotationData annotation : getAnnotationsOfType(TileEntityTypePopulator.class)) {
			try {
				Class<?> act = Class.forName(annotation.getClassType().getClassName());
				Field field = act.getField(annotation.getMemberName());
				allocatorConsumer.accept((TileEntityTypeAllocator<TileEntity>) field.get(null));
			} catch (Exception e) {
				LOGGER.error(String.format("An error occured when attempting to process tile entity allocator: %1$s.", annotation.getMemberName()), e);
			}
		}
	}

	public static void processContainerTypeAllocators(Consumer<ContainerTypeAllocator<?, ?>> allocatorConsumer) {
		// Process the allocators.
		for (AnnotationData annotation : getAnnotationsOfType(ContainerTypePopulator.class)) {
			try {
				Class<?> act = Class.forName(annotation.getClassType().getClassName());
				Field field = act.getField(annotation.getMemberName());
				allocatorConsumer.accept((ContainerTypeAllocator<?, ?>) field.get(null));
			} catch (Exception e) {
				LOGGER.error(String.format("An error occured when attempting to process container allocator: %1$s.", annotation.getMemberName()), e);
			}
		}
	}

	public static ArrayList<AnnotationData> getAnnotationsOfType(Class<? extends Annotation> annotationType) {
		// Allocate the output.
		ArrayList<AnnotationData> output = new ArrayList<AnnotationData>();

		// Iterate through all the file infos.
		for (ModFileInfo mod : FMLLoader.getLoadingModList().getModFiles()) {
			// Iterate through all the annotations.
			for (AnnotationData anno : mod.getFile().getScanResult().getAnnotations()) {
				// Check to see if the annotation is of the requested type.
				try {
					if (anno.getAnnotationType().equals(Type.getType(annotationType))) {
						output.add(anno);
					}
				} catch (Exception e) {
					LOGGER.error(String.format("An error occured when attempting to process annotation: %1$s.", anno.getAnnotationType().getClassName()), e);
				}
			}
		}

		// Return the output.
		return output;
	}
}
