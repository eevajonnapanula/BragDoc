package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateSummaryDialog(
    itemsToSelect: List<BragItem>,
    loading: Boolean,
    newSummary: String?,
    error: String,
    onDismissRequest: () -> Unit,
    onAddItem: (String, List<BragItem>) -> Unit,
) {
    val today = LocalDate.now()
    val initialTitle = stringResource(
        R.string.placeholder_summary_title,
        today.monthValue,
        today.year,
    )
    var title by remember {
        mutableStateOf(initialTitle)
    }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.create_new_summary_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = { onDismissRequest() }) {
                            Icon(
                                Icons.Filled.Close,
                                stringResource(id = R.string.button_close),
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            enabled = title.isNotEmpty() && newSummary.isNullOrEmpty() && loading.not(),
                            onClick = {
                                onAddItem(title, itemsToSelect)
                            },
                        ) {
                            Text(stringResource(R.string.button_generate))
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(GenerateSummaryDialog.modalPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(GenerateSummaryDialog.contentSpacing),
            ) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = {
                    Text(
                        stringResource(
                            R.string.title_for_summary,
                        ),
                    )
                }, value = title, onValueChange = { title = it })
                if (error.isNotEmpty()) {
                    Row() {
                        Text(error)
                        Button(onClick = {
                            onAddItem(title, itemsToSelect)
                        }) {
                            Text(text = stringResource(R.string.button_try_again))
                        }
                    }
                }
                val summaryTitleText = when {
                    loading -> stringResource(R.string.generating_summary)
                    newSummary != null -> stringResource(R.string.summary)
                    else -> ""
                }
                if (newSummary.isNullOrEmpty().not()) Text(summaryTitleText, style = MaterialTheme.typography.titleLarge)
                if (loading) CircularProgressIndicator()
                Text("$newSummary")
                if (itemsToSelect.isNotEmpty()) Text(stringResource(R.string.items_included), style = MaterialTheme.typography.titleLarge)
                itemsToSelect.map {
                    Text(
                        it.text,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                GenerateSummaryDialog.shape,
                            )
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(GenerateSummaryDialog.padding),
                    )
                }
            }
        }
    }
}

object GenerateSummaryDialog {
    val shape = RoundedCornerShape(12.dp)
    val padding = 12.dp
    val contentSpacing = 12.dp
    val modalPadding = 16.dp
}
