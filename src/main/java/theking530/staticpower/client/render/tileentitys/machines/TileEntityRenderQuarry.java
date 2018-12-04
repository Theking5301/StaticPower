package theking530.staticpower.client.render.tileentitys.machines;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.assists.utilities.SideModeList;
import theking530.staticpower.machines.quarry.TileEntityQuarry;

public class TileEntityRenderQuarry extends BaseMachineTESR<TileEntityQuarry> {

	private static final ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_front.png");
	
	private static final ResourceLocation quarrySide = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side.png");
	private static final ResourceLocation quarrySideIn = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_input.png");
	private static final ResourceLocation quarrySideOut = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_output.png");
	private static final ResourceLocation quarrySideDis = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_disabled.png");
	private static final ResourceLocation quarrySideExtra1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_extra1.png");
	private static final ResourceLocation quarrySideExtra2 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_extra2.png");
	private static final ResourceLocation quarrySideExtra3 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/machines/quarry_side_extra3.png");
	
	@Override
	protected ResourceLocation getFrontTexture(boolean machineOn) {
		return front;
	}
	@Override
	protected ResourceLocation getSideTexture(SideModeList.Mode mode) {
		switch(mode) {
			case Regular: return quarrySide;
			case Input: return quarrySideIn;
			case Output: return quarrySideOut;
			case Disabled: return quarrySideDis;
			case Input2: return quarrySideExtra1;
			case Output2: return quarrySideExtra2;
			case Output3: return quarrySideExtra3;
			default: return quarrySide;
		}
	}
}