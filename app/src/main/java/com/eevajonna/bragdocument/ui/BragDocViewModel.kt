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
import com.eevajonna.bragdocument.data.Summary
import com.eevajonna.bragdocument.data.SummaryWithItems
import com.eevajonna.bragdocument.openai.OpenAIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class BragDocViewModel(application: Application) : ViewModel() {
    var bragItems by mutableStateOf<List<BragItem>>(emptyList())
    var summaries by mutableStateOf<List<SummaryWithItems>>(emptyList())
    var summary by mutableStateOf("")
    var loading by mutableStateOf(false)
    var error by mutableStateOf("")

    private val repository: BragDocRepository
    private val openAIService: OpenAIService

    init {
        val db = AppDatabase.getDatabase(application)
        val dao = db.getDao()

        repository = BragDocRepositoryImpl(dao)
        openAIService = OpenAIService()
        getItems()
        getSummaries()
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

    fun deleteSummary(summary: Summary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSummary(summary)
        }
    }

    fun generateSummary(title: String, itemsInSummary: List<BragItem>) {
        val textItems = itemsInSummary.map { it.text }

        try {
            viewModelScope.launch(Dispatchers.IO) {
                loading = true

                val summaryContent = openAIService.getPerformanceReview(textItems)

                summaryContent?.let { content ->
                    summary = content
                    val summaryId = repository.addSummary(
                        Summary(
                            title = title,
                            text = content,
                        ),
                    )

                    itemsInSummary.forEach {
                        repository.updateBragItem(it.copy(summaryId = summaryId))
                    }
                }

                loading = false
            } } catch (e: Exception) {
            error = "Something went wrong."
        }
    }

    private fun getItems() {
        viewModelScope.launch {
            repository.getBragItems().collect {
                bragItems = it
            }
        }
    }

    private fun getSummaries() {
        viewModelScope.launch {
            repository.getSummaries().collect {
                summaries = it
            }
        }
    }
}

class BragDocViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BragDocViewModel(application) as T
    }
}
