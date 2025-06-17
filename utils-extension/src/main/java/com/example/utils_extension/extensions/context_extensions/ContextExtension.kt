package com.example.utils_extension.extensions.context_extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: Any?) {
    val messageString = when (message) {
        is String -> message
        is Int -> getString(message)
        else -> null
    }
    if (messageString.isNullOrEmpty()) return
    try {
        Toast.makeText(this, messageString, Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}