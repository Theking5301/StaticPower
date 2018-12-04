package theking530.staticpower.logic.gates.transducer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theking530.staticpower.client.GuiIDRegistry;
import theking530.staticpower.logic.gates.BlockLogicGate;

public class BlockSignalMultiplier extends BlockLogicGate {

	public BlockSignalMultiplier(String name) {
		super(name, GuiIDRegistry.guiIDSignalMultiplier);
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySignalMultiplier();
	}
	public String getDescrption(ItemStack stack){
		return "Output a redstone signal of specific strength if and only if the input is equal to a specific strength.";	
	}
	public String getInputDescrption(ItemStack stack){
		return "Redstone singal to transduce.";
	}
	public String getOutputDescrption(ItemStack stack){
		return "Redstone signal equal to the strength requested";
	}
}
