package theking530.staticpower.items.tools;

import api.IWrenchTool;
import api.IWrenchable;
import api.RegularWrenchMode;
import api.SneakWrenchMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;

public class StaticWrench extends Item implements IWrenchTool{
	
	public StaticWrench() {
		setMaxStackSize(1);
		this.setCreativeTab(StaticPower.StaticPower);
		setUnlocalizedName("StaticWrench");
		setRegistryName("StaticWrench");	
	}		
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStack = playerIn.getHeldItem(hand);
		if(!worldIn.isRemote) {
			if(!playerIn.isSneaking()) {
				if(getWrenchMode(itemStack).ordinal() + 1 <= RegularWrenchMode.values().length-1) {
					setWrenchMode(itemStack, RegularWrenchMode.values()[getWrenchMode(itemStack).ordinal() + 1]);
					playerIn.sendMessage(new TextComponentString("Wrench Mode: " + getWrenchMode(itemStack).toString()));	
				}else{
					setWrenchMode(itemStack, RegularWrenchMode.values()[0]);	
					playerIn.sendMessage(new TextComponentString("Wrench Mode: " + getWrenchMode(itemStack).toString()));	
				}	
			}
		}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
    }
	@Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if(player.getHeldItem(hand) != null) {
			if(world.getBlockState(pos).getBlock() instanceof IWrenchable) {
				IWrenchable block = (IWrenchable) world.getBlockState(pos).getBlock();
				player.swingArm(EnumHand.MAIN_HAND);
				if(!player.isSneaking()) {
					if(block.canBeWrenched(player, world, pos, side, false)){	
						block.wrenchBlock(player, getWrenchMode(player.getHeldItem(hand)), player.getHeldItem(hand), world, pos, side, true);
						playWrenchSound(world, pos);
						return EnumActionResult.SUCCESS;
					}	
				}else{
					if(world.getBlockState(pos).getBlock() instanceof IWrenchable) {
						if(block.canBeWrenched(player, world, pos, side, true)){	
							block.sneakWrenchBlock(player, getSneakWrenchMode(player.getHeldItem(hand)), player.getHeldItem(hand), world, pos, side, true);
							playWrenchSound(world, pos);
							return EnumActionResult.SUCCESS;
						}
					}				
				}				
			}
		}		
        return EnumActionResult.PASS;
    }
	public void playWrenchSound(World world, BlockPos pos) {
		world.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.15F, (float) (0.5F + Math.random()*2.0));
	}
	@Override
	public RegularWrenchMode getWrenchMode(ItemStack stack) {
		if(stack.hasTagCompound()) {
			return RegularWrenchMode.values()[stack.getTagCompound().getInteger("REGULAR")];
		}
		return RegularWrenchMode.TOGGLE_SIDES;
	}
	@Override
	public SneakWrenchMode getSneakWrenchMode(ItemStack stack) {
		return SneakWrenchMode.DISMANTE;
	}
	public void setWrenchMode(ItemStack stack, RegularWrenchMode mode) {
		if(stack.hasTagCompound()) {
			stack.getTagCompound().setInteger("REGULAR", mode.ordinal());
		}else{
			NBTTagCompound nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
			stack.getTagCompound().setInteger("REGULAR", mode.ordinal());
		}
	}
}
