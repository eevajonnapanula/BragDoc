package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.ui.components.MessageCard
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme
import com.eevajonna.bragdocument.utils.TextUtils
import java.time.LocalDate

@Composable
fun BragItemsScreen(
    bragItems: List<BragItem>,
    onEmptyStateButtonClick: () -> Unit,
    onDelete: (BragItem) -> Unit,
) {
    val itemsGroupedByYear = bragItems.groupBy { it.date.year }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            if (bragItems.isEmpty()) {
                MessageCard(
                    titleText = stringResource(R.string.items_empty_state_title),
                    text = stringResource(R.string.items_empty_state_content),
                    buttonText = stringResource(id = R.string.button_add_item),
                ) {
                    onEmptyStateButtonClick()
                }
            }
        }
        itemsGroupedByYear.entries
            .sortedByDescending { it.key }
            .map { (year, itemsForYear) ->
                item {
                    Text(
                        year.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            vertical = BragItemsScreen.yearPaddingVertical,
                            horizontal = BragItemsScreen.yearPaddingHorizontal,
                        ).semantics {
                            heading()
                        },
                    )
                }

                items(
                    itemsForYear
                        .sortedWith(
                            compareByDescending<BragItem> {
                                it.date
                            }.thenBy {
                                it.summaryId
                            },
                        ),
                ) { item ->
                    BragItemListItem(item) {
                        onDelete(item)
                    }
                    Divider(
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }
    }
}

@Composable
fun BragItemListItem(item: BragItem, onDeleteIconClick: (BragItem) -> Unit) {
    val background = if (item.summaryId != null) {
        MaterialTheme.colorScheme.surfaceVariant
    } else MaterialTheme.colorScheme.secondaryContainer
    val deleteText = stringResource(id = R.string.button_delete)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(BragItemsScreen.itemPadding)
            .semantics(mergeDescendants = true) {
                customActions = listOf(
                    CustomAccessibilityAction(deleteText) {
                        onDeleteIconClick(item)
                        true
                    },
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(5f),
            verticalArrangement = Arrangement.spacedBy(BragItemsScreen.itemContentPadding),
        ) {
            Text(
                text = TextUtils.formatFullMonth(item.date),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = item.text,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        IconButton(
            modifier = Modifier.weight(1f).clearAndSetSemantics {},
            onClick = {
                onDeleteIconClick(item)
            },
        ) {
            Icon(Icons.Filled.Close, deleteText)
        }
    }
}

@Preview
@Composable
fun BragItemScreenPreview() {
    BragDocumentTheme {
        BragItemsScreen(
            bragItems = listOf(
                BragItem(0, LocalDate.now(), "Helped my colleague to solve an issue.", 1),
                BragItem(1, LocalDate.now().minusMonths(1), "Mentored a more junior colleague", 2),
                BragItem(2, LocalDate.now().minusMonths(4), "Solved very complex issues"),
                BragItem(1, LocalDate.now().minusYears(1), "Mentored a more junior colleague", 2),
                BragItem(2, LocalDate.now().minusMonths(6).minusYears(1), "Solved very complex issues"),
            ),
            {},
        ) {}
    }
}

@Preview
@Composable
fun BragItemListItemPreview() {
    BragDocumentTheme {
        BragItemListItem(
            BragItem(2, LocalDate.now().minusMonths(6).minusYears(1), "Solved very complex issues"),
        ) {}
    }
}

object BragItemsScreen {
    val yearPaddingVertical = 12.dp
    val yearPaddingHorizontal = 16.dp
    val itemContentPadding = 8.dp
    val itemPadding = 12.dp
}
