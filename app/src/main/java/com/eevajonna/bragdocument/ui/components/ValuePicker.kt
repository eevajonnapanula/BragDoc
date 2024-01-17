package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(vertical = ValuePicker.outerPadding),
        verticalArrangement = Arrangement.spacedBy(ValuePicker.contentSpacing),
    ) {
        Text(stringResource(R.string.title_when), style = MaterialTheme.typography.titleLarge)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EditButton(
                text = selectedMonth,
            ) {
                monthsOpen = true
            }
            if (monthsOpen) {
                ValuePickerDialog(
                    title = stringResource(R.string.choose_month),
                    value = selectedMonth,
                    values = months,
                    onConfirm = {
                        setSelectedMonth(it)
                        monthsOpen = false
                    },
                ) {
                    monthsOpen = false
                }
            }

            EditButton(
                text = "$selectedYear",
            ) {
                yearsOpen = true
            }
            if (yearsOpen) {
                ValuePickerDialog(
                    title = stringResource(R.string.choose_year),
                    value = selectedYear.toString(),
                    values = years.map { it.toString() },
                    onConfirm = {
                        setSelectedYear(it.toInt())
                        yearsOpen = false
                    },
                ) {
                    yearsOpen = false
                }
            }
        }
    }
}

@Composable
fun EditButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, shape = OutlinedTextFieldDefaults.shape, contentPadding = PaddingValues(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text, style = MaterialTheme.typography.titleMedium)
            Icon(Icons.Filled.Edit, contentDescription = null)
        }
    }
}

@Composable
fun ValuePickerDialog(title: String, value: String, values: List<String>, onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var selectedValue by remember {
        mutableStateOf(value)
    }
    AlertDialog(
        title = {
            Text(text = title)
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(selectedValue)
            }) {
                Text(stringResource(id = R.string.button_choose))
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .selectableGroup()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(values) { value ->
                    ToggleableItem(
                        text = value,
                        selected = value == selectedValue,
                        onSelect = { selectedValue = value },
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text(stringResource(R.string.button_dismiss))
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

object ValuePicker {
    val outerPadding = 8.dp
    val contentSpacing = 12.dp
}
