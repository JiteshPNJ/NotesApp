package com.truehira.simplenotesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.truehira.simplenotesapp.domain.model.SimpleNote
import com.truehira.simplenotesapp.domain.repository.SimpleNotesRepository
import com.truehira.simplenotesapp.utils.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


@HiltViewModel
class SimpleNotesViewModel @Inject constructor(
    private val simpleNotesRepository: SimpleNotesRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val simpleNotesList: LiveData<List<SimpleNote>> = simpleNotesRepository.getAllSimpleNotes()

}