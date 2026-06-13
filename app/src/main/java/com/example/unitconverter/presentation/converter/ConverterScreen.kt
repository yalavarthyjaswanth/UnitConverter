package com.example.unitconverter.presentation.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.R
import com.example.unitconverter.presentation.converter.components.ResultCard
import com.example.unitconverter.presentation.converter.components.SwapUnitsButton
import com.example.unitconverter.presentation.converter.components.UnitSelector
import com.example.unitconverter.presentation.converter.components.ValueInputField
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * STATEFUL SCREEN ("the connector")
 * ---------------------------------
 * This is the ONLY composable that knows about the ViewModel. Its whole job is to:
 *   1. Get the ViewModel.
 *   2. Observe its state.
 *   3. Hand the state + an event callback to the stateless [ConverterContent] below.
 *
 * Keeping this layer tiny means the actual UI ([ConverterContent]) stays free of the
 * ViewModel, so it can be previewed and tested with hand-made state.
 *
 * `collectAsStateWithLifecycle()` turns the ViewModel's StateFlow into Compose State and
 * stops listening when the screen isn't visible (battery/lifecycle friendly — better than
 * the older `collectAsState()`).
 */
@Composable
fun ConverterScreen(
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConverterContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,   // pass the single event door straight through
        modifier = modifier,
    )
}

/**
 * STATELESS SCREEN CONTENT ("the picture")
 * ----------------------------------------
 * This draws the whole screen purely from the [uiState] it's given, and reports user actions
 * through [onEvent]. It has NO ViewModel reference, so the @Preview at the bottom can render
 * it instantly with fake state. This is the heart of the "state down, events up" idea.
 *
 * @param uiState The current screen state to render.
 * @param onEvent How to report user actions back upwards.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    uiState: ConverterUiState,
    onEvent: (ConverterEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.unit_converter)) })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // 1) The number the user types. State comes from uiState; edits go up as an event.
            ValueInputField(
                value = uiState.inputValue,
                onValueChange = { newText ->
                    onEvent(ConverterEvent.InputValueChanged(newText))
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2) The two unit selectors with a swap button in the middle.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                UnitSelector(
                    label = stringResource(R.string.from_label),
                    selectedUnit = uiState.inputUnit,
                    onUnitSelected = { unit -> onEvent(ConverterEvent.InputUnitChanged(unit)) },
                )

                SwapUnitsButton(onSwap = { onEvent(ConverterEvent.SwapUnits) })

                UnitSelector(
                    label = stringResource(R.string.to_label),
                    selectedUnit = uiState.outputUnit,
                    onUnitSelected = { unit -> onEvent(ConverterEvent.OutputUnitChanged(unit)) },
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3) The result card.
            ResultCard(
                result = uiState.result,
                outputUnit = uiState.outputUnit,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConverterContentPreview() {
    UnitConverterTheme {
        // We can preview the entire screen with completely fake state — no ViewModel needed.
        ConverterContent(
            uiState = ConverterUiState(
                inputValue = "100",
                result = "1.0",
            ),
            onEvent = {},
        )
    }
}
