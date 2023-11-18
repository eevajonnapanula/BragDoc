package com.eevajonna.bragdocument.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object TextUtils {
    fun formatDate(date: LocalDate, pattern: String = "dd.MM"): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }
}
