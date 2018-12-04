package theking530.staticpower.client.render.tileentitys.multiblock;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.assists.utilities.SideUtilities;
import theking530.staticpower.machines.refinery.fluidinterface.TileEntityRefineryFluidInterface;

public class TileEntityRenderFluidRefineryInterface extends BaseMultiblockTESR<TileEntityRefineryFluidInterface> {

	private static final ResourceLocation input = new ResourceLocation(Reference.MOD_ID, "textures/blocks/multiblock/fluid_refinery_interface_input.png");
	private static final ResourceLocation output = new ResourceLocation(Reference.MOD_ID, "textures/blocks/multiblock/fluid_refinery_interface_output.png");
		
	@Override
    public void draw(TileEntityRefineryFluidInterface tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		checkAndRenderSides(tileEntity, 0);
		checkAndRenderSides(tileEntity, 1);
		checkAndRenderSides(tileEntity, 2);
		checkAndRenderSides(tileEntity, 3);
		checkAndRenderSides(tileEntity, 4);
		checkAndRenderSides(tileEntity, 5);
    }
	public void checkAndRenderSides(TileEntityRefineryFluidInterface tileentity, int side) {
		EnumFacing adjustedSide = SideUtilities.getEnumFacingFromRenderingInt(side, tileentity.getFacingDirection());	
		bindTexture(tileentity.getSideConfiguration(adjustedSide) == Mode.Input ? input : output);
		BLOCK.drawBlock(side, 0.001f);
	}
}
