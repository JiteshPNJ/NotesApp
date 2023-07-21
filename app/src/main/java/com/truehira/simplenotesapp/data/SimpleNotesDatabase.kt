package com.truehira.simplenotesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.truehira.simplenotesapp.domain.model.SimpleNote

@Database(
    entities = [SimpleNote::class],
    version = 2,
    exportSchema = false
)
abstract class SimpleNotesDatabase: RoomDatabase() {

    abstract val simpleNotesDao: SimpleNotesDao

    companion object {
        const val DATABASE_NAME = "simple_notes_database"
    }
}