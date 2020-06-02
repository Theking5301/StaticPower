package theking530.staticpower.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import theking530.api.wrench.IWrenchable;
import theking530.api.wrench.RegularWrenchMode;
import theking530.api.wrench.SneakWrenchMode;

/**
 * Basic implmentation of a static power block.
 * 
 * @author Amine Sebastian
 *
 */
public class StaticPowerBlock extends Block implements IItemBlockProvider, IBlockRenderLayerProvider, IWrenchable {
	/**
	 * Facing property used by blocks that require keeping track of the direction
	 * they face (does not have to be used).
	 */
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	/**
	 * Rotation property used by blocks who don't use {@link #FACING} but still need
	 * the option to rotate to either face X, Y, or Z. (Does not have to be used).
	 */
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	/**
	 * Constructor for a static power block.
	 * 
	 * @param name       The registry name of this block sans namespace.
	 * @param properties The block properties to be used when defining this block.
	 */
	public StaticPowerBlock(String name, Block.Properties properties) {
		super(properties);
		setRegistryName(name);
	}

	@Override
	public BlockItem getItemBlock() {
		return new StaticPowerItemBlock(this);
	}

	/**
	 * Basic constructor for a static power block with a specific material type.
	 * 
	 * @param name     The registry name of this block sans namespace.
	 * @param material The {@link Material} this block is made of.
	 */
	public StaticPowerBlock(String name, Material material) {
		this(name, material, ToolType.PICKAXE, 1, 1.0f);
	}

	/**
	 * Basic constructor for a static power block with a specific material type,
	 * tool type.
	 * 
	 * @param name     The registry name of this block sans namespace.
	 * @param material The {@link Material} this block is made of.
	 * @param tool     The {@link ToolType} this block should be harvested by.
	 */
	public StaticPowerBlock(String name, Material material, ToolType tool) {
		this(name, material, tool, 1, 1.0f);
	}

	/**
	 * Basic constructor for a static power block with a specific material type,
	 * tool type, harvest level.
	 * 
	 * @param name         The registry name of this block sans namespace.
	 * @param material     The {@link Material} this block is made of.
	 * @param tool         The {@link ToolType} this block should be harvested by.
	 * @param harvestLevel The harvest level of this block.
	 */
	public StaticPowerBlock(String name, Material material, ToolType tool, int harvestLevel) {
		this(name, material, tool, harvestLevel, 1.0f);
	}

	/**
	 * Basic constructor for a static power block with a specific material type,
	 * tool type, harvest level, and hardness/resistance.
	 * 
	 * @param name                  The registry name of this block sans namespace.
	 * @param material              The {@link Material} this block is made of.
	 * @param tool                  The {@link ToolType} this block should be
	 *                              harvested by.
	 * @param harvestLevel          The harvest level of this block.
	 * @param hardnessAndResistance The hardness and resistance of this block.
	 */
	public StaticPowerBlock(String name, Material material, ToolType tool, int harvestLevel, float hardnessAndResistance) {
		this(name, Block.Properties.create(material).harvestTool(tool).harvestLevel(harvestLevel).hardnessAndResistance(hardnessAndResistance));
	}

	/**
	 * This is a helper event that is raised when a block is harvested by the
	 * player. Once this call is completed, the vanilla code is still executed. This
	 * is useful in the event that the block would like to serialize its owned
	 * {@link TileEntity}'s inventory to NBT or drop the contents on the floor. The
	 * vanilla code then handles dropping this actual block or any other drops as
	 * defined in the datapack. If you would like to completely override vanilla
	 * Behavior, override
	 * {@link #harvestBlock(World, PlayerEntity, BlockPos, BlockState, TileEntity, ItemStack)}
	 * instead.
	 * 
	 * @param world  The world the block was harvested in.
	 * @param player The player that harvested the block.
	 * @param pos    The position that the block was at.
	 * @param state  The state of the block at the point of harvest.
	 * @param te     The {@link TileEntity} of the block (if one existed).
	 * @param stack  The {@link ItemStack} that the block was harvested by.
	 */
	public void onStaticPowerBlockHarvested(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {

	}

	// ***********//
	// Overrides //
	// ***********//
	/**
	 * Gets the {@link RenderLayer} for this block. The default is Solid.
	 * 
	 * @return The render layer for this block.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public RenderType getRenderType() {
		return RenderType.getSolid();
	}

	/**
	 * Gets the basic tooltip that is displayed when hovered by the user.
	 * 
	 * @param stack   The item stack hovered by the user.
	 * @param worldIn The world the player was in when hovering the item.
	 * @param tooltip The list of {@link ITextComponent} to add to the tooltip.
	 */
	@OnlyIn(Dist.CLIENT)
	protected void getBasicTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip) {
	}

	/**
	 * Gets the advanced tooltip that is displayed when hovered by the user and they
	 * are holding shift.
	 * 
	 * @param stack   The item stack hovered by the user.
	 * @param worldIn The world the player was in when hovering the item.
	 * @param tooltip The list of {@link ITextComponent} to add to the tooltip.
	 */
	@OnlyIn(Dist.CLIENT)
	protected void getAdvancedTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip) {
	}

	/**
	 * Defines the behavior for when this block is regular right clicked by a
	 * wrench.
	 */
	@Override
	public void wrenchBlock(PlayerEntity player, RegularWrenchMode mode, ItemStack wrench, World world, BlockPos pos, Direction facing, boolean returnDrops) {

	}

	/**
	 * Defines the behavior for when this block is sneak right clicked by a wrench.
	 */
	@Override
	public void sneakWrenchBlock(PlayerEntity player, SneakWrenchMode mode, ItemStack wrench, World world, BlockPos pos, Direction facing, boolean returnDrops) {
	}

	/**
	 * Indicates if this block can be wrenched.
	 */
	@Override
	public boolean canBeWrenched(PlayerEntity player, World world, BlockPos pos, Direction facing, boolean sneaking) {
		return false;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		onStaticPowerBlockHarvested(worldIn, player, pos, state, te, stack);
		super.harvestBlock(worldIn, player, pos, state, te, stack);
	}
}