package theking530.staticpower.tileentity.solderingtable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theking530.staticpower.container.BaseContainer;
import theking530.staticpower.handlers.crafting.registries.SolderingRecipeRegistry;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.tools.ISolderingIron;
import theking530.staticpower.machines.tileentitycomponents.slots.StaticPowerContainerSlot;

public class ContainerSolderingTable extends BaseContainer {
	
 /** The crafting matrix inventory (3x3). */
    private TileEntitySolderingTable solderingTableTileEntity;
    public ContainerSolderingTable(InventoryPlayer invPlayer, TileEntitySolderingTable teTable) {
        solderingTableTileEntity = teTable;
        int l;
        int i1;
               
        //Crafting Area 0 - 8
        for (l = 0; l < 3; l++) {
            for (i1 = 0; i1 < 3; i1++){
                this.addSlotToContainer(new SlotSolderingTableInput(this, teTable.slotsInput, i1 + l * 3, 62 + i1 * 18, 17 + l * 18));
            }
        }
         
        //Extra Slots 9 - 15
        for (l = 0; l < 7; l++) {
        	this.addSlotToContainer(new StaticPowerContainerSlot(teTable.slotsInput, l+9, 26 + l * 18, 74) {
    			@Override
    	        public void onSlotChanged() {
    				onSolderingAreaChanged();
    		    }
    		});
        }
        
        //Output
        this.addSlotToContainer(new SlotSolderingTable(this, invPlayer.player, teTable, teTable.slotsOutput, 0, 140, 39));
        
        //Soldering Iron
		this.addSlotToContainer(new StaticPowerContainerSlot(new ItemStack(ModItems.SolderingIron), teTable.slotsInput, 16, 11, 17) {
			@Override
	        public boolean isItemValid(ItemStack itemStack) {
		          return itemStack.getItem() instanceof ISolderingIron;
		    }
		});
        this.addPlayerInventory(invPlayer, 8, 97);
        this.addPlayerHotbar(invPlayer, 8, 155);
        this.detectAndSendChanges();
    }
    public void onSolderingAreaChanged(){
    	if(!solderingTableTileEntity.getInputStack(16).isEmpty() && solderingTableTileEntity.getInputStack(16).getItem() instanceof ISolderingIron && solderingTableTileEntity.canProcess()) {
    		ISolderingIron tempIron = (ISolderingIron) solderingTableTileEntity.getInputStack(16).getItem();
    		if(tempIron.canSolder(solderingTableTileEntity.getInputStack(16))) {
    			solderingTableTileEntity.slotsOutput.setStackInSlot(0, SolderingRecipeRegistry.Soldering().getRecipe(solderingTableTileEntity.slotsInput, solderingTableTileEntity.getWorld(), 3, 3).getRecipeOutput().copy()); 		
    		}else{
        		solderingTableTileEntity.slotsOutput.setStackInSlot(0, ItemStack.EMPTY); 	
    		}    	
    	}else{
    		solderingTableTileEntity.slotsOutput.setStackInSlot(0, ItemStack.EMPTY); 
    	}
    	solderingTableTileEntity.updateBlock();
    	solderingTableTileEntity.markDirty();
    }
	@Override
	protected boolean playerItemShiftClicked(ItemStack stack, EntityPlayer player, InventoryPlayer invPlayer, Slot slot, int slotIndex) {
        if (stack.getItem() instanceof ISolderingIron && !mergeItemStack(stack, 17)) {
        	return true;
        }
        if (!mergeItemStack(stack, 9, 16, false)) {
        	return true;
        }
		return false;	
	}
}
