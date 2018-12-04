package theking530.staticpower.conduits;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import api.IWrenchable;
import api.RegularWrenchMode;
import api.SneakWrenchMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theking530.staticpower.StaticPower;
import theking530.staticpower.handlers.ModEvents;

public class BaseConduitBlock extends Block implements IWrenchable {

	private float PIXEL = 1F/16F;

	protected BaseConduitBlock(String name) {
		super(Material.GLASS);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(StaticPower.StaticPower);
		this.lightOpacity = 0;
		//RegisterHelper.registerItem(new BaseItemBlock(this, name));
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    	float max = 1-12*PIXEL/2;
    	float min = 12*PIXEL/2;

    	AxisAlignedBB base = new AxisAlignedBB(min, min, min, max, max, max);
    	
    	if(!(source.getTileEntity(pos) instanceof TileEntityBaseConduit)) {
    		return base;
    	}
		TileEntityBaseConduit conduit = (TileEntityBaseConduit) source.getTileEntity(pos);
		if (conduit != null) {
			if (conduit.connections[0] != null) {
				base = base.union(new AxisAlignedBB(min, 1.0F, min, max, max, max));
			}
			if (conduit.connections[1] != null) {
				base = base.union(new AxisAlignedBB(min, min, min, max, 0.0F, max));
			}
			if (conduit.connections[2] != null) {
				base = base.union(new AxisAlignedBB(min, min, 0.0F, max, max, max));
			}
			if (conduit.connections[3] != null) {
				base = base.union(new AxisAlignedBB(min, min, min, max, max, 1.0F));
			}
			if (conduit.connections[4] != null) {
				base = base.union(new AxisAlignedBB(1.0f, min, min, max, max, max));
			}
			if (conduit.connections[5] != null) {
				base = base.union(new AxisAlignedBB(0.0, min, min, max, max, max));
			}
			
		}
		return base;
	}
	@Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean b) {
    	float max = 1-11*PIXEL/2;
    	float min = 11*PIXEL/2;

		AxisAlignedBB bb = new AxisAlignedBB(min, min, min, max, max, max);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
		TileEntityBaseConduit conduit = (TileEntityBaseConduit) world.getTileEntity(pos);

		if (conduit != null) {
			if (conduit.connections[0] != null) {
				bb = new AxisAlignedBB(min, 1.0F, min, max, max, max);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			if (conduit.connections[1] != null) {
				bb = new AxisAlignedBB(min, min, min, max, 0.0F, max);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			if (conduit.connections[2] != null) {
				bb = new AxisAlignedBB(min, min, 0.0F, max, max, max);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			if (conduit.connections[3] != null) {
				bb = new AxisAlignedBB(min, min, min, max, max, 1.0F);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			if (conduit.connections[4] != null) {
				bb = new AxisAlignedBB(1.0F, min, min, max, max, max);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			if (conduit.connections[5] != null) {
				bb = new AxisAlignedBB(0.0F, min, min, max, max, max);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
			}
			
		}
	}
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end) {
		List<AxisAlignedBB> aabbList = new ArrayList<AxisAlignedBB>();
    	float max = 1-11*PIXEL/2;
    	float min = 11*PIXEL/2;
    	
    	aabbList.add(new AxisAlignedBB(min, min, min, max, max, max));
		TileEntityBaseConduit conduit = (TileEntityBaseConduit) world.getTileEntity(pos);

		if (conduit != null) {
			if (conduit.connections[1] != null) {
				aabbList.add(new AxisAlignedBB(min, min, min, max, 0.0F, max));
			}else{
				aabbList.add(null);
			}
			if (conduit.connections[0] != null) {
				aabbList.add(new AxisAlignedBB(min, 1.0F, min, max, max, max));
			}else{
				aabbList.add(null);
			}
			if (conduit.connections[2] != null) {
				aabbList.add(new AxisAlignedBB(min, min, 0.0F, max, max, max));
			}else{
				aabbList.add(null);
			}
			if (conduit.connections[3] != null) {
				aabbList.add(new AxisAlignedBB(min, min, min, max, max, 1.0F));
			}else{
				aabbList.add(null);
			}
			if (conduit.connections[5] != null) {
				aabbList.add(new AxisAlignedBB(0.0F, min, min, max, max, max));
			}else{
				aabbList.add(null);
			}
			if (conduit.connections[4] != null) {
				aabbList.add(new AxisAlignedBB(1.0F, min, min, max, max, max));
			}else{
				aabbList.add(null);
			}
			
			for(int i=0; i<aabbList.size(); i++) {
				if(aabbList.get(i) == null) {
					continue;
				}
				// r.dir is unit direction vector of ray

				Vec3d r = end.subtract(start).normalize();
				double xDir = 1.0f / r.x;
				double yDir = 1.0f / r.y;
				double zDir = 1.0f / r.z;
				
				Vec3d dirfrac = new Vec3d(xDir, yDir, zDir);
				
				// lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
				// r.org is origin of ray
				float t1 = (float) ((aabbList.get(i).minX+pos.getX() - start.x)*dirfrac.x);
				float t2 = (float) ((aabbList.get(i).maxX+pos.getX() - start.x)*dirfrac.x);
				float t3 = (float) ((aabbList.get(i).minY+pos.getY() - start.y)*dirfrac.y);
				float t4 = (float) ((aabbList.get(i).maxY+pos.getY() - start.y)*dirfrac.y);
				float t5 = (float) ((aabbList.get(i).minZ+pos.getZ() - start.z)*dirfrac.z);
				float t6 = (float) ((aabbList.get(i).maxZ+pos.getZ() - start.z)*dirfrac.z);

				float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
				float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

				// if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
				if (tmax < 0) {
				   	continue;
				}

				// if tmin > tmax, ray doesn't intersect AABB
				if (tmin > tmax) {
				   	continue;
				}

		        Vec3d vec3d = start.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		        Vec3d vec3d1 = end.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		        RayTraceResult raytraceresult = aabbList.get(i).calculateIntercept(vec3d, vec3d1);
		        raytraceresult = raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), raytraceresult.sideHit, pos);
		        if(raytraceresult != null) {
					raytraceresult.subHit = i;
					return raytraceresult;
		        }
			}		
			
		}

		return null;
	}
	
	@Override
	public boolean canBeWrenched(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, boolean sneaking) {
		return true;
	}	
	@Override
	public void wrenchBlock(EntityPlayer player, RegularWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {		
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null) {
				if(te instanceof TileEntityBaseConduit) {
	    			RayTraceResult target = ModEvents.retraceBlock(world, player, pos);
	    			if(target != null) {
	        			TileEntityBaseConduit cond = (TileEntityBaseConduit)te;
	        			cond.conduitWrenched(facing,  target.subHit);
	        			world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 3);
	    			}
				}
			}
		}
	}	
	@Override
	public void sneakWrenchBlock(EntityPlayer player, SneakWrenchMode mode, ItemStack wrench, World world, BlockPos pos, EnumFacing facing, boolean returnDrops) {
		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null) {
				if(te instanceof TileEntityBaseConduit) {
	    			RayTraceResult target = ModEvents.retraceBlock(world, player, pos);
	    			if(target != null) {
	        			TileEntityBaseConduit cond = (TileEntityBaseConduit)te;
	        			cond.conduitSneakWrenched(facing,  target.subHit);
	    			}
				}
			}
			
			ItemStack conduitStack = new ItemStack(Item.getItemFromBlock(this));
			EntityItem droppedItem = new EntityItem(world, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, conduitStack);
			world.spawnEntity(droppedItem);
			world.setBlockToAir(pos);
			world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
		}
	}
	
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	if(!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null) {
				if(te instanceof TileEntityBaseConduit) {
        			TileEntityBaseConduit cond = (TileEntityBaseConduit)te;
        			cond.conduitBroken();
				}
			}
    	}     
        super.breakBlock(world, pos, state);
    }
	@Override	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	return false;
	}
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityBaseConduit) {
    		((TileEntityBaseConduit)world.getTileEntity(pos)).onBlockPlacedBy(world, pos, state, placer, stack);
    	}
    }
	@Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos){
		super.onNeighborChange(world, observerPos, changedBlockPos);
    	if(world.getTileEntity(observerPos) != null && world.getTileEntity(observerPos) instanceof TileEntityBaseConduit) {
    		((TileEntityBaseConduit)world.getTileEntity(observerPos)).onNeighborUpdated(observerState, world, changedBlock, changedBlockPos, world.getBlockState(changedBlockPos).getBlock());
    	}
    }
	
	@Override	
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}
}
