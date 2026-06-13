package com.example.unitconverter.presentation.converter

import androidx.lifecycle.ViewModel
import com.example.unitconverter.domain.model.LengthUnit
import com.example.unitconverter.domain.usecase.ConvertLengthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * VIEW MODEL
 * ----------
 * The ViewModel is the "brain" that sits between the UI (Composables) and the business
 * logic (UseCase). Its jobs:
 *   1. HOLD the screen state (ConverterUiState) and expose it to the UI.
 *   2. RECEIVE user events (ConverterEvent) and decide how the state should change.
 *   3. SURVIVE configuration changes (e.g. screen rotation). A ViewModel outlives the
 *      Activity recreation, so the user doesn't lose their typed value when they rotate.
 *
 * Why this is better than the original `remember { mutableStateOf() }` approach:
 *   - State now lives OUTSIDE the Composable, so it survives rotation.
 *   - All decisions live in ONE place (here), not spread across onClick lambdas.
 *   - The UI becomes a thin "renderer" of state — much easier to read and test.
 *
 * @param convertLength The business-logic UseCase. We pass it in (dependency injection)
 *                      with a default value, which means tests can swap in a fake, while
 *                      normal app code doesn't have to provide anything.
 */
class ConverterViewModel(
    private val convertLength: ConvertLengthUseCase = ConvertLengthUseCase(),
) : ViewModel() {

    /**
     * The PRIVATE, mutable state. Only the ViewModel is allowed to change it.
     * `MutableStateFlow` is a holder that emits a new value every time state changes.
     */
    private val _uiState = MutableStateFlow(ConverterUiState())

    /**
     * The PUBLIC, read-only state the UI observes. `.asStateFlow()` hides the "mutable" part
     * so the UI can read state but can never secretly modify it — it must go through events.
     */
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    /**
     * THE SINGLE ENTRY POINT for everything the user does.
     * The UI calls `onEvent(SomeEvent)` and this `when` decides what to do.
     * Because ConverterEvent is `sealed`, the compiler guarantees we handle every case.
     */
    fun onEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.InputValueChanged -> updateInputValue(event.value)
            is ConverterEvent.InputUnitChanged -> updateInputUnit(event.unit)
            is ConverterEvent.OutputUnitChanged -> updateOutputUnit(event.unit)
            ConverterEvent.SwapUnits -> swapUnits()
        }
    }

    private fun updateInputValue(newValue: String) {
        // Update the typed text, then recompute the result from the new state.
        _uiState.update { state -> state.copy(inputValue = newValue) }
        recalculate()
    }

    private fun updateInputUnit(unit: LengthUnit) {
        _uiState.update { state -> state.copy(inputUnit = unit) }
        recalculate()
    }

    private fun updateOutputUnit(unit: LengthUnit) {
        _uiState.update { state -> state.copy(outputUnit = unit) }
        recalculate()
    }

    private fun swapUnits() {
        // A small "quality of life" feature: flip the two units around.
        _uiState.update { state ->
            state.copy(inputUnit = state.outputUnit, outputUnit = state.inputUnit)
        }
        recalculate()
    }

    /**
     * Recomputes [ConverterUiState.result] from the current input value and units.
     * This is the only place that talks to the UseCase, so the conversion rule has a single
     * well-defined trigger.
     */
    private fun recalculate() {
        val state = _uiState.value

        // The text field can hold non-numeric or empty text while the user types.
        val number = state.inputValue.toDoubleOrNull()

        val resultText = if (number == null) {
            // Nothing valid to convert yet — clear the result.
            ""
        } else {
            val converted = convertLength(
                value = number,
                from = state.inputUnit,
                to = state.outputUnit,
            )
            converted.toString()
        }

        _uiState.update { it.copy(result = resultText) }
    }
}
