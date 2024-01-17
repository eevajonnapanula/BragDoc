package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.data.BragItem

@Composable
fun ToggleableItem(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onSelect: (Boolean) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = ToggleableItem.verticalPadding)
            .toggleable(
                value = selected,
                role = Role.RadioButton,
            ) {
                onSelect(it)
            },
    ) {
        Icon(Icons.Default.Done, null, tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else Color.Transparent)
        Text(text, style = style)
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

object ToggleableItem {
    val verticalPadding = 4.dp
}