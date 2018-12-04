package theking530.staticpower.tileentity;

public interface IProcessing {
	
	public int getProcessingTime();
	public int getCurrentProgress();
	public float getProcessingPercentage();
	
    /**
     * @return True if the machine can process given all factors (ie. Enough Power or Fluid)
     **/
	public boolean canProcess();
	public boolean isProcessing();
	public boolean isMoving();
    /**
     * @return True if the machine can process given only the input items/fluids.
     **/
	public boolean hasValidRecipe();
}
