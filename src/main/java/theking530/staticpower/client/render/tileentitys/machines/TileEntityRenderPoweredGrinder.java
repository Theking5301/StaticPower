package theking530.staticpower.client.render.tileentitys.machines;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.machines.poweredgrinder.TileEntityPoweredGrinder;
import theking530.staticpower.utilities.Reference;

public class TileEntityRenderPoweredGrinder extends BaseMachineTESR<TileEntityPoweredGrinder> {

	private static final ResourceLocation frontOn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/powered_grinder_front_on.png");
	private static final ResourceLocation frontOff = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/powered_grinder_front_off.png");
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return machineOn ? frontOn : frontOff;
	}
}
