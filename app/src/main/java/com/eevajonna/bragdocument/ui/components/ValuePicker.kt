package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R

@Composable
fun ValuePicker(
    months: List<String>,
    years: List<Int>,
    selectedMonth: String,
    setSelectedMonth: (String) -> Unit,
    selectedYear: Int,
    setSelectedYear: (Int) -> Unit,
) {
    var monthsOpen by remember { mutableStateOf(false) }

    var yearsOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(vertical = ValuePicker.outerPadding)
            .clip(ValuePicker.shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(ValuePicker.contentPadding),
    ) {
        Text(stringResource(R.string.month_and_year), style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth()) {
            Box {
                TextButton(onClick = { monthsOpen = true }) {
                    Text(selectedMonth)
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Dropdown(
                    values = months,
                    expanded = monthsOpen,
                    onClose = { monthsOpen = it },
                    selectedValue = selectedMonth,
                    setSelectedValue = setSelectedMonth,
                )
            }
            Box {
                TextButton(onClick = { yearsOpen = true }) {
                    Text("$selectedYear")
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Dropdown(
                    values = years.map { it.toString() },
                    expanded = yearsOpen,
                    onClose = { yearsOpen = it },
                    selectedValue = selectedYear.toString(),
                    setSelectedValue = {
                        setSelectedYear(it.toInt())
                    },
                )
            }
        }
    }
}

@Composable
fun Dropdown(values: List<String>, expanded: Boolean, onClose: (Boolean) -> Unit, selectedValue: String, setSelectedValue: (String) -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onClose(false) },
    ) {
        values.map { value ->
            DropdownMenuItem(
                text = { Text(value) },
                onClick = { setSelectedValue(value) },
                leadingIcon = {
                    if (value == selectedValue) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    }
}

object ValuePicker {
    val outerPadding = 8.dp
    val contentPadding = 8.dp
    val shape = RoundedCornerShape(12.dp)
}
