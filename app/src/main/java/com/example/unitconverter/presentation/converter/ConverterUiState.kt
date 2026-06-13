package com.example.unitconverter.presentation.converter

import com.example.unitconverter.domain.model.LengthUnit

/**
 * UI STATE
 * --------
 * This single immutable data class describes EVERYTHING the converter screen needs to draw
 * itself at any given moment. "If you handed this object to a fresh screen, it could redraw
 * the exact same picture."
 *
 * In your original code the screen state was 8 separate `remember { mutableStateOf(...) }`
 * variables living inside the Composable (inputValue, outputValue, inputUnit, outputUnit,
 * iExpanded, oExpanded, two conversion factors). Problems with that:
 *  - The values were lost on screen rotation / process death (remember is tied to the UI).
 *  - The logic and the data were tangled together inside the Composable.
 *  - It was hard to see, at a glance, what the "state of the screen" actually was.
 *
 * Collecting them into ONE data class (held by the ViewModel) fixes all of that.
 *
 * Note: We DON'T store the conversion factors here anymore — the LengthUnit enum already
 * knows them. And we DON'T store the dropdown "expanded" booleans here: whether a menu is
 * open is throw-away UI detail, so we let the small UI component remember that locally.
 *
 * @param inputValue The raw text the user typed (kept as String so the text field can show
 *                   partial input like "" or "12." while typing).
 * @param inputUnit  The unit the user is converting FROM.
 * @param outputUnit The unit the user is converting TO.
 * @param result     The formatted result text to display.
 */
data class ConverterUiState(
    val inputValue: String = "",
    val inputUnit: LengthUnit = LengthUnit.METER,
    val outputUnit: LengthUnit = LengthUnit.CENTIMETER,
    val result: String = "",
)
