package com.truehira.simplenotesapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.truehira.simplenotesapp.databinding.SimpleNotesItemBinding
import com.truehira.simplenotesapp.domain.model.SimpleNote



class SimpleNotesAdapter(private val onNoteClicked: (SimpleNote) -> Unit) : ListAdapter<SimpleNote, SimpleNotesAdapter.SimpleNotesViewHolder>(ComparatorDiffUtil()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleNotesViewHolder {
            val binding = SimpleNotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SimpleNotesViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SimpleNotesViewHolder, position: Int) {
            val simpleNote = getItem(position)
            holder.bind(simpleNote)

        }

        inner class SimpleNotesViewHolder(private val binding: SimpleNotesItemBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(note: SimpleNote) {
                binding.tvNoteTitle.text = note.title
                binding.root.setOnClickListener {
                    onNoteClicked(note)
                }
            }

        }

        class ComparatorDiffUtil : DiffUtil.ItemCallback<SimpleNote>() {
            override fun areItemsTheSame(oldItem: SimpleNote, newItem: SimpleNote): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SimpleNote, newItem: SimpleNote): Boolean {
                return oldItem == newItem
            }

        }
    }
