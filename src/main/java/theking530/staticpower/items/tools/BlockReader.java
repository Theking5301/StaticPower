package theking530.staticpower.items.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import theking530.staticpower.conduits.TileEntityBaseConduit;
import theking530.staticpower.items.ItemBase;
import theking530.staticpower.machines.refinery.BaseRefineryTileEntity;
import theking530.staticpower.tileentity.digistorenetwork.BaseDigistoreTileEntity;

public class BlockReader extends ItemBase{

	public BlockReader(String name) {
		super(name);
	}
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
    	TileEntity tile = (TileEntity) world.getTileEntity(pos);
    	if(tile instanceof TileEntityBaseConduit) {
    		TileEntityBaseConduit te = (TileEntityBaseConduit) tile;
    		if(te.GRID != null) {
    			if(!world.isRemote) {
        			player.sendMessage(new TextComponentString(te.GRID.toString()));	
    				player.sendMessage(new TextComponentString(pos.toString()));
    				
    			    Iterator<Entry<BlockPos, TileEntity>> it = te.GRID.RECIEVER_STORAGE_MAP.entrySet().iterator();
    			    while (it.hasNext()) {
    			        Map.Entry<BlockPos, TileEntity> pair = (Map.Entry<BlockPos, TileEntity>)it.next();	
    			        if(world.getTileEntity(pair.getKey()) != null) {
    				        IEnergyStorage storage = world.getTileEntity(pair.getKey()).getCapability(CapabilityEnergy.ENERGY, null);
    				        if(storage != null && storage.canReceive() && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
    	        				//player.sendMessage(new TextComponentString("Path: " + te.GRID.doBFSShortestPath(pos, pair.getKey()).toString()));
    				        }   	
    			        }  

    			    } 
    			}    		
    	    	return EnumActionResult.SUCCESS;
    		}
    	}else if(tile instanceof BaseDigistoreTileEntity) {
    		BaseDigistoreTileEntity te = (BaseDigistoreTileEntity) tile;
    		if(te.hasManager() && te.getManager().getNetwork() != null) {
    			if(!world.isRemote) {
        			player.sendMessage(new TextComponentString(te.getManager().getNetwork().getMasterList().toString()));	
    				player.sendMessage(new TextComponentString(te.getManager().getNetwork().getMasterList().size() + ""));
    			}    		
    	    	return EnumActionResult.SUCCESS;
    		}
    	}else if(tile instanceof BaseRefineryTileEntity) {
    		BaseRefineryTileEntity te = (BaseRefineryTileEntity) tile;
    		if(te.hasController() && te.getContorller().getNetwork() != null) {
    			if(!world.isRemote) {
        			player.sendMessage(new TextComponentString(te.getContorller().getNetwork().getMasterList().toString()));
    				player.sendMessage(new TextComponentString((te.getContorller().getNetwork().getMasterList().size() + te.getContorller().getNetwork().getExtenderPositionList().size()) + ""));
    			}    		
    	    	return EnumActionResult.SUCCESS;
    		}
    	}else{
			if(!world.isRemote) {
				player.sendMessage(new TextComponentString(pos.toString()));
			}
    	}
    	return EnumActionResult.PASS;
    }
}
