package theking530.staticpower.client.rendering.blocks;

import static net.minecraftforge.client.model.SimpleModelTransform.IDENTITY;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import theking530.staticcore.utilities.SDMath;
import theking530.staticpower.client.StaticPowerSprites;
import theking530.staticpower.tileentities.TileEntityBase;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;
import theking530.staticpower.tileentities.components.control.sideconfiguration.SideConfigurationComponent;

@OnlyIn(Dist.CLIENT)
public class DefaultMachineBakedModel extends AbstractBakedModel {
	@SuppressWarnings("deprecation")
	protected static final AtlasTexture BLOCKS_TEXTURE = ModelLoader.instance().getSpriteMap().getAtlasTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
	private static final Logger LOGGER = LogManager.getLogger(DefaultMachineBakedModel.class);
	private static final ModelProperty<Optional<MachineSideMode[]>> SIDE_CONFIG = new ModelProperty<>();
	private final HashMap<Direction, Boolean> sideConfigurationRenderControl;

	public DefaultMachineBakedModel(IBakedModel baseModel) {
		super(baseModel);
		sideConfigurationRenderControl = new HashMap<Direction, Boolean>();

		// Populate with defaults.
		for (Direction dir : Direction.values()) {
			sideConfigurationRenderControl.put(dir, true);
		}
		sideConfigurationRenderControl.put(null, true);
	}

	public DefaultMachineBakedModel setSideConfigVisiblity(Direction side, boolean visible) {
		sideConfigurationRenderControl.put(side, visible);
		return this;
	}

	@Override
	@Nonnull
	public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
		Optional<MachineSideMode[]> configurations = getSideConfigurations(world, pos);
		ModelDataMap modelDataMap = getEmptyIModelData();
		modelDataMap.setData(SIDE_CONFIG, configurations);
		return modelDataMap;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected List<BakedQuad> getBakedQuadsFromIModelData(@Nullable BlockState state, Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
		// Check if the data has the SIDE_CONFIG property. If not, something has gone
		// wrong.
		if (!data.hasProperty(SIDE_CONFIG)) {
			conditionallyLogError("Encountered invalid side configuration data when attempting to bake quads for machine.");
			return BaseModel.getQuads(state, side, rand);
		}
		// Attempt to get the side configuration.
		Optional<MachineSideMode[]> sideConfigurations = data.getData(SIDE_CONFIG);

		// If we didn't get a side configuration, skip this block and just return the
		// defaults.
		if (!sideConfigurations.isPresent()) {
			conditionallyLogError("Encountered no side configuration data when attempting to bake quads for machine.");
			return BaseModel.getQuads(state, side, rand);
		}

		// Capture the default quads and retexture the sides to match the desired side
		// textures based on the configuration.
		List<BakedQuad> baseQuads = BaseModel.getQuads(state, side, rand, data);
		ImmutableList.Builder<BakedQuad> newQuads = new ImmutableList.Builder<BakedQuad>();

		// Get the block atlas texture.
		try {
			AtlasTexture blocksTexture = ModelLoader.instance().getSpriteMap().getAtlasTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

			// Iterate through all the quads.
			for (BakedQuad quad : baseQuads) {
				// Get the rendering side.
				Direction renderingSide = side == null ? quad.getFace() : side;

				// Get the side mode.
				MachineSideMode sideMode = sideConfigurations.get()[renderingSide.ordinal()];
				try {
					// Attempt to render the quad for the side.
					renderQuadsForSide(newQuads, renderingSide, blocksTexture, quad, sideMode);
				} catch (Exception e) {
					LOGGER.warn("An error occured when attempting to render the model.", e);
				}

			}
		} catch (Exception e) {
			// Silence!
		}

		// Build and return the new quad list.
		return newQuads.build();
	}

	protected void renderQuadsForSide(Builder<BakedQuad> newQuads, Direction side, AtlasTexture blocksTexture, BakedQuad originalQuad, MachineSideMode sideConfiguration) {
		// Add the original quads.
		newQuads.add(originalQuad);

		// Add the side config color if it's not NEVER and if its enabled.
		if (sideConfigurationRenderControl.get(side) && sideConfiguration != MachineSideMode.Never) {
			// Get the texture sprite for the side.
			TextureAtlasSprite sideSprite = getSpriteForMachineSide(sideConfiguration, blocksTexture, side);

			// Vectors for quads are relative to the face direction, so we need to only work
			// in the positive direction vectors.
			Direction offsetSide = side;
			if (side == Direction.EAST) {
				offsetSide = Direction.WEST;
			} else if (side == Direction.SOUTH) {
				offsetSide = Direction.NORTH;
			} else if (side == Direction.DOWN) {
				offsetSide = Direction.UP;
			}

			BlockFaceUV blockFaceUV = new BlockFaceUV(new float[] { 0.005f, 0.005f, 15.995f, 15.995f }, 0);
			BlockPartFace blockPartFace = new BlockPartFace(side, -1, sideSprite.getName().toString(), blockFaceUV);
			Vector3f posOffset = SDMath.transformVectorByDirection(offsetSide, new Vector3f(0.0f, 0.0f, 0.005f));
			posOffset.add(16.0f, 16.0f, 16.0f);
			Vector3f negOffset = SDMath.transformVectorByDirection(offsetSide, new Vector3f(0.0f, 0.0f, -0.005f));

			BakedQuad newQuad = FaceBaker.bakeQuad(negOffset, posOffset, blockPartFace, sideSprite, side, IDENTITY, null, true, new ResourceLocation("dummy_name"));
			newQuads.add(newQuad);
		}
	}

	protected TextureAtlasSprite getSpriteForMachineSide(MachineSideMode mode, AtlasTexture blocksStitchedTextures, Direction side) {
		switch (mode) {
		case Input:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_INPUT);
		case Input2:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_PURPLE);
		case Input3:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_MAGENTA);
		case Output:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_OUTPUT);
		case Output2:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_GREEN);
		case Output3:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_YELLOW);
		case Output4:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_AQUA);
		case Disabled:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_DISABLED);
		case Never:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.EMPTY_TEXTURE);
		default:
			return blocksStitchedTextures.getSprite(StaticPowerSprites.MACHINE_SIDE_NORMAL);
		}
	}

	protected ModelDataMap getEmptyIModelData() {
		ModelDataMap.Builder builder = new ModelDataMap.Builder();
		builder.withInitial(SIDE_CONFIG, Optional.empty());
		ModelDataMap modelDataMap = builder.build();
		return modelDataMap;
	}

	protected Optional<MachineSideMode[]> getSideConfigurations(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos blockPos) {
		if (!world.getBlockState(blockPos).hasTileEntity()) {
			return Optional.empty();
		}

		TileEntity rawTileEntity = world.getTileEntity(blockPos);

		if (rawTileEntity != null && rawTileEntity instanceof TileEntityBase) {
			TileEntityBase configurable = (TileEntityBase) rawTileEntity;
			if (configurable.hasComponentOfType(SideConfigurationComponent.class)) {
				return Optional.of(configurable.getComponent(SideConfigurationComponent.class).getWorldSpaceConfiguration());
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean isSideLit() {
		return BaseModel.isSideLit();
	}
}
