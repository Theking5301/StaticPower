package theking530.staticpower.tileentity.vacuumchest;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.base.Predicate;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.assists.utilities.TileEntityUtilities;
import theking530.staticpower.fluids.ModFluids;
import theking530.staticpower.items.ModItems;
import theking530.staticpower.items.itemfilter.ItemFilter;
import theking530.staticpower.items.upgrades.BaseRangeUpgrade;
import theking530.staticpower.items.upgrades.BaseTankUpgrade;
import theking530.staticpower.items.upgrades.ExperienceVacuumUpgrade;
import theking530.staticpower.items.upgrades.TeleportUpgrade;
import theking530.staticpower.tileentity.TileEntityBase;

public class TileEntityVacuumChest extends TileEntityBase implements Predicate<EntityItem> {

	private float vacuumDiamater;
	private float initialVacuumDiamater;
	private boolean shouldTeleport;
	private boolean shouldVacuumExperience;
	private FluidTank experienceTank;
	
	public TileEntityVacuumChest() {
		initializeSlots(1, 0, 30);
		initialVacuumDiamater = 6;
		vacuumDiamater = initialVacuumDiamater;
		shouldTeleport = false;
		experienceTank = new FluidTank(5000);
	}		
	@Override
	public void process() {
		
	    AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
	    aabb = aabb.expand(vacuumDiamater, vacuumDiamater, vacuumDiamater);
	    aabb = aabb.offset(-vacuumDiamater/2, -vacuumDiamater/2, -vacuumDiamater/2);
	    List<EntityItem> droppedItems = getWorld().getEntitiesWithinAABB(EntityItem.class, aabb, this);
	    List<EntityXPOrb> xpOrbs = getWorld().getEntitiesWithinAABB(EntityXPOrb.class, aabb);
	    
		for (EntityItem entity : droppedItems) {

			double x = (pos.getX() + 0.5D - entity.posX);
			double y = (pos.getY() + 0.5D - entity.posY);
			double z = (pos.getZ() + 0.5D - entity.posZ);
			ItemStack stack = entity.getItem().copy();
			if(InventoryUtilities.canFullyInsertItemIntoInventory(slotsOutput, stack) && doesItemPassFilter(stack)) {
				double distance = Math.sqrt(x * x + y * y + z * z);
				if (distance < 1.1 || (shouldTeleport && distance < getRadius()-0.1f)) {
					if(InventoryUtilities.canFullyInsertItemIntoInventory(slotsOutput, stack)) {
						if(!getWorld().isRemote) {
							InventoryUtilities.insertItemIntoInventory(slotsOutput, stack);	
						}
						entity.setDead();
						getWorld().spawnParticle(EnumParticleTypes.PORTAL, (double)pos.getX()+0.5, (double)pos.getY()+1.0, (double)pos.getZ()+0.5, 0.0D, 0.0D, 0.0D, new int[0]);
						getWorld().playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.5F, 1.0F, false);
					}
				} else {
					double var11 = 1.0 - distance / 15.0;
					if (var11 > 0.0D) {
						var11 *= var11;
						entity.motionX += x / distance * var11 * 0.06;
						entity.motionY += y / distance * var11 * 0.15;
						entity.motionZ += z / distance * var11 * 0.06;
						Vec3d entityPos = entity.getPositionVector();
						getWorld().spawnParticle(EnumParticleTypes.PORTAL, entityPos.x, entityPos.y-0.5, entityPos.z, 0.0D, 0.0D, 0.0D, new int[0]);
					}
				}
			}
		}
		if(shouldVacuumExperience) {
			for (EntityXPOrb orb : xpOrbs) {			
				double x = (pos.getX() + 0.5D - orb.posX);
				double y = (pos.getY() + 0.5D - orb.posY);
				double z = (pos.getZ() + 0.5D - orb.posZ);
				
				double distance = Math.sqrt(x * x + y * y + z * z);
				if (distance < 1.1 || (shouldTeleport && distance < getRadius()-0.1f)) {
					if(experienceTank.canFill() && experienceTank.fill(new FluidStack(ModFluids.LiquidExperience,  orb.getXpValue()), false) > 0) {
						experienceTank.fill(new FluidStack(ModFluids.LiquidExperience,  orb.getXpValue()), true);	
						updateBlock();
						orb.setDead();
						getWorld().playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, (TileEntityUtilities.RANDOM.nextFloat()+1)/2, false);
					}
				} else {
					double var11 = 1.0 - distance / 15.0;
					if (var11 > 0.0D) {
						var11 *= var11;
						orb.motionX += x / distance * var11 * 0.06;
						orb.motionY += y / distance * var11 * 0.15;
						orb.motionZ += z / distance * var11 * 0.06;
					}
				}	
			}
		}
	}
	public boolean hasFilter() {
		if(!getInternalStack(0).isEmpty() && getInternalStack(0).getItem() instanceof ItemFilter) {
			return true;
		}
		return false;
	}
	public boolean doesItemPassFilter(ItemStack stack) {
		if(hasFilter()) {
			ItemFilter tempFilter = (ItemFilter)getInternalStack(0).getItem();
			return tempFilter.evaluateFilter(getInternalStack(0), stack);
		}else{
			return true;
		}
	}
	
	//NBT
	@Override
    public void deserializeData(NBTTagCompound nbt) {
        super.deserializeData(nbt);
        if(nbt.hasKey("TANK")) {
        	experienceTank.readFromNBT(nbt.getCompoundTag("TANK"));
        }
    }		
    @Override
    public NBTTagCompound serializeData(NBTTagCompound nbt) {
        super.serializeData(nbt);
        NBTTagCompound tankTag = new NBTTagCompound();
        experienceTank.writeToNBT(tankTag);
        nbt.setTag("TANK", tankTag);
        return nbt;
	}	  	    
    
    //IInventory
	@Override
	public String getName() {
		return "container.VacuumChest";		
	}
	@Override
	public boolean apply(EntityItem input) {
		return true;
	}
	public float getRadius() {
		return vacuumDiamater/2.0f;
	}
	public FluidTank getTank() {
		return experienceTank;
	}
	public boolean showTank() {
		return shouldVacuumExperience;
	}
	
	@Override
	public boolean isSideConfigurable() {
		return false;
	}
	
	/*Update Handling*/
	@Override
	public void upgradeTick() {
		if(hasUpgrade(ModItems.BasicRangeUpgrade)) {
			BaseRangeUpgrade tempUpgrade = (BaseRangeUpgrade) getUpgrade(ModItems.BasicRangeUpgrade).getItem();
			vacuumDiamater = tempUpgrade.getValueMultiplied(initialVacuumDiamater, tempUpgrade.getUpgradeValueAtIndex(getUpgrade(ModItems.BasicRangeUpgrade), 0));
		}else{
			vacuumDiamater = initialVacuumDiamater;
		}
		if(hasUpgrade(ModItems.TeleportUpgrade)) {
			shouldTeleport = true;
		}else{
			shouldTeleport = false;
		}
		if(hasUpgrade(ModItems.ExperienceVacuumUpgrade)) {
			shouldVacuumExperience = true;
		}else{
			shouldVacuumExperience = false;
		}
		if(hasUpgrade(ModItems.BasicTankUpgrade)) {
			BaseTankUpgrade tempUpgrade = (BaseTankUpgrade) getUpgrade(ModItems.BasicTankUpgrade).getItem();
			experienceTank.setCapacity((int)(tempUpgrade.getValueMultiplied(10000, tempUpgrade.getUpgradeValueAtIndex(getUpgrade(ModItems.BasicTankUpgrade), 0))));
		}else{
			experienceTank.setCapacity(10000);
		}
	}
	@Override
	public boolean canAcceptUpgrade(@Nonnull ItemStack upgrade) {
		if(upgrade != ItemStack.EMPTY) {
			if(upgrade.getItem() instanceof BaseRangeUpgrade || upgrade.getItem() instanceof TeleportUpgrade || upgrade.getItem() instanceof ExperienceVacuumUpgrade) {
				return true;
			}
		}
		return false;
	}
    @SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
		updateBlock();
    	if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return (T) slotsOutput;
    	}
    	if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return (T) experienceTank;
    	}
    	return super.getCapability(capability, facing);
    }
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing){
    	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		return true;
    	}
    	if(capability==CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		return true;
    	}
        return super.hasCapability(capability, facing);
    }
}
