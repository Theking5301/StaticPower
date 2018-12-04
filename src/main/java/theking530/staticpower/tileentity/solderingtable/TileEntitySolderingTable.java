package theking530.staticpower.tileentity.solderingtable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import theking530.staticpower.assists.utilities.SideModeList.Mode;
import theking530.staticpower.handlers.crafting.registries.SolderingRecipeRegistry;
import theking530.staticpower.handlers.crafting.wrappers.SolderingRecipeWrapper;
import theking530.staticpower.items.tools.ISolderingIron;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemInputServo;
import theking530.staticpower.tileentity.TileEntityBase;

public class TileEntitySolderingTable extends TileEntityBase {
		
	public TileEntitySolderingTable() {
		initializeSlots(1, 17, 1);
		registerComponent(new TileEntityItemInputServo(this, 2, slotsInput, 9, 10, 11, 12, 13, 14, 15));
	}
	@Override
	public String getName() {
		return "container.SolderingTable";				
	}
	public boolean hasValidRecipe() {
		return SolderingRecipeRegistry.Soldering().getRecipe(slotsInput, getWorld(), 3, 3) != null;
	}
	public boolean canProcess() {
		if(hasValidRecipe()) {
			SolderingRecipeWrapper recipe = SolderingRecipeRegistry.Soldering().getRecipe(slotsInput, getWorld(), 3, 3);
			if(!recipe.satisfiesCount(getInputStack(9), getInputStack(10), getInputStack(11), getInputStack(12), getInputStack(13), getInputStack(14), getInputStack(15))) {
				return false;
			}
			return true;
		}
		return false;
	}
	public void onCrafted(EntityPlayer player, ItemStack item, int amount) {
		MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, getInputStack(16), EnumHand.MAIN_HAND));
		if(getInputStack(16).getItem() instanceof ISolderingIron) {
			if(!getWorld().isRemote) {
				ISolderingIron tempIron = (ISolderingIron) getInputStack(16).getItem();
				if(!tempIron.canSolder(getInputStack(16))) {
					return;
				}
				if(tempIron.useSolderingItem(getInputStack(16))) {
					slotsInput.setStackInSlot(16, ItemStack.EMPTY);
					getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 1, false);		
				}
			}
		}
    	for (int i = 0; i < 9; ++i){
    		ItemStack itemstack1 = getInputStack(i);
            if (itemstack1 != ItemStack.EMPTY) {
            	for(int j=9; j<16; j++) {
            		if(getInputStack(j) != ItemStack.EMPTY && getInputStack(j).isItemEqual(getInputStack(i))) {
        				slotsInput.extractItem(j, 1, false);
        				break;
            		}
            	}
            }
		}
	}
	@Override
	public List<Mode> getValidSideConfigurations() {
		List<Mode> modes = new ArrayList<Mode>();
		modes.add(Mode.Input);
		modes.add(Mode.Regular);
		modes.add(Mode.Disabled);
		return modes;
	}
}
