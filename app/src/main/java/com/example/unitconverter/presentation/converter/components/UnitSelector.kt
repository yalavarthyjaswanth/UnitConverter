package com.example.unitconverter.presentation.converter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.R
import com.example.unitconverter.domain.model.LengthUnit
import com.example.unitconverter.ui.theme.UnitConverterTheme

/**
 * REUSABLE UI COMPONENT — a labelled dropdown for choosing a [LengthUnit].
 *
 * In your original file there were FOUR nearly identical DropdownMenuItem blocks for the
 * input dropdown, and FOUR more for the output dropdown — eight copies in total, each
 * repeating the unit name and its conversion factor. If you added a new unit you'd have to
 * edit it in two places and not forget either.
 *
 * Here we generate the menu items by simply looping over `LengthUnit.entries` (every value
 * of the enum). Add a unit to the enum once and BOTH dropdowns update automatically.
 *
 * About the `expanded` boolean:
 *   Whether the menu is open is pure throw-away UI detail (it doesn't matter after rotation),
 *   so we keep it LOCAL with `remember` here instead of putting it in the ViewModel's state.
 *   This is the right call: hoist the state that matters, keep trivial UI state local.
 *
 * @param label          Small caption above the button, e.g. "From" / "To".
 * @param selectedUnit   The unit currently chosen.
 * @param onUnitSelected Called when the user picks a unit from the menu.
 * @param modifier       Standard Compose modifier.
 */
@Composable
fun UnitSelector(
    label: String,
    selectedUnit: LengthUnit,
    onUnitSelected: (LengthUnit) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Local UI-only state: is the dropdown menu currently showing?
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text = label)

        // The button that opens the menu and shows the current selection.
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedUnit.label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.select_unit),
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            // Build one menu item per unit, automatically, from the enum.
            LengthUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(text = unit.label) },
                    onClick = {
                        expanded = false          // close the menu
                        onUnitSelected(unit)      // report the choice upwards
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UnitSelectorPreview() {
    UnitConverterTheme {
        UnitSelector(
            label = "From",
            selectedUnit = LengthUnit.METER,
            onUnitSelected = {},
        )
    }
}
