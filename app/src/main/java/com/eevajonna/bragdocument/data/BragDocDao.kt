package com.eevajonna.bragdocument.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BragDocDao {
    @Query("SELECT * FROM BragItem")
    fun getBragItems(): Flow<List<BragItem>>

    @Query("SELECT * FROM BragItem WHERE :id = id")
    fun getBragItem(id: Long): Flow<BragItem>

    @Insert
    fun addBragItem(bragItem: BragItem)

    @Delete
    fun deleteBragItem(bragItem: BragItem)
}
