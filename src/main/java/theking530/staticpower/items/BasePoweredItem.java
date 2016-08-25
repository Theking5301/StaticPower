package theking530.staticpower.items;

import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class BasePoweredItem extends ItemEnergyContainer{

	public int DAMAGE_DIVISOR;
	
	public BasePoweredItem(String name, int capacity, int damageDivisor) {
		super(capacity);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		setMaxDamage(capacity/damageDivisor);
		setNoRepair();
		DAMAGE_DIVISOR = damageDivisor;
	}
	@Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn){
		setEnergy(stack, 0);
    }
	public void setEnergy(ItemStack container, int energy) {
		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		container.getTagCompound().setInteger("Energy", energy);
		updateDamage(container);
	}
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int recieved = super.receiveEnergy(container, maxReceive, simulate);
		updateDamage(container);
		return recieved;
	}
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(container, maxExtract, simulate);
		updateDamage(container);
		return extracted;
	}
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		if(stack.getItemDamage() == 0) {
	        return false;	
		}
		return true;
    }
	@Override
    public int getItemStackLimit(ItemStack stack) {
		if(stack.getItemDamage() > 0) {
			return 1;
		}
        return this.getItemStackLimit();
    }
	public void updateDamage(ItemStack stack) {
		setDamage(stack, (capacity - getEnergyStored(stack))/DAMAGE_DIVISOR);
	}
}