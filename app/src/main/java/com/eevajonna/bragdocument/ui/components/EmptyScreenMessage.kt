package com.eevajonna.bragdocument.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyScreenMessage(titleText: String, text: String, buttonText: String, buttonEnabled: Boolean = true, buttonAction: () -> Unit) {
    Card(
        modifier = Modifier.padding(EmptyScreenMessage.cardPadding),
    ) {
        Column(modifier = Modifier.padding(EmptyScreenMessage.contentPadding), verticalArrangement = Arrangement.spacedBy(EmptyScreenMessage.contentSpacing)) {
            Text(
                titleText,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text,
            )
            Button(modifier = Modifier.padding(vertical = EmptyScreenMessage.buttonPadding), shape = EmptyScreenMessage.shape, enabled = buttonEnabled, onClick = { buttonAction() }) {
                Text(buttonText, modifier = Modifier.padding(EmptyScreenMessage.buttonPadding))
            }
        }
    }
}

object EmptyScreenMessage {
    val cardPadding = 12.dp
    val contentPadding = 12.dp
    val contentSpacing = 16.dp
    val buttonPadding = 8.dp
    val shape = RoundedCornerShape(12.dp)
}
