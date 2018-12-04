package theking530.staticpower.logic.gates.adder;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockAdder extends BlockLogicGate {

	public BlockAdder(String name) {
		super(name, 0);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityAdder();
	}
	public String getDescrption(ItemStack stack){
		return "Add one signal to the other.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "First Operand.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "Sum of the Blue input plus the Purple input.";
	}
	public String getExtraDescrption(ItemStack stack){
		return "Second Operand.";
	}
}
