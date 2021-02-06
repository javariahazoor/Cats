package com.javaria.cats.data.network

// this class will be generic and it will handle all type of api responses that is why we have type T
//We can use this sealed class to wrap our Api responses

import okhttp3.ResponseBody

/**
 * This class will handle all type of retrofit response and errors
 */
sealed class ResultResource<out T> {

    data class Success<out T>(val value: T) : ResultResource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResultResource<Nothing>()

    object Loading : ResultResource<Nothing>()
}