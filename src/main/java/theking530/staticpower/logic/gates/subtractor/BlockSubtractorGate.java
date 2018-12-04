package theking530.staticpower.logic.gates.subtractor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockSubtractorGate extends BlockLogicGate {

	public BlockSubtractorGate(String name) {
		super(name, 0);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySubtractorGate();
	}
	public String getDescrption(ItemStack stack){
		return "Subtract one signal from the other.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "First Operand.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "Difference of the Blue input minus the Purple input.";
	}
	public String getExtraDescrption(ItemStack stack){
		return "Second Operand.";
	}
}
