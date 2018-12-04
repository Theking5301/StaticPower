package theking530.staticpower.client.render.tileentitys.machines;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.machines.chargingstation.TileEntityChargingStation;

public class TileEntityRenderChargingStation extends BaseMachineTESR<TileEntityChargingStation> {
	
	private static final ResourceLocation frontOn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/charging_station_front_on.png");
	private static final ResourceLocation frontOff = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/charging_station_front_off.png");
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return machineOn ? frontOn : frontOff;
	}	
}