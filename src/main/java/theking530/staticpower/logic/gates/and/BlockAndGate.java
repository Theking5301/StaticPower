package theking530.staticpower.logic.gates.and;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockAndGate extends BlockLogicGate {

	public BlockAndGate(String name) {
		super(name, 0);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityAndGate();
	}
	public String getDescrption(ItemStack stack){
		return "Outputs a redstone signal if and only if all inputs are powered.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "Redstone inputs.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "A signal of strength 15.";
	}
}
