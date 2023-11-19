package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.Summary
import com.eevajonna.bragdocument.data.SummaryWithItems
import com.eevajonna.bragdocument.ui.components.EmptyScreenMessage
import com.eevajonna.bragdocument.ui.components.SummaryCard

@Composable
fun SummariesScreen(summaries: List<SummaryWithItems>, itemsCount: Int, onEmptyStateButtonClick: () -> Unit, onDeleteSummary: (Summary) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (summaries.isEmpty()) {
            EmptyScreenMessage(
                titleText = stringResource(R.string.summaries_empty_state_title),
                text = stringResource(R.string.summaries_empty_state_content),
                buttonEnabled = itemsCount >= 3,
                buttonText = stringResource(id = R.string.button_generate_summary),
            ) { onEmptyStateButtonClick() }
        }
        summaries.map {
            SummaryCard(summary = it.summary, onDeleteSummary = onDeleteSummary)
        }
    }
}
