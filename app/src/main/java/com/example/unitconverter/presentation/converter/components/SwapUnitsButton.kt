package com.example.unitconverter.presentation.converter.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.R
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * REUSABLE UI COMPONENT — a small icon button that swaps the "from" and "to" units.
 *
 * This is a brand-new feature (your original app didn't have it). It's a great example of
 * how clean the new structure is: the button just reports "the user wants to swap" via
 * [onSwap]; it has no idea HOW the swap happens. The ViewModel handles the actual logic.
 *
 * @param onSwap   Called when the user taps the button.
 * @param modifier Standard Compose modifier.
 */
@Composable
fun SwapUnitsButton(
    onSwap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalIconButton(onClick = onSwap, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.SwapHoriz,
            contentDescription = stringResource(R.string.swap_units),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SwapUnitsButtonPreview() {
    UnitConverterTheme {
        SwapUnitsButton(onSwap = {})
    }
}
