package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme
import com.eevajonna.bragdocument.utils.TextUtils
import java.time.LocalDate

@Composable
fun BragItemsScreen(bragItems: List<BragItem>, onDelete: (BragItem) -> Unit) {
    val itemsGroupedByYear = bragItems.groupBy { it.date.year }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsGroupedByYear.entries
            .sortedByDescending { it.key }
            .map { (year, itemsForYear) ->
                item {
                    Text(
                        year.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = BragItemsScreen.yearPadding),
                    )
                }
                items(itemsForYear.sortedByDescending { it.date }) { item ->
                    BragItemListItem(item) {
                        onDelete(item)
                    }
                    Divider()
                }
            }
    }
}

@Composable
fun BragItemListItem(item: BragItem, onDeleteIconClick: (BragItem) -> Unit) {
    var deleteVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) {
                deleteVisible = !deleteVisible
            }
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(BragItemsScreen.itemPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BragItemsScreen.itemContentPadding),
    ) {
        if (deleteVisible) {
            IconButton(
                onClick = {
                    onDeleteIconClick(item)
                    deleteVisible = false
                },
            ) {
                Icon(Icons.Filled.Delete, "Delete")
            }
        }
        Month(item.date, modifier = Modifier.weight(1f))
        Text(
            item.text,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.weight(4f),
        )
    }
}

@Composable
fun Month(date: LocalDate, modifier: Modifier = Modifier) {
    val formattedDate = TextUtils.formatDate(date, "LLL")
    Column(
        modifier = modifier
            .size(BragItemsScreen.monthSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(BragItemsScreen.monthPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = formattedDate,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
fun BragItemScreenPreview() {
    BragDocumentTheme {
        BragItemsScreen(
            bragItems = listOf(
                BragItem(0, LocalDate.now(), "Helped my colleague to solve an issue"),
                BragItem(1, LocalDate.now().minusMonths(1), "Mentored a more junior colleague"),
                BragItem(2, LocalDate.now().minusMonths(4), "Solved very complex issues"),
                BragItem(1, LocalDate.now().minusYears(1), "Mentored a more junior colleague"),
                BragItem(2, LocalDate.now().minusMonths(6).minusYears(1), "Solved very complex issues"),
            ),
        ) {}
    }
}

object BragItemsScreen {
    val yearPadding = 12.dp
    val itemContentPadding = 8.dp
    val itemPadding = 12.dp
    val monthSize = 48.dp
    val monthPadding = 12.dp
}
