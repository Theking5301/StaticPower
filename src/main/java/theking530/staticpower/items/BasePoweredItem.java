package theking530.staticpower.items;

import cofh.redstoneflux.impl.ItemEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class BasePoweredItem extends ItemEnergyContainer{

	public int damageDivisor;

	public BasePoweredItem(String name, int capacity, int damageDivisor) {
		super(capacity);
		setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		this.damageDivisor = Math.max(1, damageDivisor);
		setMaxDamage(capacity/this.damageDivisor);
		setNoRepair();

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
		if(container.getItemDamage() == 0) {
			container.setItemDamage(1);
		}
		return extracted;
	}
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		return true;
    }
	@Override
    public int getItemStackLimit(ItemStack stack) {
		if(stack.getItemDamage() > 0) {
			return 1;
		}
        return maxStackSize;
    }
	public void updateDamage(ItemStack stack) {
		setDamage(stack, (capacity - getEnergyStored(stack))/damageDivisor);
	}
}
