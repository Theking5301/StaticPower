package theking530.staticpower.tileentity.chest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.CapabilityItemHandler;
import theking530.staticpower.tileentity.TileEntityBase;

public class TileEntityBaseChest extends TileEntityBase {

    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    public int ticksSinceSync;
	
    @Override
    public void update()
    {
         int i = this.pos.getX();
         int j = this.pos.getY();
         int k = this.pos.getZ();
         ++this.ticksSinceSync;

         if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
         {
             this.numPlayersUsing = 0;

             for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F))))
             {
                 if (entityplayer.openContainer instanceof ContainerChest)
                 {
                	 ++this.numPlayersUsing;
                 }
             }
         }

         this.prevLidAngle = this.lidAngle;
         if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
         {
             double d1 = (double)i + 0.5D;
             double d2 = (double)k + 0.5D;
             this.world.playSound((EntityPlayer)null, d1, (double)j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
         }

         if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
         {
             float f2 = this.lidAngle;

             if (this.numPlayersUsing > 0)
             {
                 this.lidAngle += 0.1F;
             }
             else
             {
                 this.lidAngle -= 0.1F;
             }

             if (this.lidAngle > 1.0F)
             {
                 this.lidAngle = 1.0F;
             }

             if (this.lidAngle < 0.5F && f2 >= 0.5F)
             {
                 double d3 = (double)i + 0.5D;
                 double d0 = (double)k + 0.5D;
                 this.world.playSound((EntityPlayer)null, d3, (double)j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
             }

             if (this.lidAngle < 0.0F)
             {
                 this.lidAngle = 0.0F;
             }
         }
    }
    public void onOpened() {
    	numPlayersUsing++;
    }
    public void onClosed() {
    	numPlayersUsing = Math.max(numPlayersUsing-1, 0);
    }
    
    public void deserializeData(NBTTagCompound nbt) {
    	super.deserializeData(nbt);
    }
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
		return super.serializeData(nbt);	
    }
    
    
	@Override
	public String getName() {
		return "ERROR";
	}					
	@Override
	public boolean isSideConfigurable() {
		return false;
	}

    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
    @SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
    	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return (T) slotsOutput;
    	}
    	return super.getCapability(capability, facing);
    }  
}
