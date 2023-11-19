package com.eevajonna.bragdocument.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteAlertDialog(titleTextItem: String?, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = "Are you sure?")
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Delete")
            }
        },
        icon = {
            Icon(Icons.Outlined.Delete, contentDescription = null)
        },
        text = {
            Text(text = "Do you want to delete \"${titleTextItem}\"?")
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text("Dismiss")
            }
        },
        onDismissRequest = { onDismiss() },
    )
}
