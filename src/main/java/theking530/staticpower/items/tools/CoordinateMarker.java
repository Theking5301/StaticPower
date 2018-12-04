package theking530.staticpower.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import theking530.staticpower.assists.utilities.EnumTextFormatting;
import theking530.staticpower.assists.utilities.WorldUtilities;
import theking530.staticpower.items.ItemBase;

public class CoordinateMarker extends ItemBase {

	public CoordinateMarker(String name) {
		super(name);
	}
	@Override
        public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemStack = player.getHeldItem(hand);
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				if(itemStack.hasTagCompound()) {
					if(itemStack.getTagCompound().getIntArray("POS2").length < 3) {
						NBTTagCompound nbt = new NBTTagCompound();
						BlockPos pos = player.getPosition();
						nbt.setIntArray("POS1", itemStack.getTagCompound().getIntArray("POS1"));
						nbt.setIntArray("POS2", new int[] {pos.getX(), pos.getY(), pos.getZ()});
						itemStack.setTagCompound(nbt);
						player.sendMessage(new TextComponentString("Position " + EnumTextFormatting.GREEN + "Two Set!"));
					}else{
						NBTTagCompound nbt = new NBTTagCompound();
						BlockPos pos = player.getPosition();
						nbt.setIntArray("POS1", new int[] {pos.getX(), pos.getY(), pos.getZ()});	
						itemStack.setTagCompound(nbt);
						player.sendMessage(new TextComponentString("Position " + EnumTextFormatting.AQUA + "One Set!"));	
					}
				}else{
					NBTTagCompound nbt = new NBTTagCompound();
					BlockPos pos = player.getPosition();
					nbt.setIntArray("POS1", new int[] {pos.getX(), pos.getY(), pos.getZ()});	
					itemStack.setTagCompound(nbt);
					player.sendMessage(new TextComponentString("Position " + EnumTextFormatting.AQUA + "One Set!"));
				}
			}else{
				itemStack.setTagCompound(null);			
				player.sendMessage(new TextComponentString(EnumTextFormatting.RED + "Positions Cleared!"));
			}
	    }	
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);    
	}
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
    	if(entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer)entity;
    		if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isItemEqual(itemstack)) {
    			if(itemstack.hasTagCompound()) {
    	    		if(itemstack.getTagCompound().getIntArray("POS1").length > 2) {
    	    			BlockPos pos1;
    	    			pos1 = WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS1"));
    	    			world.spawnParticle(EnumParticleTypes.CLOUD, pos1.getX() + 0.5D, pos1.getY() + 1.0D, 
    	    					pos1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
    	    		}
    	    		if(itemstack.getTagCompound().getIntArray("POS2").length > 2) {
    	    			BlockPos pos1;
    	    			pos1 = WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS1"));
    	    			BlockPos pos2;
    	    			pos2 = WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS2"));
    	    			world.spawnParticle(EnumParticleTypes.CLOUD, pos1.getX() + 0.5D, pos2.getY() + 1.0D, 
    	    					pos2.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
    	    			world.spawnParticle(EnumParticleTypes.CLOUD, pos2.getX() + 0.5D, pos2.getY() + 1.0D, 
    	    					pos1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
    	    			world.spawnParticle(EnumParticleTypes.CLOUD, pos2.getX() + 0.5D, pos2.getY() + 1.0D, 
    	    					pos2.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
    	    		}	
    			}	
    		}
    	}
    }
	@Override  
		public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
    	if(showHiddenTooltips()) {
    		String pos1 = "Empty";
    		String pos2 = "Empty";
    		String area = "0";
    		if(itemstack.hasTagCompound()) {
        		if(itemstack.getTagCompound().getIntArray("POS1").length > 2) {
        			pos1 = WorldUtilities.formatBlockPos(WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS1")));
        		}
        		if(itemstack.getTagCompound().getIntArray("POS2").length > 2) {
        			pos2 = WorldUtilities.formatBlockPos(WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS2")));
        			area = (EnumTextFormatting.ITALIC + "Area: " + WorldUtilities.getAreaBetweenCorners
            				(WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS1")), WorldUtilities.blockPosFromIntArray(itemstack.getTagCompound().getIntArray("POS2"))));
        		}	
    		}
    		list.add(EnumTextFormatting.ITALIC + "Position 1: " + pos1);
    		list.add(EnumTextFormatting.ITALIC + "Position 2: " + pos2);
    		list.add(EnumTextFormatting.ITALIC + "Area: " + area);
    	}else{
    		list.add(EnumTextFormatting.ITALIC + "Hold Shift");
	    }
	}
}
