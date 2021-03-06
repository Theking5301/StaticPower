package theking530.staticpower.tileentities.digistorenetwork;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import theking530.staticpower.blocks.tileentity.StaticPowerMachineBlock;
import theking530.staticpower.cables.network.CableNetworkManager;
import theking530.staticpower.cables.network.ServerCable;

public abstract class BaseDigistoreBlock extends StaticPowerMachineBlock {

	public BaseDigistoreBlock(String name) {
		super(name);
	}

	@Override
	public void onStaticPowerNeighborChanged(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onStaticPowerNeighborChanged(state, world, pos, neighbor);
		if (!world.isRemote()) {
			ServerCable cable = CableNetworkManager.get((ServerWorld) world).getCable(pos);
			if (cable != null && cable.getNetwork() != null) {
				cable.getNetwork().updateGraph((ServerWorld) world, pos);
			}
		}
	}

	@Override
	public boolean hasModelOverride(BlockState state) {
		return false;
	}
}
