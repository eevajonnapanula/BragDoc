package com.eevajonna.bragdocument.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BragDocDao {
    @Query("SELECT * FROM BragItem")
    fun getBragItems(): Flow<List<BragItem>>

    @Query("SELECT * FROM BragItem WHERE :id = id")
    fun getBragItem(id: Long): Flow<BragItem>

    @Transaction
    @Query("SELECT * FROM Summary")
    fun getSummaries(): Flow<List<SummaryWithItems>>

    @Insert
    fun insertSummary(summary: Summary): Long

    @Update
    fun updateBragItem(bragItem: BragItem)

    @Insert
    fun addBragItem(bragItem: BragItem)

    @Delete
    fun deleteBragItem(bragItem: BragItem)

    @Delete
    fun deleteSummary(summary: Summary)

    @Query("UPDATE BragItem SET summaryId = NULL WHERE summaryId = :id")
    fun setSummaryIdsNull(id: Long)
}
