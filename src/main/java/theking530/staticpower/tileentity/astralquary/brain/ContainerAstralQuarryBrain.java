package theking530.staticpower.tileentity.astralquary.brain;

import net.minecraft.entity.player.InventoryPlayer;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class ContainerAstralQuarryBrain extends BaseContainer {
	
	public ContainerAstralQuarryBrain(InventoryPlayer invPlayer, TileEntityAstralQuarryBrain teAstralQuary) {
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1){
                this.addSlotToContainer(new StaticPowerContainerSlot(teAstralQuary.slotsOutput, i1 + l * 3, 61 + i1 * 18, 20 + l * 18));
            }
        }
        
		addPlayerHotbar(invPlayer, 8, 152);
		addPlayerInventory(invPlayer, 8, 94);

	}
}
	

