package com.eevajonna.bragdocument.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eevajonna.bragdocument.R

@Composable
fun DeleteAlertDialog(titleTextItem: String?, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.delete_dialog_title))
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(stringResource(R.string.button_delete))
            }
        },
        icon = {
            Icon(Icons.Outlined.Delete, contentDescription = null)
        },
        text = {
            Text(text = stringResource(R.string.do_you_want_to_delete, "$titleTextItem"))
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text(stringResource(R.string.button_dismiss))
            }
        },
        onDismissRequest = { onDismiss() },
    )
}
