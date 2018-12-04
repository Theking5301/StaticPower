package theking530.staticpower.blocks.crops;

import net.minecraft.item.Item;

public class StaticPlant extends BaseSimplePlant {

	public StaticPlant(String name) {
		super(name);
	}

	@Override
    public Item getSeeds() {
    	return ModPlants.StaticSeeds;
    }
	@Override
    public Item getCrops() {
    	return ModPlants.StaticCrop;
    }
}
