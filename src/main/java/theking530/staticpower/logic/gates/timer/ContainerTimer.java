package theking530.staticpower.logic.gates.timer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerTimer extends Container {

	public ContainerTimer(InventoryPlayer invPlayer, TileEntityTimer teSignalMultiplier) {
		
	}
	
	public boolean enchantItem(EntityPlayer player, int action) {
		return true;
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
