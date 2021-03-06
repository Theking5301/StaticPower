package theking530.staticcore.gui.widgets;

import java.text.DecimalFormat;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import theking530.staticcore.gui.GuiDrawUtilities;
import theking530.staticcore.gui.drawables.SpriteDrawable;
import theking530.staticcore.utilities.Vector2D;
import theking530.staticpower.client.StaticPowerSprites;
import theking530.staticpower.tileentities.components.control.MachineProcessingComponent;

public class ProcessingComponentStateWidget extends AbstractGuiWidget {
	private final SpriteDrawable validDrawable;
	private final SpriteDrawable errorDrawable;
	private final MachineProcessingComponent machineProcessingComponent;

	public ProcessingComponentStateWidget(MachineProcessingComponent machineProcessingComponent, float xPosition, float yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		errorDrawable = new SpriteDrawable(StaticPowerSprites.ERROR, width, height);
		validDrawable = new SpriteDrawable(StaticPowerSprites.GREEN_CHECK, width, height);
		this.machineProcessingComponent = machineProcessingComponent;
	}

	public ProcessingComponentStateWidget(MachineProcessingComponent machineProcessingComponent, float xPosition, float yPosition) {
		this(machineProcessingComponent, xPosition, yPosition, 16, 16);
	}

	@Override
	public void renderBehindItems(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		Vector2D screenSpacePosition = GuiDrawUtilities.translatePositionByMatrix(matrix, getPosition());
		if (machineProcessingComponent.isProcessingStoppedDueToError()) {
			errorDrawable.draw(screenSpacePosition.getX(), screenSpacePosition.getY());
		} else {
			validDrawable.draw(screenSpacePosition.getX(), screenSpacePosition.getY());
		}
	}

	@Override
	public void getTooltips(Vector2D mousePosition, List<ITextComponent> tooltips, boolean showAdvanced) {
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		if (machineProcessingComponent.isProcessingStoppedDueToError()) {
			String[] splitTooltips = machineProcessingComponent.getProcessingErrorMessage().split("\\$");
			for (String tip : splitTooltips) {
				tooltips.add(new StringTextComponent(tip));
			}
		} else if (machineProcessingComponent.getCurrentProcessingTime() > 0) {
			String remainingTime = decimalFormat
					.format((machineProcessingComponent.getMaxProcessingTime() - machineProcessingComponent.getCurrentProcessingTime()) / (machineProcessingComponent.getTimeUnitsPerTick() * 20.0f));
			tooltips.add(
					new TranslationTextComponent("gui.staticpower.remaining").appendString(": ").appendString(remainingTime).append(new TranslationTextComponent("gui.staticpower.seconds.short")));
		} else {
			String maxTime = decimalFormat.format(machineProcessingComponent.getMaxProcessingTime() / (machineProcessingComponent.getTimeUnitsPerTick() * 20.0f));
			tooltips.add(new TranslationTextComponent("gui.staticpower.max").appendString(": ").appendString(maxTime).append(new TranslationTextComponent("gui.staticpower.seconds.short")));
		}
	}
}
