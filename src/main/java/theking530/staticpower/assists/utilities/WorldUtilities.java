package theking530.staticpower.assists.utilities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtilities {

	public static TileEntity[] getAdjacentEntities(World world, BlockPos pos) {
		TileEntity[] tempArray = new TileEntity[6];
		for(int i=0; i<6; i++) {
			EnumFacing facing = EnumFacing.values()[i];
			tempArray[i] = world.getTileEntity(pos.offset(facing));
		}
		return tempArray;
	}
	public static EnumFacing getXFacingFromPos(BlockPos pos1, BlockPos pos2) {
		int direction = pos1.getX() - pos2.getX();
		if(direction > 1) {
			return EnumFacing.EAST;
		}else{
			return EnumFacing.WEST;
		}
	}
	public static EnumFacing getYFacingFromPos(BlockPos pos1, BlockPos pos2) {
		int direction = pos1.getY() - pos2.getY();
		if(direction > 1) {
			return EnumFacing.UP;
		}else{
			return EnumFacing.DOWN;
		}	
	}
	public static EnumFacing getZFacingFromPos(BlockPos pos1, BlockPos pos2) {
		int direction = pos1.getZ() - pos2.getZ();
		if(direction > 1) {
			return EnumFacing.NORTH;
		}else{
			return EnumFacing.SOUTH;
		}
	}
	public static EnumFacing getFacingFromPos(BlockPos source, BlockPos query) {
		if(source != null && query != null) {
			if(source.getY() > query.getY()) {
				return EnumFacing.DOWN;
			}
			if(source.getY() < query.getY()) {
				return EnumFacing.UP;
			}
			if(source.getZ() > query.getZ()) {
				return EnumFacing.NORTH;
			}
			if(source.getZ() < query.getZ()) {
				return EnumFacing.SOUTH;
			}
			if(source.getX() > query.getX()) {
				return EnumFacing.WEST;
			}
			if(source.getX() < query.getX()) {
				return EnumFacing.EAST;
			}
		}
		return EnumFacing.UP;
	}
	public static void writeBlockPosToNBT(NBTTagCompound nbt, BlockPos pos, String name) {
		nbt.setInteger(name+"X", pos.getX());
		nbt.setInteger(name+"Y", pos.getY());
		nbt.setInteger(name+"Z", pos.getZ());
	}
	public static BlockPos readBlockPosFromNBT(NBTTagCompound nbt, String name) {
		return new BlockPos(nbt.getInteger(name+"X"), nbt.getInteger(name+"Y"), nbt.getInteger(name+"Z"));
	}
	public static int getAreaBetweenCorners(BlockPos pos1, BlockPos pos2) {
		BlockPos temp1 = pos2.subtract(pos1);
		BlockPos absPos = new BlockPos(Math.abs(temp1.getX()), Math.max(pos1.getY(), pos2.getY()), Math.abs(temp1.getZ()));
		return absPos.getX() * absPos.getY() * absPos.getZ();
	}
	public static String formatBlockPos(BlockPos pos) {
		return pos.toString().substring(9, pos.toString().length()-1);
	}
	public static BlockPos blockPosFromIntArray(int[] ints) {
		if(ints != null && ints.length > 2) {
			return new BlockPos(ints[0], ints[1], ints[2]);		
		}
		return null;
	}
	public static EntityItem dropItem(World worldIn, double x, double y, double z, ItemStack stack) {
        EntityItem entityitem = new EntityItem(worldIn, x, y, z, stack);
        worldIn.spawnEntity(entityitem);
        return entityitem;
    }
	public static EntityItem dropItem(World worldIn, EnumFacing direction, double x, double y, double z, ItemStack stack) {
		EntityItem item = null;
		if(direction == EnumFacing.EAST) {
			item = dropItem(worldIn, x, y, z-0.5, stack);
		}else if(direction == EnumFacing.NORTH) {
			item = dropItem(worldIn, x+0.5, y, z, stack);
		}else if(direction == EnumFacing.SOUTH) {
			item = dropItem(worldIn, x+0.5, y, z+0.5, stack);
		}else if(direction == EnumFacing.UP) {
			item = dropItem(worldIn, x, y+0.5, z, stack);
		}else if(direction == EnumFacing.DOWN) {
			item = dropItem(worldIn, x, y-0.5, z, stack);
		}else{
			item = dropItem(worldIn, x, y,z+0.5, stack);
		}
        return item;
    }
	public static EntityItem dropItem(World worldIn, BlockPos pos, ItemStack stack) {
		return dropItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
	public static EntityItem dropItem(World worldIn, EnumFacing facing, BlockPos pos, ItemStack stack) {
		return dropItem(worldIn, facing, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
}
