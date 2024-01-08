package com.eevajonna.bragdocument

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eevajonna.bragdocument.ui.BragDocViewModel
import com.eevajonna.bragdocument.ui.BragDocViewModelFactory
import com.eevajonna.bragdocument.ui.screens.SettingsScreen
import com.eevajonna.bragdocument.ui.theme.BragDocumentTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BragDocumentTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: BragDocViewModel = viewModel(
                        it,
                        "BragDocViewModel",
                        BragDocViewModelFactory(
                            LocalContext.current.applicationContext as Application,
                        ),
                    )

                    SettingsScreen(viewModel) {
                        this.finish()
                    }
                }
            }
        }
    }
}
