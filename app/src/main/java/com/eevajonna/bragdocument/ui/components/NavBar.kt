package com.eevajonna.bragdocument.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.ui.screens.NavRoutes
import com.eevajonna.bragdocument.ui.screens.NavigationItem

@Composable
fun NavBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(NavRoutes.Items.route) }

    val navItems = listOf(
        NavigationItem(
            Icons.Filled.List,
            NavRoutes.Items.route,
            stringResource(R.string.items),
        ),
        NavigationItem(
            Icons.Filled.Favorite,
            NavRoutes.Summaries.route,
            stringResource(R.string.summaries),
        ),
    )

    NavigationBar {
        navItems.forEach { navigationItem ->
            NavigationBarItem(
                selected = selectedItem == navigationItem.route,
                onClick = {
                    navController.navigate(navigationItem.route)
                    selectedItem = navigationItem.route
                },
                label = { Text(navigationItem.text) },
                icon = { Icon(navigationItem.icon, contentDescription = null) },
            )
        }
    }
}
