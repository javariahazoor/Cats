package com.javaria.cats.util

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.javaria.cats.data.network.ResultResource
import java.io.Console

fun Fragment.handleApiError(
        failure: ResultResource.Failure,
        retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
                "Please check the internet connection",
                retry
        )
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
            println("test javaria$error")
        }
    }
}

