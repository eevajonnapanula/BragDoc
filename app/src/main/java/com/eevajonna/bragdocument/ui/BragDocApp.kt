package com.eevajonna.bragdocument.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.data.BragItem
import com.eevajonna.bragdocument.data.Summary
import com.eevajonna.bragdocument.ui.components.AddItemDialog
import com.eevajonna.bragdocument.ui.components.DeleteAlertDialog
import com.eevajonna.bragdocument.ui.components.GenerateSummaryDialog
import com.eevajonna.bragdocument.ui.components.NavBar
import com.eevajonna.bragdocument.ui.screens.BragItemsScreen
import com.eevajonna.bragdocument.ui.screens.NavRoutes
import com.eevajonna.bragdocument.ui.screens.SummariesScreen
import kotlinx.coroutines.launch

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
    var showGenerateSummaryDialog by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf<BragItem?>(null)
    }
    var showDeleteSummaryDialog by remember {
        mutableStateOf(false)
    }
    var selectedSummary by remember {
        mutableStateOf<Summary?>(null)
    }

    var currentScreen by remember {
        mutableStateOf(NavRoutes.Items.route)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
            Column(verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.End) {
                when (currentScreen) {
                    NavRoutes.Items.route -> {
                        ExtendedFloatingActionButton(
                            onClick = { showAddItemDialog = true },
                            icon = {
                                Icon(Icons.Default.Add, contentDescription = null)
                            },
                            text = {
                                Text("Add item")
                            },
                        )
                    }
                    NavRoutes.Summaries.route -> ExtendedFloatingActionButton(
                        onClick = {
                            if (viewModel.bragItems.count { it.summaryId == null } >= 3) {
                                showGenerateSummaryDialog = true
                            } else
                                scope.launch {
                                    snackbarHostState.showSnackbar("You need to have at least three items in your list that are not yet part of a summary.")
                                }
                        },
                        icon = {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        },
                        text = { Text("Generate summary") },
                    )
                }
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
                    currentScreen = NavRoutes.Items.route
                }
                composable(NavRoutes.Summaries.route) {
                    SummariesScreen(viewModel.summaries) { summary ->
                        showDeleteSummaryDialog = true
                        selectedSummary = summary
                    }
                    currentScreen = NavRoutes.Summaries.route
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
        if (showGenerateSummaryDialog) {
            GenerateSummaryDialog(
                itemsToSelect = viewModel.bragItems.filter { items -> items.summaryId == null },
                loading = viewModel.loading,
                newSummary = viewModel.summary,
                error = viewModel.error,
                onDismissRequest = { showGenerateSummaryDialog = false },
            ) { title, items ->
                viewModel.generateSummary(title, items)
            }
        }
        if (showDeleteItemDialog) {
            DeleteAlertDialog(titleTextItem = selectedItem?.text, onDismiss = { showDeleteItemDialog = false }) {
                selectedItem?.let { item ->
                    viewModel.deleteBragItem(
                        item,
                    )
                }
                showDeleteItemDialog = false
            }
        }

        if (showDeleteSummaryDialog) {
            DeleteAlertDialog(titleTextItem = selectedSummary?.title, onDismiss = { showDeleteSummaryDialog = false }) {
                selectedSummary?.let { summary ->
                    viewModel.deleteSummary(summary)
                }
                showDeleteSummaryDialog = false
            }
        }
    }
}
