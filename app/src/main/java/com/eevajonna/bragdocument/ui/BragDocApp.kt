package com.eevajonna.bragdocument.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.ui.components.AddItemDialog
import com.eevajonna.bragdocument.ui.components.NavBar
import com.eevajonna.bragdocument.ui.components.NavRoutes
import com.eevajonna.bragdocument.ui.screens.BragItemsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BragDocApp(viewModel: BragDocViewModel) {
    val navController = rememberNavController()
    var showAddItemDialog by remember {
        mutableStateOf(false)
    }
    var showDeleteItemDialog by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf<BragItem?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.semantics { heading() },
                )
            })
        },
        bottomBar = {
            NavBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddItemDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Menu")
            }
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background,
        ) {
            NavHost(navController = navController, startDestination = NavRoutes.Items.route) {
                composable(NavRoutes.Items.route) {
                    BragItemsScreen(viewModel.bragItems) { item ->
                        showDeleteItemDialog = true
                        selectedItem = item
                    }
                }
                composable(NavRoutes.Summaries.route) {
                    // TODO
                }
            }
        }
        if (showAddItemDialog) {
            AddItemDialog(
                onDismissRequest = { showAddItemDialog = false },
                onAddItem = { text, date ->
                    viewModel.addBragItem(text, date)
                    showAddItemDialog = false
                },
            )
        }
        if (showDeleteItemDialog) {
            AlertDialog(
                title = {
                    Text(text = "Are you sure?")
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedItem?.let { item ->
                            viewModel.deleteBragItem(
                                item,
                            )
                        }
                        showDeleteItemDialog = false
                    }) {
                        Text("Delete")
                    }
                },
                icon = {
                    Icon(Icons.Outlined.Delete, contentDescription = null)
                },
                text = {
                    Text(text = "Do you want to delete \"${selectedItem?.text}\"?")
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteItemDialog = false
                        },
                    ) {
                        Text("Dismiss")
                    }
                },
                onDismissRequest = { showDeleteItemDialog = false },
            )
        }
    }
}
