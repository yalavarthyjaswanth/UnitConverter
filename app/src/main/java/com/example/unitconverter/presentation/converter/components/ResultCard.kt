package com.example.unitconverter.presentation.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.R
import com.example.unitconverter.domain.model.LengthUnit
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * REUSABLE UI COMPONENT — the card that shows the conversion result.
 *
 * It's purely presentational: give it a [result] string and the [outputUnit], and it draws
 * a nice card. If [result] is blank (user hasn't typed a valid number yet) it shows a
 * friendly placeholder instead of an empty box.
 *
 * @param result     The converted number as text (may be empty).
 * @param outputUnit The unit the result is expressed in (so we can show its label, e.g. "CM").
 * @param modifier   Standard Compose modifier.
 */
@Composable
fun ResultCard(
    result: String,
    outputUnit: LengthUnit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.result_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )

            val hasResult = result.isNotBlank()
            Text(
                text = if (hasResult) "$result ${outputUnit.label}" else stringResource(R.string.result_placeholder),
                style = if (hasResult) {
                    MaterialTheme.typography.headlineMedium
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultCardPreview() {
    UnitConverterTheme {
        ResultCard(result = "100.0", outputUnit = LengthUnit.CENTIMETER)
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultCardEmptyPreview() {
    UnitConverterTheme {
        ResultCard(result = "", outputUnit = LengthUnit.CENTIMETER)
    }
}
