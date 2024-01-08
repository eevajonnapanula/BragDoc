package com.eevajonna.bragdocument.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.ui.BragDocViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: BragDocViewModel, onBack: () -> Unit) {
    val langSelectionOn = viewModel.languageSelectionEnabled

    fun toggleLanguageSelection(newVal: Boolean) {
        viewModel.saveLanguageSelection(newVal)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleResId = R.string.settings
                    Text(
                        text = stringResource(id = titleResId),
                        modifier = Modifier.semantics { heading() },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                stringResource(R.string.title_language_selection),
                style = MaterialTheme.typography.titleLarge,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(langSelectionOn, role = Role.RadioButton) { newVal ->
                        toggleLanguageSelection(newVal)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    stringResource(R.string.setting_language_selection),
                    modifier = Modifier.weight(5f),
                )
                Switch(
                    modifier = Modifier
                        .weight(1.5f)
                        .clearAndSetSemantics {},
                    checked = langSelectionOn,
                    onCheckedChange = {
                        toggleLanguageSelection(it)
                    },
                )
            }
        }
    }
}
