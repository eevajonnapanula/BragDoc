package com.eevajonna.bragdocument.data

import kotlinx.coroutines.flow.Flow

interface BragDocRepository {
    suspend fun getBragItems(): Flow<List<BragItem>>

    suspend fun getBragItem(id: Long): Flow<BragItem>

    suspend fun getSummaries(): Flow<List<SummaryWithItems>>

    suspend fun addBragItem(bragItem: BragItem)

    suspend fun addSummary(summary: Summary): Long

    suspend fun updateBragItem(bragItem: BragItem)

    suspend fun deleteBragItem(bragItem: BragItem)

    suspend fun deleteSummary(summary: Summary)
}

class BragDocRepositoryImpl(private val dao: BragDocDao) : BragDocRepository {
    override suspend fun getBragItems(): Flow<List<BragItem>> = dao.getBragItems()

    override suspend fun getBragItem(id: Long): Flow<BragItem> = dao.getBragItem(id)

    override suspend fun getSummaries(): Flow<List<SummaryWithItems>> = dao.getSummaries()

    override suspend fun addBragItem(bragItem: BragItem) = dao.addBragItem(bragItem)

    override suspend fun addSummary(summary: Summary): Long = dao.insertSummary(summary)

    override suspend fun updateBragItem(bragItem: BragItem) = dao.updateBragItem(bragItem)

    override suspend fun deleteBragItem(bragItem: BragItem) = dao.deleteBragItem(bragItem)

    override suspend fun deleteSummary(summary: Summary) {
        dao.deleteSummary(summary)
        dao.setSummaryIdsNull(summary.id)
    }
}
