package theking530.staticpower.tileentity.digistorenetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.tileentity.digistorenetwork.digistore.TileEntityDigistore;
import theking530.staticpower.tileentity.digistorenetwork.ioport.TileEntityDigistoreIOPort;
import theking530.staticpower.tileentity.digistorenetwork.manager.TileEntityDigistoreManager;
import theking530.staticpower.tileentity.digistorenetwork.networkwire.BlockDigistoreNetworkWire;

public class DigistoreNetwork {
	
	private Map<BlockPos, BaseDigistoreTileEntity> masterDigistoreList;
	private List<TileEntityDigistore> digistoreList;
	private List<BlockPos> wirePositions;
	private List<TileEntityDigistoreIOPort> ioPortList;
	
	private World world;
	private TileEntityDigistoreManager manager;
	
	public DigistoreNetwork(TileEntityDigistoreManager manager) {
		this.manager = manager;
		this.world = manager.getWorld();
	}
	private Map<BlockPos, BaseDigistoreTileEntity> createNewGrid() {
		return new HashMap<BlockPos, BaseDigistoreTileEntity>();
	}
	public void updateGrid() {
		masterDigistoreList = createNewGrid();
		digistoreList = new ArrayList<TileEntityDigistore>();
		ioPortList = new ArrayList<TileEntityDigistoreIOPort>();
		wirePositions = new ArrayList<BlockPos>();
		masterDigistoreList.put(manager.getPos(), manager);
		manager.setManager(manager);
		worker_updateGrid(masterDigistoreList, manager.getPos()); 
	}
	private void worker_updateGrid(Map<BlockPos, BaseDigistoreTileEntity> grid, BlockPos currentPos) {
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos testPos = currentPos.offset(facing);
			TileEntity te = world.getTileEntity(testPos);
			if(te != null && !te.isInvalid() && te instanceof BaseDigistoreTileEntity) {
				if(!grid.containsKey(testPos)) {
					if(te instanceof TileEntityDigistoreManager) {
						//TileEntityDigistoreManager oldManager = (TileEntityDigistoreManager)te;
						//oldManager.masterDigistoreList = createNewGrid();
						continue;
					}
					BaseDigistoreTileEntity digistore = (BaseDigistoreTileEntity)te;
					digistore.setManager(manager);
					grid.put(testPos, digistore);
					sortTileEntity(digistore);
					worker_updateGrid(grid, testPos);
				}
			}else if(!wirePositions.contains(testPos) && world.getBlockState(testPos).getBlock() instanceof BlockDigistoreNetworkWire) {
				wirePositions.add(testPos);
				worker_updateGrid(grid, testPos);
			}
		}
	}
	
	public Map<BlockPos, BaseDigistoreTileEntity> getMasterList() {
		return masterDigistoreList;
	}
	public List<TileEntityDigistore> getDigistoreList() {
		return digistoreList;
	}
	public List<TileEntityDigistoreIOPort> getIOPortList() {
		return ioPortList;
	}
	public List<BlockPos> getExtenderPositionList() {
		return wirePositions;
	}
	
	private void sortTileEntity(BaseDigistoreTileEntity te) {
		if(te instanceof TileEntityDigistore) {
			digistoreList.add((TileEntityDigistore)te);
		}else if(te instanceof TileEntityDigistoreIOPort) {
			ioPortList.add((TileEntityDigistoreIOPort)te);
		}
	}
}
