package com.javaria.cats.data.network

import okhttp3.ResponseBody

/**
 * This class will be generic and handle all type of retrofit response and errors
 * and we had used sealed class to wrap our Api responses
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