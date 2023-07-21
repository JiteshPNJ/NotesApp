package com.truehira.simplenotesapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.truehira.simplenotesapp.data.SimpleNotesAdapter
import com.truehira.simplenotesapp.databinding.FragmentSimpleNotesBinding
import com.truehira.simplenotesapp.utils.SimpleNoteEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SimpleNotesFragment : Fragment() {


    private lateinit var binding: FragmentSimpleNotesBinding
    private lateinit var simpleNotesAdapter: SimpleNotesAdapter
    private val viewModel: SimpleNotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupSimpleNotesAdapter()
        binding = FragmentSimpleNotesBinding.inflate(inflater, container, false)
        setupSimpleNotesRecyclerView()
        setupAddNotesClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
    }

    private fun setupSimpleNotesRecyclerView() {
        binding.rvSimpleNotes.apply {
            adapter = simpleNotesAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSimpleNotesAdapter() {
        simpleNotesAdapter = SimpleNotesAdapter {
            navigateToDetailScreen(it.id)
        }
    }

    private fun bindObservers() {
        viewModel.simpleNotesList.observe(viewLifecycleOwner) { simpleNotesList ->
            simpleNotesAdapter.submitList(simpleNotesList)
        }
    }

    private fun setupAddNotesClickListener() {
        binding.fabAddSimpleNote.setOnClickListener {
            navigateToDetailScreen(-1)
        }
    }

    private fun navigateToDetailScreen(noteId: Int) {
        val action =
            SimpleNotesFragmentDirections.actionSimpleNotesFragmentToSimpleNotesDetailFragment(
                noteId
            )
        findNavController().navigate(action)
    }

}