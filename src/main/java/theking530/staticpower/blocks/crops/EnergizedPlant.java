package theking530.staticpower.blocks.crops;

import net.minecraft.item.Item;

public class EnergizedPlant extends BaseSimplePlant {

	public EnergizedPlant(String name) {
		super(name);
	}

	@Override
    public Item getSeeds() {
    	return ModPlants.EnergizedSeeds;
    }
	@Override
    public Item getCrops() {
    	return ModPlants.EnergizedCrop;
    }
}