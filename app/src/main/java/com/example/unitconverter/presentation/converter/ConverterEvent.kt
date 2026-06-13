package com.example.unitconverter.presentation.converter

import com.example.unitconverter.domain.model.LengthUnit

/**
 * UI EVENTS (user intents)
 * ------------------------
 * A sealed interface listing every action the user can perform on the converter screen.
 *
 * Instead of the UI calling many different ViewModel functions, the UI sends ONE of these
 * "events" through a single `onEvent(...)` door. This pattern is often called MVI
 * (Model-View-Intent). Benefits for you as a learner:
 *  - You can read this file and instantly know EVERY way the user can interact with the
 *    screen. It's a table of contents for the screen's behaviour.
 *  - The UI stays "dumb": it just reports what happened ("user typed X") and never decides
 *    what to do about it. The ViewModel owns all the decisions.
 *
 * `sealed` means the set of events is closed/known at compile time, so a `when` over them
 * can be exhaustive (the compiler forces you to handle every case).
 */
sealed interface ConverterEvent {

    /** The user edited the number in the text field. */
    data class InputValueChanged(val value: String) : ConverterEvent

    /** The user picked a different "from" unit in the first dropdown. */
    data class InputUnitChanged(val unit: LengthUnit) : ConverterEvent

    /** The user picked a different "to" unit in the second dropdown. */
    data class OutputUnitChanged(val unit: LengthUnit) : ConverterEvent

    /** The user tapped the swap button to flip the "from" and "to" units. */
    data object SwapUnits : ConverterEvent
}
