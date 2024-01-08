package com.eevajonna.bragdocument.ui.screens

import androidx.compose.ui.graphics.vector.ImageVector
import com.eevajonna.bragdocument.R

sealed class NavRoutes {
    data object Items : NavRoutes() {
        const val route = "items"
        val titleResId = R.string.items
    }

    data object Summaries : NavRoutes() {
        const val route = "summaries"
        val titleResId = R.string.summaries
    }
}

class NavigationItem(val icon: ImageVector, val route: String, val text: String)
