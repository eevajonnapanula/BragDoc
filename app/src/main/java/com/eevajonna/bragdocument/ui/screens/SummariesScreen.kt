package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.data.Summary
import com.eevajonna.bragdocument.data.SummaryWithItems

@Composable
fun SummariesScreen(summaries: List<SummaryWithItems>, onDeleteSummary: (Summary) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        summaries.map {
            SummaryCard(summary = it.summary, onDeleteSummary = onDeleteSummary)
        }
    }
}

@Composable
fun SummaryCard(summary: Summary, onDeleteSummary: (Summary) -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("${summary.title}", style = MaterialTheme.typography.titleLarge)
                if (expanded) {
                    IconButton(onClick = { onDeleteSummary(summary) }) {
                        Icon(Icons.Outlined.Delete, "Delete")
                    }
                }
            }
            if (expanded) {
                Button(onClick = { clipboardManager.setText(AnnotatedString(summary.text)) }) {
                    Text("Copy text")
                }
                SelectionContainer {
                    Text(summary.text)
                }
            }
        }
    }
}
