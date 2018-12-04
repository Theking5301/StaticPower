package theking530.staticpower.blocks.crops;

import net.minecraft.item.Item;

public class LumumPlant extends BaseSimplePlant {

	public LumumPlant(String name) {
		super(name);
	}

	@Override
    public Item getSeeds() {
    	return ModPlants.LumumSeeds;
    }
	@Override
    public Item getCrops() {
    	return ModPlants.LumumCrop;
    }
}