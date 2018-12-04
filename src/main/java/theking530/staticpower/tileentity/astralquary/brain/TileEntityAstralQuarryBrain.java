package theking530.staticpower.tileentity.astralquary.brain;

import java.util.Random;

import net.minecraft.item.ItemStack;
import theking530.staticpower.assists.utilities.InventoryUtilities;
import theking530.staticpower.machines.tileentitycomponents.TileEntityItemOutputServo;
import theking530.staticpower.tileentity.TileEntityBase;
import theking530.staticpower.tileentity.astralquary.AstralQuarryOreGenerator;

public class TileEntityAstralQuarryBrain extends TileEntityBase {

	public TileEntityAstralQuarryBrain() {
		initializeSlots(0, 0, 9);
		registerComponent(new TileEntityItemOutputServo(this, 1, slotsOutput, 0, 1, 2, 3, 4, 5, 6, 7, 8));
	}
	@Override
	public void process() {
		AstralQuarryOreGenerator.initializeAstralQuaryOres();
		
		Random rand = new Random();
		int index = rand.nextInt(AstralQuarryOreGenerator.ORES.size());
		
		ItemStack stack = AstralQuarryOreGenerator.ORES.get(index).copy();
		stack.setCount(1);		
		InventoryUtilities.insertItemIntoInventory(slotsOutput, stack);
		
	}
	@Override
	public String getName() {
		return "container.AstralQuarry";
	}
}
