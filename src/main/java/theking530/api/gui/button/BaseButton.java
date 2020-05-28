package theking530.api.gui.button;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.fml.client.gui.GuiUtils;
import theking530.api.gui.IInteractableGui;

public abstract class BaseButton extends AbstractGui {

	public enum ClickedState {
		NONE, LEFT, RIGHT, MIDDLE;
	}

	protected int width;
	protected int height;
	protected int xPosition;
	protected int yPosition;

	private boolean hovered = false;
	private boolean isVisible = true;
	private ClickedState clicked = ClickedState.NONE;

	private boolean toggleable = false;
	private boolean toggled = false;

	private int mouseX;
	private int mouseY;

	private float clickSoundPitch;

	protected IInteractableGui owningGui;

	private List<String> tooltip;

	public BaseButton(int width, int height, int xPos, int yPos) {
		this.width = width;
		this.height = height;
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.clickSoundPitch = 1.0f;
	}

	public void draw() {
		if (!isVisible()) {
			return;
		}
		GlStateManager.disableLighting();
		drawButton();
		drawExtra();
		GlStateManager.enableLighting();
	}

	public void handleMouseInteraction(int mouseX, int mouseY, int button) {
		if (!isVisible()) {
			return;
		}
		if (mouseX > owningGui.getGuiLeft() + xPosition && mouseX < owningGui.getGuiLeft() + xPosition + width && isVisible) {
			if (mouseY > owningGui.getGuiTop() + yPosition && mouseY < owningGui.getGuiTop() + yPosition + height) {
				clicked = button == 0 ? ClickedState.LEFT : ClickedState.RIGHT;
				playSound(clicked);
				if (toggleable) {
					toggled = !toggled;
				}
			}
		}
	}

	public void handleMouseMoveInteraction(int mouseX, int mouseY) {
		if (!isVisible()) {
			return;
		}
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		if (mouseX > owningGui.getGuiLeft() + xPosition && mouseX < owningGui.getGuiLeft() + xPosition + width && isVisible) {
			if (mouseY > owningGui.getGuiTop() + yPosition && mouseY < owningGui.getGuiTop() + yPosition + height) {
				hovered = true;
				return;
			}
		}
		hovered = false;
	}

	protected abstract void drawButton();

	protected void drawExtra() {
	}

	public void setOwningGui(IInteractableGui owningGui) {
		this.owningGui = owningGui;
	}

	protected void playSound(ClickedState state) {
		float pitch = state == ClickedState.LEFT ? clickSoundPitch : clickSoundPitch / 1.2f;
		// Minecraft.getInstance().getSoundHandler().play(SoundEvents.UI_BUTTON_CLICK);
	}

	public void drawTooltip() {
		if (tooltip != null && tooltip.size() > 0) {
			GlStateManager.disableDepthTest();
			GuiUtils.drawHoveringText(tooltip, mouseX - 4, mouseY + 8, Minecraft.getInstance().getMainWindow().getWidth(), Minecraft.getInstance().getMainWindow().getHeight(), -1,
					Minecraft.getInstance().fontRenderer);
			GlStateManager.disableLighting();
			GlStateManager.disableDepthTest();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

	public void setPosition(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public boolean isToggleable() {
		return toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isHovered() {
		return hovered;
	}

	public boolean isClicked() {
		return clicked != ClickedState.NONE;
	}

	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	public ClickedState getClickedState() {
		return clicked;
	}

	public void setClicked(ClickedState newClickedState) {
		this.clicked = newClickedState;
	}

	public void setTooltip(List<String> tooltip) {
		this.tooltip = tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = Arrays.asList(tooltip.split("="));
	}

	public void setClickSoundPitch(float newSoundPitch) {
		this.clickSoundPitch = newSoundPitch;
	}

	public float getClickSoundPitch() {
		return clickSoundPitch;
	}
}
