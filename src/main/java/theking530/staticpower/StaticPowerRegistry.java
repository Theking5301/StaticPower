package theking530.staticpower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import theking530.staticpower.blocks.interfaces.IItemBlockProvider;
import theking530.staticpower.init.TileEntityInitializer;
import theking530.staticpower.init.TileEntityTypePopulator.TileEntityTypeAllocator;

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
	public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new LinkedList<>();
	public static final HashSet<ContainerType<? extends Container>> CONTAINER_TYPES = new HashSet<>();
	public static final HashSet<FlowingFluid> FLUIDS = new HashSet<FlowingFluid>();
	public static final HashSet<IRecipeSerializer> RECIPE_SERIALIZERS = new HashSet<IRecipeSerializer>();
	public static final HashMap<ContainerType, IScreenFactory> SCREEN_FACTORIES = new HashMap<>();

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

	/**
	 * Pre-registers a {@link TileEntity} for initialization through the registry
	 * event. Returns an instance of the {@link TileEntityType} for this
	 * {@link TileEntity} and {@link Block} list combination. The registry name fo
	 * the tile entity type will be the registry name of the first provided block.
	 * 
	 * @param <T>              The type of the {@link TileEntity}.
	 * @param factory          The tile entity create factory.
	 * @param tileEntityBlocks The {@link Block} that is responsible for this tile
	 *                         entity.
	 * @return An instance of the {@link TileEntityType} for the provided
	 *         {@link TileEntity} & {@link Block}.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends TileEntity> TileEntityType<T> preRegisterTileEntity(Function<TileEntityType<T>, T> factory, Block... tileEntityBlocks) {
		TileEntityInitializer initializer = new TileEntityInitializer(factory);
		TileEntityType teType = TileEntityType.Builder.create(initializer, tileEntityBlocks).build(null);
		initializer.setType(teType);
		teType.setRegistryName(tileEntityBlocks[0].getRegistryName());
		TILE_ENTITY_TYPES.add(teType);
		return teType;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T extends TileEntity> TileEntityType<T> preRegisterTileEntity(TileEntityTypeAllocator allocator) {
		TileEntityInitializer initializer = new TileEntityInitializer(allocator.factory);
		TileEntityType teType = TileEntityType.Builder.create(initializer, allocator.blocks).build(null);
		initializer.setType(teType);
		teType.setRegistryName(allocator.blocks[0].getRegistryName());
		TILE_ENTITY_TYPES.add(teType);
		return teType;
	}

	/**
	 * Pre-registers a {@link Container} for registration through the registration
	 * event. Returns an instance of the {@link ContainerType} for this
	 * {@link Container}.
	 * 
	 * @param <T>     The type of the {@link Container}.
	 * @param name    The registry name of the container sans namespace.
	 * @param factory The factory method (usually just the class name :: new).
	 * @return An instance of {@link ContainerType} for the provided
	 *         {@link Container}.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends Container, K extends ContainerScreen<T>> ContainerType<T> preRegisterContainer(String name, IContainerFactory factory, IScreenFactory<T, K> screen) {
		ContainerType containerType = IForgeContainerType.create(factory);
		containerType.setRegistryName(name);
		CONTAINER_TYPES.add(containerType);
		SCREEN_FACTORIES.put(containerType, screen);
		return containerType;
	}

	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		for (Item item : ITEMS) {
			event.getRegistry().register(item);
		}
	}

	public static void onRegisterItemColors(ColorHandlerEvent.Item event) {
		for (Item item : ITEMS) {
			if (item instanceof IItemColor) {
				event.getItemColors().register((IItemColor) item, item);
			}
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
		for (TileEntityType<?> teType : TILE_ENTITY_TYPES) {
			event.getRegistry().register(teType);
		}
	}

	public static void onRegisterContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
		for (ContainerType<?> container : CONTAINER_TYPES) {
			event.getRegistry().register(container);
		}
	}

	public static void onRegisterRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		for (IRecipeSerializer serializer : RECIPE_SERIALIZERS) {
			event.getRegistry().register(serializer);
		}
	}
}
