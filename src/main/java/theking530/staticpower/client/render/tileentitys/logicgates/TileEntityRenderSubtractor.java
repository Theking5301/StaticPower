package theking530.staticpower.client.render.tileentitys.logicgates;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.client.model.ModelSignalMultiplier;
import theking530.staticpower.utilities.Reference;

public class TileEntityRenderSubtractor extends TileEntityRenderLogicGateBase {
	
    public TileEntityRenderSubtractor() {
        super(new ModelSignalMultiplier(), 
        		new ResourceLocation(Reference.MOD_ID, "textures/blocks/logic_gate_base_on.png"), 
        		new ResourceLocation(Reference.MOD_ID, "textures/blocks/logic_gate_base_off.png"));
    }
}



