package theking530.staticpower.tileentity.gates.timer;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.tileentity.gates.BlockLogicGate;

public class BlockTimer extends BlockLogicGate {

	public BlockTimer(String name) {
		super(name, GuiIDRegistry.guiTimer);
	}
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityTimer();
	}

}
