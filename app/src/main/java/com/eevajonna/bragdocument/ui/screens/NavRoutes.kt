package com.eevajonna.bragdocument.ui.screens

import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoutes {
    data object Items : NavRoutes() {
        const val route = "items"
    }

    data object Summaries : NavRoutes() {
        const val route = "summaries"
    }
}

class NavigationItem(val icon: ImageVector, val route: String, val text: String)
