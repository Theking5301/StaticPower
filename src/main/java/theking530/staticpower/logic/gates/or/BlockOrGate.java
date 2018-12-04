package theking530.staticpower.logic.gates.or;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockOrGate extends BlockLogicGate {

	public BlockOrGate(String name) {
		super(name, 0);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityOrGate();
	}
	public String getDescrption(ItemStack stack){
		return "Emits a redstone signal if any input is true.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "A restone signal of variable strength.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "A redstone signal of strength 15 if any inputs are powered.";
	}
	public String getExtraDescrption(ItemStack stack){
		return "Exclusive OR (XOR) output. A redstone signal of strength 15 if only one input is powered.";
	}
}
