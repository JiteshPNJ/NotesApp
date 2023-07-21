package com.truehira.simplenotesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.truehira.simplenotesapp.domain.model.SimpleNote
import kotlinx.coroutines.flow.Flow


@Dao
interface SimpleNotesDao {

    @Query("SELECT * FROM simplenote")
    fun getSimpleNotes(): LiveData<List<SimpleNote>>

    @Query("SELECT * FROM simplenote WHERE id = :id")
    suspend fun getSimpleNoteById(id: Int): SimpleNote?

    @Upsert
    suspend fun insertOrUpdateSimpleNote(note: SimpleNote)

    @Query("DELETE FROM simplenote WHERE id = :id")
    suspend fun deleteSimpleNoteById(id: Int): Int
}

