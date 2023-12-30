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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme
import com.eevajonna.bragdocument.utils.Language
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateSummaryDialog(
    itemsToSelect: List<BragItem>,
    loading: Boolean,
    newSummary: String?,
    error: String,
    onDismissRequest: () -> Unit,
    onAddItem: (String, List<BragItem>, Language) -> Unit,
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

    var selectedLanguage by remember {
        mutableStateOf(Language.EN)
    }

    fun toggleSelect(item: BragItem) {
        selectedItems = if (selectedItems.contains(item.id)) {
            selectedItems.minus(item.id)
        } else {
            selectedItems.plus(item.id)
        }
    }

    fun addItems() {
        val items = itemsToSelect.filter { selectedItems.contains(it.id) }
        onAddItem(title, items, selectedLanguage)
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
                                addItems()
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
                if (error.isNotEmpty()) {
                    ErrorContent(error) {
                        addItems()
                    }
                }
                when {
                    loading -> CircularProgressIndicator()
                    newSummary.isNullOrEmpty().not() -> {
                        GeneratedSummary(summaryTitle = title, summaryText = newSummary)
                    }
                    else -> {
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = {
                            Text(
                                stringResource(
                                    R.string.title_for_summary,
                                ),
                            )
                        }, value = title, onValueChange = { title = it })
                        LanguageSelect(selectedLanguage = selectedLanguage) {
                            selectedLanguage = it
                        }
                        ItemSelect(items = itemsToSelect, selectedItems = selectedItems) {
                            toggleSelect(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorContent(error: String, addItems: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(
                GenerateSummaryDialog.shape,
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(GenerateSummaryDialog.padding),
        verticalArrangement = Arrangement.spacedBy(GenerateSummaryDialog.contentSpacing),
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
        )
        Button(
            onClick = {
                addItems()
            },
        ) {
            Text(
                text = stringResource(R.string.button_try_again),
            )
        }
    }
}

@Composable
fun GeneratedSummary(summaryTitle: String, summaryText: String?) {
    Text(summaryTitle, style = MaterialTheme.typography.titleLarge)
    Text("$summaryText")
}

@Composable
fun ItemSelect(items: List<BragItem>, selectedItems: Set<Long>, toggleSelect: (BragItem) -> Unit) {
    Text(
        stringResource(R.string.items_included),
        style = MaterialTheme.typography.titleLarge,
    )
    Text(stringResource(R.string.select_at_least_three_items))

    items.map {
        SelectableItem(it, selectedItems.contains(it.id)) { item ->
            toggleSelect(item)
        }
    }
}

@Composable
fun LanguageSelect(selectedLanguage: Language, setSelectedLanguage: (Language) -> Unit) {
    Text(
        stringResource(R.string.title_select_language),
        style = MaterialTheme.typography.titleLarge,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(GenerateSummaryDialog.padding)) {
        ToggleableItem(text = stringResource(R.string.lang_english), selected = selectedLanguage == Language.EN) {
            setSelectedLanguage(if (it) Language.EN else Language.FI)
        }
        ToggleableItem(text = stringResource(R.string.lang_finnish), selected = selectedLanguage == Language.FI) {
            setSelectedLanguage(if (it) Language.FI else Language.EN)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GenerateSummaryDialogPreview() {
    fun a(b: String, c: List<BragItem>, d: Language) {}
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
