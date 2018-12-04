package theking530.staticpower.machines.tileentitycomponents.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import theking530.staticpower.assists.utilities.SideModeList.Mode;

public class StaticPowerContainerSlot extends SlotItemHandler {

	private ItemStack previewItem;
	private float previewAlpha;
	private Mode mode;

	public StaticPowerContainerSlot(ItemStack previewItem, float alpha, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.previewItem = previewItem;
		this.previewAlpha = alpha;
		this.mode = null;
	}
	public StaticPowerContainerSlot(ItemStack previewItem, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		this(previewItem, 0.3f, itemHandler, index, xPosition, yPosition);
	}
	public StaticPowerContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		this(ItemStack.EMPTY, 1.0f, itemHandler, index, xPosition, yPosition);
	}
	public Mode getMode() {
		return mode;
	}
	public StaticPowerContainerSlot setMode(Mode newMode) {
		mode = newMode;
		return this;
	}
	public ItemStack getPreviewItem(){
		return previewItem;
	}
	public float getPreviewAlpha() {
		return previewAlpha;
	}
	public void setPreviewItem(ItemStack newItem){
		previewItem = newItem;
	}
	public void settPreviewAlpha(float newAlpha) {
		previewAlpha = newAlpha;
	}
}
