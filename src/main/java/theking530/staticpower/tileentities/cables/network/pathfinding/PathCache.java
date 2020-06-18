package theking530.staticpower.tileentities.cables.network.pathfinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.math.BlockPos;
import theking530.staticpower.tileentities.cables.network.CableNetwork;

public class PathCache {
	public static final Logger LOGGER = LogManager.getLogger(PathCache.class);
	/** Map of destinations to map of source blocks and paths. */
	private HashMap<BlockPos, HashMap<BlockPos, List<Path>>> Cache;
	private CableNetwork OwningNetwork;

	public PathCache(CableNetwork owningNetwork) {
		Cache = new HashMap<BlockPos, HashMap<BlockPos, List<Path>>>();
		OwningNetwork = owningNetwork;
	}

	/**
	 * Checks to see if we have a path between a particular source and destination.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public boolean hasPath(BlockPos source, BlockPos destination) {
		return Cache.get(destination) != null && Cache.get(destination).get(source) != null;
	}

	/**
	 * Gets the paths between the provided source and destination if one exists.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public @Nullable List<Path> getPaths(BlockPos cablePosition, BlockPos destination) {
		if (cablePosition == null) {
			LOGGER.error("Attemtping to find a path with a null source position.");
			return null;
		}

		if (destination == null) {
			LOGGER.error("Attemtping to find a path with a null destination position.");
			return null;
		}

		if (hasPath(cablePosition, destination)) {
			return Cache.get(destination).get(cablePosition);
		} else {
			return cacheNewPath(cablePosition, destination);
		}
	}

	/**
	 * Attempts to calculate the paths between the provided source and destination,
	 * and returns the calculated paths if successful.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	private List<Path> cacheNewPath(BlockPos source, BlockPos destination) {
		// Perform the path finding.
		NetworkPathFinder pathFinder = new NetworkPathFinder(OwningNetwork.getGraph(), OwningNetwork.getWorld(), source, destination);
		List<Path> paths = pathFinder.executeAlgorithm();

		// If we found no paths, return early.
		if (paths.size() == 0) {
			LOGGER.warn(String.format("Unabled to find any paths between source: %1$s and destination: %2$s.", source, destination));
			return null;
		}

		// Cache each provided path.
		if (!Cache.containsKey(destination)) {
			Cache.put(destination, new HashMap<BlockPos, List<Path>>());
		}
		if (!Cache.get(destination).containsKey(source)) {
			Cache.get(destination).put(source, new ArrayList<Path>());
		}
		Cache.get(destination).get(source).addAll(paths);

		// If we have a destination for this, sort it by length.
		if (Cache.containsKey(destination)) {
			Cache.get(destination).get(source).sort(Comparator.comparingInt(Path::getLength));
		}

		// Check if we now have the path.
		return paths;
	}

	/**
	 * Clears all cached paths.
	 */
	public void invalidateCache() {
		Cache.clear();
	}
}
