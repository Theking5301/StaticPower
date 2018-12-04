package theking530.staticpower.client.render.tileentitys.logicgates;

import net.minecraft.util.ResourceLocation;
import theking530.staticpower.assists.Reference;
import theking530.staticpower.client.model.ModelSignalMultiplier;

public class TileEntityRenderSignalMultiplier extends TileEntityRenderLogicGateBase {
	
    public TileEntityRenderSignalMultiplier() {
        super(new ModelSignalMultiplier(), 
        		new ResourceLocation(Reference.MOD_ID, "textures/blocks/logic_gate_base_on.png"), 
        		new ResourceLocation(Reference.MOD_ID, "textures/blocks/logic_gate_base_off.png"));
    }
}



