package com.truehira.simplenotesapp.domain.repository

import androidx.lifecycle.LiveData
import com.truehira.simplenotesapp.domain.model.SimpleNote

interface SimpleNotesRepository {

    fun getAllSimpleNotes(): LiveData<List<SimpleNote>>

    suspend fun getSimpleNoteById(id: Int): SimpleNote?

    suspend fun insertOrUpdateSimpleNote(simpleNote: SimpleNote)

    suspend fun deleteSimpleNoteById(id: Int)
}