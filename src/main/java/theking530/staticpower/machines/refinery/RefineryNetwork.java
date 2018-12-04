package theking530.staticpower.machines.refinery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theking530.staticpower.machines.refinery.controller.TileEntityFluidRefineryController;
import theking530.staticpower.tileentity.digistorenetwork.manager.TileEntityDigistoreManager;

public class RefineryNetwork {
	
	private Map<BlockPos, BaseRefineryTileEntity> masterDigistoreList;
	private List<BlockPos> extenderPositions;
	
	private World world;
	private TileEntityFluidRefineryController controller;
	
	public RefineryNetwork(TileEntityFluidRefineryController controller) {
		this.controller = controller;
		this.world = controller.getWorld();
	}
	private Map<BlockPos, BaseRefineryTileEntity> createNewGrid() {
		return new HashMap<BlockPos, BaseRefineryTileEntity>();
	}
	public void updateGrid() {
		masterDigistoreList = createNewGrid();
		extenderPositions = new ArrayList<BlockPos>();
		masterDigistoreList.put(controller.getPos(), controller);
		controller.setController(controller);
		worker_updateGrid(masterDigistoreList, controller.getPos()); 
	}
	private void worker_updateGrid(Map<BlockPos, BaseRefineryTileEntity> grid, BlockPos currentPos) {
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos testPos = currentPos.offset(facing);
			TileEntity te = world.getTileEntity(testPos);
			if(te != null && !te.isInvalid() && te instanceof BaseRefineryTileEntity) {
				if(!grid.containsKey(testPos)) {
					if(te instanceof TileEntityDigistoreManager) {
						continue;
					}
					BaseRefineryTileEntity refinery = (BaseRefineryTileEntity)te;
					refinery.setController(controller);;
					grid.put(testPos, refinery);
					worker_updateGrid(grid, testPos);
				}
			}
			else if(!extenderPositions.contains(testPos) && world.getBlockState(testPos).getBlock() instanceof BlockFluidRefineryCasing) {
				extenderPositions.add(testPos);
				worker_updateGrid(grid, testPos);
			}
		}
	}
	
	public Map<BlockPos, BaseRefineryTileEntity> getMasterList() {
		return masterDigistoreList;
	}
	public List<BlockPos> getExtenderPositionList() {
		return extenderPositions;
	}
}
