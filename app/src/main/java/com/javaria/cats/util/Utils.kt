package com.javaria.cats.util

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.javaria.cats.data.network.ResultResource
import java.io.Console

/**
 * Generic function for handling api error
 */
fun Fragment.handleApiError(
        failure: ResultResource.Failure,
        retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
                "Network Error: Please check internet connection",
                retry
        )
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

