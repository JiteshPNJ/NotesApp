package com.truehira.simplenotesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.truehira.simplenotesapp.domain.model.SimpleNote
import com.truehira.simplenotesapp.domain.repository.SimpleNotesRepository
import com.truehira.simplenotesapp.utils.IODispatcher
import com.truehira.simplenotesapp.utils.SimpleNoteEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SimpleNotesDetailViewModel @AssistedInject constructor(
    private val simpleNotesRepository: SimpleNotesRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val noteId: Int
) : ViewModel() {

    var shouldSaveNote: Boolean = true


    suspend fun getSimpleNote(noteId: Int): SimpleNote? {
        return withContext(ioDispatcher) {
            simpleNotesRepository.getSimpleNoteById(noteId)
        }
    }


    fun insertSimpleNote(simpleNote: SimpleNote) {
        viewModelScope.launch(ioDispatcher) {
            simpleNotesRepository.insertOrUpdateSimpleNote(simpleNote)
        }
    }

    fun updateSimpleNote(noteTitle: String?, noteContent: String?) {
        viewModelScope.launch(ioDispatcher) {
            if (!noteTitle.isNullOrBlank() || !noteContent.isNullOrBlank()) {
                simpleNotesRepository.insertOrUpdateSimpleNote(
                    SimpleNote(noteId, noteTitle ?: "", noteContent ?: "")
                )
            }

        }
    }

    fun deleteSimpleNote() {

        viewModelScope.launch(ioDispatcher) {
            simpleNotesRepository.deleteSimpleNoteById(noteId)

        }


    }

    @AssistedFactory
    interface Factory {
        fun create(noteId: Int): SimpleNotesDetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideSimpleNotesDetailViewModelFactory(
            factory: Factory,
            noteId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(noteId) as T
                }
            }
        }
    }

}
