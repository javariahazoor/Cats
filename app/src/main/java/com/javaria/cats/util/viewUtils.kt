package com.javaria.cats.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Show Snack bar
 * @param meesage: error message
 * @param retry: it is an optional parameter
 * */
fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}