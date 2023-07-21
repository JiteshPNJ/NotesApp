package com.truehira.simplenotesapp.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class SimpleNoteEvent : Parcelable {
    object Add : SimpleNoteEvent()
    data class Edit(val noteId: Int) : SimpleNoteEvent()
}