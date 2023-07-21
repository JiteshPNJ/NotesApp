package com.truehira.simplenotesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimpleNote(
    @PrimaryKey(autoGenerate = true) val id: Int?= null,
    val title: String,
    val content: String
)
