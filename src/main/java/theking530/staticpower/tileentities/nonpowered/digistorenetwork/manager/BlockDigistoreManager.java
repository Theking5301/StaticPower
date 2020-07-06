package theking530.staticpower.tileentities.nonpowered.digistorenetwork.manager;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import theking530.staticpower.initialization.ModTileEntityTypes;
import theking530.staticpower.tileentities.nonpowered.digistorenetwork.BaseDigistoreBlock;

public class BlockDigistoreManager extends BaseDigistoreBlock {

	public BlockDigistoreManager(String name) {
		super(name);
	}

	@Override
	public HasGuiType hasGuiScreen(TileEntity tileEntity, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return HasGuiType.NEVER;
	}

	@Override
	public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
		return ModTileEntityTypes.DIGISTORE_MANAGER.create();
	}
}
