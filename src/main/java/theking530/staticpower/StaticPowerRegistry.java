package theking530.staticpower;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import theking530.staticcore.initialization.StaticCoreRegistry;
import theking530.staticpower.blocks.interfaces.IItemBlockProvider;
import theking530.staticpower.entities.AbstractEntityType;
import theking530.staticpower.entities.AbstractSpawnableMobType;
import theking530.staticpower.world.trees.AbstractStaticPowerTree;

/**
 * Main registry class responsible for preparing entities for registration and
 * then registering them.
 * 
 * @author Amine Sebastian
 *
 */
@SuppressWarnings({ "rawtypes" })
public class StaticPowerRegistry {
	public static final HashSet<Item> ITEMS = new HashSet<>();
	public static final HashSet<Block> BLOCKS = new HashSet<>();
	public static final HashSet<FlowingFluid> FLUIDS = new HashSet<FlowingFluid>();
	public static final HashSet<IRecipeSerializer> RECIPE_SERIALIZERS = new HashSet<IRecipeSerializer>();
	public static final HashSet<AbstractEntityType<?>> ENTITIES = new HashSet<AbstractEntityType<?>>();
	public static final HashSet<AbstractStaticPowerTree> TREES = new HashSet<AbstractStaticPowerTree>();

	/**
	 * Pre-registers an item for registration through the registry event.
	 * 
	 * @param item The item to pre-register.
	 * @return The item that was passed.
	 */
	public static Item preRegisterItem(Item item) {
		ITEMS.add(item);
		return item;
	}

	/**
	 * Pre-registers an entity for registration through the registry event.
	 * 
	 * @param entity The entity to pre-register.
	 * @return The entity that was passed.
	 */
	public static <T extends Entity> AbstractEntityType<T> preRegisterEntity(AbstractEntityType<T> entity) {
		ENTITIES.add(entity);
		return entity;
	}

	/**
	 * Pre-registers a tree for registration through the init method.
	 * 
	 * @param tree The tree to pre-register.
	 * @return The tree that was passed.
	 */
	public static AbstractStaticPowerTree preRegisterTree(AbstractStaticPowerTree tree) {
		TREES.add(tree);
		return tree;
	}

	/**
	 * Pre-registers a {@link Block} for initialization through the registry event.
	 * If the block implements {@link IItemBlockProvider}, a {@link BlockItem} is
	 * also pre-registered.
	 * 
	 * @param block The {@link Block} to pre-register.
	 * @return The {@link Block} that was passed.
	 */
	public static Block preRegisterBlock(Block block) {
		BLOCKS.add(block);
		if (block instanceof IItemBlockProvider) {
			IItemBlockProvider provider = (IItemBlockProvider) block;
			BlockItem itemBlock = provider.getItemBlock();

			// Skip items with null item blocks.
			if (itemBlock != null) {
				preRegisterItem(provider.getItemBlock());
			}
		}
		return block;
	}

	public static FlowingFluid preRegisterFluid(FlowingFluid fluid) {
		FLUIDS.add(fluid);
		return fluid;
	}

	public static IRecipeSerializer preRegisterRecipeSerializer(IRecipeSerializer recipeSerializer) {
		RECIPE_SERIALIZERS.add(recipeSerializer);
		return recipeSerializer;
	}

	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		for (Item item : ITEMS) {
			event.getRegistry().register(item);
		}
	}

	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : BLOCKS) {
			event.getRegistry().register(block);
		}
	}

	public static void onRegisterFluids(RegistryEvent.Register<Fluid> event) {
		for (Fluid fluid : FLUIDS) {
			event.getRegistry().register(fluid);
		}
	}

	public static void onRegisterTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
		StaticCoreRegistry.registerTileEntityTypes(event);
	}

	public static void onRegisterContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
		StaticCoreRegistry.registerContainerTypes(event);
	}

	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		// Register mobs.
		for (AbstractEntityType<?> type : ENTITIES) {
			event.getRegistry().register(type.getType());
			if (type instanceof AbstractSpawnableMobType) {
				((AbstractSpawnableMobType<?>) type).registerAttributes(event);
			}
		}
	}

	public static void onRegisterRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		for (IRecipeSerializer serializer : RECIPE_SERIALIZERS) {
			event.getRegistry().register(serializer);
		}
	}
}
