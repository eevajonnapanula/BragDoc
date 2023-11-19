package com.eevajonna.bragdocument.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Summary ADD COLUMN date TEXT NOT NULL DEFAULT \"${LocalDate.now()}\"")
        database.execSQL("ALTER TABLE Summary ADD COLUMN title TEXT NULL")
    }
}
