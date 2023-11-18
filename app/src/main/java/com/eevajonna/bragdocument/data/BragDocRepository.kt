package com.eevajonna.bragdocument.data

import kotlinx.coroutines.flow.Flow

interface BragDocRepository {
    suspend fun getBragItems(): Flow<List<BragItem>>

    suspend fun getBragItem(id: Long): Flow<BragItem>

    suspend fun addBragItem(bragItem: BragItem)

    suspend fun deleteBragItem(bragItem: BragItem)
}

class BragDocRepositoryImpl(private val dao: BragDocDao) : BragDocRepository {
    override suspend fun getBragItems(): Flow<List<BragItem>> = dao.getBragItems()

    override suspend fun getBragItem(id: Long): Flow<BragItem> = dao.getBragItem(id)

    override suspend fun addBragItem(bragItem: BragItem) = dao.addBragItem(bragItem)

    override suspend fun deleteBragItem(bragItem: BragItem) = dao.deleteBragItem(bragItem)
}
