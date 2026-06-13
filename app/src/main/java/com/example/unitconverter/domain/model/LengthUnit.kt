package com.example.unitconverter.domain.model

/**
 * DOMAIN MODEL
 * ------------
 * This enum is the "single source of truth" for every length unit the app supports.
 *
 * In your original code the unit names ("CM", "ME"...) and their conversion factors
 * (0.01, 1.0...) were scattered across many DropdownMenuItem onClick blocks. That meant
 * the same magic numbers were repeated for the input dropdown AND the output dropdown.
 *
 * By describing each unit ONCE here, every other layer (UseCase, ViewModel, UI) just
 * refers to this enum. If you ever add "KM" or "INCH", you add it in this one place and
 * it automatically shows up everywhere.
 *
 * @param label       The short text shown to the user (e.g. "CM").
 * @param meterFactor How many meters 1 of this unit equals. This is the trick that lets
 *                    us convert between ANY two units: convert input -> meters -> output.
 */
enum class LengthUnit(
    val label: String,
    val meterFactor: Double,
) {
    MILLIMETER(label = "MM", meterFactor = 0.001),
    CENTIMETER(label = "CM", meterFactor = 0.01),
    METER(label = "ME", meterFactor = 1.0),
    FEET(label = "FE", meterFactor = 0.3048),
}
