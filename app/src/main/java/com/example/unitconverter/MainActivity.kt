package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.unitconverter.presentation.converter.ConverterScreen
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * APP ENTRY POINT
 * ---------------
 * Notice how tiny this file is now compared to the original ~240 lines!
 *
 * The Activity's only responsibilities are:
 *   1. Apply the app theme.
 *   2. Show the top-level screen ([ConverterScreen]).
 *
 * Everything else — the layout, the state, the conversion logic — now lives in its own
 * focused file. This is the payoff of separating concerns: each file does ONE job, so you
 * always know where to look.
 *
 * Where things live now:
 *   - domain/model/LengthUnit.kt ............. the units + their conversion factors
 *   - domain/usecase/ConvertLengthUseCase.kt . the pure "do the math" logic
 *   - presentation/converter/ConverterUiState. the data the screen needs to draw itself
 *   - presentation/converter/ConverterEvent .. every action the user can take
 *   - presentation/converter/ConverterViewModel the brain: holds state, reacts to events
 *   - presentation/converter/ConverterScreen . wires the ViewModel to the UI
 *   - presentation/converter/components/ ..... small, reusable, previewable UI pieces
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                ConverterScreen()
            }
        }
    }
}
