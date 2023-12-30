package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme
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

    var selectedItems by remember {
        mutableStateOf(itemsToSelect.map { it.id }.toSet())
    }

    fun toggleSelect(item: BragItem) {
        if (selectedItems.contains(item.id)) {
            selectedItems = selectedItems.minus(item.id)
        } else {
            selectedItems = selectedItems.plus(item.id)
        }
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
                            enabled = title.isNotEmpty() && newSummary.isNullOrEmpty() && loading.not() && selectedItems.count() >= 3,
                            onClick = {
                                val items = itemsToSelect.filter { selectedItems.contains(it.id) }
                                onAddItem(title, items)
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
                        Button(
                            enabled = selectedItems.count() >= 3,
                            onClick = {
                                val items = itemsToSelect.filter { selectedItems.contains(it.id) }
                                onAddItem(title, items)
                            },
                        ) {
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
                if (newSummary.isNullOrEmpty()) {
                    Text(
                        stringResource(R.string.items_included),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(stringResource(R.string.select_at_least_three_items))

                    itemsToSelect.map {
                        SelectableItem(it, selectedItems.contains(it.id)) {
                            toggleSelect(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableItem(item: BragItem, selected: Boolean, toggleSelect: (BragItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                GenerateSummaryDialog.shape,
            )
            .selectable(
                selected = selected,
                role = Role.Checkbox,
            ) {
                toggleSelect(item)
            }
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(GenerateSummaryDialog.padding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else Color.Transparent,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = item.text,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(5f),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GenerateSummaryDialogPreview() {
    fun a(b: String, c: List<BragItem>) {}
    BragDocumentTheme {
        GenerateSummaryDialog(
            itemsToSelect = listOf(
                BragItem(0, LocalDate.now(), "Helped my colleague to solve an issue.", 1),
                BragItem(1, LocalDate.now().minusMonths(1), "Mentored a more junior colleague", 2),
                BragItem(2, LocalDate.now().minusMonths(4), "Solved very complex issues"),
                BragItem(1, LocalDate.now().minusYears(1), "Mentored a more junior colleague", 2),
                BragItem(2, LocalDate.now().minusMonths(6).minusYears(1), "Solved very complex issues"),
            ),
            newSummary = "",
            error = "",
            loading = false,
            onDismissRequest = {},
            onAddItem = ::a,
        )
    }
}

object GenerateSummaryDialog {
    val shape = RoundedCornerShape(12.dp)
    val padding = 12.dp
    val contentSpacing = 12.dp
    val modalPadding = 16.dp
}
