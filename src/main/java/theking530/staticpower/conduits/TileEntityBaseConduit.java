package theking530.staticpower.conduits;

import javax.annotation.Nullable;

import api.IWrenchTool;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import theking530.staticpower.assists.customboundingbox.CustomBoundingBox;
import theking530.staticpower.assists.customboundingbox.ICustomBoundingBox;
import theking530.staticpower.conduits.staticconduit.TileEntityStaticConduit;

public class TileEntityBaseConduit extends TileEntity implements IConduit, ITickable, ICustomBoundingBox {
	
	public EnumFacing[] connections = new EnumFacing[6];
	public EnumFacing[] receivers = new EnumFacing[6];
	
	public int[] SIDE_MODES = {0,0,0,0,0,0};
	public TileEntityNetwork<TileEntityBaseConduit, Capability<IItemHandler>, IItemHandler> GRID;
	
	public TileEntityBaseConduit() {}
	
	@Override
	public void update() {
		if(GRID == null) {
			generateGrid();
		}
		updateConduitRenderConnections();
		updateRecieverRenderConnections();

	}
	public void generateGrid() {
		if(GRID != null) {
			GRID.INVALID = true;	
		}

		GRID = createNewGrid();
		GRID.AddEntry(this);
		generateGridNeighbors(GRID);  
	}
	public TileEntityNetwork<TileEntityBaseConduit, Capability<IItemHandler>, IItemHandler> createNewGrid() {
		return new TileEntityNetwork<TileEntityBaseConduit, Capability<IItemHandler>, IItemHandler>(getWorld());
	}
	public void generateGridNeighbors(TileEntityNetwork<TileEntityBaseConduit, Capability<IItemHandler>, IItemHandler> masterGrid) {
		masterGrid.AddEntry(this);
		for(int i=0; i<6; i++) {
			TileEntity te = getWorld().getTileEntity(pos.offset(EnumFacing.values()[i]));
			if(te != null) {
				if(te instanceof TileEntityBaseConduit) {
					TileEntityBaseConduit tempCond = (TileEntityBaseConduit)te;
					if(SIDE_MODES[EnumFacing.values()[i].ordinal()] == 2) {
						continue;
					}
					if(tempCond.SIDE_MODES[EnumFacing.values()[i].getOpposite().ordinal()] == 2) {
						continue;
					}
					if(tempCond.GRID != masterGrid) {
						masterGrid.AddEntry(this);
						tempCond.GRID = masterGrid;
						tempCond.generateGridNeighbors(masterGrid);
					}
				}else if(isReciever(EnumFacing.values()[i])) {
					masterGrid.AddEntry(te);
				}
			}

		}
	}
    
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	generateGrid();
    }
	public void onNeighborUpdated(IBlockState observerState, World world, Block oldBlock, BlockPos changedBlockPos, Block newBlock) {
		//If a block is broken, attempt to regenerate the network.
		if(newBlock == Blocks.AIR || world.getTileEntity(changedBlockPos) != null) {
			for(int i=0; i<6; i++) {
				TileEntity te = getWorld().getTileEntity(changedBlockPos.offset(EnumFacing.values()[i]));
				if(te != null && te instanceof TileEntityBaseConduit) {
					TileEntityBaseConduit tempCond = (TileEntityBaseConduit)te;
					if(SIDE_MODES[EnumFacing.values()[i].ordinal()] == 2) {
						continue;
					}
					if(te instanceof TileEntityStaticConduit) {
						if(((TileEntityStaticConduit)te).SIDE_MODES[EnumFacing.values()[i].getOpposite().ordinal()] == 2) {
							continue;
						}
					}
					tempCond.generateGrid();
				}
			}
		}
	}
	public void updateConduitRenderConnections() {
		if(isConduit(EnumFacing.UP)) connections[0] = EnumFacing.UP;
		else connections[0] = null;
		if(isConduit(EnumFacing.DOWN)) connections[1] = EnumFacing.DOWN;
		else connections[1] = null;
		if(isConduit(EnumFacing.NORTH)) connections[2] = EnumFacing.NORTH;
		else connections[2] = null;
		if(isConduit(EnumFacing.SOUTH)) connections[3] = EnumFacing.SOUTH;
		else connections[3] = null;
		if(isConduit(EnumFacing.EAST)) connections[4] = EnumFacing.EAST;
		else connections[4] = null;
		if(isConduit(EnumFacing.WEST)) connections[5] = EnumFacing.WEST;
		else connections[5] = null;	
		
	}	
	public void updateRecieverRenderConnections() {
		if(isReciever(EnumFacing.UP)) receivers[0] = EnumFacing.UP;
		else receivers[0] = null;
		if(isReciever(EnumFacing.DOWN)) receivers[1] = EnumFacing.DOWN;
		else receivers[1] = null;
		if(isReciever(EnumFacing.NORTH)) receivers[3] = EnumFacing.NORTH;
		else receivers[3] = null;
		if(isReciever(EnumFacing.SOUTH)) receivers[2] = EnumFacing.SOUTH;
		else receivers[2] = null;
		if(isReciever(EnumFacing.EAST)) receivers[4] = EnumFacing.EAST;
		else receivers[4] = null;
		if(isReciever(EnumFacing.WEST)) receivers[5] = EnumFacing.WEST;
		else receivers[5] = null;		
	}	
		
    @Override  
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
		for(int i=0; i<6; i++) {
			SIDE_MODES[i] = nbt.getInteger("SIDE"+i);
		}
    }		
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
		for(int i=0; i<6; i++) {
			nbt.setInteger("SIDE"+i, SIDE_MODES[i]);
		}
		return nbt;
	}	
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket(){
    	NBTTagCompound tag = new NBTTagCompound();
    	writeToNBT(tag);
    	return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
    }   
	@Override
	public boolean isConduit(EnumFacing side) {
		return false;
	}		
	@Override
	public boolean isReciever(EnumFacing side) {
		return false;	
	}	
	public boolean straightConnection(EnumFacing[] directions) {
		EnumFacing mainDirection = null;
		boolean isOpposite = false;
		
		for (int i = 0; i < directions.length; i++) {
			if(mainDirection == null && directions[i] != null) mainDirection = directions[i];
			
			if(directions[i] != null && mainDirection != directions[i]) {
				if(!isOpposite(mainDirection, directions[i])) {
					return false;
				} else {
					isOpposite = true;
				}
			}
		}	
		return isOpposite;
	}	
	public boolean isOpposite(EnumFacing firstDirection, EnumFacing secondDirection) {
		return firstDirection.getOpposite() == secondDirection;
	}
	public boolean disconected(EnumFacing from) {
		if(SIDE_MODES[from.ordinal()] == 2){
			return true;
		}
		return false;
	}

	public void conduitBroken() {
		
	}
	public void conduitSneakWrenched(EnumFacing side, int subHit) {
		
	}
	public void conduitWrenched(EnumFacing side, int subHit) {
		EnumFacing adjustedSide = subHit == 0 ? side : EnumFacing.values()[subHit-1];	
		SIDE_MODES[adjustedSide.ordinal()] = SIDE_MODES[adjustedSide.ordinal()] == 2 ? 0 : 2;
		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
	}	
	@Override
	public boolean shouldRenderCustomHitBox(int subHit, EntityPlayer player) {
		if(player.getHeldItemMainhand().getItem() instanceof IWrenchTool) {
			return true;
		}
		return true;
	}
	@Override
	public CustomBoundingBox getCustomBoundingBox(int subHit, EntityPlayer player) {
		float pixel = 1F/16F;
	   	float max = 1-10*pixel/2;
    	float min = 10*pixel/2;
    	float min2 = 11*pixel/2;
    	
    	float x = getPos().getX();
    	float y = getPos().getY();
    	float z = getPos().getZ();

    	//System.out.println(subHit);
    	
		switch(subHit) {
			case 0: return new CustomBoundingBox(min, min, min, x+min2, max+y-pixel*5.5, max+z-min2);
			case 1: return new CustomBoundingBox(min+pixel, min, min, max+x-min2, max+y-pixel*11, max+z-min2);
			case 2: return new CustomBoundingBox(min+0.05, min, min, x+min2, max+y-pixel, max+z-min2);
			case 3: return new CustomBoundingBox(min, min+pixel/2, min, x+min2, max+y-min2, max+z-min2-pixel*5.5);
			case 4: return new CustomBoundingBox(min, min+pixel, min, x+min2, max+y-min2, max+z-min2+pixel*4.5);
			case 5: return new CustomBoundingBox(min, min, min+pixel, x, max+y-min2, max+z-min2);
			case 6: return new CustomBoundingBox(min, min, min+pixel, x+10*pixel, max+y-min2, max+z-min2);
		}
		return null;
	}
}
