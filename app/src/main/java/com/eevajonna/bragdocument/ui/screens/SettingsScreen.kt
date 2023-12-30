package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme

@Composable
fun SettingsScreen(languageSelectionOn: Boolean, toggleLanguageSelection: (Boolean) -> Unit) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(stringResource(R.string.title_language_selection), style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(languageSelectionOn, role = Role.RadioButton) {
                    toggleLanguageSelection(it)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.setting_language_selection), modifier = Modifier.weight(5f))
            Switch(
                modifier = Modifier
                    .weight(1.5f)
                    .clearAndSetSemantics {},
                checked = languageSelectionOn,
                onCheckedChange = {
                    toggleLanguageSelection(it)
                },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    BragDocumentTheme {
        SettingsScreen(true) {}
    }
}
