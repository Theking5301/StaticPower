package theking530.staticpower.assists.utilities;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;


public class SideUtilities {
	
	public enum BlockSide {
		TOP("Top"),
		BOTTOM("Bottom"),
		FRONT("Front"),
		BACK("Back"),
		RIGHT("Right"),
		LEFT("Left");
		
		private String name;
		
		private BlockSide(String name) {
			this.name = name;
		}
		public String getName() {
			return "side." + name;
		}
		public String getLocalizedName() {
			return I18n.format(getName());
		}
		public BlockSide getOpposite() {
			if(this == TOP){
				return BOTTOM;
			}
			if(this == BOTTOM){
				return TOP;
			}
			if(this == FRONT){
				return BACK;
			}
			if(this == BACK){
				return FRONT;
			}
			if(this == RIGHT){
				return LEFT;
			}
			if(this == LEFT){
				return RIGHT;
			}
			return null;
		}
	}
	/***
	 * @param hitSide = The Side that we want to translate to a BlockSide.
	 * @param machineFacing - The Facing Direction of the block. 
	 * @return We take the Facing Direction of the block and then return the relative blockside versus the requested hitside.
	 */
	public static BlockSide getBlockSide(EnumFacing hitSide, EnumFacing machineFacing) {
		switch(hitSide) {
		case UP: return BlockSide.TOP;
		case DOWN: return BlockSide.BOTTOM;
		case WEST: 
			switch(machineFacing) {
				case NORTH: return BlockSide.RIGHT;
				case SOUTH: return BlockSide.LEFT;
				case EAST:  return BlockSide.BACK;
				case WEST: return BlockSide.FRONT;
				default:
					break;
			}
		case EAST:
			switch(machineFacing) {
				case NORTH:return BlockSide.LEFT;
				case SOUTH:return BlockSide.RIGHT;
				case EAST: return BlockSide.FRONT;
				case WEST:  return BlockSide.BACK;
				default:
					break;
			}
		case NORTH: 
			switch(machineFacing) {
				case NORTH: return BlockSide.FRONT;
				case SOUTH: return BlockSide.BACK;
				case EAST: return BlockSide.RIGHT;
				case WEST:	return BlockSide.LEFT;
				default:
					break;
			}
		case SOUTH:
			switch(machineFacing) {
				case NORTH:  return BlockSide.BACK;
				case SOUTH: return BlockSide.FRONT;
				case EAST: return BlockSide.LEFT;
				case WEST: return BlockSide.RIGHT;
				default:
					break;
			}
		}
		return null;
	}
	public static EnumFacing getAdjustedEnumFacing(EnumFacing facing, EnumFacing horizontal) {
		int metadata = horizontal.ordinal();

		switch(facing) {
		case DOWN: return EnumFacing.DOWN;
		case UP: return EnumFacing.UP;
		case NORTH:
			switch(metadata) {
			case 0: return EnumFacing.NORTH;
			case 1: return EnumFacing.WEST;
			case 2: return EnumFacing.SOUTH;
			case 3: return EnumFacing.EAST;
			}
		case SOUTH:
			switch(metadata) {
			case 0: return EnumFacing.SOUTH;
			case 1: return EnumFacing.EAST;
			case 2: return EnumFacing.NORTH;
			case 3: return EnumFacing.WEST;
			}
		case EAST:
			switch(metadata) {
			case 0: return EnumFacing.EAST;
			case 1: return EnumFacing.NORTH;
			case 2: return EnumFacing.WEST;
			case 3: return EnumFacing.SOUTH;
			}
		case WEST:
			switch(metadata) {
			case 0: return EnumFacing.WEST;
			case 1: return EnumFacing.SOUTH;
			case 2: return EnumFacing.EAST;
			case 3: return EnumFacing.NORTH;
			}
		}
		return EnumFacing.UP;
	}
	public static EnumFacing getEnumFacingFromSide(BlockSide side, EnumFacing facing) {
		if(facing.ordinal() == 2) {
	    	switch(side) {
	    	case BOTTOM : 
	    		return EnumFacing.DOWN;
	    	case TOP : 
	    		return EnumFacing.UP;
	    	case FRONT : 
	    		return EnumFacing.NORTH;
	    	case BACK : 
	    		return EnumFacing.SOUTH;
	    	case RIGHT : 
	    		return EnumFacing.WEST;
	    	case LEFT : 
	    		return EnumFacing.EAST;
	    		default :
	    			break;
	    	}
		}
		if(facing.ordinal() == 3) {
	    	switch(side) {
	    	case BOTTOM : 
	    		return EnumFacing.DOWN;
	    	case TOP : 
	    		return EnumFacing.UP;
	    	case BACK : 
	    		return EnumFacing.NORTH;
	    	case FRONT : 
	    		return EnumFacing.SOUTH;
	    	case LEFT : 
	    		return EnumFacing.WEST;
	    	case RIGHT : 
	    		return EnumFacing.EAST;
	    		default :
	    			break;
	    	}
		}
		if(facing.ordinal() == 4) {
	    	switch(side) {
	    	case BOTTOM : 
	    		return EnumFacing.DOWN;
	    	case TOP : 
	    		return EnumFacing.UP;
	    	case LEFT : 
	    		return EnumFacing.NORTH;
	    	case RIGHT : 
	    		return EnumFacing.SOUTH;
	    	case FRONT : 
	    		return EnumFacing.WEST;
	    	case BACK : 
	    		return EnumFacing.EAST;
	    		default :
	    			break;
	    	}
		}
		if(facing.ordinal() == 5) {
	    	switch(side) {
	    	case BOTTOM : 
	    		return EnumFacing.DOWN;
	    	case TOP : 
	    		return EnumFacing.UP;
	    	case RIGHT : 
	    		return EnumFacing.NORTH;
	    	case LEFT : 
	    		return EnumFacing.SOUTH;
	    	case BACK : 
	    		return EnumFacing.WEST;
	    	case FRONT : 
	    		return EnumFacing.EAST;
	    		default :
	    			break;
	    	}
		}
		return null;
	}
	public static EnumFacing getBlockHorizontal(IBlockState block){
		if(block.getProperties().get(BlockHorizontal.FACING) != null) {
			return block.getValue(BlockHorizontal.FACING);		
		}
		return null;
	}
	public static EnumFacing getEnumFacingFromRenderingInt(int renderingInt, EnumFacing machineFacing) {
		if(machineFacing == EnumFacing.SOUTH) {
			return EnumFacing.values()[renderingInt];
		}else if(machineFacing == EnumFacing.NORTH) {
			if(renderingInt == 4) {
				return EnumFacing.EAST;
			}
			if(renderingInt == 5) {
				return EnumFacing.WEST;
			}
			if(renderingInt == 2) {
				return EnumFacing.SOUTH;
			}
		}else if(machineFacing == EnumFacing.EAST) {
			if(renderingInt == 4) {
				return EnumFacing.SOUTH;
			}
			if(renderingInt == 5) {
				return EnumFacing.NORTH;
			}
			if(renderingInt == 2) {
				return EnumFacing.WEST;
			}
		}else if(machineFacing == EnumFacing.WEST) {
			if(renderingInt == 4) {
				return EnumFacing.NORTH;
			}
			if(renderingInt == 5) {
				return EnumFacing.SOUTH;
			}
			if(renderingInt == 2) {
				return EnumFacing.EAST;
			}
		}
		return EnumFacing.values()[renderingInt];
	}
}
