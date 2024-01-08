package com.eevajonna.bragdocument.ui.components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.SettingsActivity
import com.eevajonna.bragdocument.ui.screens.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(currentScreen: String) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            val titleResId = when (currentScreen) {
                NavRoutes.Items.route -> NavRoutes.Items.titleResId
                NavRoutes.Summaries.route -> NavRoutes.Summaries.titleResId
                else -> 0 // no-op
            }
            Text(
                text = stringResource(id = titleResId),
                modifier = Modifier.semantics { heading() },
            )
        },
        actions = {
            IconButton(onClick = {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = stringResource(R.string.settings))
            }
        },
    )
}
