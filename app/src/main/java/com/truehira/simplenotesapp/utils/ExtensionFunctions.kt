package com.truehira.simplenotesapp.utils

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun View.showSoftInput(window: Window) {
    val controller = WindowCompat.getInsetsController(window, this)
    controller.show(WindowInsetsCompat.Type.ime())
}