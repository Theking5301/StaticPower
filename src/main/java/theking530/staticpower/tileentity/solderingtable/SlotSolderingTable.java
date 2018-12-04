package theking530.staticpower.tileentity.solderingtable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class SlotSolderingTable extends StaticPowerContainerSlot {

	TileEntitySolderingTable TABLE;
	EntityPlayer PLAYER;
	ContainerSolderingTable CONTAINER;
    private int amountCrafted;
    
	public SlotSolderingTable(ContainerSolderingTable container, EntityPlayer player, TileEntitySolderingTable teTable, IItemHandler inv, int i, int j, int k) {
		super(inv, i, j, k);
		TABLE = teTable;
		PLAYER = player;
		CONTAINER = container;
	}
	@Override
    protected void onCrafting(ItemStack item, int amount){
        this.amountCrafted += amount;
        this.onCrafting(item);
    }
    protected void onSwapCraft(int p_190900_1_)
    {
        this.amountCrafted += p_190900_1_;
    }
    public boolean isItemValid(ItemStack item){
        return false;
    }
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        TABLE.onCrafted(PLAYER, stack, amountCrafted);
        CONTAINER.onSolderingAreaChanged();
        return stack;
    }
}
