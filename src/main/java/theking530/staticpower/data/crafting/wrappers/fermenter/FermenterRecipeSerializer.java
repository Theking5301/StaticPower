package theking530.staticpower.data.crafting.wrappers.fermenter;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import theking530.staticpower.StaticPower;
import theking530.staticpower.data.crafting.StaticPowerIngredient;
import theking530.staticpower.data.crafting.StaticPowerJsonParsingUtilities;

public class FermenterRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FermenterRecipe> {
	public static final FermenterRecipeSerializer INSTANCE = new FermenterRecipeSerializer();

	private FermenterRecipeSerializer() {
		this.setRegistryName(new ResourceLocation(StaticPower.MOD_ID, "fermenter_recipe"));
	}

	@Override
	public FermenterRecipe read(ResourceLocation recipeId, JsonObject json) {
		// Capture the input ingredient.
		JsonObject inputElement = JSONUtils.getJsonObject(json, "input");
		StaticPowerIngredient input = StaticPowerIngredient.deserialize(inputElement);

		// Get the fluid output.
		JsonObject outputElement = JSONUtils.getJsonObject(json, "output");
		FluidStack fluidOutput = StaticPowerJsonParsingUtilities.parseFluidStack(outputElement);

		// Create the recipe.
		return new FermenterRecipe(recipeId, input, fluidOutput);
	}

	@Override
	public FermenterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		StaticPowerIngredient input = StaticPowerIngredient.read(buffer);
		FluidStack output = buffer.readFluidStack();

		// Create the recipe.
		return new FermenterRecipe(recipeId, input, output);
	}

	@Override
	public void write(PacketBuffer buffer, FermenterRecipe recipe) {
		recipe.getInputIngredient().write(buffer);
		buffer.writeFluidStack(recipe.getOutputFluidStack());
	}
}
