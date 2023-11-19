package com.eevajonna.bragdocument.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

@Entity
data class Summary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val title: String? = null,
    val date: LocalDate = LocalDate.now(),
)

data class SummaryWithItems(
    @Embedded val summary: Summary,
    @Relation(
        parentColumn = "id",
        entityColumn = "summaryId",
    )
    val items: List<BragItem>,
)
