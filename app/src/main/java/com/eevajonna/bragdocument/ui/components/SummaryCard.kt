package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.Summary

@Composable
fun SummaryCard(summary: Summary, onDeleteSummary: (Summary) -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(SummaryCard.contentPadding), verticalArrangement = Arrangement.spacedBy(SummaryCard.contentSpacing)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }) {
                Text("${summary.title}", style = MaterialTheme.typography.titleLarge)
                if (expanded) {
                    IconButton(onClick = { onDeleteSummary(summary) }) {
                        Icon(Icons.Outlined.Delete, stringResource(id = R.string.button_delete))
                    }
                }
            }
            if (expanded) {
                Button(onClick = { clipboardManager.setText(AnnotatedString(summary.text)) }) {
                    Text(stringResource(R.string.button_copy_text))
                }
                SelectionContainer {
                    Text(summary.text)
                }
            }
        }
    }
}

object SummaryCard {
    val contentPadding = 24.dp
    val contentSpacing = 8.dp
}
