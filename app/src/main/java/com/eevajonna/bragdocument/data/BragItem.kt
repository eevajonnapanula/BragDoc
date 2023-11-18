package com.eevajonna.bragdocument.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate

@Entity
data class BragItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val text: String = "",
)

class Converters {
    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value) }
    }

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date.toString()
    }
}
