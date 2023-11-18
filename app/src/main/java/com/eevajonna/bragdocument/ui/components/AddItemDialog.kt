package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.DateFormatSymbols
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(onDismissRequest: () -> Unit, onAddItem: (text: String, date: LocalDate) -> Unit) {
    val months = DateFormatSymbols().shortMonths.toList()
    val years = (2010..LocalDate.now().year).sortedDescending().toList()

    var text by remember {
        mutableStateOf("")
    }
    var year by remember {
        mutableIntStateOf(LocalDate.now().year)
    }
    var month by remember {
        mutableStateOf(months[LocalDate.now().monthValue - 1])
    }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                            Text("Add new item")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onDismissRequest() }) {
                            Icon(
                                Icons.Filled.Close,
                                "Close",
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            enabled = text.isNotEmpty(),
                            onClick = {
                                val date = LocalDate.of(year, months.indexOf(month) + 1, 1)
                                onAddItem(text, date)
                            },
                        ) {
                            Text("Save")
                        }
                    }
                )
            },
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(AddItemDialog.valuePickerOuterPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(AddItemDialog.valuePickerInnerPadding)
            ) {


                ValuePicker(
                    months = months,
                    selectedMonth = month,
                    setSelectedMonth = { month = it },
                    years = years,
                    selectedYear = year,
                    setSelectedYear = { year = it },
                )

                OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = { Text("The cool thing I did") }, value = text, onValueChange = { text = it })
            }
        }
    }
}

object AddItemDialog {
    val valuePickerOuterPadding = 16.dp
    val valuePickerInnerPadding = 8.dp
}