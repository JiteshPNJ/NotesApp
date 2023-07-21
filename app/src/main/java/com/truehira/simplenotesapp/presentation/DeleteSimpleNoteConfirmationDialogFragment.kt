package com.truehira.simplenotesapp.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.truehira.simplenotesapp.R
import com.truehira.simplenotesapp.databinding.FragmentDeleteSimpleNoteConfirmationDialogBinding


class DeleteSimpleNoteConfirmationDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDeleteSimpleNoteConfirmationDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = it.layoutInflater
            binding =
                FragmentDeleteSimpleNoteConfirmationDialogBinding.inflate(inflater, null, false)
            val builder = MaterialAlertDialogBuilder(it, R.style.ConfirmationDialogTheme)
            builder.setView(binding.root)
            val mDialog = builder.create()
            setupButtonClickListeners()
            mDialog
        } ?: super.onCreateDialog(savedInstanceState)

    }

    private fun setupButtonClickListeners() {
        binding.btnDelete.setOnClickListener {
            setFragmentResult(
                SimpleNotesDetailFragment.CONFORMATION_REQUEST_KEY,
                bundleOf(SimpleNotesDetailFragment.DELETE_NOTE_KEY to true)
            )
            findNavController().navigateUp()
        }

        binding.btnCancel.setOnClickListener {
            setFragmentResult(
                SimpleNotesDetailFragment.CONFORMATION_REQUEST_KEY,
                bundleOf(SimpleNotesDetailFragment.DELETE_NOTE_KEY to false)
            )
            findNavController().navigateUp()
        }
    }

}