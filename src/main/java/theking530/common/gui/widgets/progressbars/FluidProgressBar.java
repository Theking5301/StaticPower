package theking530.common.gui.widgets.progressbars;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.fluids.FluidStack;
import theking530.common.gui.GuiDrawUtilities;
import theking530.common.utilities.Color;
import theking530.common.utilities.Vector2D;

public class FluidProgressBar extends AbstractProgressBar {
	private FluidStack displayFluidStack;

	public FluidProgressBar(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		displayFluidStack = FluidStack.EMPTY;
	}

	public void setFluidStack(FluidStack fluidStack) {
		this.displayFluidStack = fluidStack;
	}

	@Override
	public void renderBehindItems(int mouseX, int mouseY, float partialTicks) {
		super.renderBehindItems(mouseX, mouseY, partialTicks);

		// Get the screen space position.
		Vector2D screenSpacePosition = this.getScreenSpacePosition();

		// Draw the background.
		GuiDrawUtilities.drawSlot(screenSpacePosition.getX(), screenSpacePosition.getY(), getSize().getX(), getSize().getY());

		// Draw the fluid.
		if (!displayFluidStack.isEmpty()) {
			// Get the adjusted progress.
			float adjustedProgress = visualCurrentProgress / maxProgress;

			// Get the fluid icon.
			TextureAtlasSprite icon = GuiDrawUtilities.getStillFluidSprite(displayFluidStack);

			// If the icon is valid, draw the progress.
			if (icon != null) {
				// Get the fluid color.
				Color fluidColor = GuiDrawUtilities.getFluidColor(displayFluidStack);

				// Calculate the UV difference.
				float uvDiff = icon.getMaxU() - icon.getMinU();

				Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
				GuiDrawUtilities.drawTexturedModalRect(PlayerContainer.LOCATION_BLOCKS_TEXTURE, screenSpacePosition.getX(), screenSpacePosition.getY(), adjustedProgress * getSize().getX(),
						getSize().getY(), icon.getMinU(), icon.getMinV(), icon.getMinU() + (uvDiff * adjustedProgress), icon.getMaxV(), fluidColor);
			}

			// Draw the leading white line.
			GuiDrawUtilities.drawColoredRectangle(screenSpacePosition.getX() + (adjustedProgress * getSize().getX()), screenSpacePosition.getY(), 0.75f, getSize().getY(), 1.0f, Color.WHITE);
		}

		// Draw the error indicator if needed.
		if (isProcessingErrored) {
			getErrorDrawable().draw(screenSpacePosition.getX() + (getSize().getY() / 2.0f) + 4.0f, screenSpacePosition.getY() - (getSize().getY() / 2.0f) - 3.0f);
		}
	}
}
