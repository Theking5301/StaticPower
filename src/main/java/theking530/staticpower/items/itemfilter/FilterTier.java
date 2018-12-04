package theking530.staticpower.items.itemfilter;

public enum FilterTier {
	BASIC(3), UPGRADED(7), ADVANCED(9);
	
	private int slotCount;
	
	private FilterTier(int slotCount) {
		this.slotCount = slotCount;
    }
	public int getSlotCount() {
		return slotCount;
	}
}
