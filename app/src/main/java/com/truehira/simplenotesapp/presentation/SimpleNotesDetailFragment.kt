package com.truehira.simplenotesapp.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.truehira.simplenotesapp.R
import com.truehira.simplenotesapp.databinding.FragmentSimpleNotesDetailBinding
import com.truehira.simplenotesapp.domain.model.SimpleNote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SimpleNotesDetailFragment : Fragment() {

    companion object {
        const val CONFORMATION_REQUEST_KEY = "conformation_result_key"
        const val DELETE_NOTE_KEY = "delete_note_key"
        const val TAG = "SimpleNotesDetail"
    }

    private val args by navArgs<SimpleNotesDetailFragmentArgs>()

    @Inject
    internal lateinit var factory: SimpleNotesDetailViewModel.Factory

    private val viewModel: SimpleNotesDetailViewModel by viewModels {
        SimpleNotesDetailViewModel.provideSimpleNotesDetailViewModelFactory(
            factory,
            args.noteId
        )
    }

    private lateinit var binding: FragmentSimpleNotesDetailBinding


    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.simple_notes_detail_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.delete -> {
                    setupDeleteConformationResultListener()
                    findNavController().navigate(R.id.action_simpleNotesDetailFragment_to_deleteSimpleNoteConfirmationDialogFragment)
                    false
                }

                else -> {
                    true
                }
            }
        }

    }


    private fun setupDeleteConformationResultListener() {
        setFragmentResultListener(CONFORMATION_REQUEST_KEY) { requestKey, bundle ->
            Log.d("ConformationListener", "setFragmentResultListener")
            if (bundle.isEmpty) return@setFragmentResultListener
            val shouldDeleteNote = bundle.getBoolean(DELETE_NOTE_KEY)
            Log.d("ConformationListener", "Should delete: $shouldDeleteNote")
            bundle.clear()
            if (shouldDeleteNote) {
                viewModel.shouldSaveNote = false
                viewModel.deleteSimpleNote()
                findNavController().navigateUp()
            }
            clearFragmentResult(requestKey)
        }
    }


    private val menuHost: MenuHost
        get() = requireActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding = FragmentSimpleNotesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
    }


    override fun onPause() {
        super.onPause()
        if (viewModel.shouldSaveNote) {
            val noteTitle = binding.etNoteDetailEditableTitle.text.toString()
            val noteContent = binding.etNoteDetailEditableContent.text.toString()
            if(args.noteId == -1){
                viewModel.insertSimpleNote(SimpleNote(title =  noteTitle, content = noteContent))
            }else{
                viewModel.updateSimpleNote(noteTitle, noteContent)
            }

        }
        menuHost.removeMenuProvider(menuProvider)

    }

    private fun setInitialData() {
        if (args.noteId != -1) {
            lifecycleScope.launch {
                val simpleNote = viewModel.getSimpleNote(args.noteId)
                simpleNote?.let {
                    binding.etNoteDetailEditableContent.setText(simpleNote.content)
                    binding.etNoteDetailEditableTitle.setText(simpleNote.title)
                }
            }
        }


    }


}