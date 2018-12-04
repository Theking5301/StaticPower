package theking530.staticpower.client.render.tileentitys.multiblock;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.machines.refinery.mixer.TileEntityRefineryMixer;

public class TileEntityRenderFluidRefineryMixer extends BaseMultiblockTESR<TileEntityRefineryMixer> {
	
	private static final ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/multiblock/fluid_refinery_mixer.png");
		
    public void draw(TileEntityRefineryMixer tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		checkAndRenderSides(tileEntity, 0);
		checkAndRenderSides(tileEntity, 1);
		checkAndRenderSides(tileEntity, 2);
		checkAndRenderSides(tileEntity, 3);
		checkAndRenderSides(tileEntity, 4);
		checkAndRenderSides(tileEntity, 5);
    }
	public void checkAndRenderSides(TileEntityRefineryMixer tileentity, int side) {
		bindTexture(front);
		BLOCK.drawBlock(side, 0.001f);
	}
}
