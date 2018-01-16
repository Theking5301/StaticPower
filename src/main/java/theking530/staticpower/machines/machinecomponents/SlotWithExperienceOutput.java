package theking530.staticpower.machines.machinecomponents;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotWithExperienceOutput extends SlotItemHandler {

    private final EntityPlayer thePlayer;
    private int removeCount;
	
	public SlotWithExperienceOutput(EntityPlayer player, IItemHandler handler, int i, int j, int k) {
		super(handler, i, j, k);
		thePlayer = player;

	}

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    public void onSlotChange(EntityPlayer playerIn, ItemStack stack) {
        this.onCrafting(stack);
        super.onTake(playerIn, stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(this.thePlayer.getEntityWorld(), this.thePlayer, this.removeCount);

        if (!this.thePlayer.getEntityWorld().isRemote) {
            int i = this.removeCount;
            float f = getExperience(stack);

            if (f == 0.0F) {
                i = 0;
            } else if (f < 1.0F) {
                int j = MathHelper.floor((float)i * f);

                if (j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j)) {
                    ++j;
                }

                i = j;
            }

            while (i > 0) {
                int k = EntityXPOrb.getXPSplit(i);
                i -= k;
                this.thePlayer.getEntityWorld().spawnEntity(new EntityXPOrb(this.thePlayer.getEntityWorld(), this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, k));
            }
        }

        this.removeCount = 0;

        fireEvents(stack);
    }
    public float getExperience(ItemStack stack) {
    	return FurnaceRecipes.instance().getSmeltingExperience(stack);
    }
    public void fireEvents(ItemStack stack) {
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerSmeltedEvent(thePlayer, stack);
    }
}
