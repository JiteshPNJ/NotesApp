package com.truehira.simplenotesapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.truehira.simplenotesapp.domain.model.SimpleNote
import com.truehira.simplenotesapp.domain.repository.SimpleNotesRepository


class SimpleNotesRepositoryImpl(
    private val simpleNotesDao: SimpleNotesDao
) : SimpleNotesRepository {


    override fun getAllSimpleNotes(): LiveData<List<SimpleNote>> {
        return simpleNotesDao.getSimpleNotes()
    }

    override suspend fun getSimpleNoteById(id: Int): SimpleNote? {
        return simpleNotesDao.getSimpleNoteById(id)
    }

    override suspend fun insertOrUpdateSimpleNote(simpleNote: SimpleNote) {
        simpleNotesDao.insertOrUpdateSimpleNote(simpleNote)
    }

    override suspend fun deleteSimpleNoteById(id: Int) {
       val deleted = simpleNotesDao.deleteSimpleNoteById(id)
        Log.d("Deleted", "note deleted $deleted")
    }
}