package theking530.staticpower.integration.JEI.squeezer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import theking530.staticcore.gui.GuiDrawUtilities;
import theking530.staticcore.gui.widgets.valuebars.GuiFluidBarUtilities;
import theking530.staticcore.gui.widgets.valuebars.GuiPowerBarUtilities;
import theking530.staticcore.utilities.Vector2D;
import theking530.staticpower.StaticPower;
import theking530.staticpower.client.utilities.GuiTextUtilities;
import theking530.staticpower.data.crafting.wrappers.squeezer.SqueezerRecipe;
import theking530.staticpower.init.ModBlocks;
import theking530.staticpower.integration.JEI.BaseJEIRecipeCategory;
import theking530.staticpower.tileentities.components.control.sideconfiguration.MachineSideMode;

public class SqueezerRecipeCategory extends BaseJEIRecipeCategory<SqueezerRecipe> {
	public static final ResourceLocation SQUEEZER_UID = new ResourceLocation(StaticPower.MOD_ID, "squeezer");
	private static final int INTPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;

	private final TranslationTextComponent locTitle;
	private final IDrawable background;
	private final IDrawable icon;

	private ITickTimer powerTimer;
	private ITickTimer processingTimer;

	public SqueezerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		locTitle = new TranslationTextComponent(ModBlocks.Squeezer.getTranslationKey());
		background = guiHelper.createBlankDrawable(146, 60);
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.Squeezer));
	}

	@Override
	@Nonnull
	public ResourceLocation getUid() {
		return SQUEEZER_UID;
	}

	@Override
	@Nonnull
	public String getTitle() {
		return locTitle.getString();
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public Class<? extends SqueezerRecipe> getRecipeClass() {
		return SqueezerRecipe.class;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void draw(SqueezerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		Vector2D origin = getGuiOrigin(matrixStack);
		GuiDrawUtilities.drawSlot(origin.getX() + 50, origin.getY() + 13, 16, 16);
		GuiDrawUtilities.drawSlot(origin.getX() + 75, origin.getY() + 32, 20, 20);

		// This doesn't actually draw the fluid, just the bars.
		GuiFluidBarUtilities.drawFluidBar(recipe.getOutputFluid(), 0, 0, origin.getX() + 106, origin.getY() + 56, 1.0f, 16, 52, MachineSideMode.Never, true);
		GuiPowerBarUtilities.drawPowerBar(origin.getX() + 8, origin.getY() + 54, 16, 48, 1.0f, powerTimer.getValue(), powerTimer.getMaxValue());

		// Draw the progress bar as a fluid.
		GuiDrawUtilities.drawSlot(origin.getX() + 72, origin.getY() + 18, 28, 5);
		float progress = ((float) processingTimer.getValue() / processingTimer.getMaxValue()) * 28;
		FluidStack fluid = recipe.getOutputFluid();
		GuiFluidBarUtilities.drawFluidBar(fluid, 1000, 1000, origin.getX() + 72, origin.getY() + 23, 1, progress, 5, false);
	}

	@Override
	public List<ITextComponent> getTooltipStrings(SqueezerRecipe recipe, double mouseX, double mouseY) {
		List<ITextComponent> output = new ArrayList<ITextComponent>();
		if (mouseX > 8 && mouseX < 24 && mouseY < 54 && mouseY > 4) {
			output.add(new StringTextComponent("Usage: ").append(GuiTextUtilities.formatEnergyToString(recipe.getPowerCost() * recipe.getProcessingTime())));
		}

		return output;
	}

	@Override
	public void setIngredients(SqueezerRecipe recipe, IIngredients ingredients) {
		// Add the inputs.
		List<Ingredient> input = new ArrayList<Ingredient>();
		input.add(recipe.getInput().getIngredient());
		ingredients.setInputIngredients(input);

		List<ItemStack> outputs = new ArrayList<ItemStack>();
		// Add the output item if one exists.
		if (recipe.hasItemOutput()) {
			outputs.add(recipe.getOutput().getItem());
		}

		// Add the output fluid if one exists.
		if (recipe.hasOutputFluid()) {
			ingredients.setOutput(VanillaTypes.FLUID, recipe.getOutputFluid());
		}

		// Set the output item stacks.
		ingredients.setOutputs(VanillaTypes.ITEM, outputs);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SqueezerRecipe recipe, IIngredients ingredients) {
		// Add the input.
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(INTPUT_SLOT, true, 49, 11);

		// Add the output item if it exists.
		if (recipe.hasItemOutput()) {
			guiItemStacks.init(OUTPUT_SLOT, false, 76, 33);
		}

		// Set the items.
		guiItemStacks.set(ingredients);

		// Add the fluid.
		if (recipe.hasOutputFluid()) {
			IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
			fluids.init(3, false, 106, 4, 16, 52, getFluidTankDisplaySize(recipe.getOutputFluid()), false, null);
			fluids.set(ingredients);
		}

		powerTimer = guiHelper.createTickTimer(recipe.getProcessingTime(), recipe.getProcessingTime() * recipe.getPowerCost(), true);
		processingTimer = guiHelper.createTickTimer(recipe.getProcessingTime(), recipe.getProcessingTime(), false);
	}
}
