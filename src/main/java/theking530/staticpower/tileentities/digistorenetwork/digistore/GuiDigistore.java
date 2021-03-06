package theking530.staticpower.tileentities.digistorenetwork.digistore;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import theking530.api.digistore.IDigistoreInventory;
import theking530.staticcore.gui.GuiDrawUtilities;
import theking530.staticcore.gui.widgets.button.StandardButton;
import theking530.staticcore.gui.widgets.button.StandardButton.MouseButton;
import theking530.staticcore.gui.widgets.button.TextButton;
import theking530.staticcore.gui.widgets.tabs.GuiInfoTab;
import theking530.staticcore.utilities.Color;
import theking530.staticcore.utilities.GuiDrawItem;
import theking530.staticpower.client.gui.StaticPowerTileEntityGui;
import theking530.staticpower.network.NetworkMessage;
import theking530.staticpower.network.StaticPowerMessageHandler;
import theking530.staticpower.utilities.MetricConverter;

public class GuiDigistore extends StaticPowerTileEntityGui<ContainerDigistore, TileEntityDigistore> {

	private GuiInfoTab infoTab;
	private TextButton lockedButton;
	private final GuiDrawItem itemRenderer;
	private final IDigistoreInventory inventory;

	public GuiDigistore(ContainerDigistore container, PlayerInventory invPlayer, ITextComponent name) {
		super(container, invPlayer, name, 176, 150);
		itemRenderer = new GuiDrawItem();
		inventory = getTileEntity().inventory;
	}

	@Override
	public void initializeGui() {
		registerWidget(lockedButton = new TextButton(10, 23, 50, 20, "Locked", this::buttonPressed));
		lockedButton.setToggleable(true);
		lockedButton.setText(getTileEntity().isLocked() ? "Locked" : "Unlocked");
		lockedButton.setToggled(getTileEntity().isLocked());

		getTabManager().registerTab(infoTab = new GuiInfoTab(getTileEntity().getDisplayName().getString(), 100));
	}

	public void buttonPressed(StandardButton button, MouseButton mouseButton) {
		if (getTileEntity() != null) {
			getTileEntity().setLocked(!getTileEntity().isLocked());
			button.setToggled(getTileEntity().isLocked());
			lockedButton.setText(getTileEntity().isLocked() ? "Locked" : "Unlocked");

			NetworkMessage msg = new PacketLockDigistore(getTileEntity().isLocked(), getTileEntity().getPos());
			StaticPowerMessageHandler.MAIN_PACKET_CHANNEL.sendToServer(msg);
		}
	}

	@Override
	public void updateData() {
		// Update the info tab.
		infoTab.clear();
		infoTab.addLine("desc", new StringTextComponent("Stores a large amount of a single item."));
		infoTab.addLineBreak();

		// Pass the itemstack count through the metric converter.
		MetricConverter count = new MetricConverter(inventory.getItemCapacity());
		infoTab.addKeyValueLine("max", new StringTextComponent("Max Items"),
				new StringTextComponent(TextFormatting.WHITE.toString()).append(new StringTextComponent(count.getValueAsString(true))), TextFormatting.RED);
	}

	@Override
	protected void drawForegroundExtras(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawForegroundExtras(stack, partialTicks, mouseX, mouseY);
		if (inventory.getItemCapacity() > 0) {
			if (mouseX >= guiLeft + 76 && mouseX <= guiLeft + 100 && mouseY >= guiTop + 21 && mouseY <= guiTop + 45) {
				GuiDrawUtilities.drawColoredRectangle(stack, 79, 19, 18, 18, 1.0f, new Color(200, 200, 200, 200).fromEightBitToFloat());
			}
		}
	}

	@Override
	protected void getExtraTooltips(List<ITextComponent> tooltips, MatrixStack stack, int mouseX, int mouseY) {
		super.getExtraTooltips(tooltips, stack, mouseX, mouseY);
		if (inventory.getItemCapacity() > 0) {
			if (mouseX >= guiLeft + 76 && mouseX <= guiLeft + 100 && mouseY >= guiTop + 21 && mouseY <= guiTop + 45) {
				tooltips.addAll(inventory.getDigistoreStack(0).getStoredItem().getTooltip(Minecraft.getInstance().player, TooltipFlags.NORMAL));
			}
		}
	}

	@Override
	protected void drawBackgroundExtras(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawBackgroundExtras(stack, partialTicks, mouseX, mouseY);

		// Draw the massive digistore slot.
		drawEmptySlot(stack, 78, 18, 20, 20);

		// Draw the item.
		if (inventory.getCurrentUniqueItemTypeCount() > 0) {
			RenderHelper.enableStandardItemLighting();
			itemRenderer.drawItem(inventory.getDigistoreStack(0).getStoredItem(), guiLeft, guiTop, 80, 21, 1.0f);
			RenderHelper.disableStandardItemLighting();

			// Pass the itemstack count through the metric converter.
			MetricConverter count = new MetricConverter(inventory.getTotalContainedCount());

			// Draw the item count string.
			GuiDrawUtilities.drawStringWithSize(stack, count.getValueAsString(true), 98, 37, 0.5f, Color.EIGHT_BIT_WHITE, true);
		}
	}
}
