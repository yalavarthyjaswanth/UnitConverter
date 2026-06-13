package com.example.unitconverter.presentation.converter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.R
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * REUSABLE UI COMPONENT — the number input field.
 *
 * Notice this is a "STATELESS" / "dumb" component:
 *   - It does NOT remember anything itself.
 *   - It receives the current text via [value] (state flows DOWN from the ViewModel).
 *   - It reports edits via [onValueChange] (events flow UP to the ViewModel).
 *
 * This "state down, events up" pattern is called STATE HOISTING. It makes the component
 * reusable and trivial to preview/test, because it has no hidden internal behaviour.
 *
 * @param value         The text currently shown in the field.
 * @param onValueChange Called with the new text whenever the user types.
 * @param modifier      Standard Compose modifier so callers can size/position it.
 */
@Composable
fun ValueInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = stringResource(R.string.enter_your_number)) },
        singleLine = true,
        // Show the numeric keyboard since we only ever convert numbers.
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    )
}

@Preview(showBackground = true)
@Composable
private fun ValueInputFieldPreview() {
    UnitConverterTheme {
        ValueInputField(value = "12.5", onValueChange = {})
    }
}
