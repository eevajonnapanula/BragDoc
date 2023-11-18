package com.eevajonna.bragdocument.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eevajonna.bragdocument.data.AppDatabase
import com.eevajonna.bragdocument.data.BragDocRepository
import com.eevajonna.bragdocument.data.BragDocRepositoryImpl
import com.eevajonna.bragdocument.data.BragItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class BragDocViewModel(application: Application) : ViewModel() {
    var bragItems by mutableStateOf<List<BragItem>>(emptyList())

    private val repository: BragDocRepository

    init {
        val db = AppDatabase.getDatabase(application)
        val dao = db.getDao()

        repository = BragDocRepositoryImpl(dao)
        getItemsAndCategories()
    }

    fun addBragItem(text: String, date: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBragItem(BragItem(text = text, date = date))
        }
    }

    fun deleteBragItem(item: BragItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBragItem(item)
        }
    }

    private fun getItemsAndCategories() {
        viewModelScope.launch {
            repository.getBragItems().collect {
                bragItems = it
            }
        }
    }
}

class BragDocViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BragDocViewModel(application) as T
    }
}
