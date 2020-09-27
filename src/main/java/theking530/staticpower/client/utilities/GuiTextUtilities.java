package theking530.staticpower.client.utilities;

import java.text.NumberFormat;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import theking530.staticpower.utilities.MetricConverter;

/**
 * Utility class to format various values into display in the UI including
 * energy and fluid.
 * 
 * @author Amine Sebastian
 *
 */
public class GuiTextUtilities {
	/** Translation text component for Static Volts (SV). */
	public static final TranslationTextComponent ENERGY_UNIT_TRANSLATION = new TranslationTextComponent("gui.staticpower.energy_unit");
	/** Translation text component for Static Volts Per Tick (SV/t). */
	public static final TranslationTextComponent ENERGY_RATE_TRANSLATION = new TranslationTextComponent("gui.staticpower.energy_unit_per_tick");

	/** Translation text component for millibuckets (mB). */
	public static final TranslationTextComponent FLUID_UNIT_TRANSLATION = new TranslationTextComponent("gui.staticpower.fluid_unit");
	/** Translation text component for millibuckets Per Tick (mB/t). */
	public static final TranslationTextComponent FLUID_RATE_TRANSLATION = new TranslationTextComponent("gui.staticpower.fluid_unit_per_tick");

	/** Translation text component for heat (H). */
	public static final TranslationTextComponent HEAT_UNIT_TRANSLATION = new TranslationTextComponent("gui.staticpower.heat_unit");
	/** Translation text component for Heat Per Tick (H/t). */
	public static final TranslationTextComponent HEAT_RATE_TRANSLATION = new TranslationTextComponent("gui.staticpower.heat_unit_per_tick");
	/** Translation text component for Conductivity (σ). */
	public static final TranslationTextComponent HEAT_CONDUCTIVITY_TRANSLATION = new TranslationTextComponent("gui.staticpower.heat_conductivity");

	/** Single instance of number formatter. */
	private static final NumberFormat NUMBER_FORMATTER;

	/**
	 * Static initializer for number formatter.
	 */
	static {
		NUMBER_FORMATTER = NumberFormat.getInstance();
		NUMBER_FORMATTER.setGroupingUsed(true);
		NUMBER_FORMATTER.setMaximumFractionDigits(2);
	}

	/**
	 * Formats the provided energy into a string for display in the UI. Example,
	 * energy 50000 turns into 50kSV. Uses localization.
	 * 
	 * @param energy The amount of energy to format.
	 * @return The formatted string.
	 */
	public static IFormattableTextComponent formatEnergyToString(int energy, boolean includeUnits, boolean includeMetricUnit) {
		MetricConverter metricEnergy = new MetricConverter(energy);
		IFormattableTextComponent output = new StringTextComponent(NUMBER_FORMATTER.format(metricEnergy.getValue()));

		if (includeMetricUnit) {
			output.appendString(metricEnergy.getSuffix());
		}

		if (includeUnits) {
			output.append(ENERGY_UNIT_TRANSLATION);
		}
		return output;
	}

	public static IFormattableTextComponent formatEnergyToString(int energy, boolean includeUnits) {
		return formatEnergyToString(energy, includeUnits, true);
	}

	public static IFormattableTextComponent formatEnergyToString(int energy) {
		return formatEnergyToString(energy, true, true);
	}

	/**
	 * Formats the provided energy and capacity into a string for display in the UI.
	 * Example, energy 50000 and storage 100000 turns into 50,000/100,000 SV. Uses
	 * localization.
	 * 
	 * @param energy   The amount of energy to format as the numerator.
	 * @param capacity The maximum amount of energy to use as the denominator.
	 * @return The formatted string.
	 */
	public static IFormattableTextComponent formatEnergyToString(int energy, int capacity) {
		return formatEnergyToString(energy, false, true).appendString("/").append(formatEnergyToString(capacity));

	}

	/**
	 * Formats the provided energyRate into a string for display in the UI. Example,
	 * energyRate 1000 turns into 1kSV/t. Uses localization.
	 * 
	 * @param energyRate The energy rate to format.
	 * @return The formatted string.
	 */
	public static IFormattableTextComponent formatEnergyRateToString(float energyRate) {
		MetricConverter metricRate = new MetricConverter(energyRate);
		return new StringTextComponent(NUMBER_FORMATTER.format(metricRate.getValue())).appendString(metricRate.getSuffix()).append(ENERGY_RATE_TRANSLATION);
	}

	public static IFormattableTextComponent formatHeatToString(float currentHeat, float capacity) {
		return formatHeatToString(currentHeat, false, true).appendString("/").append(formatHeatToString(capacity));

	}

	public static IFormattableTextComponent formatHeatToString(float heat) {
		return formatHeatToString(heat, true, true);
	}

	public static IFormattableTextComponent formatHeatToString(float heat, boolean includeUnits, boolean includeMetricUnit) {
		MetricConverter metricEnergy = new MetricConverter(heat);
		IFormattableTextComponent output = new StringTextComponent(NUMBER_FORMATTER.format(metricEnergy.getValue()));

		if (includeMetricUnit) {
			output.appendString(metricEnergy.getSuffix());
		}

		if (includeUnits) {
			output.appendString(" ").append(HEAT_UNIT_TRANSLATION);
		}
		return output;
	}

	public static IFormattableTextComponent formatHeatRateToString(float heatTransferRate) {
		MetricConverter metricRate = new MetricConverter(heatTransferRate);
		return new StringTextComponent(NUMBER_FORMATTER.format(metricRate.getValue())).appendString(" ").appendString(metricRate.getSuffix()).append(HEAT_RATE_TRANSLATION);
	}

	public static IFormattableTextComponent formatConductivityToString(float conductivity) {
		MetricConverter metricRate = new MetricConverter(conductivity);
		return new StringTextComponent(NUMBER_FORMATTER.format(metricRate.getValue())).appendString(metricRate.getSuffix()).append(HEAT_CONDUCTIVITY_TRANSLATION);
	}

	public static IFormattableTextComponent formatFluidToString(float currentFluid, float capacity) {
		return formatFluidToString(currentFluid, false, true).appendString("/").append(formatFluidToString(capacity));

	}

	public static IFormattableTextComponent formatFluidToString(float fluidAmount) {
		return formatFluidToString(fluidAmount, true, true);
	}

	public static IFormattableTextComponent formatFluidToString(float fluid, boolean includeUnits, boolean includeMetricUnit) {
		MetricConverter metricEnergy = new MetricConverter(fluid, -1);
		IFormattableTextComponent output = new StringTextComponent(NUMBER_FORMATTER.format(metricEnergy.getValue()));

		if (includeMetricUnit) {
			output.appendString(metricEnergy.getSuffix());
		}

		if (includeUnits) {
			output.appendString(" ").append(FLUID_UNIT_TRANSLATION);
		}
		return output;
	}

	public static IFormattableTextComponent formatFluidRateToString(float fluidRate) {
		MetricConverter metricRate = new MetricConverter(fluidRate, -1);
		return new StringTextComponent(NUMBER_FORMATTER.format(metricRate.getValue())).appendString(" ").appendString(metricRate.getSuffix()).append(FLUID_RATE_TRANSLATION);
	}

	public static IFormattableTextComponent formatNumberAsString(float number) {
		return new StringTextComponent(NUMBER_FORMATTER.format(number));
	}

	public static IFormattableTextComponent formatNumberAsString(int number) {
		return new StringTextComponent(NUMBER_FORMATTER.format(number));
	}
}
