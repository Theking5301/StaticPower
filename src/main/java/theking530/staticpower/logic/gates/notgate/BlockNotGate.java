package theking530.staticpower.logic.gates.notgate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockNotGate extends BlockLogicGate {

	public BlockNotGate(String name) {
		super(name, 0);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityNotGate();
	}
	public String getDescrption(ItemStack stack){
		return "Inverts the supplied input.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "A redstone signal of variable strength.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "A signal of strength 15 if the input is powered, otherwise no output.";
	}
}
