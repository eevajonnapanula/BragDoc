package com.eevajonna.bragdocument.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.ui.screens.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(currentScreen: String, navController: NavController) {
    TopAppBar(
        title = {
            val titleResId = when (currentScreen) {
                NavRoutes.Items.route -> NavRoutes.Items.titleResId
                NavRoutes.Summaries.route -> NavRoutes.Summaries.titleResId
                NavRoutes.Settings.route -> NavRoutes.Settings.titleResId
                else -> 0 // no-op
            }
            Text(
                text = stringResource(id = titleResId),
                modifier = Modifier.semantics { heading() },
            )
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(NavRoutes.Settings.route)
            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = stringResource(R.string.settings))
            }
        },
    )
}
