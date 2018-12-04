package theking530.staticpower.tileentity.solderingtable;

import net.minecraftforge.items.ItemStackHandler;
import theking530.staticpower.client.gui.widgets.SlotPhantom;

public class SlotSolderingTableInput extends SlotPhantom {

	TileEntitySolderingTable TABLE;
	ContainerSolderingTable CONTAINER;
	
	public SlotSolderingTableInput(ContainerSolderingTable container,  ItemStackHandler teTable, int i, int j, int k) {
		super(teTable, i, j, k);
		CONTAINER = container;
	}
    public void onSlotChanged() {
        super.onSlotChanged();
        CONTAINER.onSolderingAreaChanged();
    }
}
