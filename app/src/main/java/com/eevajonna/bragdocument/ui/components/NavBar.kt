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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun NavBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(NavRoutes.Items.route) }

    val navItems = listOf(
        NavigationItem(
            Icons.Filled.List,
            NavRoutes.Items.route,
            "Items",
        ),
        NavigationItem(Icons.Filled.Favorite, NavRoutes.Summaries.route, "Summaries"),
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

class NavigationItem(val icon: ImageVector, val route: String, val text: String)

sealed class NavRoutes {
    data object Items : NavRoutes() {
        const val route = "items"
    }

    data object Summaries : NavRoutes() {
        const val route = "summaries"
    }
}
