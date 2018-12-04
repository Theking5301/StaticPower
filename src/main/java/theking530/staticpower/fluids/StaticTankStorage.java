package theking530.staticpower.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class StaticTankStorage extends FluidTank {
	private String name;
	
    public StaticTankStorage(String name, int capacity) {
    	super(capacity);
    	this.name = name;
    }
	public StaticTankStorage(String name, Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public boolean canFill(FluidStack input) {
		if(!canFillFluidType(input)) {
			return false;
		}
		if(input == null) {
			return false;
		}
		return input.amount == fill(input, false);
	}
}
