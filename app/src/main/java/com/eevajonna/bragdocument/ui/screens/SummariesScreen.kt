package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.data.Summary
import com.eevajonna.bragdocument.data.SummaryWithItems
import com.eevajonna.bragdocument.ui.components.SummaryCard

@Composable
fun SummariesScreen(summaries: List<SummaryWithItems>, onDeleteSummary: (Summary) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        summaries.map {
            SummaryCard(summary = it.summary, onDeleteSummary = onDeleteSummary)
        }
    }
}
